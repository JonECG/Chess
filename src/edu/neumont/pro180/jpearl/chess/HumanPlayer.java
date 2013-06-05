/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class HumanPlayer extends Player
{

	public HumanPlayer( PieceColor commandingColor, ChessGame game )
	{
		super( commandingColor, game );
	}


	@Override
	public void takeTurn()
	{
//		try
//		{
//			synchronized( this )
//			{
//				Thread.currentThread().wait();
//			}
//		}
//		catch ( InterruptedException e )
//		{
//			System.out.println( "The main thread has been interrupted" );
//		}
	}

}
