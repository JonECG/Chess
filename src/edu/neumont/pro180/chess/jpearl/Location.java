/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

public class Location
{
	private static final int CHARACTER_LOWERCASE_A_REFERENCE = (int) 'a';
	private int x, y;
	
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
	
	public Location addMove( Move move )
	{
		return new Location( x + move.getDeltaX(), y + move.getDeltaY() );
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public static Location parseFromCoordinates( String coordinates )
	{
		String lowerCaseCoordinates = coordinates.toLowerCase();
		
		int x = lowerCaseCoordinates.charAt( 0 ) - CHARACTER_LOWERCASE_A_REFERENCE;
		int y = Integer.parseInt( String.valueOf( lowerCaseCoordinates.charAt( 1 ) ) ) - 1;
		return new Location( x, y );
	}
	
	@Override
	public String toString()
	{
		return String.format( "%c%d" , x+CHARACTER_LOWERCASE_A_REFERENCE, y+1 );
	}
}
