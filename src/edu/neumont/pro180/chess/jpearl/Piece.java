/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

public abstract class Piece
{
	private PieceColor color;
	private boolean hasMovedBefore;
	
 	public Piece( PieceColor color )
	{
 		hasMovedBefore = false;
		this.color = color;
	}
	
	public abstract MoveSet getMoveSet();
	
	public MoveSet getMoveSetByColor()
	{
		MoveSet moveSet = getMoveSet();
		MoveSet result;
		if (color.getVerticalDirection().getDeltaY() == -1)
		{
			Move[] reference = moveSet.getMoves();
			Move[] newMoves = new Move[reference.length];
			
			for( int i = 0; i < reference.length; i++ )
			{
				newMoves[i] = reference[i].getVerticallyFlipped();
			}
			
			result = new MoveSet( newMoves );
		}
		else
		{
			result = moveSet;
		}
		
		return result;	
	}
	
	public abstract char getCharacterRepresentation();
	
	public PieceColor getColor()
	{
		return color;
	}
	
	public VerticalDirection getDirection()
	{
		return color.getVerticalDirection();
	}
	
	public boolean hasMoved()
	{
		return hasMovedBefore;
	}
	
	public void move()
	{
		hasMovedBefore = true;
	}
	
	public String toString()
	{
		return String.format( "%c%c", color.getRepresentation(), getCharacterRepresentation() );
	}
}
