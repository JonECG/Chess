/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class Queen extends Piece
{
	public Queen( PieceColor color )
	{
		super( color, 'q', 18 );
	}
	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 1, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ),
				new Move( 0, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
