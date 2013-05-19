/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import edu.neumont.pro180.chess.jpearl.Move.MoveStyle;
import edu.neumont.pro180.chess.jpearl.Move.MoveType;

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
