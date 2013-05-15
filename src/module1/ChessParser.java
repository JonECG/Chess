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
	private static final String regexPlacePiece = "([kqbrnp][ld])([a-h][1-8])";
	private static final String regexMoveWithCapture = "([a-h][1-8]) ([a-h][1-8])\\*";
	private static final String regexMoveWithoutCapture = "([a-h][1-8]) ([a-h][1-8])";
	//private static final String regexMoveTwoPieces = "([a-h][1-8])(?: ([a-h][1-8])){3}";
	
	BufferedReader reader;
	
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
		
		
		if ( currentLine.matches( regexPlacePiece ) )
		{
			Pattern pattern = Pattern.compile( regexPlacePiece );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			System.out.printf( "%s represents a placement of a new %s %s at %s.\n", currentLine, 
					PieceColor.parseCharacter( match.group(1).charAt( 1 ) ),
					PieceRepresentation.parseCharacter( match.group(1).charAt( 0 ) ), 
					match.group(2) );
		}
		else
		if ( currentLine.matches( regexMoveWithCapture ) )
		{
			Pattern pattern = Pattern.compile( regexMoveWithCapture );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			System.out.printf( "%s represents a movement of a piece at %s to %s with a capture.\n", currentLine, match.group(1), match.group(2) );
		}
		else
		if ( currentLine.matches( regexMoveWithoutCapture ) )
		{
			Pattern pattern = Pattern.compile( regexMoveWithoutCapture );
			Matcher match = pattern.matcher( currentLine );
			match.find();
			System.out.printf( "%s represents a movement of a piece at %s to %s.\n", currentLine, match.group(1), match.group(2) );
		}
		else
		if ( currentLine.matches( regexMoveTwoPieces ) )
		{
			System.out.printf( "%s represents a movement of two pieces\n", currentLine );
		}
		else
		{
			System.out.printf( "%s is an invalid command\n", currentLine );
		}
		return "";
	}
}
