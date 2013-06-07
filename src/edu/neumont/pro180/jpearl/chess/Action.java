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
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.MoveSet;
import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class Action implements Comparable<Action>
{
	private Cell originCell;
	private Move move;
	private Cell otherCell;
	private static final int MS_TO_CALCULATE = 10000;
	private static final double TOP_PERCENT_TO_EVALUATE = 1;
	
	public Action( Cell originCell, Move move, Cell otherCell )
	{
		this.originCell = originCell;
		this.move = move;
		this.otherCell = otherCell;
	}

	
	public void perform()
	{
		originCell.suggestMove( move );
	}
	
	public double getValue()
	{
        double result = recurseForValue( 4, originCell.getBoard().getGame().getTurnColor(), System.currentTimeMillis() );
		return result;
	}
	
	public double recurseForValue( int recurseLevel, PieceColor favor, long startTime )
	{
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		double result = ( originCell.getBoard().getGame().getTurnColor() == favor ) ? otherCellValue : -otherCellValue*1.05;// - originCell.getPiece().getUnitWorth()/2;
		
		if (recurseLevel > 0 && (startTime > ( System.currentTimeMillis() - MS_TO_CALCULATE ) ) )
		{
            originCell.getBoard().digSimulation();
            Piece pieceHold = originCell.getPiece();
            perform();
            ArrayList<Action> allActions = originCell.getBoard().getGame().getAllActions( originCell.getBoard().getGame().getTurnColor() );
            
            Collections.sort( allActions );
            int numberEvaluated = 0;
            int numberToEvaluate = (int) (allActions.size() * TOP_PERCENT_TO_EVALUATE);
            
            double runningTotal = 0;
            for( Action action : allActions)
            {
            	if ( numberEvaluated < numberToEvaluate && startTime > ( System.currentTimeMillis() - MS_TO_CALCULATE ))
            	{
	                runningTotal += action.recurseForValue(recurseLevel -1, favor, startTime)*.95;
            	}
            	numberEvaluated++;
            }
            result += runningTotal / allActions.size();
            
            pieceHold.undoMove();
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

}
