/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.neumont.pro180.jpearl.chess.ChessGame;
import edu.neumont.pro180.jpearl.chess.controller.CellClickListener;
import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.Cell.TurnResult;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;
import edu.neumont.pro180.jpearl.chess.environment.Location;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveCase;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class BoardPanel extends JPanel
{
	private enum ChoicePhase{ CHOOSING_PIECE, CHOOSING_MOVEMENT };
	
	private static final long serialVersionUID = 4544274521677797026L;
	private static final int BUFFER = 16;
	private Cell fromCell;
	private HashMap<Location,CellPanel> viewBoard;
	private ChoicePhase currentPhase;
	private ChessGame game;
	private JFrame frame;
	
	
	
	public BoardPanel( JFrame frame, ChessBoard board )
	{
		this.frame = frame;
		viewBoard = new HashMap<Location,CellPanel>();
		this.game = board.getGame();
		currentPhase = ChoicePhase.CHOOSING_PIECE;
		GridLayout boardLayout = new GridLayout(ChessBoard.BOARD_SIZE,ChessBoard.BOARD_SIZE);
		setLayout( boardLayout );
		this.setBackground( Color.BLACK );

		
		for( int y = ChessBoard.BOARD_SIZE-1; y >= 0; y-- )
		{
			for( int x = 0; x < ChessBoard.BOARD_SIZE ; x++ )
			{
				Location reference = new Location( x, y );
				Cell cell = board.getCell( reference );
				CellPanel panel = new CellPanel( cell );
				
				add( panel );
				panel.addMouseListener( new CellClickListener(this, cell) );
				viewBoard.put( reference, panel );
			}
		}
	}
	
	public void processClick( Cell cell )
	{

		switch (currentPhase)
		{
		case CHOOSING_PIECE:
			
			ArrayList<Move> moves = game.getPossibleMovesForPiece( cell.getLocation() );
			
			if ( moves != null && moves.size() > 0 )
			{
				highlightMoves( cell.getLocation(), moves );
				fromCell = cell;
				currentPhase = ChoicePhase.CHOOSING_MOVEMENT;
			}
			
			repaint();
			break;
			
		case CHOOSING_MOVEMENT:
			MoveType type;
			
			if ( cell.hasPiece() )
			{
				type = MoveType.CAPTURE;
			}
			else
			{
				type = MoveType.MOVE;
			}
			
			TurnResult result = fromCell.suggestMove( Move.makeFromLocations( fromCell.getLocation(), cell.getLocation(), type ) );
			currentPhase = ChoicePhase.CHOOSING_PIECE;
			unhighlightBoard();
			repaint();
			
			switch( result )
			{
			case NO_MOVE_MADE:
				break;
			case NORMAL_MOVE:
				break;
			case SELF_CHECK:
				JOptionPane.showMessageDialog(frame, "You cannot move here, it will put you in check.", "Check", JOptionPane.WARNING_MESSAGE);
				break;
			case OPPONENT_CHECK:
				JOptionPane.showMessageDialog(frame, "You've put your opponent in check!", "Check", JOptionPane.PLAIN_MESSAGE );
				break;
			case OPPONENT_CHECKMATE:
				JOptionPane.showMessageDialog(frame, "You've put your opponent in checkmate! You Win!", "Congratulations!", JOptionPane.PLAIN_MESSAGE);
				System.exit( 0 );
				break;
			}
			
			repaint();
			break;
		}
		System.out.println( cell.getLocation().toString() );
	}

	private void highlightMoves( Location location, ArrayList<Move> moves )
	{
		for( Move move : moves )
		{
			Location offsetLocation = location.addMove( move );
			CellPanel cell = viewBoard.get( offsetLocation );
			Color toHighlight = Color.YELLOW;
			if ( cell.getRepresentedCell().hasPiece() )
			{
				toHighlight = Color.PINK;
			}
			else
			if ( move.getCases().length != 0 )
			{
				toHighlight = Color.CYAN;
			}
			cell.highlight( toHighlight );
		}
	}
	
	private void unhighlightBoard()
	{
		for( int i = 0; i < ChessBoard.BOARD_SIZE; i++ )
		{
			for( int j = 0; j < ChessBoard.BOARD_SIZE; j++ )
			{
				CellPanel cell = viewBoard.get( new Location( i, j ) );
				cell.unhighlight();
			}
		}
	}
}
