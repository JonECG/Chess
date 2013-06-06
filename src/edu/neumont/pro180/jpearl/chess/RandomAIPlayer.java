/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class RandomAIPlayer extends Player
{
	private Random rand;
	
	public RandomAIPlayer( PieceColor commandingColor, ChessGame game )
	{
		super( commandingColor, game );
		
		rand = new Random();
	}


	@Override
	public void takeTurn()
	{
		ArrayList<Action> allActions = getGame().getAllActions( getCommandingColor() );
		Action actionToTake = allActions.get( rand.nextInt( allActions.size() ) );
		
		actionToTake.perform();
		try
		{
			Thread.sleep(100);
		}
		catch ( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Collections.sort( allActions );
//		Action topAction = allActions.get( 0 );
	}

}
