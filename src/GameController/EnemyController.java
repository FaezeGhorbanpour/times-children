package GameController;

import GameEngine.DotaExceptionBase;
import GameEngine.Hero;
import GameEngine.Judge;
import GameEngine.JudgeAbstract;
import GamePanel.DotaAllStars;

import javax.swing.*;


public class EnemyController {

    private Judge judge;
    private Hero enemyHero;
    private DotaAllStars dotaAllStars;
    private int enemy;
    private int xButton;
    private int yButton;
    private int type;
    private String codingKey = Controller.codingKey;

    public void init (int enemy,Judge judge, DotaAllStars dotaAllStars){
        this.dotaAllStars = dotaAllStars;
        this.enemy = enemy;
        this.judge = judge;
        enemyHero = judge.getEngine().getHeros().get(enemy);
    }

    public boolean deCodingMassage(String massage) {
        if (!massage.contains(codingKey))
            return false;

        String[] parts = massage.split(codingKey);
        if (parts.length != 5) {
            return false;
        }


        xButton = Integer.parseInt(parts[3]);
        yButton = Integer.parseInt(parts[4]);
        type = Integer.parseInt(parts[2]);

        try {
            if (parts[0].equals("hero")) {
                if (parts[1].equals("move")) {
                    switch (type) {
                        case JudgeAbstract.DIRECTION_UP: {
                            judge.heroMove(enemyHero, JudgeAbstract.DIRECTION_UP);
                            enemyHero.getCell().setRow(xButton);
                            enemyHero.getCell().setColumn(yButton);
                            break;
                        }
                        case JudgeAbstract.DIRECTION_DOWN: {
                            judge.heroMove(enemyHero, JudgeAbstract.DIRECTION_DOWN);
                            enemyHero.getCell().setRow(xButton);
                            enemyHero.getCell().setColumn(yButton);
                            break;
                        }
                        case JudgeAbstract.DIRECTION_LEFT: {
                            judge.heroMove(enemyHero, JudgeAbstract.DIRECTION_LEFT);
                            enemyHero.getCell().setRow(xButton);
                            enemyHero.getCell().setColumn(yButton);
                            break;
                        }
                        case JudgeAbstract.DIRECTION_RIGHT: {
                            judge.heroMove(enemyHero, JudgeAbstract.DIRECTION_RIGHT);
                            enemyHero.getCell().setRow(xButton);
                            enemyHero.getCell().setColumn(yButton);
                            break;
                        }
                    }
                } else if (parts[1].equals("shoot")) {
                    judge.getEngine().heroAttack(enemyHero, xButton, yButton);
                }
            } else if (parts[0].equals("attacker")) {

                if (parts[1].equals("purchase")) {
                    judge.purchaseAttackersPowerup(enemy, type);
                } else if (parts[1].equals("create")) {
                    judge.getEngine().createAttacker(enemy, type, null, null, xButton, yButton);
                }

            } else if (parts[0].equals("tower")) {
                if (parts[1].equals("purchase")) {
                    judge.getEngine().towerPowerUp(enemy, type, xButton, yButton);
                } else if (parts[1].equals("create")) {
                    judge.getEngine().createTower(enemy, type, null, null, 0, xButton, yButton);
                }
            }else if(parts[0].equals("goldmine")){
                judge.getEngine().getGoldMines().get(type).setCell(xButton,yButton);
            }else if(parts[0].equals("pause")){
               if(type == 0){
                   dotaAllStars.setPaused(false);
                   JOptionPane.showMessageDialog(null, "Continue playing .");
               }else{
                   dotaAllStars.setPaused(true);
                   JOptionPane.showMessageDialog(null, "Game is paused .");
               }
            }else if (parts[0].equals("disconnect")){
                JOptionPane.showMessageDialog(null,"You are getting disconnected!","Warning",JOptionPane.WARNING_MESSAGE);
            }
        } catch (DotaExceptionBase dotaExceptionBase) {
            dotaExceptionBase.printStackTrace();
        }
            return true;
    }

}
