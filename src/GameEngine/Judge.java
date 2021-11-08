package GameEngine;

import Util.Utils;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.HashMap;


public  class  Judge extends JudgeAbstract {
    Engine engine = new Engine();
    private ArrayList<Integer> listOfPowerUpforSentinalAttacker = new ArrayList<>();
    private ArrayList<Integer> listOfPowerUpforScourgeAttacker = new ArrayList<>();
    private static Clip errSound=null;
    private static Clip shootingSound=null;
    private static Clip dyingSound=null;
    private static Clip winningSound=null;
    private static Clip loosingSound=null;
    public static Clip backSound=null;
    private int team;

    public Judge(int team){
        this.team=team;
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public void loadMap(int columns, int rows, ArrayList<ArrayList<Cell>> path1, ArrayList<ArrayList<Cell>> path2, ArrayList<ArrayList<Cell>> path3, Cell[][] ancient1, Cell[][] ancient2, ArrayList<Cell[][]> barraks1, ArrayList<Cell[][]> barraks2, ArrayList<Cell> goldMines) {
        engine.setMapWidth(columns);
        engine.setMapHeight(rows);

        engine.setScourgesAncient(ancient2);
        engine.setSentinelsAncient(ancient1);

        engine.setScourgesBarraks(barraks2);
        engine.setSentinelsBarraks(barraks1);

        engine.setPath1(path1);
        engine.setPath2(path2);
        engine.setPath3(path3);

        engine.setGoldMine(goldMines);

        engine.setHero();

    }

    @Override
    public int getMapWidth() {
        return engine.getMapWidth();
    }

    @Override
    public int getMapHeight() {
        return engine.getMapHeight();
    }

    @Override
    public GameObjectID getGoldMineID(int goldMineNumber) throws DotaExceptionBase {
        return engine.getGoldMineID(goldMineNumber);
    }

    @Override
    public GameObjectID[] getBuildingID(int teamID, int buildingType) throws DotaExceptionBase {
        return engine.getBuildingID(teamID, buildingType);
    }

    @Override
    public GameObjectID getPathID(int pathNumber) {
        GameObjectID[] gameObjectID = engine.getPathID();
        switch (pathNumber) {
            case 0:
                return gameObjectID[0];
            case 1:
                return gameObjectID[1];
            case 2:
                return gameObjectID[2];
        }
        return null;
    }

    @Override
    public GameObjectID[] getLaneID(int pathNumber) {
        switch (pathNumber) {
            case 0:
                return engine.getLane1ID();
            case 1:
                return engine.getLane2ID();
            case 2:
                return engine.getLane3ID();
        }
        return null;
    }

    @Override
    public GameObjectID getHeroID(int teamID, int heroID) {
        if (teamID == JudgeAbstract.TEAM_SENTINEL || heroID == JudgeAbstract.HERO_TINY) {
            return engine.getTinyId();
        } else if (teamID == JudgeAbstract.TEAM_SCOURGE || heroID == JudgeAbstract.HERO_VENOMANCER) {
            return engine.getVenomancerId();
        }
        return null;
    }

    @Override
    public void setup() {
        engine.setUp();
        if(team==Judge.TEAM_SCOURGE){
       try {
           backSound=Utils.getClip("audio/Pirates Of Caribbeans.wav");
        } catch (Exception e) {
           e.printStackTrace();
       }
      backSound.loop(20);
        }
    }

    public static void callErr(){
        try {
            errSound=Utils.getClip("audio/erro.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        errSound.start();
    }

    public static void callLoosing(){
        try {
            loosingSound=Utils.getClip("audio/GameOver.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loosingSound.start();
    }

    public static void callWinnig(){
        try {
            winningSound=Utils.getClip("audio/winning.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        winningSound.start();
    }

    public static void callShooting(){
        try {
            shootingSound=Utils.getClip("audio/shooting.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        shootingSound.start();
    }
    public static void callDying(){
        try {
            dyingSound=Utils.getClip("audio/dying.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        dyingSound.start();
    }

    @Override
    public GameObjectID createAttacker(int teamID, int attackerType, GameObjectID path, GameObjectID lane, int rowNumber, int colNumber) throws DotaExceptionBase {
        return engine.createAttacker(teamID, attackerType, path, lane, rowNumber, colNumber);
        //if (gameObjectID == null) {
        //    throw new DotaExceptionBase();
        //}

    }

    @Override
    public GameObjectID createTower(int teamID, int towerType, GameObjectID path, GameObjectID lane, int index, int rowNumber, int colNumber) throws DotaExceptionBase {
        return  engine.createTower(teamID, towerType, path, lane, index, rowNumber, colNumber);

    }

    @Override
    public void purchaseAttackersPowerup(int teamID, int powerupType) throws DotaExceptionBase {
        switch (teamID) {
            case JudgeAbstract.TEAM_SENTINEL: {
                switch (powerupType) {
                    case POWERUP_ATTACKER_HEALTH: {
                        Attacker.numberOfLifeUpForSentinels++;
                        if (engine.getAncients().get(0).decCoin(Attacker.numberOfLifeUpForSentinels * 500)) {
                            listOfPowerUpforSentinalAttacker.add(POWERUP_ATTACKER_HEALTH);
                        } else {
                            Attacker.numberOfLifeUpForSentinels--;
                        }
                        break;
                    }
                    case POWERUP_ATTACKER_POWER: {
                        Attacker.numberOfPowerUpForSentinels++;
                        if (engine.getAncients().get(0).decCoin(Attacker.numberOfPowerUpForSentinels * 1000)) {
                            listOfPowerUpforSentinalAttacker.add(POWERUP_ATTACKER_POWER);
                        } else {
                            Attacker.numberOfPowerUpForSentinels--;
                        }
                        break;
                    }
                }
            }
            case JudgeAbstract.TEAM_SCOURGE: {
                switch (powerupType) {
                    case POWERUP_ATTACKER_HEALTH: {
                        Attacker.numberOfLifeUpForScourge++;
                        if (engine.getAncients().get(1).decCoin(Attacker.numberOfLifeUpForScourge * 500))
                            listOfPowerUpforScourgeAttacker.add(POWERUP_ATTACKER_HEALTH);
                        else
                            Attacker.numberOfLifeUpForScourge--;
                        break;
                    }
                    case POWERUP_ATTACKER_POWER: {
                        Attacker.numberOfPowerUpForScourage++;
                        if (engine.getAncients().get(1).decCoin(Attacker.numberOfPowerUpForScourage * 500)) {
                            listOfPowerUpforScourgeAttacker.add(POWERUP_ATTACKER_POWER);
                        } else
                            Attacker.numberOfPowerUpForScourage--;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void purchaseTowerPowerup(int teamID, GameObjectID towerID, int powerupType) throws DotaExceptionBase {
        engine.towerPowerUp(teamID, powerupType, towerID);
    }

    @Override
    public synchronized GameObjectID heroMove(GameObjectID hero, Cell dest, int direction) throws DotaExceptionBase {
        return engine.heroMove(hero, dest, direction);
    }

    @Override
    public synchronized GameObjectID heroAttack(GameObjectID hero, Cell target) throws DotaExceptionBase {
        return engine.heroAttack(hero, target);
    }

    @Override
    public int getMoney(int teamID) {
        switch (teamID) {
            case JudgeAbstract.TEAM_SENTINEL:
                return engine.getAncients().get(0).getCoins();
            case JudgeAbstract.TEAM_SCOURGE:
                return engine.getAncients().get(1).getCoins();
        }
        return 0;
    }

    @Override
    public ArrayList<Integer> getAttackerPowerups(int teamID) {
        switch (teamID) {
            case JudgeAbstract.TEAM_SENTINEL: {
                return listOfPowerUpforSentinalAttacker;
            }
            case JudgeAbstract.TEAM_SCOURGE: {
                return listOfPowerUpforScourgeAttacker;
            }
        }
        return null;
    }

    @Override
    public ArrayList<GameObjectID> getTeamGoldMines(int teamID) throws DotaExceptionBase {

        return engine.goldMinesOwner(teamID);
    }

    @Override
    public HashMap<String, Integer> getInfo(GameObjectID id) throws DotaExceptionBase {
        return engine.getInfo(id);
    }

    //******************************************************************************************************************
    @Override
    public GameObjectID[] getInRange(GameObjectID id) throws DotaExceptionBase {

        return (GameObjectID[]) engine.getInRange(id).toArray();
    }

    @Override
    public GameObjectID getTarget(GameObjectID id) throws DotaExceptionBase {
        return engine.getTarget(id);
    }

    @Override
    public void next50milis() {

        engine.addCoinForGroups();
        engine.checkForAttackersMove();
        if (Engine.time % 40 == 0) {
            engine.towersAttack(JudgeAbstract.TOWER_FIRE);
        }
        if (Engine.time % 16 == 0) {
            engine.towersAttack(JudgeAbstract.TOWER_STONE);
        }
        if (Engine.time % 10 == 0) {
            engine.towersAttack(JudgeAbstract.TOWER_BLACK);
            engine.towersAttack(JudgeAbstract.TOWER_POISON);
        }
        Engine.time++;

    }

    @Override
    public void startTimer() {

    }

    @Override
    public void pauseTimer() {

    }

    @Override
    public float getTime() {
        return Engine.getTime();
    }

    @Override
    public void setMoney(int teamID, int amount) {
    }

    @Override
    public void updateInfo(GameObjectID id, String infoKey, Integer infoValue) throws DotaExceptionBase {

    }

    @Override
    public void updateInfo(GameObjectID id, HashMap<String, Integer> newInfo) throws DotaExceptionBase {

    }

    public synchronized GoldMine heroMove(Hero movingHero, int direction) {
        if (movingHero.heroMove(direction, Engine.time)) {
            for (GoldMine goldMine : engine.getGoldMines()) {
                if (goldMine.getCell().getColumn() == movingHero.getCell().getColumn() && goldMine.getCell().getRow() == movingHero.getCell().getRow()) {
                    if (goldMine.ifFree()) {
                        goldMine.setBelong(movingHero.getTeam());
                    } else {
                        if (!goldMine.ifBelong(movingHero.getTeam())) {
                            goldMine.destroy(goldMine, engine.getPaths(), engine.getBarracks(), engine.getAncients());
                            return goldMine;
                        }
                    }
                }
            }
        }
        return null;
    }


}
