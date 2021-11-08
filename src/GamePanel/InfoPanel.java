package GamePanel;

import GameController.Controller;
import GameEngine.Engine;
import GameEngine.Judge;
import GameEngine.JudgeAbstract;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    private Judge judge;
    private Controller controller;
    private int team;


    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottonPanel;;
    private JTextArea  receviengBox;
    private JTextField sendingBox;
    private JButton send;
    private String sendingMassage = "";
    private String recievingMassage = "";
    private JButton pause;
    private JButton disconnect;

    JLabel timeLabel;
    JTextField timeText;
    JLabel gameInfo;
    JLabel[] informationsLabel;
    JTextField[] inforamtionText;


    public void setSendingMassage(String sendingMassage) {
        this.sendingMassage = sendingMassage;
    }

    public String getRecievingMassage() {

        return recievingMassage;
    }

    public JButton getSend() {
        return send;
    }

    public void setRecievingMassage(String recievingMassage) {
        receviengBox.append(recievingMassage + "\n");
    }

    public JTextField getSendingKey() {
        return sendingBox;
    }

    private JButton[] button;

    public JButton[] getButton() {
        return button;
    }

    public JButton getPause() {
        return pause;
    }

    public JButton getDisconnect() {
        return disconnect;
    }

    public InfoPanel(int team, Judge judge, Controller controller) {
        setBorder(new LineBorder(new Color(160, 82, 45), 5));

        this.judge = judge;
        this.controller = controller;
        this.team = team;

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        setTopPanel();
        topPanel.setBounds(0, 0, 150, 150);
        setCenterPanel();
        centerPanel.setBounds(0, 150, 150, 500);
        setBottonPanel();
        bottonPanel.setBounds(0, 750, 150, 500);

        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottonPanel, BorderLayout.SOUTH);

    }

    private void setTopPanel() {
        topPanel = new JPanel();

        topPanel.setSize(500, 100);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        timeLabel = new JLabel("Time :");
        timeLabel.setFont(timeLabel.getFont().deriveFont(Font.BOLD, 24f));

        pause = new JButton("Pause");
        pause.setFocusable(false);
        pause.addMouseListener(controller);
        disconnect = new JButton("Disconnect");
        disconnect.setFocusable(false);
        disconnect.addMouseListener(controller);

        timeText = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                double passedTime = Engine.getTime();
                passedTime *= 0.05;
                passedTime=Math.round(passedTime * 100.0) / 100.0;
                setText(String.valueOf(passedTime));
                super.paintComponent(g);
            }
        };
        timeText.setEditable(false);
        topPanel.add(timeLabel);
        topPanel.add(timeText);
        topPanel.add(pause);
        topPanel.add(disconnect);
    }


    private void setCenterPanel() {
        centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(2,1));

        JPanel grid1 = setGrid1();
        JPanel grid2 = setGrid2();

        centerPanel.add(grid1);
        centerPanel.add(grid2);

    }

    private void setBottonPanel() {
        bottonPanel = new JPanel();
        button = new JButton[8];
        bottonPanel.setLayout(new GridLayout(4,2,10,0));

        String type1 ;
        String type2 ;
        if(team == JudgeAbstract.TEAM_SENTINEL){
            type1 = "Fire" ;
            type2 = "Stone" ;
        }else{
            type1 = "Black" ;
            type2 = " Poison" ;
        }
        button[0] = new JButton("Create Infantry ");
        button[0].setToolTipText("Key A ");
        button[0].setBackground(new Color(250, 100, 100));

        button[2] = new JButton("Create Tank ");
        button[2].setToolTipText("Key Z ");
        button[2].setBackground(new Color(250, 100, 100));

        button[4] = new JButton("Power up Attacker Power");
        button[6] = new JButton("Power up Attacker Health");
        button[1] = new JButton("Create "+ type1 +" Tower");
        button[1].setBackground(new Color(250,100, 100));
        button[1].setToolTipText("Key S");

        button[3] = new JButton("Create "+ type2 +" Tower");
        button[3].setBackground(new Color(250, 100, 100));
        button[3].setToolTipText("Key X");

        button[5] = new JButton("Power up Tower Range");
        button[7] = new JButton("Power up Twer Power");

        for (int i = 0; i < 8; i++) {
            button[i].addMouseListener(controller);
            button[i].setFocusable(false);
            bottonPanel.add(button[i]);
        }
    }



    private JPanel setGrid1(){
        JPanel panel = new JPanel( new BorderLayout());

        receviengBox = new JTextArea();
        receviengBox.append(recievingMassage);
        receviengBox.setBackground(Color.LIGHT_GRAY);
        receviengBox.setEditable(false);

        JPanel panel1 = new JPanel(new GridLayout(2,1));

        sendingBox= new JTextField();
        sendingMassage = sendingBox.getText();
        send = new JButton("Send");
        send.setFocusable(false);
        send.addMouseListener(controller);

        panel1.add(sendingBox);
        panel1.add(send);
        panel.add(receviengBox,BorderLayout.CENTER);
        panel.add(panel1,BorderLayout.SOUTH);

        return panel;
    }

    private JPanel setGrid2(){
        int numberOfInformation = 4;

        JPanel panel = new JPanel( new GridLayout(numberOfInformation + 1,2,10,10));


        gameInfo = new JLabel("Your players inforamtion :");
        gameInfo.setFont(gameInfo.getFont().deriveFont(Font.ROMAN_BASELINE, 18f));
        panel.add(gameInfo);

        JLabel emptyLabel = new JLabel();
        panel.add(emptyLabel);

        informationsLabel = new JLabel[numberOfInformation];
        informationsLabel[0] = new JLabel("Number of coins : ");
        informationsLabel[1] = new JLabel("Health percent of Anceint : ");
        informationsLabel[2] = new JLabel("Number of remaing Attacker");
        informationsLabel[3] = new JLabel("Number of remaing Tower : ");

        inforamtionText = new JTextField[numberOfInformation];
        inforamtionText[0] = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                setText(String.valueOf(judge.getMoney(team)));
                super.paintComponent(g);
            }
        };

        inforamtionText[1] = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                switch (team) {
                    case JudgeAbstract.TEAM_SCOURGE:
                    {     double num=Math.round((1.0 * judge.getEngine().getAncients().get(1).getLife()) / (1.0*judge.getEngine().getAncients().get(1).getBaseLife()) * 10000)/100.0;
                        setText(String.valueOf(num) + "/100");
                        break;}
                    case JudgeAbstract.TEAM_SENTINEL:
                       double num=Math.round((1.0 * judge.getEngine().getAncients().get(0).getLife()) / (1.0*judge.getEngine().getAncients().get(0).getBaseLife()) * 10000)/100.0;
                        setText(String.valueOf(num) + "/100");
                }
                super.paintComponent(g);
            }
        };

        inforamtionText[2] = new JTextField(){
            @Override
            protected void paintComponent(Graphics g) {
                switch (team){
                    case JudgeAbstract.TEAM_SCOURGE:
                    {setText(judge.getEngine().getAttackers().get(1).size() + "");
                        break;}
                    case JudgeAbstract.TEAM_SENTINEL:
                        setText(judge.getEngine().getAttackers().get(0).size() + "");

                }
                super.paintComponent(g);
            }
        };

        inforamtionText[3] = new JTextField(){
            @Override
            protected void paintComponent(Graphics g) {
                switch (team){
                    case JudgeAbstract.TEAM_SCOURGE:
                    {setText(judge.getEngine().getTowers().get(1).size() + "");
                        break;}
                    case JudgeAbstract.TEAM_SENTINEL:
                        setText(judge.getEngine().getTowers().get(0).size() + "");
                }
                super.paintComponent(g);
            }
        };

        for (int i = 0; i < numberOfInformation; i++) {
            informationsLabel[i].setLabelFor(inforamtionText[i]);
            inforamtionText[i].setEditable(false);
            panel.add(informationsLabel[i]);
            panel.add(inforamtionText[i]);
        }
        return panel;
    }

}
