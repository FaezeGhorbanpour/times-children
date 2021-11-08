package GamePanel;

import GameController.Controller;
import GameEngine.Judge;
import GameEngine.JudgeAbstract;
import GameNetwork.Model;
import Test.MapReader;
import Test.MapReader2;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class DotaAllStars {

    public static int team;
    public static int map;

    private int enemy;
    private MainFrame gameFrame;

    boolean paused = false;

    Model model;
    EndPanel endPanel;

    public DotaAllStars(int map0,int team0,Model model0){
        map = map0;
        this.model=model0;
        team=team0;
        Judge judge = new Judge(team);
        Controller controller = new Controller();
        enemy = (team == JudgeAbstract.TEAM_SENTINEL)? JudgeAbstract.TEAM_SCOURGE:JudgeAbstract.TEAM_SENTINEL;

        if(map == 0)
        {
            MapReader m = new MapReader();
        m.init1();
        judge.loadMap(m.getColumns(), m.getRows(), m.getPath1(), m.getPath2(),
                m.getPath3(), m.getAncient1(), m.getAncient2(),
                m.getBarracks1(), m.getBarracks2(), m.getGoldMines());}
        else {
            MapReader2 m = new MapReader2();
            m.init1();
            judge.loadMap(m.getColumns(), m.getRows(), m.getPath1(), m.getPath2(),
                    m.getPath3(), m.getAncient1(), m.getAncient2(),
                    m.getBarracks1(), m.getBarracks2(), m.getGoldMines());
        }

        judge.setup();
        gameFrame=new MainFrame(judge,controller,team);
        controller.init(team,judge,this);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setBounds(0, 0, 1350, 700);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                    if(paused == false) {
                        judge.next50milis();
                        gameFrame.getMainPanel().repaint();
                        gameFrame.getInfoPanel().repaint();
                        if (gameOver(judge)) {
                            cancel();
                        }
                    }

            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 50;
        timer.scheduleAtFixedRate(task, delay,
                intevalPeriod);


        controller.start();

    }

    public boolean gameOver(Judge judge){
        if(!judge.getEngine().getAncients().get(team).isAlive()){
            endPanel = new EndPanel(team,1,judge);
            return true;
        }
        if(!judge.getEngine().getAncients().get(enemy).isAlive()){
            endPanel = new EndPanel(team,0,judge);
            return true;
        }
        return false;
    }




    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public MainFrame getGameFrame() {
        return gameFrame;
    }

    public Model getModel() {
        return model;
    }
}
