/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveCase;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveStyle;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class King extends Piece
{
	public static final char REPRESENTATION = 'k';
	public static final double VALUE = 1000000;
	
	public King( PieceColor color )
	{
		super( color, REPRESENTATION, VALUE );
		color.getDeclaredPlayer().setVitalPiece( this );
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
		allMoves[ regularMoves.length ] = new Move( -2, 0, MoveType.MOVE, MoveStyle.STEP, MoveCase.CASTLING );
		allMoves[ regularMoves.length + 1 ] = new Move( 2, 0, MoveType.MOVE, MoveStyle.STEP, MoveCase.CASTLING );
		return new MoveSet( allMoves );
	}
}
