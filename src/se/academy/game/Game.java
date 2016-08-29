package se.academy.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016-08-17.
 */
public class Game {
    public boolean[][] board;
    private Player[] players;
    private Enemy[] enemies;
    private List<Wall> walls;
    private RuleBook rules;

    private SettingsParser settingsParser = new SettingsParser();

    public Game(int numberOfPlayers, int numberOfEnemies, Terminal terminal, RuleBook rules) throws IOException {
        this.players = new Player[numberOfPlayers];
        this.rules = rules;

        board = new boolean[terminal.getTerminalSize().getColumns()][terminal.getTerminalSize().getRows()];

        this.enemies = new Enemy[numberOfEnemies];
        for (int i = 0; i < numberOfEnemies; i++) {
            addEnemy(i);
        }


        for (int i = 0; i < players.length; i++) {
            addPlayer(i);
            keyAddPlayer(i);
            players[i].setApparence((char) ((int) 'O' + i));
        }
        addWalls();
        AddBoarders(); // needs to be terminated after tail and collision is modified
        addObstaclesToBoard();
    }

    // Boarders can be REMOVED, enhansed to walls instead.
    private void AddBoarders() {
        for (int i = 0; i < board[0].length; i++) {
            board[0][i] = true;
            board[board.length - 1][i] = true;
        }
        for (int i = 0; i < board.length; i++) {
            board[i][0] = true;
            board[i][board[0].length - 1] = true;
        }
    }

    private void addWalls() {
        walls = new ArrayList<>();


        for (int i = 0; i < board[0].length; i++) {
            Coordinates coordinatesFirstWall = new Coordinates(0, i);
            Wall firstWall = new Wall(coordinatesFirstWall);
            walls.add(firstWall);
            Coordinates coordinatesSecondWall = new Coordinates(board.length - 1, i);
            Wall secondWall = new Wall(coordinatesSecondWall);
            walls.add(secondWall);
        }
        for (int i = 0; i < board.length; i++) {
            Coordinates coordinatesFirstWall = new Coordinates(i, 0);
            Wall firstWall = new Wall(coordinatesFirstWall);
            walls.add(firstWall);
            Coordinates coordinatesSecondWall = new Coordinates(i, board[0].length - 1);
            System.out.println();
            Wall secondWall = new Wall(coordinatesSecondWall);
            walls.add(secondWall);
        }
    }

    public List<Wall> getWalls() {
        return walls;
    }

    //Board methods below
    public void addObstaclesToBoard() {
        this.addPlayerTailToBoard();
    }

    private void addPlayerTailToBoard() {
        for (int i = 0; i < players.length; i++) {
            board[players[i].getCoord().getX()][players[i].getCoord().getY()] = true;
        }
    }

    public Coordinates randomCoordinates() {
        Random rand = new Random();
        return new Coordinates(rand.nextInt(board.length - 5) + 2, rand.nextInt(board[0].length - 5) + 2);
    }
    //end of Board methods

    //Player methods below
    public Player[] getPlayers() {
        return players;
    }

    public void addPlayer(int playerNumber) {
        Random rand = new Random();
        if (playerNumber < this.players.length && playerNumber >= 0) {
            players[playerNumber] = new Player(randomCoordinates(), playerNumber);
        }
    }

    public Player getPlayer(int Number) {
        return players[Number];
    }

    public void keyAddPlayer(int playerNumber) {
        String playerName = players[playerNumber].getName();
        players[playerNumber].setKeyInputRight(settingsParser.getKeySettingsForPlayer(playerName, "Rightkey").charAt(0));
        players[playerNumber].setKeyInputLeft(settingsParser.getKeySettingsForPlayer(playerName, "Leftkey").charAt(0));
        //        players[playerNumber].setKeyInputRight(JOptionPane.showInputDialog("Välj höger för spelare " +
//                "(R,L,U,D för piltangenter)" + playerNumber).charAt(0));
//        players[playerNumber].setKeyInputLeft(JOptionPane.showInputDialog("Välj vänster för spelare" +
//                "(R,L,U,D för piltangenter)" + playerNumber).charAt(0));
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
                                    players[i].changeMomentum(-1);
                                    break;
                                case 1:
                                    players[i].changeMomentum(1);
                                    break;
                            }
                            keysPressed[i] = true;
                        }

                    }
                }
                System.out.println(key.getCharacter() + " " + key.getKind());
            }

        } while (1 > (Duration.between(tm, LocalTime.now()).getNano() / 300000000)); //Seconds before the game updates

    }
    //end of Player methods

    //Enemy methods below
    public void addEnemy(int enemyNumber) {
        Random rand = new Random();
        if (enemyNumber % 3 == 0) {
            enemies[enemyNumber] = new StupidEnemy(randomCoordinates());
            enemies[enemyNumber].setApparence('X');
        } else if (enemyNumber % 2 == 0){
            enemies[enemyNumber] = new SmartEnemy(randomCoordinates());
            enemies[enemyNumber].setApparence('Y');
        }
        else {
            enemies[enemyNumber] = new Wizard(randomCoordinates());
            enemies[enemyNumber].setApparence('W');
        }
    }

    public Enemy getEnemy(int enemyNumber) {
        return enemies[enemyNumber];
    }

    public Enemy[] getEnemies() {
        return enemies;
    }


    //end of Enemy methods

    //game help methods follow
    public boolean isGameOver() {
        return rules.isGameOver();
    }
    public boolean isGameDraw() {
        return rules.isDraw();
    }
    public String onlyOneAlive() {
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isDead()) {
                return "Player " + (i + 1);
            }
        }
        return "No players alive";
    }
    public boolean isAllPlayersDead() {
        for (Player p : players) {
            if (!p.isDead()) {
                return false;
            }
        }
        return true;

    }
    public void endGame(boolean playerStillAlive) {
        System.out.println("The Winner is " + onlyOneAlive());
        rules.endGame();
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
        } else {
            return false;
        }
    }
    public void gameCountdown() throws InterruptedException {
        gameCountdown(3);
    }

    public void gameCountdown(int countDownTimer) throws InterruptedException {
        for (int i = countDownTimer; i > 0; i--) {
            System.out.println("Game starts in: " + i);
            Thread.sleep((long) 1000);
        }
    }

    public void updateState() {

        rules.movePlayersByMomentum(this.players, this);
        rules.moveEnemiesTowardsPlayer(getPlayer(0), enemies);
        rules.moveEnemies(enemies, this);
        rules.enemiesTryKillPlayers(this.players, this.enemies);
        rules.playersDraw(this.players, this);
        rules.playersHitObject(this.players, this);
        if (players.length > 1 && isOnlyOnePlayerAlive()) {
            endGame(isOnlyOnePlayerAlive());
        } else if (isAllPlayersDead()) {
            rules.endGame();
        }
        addObstaclesToBoard();
    }
}
