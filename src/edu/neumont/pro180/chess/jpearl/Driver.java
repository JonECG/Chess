/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.io.IOException;

public class Driver
{
	private static final String PATH = "res\\log.txt";
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		String path = (args.length == 1) ? args[0] : PATH;
		
		ChessBoard board = new ChessBoard();
		
		try
		{
			ChessParser parser = new ChessParser( path );
			parser.parseToBoard( board );
			System.out.println( board );
			parser.parseFromInput( board );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
		System.out.println( board );
	}

}
