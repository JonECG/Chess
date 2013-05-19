/**
 * @author JonathanPearl
 *
 */
package module2;

import java.util.ArrayList;

import module2.Move.MoveCase;
import module2.Move.MoveStyle;
import module2.Move.MoveType;

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
	
	public void suggestMove( Move suggestion )
	{
		if ( piece != null )
		{
			MoveSet potentialMoves = piece.getMoves();
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
			System.out.println( "<<ILLEGAL MOVE -- THERE IS NO PIECE TO MOVE>>" );
		}
	}
	
	private boolean considerMove( Move potentialMove, Move referenceMove, Cell toCell )
	{
		boolean moveWasMade = false;
		
		if( evaluateSpecialCases( potentialMove ) )
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
			System.out.println( "<<ILLEGAL MOVE -- MOVE'S SPECIAL CASE WAS NOT SATISFIED>>" );
		}
		
		return moveWasMade;
	}
	
	private boolean evaluateSpecialCases( Move evaluating )
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
		
		return result;
	}
	
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
