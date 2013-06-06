/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import java.awt.image.BufferedImage;

import edu.neumont.pro180.jpearl.chess.io.CachedImageLoader;


public abstract class Piece
{
	private PieceColor color;
	private BufferedImage image;
	private int numberOfMoves;
	private char characterRepresentation;
	private int unitWorth;
	
 	public Piece( PieceColor color, char characterRepresentation, int unitWorth )
	{
 		this.characterRepresentation = characterRepresentation;
 		this.unitWorth = unitWorth;
 		
 		numberOfMoves = 0;
		this.color = color;
		
		image = CachedImageLoader.loadImageFromPath( String.format( "res\\pieces\\%s.png", toString() ) );
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
	
	public char getCharacterRepresentation()
	{
		return characterRepresentation;
	}
	
	public int getUnitWorth()
	{
		return unitWorth;
	}
	
	public PieceColor getColor()
	{
		return color;
	}
	
	public VerticalDirection getDirection()
	{
		return color.getVerticalDirection();
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public int getNumberOfMoves()
	{
		return numberOfMoves;
	}
	
	public void move()
	{
		numberOfMoves += 1;
	}
	
	public void undoMove()
	{
		numberOfMoves -= 1;
	}
	
	public String toString()
	{
		return String.format( "%c%c", color.getRepresentation(), getCharacterRepresentation() );
	}
}
