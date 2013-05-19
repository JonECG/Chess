/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.HashMap;


public class ChessBoard
{
	private HashMap<Location,Cell> board;
	private static final int BOARD_SIZE = 8;
	
	public ChessBoard()
	{
		board = new HashMap<Location,Cell>();
		for( int i = 0; i < BOARD_SIZE; i++ )
		{
			for( int j = 0; j < BOARD_SIZE; j++ )
			{
				Cell cell = new Cell( i, j, this );
				board.put( cell.getLocation(), cell );

			}
		}
	}
	
	public Cell getCell( Location location )
	{
		return board.get( location );
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String result = "";
		for( int y = BOARD_SIZE-1; y >= 0; y-- )
		{
			for( int x = 0; x < BOARD_SIZE ; x++ )
			{
				Location test = new Location( x, y );
				Cell cell = board.get( test );
				result += String.format( "[%s]", cell );
			}
			result += "\n";
		}
		return result;
	}
}
