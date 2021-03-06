/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.neumont.pro180.jpearl.chess.ChessGame;
import edu.neumont.pro180.jpearl.chess.controller.CellClickListener;
import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.Cell.TurnResult;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;
import edu.neumont.pro180.jpearl.chess.environment.Location;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class BoardPanel extends JPanel
{
	private enum ChoicePhase{ CHOOSING_PIECE, CHOOSING_MOVEMENT };
	
	private static final long serialVersionUID = 4544274521677797026L;
	private Cell fromCell;
	private HashMap<Location,CellPanel> viewBoard;
	private ChoicePhase currentPhase;
	private ChessGame game;
	private ChessBoard board;
	private JFrame frame;
	private static final int BORDER_SIZE = 35;
	
	
	
	public BoardPanel( JFrame frame, ChessBoard board )
	{
		this.board = board;
		this.frame = frame;
		this.game = board.getGame();
		
		viewBoard = new HashMap<Location,CellPanel>();
		currentPhase = ChoicePhase.CHOOSING_PIECE;
		
		GridLayout boardLayout = new GridLayout(ChessBoard.BOARD_SIZE,ChessBoard.BOARD_SIZE);
		setLayout( boardLayout );
		
		setBackground( Color.BLACK );

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
				repaint();
			}
		}
		
		updateBorder();
		revalidate();
		repaint();
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
			
			break;
		}
		repaint();
		updateBorder();
	}

	public void updateBorder()
	{
		setBorder(BorderFactory.createMatteBorder( BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, board.getGame().getCurrentPlayerTurn().getCommandingColor().getPlayerTurnColor()));
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
