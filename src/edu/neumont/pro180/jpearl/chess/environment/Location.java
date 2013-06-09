/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.environment;

import edu.neumont.pro180.jpearl.chess.pieces.Move;

public class Location
{
	private static final int CHARACTER_LOWERCASE_A_REFERENCE = (int) 'a';
	private int x, y;
	
	//Creates a location which represents a coordinate
	public Location( int x, int y )
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Location other = (Location) obj;
		if ( x != other.x )
			return false;
		if ( y != other.y )
			return false;
		return true;
	}
	
	//Return a new location where a move has been added to the current location
	public Location addMove( Move move )
	{
		return new Location( x + move.getDeltaX(), y + move.getDeltaY() );
	}
	
	public Location adjust( int deltaX, int deltaY )
	{
		return new Location( x + deltaX, y + deltaY );
	}
	
	public boolean isInBoard()
	{
		return ( x >= 0 && x < ChessBoard.BOARD_SIZE && y >= 0 && y < ChessBoard.BOARD_SIZE );
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	//Parse a location from chessboard coordinates
	public static Location parseFromCoordinates( String coordinates )
	{
		if (coordinates.length() != 2)
		{
			throw new IllegalArgumentException( coordinates + " is improper length." );
		}
		
		String lowerCaseCoordinates = coordinates.toLowerCase();
		
		int x = lowerCaseCoordinates.charAt( 0 ) - CHARACTER_LOWERCASE_A_REFERENCE;
		int y = Integer.parseInt( String.valueOf( lowerCaseCoordinates.charAt( 1 ) ) ) - 1;
		
		Location location = new Location( x, y );
		
		if (!location.isInBoard())
		{
			throw new IllegalArgumentException( coordinates + " falls outside the board." );
		}
		
		return location;
	}
	
	//Displays the location as chessboard coordinates
	@Override
	public String toString()
	{
		return String.format( "%c%d" , x+CHARACTER_LOWERCASE_A_REFERENCE, y+1 );
	}
}
