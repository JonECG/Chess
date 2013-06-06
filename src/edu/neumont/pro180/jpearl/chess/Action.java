/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
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
		return recurseForValue( 0, originCell.getBoard().getGame().getTurnColor() );
	}
	
	public int recurseForValue( int recurseLevel, PieceColor favor )
	{
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		int result = otherCellValue - originCell.getPiece().getUnitWorth()/2;
		
		if (recurseLevel > 0)
		{
			ArrayList<Cell> cells = originCell.getBoard().getAllCellsWithPiece( originCell.getBoard().getGame().getTurnColor() );
		}
		
		return result;
	}
}
