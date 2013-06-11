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
		super( commandingColor, game, false );
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

		bestAction.perform();

//		Collections.sort( allActions );
//		Action topAction = allActions.get( 0 );
	}

    public String toString(){
        return "RandomWithCapture";
    }

}
