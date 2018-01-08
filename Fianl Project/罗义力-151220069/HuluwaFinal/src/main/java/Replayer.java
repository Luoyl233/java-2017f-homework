import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Replayer {
    private GameView gameView;

    Replayer(GameView gameView) {
        this.gameView = gameView;
    }

    public synchronized void replay() {
        FileDialog dlg ;
        String fileName = null;
        BufferedReader reader;
        String str;
        String [] buf;
        dlg = new FileDialog(new Frame(), "选择记录", FileDialog.LOAD);
        dlg.setVisible(true);
        fileName = dlg.getFile();
        if(fileName == null ) {
            return;
        }
        System.out.println(fileName);
        try {
            reader = new BufferedReader(new FileReader(Constant.RECORD_PATH + fileName));
            str = reader.readLine();
            int width = 0, height = 0;
            if(str != null ){
                buf = str.split(" |，");
                width = Integer.parseInt(buf[0]);
                height = Integer.parseInt(buf[1]);
            }
            System.out.println(width + " " + height);
            //初始化ground
            Ground ground = new Ground(width, height);

            for(int i=0; i < height; i++) {
                str = reader.readLine();
                for(int j=0; j < width; j++) {
                    char c = str.charAt(j);
                    switch (c) {
                        case '口':
                            break;
                        case '一':
                            ground.placeCreature(new Huluwa(COLOR.values()[1], SENIORITY.values()[1]), i, j);
                            break;
                        case '二':
                            ground.placeCreature(new Huluwa(COLOR.values()[2], SENIORITY.values()[2]), i, j);
                            break;
                        case '三':
                            ground.placeCreature(new Huluwa(COLOR.values()[3], SENIORITY.values()[3]), i, j);
                            break;
                        case '四':
                            ground.placeCreature(new Huluwa(COLOR.values()[4], SENIORITY.values()[4]), i, j);
                            break;
                        case '五':
                            ground.placeCreature(new Huluwa(COLOR.values()[5], SENIORITY.values()[5]), i, j);
                            break;
                        case '六':
                            ground.placeCreature(new Huluwa(COLOR.values()[6], SENIORITY.values()[6]), i, j);
                            break;
                        case '七':
                            ground.placeCreature(new Huluwa(COLOR.values()[0], SENIORITY.values()[0]), i, j);
                            break;
                        case '怪':
                            ground.placeCreature(new Monster(), i, j);
                            break;
                        case '蛇':
                            ground.placeCreature(new Snake(), i, j);
                            break;
                        case '蝎':
                            ground.placeCreature(new Scorpin(), i, j);
                            break;
                        case '爷':
                            ground.placeCreature(new Grandpa(), i, j);
                            break;
                    }
                }
            }
            //开始回放
            this.gameView.setGround(ground);
            while((str = reader.readLine()) != null) {
                buf = str.split(" |,|:");
                if(buf[0].equals("move")) {
                    int srcx, srcy, dstx, dsty;
                    srcx = Integer.parseInt(buf[1]);
                    srcy = Integer.parseInt(buf[2]);
                    dstx = Integer.parseInt(buf[3]);
                    dsty = Integer.parseInt(buf[4]);
                    ground.moveCreature(srcx, srcy, dstx, dsty);
                } else if (buf[0].equals("kill")) {
                    int x, y;
                    x = Integer.parseInt(buf[1]);
                    y = Integer.parseInt(buf[2]);
                    ground.killCreature(x, y);
                }
                this.gameView.repaint();
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
