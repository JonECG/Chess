/**
 * @author JonathanPearl
 *
 */
package module2;

import module2.Move.MoveStyle;
import module2.Move.MoveType;

public class Rook extends Piece
{
	public Rook( PieceColor color )
	{
		super( color );
	}

	@Override
	public char getCharacterRepresentation()
	{
		return 'r';
	}

	@Override
	public MoveSet getMoves()
	{
		Move[] baseMoves = { new Move( 0, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
