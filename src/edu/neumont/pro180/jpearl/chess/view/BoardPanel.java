/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;
import edu.neumont.pro180.jpearl.chess.environment.Location;

public class BoardPanel extends JPanel
{
	private static final long serialVersionUID = 4544274521677797026L;

	public BoardPanel( ChessBoard board )
	{
		GridLayout boardLayout = new GridLayout(ChessBoard.BOARD_SIZE,ChessBoard.BOARD_SIZE);
		setLayout( boardLayout );
		
		//Test scaling
		//this.setPreferredSize( new Dimension(600,600) );
		
		for( int y = ChessBoard.BOARD_SIZE-1; y >= 0; y-- )
		{
			for( int x = 0; x < ChessBoard.BOARD_SIZE ; x++ )
			{
				Location reference = new Location( x, y );
				add( new CellPanel( board.getCell( reference ) ) );
			}
		}
	}
}
