/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.environment;

import java.util.ArrayList;

import edu.neumont.pro180.jpearl.chess.Player;
import edu.neumont.pro180.jpearl.chess.pieces.*;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveCase;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;
import edu.neumont.pro180.jpearl.chess.view.CellPanel;

public class Cell
{
	private ArrayList<Piece> pieceLevels;
	private ArrayList<Integer> movesBeforeSimulation;
	private Location location;
	private ChessBoard board;
	private CellPanel viewEquivalent;

	private static final int EN_PASSANT_RANK = 2;
	
	public enum TurnResult{ NO_MOVE_MADE, NORMAL_MOVE, SELF_CHECK, OPPONENT_CHECK, OPPONENT_CHECKMATE, STALEMATE };
	
	public Cell( int x, int y, ChessBoard board )
	{
		viewEquivalent = null;
		location = new Location( x, y );
		pieceLevels = new ArrayList<Piece>();
		pieceLevels.add( null );
		movesBeforeSimulation = new ArrayList<Integer>();
		movesBeforeSimulation.add( null );
		this.board = board;
	}
	
	//Gives the cell a move for it to check against, if it has a piece, its piece's moveset. Returns if move successful.
	public TurnResult suggestMove( Move suggestion )
	{
		TurnResult result = TurnResult.NO_MOVE_MADE;
		
		if ( getPiece() != null )
		{
			if( getPiece().getColor() == board.getGame().getTurnColor() )
			{
				MoveSet potentialMoves = getPiece().getMoveSetByColor();
				ArrayList<Move> applicableMoves = potentialMoves.matchingMoves( suggestion );
				if (applicableMoves.size() > 0)
				{
					Location adjustedLocation = location.addMove( suggestion );
					Cell adjustedCell = board.getCell( adjustedLocation );
					
					
					for( Move move : applicableMoves )
					{
						if( result == TurnResult.NO_MOVE_MADE )
						{
							result = considerMove( suggestion, move, adjustedCell );
						}
					}
				}
			}
		}
		return result;
	}
	
	//Figures out if a move coincides with the board environment as defined by the given reference move. Returns if a move was made
	private TurnResult considerMove( Move potentialMove, Move referenceMove, Cell toCell )
	{
		TurnResult result = TurnResult.NO_MOVE_MADE;
		boolean isPossible = isMovePossible( potentialMove, referenceMove );
		if ( isPossible )
		{
			result = resultOfMove( potentialMove, referenceMove );
			
			switch( result )
			{
			case NORMAL_MOVE:
				break;
			case SELF_CHECK:
				//System.out.println( "Your move has left yourself in check" );
				//System.out.println( "Your move will be undone." );
				isPossible = false;
				break;
			case OPPONENT_CHECK:
				//System.out.println( "You have left your opponent in check" );
				break;
			case OPPONENT_CHECKMATE:
				//System.out.println( "You have left your opponent in checkmate. You win!" );
				break;
			case NO_MOVE_MADE:
				//Impossible
				break;
			case STALEMATE:
				break;
			}
			
			if (isPossible)
			{	
				executeSpecialCases( potentialMove, referenceMove );
				placePieceAt( toCell );
				
				if (board.getSimulationLevel() == 0)
				{
					char append = ( potentialMove.getType() == MoveType.CAPTURE ? '*' : ' ' );
					System.out.println( String.format("%s %s%c", location.toString(), location.addMove( potentialMove ).toString(), append ) );
				}
				
				board.getGame().giveNextPlayerControl();
			}
		}
		
		return result;
	}

	public TurnResult resultOfMove( Move checkedMove )
	{
		Move referenceMove = getPiece().getMoveSetByColor().matchingMove( checkedMove );
		return resultOfMove( checkedMove, referenceMove );
	}
	
	public TurnResult resultOfMove( Move checkedMove, Move referenceMove )
	{
		TurnResult result = TurnResult.NORMAL_MOVE;
		Cell toCell = board.getCell( location.addMove( checkedMove ) );
		
		board.digSimulation();
		
		executeSpecialCases( checkedMove, referenceMove );
		placePieceAt( toCell );
		
		if ( board.getGame().isInCheck( board.getGame().getTurnColor().getDeclaredPlayer() ) )
		{
			result = TurnResult.SELF_CHECK;
		}
		else
		if ( board.getGame().isInCheck( board.getGame().getTurnColor().getOpposing().getDeclaredPlayer() ) )
		{
			if ( board.getGame().isInCheckMate( board.getGame().getTurnColor().getOpposing().getDeclaredPlayer() ) )
			{
				result = TurnResult.OPPONENT_CHECKMATE;
			}
			else
			{
				result = TurnResult.OPPONENT_CHECK;
			}
		}
		else
		if ( board.getGame().isInCheckMate( board.getGame().getTurnColor().getOpposing().getDeclaredPlayer() ) )
		{
			result = TurnResult.STALEMATE;
		}
			
		//Rollback move
		board.rollBackSimulation();
			
		return result;
	}
	
