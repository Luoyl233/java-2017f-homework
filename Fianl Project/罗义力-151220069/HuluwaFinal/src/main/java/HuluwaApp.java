

import javax.swing.*;

public class HuluwaApp extends JFrame {
    private static HuluwaApp instance;
    private GameController gameController;
    private GameView gameView;
    private Ground ground;

    private HuluwaApp() {
        ground = new Ground(30, 30);
        gameView = new GameView(ground);
        gameController = GameController.getInstance();
        gameController.init(gameView);

        initUI();
    }

    public static HuluwaApp getInstance() {
        if(instance == null){
            instance = new HuluwaApp();
        }
        return instance;
    }

    public void initUI() {
        add(gameView);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constant.WIDTH * 20 + 30,
                Constant.HEIGHT * 22 + 30);
        setLocationRelativeTo(null);
        setTitle("Huluwa Game");
    }

}
