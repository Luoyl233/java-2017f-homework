

public class YanXing implements Formation{
    private Position nextPos;
    private Direction direction;
    int cnt;


    public Position nextPos() {
        Position current = new Position(nextPos.getX(), nextPos.getY());

        cnt++;
        switch (direction) {
            case UP:
                nextPos.setPosition(current.getX() + 1, current.getY() - 1);
                break;
            case DOWN:
                nextPos.setPosition(current.getX() - 1, current.getY() + 1);
                break;
            case LEFT:
                nextPos.setPosition(current.getX() + 1, current.getY() + 1);
                break;
            case RIGHT:
                nextPos.setPosition(current.getX() - 1, current.getY() - 1);
                break;
        }


        return current;
    }

    YanXing() {
        this.direction = Direction.RIGHT;
        nextPos = new Position(0, 0);
    }

    YanXing(Position first, Direction dic) {
        nextPos = new Position(first.getX(), first.getY());
        this.direction = dic;
        this.cnt = 0;
    }
}

