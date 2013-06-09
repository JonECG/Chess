/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveCase;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class Pawn extends Piece
{
	public static final char REPRESENTATION = 'p';
	public static final int VALUE = 1;
	
	public Pawn( PieceColor color )
	{
		super( color, REPRESENTATION, VALUE );
	}
	
	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 0, 1, MoveType.MOVE, MoveStyle.STEP ),
				new Move( 1, 1, MoveType.CAPTURE, MoveStyle.STEP ),
				new Move( -1, 1, MoveType.CAPTURE, MoveStyle.STEP ),
				new Move( 0, 2, MoveType.MOVE, MoveStyle.STEP, MoveCase.ON_PIECE_FIRST_MOVE, MoveCase.MID_POINT_FREE ),
				new Move( 1, 1, MoveType.MOVE, MoveStyle.STEP, MoveCase.IN_PASSING ),
				new Move( -1, 1, MoveType.MOVE, MoveStyle.STEP, MoveCase.IN_PASSING )
			};
		return new MoveSet( baseMoves );
	}
}
