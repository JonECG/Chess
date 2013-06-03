/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Scanner;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;
import edu.neumont.pro180.jpearl.chess.environment.Location;
import edu.neumont.pro180.jpearl.chess.io.ChessParser;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

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
		
		if (!cell.hasPiece())
		{
			System.out.println( location + " has no piece on it" );
		}
		else
		if (cell.getPiece().getColor() != playerRoster[playerTurnIndex].getCommandingColor())
		{
			System.out.println( "You can't select your opponent's piece" );
		}
		else
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
}
