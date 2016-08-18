package se.academy.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-17.
 */
public abstract class Entity {
    public int x, y;
    List<Coordinates> movements;

    public Entity() {
        this.x = 0;
        this.y = 0;
        movements = new LinkedList<>();
    }
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        movements = new LinkedList<>();
    }

    public void trackMovement(int x, int y) {
        movements.add(new Coordinates(x,y));
    }
    public int howManySquaresMoved() {
        return movements.size();
    }
}
