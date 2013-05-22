/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ChessFileParser extends ChessParser
{
	private BufferedReader reader;

	public ChessFileParser( String path )
	{
		try
		{
			reader = new BufferedReader( new FileReader( new File( path ) ) );
		}
		catch ( FileNotFoundException e )
		{
			System.out.println( "File could not be found: " + e.getMessage() );
		}
	}
	
	@Override
	public boolean isReady()
	{
		boolean result = false;
		try
		{
			result = reader.ready();
		}
		catch ( IOException e )
		{
			System.out.println( "There was an interruption when reading the file: " + e.getMessage() );
		}
		return result;
	}


	@Override
	public String nextLine()
	{
		String result = null;
		try
		{
			result = reader.readLine();
		}
		catch ( IOException e )
		{
			System.out.println( "There was an interruption when reading the file: " + e.getMessage() );
		}
		return result;
	}
}
