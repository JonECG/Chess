/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.Cell.TurnResult;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.MoveSet;
import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class Action implements Comparable<Action>
{
	private Cell originCell;
	private Move move;
	private Cell otherCell;
	private TurnResult moveResult;
	private static final int MS_TO_CALCULATE = 100000;
	private static final double TOP_PERCENT_TO_EVALUATE = 1;
	
	public Action( Cell originCell, Move move, Cell otherCell, TurnResult moveResult )
	{
		this.originCell = originCell;
		this.move = move;
		this.otherCell = otherCell;
		this.moveResult = moveResult;
	}

	
	public void perform()
	{
		originCell.suggestMove( move );
	}
	
	public double getValue()
	{
        return getValue(3);
	}
	
	public double getValue( int numberOfRecursions )
	{
        double result = recurseForValue( numberOfRecursions, originCell.getBoard().getGame().getTurnColor(), System.currentTimeMillis() );
		return result;
	}
	
	public double recurseForValue( int recurseLevel, PieceColor favor, long startTime )
	{
		boolean isInFavor = originCell.getBoard().getGame().getTurnColor() == favor;
		
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		double result = ( isInFavor ) ? otherCellValue : -otherCellValue*1.02;
		
		double subTotal = move.getLength()*.0001 - originCell.getPiece().getNumberOfMoves()*.001;
		result += ( isInFavor ) ? subTotal : -subTotal;
		
		if( moveResult == TurnResult.OPPONENT_CHECKMATE )
		{
			result += ( isInFavor ) ? 1000000 : -1000000;
		}
		else
		if (recurseLevel > 0 && (startTime > ( System.currentTimeMillis() - MS_TO_CALCULATE ) ) )
		{
            originCell.getBoard().digSimulation();
            perform();
            ArrayList<Action> allActions = originCell.getBoard().getGame().getAllActions( originCell.getBoard().getGame().getTurnColor() );
            
            int numberToEvaluate = (int) (allActions.size() * TOP_PERCENT_TO_EVALUATE);
            
            double bestMove = ( !isInFavor ) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            
            for( int i = 0; i < numberToEvaluate; i++ )
            {
            	Action testingAction = allActions.get( i );
            	double actionValue = testingAction.recurseForValue(recurseLevel -1, favor, startTime);
            	if ( !isInFavor )
            	{
            		if ( actionValue > bestMove )
            		{
            			bestMove = actionValue;
            		}
            	}
            	else
            	{
            		if ( actionValue < bestMove )
            		{
            			bestMove = actionValue;
            		}
            	}
            }
            
            result += bestMove*.999;
            
            originCell.getBoard().rollBackSimulation();
        }
		
		return result;
	}



	public int getImmediateValue()
	{
		return otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
	}


	@Override
	public int compareTo( Action other )
	{
		return other.getImmediateValue() - getImmediateValue();
	}


	/**
	 * 
	 */
	public void printPieceMoves()
	{
		System.out.println( otherCell.getPiece().getNumberOfMoves() );
	}

}
