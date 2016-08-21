package se.academy.game;

/**
 * Created by Administrator on 2016-08-16.
 */
public class Player extends Entity {
    private char[] keyInputs = {'R', 'L'};
    private char momentum = 'U';
    private boolean dead = false;
    private String name = "Player 1";

    //enum ;
    // om vi berstämmer oss för nya funktioner så tar vi bort dessa konstructorer
    // för stundern så overloadar jag bara dom
    public Player(int x, int y) {
        super(x, y);
    }

    public Player(Coordinates coord) {
        super(coord);
    }
    // gamla konstruktorer ovan

    public Player(int x, int y, int number) {
        super(x, y);
        name = "Player " + (number + 1);
    }

    public Player(Coordinates coord, int number) {
        super(coord);
        name = "Player " + (number + 1);
    }

    public char getKeyInput(int index) {
        return keyInputs[index];
    }

    // ordningen känns märklig, tycker[left, right] känns mer naturligt
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

    public String getName() {
        return name;
    }

}
