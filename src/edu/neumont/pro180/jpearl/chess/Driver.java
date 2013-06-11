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
    private static final int TOURNAMENT_LENGTH = 5;
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{		
		String path = (args.length == 1) ? args[0] : PATH;
		
		
		JFrame frame = new JFrame( "Chess and things" );
		
		Dimension preferred = new Dimension( DEFAULT_SIZE,DEFAULT_SIZE );
		frame.setPreferredSize( preferred );

		ChessGame game = new ChessGame();
		
        //TODO: Prompt for player types
        Object[] options = { "Random AI", "Smart AI", "Human", "Tournament" };
        JOptionPane gameTypeOptionPane = new JOptionPane( "Choose a game type", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[0] );
        JDialog gameTypeDialog = gameTypeOptionPane.createDialog( null, "Chess and things" );
        gameTypeDialog.setVisible( true );
        Object selected = gameTypeOptionPane.getValue();
        System.out.println( selected );
        if(selected.equals( options[0]) ){
        	game.addNewPlayer( new RandomWithCaptureAIPlayer( PieceColor.LIGHT, game ) );
            game.addNewPlayer( new RandomWithCaptureAIPlayer( PieceColor.DARK, game ) );
        }
        if(selected.equals( options[1]) ){
            game.addNewPlayer( new HumanPlayer( PieceColor.LIGHT, game ) );
            game.addNewPlayer( new SmartAIPlayer( PieceColor.DARK, game, 2 ) ); //TODO: Change 2 to the desired recursion level
        }
        if(selected.equals( options[2]) ){
            game.addNewPlayer( new HumanPlayer( PieceColor.LIGHT, game ) );
            game.addNewPlayer( new HumanPlayer( PieceColor.DARK, game ) );
        }
        if(selected.equals( options[3] )){
            Tournament tournament = new Tournament( frame, path, DEFAULT_SIZE, TOURNAMENT_LENGTH );
            tournament.start();
        }
        if(!selected.equals( options[3] )){
        	BoardPanel boardView = new BoardPanel(frame, game.getChessBoard());
            frame.add( boardView );

            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.pack();

            game.runParser( new ChessFileParser( path ) );

            frame.repaint();

            frame.setVisible( true );
            frame.setLocationRelativeTo( null );

            game.designateBoardPanel( boardView );
            game.playGame();
        }
	}

}
