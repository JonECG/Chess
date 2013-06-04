/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;


public class Player
{
	private PieceColor commandingColor;
	private Piece vitalPiece;
	
	public Player( PieceColor commandingColor )
	{
		this.commandingColor = commandingColor;
		commandingColor.setDeclaredPlayer( this );
	}
	
	public PieceColor getCommandingColor()
	{
		return commandingColor;
	}
	
	public Piece getVitalPiece()
	{
		return vitalPiece;
	}
	
	public void setVitalPiece( Piece piece )
	{
		vitalPiece = piece;
	}
}