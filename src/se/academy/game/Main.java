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
            String numberOfplayers = (String) JOptionPane.showInputDialog(null, "Välj Antal Spelare", "Antal Spelare",
                    JOptionPane.QUESTION_MESSAGE, null, playerList, playerList[0]);
            if (numberOfplayers == null) {
                System.out.println("Spelet avbröts!");
                exit(0);
            }
            Player[] players = new Player[Integer.parseInt("" + numberOfplayers.charAt(0))];

            int numberOfEnemies = players.length == 1 ? 3 : 0;

            Game game = new Game(players, numberOfEnemies);

            do {
                game.addObstaclesToBoard();
                updateScreen(game.getPlayers(), terminal, game.getEnemies());
                game.changeMomentumOfPlayers(terminal);
                game.movePlayersByMomentum();
                game.moveEnemiesTowardsPlayer(game.getPlayer(0));
                game.enemiesTryKillPlayers();
                game.playersDraw();
                game.playersHitObject();
                if (players.length > 1 && game.isOnlyOnePlayerAlive()) {
                    game.endGame(game.isOnlyOnePlayerAlive());
                }
                else if (game.isAllPlayersDead()) {
                    game.endGame();
                }
            } while (!game.isGameOver());
            updateScreen(game.getPlayers(), terminal, game.getEnemies());
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

    public static void updateScreen(Player[] playerx, Terminal terminal, Enemy[] enemies) {
//        terminal.clearScreen();
        for (int i = 0; i < playerx.length; i++) {
            terminal.moveCursor(playerx[i].x, playerx[i].y);
            terminal.putCharacter((char) ((int) 'O' + i));
        }
        for (int i = 0; i < enemies.length; i++) {
            terminal.moveCursor(enemies[i].x, enemies[i].y);
            terminal.putCharacter('X');
        }
        terminal.moveCursor(0, 0);
    }
}
/*R ArrowRight
    U ArrowUp */