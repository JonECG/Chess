/**
 * @author JonathanPearl
 *
 */
package module2;

import module2.Move.MoveCase;
import module2.Move.MoveStyle;
import module2.Move.MoveType;

public class Pawn extends Piece
{
	public Pawn( PieceColor color )
	{
		super( color );
	}

	@Override
	public char getCharacterRepresentation()
	{
		return 'p';
	}
	
	@Override
	public MoveSet getMoves()
	{
		Move[] baseMoves = { new Move( 0, 1, MoveType.MOVE, MoveStyle.STEP ),
				new Move( 1, 1, MoveType.CAPTURE, MoveStyle.STEP ),
				new Move( -1, 1, MoveType.CAPTURE, MoveStyle.STEP ),
				new Move( 0, 2, MoveType.MOVE, MoveStyle.STEP, MoveCase.ON_PIECE_FIRST_MOVE ),
				new Move( 1, 1, MoveType.CAPTURE, MoveStyle.STEP, MoveCase.IN_PASSING ),
				new Move( -1, 1, MoveType.CAPTURE, MoveStyle.STEP, MoveCase.IN_PASSING )
			};
		return new MoveSet( baseMoves );
	}
}
