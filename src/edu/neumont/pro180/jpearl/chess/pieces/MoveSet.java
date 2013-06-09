/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import java.util.ArrayList;

import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveType;

public class MoveSet
{
	private Move[] moves;
	
	public MoveSet( Move[] moves )
	{
		this.moves = moves;
	}


	//Returns a list of moves in the moveset that match a given move
	public ArrayList<Move> matchingMoves( Move testMove )
	{
		ArrayList<Move> result = new ArrayList<Move>();
		
		for( Move move : moves )
		{
			if (testMove.equals( move ))
			{
				result.add( move );
			}
		}
		if (result.size() > 1)
			System.out.println( result.size() );
		return result;
	}
	
	//Returns a list of moves in the moveset that match a given move
	public Move matchingMove( Move testMove )
	{
		Move result = null;
		
		for( Move move : moves )
		{
			if ( result == null && testMove.equals( move ))
			{
				result = move;
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
