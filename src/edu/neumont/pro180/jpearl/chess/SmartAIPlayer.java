/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class SmartAIPlayer extends Player
{
	private Random rand;
	
	public SmartAIPlayer( PieceColor commandingColor, ChessGame game )
	{
		super( commandingColor, game );
		
		rand = new Random();
	}


	@Override
	public void takeTurn()
	{
		ArrayList<Action> allActions = getGame().getAllActions( getCommandingColor() );
		
		double bestValue = Integer.MIN_VALUE;
		Action bestAction = allActions.get( 0 );
		for( Action action : allActions )
		{
			double holdValue = action.getValue();
			if ( holdValue > bestValue )
			{
				bestValue = holdValue;
				bestAction = action;
			}
		}
		bestAction.perform();
		//bestAction.printPieceMoves();

	}

}
