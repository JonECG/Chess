/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.ArrayList;

import edu.neumont.pro180.chess.jpearl.Move.MoveType;

public class MoveSet
{
	private Move[] moves;
	
	public MoveSet( Move[] moves )
	{
		this.moves = moves;
	}


	//Returns a list of moves in the moveset that match a given move in the given direction
	public ArrayList<Move> matchMoves( Move testMove )
	{
		ArrayList<Move> result = new ArrayList<Move>();
		
		for( Move move : moves )
		{
			if (testMove.equals( move ))
			{
				result.add( move );
			}
		}
		
		return result;
	}

	public Move[] getMoves()
	{
		return moves;
	}
	
	//Like getMoves but splits MOVE_AND_CAPTURE into two separate move identities with MOVE and the other CAPTURE
	public Move[] getExpandedTypeMoves()
	{
		ArrayList<Move> expandedMoves = new ArrayList<Move>();
		Move[] result = new Move[0];
		
		for( Move move : moves )
		{
			if (move.getType() == MoveType.MOVE_AND_CAPTURE)
			{
				expandedMoves.add( new Move( move.getDeltaX(), move.getDeltaY(), MoveType.MOVE, move.getStyle(), move.getCases() ) );
				expandedMoves.add( new Move( move.getDeltaX(), move.getDeltaY(), MoveType.CAPTURE, move.getStyle(), move.getCases() ) );
			}
			else
			{
				expandedMoves.add( move );
			}
		}
		
		return expandedMoves.toArray(result);
	}
}
