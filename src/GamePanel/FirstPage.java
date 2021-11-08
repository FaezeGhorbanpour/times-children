package GamePanel;

import GameNetwork.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by VAIO on 1/29/2016.
 */
public class FirstPage extends JPanel {
    private JButton butt1;
    private JButton butt2;
    private JFrame gameFrame;
    private JPanel keyPanel;
    private JPanel picPanel;
    private static ImageIcon gameImage2;
    private int team;
    private Model model;
    static {
        gameImage2=new ImageIcon("image/DefenceOfAncients2.png");
    }

    public void init(JFrame gameFrame,int team, Model model) {
        this.setLayout(new BorderLayout());
        setKeyPanel();
        setPicPanel();
        this.add(picPanel, BorderLayout.CENTER);
        this.add(keyPanel, BorderLayout.PAGE_END);
        this.gameFrame = gameFrame;
        this.model=model;
        this.team=team;
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if ( event.getSource() == butt1) {
                gameFrame.dispose();
                SecondPage secondPage = new SecondPage();
                JFrame gameFrame2 = new JFrame("Game Level");
                secondPage.init(gameFrame2,team,model);
                gameFrame2.setVisible(true);
                gameFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame2.getContentPane().add(secondPage);
                gameFrame2.pack();
                gameFrame2.setLocationRelativeTo(null);
            } else if ( event.getSource() == butt2) {
                System.exit(1);
            }
        }
    }

    private void setKeyPanel() {
        keyPanel = new JPanel();
        butt1 = new JButton("New Game");
        butt2 = new JButton("Exit");
        butt1.setBackground(new Color(120,35,35));
        butt1.setForeground(Color.WHITE);
        butt2.setBackground(new Color(120, 35, 35));
        butt2.setForeground(Color.WHITE);
        GridLayout gridLayout = new GridLayout(1, 2);
        keyPanel.setLayout(gridLayout);
        keyPanel.add(butt1);
        keyPanel.add(butt2);
        ButtonHandler buttonHandler = new ButtonHandler();
        butt1.addActionListener(buttonHandler);
        butt2.addActionListener(buttonHandler);
    }

    private void setPicPanel() {
        picPanel = new JPanel();
        picPanel.setBounds(0, 0, 1300, 397);
        picPanel.add(new JLabel(gameImage2));
      //  picPanel.repaint();
    }

}