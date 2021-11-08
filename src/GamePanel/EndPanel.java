package GamePanel;

import GameEngine.Judge;
import GameEngine.JudgeAbstract;
import Util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndPanel extends JFrame {
    private Image image;


    public EndPanel(int team, int state, Judge judge) {
        if (team == Judge.TEAM_SCOURGE) Judge.backSound.stop();
        String teamName = (team == JudgeAbstract.TEAM_SENTINEL) ? "Sentinel" : " Scourges";
        if (state == 0) {
            image = Utils.getImage("winner.png");
            Judge.callWinnig();
        } else if (state == 1) {
            image = Utils.getImage("gameOver.png");
            Judge.callLoosing();
        }

        setTitle("Massage For " + teamName);
        setSize(500, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }


    public void init() {
        JLabel lblWinnerPic = new JLabel();
        lblWinnerPic.setIcon(new ImageIcon(image));
        add(lblWinnerPic, BorderLayout.CENTER);

        JButton endButton = new JButton("Exit");
        add(endButton, BorderLayout.SOUTH);


        endButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


    }


}