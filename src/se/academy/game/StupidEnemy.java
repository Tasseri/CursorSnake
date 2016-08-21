package se.academy.game;

import java.util.Random;

/**
 * Created by Administrator on 2016-08-19.
 */
public class StupidEnemy extends Enemy {
    public StupidEnemy(Coordinates coord) {
        super(coord);
    }
    public void moveEnemyTowardsPlayer(Player player) {

    }
    public void moveEnemy(Game game) {
        Random rand = new Random();
        int r = rand.nextInt(10);
        if (r > 7) {
            changeOneInX(1);
        }
        else if (r > 5) {
            changeOneInY(-1);
        }
        else if (r > 3) {
            changeOneInX(-1);
        }
        else if (r > 1) {
            changeOneInY(1);
        }
    }
}
