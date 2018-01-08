import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Grandpa extends Creature{

    Grandpa() {
        camp = Camp.GOOD;
        Random rnd = new Random();
        lucky = rnd.nextInt(50) + 50;
        step = 1;

        URL loc = this.getClass().getClassLoader().getResource("player.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();

        this.setImage(image);
    }

    @Override
    public String toString() {
        return "爷";
    }

    @Override
    public void kill() {
        super.kill();
        URL loc = this.getClass().getClassLoader().getResource("dead-player.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
