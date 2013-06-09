/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class Knight extends Piece
{
	public static final char REPRESENTATION = 'n';
	public static final double VALUE = 3.2;
	
	public Knight( PieceColor color )
	{
		super( color, REPRESENTATION, VALUE );
	}
	
	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 2, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ), new Move( 1, 2, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
