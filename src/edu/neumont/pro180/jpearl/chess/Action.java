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
        int result = recurseForValue( 2, originCell.getBoard().getGame().getTurnColor() );
        if(startColor !=  originCell.getBoard().getGame().getTurnColor())
            originCell.getBoard().getGame().giveNextPlayerControl();
		return result;
	}
	
	public int recurseForValue( int recurseLevel, PieceColor favor )
	{
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		int result = otherCellValue - originCell.getPiece().getUnitWorth()/2;
		
		if (recurseLevel > 0)
		{
            originCell.getBoard().digSimulation();
            originCell.getBoard().getGame().giveNextPlayerControl();
            ArrayList<Cell> cells = originCell.getBoard().getAllCellsWithPiece( originCell.getBoard().getGame().getTurnColor() );
            for (Cell cell : cells) {
                ArrayList<Action> allActions = originCell.getBoard().getGame().getAllActions( originCell.getBoard().getGame().getTurnColor() );
                for( Action action : allActions){
                    if ( originCell.getBoard().getGame().getTurnColor() == favor )
                        result += action.recurseForValue(recurseLevel -1, favor);
                    else
                        result -= action.recurseForValue(recurseLevel -1, favor);
                }
            }
            originCell.getBoard().rollBackSimulation();
        }
		
		return result;
	}

}
