/**
 * @author JonathanPearl
 *
 */
package module2;

public enum PieceColor
{
	LIGHT('l', VerticalDirection.UP ), DARK('d', VerticalDirection.DOWN );
	
	private char representation;
	private VerticalDirection verticalDirection;
	
	PieceColor( char representation, VerticalDirection verticalDirection )
	{
		this.representation = representation;
		this.verticalDirection = verticalDirection;
	}
	
	//Returns the enum value that is represented by a character
	public static PieceColor parseCharacter( char representation )
	{
		PieceColor result = null;
		
		for( PieceColor test : PieceColor.values() )
		{
			if ( representation == test.getRepresentation() )
			{
				result = test;
			}
		}
		
		if (result == null)
		{
			throw new IllegalArgumentException( String.format( "The character '%c' does not match a color." , representation ) );
		}
		
		return result;
	}
	
	public char getRepresentation()
	{
		return representation;
	}
	
	public VerticalDirection getVerticalDirection()
	{
		return verticalDirection;
	}
}
