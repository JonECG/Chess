/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import edu.neumont.pro180.chess.jpearl.Move.MoveStyle;
import edu.neumont.pro180.chess.jpearl.Move.MoveType;

public class Queen extends Piece
{
	public Queen( PieceColor color )
	{
		super( color );
	}

	@Override
	public char getCharacterRepresentation()
	{
		return 'q';
	}

	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { new Move( 1, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ),
				new Move( 0, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.SLIDE ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
