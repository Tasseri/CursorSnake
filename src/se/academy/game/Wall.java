package se.academy.game;

/**
 * Created by mohed on 2016-08-20.
 */
public class Wall extends Entity {

    public Wall() {    }

    public Wall(Coordinates coordinates) {
        this.setApparence('\u2588');
        this.setCoord(coordinates);
    }
}
