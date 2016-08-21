package se.academy.game;

/**
 * Created by Administrator on 2016-08-17.
 */
public abstract class Enemy extends Entity {
    private char Apparence;

    protected Enemy() {
    }

    public Enemy(int x, int y) {
        super(x, y);
    }

    public Enemy(Coordinates coord) {
        super(coord);
    }

    public abstract void moveEnemyTowardsPlayer(Player player);

    public abstract void moveEnemy(Game game);
}
