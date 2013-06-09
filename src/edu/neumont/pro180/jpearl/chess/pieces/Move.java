/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess.pieces;

import java.util.Arrays;
import java.util.List;

import edu.neumont.pro180.jpearl.chess.environment.Location;

public class Move
{
	//move -- The piece can only move here on a regular move
	//capture -- The piece can only move here provided it is performing a capture
	//move and capture -- The piece can move to this spot for a regular move or for performing a capture
	public enum MoveType{ MOVE, CAPTURE, MOVE_AND_CAPTURE };
	
	//step -- The piece jumps to the given offset ignoring pieces in the way
	//slide -- The piece has unlimited move range in the given direction and will be stopped at any piece in its way
	public enum MoveStyle{ STEP, SLIDE }
	
	//on piece first move -- Move can only be made as the piece's first move (a pawn moving two spaces on its first move)
	//once per game -- Move can only be made once per game per player (castling)
	//in passing -- Move can only be made when a piece could take a pawn out if it had moved one space but has just moved two as its first move (en passant)
	//mid point free -- Move can only be made if the space between the start and destination is free
	public enum MoveCase{ ON_PIECE_FIRST_MOVE, ONCE_PER_GAME, IN_PASSING, MID_POINT_FREE };
	
	private static final int NUMBER_OF_REFLECTIONS = 4;
	
	private int deltaX, deltaY;
	private MoveType type;
	private MoveStyle style;
	private List<MoveCase> cases;
	
	public Move( int deltaX, int deltaY, MoveType type, MoveStyle style, MoveCase... cases )
	{
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.type = type;
		this.style = style;
		this.cases = Arrays.asList( cases );
	}
	
	//Rotates moves 90 degrees 3 times to make them point in all direction
	public static Move[] reflectMoves( Move... moves )
	{
		Move[] result = new Move[ moves.length * NUMBER_OF_REFLECTIONS ];
		
		for( int i = 0; i < moves.length; i++ )
		{
			result[ (i*NUMBER_OF_REFLECTIONS) ] = moves[i];
			for( int j = 1; j < NUMBER_OF_REFLECTIONS; j++ )
			{
				Move reference = result[ (i*NUMBER_OF_REFLECTIONS) + j - 1 ];
				result[ (i*NUMBER_OF_REFLECTIONS) + j ] = new Move( -reference.getDeltaY(), reference.getDeltaX(), reference.getType(), reference.getStyle() );
			}
		}
		
		return result;
	}
	
	//Find the move that would result in a movement from one location to another
	public static Move makeFromLocations( Location from, Location to, MoveType type )
	{
		return new Move( to.getX()-from.getX(), to.getY()-from.getY(), type, MoveStyle.STEP );
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + deltaX;
		result = prime * result + deltaY;
		result = prime * result + ( ( style == null ) ? 0 : style.hashCode() );
		result = prime * result + ( ( type == null ) ? 0 : type.hashCode() );
		return result;
	}

	//Allows moves that are acceptable with each other to be equal. E.G. steps match slide when they are inside and capture matches move_and_capture
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Move other = (Move) obj;
		if ( style == MoveStyle.SLIDE ^ other.style == MoveStyle.SLIDE )
		{
			Move slider = ( style == MoveStyle.SLIDE ) ? this : other;
			Move stepper = ( other.style == MoveStyle.SLIDE ) ? this : other;
			if (!isStepInSlide( stepper, slider ) )
			{
				return false;
			}
		}
		else
		{
			if ( deltaX != other.deltaX )
				return false;
			if ( deltaY != other.deltaY )
				return false;
		}
		if ( (type != MoveType.MOVE_AND_CAPTURE && other.type != MoveType.MOVE_AND_CAPTURE) && (type != other.type) )
			return false;
		return true;
	}

	public int getDeltaX()
	{
		return deltaX;
	}

	public int getDeltaY()
	{
		return deltaY;
	}

	public MoveType getType()
	{
		return type;
	}
	
	public MoveStyle getStyle()
	{
		return style;
	}

	public MoveCase[] getCases()
	{
		return (MoveCase[]) cases.toArray();
	}
	
	//Figures out if a moving step resolves a slide condition move
	public static boolean isStepInSlide( Move stepMove, Move slideMove )
	{
		boolean result = false;
		if (stepMove.getStyle() != MoveStyle.STEP)
		{
			throw new IllegalArgumentException( "The provided stepMove does not contain a STEP style" );
		}
		if (slideMove.getStyle() != MoveStyle.SLIDE)
		{
			throw new IllegalArgumentException( "The provided slideMove does not contain a SLIDE style" );
		}
		int stepMoveX = stepMove.getDeltaX();
		int stepMoveY = stepMove.getDeltaY();
		int slideMoveX = slideMove.getDeltaX();
		int slideMoveY = slideMove.getDeltaY();
		
		if (slideMoveX == 0)
		{
			result = ( 
					( stepMoveX == 0 ) && 				//Do they share the same slope
					( stepMoveY % slideMoveY == 0 ) && 	//Does it step precisely with the y change
					( stepMoveY/slideMoveY > 0 ) 		//Do they move in the same direction
				);
		}
		else if (slideMoveY == 0)
		{
			result = ( 
					( stepMoveY == 0 ) && 				//Do they share the same slope
					( stepMoveX % slideMoveX == 0 ) && 	//Does it step precisely with the X change
					( stepMoveX/slideMoveX > 0 ) 		//Do they move in the same direction
				);
		}
		else
		{
			result = ( 
					( stepMoveY % slideMoveY == 0 ) && 					//Does it step precisely with the y change
					( stepMoveX % slideMoveX == 0 ) && 					//Does it step precisely with the x change
					( stepMoveY/slideMoveY == stepMoveX/slideMoveX ) &&	//Do they share the same slope
					( stepMoveY/slideMoveY > 0 ) 						//Do they move in the same direction
				);
		}
		return result;
		
	}
	
	public boolean hasCase( MoveCase testCase )
	{
		return cases.contains( testCase );
	}


	public Move getVerticallyFlipped()
	{
		return new Move( deltaX, -deltaY, type, style, getCases() ); 
	}

	public String toString()
	{
		return String.format( "Delta X: %s; Delta Y: %s; Style: %s", deltaX, deltaY, style );
	}


	public int getLength()
	{
		return Math.abs( deltaX ) + Math.abs( deltaY );
	}
}
