package se.academy.game;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();
        String[] playerList = {"1 Player", "2 Players", "3 Players", "4 Players"};
        String newGame = "y";
        do {
            terminal.clearScreen();
            RuleBook rule = new SnakeRules();
            String numberOfPlayers = (String) JOptionPane.showInputDialog(null, "Välj Antal Spelare", "Antal Spelare",
                    JOptionPane.QUESTION_MESSAGE, null, playerList, playerList[0]);
            if (numberOfPlayers == null) {
                System.out.println("Spelet avbröts!");
                exit(0);
            }
            int players = Integer.parseInt("" + numberOfPlayers.charAt(0));

            int numberOfEnemies = players == 1 ? 3 : 0;

            Game game = new Game(players, numberOfEnemies, terminal, rule);
            updateScreen(game, terminal);
            game.gameCountdown();

            do {
                game.changeMomentumOfPlayers(terminal);
                game.updateState();
                updateScreen(game, terminal);

            } while (!game.isGameOver());
            updateScreen(game, terminal);
            System.out.println("Game Over!");
            if (game.isGameDraw()) {
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
        terminal.clearScreen();

        // placee Wals with a foor loop that cycles thrue wall list
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[i].length; j++) {
                if (game.board[i][j]) {
                    terminal.moveCursor(i, j);
                    terminal.putCharacter('\u2588');
                }
            }
        }
        // old walls ends here, need to sort out tail before removal

        for (Wall wall : game.getWalls()) {

            terminal.moveCursor(wall.getCoord().getX(), wall.getCoord().getY());
            terminal.putCharacter(wall.getApparence());

        }
        for (int i = 0; i < game.getPlayers().length; i++) {
            terminal.moveCursor(game.getPlayer(i).getCoord().getX(), game.getPlayer(i).getCoord().getY());
            terminal.putCharacter(game.getPlayer(i).getApparence());
        }
        for (int i = 0; i < game.getEnemies().length; i++) {
            terminal.moveCursor(game.getEnemy(i).getCoord().getX(), game.getEnemy(i).getCoord().getY());
            terminal.putCharacter(game.getEnemy(i).getApparence());
        }
        terminal.moveCursor(0, 0);
    }
}
/*R ArrowRight
    U ArrowUp */