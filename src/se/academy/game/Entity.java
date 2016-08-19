package se.academy.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-17.
 */
public abstract class Entity {
    private Coordinates coord;
    private List<Coordinates> movements;
    private char Apparence = 'N';

    public Entity() {
        coord = new Coordinates(0,0);
        movements = new LinkedList<>(); //arraydeck
    }
    public Entity(int x, int y) {
        coord = new Coordinates(x,y);
        movements = new LinkedList<>();
    }
    public Entity(Coordinates coord) {
        this.coord = coord;
        movements = new LinkedList<>();
    }

    public Coordinates getCoord() {
        return coord;
    }
    public void changeOneInX (int change) {
        if (change > 0) {
            addOneToX();
        }
        else if (change < 0){
            removeOneOffX();
        }
    }
    public void changeOneInY (int change) {
        if (change > 0) {
            addOneToY();
        }
        else if (change < 0) {
            removeOneOffY();
        }
    }
    public void addOneToX() {
        coord.setX(coord.getX() + 1);
    }
    public void removeOneOffX() {
        coord.setX(coord.getX() - 1);
    }
    public void addOneToY() {
        coord.setY(coord.getY() + 1);
    }
    public void removeOneOffY() {
        coord.setY(coord.getY() - 1);
    }

    public void trackMovement(int x, int y) {
        movements.add(new Coordinates(x,y));
    }
    public int howManySquaresMoved() {
        return movements.size();
    }

    public char getApparence() {
        return Apparence;
    }

    public void setApparence(char apparence) {
        Apparence = apparence;
    }
}
