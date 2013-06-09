/**
 * @author JonathanPearl
 *
 */
package edu.neumont.pro180.jpearl.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import edu.neumont.pro180.jpearl.chess.environment.Cell;
import edu.neumont.pro180.jpearl.chess.environment.ChessBoard;
import edu.neumont.pro180.jpearl.chess.environment.Cell.TurnResult;
import edu.neumont.pro180.jpearl.chess.pieces.King;
import edu.neumont.pro180.jpearl.chess.pieces.Move;
import edu.neumont.pro180.jpearl.chess.pieces.MoveSet;
import edu.neumont.pro180.jpearl.chess.pieces.Pawn;
import edu.neumont.pro180.jpearl.chess.pieces.Piece;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.pieces.Queen;
import edu.neumont.pro180.jpearl.chess.pieces.Move.MoveCase;

public class Action implements Comparable<Action>
{
	private Cell originCell;
	private Move move;
	private Cell otherCell;
	private TurnResult moveResult;
	
	private static final double SLIGHT_BIAS = 0.00001;
	private static final double SMALL_BIAS = 0.0001;
	private static final double MEDIUM_BIAS = 0.001;
	private static final double LARGE_BIAS = 0.01;
	
	public Action( Cell originCell, Move move, Cell otherCell, TurnResult moveResult )
	{
		this.originCell = originCell;
		this.move = move;
		this.otherCell = otherCell;
		this.moveResult = moveResult;
	}

	
	public void perform()
	{
		originCell.suggestMove( move );
	}
	
	public double getValue()
	{
        return getValue(3);
	}
	
	public double getValue( int numberOfRecursions )
	{
        double result = recurseForValue( numberOfRecursions, originCell.getBoard().getGame().getTurnColor(), System.currentTimeMillis() );
		return result;
	}
	
	public double recurseForValue( int recurseLevel, PieceColor favor, long startTime )
	{
		boolean isInFavor = originCell.getBoard().getGame().getTurnColor() == favor;
		
		int otherCellValue = otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : 0;
		double result = ( isInFavor ) ? otherCellValue : -otherCellValue*(1+LARGE_BIAS);
		
		//Add Bias for moves with larger board cover and pieces that have not been used as often
		double subTotal = move.getLength()*SMALL_BIAS - originCell.getPiece().getNumberOfMoves()*LARGE_BIAS;
		
		if( originCell.getPiece().getCharacterRepresentation() == Pawn.REPRESENTATION )
		{
			subTotal += SLIGHT_BIAS; //Bias to move pawns if no move is available
			
			if( otherCell.getLocation().getY() == 0 || otherCell.getLocation().getY() == ChessBoard.BOARD_SIZE-1 )
			{
				subTotal += Queen.VALUE; //Add value to moves with pawn promotion
			}
			
			if ( move.hasCase( MoveCase.IN_PASSING ) ) //Consider en passant a capture of a pawn
			{
				subTotal += Pawn.VALUE;
			}
		}
		
		
		result += ( isInFavor ) ? subTotal : -subTotal;
		
		if( moveResult == TurnResult.OPPONENT_CHECKMATE )
		{
			result += ( isInFavor ) ? King.VALUE : -King.VALUE; //Register value of checkmate despite no capture
		}
		else
		if( recurseLevel > 0 )
		{
            originCell.getBoard().digSimulation();
            perform();
            ArrayList<Action> allActions = originCell.getBoard().getGame().getAllActions( originCell.getBoard().getGame().getTurnColor() );
                    
            double bestMove = ( isInFavor ) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            
            for( Action testingAction : allActions )
            {
            	double actionValue = testingAction.recurseForValue(recurseLevel -1, favor, startTime);
            	if ( isInFavor )
            	{
            		if ( actionValue < bestMove )
            		{
            			bestMove = actionValue;
            		}
            	}
            	else
            	{
            		if ( actionValue > bestMove )
            		{
            			bestMove = actionValue;
            		}
            	}
            }
            
            result += bestMove*(1-MEDIUM_BIAS); //Uncertainty and Impatience bias
            
            originCell.getBoard().rollBackSimulation();
        }
		
		return result;
	}



	public int getImmediateValue()
	{
		int enPassantValue = 0;

		if ( move.hasCase( MoveCase.IN_PASSING ) ) //Consider en passant a capture of a pawn
			enPassantValue = 1;
			
		return otherCell.hasPiece() ? otherCell.getPiece().getUnitWorth() : enPassantValue;
	}


	@Override
	public int compareTo( Action other )
	{
		return other.getImmediateValue() - getImmediateValue();
	}


	/**
	 * 
	 */
	public void printPieceMoves()
	{
		System.out.println( otherCell.getPiece().getNumberOfMoves() );
	}

}
