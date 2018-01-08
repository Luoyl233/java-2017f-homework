

public class HeYi implements Formation{
    private Position firstPos;
    private Direction direction;
    private int cnt;


    public Position nextPos() {
        Position current = new Position(firstPos.getX(), firstPos.getY());

        cnt++;
        int x = -cnt/2;
        int y = cnt/2;
        if((cnt-1)%2 == 1)
            y = -y;

        switch (direction) {
            case UP:
                current.setPosition(current.getX() + x, current.getY() + y);
                break;
            case DOWN:
                current.setPosition(current.getX() - x, current.getY() - y);
                break;
            case LEFT:
                current.setPosition(current.getX() + y, current.getY() + x);
                break;
            case RIGHT:
                current.setPosition(current.getX() - y, current.getY() - x);
                break;
        }

        return current;
    }

    HeYi(Position first, Direction dic) {
        this.firstPos = new Position(first.getX(), first.getY());
        this.direction = dic;
        this.cnt = 0;
    }

    HeYi() {
        this.firstPos = new Position(0, 0);
        this.direction = Direction.RIGHT;
        this.cnt = 0;
    }
}
