package se.academy.game;

/**
 * Created by Administrator on 2016-08-17.
 */
public class Enemy extends Entity {

    public Enemy(int x, int y) {
        super(x, y);
    }

    public void moveEnemyTowardsPlayer(Player player) {
        if (this.getCoord().getX() > player.getCoord().getX()) {
            changeOneInX(-1);
        }
        else if (this.getCoord().getX() < player.getCoord().getX()) {
            changeOneInX(1);
        }
        else if (this.getCoord().getY() > player.getCoord().getY() ) {
            changeOneInY(-1);
        }
        else if (this.getCoord().getY() < player.getCoord().getY()) {
            changeOneInY(1);
        }

    }
}
