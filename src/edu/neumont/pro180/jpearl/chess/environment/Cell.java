/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.environment;

import java.util.ArrayList;

import edu.neumont.pro180.jpearl.chess.Player;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.MoveSet;
import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveCase;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;


public class Cell
{
	private Piece piece;
	private Location location;
	private ChessBoard board;
	
	public Cell( int x, int y, ChessBoard board )
	{
		location = new Location( x, y );
		piece = null;
		this.board = board;
	}
	
	//Gives the cell a move for it to check against, if it has a piece, its piece's moveset.
	public void suggestMove( Move suggestion )
	{
		if ( piece != null )
		{
			if( piece.getColor() == board.getGame().getTurnColor() )
			{
				MoveSet potentialMoves = piece.getMoveSetByColor();
				ArrayList<Move> applicableMoves = potentialMoves.matchMoves( suggestion );
				if (applicableMoves.size() > 0)
				{
					Location adjustedLocation = location.addMove( suggestion );
					Cell adjustedCell = board.getCell( adjustedLocation );
					
					boolean moveFound = false;
					for( Move move : applicableMoves )
					{
						if( !moveFound )
						{
							moveFound = considerMove( suggestion, move, adjustedCell );
						}
					}
					
					
				}
				else
				{
					System.out.println( "<<ILLEGAL MOVE -- NOT IN MOVESET FOR PIECE>>" );
				}
			}
			else
			{
				System.out.println( "<<ILLEGAL MOVE -- NOT " + piece.getColor() + "'S TURN>>" );
			}
		}
		else
		{
			System.out.println( "<<ILLEGAL MOVE -- THERE IS NO PIECE TO MOVE>>" );
		}
	}
	
	//Figures out if a move coincides with the board environment as defined by the given reference move. Returns if a move was made
	private boolean considerMove( Move potentialMove, Move referenceMove, Cell toCell )
	{
		boolean moveWasMade = isMovePossible( potentialMove, referenceMove );
		
		if (moveWasMade)
		{
			Piece heldPiece = placePieceAt( toCell );
			
			if ( board.getGame().isInCheck( board.getGame().getTurnColor().getDeclaredPlayer() ) )
			{
				System.out.println( "Your move has left yourself in check" );
				System.out.println( "Your move will be undone." );
				moveWasMade = false;
			}
			else
			if ( board.getGame().isInCheck( board.getGame().getTurnColor().getOpposing().getDeclaredPlayer() ) )
			{
				if ( board.getGame().isInCheckMate( board.getGame().getTurnColor().getOpposing().getDeclaredPlayer() ) )
				{
					System.out.println( "You have left your opponent in checkmate. You win!" );
				}
				else
				{
					System.out.println( "You have left your opponent in check" );
				}
			}
			
			if (moveWasMade)
			{
				board.getGame().giveNextPlayerControl();
			}
			else
			{
				//Rollback move
				givePiece( toCell.takePiece() );
				toCell.givePiece( heldPiece );
			}
			
			
		}
		else
		System.out.println( "INVALID?" );
		
		return moveWasMade;
	}
	
	//Returns whether a move is possible
	public boolean isMoveValid( Move potentialMove )
	{
		boolean result = false;
		
		MoveSet potentialMoves = piece.getMoveSetByColor();
		ArrayList<Move> applicableMoves = potentialMoves.matchMoves( potentialMove );

		for( Move move : applicableMoves )
		{
			if( !result )
			{
				result = isMovePossible( potentialMove, move );
			}
		}
		
		return result;
	}
		
	//Returns whether a move is possible
	private boolean isMovePossible( Move potentialMove, Move referenceMove )
	{
		boolean result = false;
		
		Location adjustedLocation = location.addMove( potentialMove );
		Cell toCell = board.getCell( adjustedLocation );
		
		if( evaluateSpecialCases( potentialMove, referenceMove ) )
		{
			if ( referenceMove.getStyle() != MoveStyle.SLIDE || isSlideUnblocked( potentialMove, referenceMove ) )
			{
				if( potentialMove.getType() == MoveType.CAPTURE )
				{
					if (toCell.hasPiece())
					{
						if (toCell.getPiece().getColor() != piece.getColor())
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
			}
		}
		
		return result;
	}
	
	//Returns whether all of the special cases of a move are currently satisfied
	private boolean evaluateSpecialCases( Move attempt, Move evaluating )
	{
		boolean result = true;
		
		if (evaluating.hasCase( MoveCase.ON_PIECE_FIRST_MOVE ) )
		{
			if (piece.hasMoved())
			{
				result = false;
			}
		}
		if (evaluating.hasCase( MoveCase.IN_PASSING ) )
		{
			//NOT IMPLEMENTED YET
			result = false;
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
				if (testCell.getPiece() != null)
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
		
		if (piece != null)
		{
			Move[] moves = piece.getMoveSetByColor().getExpandedTypeMoves();
			
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
		
		piece.move();
		if ( adjustedCell.hasPiece() )
		{
			heldPiece = adjustedCell.takePiece();
		}
		adjustedCell.givePiece( piece );
		piece = null;
		
		return heldPiece;
	}

	public void givePiece( Piece piece )
	{
		this.piece = piece;
	}
	
	public Piece takePiece()
	{
		Piece heldPiece = piece;
		piece = null;
		return heldPiece;
	}
	
	public boolean hasPiece()
	{
		return (piece != null);
	}
	
	public Piece getPiece()
	{
		return piece;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public String toString()
	{
		String pieceString = (piece != null) ? piece.toString() : "  ";
		return pieceString + location;
	}


	public boolean hasChecklessMove()
	{
		boolean result = false;
		ArrayList<Move> moves = getPossibleMoves();
		Player controllingPlayer = piece.getColor().getDeclaredPlayer();
		
		for(Move move : moves)
		{
			if (!result)
			{
				Cell toCell = board.getCell( location.addMove( move ) );
				Piece heldPiece = placePieceAt( toCell );
				
				result = !board.getGame().isInCheck( controllingPlayer );
				
				//Rollback
				givePiece( toCell.takePiece() );
				toCell.givePiece( heldPiece );
			}
		}
		return result;
	}
}
