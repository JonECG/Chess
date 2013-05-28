/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

public class Driver
{
	private static final String PATH = "res\\log.txt";
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		String path = (args.length == 1) ? args[0] : PATH;
		
		ChessGame game = new ChessGame(
				new Player( PieceColor.LIGHT ),
				new Player( PieceColor.DARK)
				);
		
		game.runParser( new ChessFileParser( path ) );
		
		System.out.println( "\n" + game );
		
		game.passToUser();
		
		System.out.println( game );
		
		System.out.println( "Good Bye!" );
	}

}
