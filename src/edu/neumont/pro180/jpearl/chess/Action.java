/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.MoveSet;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class Action implements Comparable<Action>
{
	private Cell originCell;
	private Move move;
	private Cell otherCell;
	private static final int SECONDS_TO_CALCULATE = 1;
	
	public Action( Cell originCell, Move move, Cell otherCell )
	{
		this.originCell = originCell;
		this.move = move;
		this.otherCell = otherCell;
	}

	@Override
	public int compareTo( Action other )
	{
		return other.getValue() - getValue();
	}
	
	public void perform()
	{
		originCell.suggestMove( move );
	}
	
	public int getValue()
	{
		//Cell otherCell = board.getCell( originCell.getLocation().addMove( move ) );
		
        PieceColor startColor = originCell.getBoard().getGame().getTurnColor();
        int result = recurseForValue( 3, originCell.getBoard().getGame().getTurnColor(), System.currentTimeMillis() );
        if(startColor !=  originCell.getBoard().getGame().getTurnColor())
            originCell.getBoard().getGame().giveNextPlayerControl();
		return result;
	}
	
	public int recurseForValue( int recurseLevel, PieceColor favor, long startTime )
	{
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		int result = otherCellValue;// - originCell.getPiece().getUnitWorth()/2;
		
		if (recurseLevel > 0 && startTime > ( System.currentTimeMillis() - SECONDS_TO_CALCULATE*10000 ) )
		{
            originCell.getBoard().digSimulation();
            originCell.getBoard().getGame().giveNextPlayerControl();
            ArrayList<Action> allActions = originCell.getBoard().getGame().getAllActions( originCell.getBoard().getGame().getTurnColor() );
            for( Action action : allActions)
            {
            	if (startTime > ( System.currentTimeMillis() - SECONDS_TO_CALCULATE*1000 ))
            	{
	                if ( originCell.getBoard().getGame().getTurnColor() == favor )
	                    result += action.recurseForValue(recurseLevel -1, favor, startTime);
	                else
	                    result -= action.recurseForValue(recurseLevel -1, favor, startTime);
            	}
            }
            originCell.getBoard().rollBackSimulation();
        }
		
		return result;
	}

}
