/**
 * @author JonathanPearl
 *
 */
package module2;

import module2.Move.MoveCase;
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
			if (potentialMoves.containsMove( suggestion, piece.getDirection() ))
			{
				Location adjustedLocation = location.addMove( suggestion );
				Cell adjustedCell = board.getCell( adjustedLocation );
				if( suggestion.getType() == MoveType.CAPTURE )
				{
					if (adjustedCell.hasPiece())
					{
						if (adjustedCell.getPiece().getColor() != piece.getColor())
						{
							placePieceAt( adjustedCell );
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
				else if( suggestion.getType() == MoveType.MOVE )
				{
					if (!adjustedCell.hasPiece())
					{
						placePieceAt( adjustedCell );
					}
					else
					{
						System.out.println( "<<ILLEGAL MOVE -- NEW LOCATION IS OCCUPIED>>" );
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
	
	private void placePieceAt( Cell adjustedCell )
	{
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
