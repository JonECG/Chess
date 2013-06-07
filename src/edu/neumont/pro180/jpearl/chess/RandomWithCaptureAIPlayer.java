/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class RandomWithCaptureAIPlayer extends Player
{
	public RandomWithCaptureAIPlayer( PieceColor commandingColor, ChessGame game )
	{
		super( commandingColor, game );
	}


	@Override
	public void takeTurn()
	{
		ArrayList<Action> allActions = getGame().getAllActions( getCommandingColor() );
		
		Collections.shuffle( allActions );
		
		double bestValue = Integer.MIN_VALUE;
		Action bestAction = null;
		
		for( Action action : allActions )
		{
			double holdValue = action.getImmediateValue();
			if ( holdValue > bestValue )
			{
				bestValue = holdValue;
				bestAction = action;
			}
		}

		try
		{
			Thread.sleep(200);
			bestAction.perform();
			Thread.sleep(200);
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
