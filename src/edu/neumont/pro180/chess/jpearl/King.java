/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import edu.neumont.pro180.chess.jpearl.Move.MoveCase;
import edu.neumont.pro180.chess.jpearl.Move.MoveStyle;
import edu.neumont.pro180.chess.jpearl.Move.MoveType;

public class King extends Piece
{
	public King( PieceColor color )
	{
		super( color );
		color.getDeclaredPlayer().setVitalPiece( this );
	}

	@Override
	public char getCharacterRepresentation()
	{
		return 'k';
	}
	
	@Override
	public MoveSet getMoveSet()
	{
		Move[] baseMoves = { 
				new Move( 0, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ),
				new Move( 1, 1, MoveType.MOVE_AND_CAPTURE, MoveStyle.STEP ),
				};
		Move[] regularMoves = Move.reflectMoves( baseMoves );
		Move[] allMoves = new Move[ regularMoves.length + 2 ];
		for( int i = 0; i < regularMoves.length; i++ )
		{
			allMoves[i] = regularMoves[i];
		}
		allMoves[ regularMoves.length ] = new Move( -2, 0, MoveType.MOVE, MoveStyle.STEP, MoveCase.ONCE_PER_GAME );
		allMoves[ regularMoves.length + 1 ] = new Move( 2, 0, MoveType.MOVE, MoveStyle.STEP, MoveCase.ONCE_PER_GAME );
		return new MoveSet( allMoves );
	}
}
