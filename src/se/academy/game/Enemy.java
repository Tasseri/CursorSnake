package se.academy.game;

/**
 * Created by Administrator on 2016-08-17.
 */
public class Enemy extends Entity {

    public Enemy(int x, int y) {
        super(x, y);
    }

    public void moveEnemyTowardsPlayer(Player player) {
        if (this.x > player.x) {
            this.x--;
        }
        else if (this.x < player.x) {
            this.x++;
        }
        else if (this.y > player.y ) {
            this.y--;
        }
        else if (this.y < player.y) {
            this.y++;
        }

    }
}
