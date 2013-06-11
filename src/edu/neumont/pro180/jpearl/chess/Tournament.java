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
    private int numOfGames;

    public Tournament( JFrame frame, String path, int defaultSize, int numOfGames ) {
        this.frame = frame;
        this.path = path;
        this.defaultSize = defaultSize;
        this.numOfGames = numOfGames;
        winners = new Player[numOfGames];
    }

    public void start(){
        String title = "Smart: 0, RandomWithCapture: 0";
        frame.setTitle( title );
        for( int i = 0; i < winners.length; i ++ ){
            Player winner = newGame();
            winners[i] = winner;
            frame.setTitle( getTitle() );
            frame.repaint();
        }
    }

    private String getTitle(){
        int p1wins = 0, p2wins = 0;
        String p1 = game.getPlayerRoster()[0].toString();
        String p2 = game.getPlayerRoster()[1].toString();
        for( int i = 0; i < winners.length; i++ ){
            if( winners[i] != null && winners[i].toString().equals( p1 ) )
                p1wins++;
            else if( winners[i] != null )
                p2wins++;
        }
        String title;
        if(p1wins < ( numOfGames / 2 ) + 1 && p1wins < ( numOfGames / 2 ) + 1 )
            title = p1 + ": " + p1wins + ", " + p2 + ": " + p2wins;
        else {
            String winner = ( p1wins > p2wins ) ? p1 : p2;
            title = winner + " won the Tournament!";
        }
        return title;
    }

    private Player newGame(){

        Dimension preferred = new Dimension( defaultSize, defaultSize );
        frame.setPreferredSize( preferred );

        game = new ChessGame();
        game.addNewPlayer( new SmartAIPlayer( PieceColor.LIGHT, game, 1 ) );
        game.addNewPlayer( new RandomWithCaptureAIPlayer( PieceColor.DARK, game) );

        BoardPanel boardPanel = new BoardPanel( frame, game.getChessBoard() );
        frame.add(boardPanel);

        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.pack();

        game.runParser( new ChessFileParser( path ) );

        frame.setVisible( true );

        frame.repaint();

        Player winner = game.playGame();

        try { Thread.sleep( 4000 ); }
        catch ( InterruptedException ignored ) { }

        frame.remove( boardPanel );
        frame.repaint();

        return winner;
    }
}
