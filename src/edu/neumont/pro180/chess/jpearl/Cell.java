/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.ArrayList;

import edu.neumont.pro180.chess.jpearl.Move.MoveCase;
import edu.neumont.pro180.chess.jpearl.Move.MoveStyle;
import edu.neumont.pro180.chess.jpearl.Move.MoveType;


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
				MoveSet potentialMoves = piece.getMoveSet();
				ArrayList<Move> applicableMoves = potentialMoves.matchMoves( suggestion, piece.getDirection() );
				//if (potentialMoves.containsMove( suggestion, piece.getDirection() ))
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
		boolean moveWasMade = false;
		
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
							placePieceAt( toCell );
							moveWasMade = true;
						}
						else
						{
							System.out.println( "<<ILLEGAL CAPTURE -- OTHER PIECE BELONGS TO CAPTURING PLAYER>>" );
						}
					}
					else
					{
						System.out.println( "<<ILLEGAL CAPTURE -- NO PIECE TO CAPTURE>>" );
					}
				}
				else if( potentialMove.getType() == MoveType.MOVE )
				{
					if (!toCell.hasPiece())
					{
						placePieceAt( toCell );
						moveWasMade = true;
					}
					else
					{
						System.out.println( "<<ILLEGAL MOVE -- NEW LOCATION IS OCCUPIED>>" );
					}
				}
			}
			else
			{
				System.out.println( "<<ILLEGAL MOVE -- SLIDE MOVEMENT IS BLOCKED>>" );
			}
		}
		else
		{
			System.out.println( "<<POTENTIAL ILLEGAL MOVE -- POSSIBLE MOVE'S SPECIAL CASE WAS NOT SATISFIED>>" );
		}
		
		if (moveWasMade)
		{
			board.getGame().giveNextPlayerControl();
		}
		
		return moveWasMade;
	}
	
	private boolean isMoveValid( Move potentialMove, Move referenceMove )
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
						else
						{
							System.out.println( "<<ILLEGAL CAPTURE -- OTHER PIECE BELONGS TO CAPTURING PLAYER>>" );
						}
					}
					else
					{
						System.out.println( "<<ILLEGAL CAPTURE -- NO PIECE TO CAPTURE>>" );
					}
				}
				else if( potentialMove.getType() == MoveType.MOVE )
				{
					if (!toCell.hasPiece())
					{
						result = true;
					}
					else
					{
						System.out.println( "<<ILLEGAL MOVE -- NEW LOCATION IS OCCUPIED>>" );
					}
				}
			}
			else
			{
				System.out.println( "<<ILLEGAL MOVE -- SLIDE MOVEMENT IS BLOCKED>>" );
			}
		}
		else
		{
			System.out.println( "<<POTENTIAL ILLEGAL MOVE -- POSSIBLE MOVE'S SPECIAL CASE WAS NOT SATISFIED>>" );
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
			maxNumberOfTilesToCheck = destination.getDeltaY()/slideReference.getDeltaY();
		}
		else
		{
			maxNumberOfTilesToCheck = destination.getDeltaX()/slideReference.getDeltaX();
		}
		
		for( int i = 1; i < maxNumberOfTilesToCheck; i++ )
		{
			if (result)
			{
				simulate = simulate.addMove( slideReference );
				result = ( !board.getCell( simulate ).hasPiece() );
			}
		}
		
		return result;
	}
	
	public ArrayList<Move> getPossibleMoves()
	{
		ArrayList<Move> result = new ArrayList<Move>();
		
		if (piece != null)
		{
			Move[] moves = piece.getMoveSet().getMoves();
			
			for( Move move : moves )
			{
				if (move.getStyle() == MoveStyle.STEP )
				{
					if( isMoveValid( move, move ) )
						result.add( move );
				}
				else
				{
					Location relative = location;
					boolean solvingNext = true;
					do
					{
						relative = relative.addMove( move );
						Move nextMove = Move.makeFromLocations( location, relative, move.getType() );
						
						if (relative.isInBoard() && isMoveValid(nextMove, move))
						{
							result.add( nextMove );
						}
						else
							solvingNext = false;
					}
					while(solvingNext);
				}
			}
			
		}
		
		return result;
	}
	
	private void placePieceAt( Cell adjustedCell )
	{
		piece.move();
		if ( adjustedCell.hasPiece() )
		{
			adjustedCell.takePiece();
		}
		adjustedCell.givePiece( piece );
		piece = null;
	}

	public void givePiece( Piece piece )
	{
		this.piece = piece;
	}
	
	public void takePiece()
	{
		piece = null;
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
}
