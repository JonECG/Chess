/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import edu.neumont.pro180.chess.jpearl.Move.MoveStyle;
import edu.neumont.pro180.chess.jpearl.Move.MoveType;

public class Knight extends Piece
{
	public Knight( PieceColor color )
	{
		super( color );
	}

	@Override
	public char getCharacterRepresentation()
	{
		return 'n';
	}
	
	@Override
	public MoveSet getMoves()
	{
		Move[] baseMoves = { new Move( 2, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ), new Move( 1, 2, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ) };
		return new MoveSet( Move.reflectMoves( baseMoves ) );
	}
}
