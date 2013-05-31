/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.environment;

import java.util.ArrayList;
import java.util.HashMap;

import edu.neumont.pro180.jpearl.chess.ChessGame;
import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;


public class ChessBoard
{
	private HashMap<Location,Cell> board;
	public static final int BOARD_SIZE = 8;
	private ChessGame game;
	
	//Create the board and initiate the cells inside it
	public ChessBoard(ChessGame game)
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
		
		this.game = game;
	}
	
	//Query the board for a cell at a location
	public Cell getCell( Location location )
	{
		return board.get( location );
	}
	
	//
	public Cell findPiece( Piece piece )
	{
		Cell result = null;
		for( int y = BOARD_SIZE-1; y >= 0; y-- )
		{
			for( int x = 0; x < BOARD_SIZE ; x++ )
			{
				Location test = new Location( x, y );
				Cell cell = board.get( test );
				if ( cell.hasPiece() && piece == cell.getPiece() )
				{
					result = cell;
				}
			}
		}
		return result;
	}
	
	public ArrayList<Cell> getAllCellsWithPiece( PieceColor color )
	{
		ArrayList<Cell> result = new ArrayList<Cell>();
		for( int y = BOARD_SIZE-1; y >= 0; y-- )
		{
			for( int x = 0; x < BOARD_SIZE ; x++ )
			{
				Location test = new Location( x, y );
				Cell cell = board.get( test );
				if ( cell.hasPiece() && cell.getPiece().getColor() == color )
				{
					result.add( cell );
				}
			}
		}
		return result;
	}
	
	//Create a textual representation of the board
	public String toString()
	{
		String result = "";
		for( int y = BOARD_SIZE-1; y >= 0; y-- )
		{
			for( int x = 0; x < BOARD_SIZE ; x++ )
			{
				Location reference = new Location( x, y );
				Cell cell = board.get( reference );
				result += String.format( "[%s]", cell );
			}
			result += "\n";
		}
		return result;
	}
	
	protected void rollbackTurn()
	{
	}
	
	public ChessGame getGame()
	{
		return game;
	}
}
