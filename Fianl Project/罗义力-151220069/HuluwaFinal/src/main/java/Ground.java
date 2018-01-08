
import java.util.ArrayList;

public class Ground {
    private final int width;
    private final int height;
    private int brothersNumber;
    private int monsterNumber;
    private Position[][] positions;
    private ArrayList<Creature> creatures;
    private Huluwa[] brothers;
    private Monster[] monsters;
    private Grandpa grandpa;
    private Snake snake;
    //private Scorpin scorpin;

    private static final String[] formationClassList = {"HeYi", "YanXing", "ChongE"};

    Ground() {
        this.width = 0;
        this.height = 0;
        brothersNumber = 0;
        monsterNumber = 0;
    }

    Ground(int width, int height){
        this.width = width;
        this.height = height;
        brothersNumber = 0;
        monsterNumber = 0;

        creatures  = new ArrayList<Creature>();
        positions = new Position[width][height];
        for(int i=0; i < width; i++){
            for(int j=0; j < height; j++){
                positions[i][j] = new Position(i, j);
            }
        }
    }

    public void init() {

        brothers = new Huluwa[7];
        Formation formation = new HeYi(
                positions[0][width/2], Direction.DOWN
        );
        for (int i = 0; i < brothers.length; i++) {
            brothers[i] = new Huluwa(COLOR.values()[i], SENIORITY.values()[i]);
            Position pos = formation.nextPos();
            if(pos.getX() >= 0 && pos.getX() < width && pos.getY()  >= 0 && pos.getY() < height)
                positions[pos.getX()][pos.getY()].setHolder(brothers[i]);
            creatures.add(brothers[i]);
        }
        grandpa = new Grandpa();
        creatures.add(grandpa);
        positions[0][0].setHolder(grandpa);
        this.brothersNumber = brothers.length + 1;

        monsters = new Monster[7];
        Formation formation1 = new ChongE(
                positions[height-1][width/3], Direction.DOWN
        );

        for(int i=0; i < monsters.length; i++) {
            if(i == monsters.length / 2) {
                monsters[i] = new Scorpin();
            }
            else {
                monsters[i] = new Monster();
            }
            Position pos = formation1.nextPos();
            if(pos.getX() >= 0 && pos.getX() < width && pos.getY()  >= 0 && pos.getY() < height)
                positions[pos.getX()][pos.getY()].setHolder(monsters[i]);
            creatures.add(monsters[i]);
        }
        snake = new Snake();
        creatures.add(snake);
        positions[height-1][0].setHolder(snake);
        this.monsterNumber = monsters.length + 1;

    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position[][] getPositions() {
        return positions;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public Huluwa[] getBrothers() {
        return brothers;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public int getBrothersNumber() {
        return brothersNumber;
    }

    public int getMonsterNumber() {
        return monsterNumber;
    }



    public void moveCreature(Creature creature, Position dst) {
        creature.getPosition().removeHolder();
        dst.setHolder(creature);
    }

    public void placeCreature(Creature creature, int x, int y) {
        if(x >=0 && x < width && y >= 0 && y < height){
            positions[x][y].setHolder(creature);
            creatures.add(creature);
            if(creature instanceof Huluwa) {
                brothersNumber ++;
            }
            else if(creature instanceof Monster) {
                monsterNumber ++;
            }
        }
    }

    public void killCreature(int x, int y) {
        if(x >=0 && x < width && y >= 0 && y < height) {
            Creature creature = positions[x][y].getHolder();
            if(creature != null)
                creature.kill();
        }
    }

    public void moveCreature(int srcx, int srcy, int dstx, int dsty) {
        if(srcx >=0 && srcx < width && srcy >= 0 && srcy < height
                && dstx >=0 && dstx < width && dsty >= 0 && dsty < height){
            positions[dstx][dsty].setHolder(positions[srcx][srcy].getHolder());
            positions[srcx][srcy].removeHolder();
        }
    }



    @Override
    public String toString() {
        String str = "";
        for(int i=0; i < width; i++){
            for(int j=0; j < height; j++){
                str += positions[i][j].toString();
            }
            str += "\r\n";
        }
        return str;
    }
}
