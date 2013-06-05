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
	
	public Action( Cell originCell, Move move )
	{
		this.originCell = originCell;
		this.move = move;
	}

	@Override
	public int compareTo( Action other )
	{
		return getValue() - other.getValue();
	}
	
	public void perform()
	{
		originCell.suggestMove( move );
	}
	
	public int getValue()
	{
		return 4;
	}
}
