/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import edu.neumont.pro180.chess.jpearl.Move.MoveCase;
import edu.neumont.pro180.chess.jpearl.Move.MoveStyle;
import edu.neumont.pro180.chess.jpearl.Move.MoveType;

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
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 0, 1, MoveType.MOVE, MoveStyle.STEP ),
				new Move( 1, 1, MoveType.CAPTURE, MoveStyle.STEP ),
				new Move( -1, 1, MoveType.CAPTURE, MoveStyle.STEP ),
				new Move( 0, 2, MoveType.MOVE, MoveStyle.STEP, MoveCase.ON_PIECE_FIRST_MOVE, MoveCase.MID_POINT_FREE ),
				new Move( 1, 1, MoveType.CAPTURE, MoveStyle.STEP, MoveCase.IN_PASSING ),
				new Move( -1, 1, MoveType.CAPTURE, MoveStyle.STEP, MoveCase.IN_PASSING )
			};
		return new MoveSet( baseMoves );
	}
}
