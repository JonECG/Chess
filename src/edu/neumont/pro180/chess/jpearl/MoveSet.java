/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.ArrayList;

public class MoveSet
{
	Move[] moves;
	
	public MoveSet( Move[] moves )
	{
		this.moves = moves;
	}
	
	public boolean containsMove( Move testMove, VerticalDirection direction )
	{
		boolean result = false;
		Move relativeMove = new Move( testMove.getDeltaX(), testMove.getDeltaY()*direction.getDeltaY(), testMove.getType(), testMove.getStyle() );
		for( Move move : moves )
		{
			if (!result)
			{
				if (relativeMove.equals( move ))
				{
					result = true;
				}
			}
		}
		return result;
	}
	
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
