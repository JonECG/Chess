/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;


public abstract class Player
{
	private PieceColor commandingColor;
	private Piece vitalPiece;
	private ChessGame game;
	private boolean isInteractive;
	
	public Player( PieceColor commandingColor, ChessGame game, boolean isInteractive )
	{
		this.game = game;
		this.commandingColor = commandingColor;
		this.isInteractive = isInteractive;
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
	
	public abstract void takeTurn();
	
	protected ChessGame getGame()
	{
		return game;
	}

	public boolean isGuiInteractive()
	{
		return isInteractive;
	}
}
