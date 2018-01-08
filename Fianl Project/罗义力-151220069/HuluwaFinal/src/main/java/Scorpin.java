import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Scorpin extends Monster {

    Scorpin() {
        camp = Camp.BAD;
        Random rnd = new Random();
        lucky = rnd.nextInt(50) + 50;
        step = 1;

        URL loc = this.getClass().getClassLoader().getResource("monster.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();

        this.setImage(image);
    }

    @Override
    public String toString() {
        return "蝎";
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
