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
    private ChessGame game;

    public Tournament( JFrame frame, String path, int defaultSize, int numOfGames ) {
        this.frame = frame;
        this.path = path;
        this.defaultSize = defaultSize;
        winners = new Player[numOfGames];
    }

    public void start(){
        String title = "Smart: 0, RandomWithCapture: 0";
        frame.setTitle(title);
        for( int i = 0; i < winners.length; i ++ ){
            Player winner = newGame();
            winners[i] = winner;
            frame.setTitle(getTitle());
            frame.repaint();
        }
        System.out.println( "tournament winner: " + tournamentWinner() );
    }

    private String getTitle(){
        int p1wins = 0, p2wins = 0;
        String p1 = game.getPlayerRoster()[0].toString();
        String p2 = game.getPlayerRoster()[1].toString();
        for(int i = 0; i < winners.length; i++){
            if(winners[i] != null && winners[i].toString().equals(p1))
                p1wins++;
            else if(winners[i] != null)
                p2wins++;
        }
        String p1txt = p1 + ": " + p1wins;
        String p2txt = p2 + ": " + p2wins;
        return p1txt + ", " + p2txt;
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

        game = new ChessGame();
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
