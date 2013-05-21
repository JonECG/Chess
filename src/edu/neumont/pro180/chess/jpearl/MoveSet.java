/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.ArrayList;

public class MoveSet
{
	private Move[] moves;
	
	public MoveSet( Move[] moves )
	{
		this.moves = moves;
	}
	
	//Returns a list of moves in the moveset that match a given move in the given direction
	public ArrayList<Move> matchMoves( Move testMove, VerticalDirection direction )
	{
		ArrayList<Move> result = new ArrayList<Move>();
		
		Move relativeMove = new Move( testMove.getDeltaX(), testMove.getDeltaY()*direction.getDeltaY(), testMove.getType(), testMove.getStyle() );
		for( Move move : moves )
		{
			if (relativeMove.equals( move ))
			{
				result.add( move );
			}
		}
		
		return result;
	}
}
