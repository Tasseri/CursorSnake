package se.academy.game;

/**
 * Created by Administrator on 2016-08-24.
 */
public final class Boarder extends Wall {
    Boarder(Coordinates boardMin, Coordinates boardMax) {
        for (int i = boardMin.getY(); i < boardMax.getY(); i++) {
            for (int j = boardMin.getX(); j < boardMax.getX(); j++) {
                if (boardMin.getY() == i) {
                    addToSize(j, i);
                }
                else if (boardMax.getY() == i) {
                    addToSize(j, i);
                }
                else if (boardMin.getX() == j) {
                    addToSize(j, i);
                }
                else if (boardMax.getY() == j) {
                    addToSize(j, i);
                }
            }
        }
    }
}
