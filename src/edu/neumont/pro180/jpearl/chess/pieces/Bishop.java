/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class Bishop extends Piece
{
	public static final char REPRESENTATION = 'b';
	public static final int VALUE = 3;
	
	public Bishop( PieceColor color )
	{
		super( color, REPRESENTATION, VALUE );
	}

	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 1, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
