

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class GameView extends JPanel{
    private Ground ground;

    GameView() {
    }

    GameView(Ground ground) {
        this.ground = ground;
        addKeyListener(GameController.getInstance());
        setFocusable(true);
    }


    public void setGround(Ground ground) {
        this.ground = ground;
    }


    public void buildWorld(Graphics g) {
        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, ground.getWidth(), ground.getHeight());

        Position[][] positions = ground.getPositions();
        URL loc = this.getClass().getClassLoader().getResource("tile.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        for(int i=0; i < ground.getWidth(); i++){
            for(int j=0; j < ground.getHeight(); j++){
                g.drawImage(image, i * 20 + 2, j * 20 + 2 , this);
            }
        }

        ArrayList<Creature> creatures = ground.getCreatures();
        for(Creature creature: creatures){
            g.drawImage(creature.getImage(), creature.getPosition().getX() * 20 + 2, creature.getPosition().getY() * 20 + 2, this);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.buildWorld(g);
     //   this.display();
    }
}


