package GamePanel;

import GameEngine.Judge;
import GameNetwork.Client;
import GameNetwork.Model;
import GameNetwork.Server;

import javax.swing.*;

public class StartFrame extends JFrame{
    public static void main(String[] args) {
         Model model=null;
         int team=0;
        Object[] options = new String[2];
        options[0] = "client";
        options[1] = "server";
        int answer = JOptionPane.showOptionDialog(null, "client or server",
                "\"Defense of Ancient\"", 0, JOptionPane.QUESTION_MESSAGE, null, options, null);
        switch (answer) {
            case 0:
                model = new Client(5555, "localhost");
                team= Judge.TEAM_SCOURGE;
                break;
            case 1:
                model = new Server(5555);
                team=Judge.TEAM_SENTINEL;
                break;

            default:
                System.exit(1);
                break;
        }
        FirstPage firstPage=new FirstPage();
        JFrame gameFrame=new JFrame("WELCOME");
        firstPage.init(gameFrame, team, model);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.getContentPane().add(firstPage);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }
}


