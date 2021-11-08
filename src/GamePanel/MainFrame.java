package GamePanel;

import GameController.Controller;
import GameEngine.Judge;
import GameEngine.JudgeAbstract;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private InfoPanel infoPanel;
    private MainPanel mainPanel;

    int team;
    String teamName;

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public InfoPanel getInfoPanel() {

        return infoPanel;
    }

    public MainFrame(Judge judge, Controller controller,int team){
        this.team = team;
        teamName = (team == JudgeAbstract.TEAM_SENTINEL)? "Sentinel" : " Scourges" ;
        setTitle("Dota All Stars \" " + teamName +" \"");
        setLayout( new BorderLayout());

        infoPanel = new InfoPanel(this.team,judge,controller);
        mainPanel = new MainPanel(judge,controller,this.team);

        addKeyListener(controller);
        setFocusable(true);
        requestFocusInWindow();

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(infoPanel,BorderLayout.LINE_START);

    }
}
