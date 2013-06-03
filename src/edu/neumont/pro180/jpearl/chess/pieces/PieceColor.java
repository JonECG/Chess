/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import java.awt.Color;

import edu.neumont.pro180.jpearl.chess.Player;

public enum PieceColor
{
	LIGHT('l', VerticalDirection.UP, Color.WHITE ), DARK('d', VerticalDirection.DOWN, Color.GRAY );
	
	private char representation;
	private VerticalDirection verticalDirection;
	private Player declaredPlayer;
	private Color playerTurnColor;
	
	PieceColor( char representation, VerticalDirection verticalDirection, Color playerTurnColor )
	{
		this.representation = representation;
		this.verticalDirection = verticalDirection;
		this.playerTurnColor = playerTurnColor;
	}
	
	//Returns the enum value that is represented by a character
	public static PieceColor parseCharacter( char representation )
	{
		PieceColor result = null;
		
		for( PieceColor test : PieceColor.values() )
		{
			if ( representation == test.getRepresentation() )
			{
				result = test;
			}
		}
		
		if (result == null)
		{
			throw new IllegalArgumentException( String.format( "The character '%c' does not match a color." , representation ) );
		}
		
		return result;
	}
	
	public char getRepresentation()
	{
		return representation;
	}
	
	public PieceColor getOpposing()
	{
		PieceColor result;
		if (this == PieceColor.DARK)
		{
			result = PieceColor.LIGHT;
		}
		else
		{
			result = PieceColor.DARK;
		}
		return result;
	}
	
	public static PieceColor getOpposing( PieceColor check )
	{
		PieceColor result;
		if (check == PieceColor.DARK)
		{
			result = PieceColor.LIGHT;
		}
		else
		{
			result = PieceColor.DARK;
		}
		return result;
	}
	
	public VerticalDirection getVerticalDirection()
	{
		return verticalDirection;
	}
	
	public Player getDeclaredPlayer()
	{
		return declaredPlayer;
	}
	
	public Color getPlayerTurnColor()
	{
		return playerTurnColor;
	}
	
	public void setDeclaredPlayer( Player player )
	{
		declaredPlayer = player;
	}
}
