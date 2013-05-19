/**
 * @author JonathanPearl
 *
 */
package module2;

public enum PieceRepresentation
{
	KING('k'), QUEEN('q'), BISHOP('b'), KNIGHT('n'), ROOK('r'), PAWN('p');
	
	private char representation;
	
	PieceRepresentation( char representation )
	{
		this.representation = representation;
	}
	
	public Piece createPiece( PieceColor color )
	{
		Piece piece = null;
		switch( this )
		{
		case KING:
			piece = new King(color);
			break;
		case QUEEN:
			piece = new Queen(color);
			break;
		case BISHOP:
			piece = new Bishop(color);
			break;
		case KNIGHT:
			piece = new Knight(color);
			break;
		case ROOK:
			piece = new Rook(color);
			break;
		case PAWN:
			piece = new Pawn(color);
			break;
		}
		return piece;
	}
	
	//Returns the enum value that is represented by a character
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
