package se.academy.game;

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
}
