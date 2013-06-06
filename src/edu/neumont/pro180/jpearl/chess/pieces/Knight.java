/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class Knight extends Piece
{
	public Knight( PieceColor color )
	{
		super( color, 'n', 6 );
	}
	
	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 2, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ), new Move( 1, 2, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
