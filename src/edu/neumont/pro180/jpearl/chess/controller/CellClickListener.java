/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.view.BoardPanel;

public class CellClickListener extends MouseAdapter
{
	private BoardPanel board;
	private Cell cell;
	
	public CellClickListener( BoardPanel board, Cell cell )
	{
		this.board = board;
		this.cell = cell;
	}

	
	@Override
	public void mousePressed( MouseEvent e )
	{
		if ( cell.getBoard().getGame().getCurrentPlayerTurn().isGuiInteractive() )
		board.processClick( cell );
	}

}
