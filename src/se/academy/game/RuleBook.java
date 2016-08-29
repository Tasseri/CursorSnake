package se.academy.game;

/**
 * Created by Administrator on 2016-08-29.
 */
public abstract class RuleBook {
    private boolean GameOver = false;
    private boolean draw = false;

    public void playersHitObject(Player[] players, Game game) {
        for (int i = 0; i < players.length; i++) {
            playerHitObject(players[i], game);
        }
    }

    public void playerHitObject(Player player, Game game) {
        if (game.board[player.getCoord().getX()][player.getCoord().getY()]) {
            player.kill();
        }
    }

    public void enemiesTryKillPlayers(Player[] players, Enemy[] enemies) {
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

    // move functions
    public void moveEnemiesTowardsPlayer(Player player, Enemy[] enemies) {
        for (Enemy e : enemies) {
            e.moveEnemyTowardsPlayer(player);
        }
    }
    public void moveEnemies(Enemy[] enemies, Game game) {
        for (Enemy e : enemies) {
            e.moveEnemy(game);
        }
    }

    private void movePlayer(int changeInX, int changeInY, Player player, Game game) {
        if (!((player.getCoord().getX() == 0 && changeInX < 0) || (player.getCoord().getX() == game.board.length && changeInX > 0))) {
            player.changeOneInX(changeInX);
        }
        if (!((player.getCoord().getY() == 0 && changeInY < 0) || (player.getCoord().getY() == game.board[0].length && changeInY > 0))) {
            player.changeOneInY(changeInY);
        }
    }
    public void movePlayersByMomentum(Player[] players, Game game) {
        for (int i = 0; i < players.length; i++) { //Applies the momentum and moves the player
            players[i].addToSize(players[i].getCoord().getX(), players[i].getCoord().getY());

            moveByMomentum(players[i], game);
        }
    }
    public void moveByMomentum(Player player, Game game) {
        if (player.isDead()) {
            //don't move
        } else if (player.getMomentumOfPlayer() == 'U') {
            movePlayer(0, -1, player, game);
        } else if (player.getMomentumOfPlayer() == 'D') {
            movePlayer(0, 1, player, game);
        } else if (player.getMomentumOfPlayer() == 'R') {
            movePlayer(1, 0, player, game);
        } else if (player.getMomentumOfPlayer() == 'L') {
            movePlayer(-1, 0, player, game);
        }

    }

    //end game functions
    public boolean playerDraw(Player player1, Player player2) {
        boolean isDraw = false;
        if ((player1.getCoord().getX() == player2.getCoord().getX()) &&
                (player1.getCoord().getY() == player2.getCoord().getY())) {
            if (player1.isDead() || player2.isDead()) {
                isDraw = false;
            }
            else {
                isDraw =  true;
            }
            player1.kill();
            player2.kill();
        }
        return isDraw;
    }
    public void drawGame() { draw = true;}
    public boolean isDraw() {
        return draw;
    }

    public void playersDraw(Player[] players, Game game) {
        for (int i = 0; i < players.length - 1; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (i != j) {
                   if (playerDraw(players[i], players[j]) && game.isAllPlayersDead()) {
                        drawGame();
                   }
                }
            }
        }
    }

    public void endGame() {
        GameOver = true;
    }

    public boolean isGameOver() {
        return GameOver;
    }
}
