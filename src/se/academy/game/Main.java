package se.academy.game;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.nio.charset.Charset;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();
        String[] playerList = {"1 Player", "2 Players", "3 Players", "4 Players"};
        String newGame = "y";
        do {
            terminal.clearScreen();
            String numberOfPlayers = (String) JOptionPane.showInputDialog(null, "Välj Antal Spelare", "Antal Spelare",
                    JOptionPane.QUESTION_MESSAGE, null, playerList, playerList[0]);
            if (numberOfPlayers == null) {
                System.out.println("Spelet avbröts!");
                exit(0);
            }
            Player[] players = new Player[Integer.parseInt("" + numberOfPlayers.charAt(0))];

            int numberOfEnemies = players.length == 1 ? 3 : 0;

            Game game = new Game(players, numberOfEnemies, terminal);

            do {
                game.addObstaclesToBoard();
                updateScreen(game, terminal);
                game.changeMomentumOfPlayers(terminal);
                game.movePlayersByMomentum();
                game.moveEnemiesTowardsPlayer(game.getPlayer(0));
                game.enemiesTryKillPlayers();
                game.playersDraw();
                game.playersHitObject();
                if (players.length > 1 && game.isOnlyOnePlayerAlive()) {
                    game.endGame(game.isOnlyOnePlayerAlive());
                } else if (game.isAllPlayersDead()) {
                    game.endGame();
                }
            } while (!game.isGameOver());
            updateScreen(game, terminal);
            System.out.println("Game Over!");
            if (game.isDraw()) {
                System.out.println("Nobody Wins!");
            }
            newGame = JOptionPane.showInputDialog("New game? (y/n");
            if (newGame == null) {
                System.exit(0);
            }
        } while (newGame.charAt(0) == 'y');
        System.exit(0);
    }

    public static void updateScreen(Game game, Terminal terminal) {
//        terminal.clearScreen();
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[i].length; j++) {
                if (game.board[i][j]) {
                    terminal.moveCursor(i, j);
                    terminal.putCharacter('\u2588');
                }
            }
        }
        for (int i = 0; i < game.getPlayers().length; i++) {
            terminal.moveCursor(game.getPlayer(i).x, game.getPlayer(i).y);
            terminal.putCharacter((char) ((int) 'O' + i));
        }
        for (int i = 0; i < game.getEnemies().length; i++) {
            terminal.moveCursor(game.getEnemy(i).x, game.getEnemy(i).y);
            terminal.putCharacter('X');
        }
        terminal.moveCursor(0, 0);
    }
}
/*R ArrowRight
    U ArrowUp */