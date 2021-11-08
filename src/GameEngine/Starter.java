package GameEngine;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Starter {
    public static int goldMineNumber = -1;
    public static int time = 0;

    protected HashMap<GameObjectID, Object> mainHashmap = new HashMap<>();
    protected int mapWidth;
    protected int mapHeight;

    protected Ancient sentinelsAncient;
    protected Ancient scourgesAncient;
    protected GameObjectID sentinelsAncientID;
    protected GameObjectID scourgesAncientID;


    protected ArrayList<Barracks> sentinelsBarraks = new ArrayList<>();
    protected ArrayList<Barracks> scourgesBarraks = new ArrayList<>();
    protected ArrayList<GameObjectID> sentinelsBarrakID = new ArrayList<>();
    protected ArrayList<GameObjectID> scourgesBarraksID = new ArrayList<>();

    protected Path path1;
    protected Path path2;
    protected Path path3;
    protected GameObjectID[] pathID = new GameObjectID[3];

    protected Lane[] lanes1 = new Lane[5];
    protected Lane[] lanes2 = new Lane[5];
    protected Lane[] lanes3 = new Lane[5];
    protected GameObjectID[] lane3ID = new GameObjectID[5];
    protected GameObjectID[] lane2ID = new GameObjectID[5];
    protected GameObjectID[] lane1ID = new GameObjectID[5];

    protected Hero heroSentinels;
    protected GameObjectID tinyId;
    protected Hero heroScourges;
    protected GameObjectID venomancerId;

    protected ArrayList<GoldMine> goldMines = new ArrayList<>();
    protected ArrayList<GameObjectID> goldMineID = new ArrayList<>();
    protected ArrayList<GameObjectID> sentinalGoldMines = new ArrayList<>();
    protected ArrayList<GameObjectID> scourgeGoldMines = new ArrayList<>();

    protected ArrayList<Attacker> attackersSentinels = new ArrayList<>();
    protected ArrayList<GameObjectID> attackerSentinelsID = new ArrayList<>();
    protected ArrayList<Attacker> deadAttackersSentinels = new ArrayList<>();
    protected ArrayList<GameObjectID> deadAttackerSentinelsID = new ArrayList<>();

    protected ArrayList<Attacker> attackersScourges = new ArrayList<>();
    protected ArrayList<GameObjectID> attackerScourgesID = new ArrayList<>();
    protected ArrayList<GameObjectID> deadAttackerScourgesID = new ArrayList<>();
    protected ArrayList<Attacker> deadAttackersScourges = new ArrayList<>();

    protected ArrayList<GameObjectID> deadTowerSentinelsID = new ArrayList<>();
    protected ArrayList<GameObjectID> towerSentinelsID = new ArrayList<>();
    protected ArrayList<GameObjectID> towerScourgesID = new ArrayList<>();
    protected ArrayList<GameObjectID> deadTowerScourgesID = new ArrayList<>();

    protected ArrayList<Tower> towersSentinels = new ArrayList<>();
    protected ArrayList<Tower> deadTowersSentinels = new ArrayList<>();
    protected ArrayList<Tower> towersScourges = new ArrayList<>();
    protected ArrayList<Tower> deadTowersScourges = new ArrayList<>();

    protected ArrayList<ArrayList<Attacker>> attackers = new ArrayList<>();
    protected ArrayList<ArrayList<Attacker>> deadAttackers=new ArrayList<>();
    protected ArrayList<ArrayList<GameObjectID>> attackersId = new ArrayList<>();
    protected ArrayList<ArrayList<GameObjectID>> deadAttackersId = new ArrayList<>();
    protected ArrayList<ArrayList<Tower>> towers = new ArrayList<>();
    protected ArrayList<ArrayList<GameObjectID>> towersId = new ArrayList<>();
    protected ArrayList<Ancient> ancients = new ArrayList<>();
    protected ArrayList<GameObjectID> ancientsId = new ArrayList<>();
    protected ArrayList<ArrayList<Barracks>> barracks = new ArrayList<>();
    protected ArrayList<ArrayList<GameObjectID>> barracksId = new ArrayList<>();
    protected ArrayList<Hero> heros = new ArrayList<>();
    protected ArrayList<GameObjectID> herosId = new ArrayList<>();
    protected ArrayList<Path> paths=new ArrayList<>();

    public static int getTime() {
        return time;
    }

    public GameObjectID getTinyId() {
        return tinyId;
    }

    public GameObjectID getVenomancerId() {
        return venomancerId;
    }

    public GameObjectID[] getLane1ID() {
        return lane1ID;
    }

    public GameObjectID[] getLane2ID() {
        return lane2ID;
    }

    public GameObjectID[] getLane3ID() {
        return lane3ID;
    }

    public GameObjectID[] getPathID() {
        return pathID;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }


    public void setSentinelsAncient(Cell[][] cells) {
        sentinelsAncient = new Ancient(cells, JudgeAbstract.TEAM_SENTINEL, JudgeAbstract.BUILDING_TYPE_ANCIENT);
        sentinelsAncientID = GameObjectID.create(Ancient.class);
        mainHashmap.put(sentinelsAncientID, sentinelsAncient);
    }

    public void setScourgesAncient(Cell[][] cells) {
        scourgesAncient = new Ancient(cells, JudgeAbstract.TEAM_SCOURGE, JudgeAbstract.BUILDING_TYPE_ANCIENT);
        scourgesAncientID = GameObjectID.create(Ancient.class);
        mainHashmap.put(scourgesAncientID, sentinelsAncient);
    }

    public void setSentinelsBarraks(ArrayList<Cell[][]> barraks) {

        sentinelsBarraks.add(new Barracks(barraks.get(0), JudgeAbstract.TEAM_SENTINEL, JudgeAbstract.BUILDING_TYPE_BARRAKS,0));
        sentinelsBarrakID.add(GameObjectID.create(Barracks.class));
        mainHashmap.put(sentinelsBarrakID.get(0), sentinelsBarraks.get(0));

        sentinelsBarraks.add(new Barracks(barraks.get(1), JudgeAbstract.TEAM_SENTINEL, JudgeAbstract.BUILDING_TYPE_BARRAKS,1));
        sentinelsBarrakID.add(GameObjectID.create(Barracks.class));
        mainHashmap.put(sentinelsBarrakID.get(1), sentinelsBarraks.get(1));

        sentinelsBarraks.add(new Barracks(barraks.get(2), JudgeAbstract.TEAM_SENTINEL, JudgeAbstract.BUILDING_TYPE_BARRAKS,2));
        sentinelsBarrakID.add(GameObjectID.create(Barracks.class));
        mainHashmap.put(sentinelsBarrakID.get(2), sentinelsBarraks.get(2));

    }

    public void setScourgesBarraks(ArrayList<Cell[][]> barraks) {
        Barracks barracks = new Barracks(barraks.get(0), JudgeAbstract.TEAM_SCOURGE, JudgeAbstract.BUILDING_TYPE_BARRAKS,0);
        scourgesBarraks.add(barracks);
        scourgesBarraksID.add(GameObjectID.create(Barracks.class));
        mainHashmap.put(scourgesBarraksID.get(0), scourgesBarraks.get(0));
        scourgesBarraks.add(new Barracks(barraks.get(1), JudgeAbstract.TEAM_SCOURGE, JudgeAbstract.BUILDING_TYPE_BARRAKS,1));
        scourgesBarraksID.add(GameObjectID.create(Barracks.class));
        mainHashmap.put(scourgesBarraksID.get(1), scourgesBarraks.get(1));

        scourgesBarraks.add(new Barracks(barraks.get(2), JudgeAbstract.TEAM_SCOURGE, JudgeAbstract.BUILDING_TYPE_BARRAKS,2));
        scourgesBarraksID.add(GameObjectID.create(Barracks.class));
        mainHashmap.put(scourgesBarraksID.get(2), scourgesBarraks.get(2));

    }

    public void setPath1(ArrayList<ArrayList<Cell>> path1) {


        this.lanes1[0] = new Lane(path1.get(0));
        lane1ID[0] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane1ID[0], lanes1[0]);


        this.lanes1[1] = new Lane(path1.get(1));
        lane1ID[1] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane1ID[1], lanes1[1]);

        this.lanes1[2] = new Lane(path1.get(2));
        lane1ID[2] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane1ID[2], lanes1[2]);

        this.lanes1[3] = new Lane(path1.get(3));
        lane1ID[3] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane1ID[3], lanes1[3]);

        this.lanes1[4] = new Lane(path1.get(4));
        lane1ID[4] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane1ID[4], lanes1[4]);

        this.path1 = new Path(path1, lanes1);
        pathID[0] = GameObjectID.create(Path.class);
        mainHashmap.put(pathID[0], this.path1);


    }

    public void setPath2(ArrayList<ArrayList<Cell>> path2) {

        this.lanes2[0] = new Lane(path2.get(0));
        lane2ID[0] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane2ID[0], lanes2[0]);


        this.lanes2[1] = new Lane(path2.get(1));
        lane2ID[1] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane2ID[1], lanes2[1]);

        this.lanes2[2] = new Lane(path2.get(2));
        lane2ID[2] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane2ID[2], lanes2[2]);

        this.lanes2[3] = new Lane(path2.get(3));
        lane2ID[3] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane2ID[3], lanes2[3]);

        this.lanes2[4] = new Lane(path2.get(4));
        lane2ID[4] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane2ID[4], lanes2[4]);

        this.path2 = new Path(path2, lanes2);
        pathID[1] = GameObjectID.create(Path.class);
        mainHashmap.put(pathID[1], this.path2);

    }

    public void setPath3(ArrayList<ArrayList<Cell>> path3) {

        this.lanes3[0] = new Lane(path3.get(0));
        lane3ID[0] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane3ID[0], lanes3[0]);


        this.lanes3[1] = new Lane(path3.get(1));
        lane3ID[1] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane3ID[1], lanes3[1]);

        this.lanes3[2] = new Lane(path3.get(2));
        lane3ID[2] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane3ID[2], lanes3[2]);

        this.lanes3[3] = new Lane(path3.get(3));
        lane3ID[3] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane3ID[3], lanes3[3]);

        this.lanes3[4] = new Lane(path3.get(4));
        lane3ID[4] = GameObjectID.create(Lane.class);
        mainHashmap.put(lane3ID[4], lanes3[4]);

        this.path3 = new Path(path3, lanes3);
        pathID[2] = GameObjectID.create(Path.class);
        mainHashmap.put(pathID[2], this.path3);

    }


    public void setGoldMine(ArrayList<Cell> goldMineS) {
        for (Cell goldMineCell : goldMineS) {
            goldMineNumber++;
            goldMines.add(new GoldMine(goldMineCell, mapHeight, mapWidth));
            goldMineID.add(GameObjectID.create(GoldMine.class));
            mainHashmap.put(goldMineID.get(goldMineNumber), goldMines.get(goldMineNumber));
        }
    }

    public void setHero() {
        heroSentinels = new Hero(JudgeAbstract.TEAM_SENTINEL, sentinelsAncient.getCentralHome(), mapHeight, mapWidth);
        tinyId = GameObjectID.create(Hero.class);
        mainHashmap.put(tinyId, heroSentinels);

        heroScourges = new Hero(JudgeAbstract.TEAM_SCOURGE, scourgesAncient.getCentralHome(), mapHeight, mapWidth);
        venomancerId = GameObjectID.create(Hero.class);
        mainHashmap.put(venomancerId, heroScourges);
    }

    public void setUp() {
        attackers.add(attackersSentinels);
        attackers.add(attackersScourges);
        deadAttackers.add(deadAttackersSentinels);
        deadAttackers.add(deadAttackersScourges);
        deadAttackersId.add(deadAttackerSentinelsID);
        deadAttackersId.add(deadAttackerScourgesID);
        attackersId.add(attackerSentinelsID);
        attackersId.add(attackerScourgesID);
        towers.add(towersSentinels);
        towers.add(towersScourges);
        towersId.add(towerSentinelsID);
        towersId.add(towerScourgesID);
        ancients.add(sentinelsAncient);
        ancients.add(scourgesAncient);
        ancientsId.add(sentinelsAncientID);
        ancientsId.add(scourgesAncientID);
        barracks.add(sentinelsBarraks);
        barracks.add(scourgesBarraks);
        barracksId.add(sentinelsBarrakID);
        barracksId.add(scourgesBarraksID);
        heros.add(heroSentinels);
        heros.add(heroScourges);
        herosId.add(tinyId);
        herosId.add(venomancerId);
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
    }

    public ArrayList<ArrayList<Attacker>> getAttackers() {
        return attackers;
    }

    public ArrayList<Ancient> getAncients() {
        return ancients;
    }

    public ArrayList<Hero> getHeros() {
        return heros;
    }

    public ArrayList<ArrayList<Barracks>> getBarracks() {
        return barracks;
    }

    public ArrayList<ArrayList<Tower>> getTowers() {
        return towers;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public ArrayList<GoldMine> getGoldMines() {
        return goldMines;
    }

}
