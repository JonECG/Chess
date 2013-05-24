/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.ArrayList;
import java.util.Scanner;

import edu.neumont.pro180.chess.jpearl.Move.MoveType;

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

	public void runParser( ChessParser parser )
	{
		parser.parseToBoard( board );
	}
	
	public void passToUser()
	{
		Scanner scan = new Scanner( System.in );
		
		while( true )
		{
			Location from = getLocationFromInput( "Type location containing piece:", scan );
			ArrayList<Move> moves = getPossibleMovesForPiece( from );
			
			if ( moves != null && moves.size() > 0 )
			{
				String output = from + " can move to: ";
				for( Move move : moves )
				{
					output += from.addMove( move ) + (move.getType()==MoveType.MOVE? "" : "*" ) + ", " ;
				}
				System.out.println( output + "\n");
				
				Location to = getLocationFromInput( "Type location to move this piece to", scan );
				MoveType type;
				
				if ( board.getCell( to ).hasPiece() )
				{
					type = MoveType.CAPTURE;
				}
				else
				{
					type = MoveType.MOVE;
				}
				
				board.getCell( from ).suggestMove( Move.makeFromLocations( from, to, type ) );
			}
			else
			{
				System.out.println( from + " cannot be moved from" );
			}
			
			System.out.println( "\n" + this );
		}
	}

	private Location getLocationFromInput( String prompt, Scanner scan )
	{
		boolean needsLegalInput = true;
		Location location = null;
		
		while( needsLegalInput )
		{
			System.out.println( prompt );
			try
			{
				location = Location.parseFromCoordinates( scan.nextLine() );
				needsLegalInput = false;
			}
			catch( IllegalArgumentException e )
			{
				System.out.println( e.getMessage() );
			}
		}
		
		return location;
	}
	
	private ArrayList<Move> getPossibleMovesForPiece( Location location )
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
	
	public String toString()
	{
		return getTurnColor() + "'s Turn to Move\n" + board.toString();
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
