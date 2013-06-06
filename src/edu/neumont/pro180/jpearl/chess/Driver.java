/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.awt.Dimension;

import javax.swing.*;

import edu.neumont.pro180.jpearl.chess.io.ChessFileParser;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.view.BoardPanel;

public class Driver
{
	private static final String PATH = "res\\log.txt";
	private static final int DEFAULT_SIZE = 720;
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{		
		String path = (args.length == 1) ? args[0] : PATH;
		
		
		JFrame frame = new JFrame( "Chess and things" );
		
		Dimension preferred = new Dimension( DEFAULT_SIZE,DEFAULT_SIZE );
		frame.setPreferredSize( preferred );

		ChessGame game = new ChessGame( frame );
		
        //TODO: Prompt for player types
        Object[] options = { "Random AI", "Smart AI", "Human" };
        JOptionPane gameTypeOptionPane = new JOptionPane( "Choose a game type", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0] );
        JDialog gameTypeDialog = gameTypeOptionPane.createDialog( null, "Chess and things" );
        gameTypeDialog.setVisible( true );
        Object selected = gameTypeOptionPane.getValue();
        System.out.println( selected );
        if(selected.equals( options[0]) ){
        	game.addNewPlayer( new RandomAIPlayer( PieceColor.LIGHT, game ) );
            game.addNewPlayer( new RandomAIPlayer( PieceColor.DARK, game ) );
        }
        if(selected.equals( options[1]) ){
        	game.addNewPlayer( new SmartAIPlayer( PieceColor.LIGHT, game ) );
            game.addNewPlayer( new RandomAIPlayer( PieceColor.DARK, game ) );
        }
        if(selected.equals( options[2]) ){
            game.addNewPlayer( new HumanPlayer( PieceColor.LIGHT, game ) );
            game.addNewPlayer( new HumanPlayer( PieceColor.DARK, game ) );
        }
		
		
		frame.add( new BoardPanel(frame, game.getChessBoard()) );
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		
		game.runParser( new ChessFileParser( path ) );
		
		
		frame.repaint();
		
		frame.setVisible( true );
		frame.setLocationRelativeTo( null );
		
		game.playGame();
	}

}
