/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import edu.neumont.pro180.jpearl.chess.ChessGame;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;

public class ChessPanel extends JPanel
{
	private static final long serialVersionUID = -8633816962660382985L;
	private final static int DEFAULT_SIZE = 640;
	//private final static int BUFFER = 16;
	//private ChessGame game;
	
	public ChessPanel(ChessGame game)
	{
		//this.game = game;
		this.setPreferredSize( new Dimension(DEFAULT_SIZE,DEFAULT_SIZE) );
		add( new BoardPanel( game.getChessBoard() ) );
	}
}
