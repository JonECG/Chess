/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.Cell.TurnResult;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;
import edu.neumont.pro180.jpearl.chess.environment.Location;
import edu.neumont.pro180.jpearl.chess.io.ChessParser;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;

public class ChessGame
{
	private Player[] playerRoster;
	private int playerTurnIndex;
	private ChessBoard board;
	
	public ChessGame( Player...players )
	{
		playerRoster = players;
		playerTurnIndex = 0;
		board = new ChessBoard( this );
	}

	public void playGame()
	{
		while( true )
		{
			System.out.println(getCurrentPlayerTurn().getCommandingColor());
			getCurrentPlayerTurn().takeTurn();
		}
	}
	
	//Run a bare parser against the board
	public void runParser( ChessParser parser )
	{
		parser.parseToBoard( board );
	}
	
	//Gets a pieces possible moves taking whether the cell has a piece and it is the player's current turn in account
	public ArrayList<Move> getPossibleMovesForPiece( Location location )
	{
		Cell cell = board.getCell( location );
		ArrayList<Move> result = null;
		
		if (cell.hasPiece() && cell.getPiece().getColor() == playerRoster[playerTurnIndex].getCommandingColor())
		{
			result = cell.getPossibleMoves();
		}
		return result;
	}
	
	//Returns whether a piece at a location is safe from capture
	private boolean isPieceSafe( Location pieceLocation )
	{
		boolean result = true;
		
		Cell cell = board.getCell( pieceLocation );
		if ( !cell.hasPiece() )
		{
			throw new IllegalArgumentException( "There is no piece at the given location" );
		}
		
		ArrayList<Cell> opposing = board.getAllCellsWithPiece( cell.getPiece().getColor().getOpposing() );
		
		for( Cell test : opposing )
		{
			if (result)
			{
				Move requiredMove = Move.makeFromLocations( test.getLocation(), pieceLocation, MoveType.CAPTURE );
				result = !test.getPossibleMoves().contains( requiredMove );
			}
		}
		
		return result;
	}
	
	public boolean isInCheck( Player player )
	{
		Cell cell = board.findPiece( player.getVitalPiece() );
		return !isPieceSafe( cell.getLocation() );
	}
	
	public boolean isInCheckMate( Player player )
	{
		boolean result = true;
		ArrayList<Cell> cells = board.getAllCellsWithPiece( player.getCommandingColor() );
		
		for( Cell cell : cells )
		{
			if (result)
			{
				result = !cell.hasChecklessMove();
			}
		}
		
		return result;
	}
	
	public String toString()
	{
		return getTurnColor() + "'s Turn to Move\n" + board.toString();
	}
	
	public ChessBoard getChessBoard()
	{
		return board;
	}
	
	public PieceColor getTurnColor()
	{
		return getCurrentPlayerTurn().getCommandingColor();
	}
	
	public Player getCurrentPlayerTurn()
	{
		return playerRoster[playerTurnIndex];
	}
	
	public void giveNextPlayerControl()
	{
		playerTurnIndex = ( playerTurnIndex + 1 ) % playerRoster.length;
	}


	public void addNewPlayer( Player player )
	{
		Player[] newPlayerRoster = new Player[playerRoster.length + 1];
		for( int i = 0; i < playerRoster.length; i++ )
		{
			newPlayerRoster[i] = playerRoster[i];
		}
		newPlayerRoster[ playerRoster.length ] = player;
		playerRoster = newPlayerRoster;
	}


	public ArrayList<Action> getAllActions( PieceColor commandingColor )
	{
		ArrayList<Action> result = new ArrayList<Action>();
		
		ArrayList<Cell> cellPieces = getChessBoard().getAllCellsWithPiece( commandingColor );
		
		board.digSimulation();
		
		for( Cell cell : cellPieces )
		{
			for( Move move : cell.getPossibleMoves() )
			{
				TurnResult moveResult = cell.resultOfMove( move );

				if (moveResult != TurnResult.SELF_CHECK && moveResult != TurnResult.STALEMATE)
				{
					result.add( new Action( cell, move, board.getCell( cell.getLocation().addMove( move ) ) ) );
				}
			}
		}
		
		board.rollBackSimulation();
		
		return result;
	}
}
