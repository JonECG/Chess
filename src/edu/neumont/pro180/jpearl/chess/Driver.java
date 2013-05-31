/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import javax.swing.JFrame;

import edu.neumont.pro180.jpearl.chess.io.ChessFileParser;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.view.ChessPanel;

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
		
		JFrame frame = new JFrame( "Chess and things" );
		frame.add( new ChessPanel(game) );
		
		frame.pack();
		frame.setVisible( true );
		
		
		game.runParser( new ChessFileParser( path ) );
		
		System.out.println( "\n" + game );
		
		game.passToUser();
		
		System.out.println( game );
		
		System.out.println( "Good Bye!" );
	}

}
