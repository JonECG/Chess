package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.io.ChessFileParser;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.view.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Tournament {

    private String path;
    private int defaultSize;
    Player [] winners;

    public Tournament( String path, int defaultSize, int numOfGames ) {
        this.path = path;
        this.defaultSize = defaultSize;
        winners = new Player[numOfGames];
        for(int i = 0; i < numOfGames; i ++){
            Player winner = newGame();
            winners[i] = winner;
            System.out.println("round winner: " + winner);
        }
        System.out.println("tournament winner: " + tournamentWinner());
    }

    private PieceColor tournamentWinner(){
        PieceColor winner = null;
        int darkWins = 0, lightWins = 0;
        for(int i = 0; i < winners.length; i++){
            if(winners[i].getCommandingColor() == PieceColor.DARK)
                darkWins++;
            else
                lightWins++;
        }
        if(darkWins != lightWins)
            winner = (darkWins > lightWins) ? PieceColor.DARK : PieceColor.LIGHT;
        return winner;
    }

    private Player newGame(){

        JFrame frame = new JFrame( "Chess and things" );

        Dimension preferred = new Dimension( defaultSize,defaultSize );
        frame.setPreferredSize( preferred );

        ChessGame game = new ChessGame();
        game.addNewPlayer( new SmartAIPlayer( PieceColor.LIGHT, game, 2 ) );
        game.addNewPlayer( new RandomWithCaptureAIPlayer( PieceColor.DARK, game) );

        frame.add( new BoardPanel(frame, game.getChessBoard()) );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();

        game.runParser( new ChessFileParser( path ) );

        frame.repaint();

        frame.setVisible( true );
        frame.setLocationRelativeTo( null );

        Player winner = game.playGame();

        frame.repaint();

        try { Thread.sleep(4000); }
        catch ( InterruptedException e ) { }
        frame.setVisible(false);
        frame.dispose();
        return winner;
    }
}
