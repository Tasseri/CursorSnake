package se.academy.game;

/**
 * Created by Administrator on 2016-08-16.
 */
public class Player extends Entity {
    private char[] keyInputs = {'R', 'L'};
    private char momentum = 'U';
    private boolean dead = false;

    Player(int x, int y) {
        super(x, y);
    }

    private void movePlayer(int changeInX, int changeInY) {
        if (!((this.x == 0 && changeInX < 0) || (this.x == 20 && changeInX > 0))) {
            this.x += changeInX;
        }
        if (!((this.y == 0 && changeInY < 0) || (this.y == 20 && changeInY > 0))) {
            this.y += changeInY;
        }
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

    public void moveByMomentum() {
        if (this.dead) {
            //don't move
        } else if (momentum == 'U') {
            movePlayer(0, -1);
        } else if (momentum == 'D') {
            movePlayer(0, 1);
        } else if (momentum == 'R') {
            movePlayer(1, 0);
        } else if (momentum == 'L') {
            movePlayer(-1, 0);
        }

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
