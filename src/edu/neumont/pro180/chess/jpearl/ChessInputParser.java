/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.chess.jpearl;

import java.util.Scanner;

public class ChessInputParser extends ChessParser
{
	private Scanner scan;
	private String nextLine;
	
	public ChessInputParser()
	{
		scan = new Scanner( System.in );
	}
	
	@Override
	public boolean isReady()
	{
		boolean result = true;
		System.out.println( "Enter command or EXIT: " );
		 nextLine = scan.nextLine();
		 
		if ( nextLine.toUpperCase().equals( "EXIT" ) )
		{
			result = false;
			scan.close();
		}
		
		return result;
	}

	@Override
	public String nextLine()
	{
		return nextLine;
	}
}
