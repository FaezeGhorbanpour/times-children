package GamePanel;

import GameNetwork.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SecondPage extends JPanel {
    private JButton butt1;
    private JButton butt2;
    private JFrame gameFrame;
    private JPanel keyPanel;
    private JPanel picPanel;
    private static ImageIcon pathImage1;
    private static ImageIcon pathImage2;
    private int team;
    private Model model;
    static {
        pathImage1=new ImageIcon("image/Map1.png");
        pathImage2=new ImageIcon("image/Map2.png");
    }

    public void init(JFrame gameFrame,int team,Model model) {
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
            gameFrame.dispose();
            if (event.getSource() == butt1) {
                  new DotaAllStars(0,team,model);
            } else if(event.getSource() == butt2){
                 new DotaAllStars(1,team,model);
            }
        }
    }

    private void setKeyPanel() {
        keyPanel = new JPanel();
        butt1 = new JButton("Horror Path");
        butt2 = new JButton("Danger Path");
        butt1.setBackground(new Color(120,40,40));
        butt1.setForeground(Color.WHITE);
        butt2.setBackground(new Color(120, 40, 40));
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
        picPanel.setLayout(new GridLayout(1,2));
        picPanel.add(new JLabel(pathImage1));
        picPanel.add(new JLabel(pathImage2));
    }
}
