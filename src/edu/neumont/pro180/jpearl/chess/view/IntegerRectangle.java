/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.view;

public class IntegerRectangle
{
	private int x;
	private int y;
	private int w;
	private int h;
	
	public IntegerRectangle( int x, int y, int w, int h )
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return w;
	}
	
	public int getHeight()
	{
		return h;
	}
}
