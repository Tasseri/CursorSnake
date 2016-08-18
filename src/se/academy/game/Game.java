package se.academy.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

/**
 * Created by Administrator on 2016-08-17.
 */
public class Game {
    public boolean[][] board = new boolean[21][21];
    private Player[] players;
    private Enemy[] enemies;
    //   private String[] playerList = {"1 Player", "2 Players", "3 Players", "4 Players"};
    private boolean GameOver = false;
    private boolean draw = false;

    public Game(Player[] players, int numberOfEnemies) {
        this.players = players;

        this.enemies = new Enemy[numberOfEnemies];
        for (int i = 0; i < numberOfEnemies; i++) {
            addEnemy(i);
        }


        for (int i = 0; i < players.length; i++) {
            addPlayer(i);
            keyAddPlayer(i);
        }
    }

    //Board methods below
    public void addObstaclesToBoard() {
        this.addPlayerTailToBoard();
    }

    private void addPlayerTailToBoard() {
        for (int i = 0; i < players.length; i++) {
            board[players[i].x][players[i].y] = true;
        }
    }
    //end of Board methods

    //Player methods below
    public Player[] getPlayers() {
        return players;
    }

    public void addPlayer(int playerNumber) {
        Random rand = new Random();
        if (playerNumber < this.players.length && playerNumber >= 0) {
            players[playerNumber] = new Player((rand.nextInt(19) + 1), (rand.nextInt(20) + 1));
        }
    }

    public Player getPlayer(int Number) {
        return players[Number];
    }

    public void keyAddPlayer(int playerNumber) {
        players[playerNumber].setKeyInputRight(JOptionPane.showInputDialog("Välj höger för spelare " +
                "(R,L,U,D för piltangenter)" + playerNumber).charAt(0));
        players[playerNumber].setKeyInputLeft(JOptionPane.showInputDialog("Välj vänster för spelare" +
                "(R,L,U,D för piltangenter)" + playerNumber).charAt(0));
    }

    public boolean isAllPlayersDead() {
        for (Player p : players) {
            if (!p.isDead()) {
                return false;
            }
        }
        return true;

    }

    public void movePlayersByMomentum() {
        for (int i = 0; i < players.length; i++) { //Applies the momentum and moves the player
            players[i].trackMovement(players[i].x, players[i].y);

            players[i].moveByMomentum();
        }
    }

    /**
     * takes keyinputs calculates which Player it belongs to and changes the momentum of the player in that direction.
     *
     * @param terminal
     * @throws InterruptedException
     */
    public void changeMomentumOfPlayers(Terminal terminal) throws InterruptedException {
        Key key;
        LocalTime tm = LocalTime.now();

        boolean[] keysPressed = new boolean[players.length];
        for (int i = 0; i < keysPressed.length; i++) {
            keysPressed[0] = false;
        }
        do {
            Thread.sleep(5);
            key = terminal.readInput();
            if (key != null) {

                char keyKind = key.getCharacter();

                for (int i = 0; i < players.length; i++) {
                    for (int j = 0; j < 2; j++) {
                        if (players[i].getKeyInput(j) == keyKind && !keysPressed[i]) {
                            switch (j) {
                                case 0:
                                    players[i].changeMomentum(1);
                                    break;
                                case 1:
                                    players[i].changeMomentum(-1);
                                    break;
                            }
                            keysPressed[i] = true;
                        }

                    }
                }
                System.out.println(key.getCharacter() + " " + key.getKind());
            }

        } while (1 > Duration.between(tm, LocalTime.now()).getSeconds()); //Seconds before the game updates

    }
    //end of Player methods

    //Enemy methods below
    public void addEnemy(int enemyNumber) {
        Random rand = new Random();
        enemies[enemyNumber] = new Enemy((rand.nextInt(20) + 1), (rand.nextInt(20) + 1));
    }

    public Enemy getEnemy(int enemyNumber) {
        return enemies[enemyNumber];
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void moveEnemiesTowardsPlayer(Player player) {
        for (Enemy e : enemies) {
            e.moveEnemyTowardsPlayer(player);
        }
    }
    //end of Enemy methods

    //game logic methods follow
    public void playersHitObject() {
        for (int i = 0; i < players.length; i++) {
            playerHitObject(players[i]);
        }
    }

    public void playerHitObject(Player player) {
        if (board[player.x][player.y]) {
            player.kill();
        }
    }
    public void enemiesTryKillPlayers() {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < enemies.length; j++) {
                enemyTryKillPlayer(players[i], enemies[j]);
            }
        }
    }

    public void enemyTryKillPlayer(Player player1, Enemy enemy1) {
        if ((player1.x == enemy1.x) && (player1.y == enemy1.y)) {
            player1.kill();
        }
    }

    public void playerDraw(Player player1, Player player2) {
        boolean deadAlready = false;
        if ((player1.x == player2.x) && (player1.y == player2.y)) {
            if (player1.isDead() || player2.isDead()) {
                deadAlready = true;
            }
            player1.kill();
            player2.kill();
            if (isAllPlayersDead() && !deadAlready) {
                draw = true;
            }
        }
    }

    public void playersDraw() {
        for (int i = 0; i < players.length - 1; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (i != j) {
                    playerDraw(players[i], players[j]);
                }
            }
        }
    }

    public boolean isDraw() {
        return draw;
    }

    public void endGame() {
        GameOver = true;
    }
    public void endGame(boolean playerStillAlive) {
        System.out.println("The Winner is " + onlyOneAlive());
        GameOver = playerStillAlive;
    }

    private String onlyOneAlive() {
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isDead()) {
                return "Player " + (i+1);
            }
        }
        return "No players alive";
    }

    public boolean isGameOver() {
        return GameOver;
    }


    public boolean isOnlyOnePlayerAlive() {
        int counter = 0;
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isDead()) {
                counter++;
            }
        }
        if (counter == 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
