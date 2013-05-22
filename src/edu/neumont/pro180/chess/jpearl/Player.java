/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

public class Player
{
	private PieceColor commandingColor;
	
	public Player( PieceColor commandingColor )
	{
		this.commandingColor = commandingColor;
	}
	
	public PieceColor getCommandingColor()
	{
		return commandingColor;
	}
}
