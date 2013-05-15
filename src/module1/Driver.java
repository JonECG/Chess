/**
 * @author JonathanPearl
 *
 */
package module1;

import java.io.IOException;

public class Driver
{
	private static final String PATH = "res\\moves.txt";

	public static void main( String[] args )
	{
		try
		{
			//Set up a parser -- Reading the path from the command line if it has been provided, otherwise defaulting to the constant path
			ChessParser parser = new ChessParser( (args.length == 1) ? args[0] : PATH );
			while ( parser.isReady() )
			{
				System.out.println( parser.parseNextLine() );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
}
