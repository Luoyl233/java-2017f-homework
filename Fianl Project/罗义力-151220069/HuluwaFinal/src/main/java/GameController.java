

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

enum GameState {
    INIT,
    READY,
    WAIT,
    RUNNING,
    OVER,
    REPLAY
}

public class GameController extends KeyAdapter {
    private static Ground ground;
    private static GameView gameView;
    private static GameController gameController;
    private GameState gameState;

    private final String recordPath = "record/";
    private Recorder recorder;

    private static int brothersNumber;
    private static int armyNumber;

    private GameController() {
        gameState = GameState.INIT;
        brothersNumber = 0;
        armyNumber = 0;

    }

    public static GameController getInstance() {
        if(gameController == null){
            gameController = new GameController();
        }
        return gameController;
    }

    public void init(GameView gameView) {
        this.gameView = gameView;
        gameState = GameState.READY;
    }

    public void replay() {
        gameState = GameState.REPLAY;
        new Replayer(this.gameView).replay();
        gameState = GameState.READY;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_SPACE:
                if(gameState == GameState.OVER || gameState == GameState.READY || gameState == GameState.INIT) {
                    this.gameStart();
                }
                break;
            case KeyEvent.VK_S:
                break;
            case KeyEvent.VK_L:
                if(gameState == GameState.OVER || gameState == GameState.READY || gameState == GameState.INIT) {
                    new Thread(new Runnable() {

                        public void run() {
                            replay();
                        }
                    }).start();

                }

                break;
                default:
        }


    }

    public void gameStart() {
        ground = new Ground(30, 30);
        ground.init();
        gameView.setGround(ground);

        recorder = new Recorder();

        this.brothersNumber = ground.getBrothers().length;
        this.armyNumber = ground.getMonsters().length;
        recorder.writeGround(ground.toString(), ground.getWidth(), ground.getHeight());

        gameState = GameState.RUNNING;
        ArrayList<Creature> creatures = ground.getCreatures();
        for(Creature creature: creatures) {
            new Thread(creature).start();
        }
/*
        new Thread(new Runnable() {
            public void run() {
                while(armyNumber !=0 && brothersNumber != 0) {
                    Thread.yield();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        */
    }

    public ArrayList<Creature> getCreatures() {
        return ground.getCreatures();
    }

    public Position[][] getPositions() {
        return ground.getPositions();
    }

    public static int getArmyNumber() {
        return armyNumber;
    }

    public static int getBrothersNumber() {
        return brothersNumber;
    }

    public GameState getGameState() {
        return gameState;
    }

    public  boolean makeStep(Creature creature, Direction direction, int step) {
        Position position = creature.getPosition();
        int x = position.getX();
        int y = position.getY();

        switch (direction) {
            case UP:
                y += step;
                break;
            case DOWN:
                y -= step;
                break;
            case LEFT:
                x -= step;
                break;
            case RIGHT:
                x += step;
                break;
        }


        if(x < 0 || y < 0 || x >= ground.getWidth() || y >= ground.getHeight()) {
            //x或者y超出范围
            return false;
        }
        //访问目标位置前，先锁住
        synchronized(ground.getPositions()[x][y]) {
            if (ground.getPositions()[x][y].getHolder() != null ) {
                //该处已经有生物
                return false;
            }
            else {
                //要改变生物现在位置，所以先锁住
                synchronized (creature.getPosition()) {
                    recorder.writeMove(creature.getPosition().getX(), creature.getPosition().getY(), x, y);
                    ground.moveCreature(creature, ground.getPositions()[x][y]);
                }
                    gameView.repaint();

                return true;
            }
        }
    }

    public boolean findAndAttack(Creature creature, Direction direction) {
        Position position = creature.getPosition();
        int x = position.getX();
        int y = position.getY();

        switch (direction) {
            case UP:
                y += 1;
                break;
            case DOWN:
                y -= 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }

        if(x < 0 || y < 0 || x >= ground.getWidth() || y >= ground.getHeight()) {
            //x或者y超出范围
            return false;
        }

        synchronized (ground.getPositions()[x][y]) {
            Creature creature1 = ground.getPositions()[x][y].getHolder();
            if(creature1 != null && creature1.alive && creature1.camp != creature.camp) {
                 synchronized(creature.getPosition()){
                     Random rnd = new Random();
                     if(rnd.nextInt(creature.getLucky()) > rnd.nextInt(creature1.getLucky())) {
                         if(creature1.camp == Camp.BAD)
                             armyNumber --;
                         else
                             brothersNumber --;
                         recorder.writeKill(creature1.getPosition().getX(), creature1.getPosition().getY());
                         creature1.kill();
                     }
                     else {
                         if(creature.camp == Camp.BAD)
                             armyNumber --;
                         else
                             brothersNumber --;
                         recorder.writeKill(creature.getPosition().getX(), creature.getPosition().getY());
                         creature.kill();
                     }
                 }
                 gameView.repaint();

                 if(armyNumber == 0 || brothersNumber == 0) {
                    gameState = GameState.OVER;
                    recorder.close();
                 }
                 return true;
            }
        }

        return false;
    }

}
