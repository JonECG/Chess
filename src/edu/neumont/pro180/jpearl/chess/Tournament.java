package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.io.ChessFileParser;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.view.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class Tournament {

    private String path;
    private int defaultSize;
    private Player [] winners;
    private JFrame frame;

    public Tournament( JFrame frame, String path, int defaultSize, int numOfGames ) {
        this.frame = frame;
        this.path = path;
        this.defaultSize = defaultSize;
        winners = new Player[numOfGames];
    }

    public void start(){
        for( int i = 0; i < winners.length; i ++ ){
            Player winner = newGame();
            winners[i] = winner;
            System.out.println( "round winner: " + winner );
        }
        System.out.println( "tournament winner: " + tournamentWinner() );
    }

    private PieceColor tournamentWinner(){
        PieceColor winner = null;
        int darkWins = 0, lightWins = 0;
        for ( Player winner1 : winners ) {
            if ( winner1.getCommandingColor() == PieceColor.DARK )
                darkWins++;
            else
                lightWins++;
        }
        if( darkWins != lightWins )
            winner = ( darkWins > lightWins ) ? PieceColor.DARK : PieceColor.LIGHT;
        return winner;
    }

    private Player newGame(){

        Dimension preferred = new Dimension( defaultSize, defaultSize );
        frame.setPreferredSize( preferred );

        ChessGame game = new ChessGame();
        game.addNewPlayer( new SmartAIPlayer( PieceColor.LIGHT, game, 2 ) );
        game.addNewPlayer( new RandomWithCaptureAIPlayer( PieceColor.DARK, game) );

        BoardPanel boardPanel = new BoardPanel( frame, game.getChessBoard() );
        frame.add(boardPanel);

        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.pack();

        game.runParser( new ChessFileParser( path ) );

        frame.setVisible( true );

        frame.repaint();

        Player winner = game.playGame();

        try { Thread.sleep(4000); }
        catch ( InterruptedException ignored ) { }

        frame.remove(boardPanel);
        frame.repaint();

        return winner;
    }
}
