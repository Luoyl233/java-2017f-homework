import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Recorder {
    private BufferedWriter writer;
    private String recordFileName;

    Recorder() {
        try {
            for(int i=0; ; i++) {
                recordFileName = Constant.RECORD_PATH + "record-" + i + ".txt";
                File file = new File(recordFileName);
                if(!file.exists())
                    break;;
            }
            writer = new BufferedWriter(new FileWriter(recordFileName, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeGround(String str, int width, int height) {
        try {
            writer.write(width + " " + height + "\r\n");
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeMove(int xFrom, int yFrom, int xTo, int yTo) {
        try {
            writer.write("move:" + xFrom + " " + yFrom + "," + xTo + " " + yTo + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeKill(int x, int y) {
        try {
            writer.write("kill:" + x + " " + y + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
