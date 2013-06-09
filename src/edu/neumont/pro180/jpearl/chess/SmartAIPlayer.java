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
	private int numberOfRecursions;
	private static final int MINIMUM_SEEKER_RECURSIONS = 3;
	
	public SmartAIPlayer( PieceColor commandingColor, ChessGame game, int numberOfRecursions )
	{
		super( commandingColor, game );
		this.numberOfRecursions = numberOfRecursions;
	}
	
	public SmartAIPlayer( PieceColor commandingColor, ChessGame game )
	{
		this( commandingColor, game, 0 );
	}

	@Override
	public void takeTurn()
	{
		int turnRecursions = numberOfRecursions;
		
		if ( getGame().getChessBoard().getAllCellsWithPiece( getCommandingColor().getOpposing() ).size() == 1)
		{
			turnRecursions = Math.max( MINIMUM_SEEKER_RECURSIONS, turnRecursions );
		}
		
		ArrayList<Action> allActions = getGame().getAllActions( getCommandingColor() );
		
		double bestValue = Integer.MIN_VALUE;
		Action bestAction = allActions.get( 0 );
		for( Action action : allActions )
		{
			double holdValue = action.getValue( numberOfRecursions );
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
