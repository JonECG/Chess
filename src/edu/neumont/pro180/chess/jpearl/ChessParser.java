/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.neumont.pro180.chess.jpearl.Move.MoveType;


public class ChessParser
{
	//Represents the regex operations
	private static final String REGEX_PLACE_PIECE = "([kqbrnp][ld])([a-h][1-8])";
	private static final String REGEX_MOVE_WITH_CAPTURE = "([a-h][1-8]) ([a-h][1-8])\\*";
	private static final String REGEX_MOVE_WITHOUT_CAPTURE = "([a-h][1-8]) ([a-h][1-8])";
	private static final String REGEX_MOVE_TWO_PIECES = "([a-h][1-8]) ([a-h][1-8]) ([a-h][1-8]) ([a-h][1-8])";
	
	//Represents the regex capture groups holding pertinent information
	private static final int NEW_PIECE_DESCRIPTION_GROUP = 1;
	private static final int NEW_PIECE_POSITION_GROUP = 2;
	private static final int FIRST_MOVE_FROM_GROUP = 1;
	private static final int FIRST_MOVE_TO_GROUP = 2;
	private static final int SECOND_MOVE_FROM_GROUP = 3;
	private static final int SECOND_MOVE_TO_GROUP = 4;
	
	private BufferedReader reader;
	
	//Create the reader
	public ChessParser( String path ) throws FileNotFoundException
	{
		reader = new BufferedReader( new FileReader( new File( path ) ) );
	}
	
	public void parseToBoard( ChessBoard board ) throws IOException
	{
		while ( isReady() )
		{
			parseNextLine( board );
		}
	}
	
	//Intefaces to the underlying ready to check if it is ready to be read from
	public boolean isReady() throws IOException
	{
		return reader.ready();
	}
	
	//Parses next command from a file
	public void parseNextLine( ChessBoard board ) throws IOException
	{
		parse( reader.readLine(), board );
	}
	
	//Parses next command from input
	public void parseFromInput( ChessBoard board )
	{
		Scanner scan = new Scanner( System.in );
		boolean gettingUserInput = true;
		while( gettingUserInput )
		{
			System.out.println( "Enter command or EXIT: " );
			String nextLine = scan.nextLine();
			if ( nextLine.toUpperCase().equals( "EXIT" ) )
			{
				gettingUserInput = false;
			}
			else
			{
				parse( nextLine, board );
			}
		}
		scan.close();
	}
	
	//Runs a command through regex to find out how to use it and tell the cell it affects what it needs to do
	private void parse( String command, ChessBoard board )
	{
		String currentLine = command.trim().toLowerCase();
		
		if ( currentLine.matches( REGEX_PLACE_PIECE ) )
		{
			Pattern pattern = Pattern.compile( REGEX_PLACE_PIECE );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			PieceColor color = PieceColor.parseCharacter( match.group(NEW_PIECE_DESCRIPTION_GROUP).charAt( 1 ) );
			PieceRepresentation pieceRep = PieceRepresentation.parseCharacter( match.group(NEW_PIECE_DESCRIPTION_GROUP).charAt( 0 ) );
			Location location = Location.parseFromCoordinates( match.group(NEW_PIECE_POSITION_GROUP) );
			Piece piece = pieceRep.createPiece( color );
			System.out.println( String.format( "%s represents a placement of a new %s %s at %s.", currentLine, color, pieceRep, location ) );
			board.getCell( location ).givePiece( piece );
		}
		else
		if ( currentLine.matches( REGEX_MOVE_WITH_CAPTURE ) )
		{
			Pattern pattern = Pattern.compile( REGEX_MOVE_WITH_CAPTURE );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			Location locationFrom = Location.parseFromCoordinates( match.group(FIRST_MOVE_FROM_GROUP) );
			Location locationTo = Location.parseFromCoordinates( match.group(FIRST_MOVE_TO_GROUP) );
			Move inferredMove = Move.makeFromLocations( locationFrom, locationTo, MoveType.CAPTURE );
			Cell movingPieceCell = board.getCell( locationFrom );
			System.out.println( String.format( "%s represents a movement of a piece at %s to %s with a capture.", currentLine,
					match.group(FIRST_MOVE_FROM_GROUP), match.group(FIRST_MOVE_TO_GROUP) ) );
			movingPieceCell.suggestMove( inferredMove );
			System.out.println( board );
		}
		else
		if ( currentLine.matches( REGEX_MOVE_WITHOUT_CAPTURE ) )
		{
			Pattern pattern = Pattern.compile( REGEX_MOVE_WITHOUT_CAPTURE );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			Location locationFrom = Location.parseFromCoordinates( match.group(FIRST_MOVE_FROM_GROUP) );
			Location locationTo = Location.parseFromCoordinates( match.group(FIRST_MOVE_TO_GROUP) );
			Move inferredMove = Move.makeFromLocations( locationFrom, locationTo, MoveType.MOVE );
			Cell movingPieceCell = board.getCell( locationFrom );
			System.out.println( String.format( "%s represents a movement of a piece at %s to %s.", currentLine,
					match.group(FIRST_MOVE_FROM_GROUP), match.group(FIRST_MOVE_TO_GROUP) ) );
			System.out.println();
			movingPieceCell.suggestMove( inferredMove );
			System.out.println();
			System.out.println( board );
		}
//		else
//		if ( currentLine.matches( REGEX_MOVE_TWO_PIECES ) )
//		{
//			Pattern pattern = Pattern.compile( REGEX_MOVE_TWO_PIECES );
//			Matcher match = pattern.matcher( currentLine );
//			match.find();
//			result = String.format( "%s represents a movement of a piece at %s to %s and a piece at %s to %s together.", currentLine,
//					match.group(FIRST_MOVE_FROM_GROUP), match.group(FIRST_MOVE_TO_GROUP),
//					match.group(SECOND_MOVE_FROM_GROUP), match.group(SECOND_MOVE_TO_GROUP) );
//		}
		else
		{
			System.out.println( String.format( "%s is an invalid command", currentLine ) );
		}
		
	}
}
