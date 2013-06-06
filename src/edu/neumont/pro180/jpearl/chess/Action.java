/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.pieces.Move;

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
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		return otherCellValue - originCell.getPiece().getUnitWorth()/2;
	}
}
