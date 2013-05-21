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
	
	public abstract MoveSet getMoves();
	
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
