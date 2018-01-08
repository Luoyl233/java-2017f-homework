

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Monster extends Creature {

    Monster() {
        camp = Camp.BAD;
        Random rnd = new Random();
        lucky = rnd.nextInt(50) + 50;

        URL loc = this.getClass().getClassLoader().getResource("monster.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public String toString() {
        return "怪";
    }

    @Override
    public void kill() {
        super.kill();
        URL loc = this.getClass().getClassLoader().getResource("dead-monster.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
