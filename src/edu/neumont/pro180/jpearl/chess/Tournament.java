package edu.neumont.pro180.jpearl.chess;

import edu.neumont.pro180.jpearl.chess.io.ChessFileParser;
import edu.neumont.pro180.jpearl.chess.pieces.PieceColor;
import edu.neumont.pro180.jpearl.chess.view.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class Tournament {

    private String path;
    private int defaultSize;
    Player [] winners;

    public Tournament( String path, int defaultSize, int numOfGames ) {
        this.path = path;
        this.defaultSize = defaultSize;
        winners = new Player[numOfGames];
    }

    public void start(){
        for(int i = 0; i < winners.length; i ++){
            Player winner = newGame();
            winners[i] = winner;
            System.out.println("round winner: " + winner);
        }
        System.out.println("tournament winner: " + tournamentWinner());
    }

    private PieceColor tournamentWinner(){
        PieceColor winner = null;
        int darkWins = 0, lightWins = 0;
        for (Player winner1 : winners) {
            if (winner1.getCommandingColor() == PieceColor.DARK)
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

        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.pack();

        game.runParser( new ChessFileParser( path ) );

        frame.repaint();

        frame.setVisible( true );
        frame.setLocationRelativeTo( null );

        Player winner = game.playGame();

        frame.repaint();

        try { Thread.sleep(4000); }
        catch ( InterruptedException ignored) { }
        frame.setVisible(false);
        frame.dispose();
        return winner;
    }
}
