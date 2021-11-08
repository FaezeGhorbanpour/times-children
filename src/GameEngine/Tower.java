package GameEngine;

import Util.Utils;

import java.awt.*;
import java.util.HashMap;

public class Tower extends Target {

    private int numberOfRequestingRange = 0;
    private Image image ;

    public Tower(int teamID, int type, Cell cell, Path path, Lane lane) {
        super.path = path;
        super.teamID = teamID;
        super.cell = cell;
        super.lane = lane;
        this.setType(type);
        super.fieldOfView = 7;

        if (teamID == JudgeAbstract.TEAM_SENTINEL && type == JudgeAbstract.TOWER_FIRE) {
            setInformation(5000,50,400,300,40,"FireTower");
        } else if (teamID == JudgeAbstract.TEAM_SENTINEL && type == JudgeAbstract.TOWER_STONE) {
            setInformation(4000,200,40,500,16,"StoneTower");
        } else if (teamID == JudgeAbstract.TEAM_SCOURGE && type == JudgeAbstract.TOWER_BLACK) {
            setInformation(4000,100,20,500,10,"BlackTower");
        } else if (teamID == JudgeAbstract.TEAM_SCOURGE && type == JudgeAbstract.TOWER_POISON) {
            setInformation(5000,20,100,300,10,"PoisonTower");
        }
    }

    public Image getImage() {
        return image;
    }

    public boolean powerUp(int towerUpType) {
        switch (towerUpType) {
            case JudgeAbstract.POWERUP_TOWER_POWER: {
                price += 0.15 * price;
                return true;
            }
            case JudgeAbstract.POWERUP_TOWER_RANGE: {
                if (numberOfRequestingRange < 3) {
                    numberOfRequestingRange ++;
                    price += 0.2 * price;
                    fieldOfView++;
                    return true;
                }else {
                    Judge.callErr();
                    System.err.print("You've used 3 times");
                }
            }
        }
        return false;
    }

    private void setInformation(int life,int powerForTank , int powerForInfantry,int price , int timeBetweenShouting , String name){
        this.life = life;
        this.powerForTank = powerForTank;
        this.powerForInfantry = powerForInfantry;
        super.price = price;
        this.timeBetweenShouting = timeBetweenShouting;
        this.name = name;
        this.image = Utils.getImage(name+".png");
    }






    public HashMap<String, Integer> getInfo() {
        HashMap<String, Integer> towerInfo = new HashMap<>();
        towerInfo.put(JudgeAbstract.TEAM_ID, teamID);
        towerInfo.put(JudgeAbstract.HEALTH, life);
        towerInfo.put(JudgeAbstract.RANGE,fieldOfView);
        towerInfo.put(JudgeAbstract.RELOAD_TIME,timeBetweenShouting);
        towerInfo.put(JudgeAbstract.ROW,cell.getRow());
        towerInfo.put(JudgeAbstract.COLOUMN,cell.getColumn());
        towerInfo.put(JudgeAbstract.VALUE,(int)(price*0.8));
        if(isAlive()) towerInfo.put(JudgeAbstract.IS_ALIVE,1);
        else towerInfo.put(JudgeAbstract.IS_ALIVE,0);
        towerInfo.put(JudgeAbstract.INFANTRY_ATTACK,powerForInfantry);
        towerInfo.put(JudgeAbstract.TANK_ATTACK,powerForTank);
        return towerInfo;
    }


}