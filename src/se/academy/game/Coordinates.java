package se.academy.game;

import java.util.Arrays;

/**
 * Created by Administrator on 2016-08-18.
 */
public class Coordinates {
    private int[] coord = new int[2];
    public Coordinates(int x, int y) {
        coord[0] = x;
        coord[1] = y;
    }
    public int[] getCoordinates(){
        return coord;
    }
    public int getX() {
        return coord[0];
    }
    public int getY() {
        return coord[1];
    }

    public void setCoordinates(int[] coord) {
        this.coord = coord;
    }
    public void setCoordinates(int x, int y) {
        setX(x);
        setY(y);
    }
    public void setX(int x) {
        this.coord[0] = x;
    }
    public void setY(int y) {
        this.coord[1] = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Arrays.equals(coord, that.coord);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coord);
    }

    public double distance (Coordinates coord) {
        return Math.sqrt(Math.pow(coord.getX() - this.getX(), 2) + Math.pow(coord.getY() - this.getY(), 2));
    }
    public boolean isDistanceInXlessThanInY(Coordinates coord) {
        if (Math.abs(coord.getX() - this.getX()) < Math.abs(coord.getY() - this.getY())) {
            return true;
        }
        return false;
    }
    public Coordinates getRelativeCoord(int x, int y) {
        return new Coordinates(getRelativeXCoord(x), getRelativeYCoord(y));
    }
    public int getRelativeXCoord(int x) {
        return this.getX() - x;
    }
    public int getRelativeYCoord(int y) {
        return this.getY() - y;
    }
}