	//Returns whether a move is possible
	private boolean isMovePossible( Move potentialMove, Move referenceMove )
	{
		boolean result = false;
		
		Location adjustedLocation = location.addMove( potentialMove );
		Cell toCell = board.getCell( adjustedLocation );
		
		if( evaluateSpecialCases( potentialMove, referenceMove, toCell ) )
		{
			if ( referenceMove.getStyle() != MoveStyle.SLIDE || isSlideUnblocked( potentialMove, referenceMove ) )
			{
				if( potentialMove.getType() == MoveType.CAPTURE )
				{
					if (toCell.hasPiece())
					{
						if (toCell.getPiece().getColor() != getPiece().getColor())
						{
							result = true;
						}
					}
				}
				else if( potentialMove.getType() == MoveType.MOVE )
				{
					if (!toCell.hasPiece())
					{
						result = true;
					}
				}
				
				if( !result && referenceMove.hasCases() )
				{
					result = evaluateSpecialCases( potentialMove, referenceMove, toCell );
				}
			}
		}
		
		return result;
	}
	
	//Returns whether all of the special cases of a move are currently satisfied
	private boolean evaluateSpecialCases( Move attempt, Move evaluating, Cell toCell )
	{
		boolean result = true;
		
		if (evaluating.hasCase( MoveCase.ON_PIECE_FIRST_MOVE ) )
		{
			if (getPiece().getNumberOfMoves() > 0)
			{
				result = false;
			}
		}
		if (evaluating.hasCase( MoveCase.IN_PASSING ) )
		{
			boolean enPassantPossible = false;
			int neededRank = getPiece().getColor() == PieceColor.LIGHT ? ChessBoard.BOARD_SIZE - EN_PASSANT_RANK - 1 : EN_PASSANT_RANK;
			if (toCell.getLocation().getY() == neededRank )
			{
				Cell shouldHave = board.getCell( toCell.getLocation().adjust( 0, -getPiece().getColor().getVerticalDirection().getDeltaY() ) );
				if( shouldHave.hasPiece() )
				{
					Piece testingPiece = shouldHave.getPiece();
					if (testingPiece.getCharacterRepresentation() == Pawn.REPRESENTATION )
					{
						if( testingPiece.getLastTurn() == board.getGame().getMoveNumber() - 1 && testingPiece.getNumberOfMoves() == 1 )
						{
							enPassantPossible = true;
						}
					}
				}
			}
			result = enPassantPossible && result;
		}
		if (evaluating.hasCase( MoveCase.ONCE_PER_GAME ) )
		{
			//NOT IMPLEMENTED YET
			result = false;
		}
		if (evaluating.hasCase( MoveCase.MID_POINT_FREE ) )
		{
			if(attempt.getDeltaX() % 2 == 0 && attempt.getDeltaY() % 2 == 0 )
			{
				//Get the cell that is between the current location and the potential destination
				Cell testCell = board.getCell( location.addMove( new Move( attempt.getDeltaX() / 2, attempt.getDeltaY() / 2, null, null ) ) );
				if (testCell.getPiece() != null || toCell.hasPiece())
				{
					result = false;
				}
			}
			else
			{
				result = false;
			}
		}
		
		return result;
	}
	
	private void executeSpecialCases( Move attempt, Move reference )
	{
		Cell toCell = board.getCell( location.addMove( attempt ) );
		if (reference.hasCase( MoveCase.IN_PASSING ) )
		{
			int neededRank = getPiece().getColor() == PieceColor.LIGHT ? ChessBoard.BOARD_SIZE - EN_PASSANT_RANK - 1 : EN_PASSANT_RANK;
			
			if (toCell.getLocation().getY() == neededRank )
			{
				Cell shouldHave = board.getCell( toCell.getLocation().adjust( 0, -getPiece().getColor().getVerticalDirection().getDeltaY() ) );
				shouldHave.takePiece();
			}
		}
		if (reference.hasCase( MoveCase.ONCE_PER_GAME ) )
		{
			//NOT IMPLEMENTED YET
		}
	}
	
