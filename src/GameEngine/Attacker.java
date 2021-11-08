package GameEngine;

import Util.Utils;

import java.awt.*;
import java.util.HashMap;
public class Attacker extends Target {

    public static int numberOfPowerUpForSentinels = 0;
    public static int numberOfLifeUpForSentinels = 0;
    public static int numberOfPowerUpForScourage = 0;
    public static int numberOfLifeUpForScourge = 0;


    private int speed;
    private Integer firstTimeOfMovement;
    private int basePrice;
    private int basePowerimpact;
    private Image image ;

    int numberOfLifeUp = 0;
    int numberOfPowerUp = 0;

    public Image getImage() {
        return image;
    }

    private void setImage()  {
            if (teamID == JudgeAbstract.TEAM_SENTINEL & type == JudgeAbstract.ATTACKER_TANK) {
                image = Utils.getImage("sentinelTank.png");
            }else if (teamID == JudgeAbstract.TEAM_SENTINEL & type == JudgeAbstract.ATTACKER_INFANTRY){
                image = Utils.getImage("sentinelAttacker.png");
            }else if (teamID == JudgeAbstract.TEAM_SCOURGE & type == JudgeAbstract.ATTACKER_TANK){
                image = Utils.getImage("scourgeTank.png");
            }else{
                image = Utils.getImage("Attacker.png");
            }
    }

    public Attacker(int teamID, int type, Lane lane, Path path, Cell cell) {
        super.type = type;
        super.teamID = teamID;
        super.cell = cell;
        super.path = path;
        super.lane = lane;

        setImage();

        speed = 10;
        switch (type) {
            case JudgeAbstract.ATTACKER_INFANTRY: {
                super.timeBetweenShouting = 4;
                super.baseLife = 400;
                super.fieldOfView = 4;

                basePrice = 10;
                basePowerimpact = 20;
                super.name = "ATTACKER_INFANTRY";
                setAttackersPowerup();
                break;
            }
            case JudgeAbstract.ATTACKER_TANK: {
                super.fieldOfView = 6;
                super.timeBetweenShouting = 10;
                super.baseLife = 1000;

                basePrice = 40;
                basePowerimpact = 100;
                super.name = "ATTACKER_TANK";
                setAttackersPowerup();
                break;
            }
        }
    }

    private void setAttackersPowerup(){
        if (teamID == JudgeAbstract.TEAM_SENTINEL) {
            numberOfLifeUp = numberOfLifeUpForSentinels;
            numberOfPowerUp = numberOfPowerUpForSentinels;
        } else {
            numberOfLifeUp = numberOfLifeUpForScourge;
            numberOfPowerUp = numberOfPowerUpForScourage;
        }
        life = baseLife + 5 * numberOfLifeUp;
        price = (int) (basePrice + basePrice * Math.pow(.5, numberOfPowerUp) + basePrice * Math.pow(.5, numberOfLifeUp));
        powerForInfantry = (int) (basePowerimpact * Math.pow(1.1, numberOfPowerUp));
        powerForTank = powerForInfantry;
    }




    public void move(int time) {
        if (firstTimeOfMovement == null){
            firstTimeOfMovement = time;
        }else if(time - firstTimeOfMovement >= speed){
            firstTimeOfMovement  = time;
        }else{
            return;
        }
        if (teamID == JudgeAbstract.TEAM_SENTINEL) {
            cell = path.nextCell(cell, true);
        } else if (teamID == JudgeAbstract.TEAM_SCOURGE) {
            cell = path.nextCell(cell, false);
        }
    }



    public HashMap<String, Integer> getInfo()  {
        HashMap<String, Integer> attackerInfo = new HashMap<>();
        attackerInfo.put(JudgeAbstract.TEAM_ID, teamID);
        attackerInfo.put(JudgeAbstract.HEALTH, life);
        attackerInfo.put(JudgeAbstract.RANGE, fieldOfView);
        attackerInfo.put(JudgeAbstract.SPEED, speed);
        attackerInfo.put(JudgeAbstract.ROW, cell.getRow());
        attackerInfo.put(JudgeAbstract.COLOUMN, cell.getColumn());
        attackerInfo.put(JudgeAbstract.RELOAD_TIME,timeBetweenShouting);
        attackerInfo.put(JudgeAbstract.ATTACK,powerForInfantry);
        attackerInfo.put(JudgeAbstract.VALUE,price);
        if (isAlive)
            attackerInfo.put(JudgeAbstract.IS_ALIVE, 1);
        else
            attackerInfo.put(JudgeAbstract.IS_ALIVE, 0);

        return attackerInfo;
    }
}
