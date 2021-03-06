package se.academy.game;

import java.util.Random;

/**
 * Created by Administrator on 2016-08-19.
 */
public class SmartEnemy extends Enemy {
    public SmartEnemy(Coordinates coord) {
        super(coord);
    }

    public void moveEnemyTowardsPlayer(Player player) {
        if (!this.getCoord().isDistanceInXlessThanInY(player.getCoord())) {
            Random rnd = new Random();
            int temp = rnd.nextInt(10);
            if (8 > rnd.nextInt(10)) {
                if (this.getCoord().getX() > player.getCoord().getX()) {

                    changeOneInX(-1);
                } else {
                    changeOneInX(1);
                }
            } else {
                changeOneInY(-1);
            }
        } else {
            if (this.getCoord().getY() > player.getCoord().getY()) {
                changeOneInY(-1);
            } else {
                changeOneInY(1);
            }
        }
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
