/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class Rook extends Piece
{
	public static final char REPRESENTATION = 'r';
	public static final double VALUE = 5.1;
	
	public Rook( PieceColor color )
	{
		super( color, REPRESENTATION, VALUE );
	}
	
	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 0, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
