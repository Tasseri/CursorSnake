package se.academy.game;

/**
 * Created by Administrator on 2016-08-16.
 */
public class Player extends Entity {
    private char[] keyInputs = {'L', 'R'};
    //    private char momentum = 'U';
    private boolean dead = false;
    private String name = "Player 1";
    private Direction momentum = Direction.U;

    private enum Direction {
        U('U'), D('D'), R('R'), L('L');

        public char asChar() {
            return asChar;
        }

        private final char asChar;

        Direction(char asChar) {
            this.asChar = asChar;
        }
    }

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
        keyInputs[0] = c;
    }

    public void setKeyInputRight(char c) {
        keyInputs[1] = c;
    }

    public char getMomentumOfPlayer() {
        return momentum.asChar();
    }

    public void changeMomentum(int direction) {
        if ((momentum.asChar() == 'R' && direction == -1) || (momentum.asChar() == 'L' && direction == 1)) {
            this.momentum = Direction.U;
        } else if ((momentum.asChar() == 'U' && direction == 1) || (momentum.asChar() == 'D' && direction == -1)) {
            this.momentum = Direction.R;
        } else if ((momentum.asChar() == 'U' && direction == -1) || (momentum.asChar() == 'D' && direction == 1)) {
            this.momentum = Direction.L;
        } else if ((momentum.asChar() == 'L' && direction == -1) || (momentum.asChar() == 'R' && direction == 1)) {
            this.momentum = Direction.D;
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