	//Returns whether a slide movement path (eg: queen, bishop, rook) is unblocked by other pieces
	private boolean isSlideUnblocked( Move destination, Move slideReference )
	{
		boolean result = true;
		
		Location simulate = location;
		
		int maxNumberOfTilesToCheck;
		
		
		if ( slideReference.getDeltaX() == 0 )
		{
			maxNumberOfTilesToCheck = Math.abs(destination.getDeltaY()/slideReference.getDeltaY());
		}
		else
		{
			maxNumberOfTilesToCheck = Math.abs(destination.getDeltaX()/slideReference.getDeltaX());
		}
		
		for( int i = 1; i < maxNumberOfTilesToCheck; i++ )
		{
			if (result)
			{
				simulate = simulate.addMove( slideReference );
				result = ( simulate.isInBoard() && !board.getCell( simulate ).hasPiece() );
			}
		}
		
		return result;
	}
	
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> result = new ArrayList<Move>();
		
		if (getPiece() != null)
		{
			Move[] moves = getPiece().getMoveSetByColor().getExpandedTypeMoves();
			
			for( Move move : moves )
			{
				Location relative = location;
				if (move.getStyle() == MoveStyle.STEP )
				{
					relative = relative.addMove( move );
					if(relative.isInBoard() &&  isMovePossible( move, move ) )
						result.add( move );
				}
				else
				{
					relative = relative.addMove( move );
					Move nextMove = Move.makeFromLocations( location, relative, move.getType() );
					do
					{
						if (relative.isInBoard() && isMovePossible(nextMove, move))
						{
							result.add( nextMove );
						}

						relative = relative.addMove( move );
						nextMove = Move.makeFromLocations( location, relative, move.getType() );
					}
					while(isSlideUnblocked(nextMove,move));
				}
			}
			
		}
		
		return result;
	}
	
	private Piece placePieceAt( Cell adjustedCell )
	{
		Piece heldPiece = null;
		
		if (board.getSimulationLevel() == 0)
		{
			getPiece().move( board.getGame().getMoveNumber() );
		}
		else
		{
			getPiece().move();
		}
		if ( adjustedCell.hasPiece() )
		{
			heldPiece = adjustedCell.takePiece();
		}
		adjustedCell.givePiece( getPiece() );
		
		setPiece(null);
		
		if (viewEquivalent != null)
			viewEquivalent.repaint();
		
		return heldPiece;
	}

	public void givePiece( Piece piece )
	{
        if( piece instanceof Pawn )
        {
            PieceColor color = piece.getColor();
            if( location.getY() == 0 || location.getY() == ChessBoard.BOARD_SIZE - 1 )
                piece = new Queen(color);
        }
        setPiece(piece);
		if (viewEquivalent != null)
			viewEquivalent.repaint();
	}
	
	public Piece takePiece()
	{
		Piece heldPiece = getPiece();
		setPiece(null);
		
		if (viewEquivalent != null)
			viewEquivalent.repaint();
		
		return heldPiece;
	}
	
	public void setViewEquivalent( CellPanel viewEquivalent )
	{
		this.viewEquivalent = viewEquivalent;
	}
	
	public boolean hasPiece()
	{
		return (getPiece() != null);
	}
	
	public boolean hasActualPiece()
	{
		return (getActualPiece() != null);
	}

	private void setPiece( Piece piece )
	{
		pieceLevels.set( board.getSimulationLevel(), piece );
	}
	
	public Piece getActualPiece()
	{
		return pieceLevels.get(0);
	}
	
	public Piece getPiece()
	{
		return pieceLevels.get( board.getSimulationLevel() );
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public String toString()
	{
		String pieceString = (getPiece() != null) ? getPiece().toString() : "  ";
		return pieceString + location;
	}


	public boolean hasChecklessMove()
	{
		boolean result = false;
		ArrayList<Move> moves = getPossibleMoves();
		Player controllingPlayer = getPiece().getColor().getDeclaredPlayer();
	
		
		for(Move move : moves)
		{
			if (!result)
			{
				Cell toCell = board.getCell( location.addMove( move ) );
				
				board.digSimulation();
				
				placePieceAt( toCell );
				
				result = !board.getGame().isInCheck( controllingPlayer );
				
				//Rollback
				board.rollBackSimulation();
			}
		}
		return result;
	}


	public void mirrorForSimulation()
	{
		if ( pieceLevels.size() <= board.getSimulationLevel() )
		{
			pieceLevels.add( pieceLevels.get( pieceLevels.size() - 1 ) );
			movesBeforeSimulation.add( ( hasPiece() ) ? getPiece().getNumberOfMoves() : null );
		}
		else
		{
			pieceLevels.set( board.getSimulationLevel(), pieceLevels.get( board.getSimulationLevel() - 1 ) );
			movesBeforeSimulation.set( board.getSimulationLevel(), ( hasPiece() ) ? getPiece().getNumberOfMoves() : null );
		}
	}

	public void returnFromSimulation()
	{
		if (hasPiece())
		{
			getPiece().setNumberOfMoves( movesBeforeSimulation.get( board.getSimulationLevel() + 1 ) );
		}
	}

	public ChessBoard getBoard()
	{
		return board;
	}
}
