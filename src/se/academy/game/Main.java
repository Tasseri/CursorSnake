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


            //       players[1].keyInputs[0] = 'w';
            int numberOfEnemies = Integer.parseInt(JOptionPane.showInputDialog("Ange antal fiender!"));
            Game game = new Game(players, numberOfEnemies);

            do {
                game.addObstaclesToBoard();
                updateScreen(game.getPlayers(), terminal, game.getEnemies());
                game.changeMomentumOfPlayers(game.getPlayers(), terminal);
                game.movePlayersByMomentum(players);
                //           game.board[players[i].x][ playerx[i].y]
                game.moveEnemiesTowardsPlayer(game.getPlayer(0));
                game.enemyTryKillPlayer(players[0], game.getEnemy(0));
                game.playersDraw(players);
                game.playersHitObejct(players);

                if (game.isAllPlayersDead()) {
                    game.endGame();
                }
            } while (!game.isGameOver());
            updateScreen(players, terminal, game.getEnemies());
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