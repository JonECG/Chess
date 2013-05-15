/**
 * @author JonathanPearl
 *
 */
package module1;

import java.io.IOException;

public class Driver
{
	private static final String PATH = "res\\moves.txt";

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try
		{
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
