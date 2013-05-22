/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

public class ChessGame
{
	private Player[] playerRoster;
	private int playerTurnIndex;
	private ChessBoard board;
	
	public ChessGame( Player...players )
	{
		playerRoster = players;
		playerTurnIndex = 0;
		board = new ChessBoard( this );
	}

	public void runParser( ChessParser parser )
	{
		parser.parseToBoard( board );
	}
	
	public String toString()
	{
		return getTurnColor() + "'s Turn to Move\n" + board.toString();
	}
	
	public PieceColor getTurnColor()
	{
		return getCurrentPlayerTurn().getCommandingColor();
	}
	
	public Player getCurrentPlayerTurn()
	{
		return playerRoster[playerTurnIndex];
	}
	
	public void giveNextPlayerControl()
	{
		playerTurnIndex = ( playerTurnIndex + 1 ) % playerRoster.length;
	}
}
