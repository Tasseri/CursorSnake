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
    //   private String[] playerList = {"1 Player", "2 Players", "3 Players", "4 Players"};
    private boolean GameOver = false;
    private boolean draw = false;
    private SettingsParser settingsParser = new SettingsParser();

    public Game(int numberOfPlayers, int numberOfEnemies, Terminal terminal) throws IOException {
        this.players = new Player[numberOfPlayers];

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

    public boolean isAllPlayersDead() {
        for (Player p : players) {
            if (!p.isDead()) {
                return false;
            }
        }
        return true;

    }

    private void movePlayer(int changeInX, int changeInY, Player player) {
        if (!((player.getCoord().getX() == 0 && changeInX < 0) || (player.getCoord().getX() == board.length && changeInX > 0))) {
            player.changeOneInX(changeInX);
        }
        if (!((player.getCoord().getY() == 0 && changeInY < 0) || (player.getCoord().getY() == board[0].length && changeInY > 0))) {
            player.changeOneInY(changeInY);
        }
    }

    public void movePlayersByMomentum() {
        for (int i = 0; i < players.length; i++) { //Applies the momentum and moves the player
            players[i].addToSize(players[i].getCoord().getX(), players[i].getCoord().getY());

            moveByMomentum(players[i]);
        }
    }

    public void moveByMomentum(Player player) {
        if (player.isDead()) {
            //don't move
        } else if (player.getMomentumOfPlayer() == 'U') {
            movePlayer(0, -1, player);
        } else if (player.getMomentumOfPlayer() == 'D') {
            movePlayer(0, 1, player);
        } else if (player.getMomentumOfPlayer() == 'R') {
            movePlayer(1, 0, player);
        } else if (player.getMomentumOfPlayer() == 'L') {
            movePlayer(-1, 0, player);
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

    public void moveEnemiesTowardsPlayer(Player player) {
        for (Enemy e : enemies) {
            e.moveEnemyTowardsPlayer(player);
        }
    }
    public void moveEnemies() {
        for (Enemy e : enemies) {
            e.moveEnemy(this);
        }
    }
    //end of Enemy methods

    //game logic methods follow
    public void gameCountdown() throws InterruptedException {
        gameCountdown(3);
    }

    public void gameCountdown(int countDownTimer) throws InterruptedException {
        for (int i = countDownTimer; i > 0; i--) {
            System.out.println("Game starts in: " + i);
            Thread.sleep((long) 1000);
        }
    }

    public void playersHitObject() {
        for (int i = 0; i < players.length; i++) {
            playerHitObject(players[i]);
        }
    }

    public void playerHitObject(Player player) {
        if (board[player.getCoord().getX()][player.getCoord().getY()]) {
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
        if (player1.getCoord().equals(enemy1.getCoord())) {
            player1.kill();
        }
        else if (enemy1 instanceof Wizard) {
            for (int i = 0; i < enemy1.nearbyCoordinates().length; i++) {
                if (player1.getCoord().equals( enemy1.nearbyCoordinates()[i])) {
                    player1.kill();
                }
            }
        }
    }

    public void playerDraw(Player player1, Player player2) {
        boolean deadAlready = false;
        if ((player1.getCoord().getX() == player2.getCoord().getX()) &&
                (player1.getCoord().getY() == player2.getCoord().getY())) {
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
                return "Player " + (i + 1);
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
        } else {
            return false;
        }
    }

    public void updateState() {

        movePlayersByMomentum();
        moveEnemiesTowardsPlayer(getPlayer(0));
        moveEnemies();
        enemiesTryKillPlayers();
        playersDraw();
        playersHitObject();
        if (players.length > 1 && isOnlyOnePlayerAlive()) {
            endGame(isOnlyOnePlayerAlive());
        } else if (isAllPlayersDead()) {
            endGame();
        }
        addObstaclesToBoard();
    }
}
