/**
 * @author JonathanPearl
 *
 */
package module2;

import module2.Move.MoveStyle;
import module2.Move.MoveType;

public class Knight extends Piece
{
	public Knight( PieceColor color )
	{
		super( color );
	}

	@Override
	public char getCharacterRepresentation()
	{
		return 'n';
	}
	
	@Override
	public MoveSet getMoves()
	{
		Move[] baseMoves = { new Move( 2, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ), new Move( 1, 2, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
