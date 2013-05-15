/**
 * @author JonathanPearl
 *
 */
package module1;

public enum PieceRepresentation
{
	KING('k'), QUEEN('q'), BISHOP('b'), KNIGHT('n'), ROOK('r'), PAWN('p');
	
	private char representation;
	
	PieceRepresentation( char representation )
	{
		this.representation = representation;
	}
	
	public static PieceRepresentation parseCharacter( char representation )
	{
		PieceRepresentation result = null;
		
		for( PieceRepresentation test : PieceRepresentation.values() )
		{
			if ( representation == test.getRepresentation() )
			{
				result = test;
			}
		}
		
		if (result == null)
		{
			throw new IllegalArgumentException( String.format( "The character '%c' does not match any existing piece." , representation ) );
		}
		
		return result;
	}
	
	public char getRepresentation()
	{
		return representation;
	}
}
