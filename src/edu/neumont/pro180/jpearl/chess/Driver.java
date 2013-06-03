/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.neumont.pro180.jpearl.chess.io.ChessFileParser;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.view.BoardPanel;

public class Driver
{
	private static final String PATH = "res\\log.txt";
	private static final int DEFAULT_SIZE = 640;
	
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
		frame.setPreferredSize( new Dimension(DEFAULT_SIZE,DEFAULT_SIZE) );
		frame.add( new BoardPanel(frame, game.getChessBoard()) );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
		
		
		game.runParser( new ChessFileParser( path ) );
		//game.runParser( new ChessFileParser( "res\\test.txt" ) );
		
		System.out.println( "\n" + game );
		
		game.passToUser();
		
		System.out.println( game );
		
		System.out.println( "Good Bye!" );
	}

}
