package GameEngine;

import Util.Utils;

import java.awt.*;
import java.util.HashMap;

public class Hero {
    private final int tinyLife = 5000;
    private final int venomancerLife = 4000;
    private int lastAttackTime = 0;
    private Integer lastMoveTime = null;
    private int life;
    private int fieldOfView;
    private int power;
    private Cell cell = new Cell();
    private int teamID;
    private int moveTime;
    private int AttackTime;
    private int heroType;
    private int max_Row;
    private int max_Cul;
    private int bornRow, bornColum;
    private int dieTime = -600;
    private Image image;

    public Hero(int teamID, Cell bornCell, int maxRow, int maxCul) {
        if (teamID == JudgeAbstract.TEAM_SENTINEL)// sentinel's HERO:Tiny
        {
            life = tinyLife;
            power = 400;
            this.teamID = JudgeAbstract.TEAM_SENTINEL;
            heroType = JudgeAbstract.HERO_TINY;
            moveTime = 8;
            AttackTime = 8;
            image = Utils.getImage("StoneMan.png");
        } else {
            life = venomancerLife;
            power = 300;
            this.teamID = JudgeAbstract.TEAM_SCOURGE;
            heroType = JudgeAbstract.HERO_VENOMANCER;
            moveTime = 5;
            AttackTime = 6;
            image = Utils.getImage("scourgeHero.png");
        }
        fieldOfView = 7;
        this.bornRow = bornCell.getRow();
        this.bornColum = bornCell.getColumn();
        this.cell = new Cell();
        this.cell.setRow(bornRow);
        this.cell.setColumn(bornColum);
        max_Cul = maxCul;
        max_Row = maxRow;

    }

    public int getLife() {
        return life;
    }

    public Cell getCell() {
        return cell;
    }

    public int getTeam() {
        return teamID;
    }

    public int getFieldOfView() {
        return fieldOfView;
    }

    public boolean heroMove(int direction, int time) {
        checkForGettingBorned();
        if (lastMoveTime == null || (time - lastMoveTime >= moveTime && time - dieTime >= 600)) {
            if (direction == JudgeAbstract.DIRECTION_DOWN && cell.getRow() + 1 < max_Row) {
                cell.setRow(cell.getRow() + 1);
            } else if (direction == JudgeAbstract.DIRECTION_UP && cell.getRow() - 1 >= 0) {
                cell.setRow(cell.getRow() - 1);
            } else if (direction == JudgeAbstract.DIRECTION_LEFT && cell.getColumn() - 1 >= 0) {
                cell.setColumn(cell.getColumn() - 1);
            } else if (direction == JudgeAbstract.DIRECTION_RIGHT && cell.getColumn() + 1 < max_Cul) {
                cell.setColumn(cell.getColumn() + 1);
            } else {

                return false;
            }
            lastMoveTime = time;
            return true;
        } else
            return false;
    }


    public double heroAttackAttacker(Attacker attacker, int time) throws DotaExceptionBase {
        return heroAttack(attacker, time);
    }

    public double heroAttackTower(Tower tower, int time) throws DotaExceptionBase {
        return heroAttack(tower, time);
    }

    public double heroAttackAncient(Ancient ancient, int time) throws DotaExceptionBase {
        return heroAttack(ancient, time);
    }

    public double heroAttackBarrack(Barracks barrack, int time) throws DotaExceptionBase {
        return heroAttack(barrack, time);
    }

    private double heroAttack(Object object, int time) throws DotaExceptionBase {
        checkForGettingBorned();
        if (time - lastAttackTime >= AttackTime && time - dieTime >= 600) {
            lastAttackTime = time;
            Judge.callShooting();
            if (object instanceof Target) {
                if (((Target) object).hurt(power)) {
                    return ((Target) object).getPrice() * .8;
                }
            } else if (object instanceof Barracks) {
                ((Barracks) object).hurt(power);
            } else if (object instanceof Ancient) {
                ((Ancient) object).hurt(power);
            }
            return 0;

        } else {
            //throw new DotaExceptionBase();
            return 0;
        }
    }


    public int heroAttackHero(Hero hero, int time) throws DotaExceptionBase {
        checkForGettingBorned();
        if (time - lastAttackTime >= AttackTime && time - dieTime >= 600) {
            lastAttackTime = time;
            Judge.callShooting();
            hero.hurt(power, time);
            return 0;
        }
        Judge.callErr();
        throw new DotaExceptionBase();
    }
    //public void setLastAttackTime(int lastAttackTime) {
    //    this.lastAttackTime = lastAttackTime;
    //}

    public boolean hurt(int lifeDec, int time) {// if dies return true
        checkForGettingBorned();
        if (life > 0) {
            life = life - lifeDec;
            if (life <= 0) {
                Judge.callDying();
                cell.setRow(bornRow);
                cell.setColumn(bornColum);
                life = 0;
                dieTime = time;
                return true;
            }
        }
        return false;
    }

    public HashMap<String, Integer> getInfo() {
        checkForGettingBorned();
        HashMap<String, Integer> heroInfo = new HashMap<>();
        heroInfo.put(JudgeAbstract.TEAM_ID, teamID);
        heroInfo.put(JudgeAbstract.HEALTH, life);
        heroInfo.put(JudgeAbstract.RANGE, fieldOfView);
        heroInfo.put(JudgeAbstract.RELOAD_TIME, AttackTime);
        heroInfo.put(JudgeAbstract.ATTACK, power);
        heroInfo.put(JudgeAbstract.SPEED, moveTime);
        heroInfo.put(JudgeAbstract.ROW, cell.getRow());
        heroInfo.put(JudgeAbstract.COLOUMN, cell.getColumn());
        if (life > 0)
            heroInfo.put(JudgeAbstract.IS_ALIVE, 1);
        else
            heroInfo.put(JudgeAbstract.IS_ALIVE, 0);
        return heroInfo;
    }

    private void checkForGettingBorned() {
        if (Engine.time - dieTime >= 600 && life == 0)
            if (heroType == JudgeAbstract.HERO_TINY) {
                life = tinyLife;
            } else {
                life = venomancerLife;
            }
    }

    public boolean isInFieldOfView(int row, int col) {
        return Math.abs(row - cell.getRow()) <= fieldOfView && Math.abs(col - cell.getColumn()) <= fieldOfView;

    }

    public Image getImage() {
        return image;
    }
}