

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

enum Camp {
    GOOD,
    BAD,
    NONE
}

public abstract class Creature implements Runnable{
    protected Position position;
    protected int step;
    protected Image image;
    protected Camp camp;
    protected boolean alive;
    protected int lucky;

   // public abstract  void report();
    Creature(){
        step = 1;
        alive = true;
        camp = Camp.NONE;
        lucky = 1;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getLucky() {
        return lucky;
    }

    public void kill() {
        if(alive == true) {
            alive = false;
        }
    }

    public void move(){
        Direction direct = searchPath();
        if(!GameController.getInstance().makeStep(this, direct, step)){
            for(int i = 0; i < 4; i++){
                Random rnd = new Random();
                int r = rnd.nextInt(4);
                direct = Direction.values()[(i + r)%4];
                if(GameController.getInstance().makeStep(this, direct, step)) {
                    //合法的一步
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "c";
    }


    public void run() {
        while(!Thread.interrupted() && alive){
            //如果敌人全部死掉了
            if(camp == Camp.GOOD && GameController.getArmyNumber() == 0 || camp == Camp.BAD && GameController.getBrothersNumber() == 0)
                return;
            if(GameController.getInstance().getGameState() == GameState.OVER)
                return;

            //移动
            move();



            //检查周围有没有敌人
            for(Direction direction: Direction.values()) {
                if(GameController.getInstance().findAndAttack(this, direction)) {
                    //合法的一步
                    break;
                }
            }


            //备战休息
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    Direction searchPath() {
        ArrayList<Creature> creatures = GameController.getInstance().getCreatures();
        int distance = 0x7fffffff;
        Creature dst = null;
        for(Creature creature: creatures) {
            if(creature.camp != this.camp && creature.alive) {
                int tmp = Math.abs(creature.getPosition().getX() - position.getX())
                        + Math.abs(creature.getPosition().getY() - position.getY());
                if(tmp < distance) {
                    distance = tmp;
                    dst = creature;
                }
            }
        }
        if(dst != null) {
            if(dst.getPosition().getX() > position.getX())
                return Direction.RIGHT;
            else if(dst.getPosition().getX() < position.getX())
                return Direction.LEFT;
            if(dst.getPosition().getY() > position.getY())
                return Direction.UP;
            else if(dst.getPosition().getY() < position.getY())
                return Direction.DOWN;
        }
        return Direction.DOWN;
    }

}