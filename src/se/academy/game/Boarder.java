package se.academy.game;

/**
 * Created by Administrator on 2016-08-24.
 */
public final class Boarder extends Wall {
    Boarder(Coordinates boardmin, Coordinates boardmax) {
        for (int i = boardmin.getY(); i < boardmax.getY(); i++) {
            for (int j = boardmin.getX(); j < boardmax.getX(); j++) {
                if (boardmin.getY() == i) {
                    addToSize(j, i);
                }
                else if (boardmax.getY() == i) {
                    addToSize(j, i);
                }
                else if (boardmin.getX() == j) {
                    addToSize(j, i);
                }
                else if (boardmax.getY() == j) {
                    addToSize(j, i);
                }
            }
        }

    }
}
