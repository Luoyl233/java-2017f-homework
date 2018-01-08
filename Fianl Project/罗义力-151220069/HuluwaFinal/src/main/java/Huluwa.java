

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

enum COLOR {
    赤, 橙, 黄, 绿, 青, 蓝, 紫
}

enum SENIORITY {
    一, 二, 三, 四, 五, 六, 七
}



public class Huluwa extends Creature {
    private COLOR color;
    private SENIORITY seniority;


    Huluwa(COLOR color, SENIORITY seniority){
        this.color = color;
        this.seniority = seniority;
        camp = Camp.GOOD;
        Random rnd = new Random();
        lucky = rnd.nextInt(60) + 40;
        step = 1;

        URL loc = this.getClass().getClassLoader().getResource("player.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();

        this.setImage(image);
    }

    @Override
    public String toString() {
        return this.seniority.toString();
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
