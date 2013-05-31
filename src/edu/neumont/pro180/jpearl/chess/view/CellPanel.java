/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;

public class CellPanel extends JPanel
{
	private static final long serialVersionUID = 3142869218407839386L;
	private Cell cell;
	
	public CellPanel( Cell cell )
	{
		this.cell = cell;
	}

	@Override
	public void paint(Graphics g)
	{
		g.drawRect( 0 , 0 , getWidth()-1, getHeight()-1 );
		if (cell.hasPiece() )
		{
			g.drawString( cell.getPiece().toString(), getWidth()/2, getHeight()/2 );
		}
	}
}
