/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

public enum VerticalDirection
{
	UP( 1 ), DOWN( -1 );
	
	private int deltaY;
	
	VerticalDirection( int deltaY )
	{
		this.deltaY = deltaY;
	}
	
	public int getDeltaY()
	{
		return deltaY;
	}
}
