package GameController;


import GameEngine.*;
import GameNetwork.Client;
import GameNetwork.Message;
import GameNetwork.Model;
import GameNetwork.Server;
import GamePanel.CellButton;
import GamePanel.DotaAllStars;
import GamePanel.InfoPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Controller implements MouseListener, KeyListener {
    private Judge judge;
    private InfoPanel infoPanel;
    private DotaAllStars dotaAllStars;
    private Hero hero;
    private int team;
    private int gameState;
    private Model model;
    private String sendingMassage;
    public static String codingKey = "__";
    private EnemyController enemyController;


    public void init(int team0, Judge judge, DotaAllStars dotaAllStars) {
        this.team = team0;
        int enemy = (team == JudgeAbstract.TEAM_SENTINEL) ? JudgeAbstract.TEAM_SCOURGE : JudgeAbstract.TEAM_SENTINEL;
        if (team == JudgeAbstract.TEAM_SCOURGE)
            hero = judge.getEngine().getHeroScourges();
        else
            hero = judge.getEngine().getHeroSentinels();
        this.judge = judge;
        this.dotaAllStars = dotaAllStars;
        infoPanel = dotaAllStars.getGameFrame().getInfoPanel();
        this.model = dotaAllStars.getModel();
        enemyController = new EnemyController();
        enemyController.init(enemy, judge, dotaAllStars);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == infoPanel.getPause()) {
            if (dotaAllStars.isPaused()) {
                dotaAllStars.getGameFrame().requestFocus();
                dotaAllStars.setPaused(false);
                sendingMassage = "pause" + codingKey + " " + codingKey + "0" + codingKey + "0" + codingKey + "0";
            } else {
                dotaAllStars.setPaused(true);
                sendingMassage = "pause" + codingKey + " " + codingKey + "1" + codingKey + "0" + codingKey + "0";
            }
            sendMessage(sendingMassage);
        } else if (e.getSource() == infoPanel.getDisconnect()) {
            JOptionPane.showMessageDialog(null, "The connection is down!", "Warning", JOptionPane.WARNING_MESSAGE);
            sendingMassage = "disconnect" + codingKey + " " + codingKey + "1" + codingKey + "1" + codingKey + "0";
            sendMessage(sendingMassage);
            if (model instanceof Server) {
                try {
                    ((Server) model).getNewSocket().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (model instanceof Client) {
                try {
                    ((Client) model).getNewSocket().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dotaAllStars.isPaused()) {
            if (e.getSource() == infoPanel.getButton()[0])
                gameState = 1;
            if (e.getSource() == infoPanel.getButton()[1])
                gameState = 3;
            if (e.getSource() == infoPanel.getButton()[2])
                gameState = 2;
            if (e.getSource() == infoPanel.getButton()[3])
                gameState = 4;
            if (e.getSource() == infoPanel.getButton()[5])
                gameState = 5;
            if (e.getSource() == infoPanel.getButton()[7])
                gameState = 6;
            if (e.getSource() == infoPanel.getButton()[4]) {
                try {
                    gameState = 0;
                    judge.purchaseAttackersPowerup(team, JudgeAbstract.POWERUP_ATTACKER_POWER);
                    sendingMassage = "attacker" + codingKey + "purchase" + codingKey + JudgeAbstract.POWERUP_ATTACKER_POWER + codingKey + "00" + codingKey + "00";
                    sendMessage(sendingMassage);
                } catch (DotaExceptionBase dotaExceptionBase) {
                    dotaExceptionBase.printStackTrace();
                }
            }

            if (e.getSource() == infoPanel.getButton()[6]) {
                try {
                    gameState = 0;
                    judge.purchaseAttackersPowerup(team, JudgeAbstract.POWERUP_ATTACKER_HEALTH);
                    sendingMassage = "attacker" + codingKey + "purchase" + codingKey + JudgeAbstract.POWERUP_ATTACKER_HEALTH + codingKey + "00" + codingKey + "00";
                    sendMessage(sendingMassage);
                } catch (DotaExceptionBase dotaExceptionBase) {
                    dotaExceptionBase.printStackTrace();
                }
            }

            if (e.getSource() == infoPanel.getSend()) {

                if (!infoPanel.getSendingKey().getText().equals("")) {
                    String messageToSend = infoPanel.getSendingKey().getText();
                    infoPanel.getSendingKey().setText("");
                    infoPanel.setRecievingMassage("me: " + messageToSend);
                    Message message = new Message(messageToSend, model.getMyID(),
                            model.getBroadcastID());
                    try {
                        for (ObjectOutputStream output : model.getOutputs().values()) {
                            output.writeObject(message);
                            output.flush();
                            System.out.println(message.getMessage() + "sent");
                        }
                    } catch (Exception ee) {
                        System.out.println(ee.getMessage());
                    }
                }
            }

            if (e.getSource() instanceof CellButton) {
                CellButton button = (CellButton) e.getSource();
                int xButton = button.getRow();
                int yButton = button.getColumn();

                try {
                    switch (gameState) {
                        case 1: {

                            judge.createAttacker(team, JudgeAbstract.ATTACKER_INFANTRY, null, null, xButton, yButton);
                            sendingMassage = "attacker" + codingKey + "create" + codingKey + JudgeAbstract.ATTACKER_INFANTRY + codingKey + xButton + codingKey + yButton;
                            break;
                        }
                        case 2: {
                            judge.createAttacker(team, JudgeAbstract.ATTACKER_TANK, null, null, xButton, yButton);
                            sendingMassage = "attacker" + codingKey + "create" + codingKey + JudgeAbstract.ATTACKER_TANK + codingKey + xButton + codingKey + yButton;
                            break;

                        }
                        case 3: {
                            int type = (team == JudgeAbstract.TEAM_SENTINEL) ? JudgeAbstract.TOWER_FIRE : JudgeAbstract.TOWER_BLACK;
                            judge.createTower(team, type, null, null, 0, xButton, yButton);
                            sendingMassage = "tower" + codingKey + "create" + codingKey + type + codingKey + xButton + codingKey + yButton;
                            break;

                        }
                        case 4: {
                            int type = (team == JudgeAbstract.TEAM_SENTINEL) ? JudgeAbstract.TOWER_STONE : JudgeAbstract.TOWER_POISON;
                            judge.createTower(team, type, null, null, 0, xButton, yButton);
                            sendingMassage = "tower" + codingKey + "create" + codingKey + type + codingKey + xButton + codingKey + yButton;
                            break;
                        }
                        case 5: {
                            judge.getEngine().towerPowerUp(team, JudgeAbstract.POWERUP_TOWER_RANGE, xButton, yButton);
                            sendingMassage = "tower" + codingKey + "purchase" + codingKey + JudgeAbstract.POWERUP_TOWER_RANGE + codingKey + xButton + codingKey + yButton;
                            break;

                        }
                        case 6: {

                            judge.getEngine().towerPowerUp(team, JudgeAbstract.POWERUP_TOWER_POWER, xButton, yButton);
                            sendingMassage = "tower" + codingKey + "purchase" + codingKey + JudgeAbstract.POWERUP_TOWER_POWER + codingKey + xButton + codingKey + yButton;
                            break;

                        }
                        case 0: {
                            judge.getEngine().heroAttack(hero, xButton, yButton);
                            sendingMassage = "hero" + codingKey + "shoot" + codingKey + 0 + codingKey + xButton + codingKey + yButton;

                        }
                    }
                } catch (DotaExceptionBase dotaExceptionBase) {
                    dotaExceptionBase.printStackTrace();
                }
                gameState = 0;
                sendMessage(sendingMassage);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!dotaAllStars.isPaused()) {
            int key = e.getKeyCode();
            sendingMassage = null;
            GoldMine goldMine = null;
            if (key == KeyEvent.VK_LEFT) {
                goldMine = judge.heroMove(hero, JudgeAbstract.DIRECTION_LEFT);
                sendingMassage = "hero" + codingKey + "move" + codingKey + JudgeAbstract.DIRECTION_LEFT + codingKey + hero.getCell().getRow() + codingKey + hero.getCell().getColumn();
            }
            if (key == KeyEvent.VK_RIGHT) {
                goldMine = judge.heroMove(hero, JudgeAbstract.DIRECTION_RIGHT);
                sendingMassage = "hero" + codingKey + "move" + codingKey + JudgeAbstract.DIRECTION_RIGHT + codingKey + hero.getCell().getRow() + codingKey + hero.getCell().getColumn();
            }
            if (key == KeyEvent.VK_DOWN) {
                goldMine = judge.heroMove(hero, JudgeAbstract.DIRECTION_DOWN);
                sendingMassage = "hero" + codingKey + "move" + codingKey + JudgeAbstract.DIRECTION_DOWN + codingKey + hero.getCell().getRow() + codingKey + hero.getCell().getColumn();
            }
            if (key == KeyEvent.VK_UP) {
                goldMine = judge.heroMove(hero, JudgeAbstract.DIRECTION_UP);
                sendingMassage = "hero" + codingKey + "move" + codingKey + JudgeAbstract.DIRECTION_UP + codingKey + hero.getCell().getRow() + codingKey + hero.getCell().getColumn();
            }
            if (sendingMassage != null) {
                sendMessage(sendingMassage);
                if (goldMine != null)
                    sendMessage("goldmine" + codingKey + " " + codingKey + goldMine.getRank() + codingKey + goldMine.getCell().getRow() + codingKey + goldMine.getCell().getColumn());
            }
            if (key == KeyEvent.VK_S) {
                gameState = 3;
            }
            if (key == KeyEvent.VK_A) {
                gameState = 1;
            }
            if (key == KeyEvent.VK_Z) {
                gameState = 2;
            }
            if (key == KeyEvent.VK_X) {
                gameState = 4;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void start() {

        Thread loop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean isInstruction;
                    Message message = model.pollMessage();
                    String str = model.pollString();
                    if (message != null) {
                        isInstruction = enemyController.deCodingMassage(message.getMessage());
                        if (!isInstruction) {
                            infoPanel.setRecievingMassage(message.getMessage());
                        }
                    }
                    if (str != null) {
                        infoPanel.setRecievingMassage(str);
                        Engine.time = 0;
                        judge.getEngine().getAncients().get(team).setCoins(5000);
                    }
                    // repaint();
                    try {
                        Thread.sleep(5);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        loop.start();
        model.start();
    }

    private void sendMessage(String messageToSend) {
        Message message = new Message(messageToSend, model.getMyID(),
                model.getBroadcastID());
        try {
            for (ObjectOutputStream output : model.getOutputs().values()) {
                output.writeObject(message);
                output.flush();
            }
        } catch (Exception ee) {
            System.out.println(ee.getMessage());
        }
    }


}
