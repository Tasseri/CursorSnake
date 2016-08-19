package se.academy.game;

/**
 * Created by Administrator on 2016-08-16.
 */
public class Player extends Entity {
    private char[] keyInputs = {'R', 'L'};
    private char momentum = 'U';
    private boolean dead = false;

    //enum ;

    public Player(int x, int y) {
        super(x, y);
    }
    public Player(Coordinates coord) {
        super(coord);
    }


    public char getKeyInput(int index) {
        return keyInputs[index];
    }

    public void setKeyInputLeft(char c) {
        keyInputs[1] = c;
    }

    public void setKeyInputRight(char c) {
        keyInputs[0] = c;
    }

    public char getMomentumOfPlayer() {
        return momentum;
    }

    public void changeMomentum(int direction) {
        if ((momentum == 'R' && direction == -1) || (momentum == 'L' && direction == 1)) {
            momentum = 'U';
        } else if ((momentum == 'U' && direction == 1) || (momentum == 'D' && direction == -1)) {
            momentum = 'R';
        } else if ((momentum == 'U' && direction == -1) || (momentum == 'D' && direction == 1)) {
            momentum = 'L';
        } else if ((momentum == 'L' && direction == -1) || (momentum == 'R' && direction == 1)) {
            momentum = 'D';
        }
    }

    public void kill() {
        dead = true;
    }
    public boolean isDead() {
        return dead;
    }
}
