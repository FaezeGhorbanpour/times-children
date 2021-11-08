package GameEngine;

import Util.Utils;

import java.awt.*;
import java.util.HashMap;

public class Ancient {

    private Cell[][] cell;
    private int life;
    private int baseLife;
    private int coins;
    private boolean isAlive = true;
    private Cell centralHome;
    private int teamID;
    private int buildingTYPE;
    private Image image;

    public int getCoins() {
        return coins;
    }

    public int getBaseLife() {
        return baseLife;
    }

    public int getLife() {
        return life;

    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Cell getCentralHome() {
        return centralHome;
    }

    public Cell[][] getCell() {
        return cell;
    }

    public Image getImage() {
        return image;
    }

    public boolean isAlive() {
        return isAlive;
    }


    public Ancient(Cell[][] cell, int ID, int type) {
        this.cell = cell;
        baseLife = 10000;
        life = 10000;
        coins = 5000;
        centralHome = cell[2][2];
        teamID = ID;
        buildingTYPE = type;
        image = Utils.getImage("ancient.jpg");

    }

    public void addCoin(int numberOfCoins) {
        coins += numberOfCoins;
    }

    public boolean decCoin(int numberOfCoin) {
        coins -= numberOfCoin;
        if (coins >= 0)
            return true;
        else
            addCoin(numberOfCoin);
        Judge.callErr();
        return false;
    }

    public void hurt(int lifeDec) {
        if (life > 0) {
            life = life - lifeDec;
            if (life <= 0) {
                life = 0;
                isAlive = false;
            }
        }
    }

    public boolean ifContains(int row,int column){
        for (int i = 0; i <cell.length ; i++) {
            for (int j = 0; j <cell[i].length ; j++) {
                if(cell[i][j].getRow()==row&&cell[i][j].getColumn()==column)
                    return true;
            }
        }
        return false;
    }

    public HashMap<String, Integer> getInfo() {
        HashMap<String, Integer> ancientInfo = new HashMap<>();
        ancientInfo.put(JudgeAbstract.TEAM_ID, teamID);
        ancientInfo.put(JudgeAbstract.HEALTH, life);
        if (isAlive)
            ancientInfo.put(JudgeAbstract.IS_ALIVE, 1);
        else
            ancientInfo.put(JudgeAbstract.IS_ALIVE, 0);
        return ancientInfo;
    }

}
