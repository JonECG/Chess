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
		super( commandingColor, game, false );
		
		rand = new Random();
	}


	@Override
	public void takeTurn()
	{
		ArrayList<Action> allActions = getGame().getAllActions( getCommandingColor() );
		Action actionToTake = allActions.get( rand.nextInt( allActions.size() ) );


		actionToTake.perform();

//		Collections.sort( allActions );
//		Action topAction = allActions.get( 0 );
	}

}
