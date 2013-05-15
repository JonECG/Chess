/**
 * @author JonathanPearl
 *
 */
package module1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public ChessParser( String path ) throws FileNotFoundException
	{
		reader = new BufferedReader( new FileReader( new File( path ) ) );
	}
	
	public boolean isReady() throws IOException
	{
		return reader.ready();
	}
	
	public String parseNextLine() throws IOException
	{
		String currentLine = reader.readLine().trim();
		String result;
		
		if ( currentLine.matches( REGEX_PLACE_PIECE ) )
		{
			Pattern pattern = Pattern.compile( REGEX_PLACE_PIECE );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			result = String.format( "%s represents a placement of a new %s %s at %s.", currentLine, 
					PieceColor.parseCharacter( match.group(NEW_PIECE_DESCRIPTION_GROUP).charAt( 1 ) ),
					PieceRepresentation.parseCharacter( match.group(NEW_PIECE_DESCRIPTION_GROUP).charAt( 0 ) ), 
					match.group(NEW_PIECE_POSITION_GROUP) );
		}
		else
		if ( currentLine.matches( REGEX_MOVE_WITH_CAPTURE ) )
		{
			Pattern pattern = Pattern.compile( REGEX_MOVE_WITH_CAPTURE );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			result = String.format( "%s represents a movement of a piece at %s to %s with a capture.", currentLine,
					match.group(FIRST_MOVE_FROM_GROUP), match.group(FIRST_MOVE_TO_GROUP) );
		}
		else
		if ( currentLine.matches( REGEX_MOVE_WITHOUT_CAPTURE ) )
		{
			Pattern pattern = Pattern.compile( REGEX_MOVE_WITHOUT_CAPTURE );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			result = String.format( "%s represents a movement of a piece at %s to %s.", currentLine,
					match.group(FIRST_MOVE_FROM_GROUP), match.group(FIRST_MOVE_TO_GROUP) );
		}
		else
		if ( currentLine.matches( REGEX_MOVE_TWO_PIECES ) )
		{
			Pattern pattern = Pattern.compile( REGEX_MOVE_TWO_PIECES );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			result = String.format( "%s represents a movement of a piece at %s to %s and a piece at %s to %s together.", currentLine,
					match.group(FIRST_MOVE_FROM_GROUP), match.group(FIRST_MOVE_TO_GROUP),
					match.group(SECOND_MOVE_FROM_GROUP), match.group(SECOND_MOVE_TO_GROUP) );
		}
		else
		{
			result = String.format( "%s is an invalid command", currentLine );
		}
		return result;
	}
}
