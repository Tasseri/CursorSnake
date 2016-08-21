package se.academy.game;

/**
 * Created by Administrator on 2016-08-21.
 */
public class Wizard extends Enemy {
    public Wizard(Coordinates coord) {
        super(coord);
    }
    public void moveEnemyTowardsPlayer(Player player) {

    }
    public void moveEnemy(Game game) {
        this.setCoord(game.randomCoordinates());
    }

}
