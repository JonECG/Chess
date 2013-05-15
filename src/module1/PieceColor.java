/**
 * @author JonathanPearl
 *
 */
package module1;

public enum PieceColor
{
	LIGHT('l'), DARK('d');
	
	private char representation;
	
	PieceColor( char representation )
	{
		this.representation = representation;
	}
	
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
}
