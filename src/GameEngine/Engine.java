package GameEngine;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Engine extends Starter{

    public void towerPowerUp(int team, int powerUpType, GameObjectID towerGameObjectID) throws DotaExceptionBase {
        switch (team) {
            case JudgeAbstract.TEAM_SENTINEL: {
                if (!towerSentinelsID.contains(towerGameObjectID))
                    throw new DotaExceptionBase();
                Tower tower = towersSentinels.get(towerSentinelsID.indexOf(towerGameObjectID));
                if (sentinelsAncient.decCoin((int) (tower.getPrice() * .15 * .8)))
                    tower.powerUp(powerUpType);
                break;
            }
            case JudgeAbstract.TEAM_SCOURGE: {
                if (!towerScourgesID.contains(towerGameObjectID))
                    throw new DotaExceptionBase();
                Tower tower = towersScourges.get(towerScourgesID.indexOf(towerGameObjectID));
                if (scourgesAncient.decCoin((int) (tower.getPrice() * .8 * .15)))
                    tower.powerUp(powerUpType);
            }
        }
    }

    public void towerPowerUp(int team , int powerUpType  ,int xTower , int yTower) throws DotaExceptionBase {
        switch (team){
            case JudgeAbstract.TEAM_SENTINEL:  {
                for (int towerIndex = 0; towerIndex < towersSentinels.size(); towerIndex++) {
                    if(towersSentinels.get(towerIndex).getCell().getRow() == xTower && towersSentinels.get(towerIndex).getCell().getColumn() == yTower)
                        towerPowerUp(team , powerUpType , towerSentinelsID.get(towerIndex));
                }
                break;
            }
            case JudgeAbstract.TEAM_SCOURGE:{
                for (int towerIndex = 0; towerIndex < towersScourges.size(); towerIndex++) {
                    if(towersScourges.get(towerIndex).getCell().getRow() == xTower && towersScourges.get(towerIndex).getCell().getColumn() == yTower)
                        towerPowerUp(team , powerUpType , towerScourgesID.get(towerIndex));
                }
                break;
            }
        }
    }



    public GameObjectID heroMove(GameObjectID hero, Cell dest, int direction) throws DotaExceptionBase {
        Hero movingHero = (Hero) mainHashmap.get(hero);
        if (movingHero.getCell().getRow() - dest.getRow() > 1 || movingHero.getCell().getColumn() - dest.getColumn() > 1 ||
                (movingHero.getCell().getRow() == dest.getRow() && movingHero.getCell().getColumn() == dest.getColumn()))
            throw new DotaExceptionBase();
        else {
            if (movingHero.heroMove(direction, time)) {
                for (GoldMine goldMine : goldMines) {
                    if (goldMine.getCell().getColumn() == movingHero.getCell().getColumn() && goldMine.getCell().getRow() == movingHero.getCell().getRow()) {
                        if (goldMine.ifFree()) {
                            goldMine.setBelong(movingHero.getTeam());
                        } else {
                            if (!goldMine.ifBelong(movingHero.getTeam())) {
                                goldMine.destroy(goldMine, paths, barracks, ancients);
                            }
                        }
                    }
                }
                return hero;
            }
            throw new DotaExceptionBase();
        }
    }

    public GameObjectID heroAttack(GameObjectID hero, Cell target) throws DotaExceptionBase {
        Hero attackingHero = (Hero) mainHashmap.get(hero);
        heroAttack(attackingHero, target.getRow(), target.getColumn());
        return hero;
    }

    public void heroAttack(Hero hero,int xCell , int yCell) throws DotaExceptionBase {
        Cell target = new Cell();
        target.setRow(xCell);
        target.setColumn(yCell);
        boolean noObjectFound = true;
        noObjectFound = findTargetForHero(hero, target, noObjectFound);
        if (noObjectFound) {
            Judge.callErr();
            throw new DotaExceptionBase();
        }

    }

    private boolean findTargetForHero(Hero attackingHero, Cell target, boolean noObjectFound) throws DotaExceptionBase//team is hero's enemy
    {
        int team = Judge.TEAM_SENTINEL;
        if (attackingHero.getTeam() == Judge.TEAM_SENTINEL) {
            team = Judge.TEAM_SCOURGE;
        }
        for (Attacker attacker : attackers.get(team))
            if (attacker.getCell().getColumn() == target.getColumn() && attacker.getCell().getRow() == target.getRow()) {
                int coins;
                coins = (int) attackingHero.heroAttackAttacker(attacker, time);
                if (coins != 0) {
                    ancients.get(attackingHero.getTeam()).addCoin(coins);
                    if (team == Judge.TEAM_SCOURGE) {
                        deadAttackersScourges.add(attacker);//*****************************************************************Refactor : this part has repeted
                        deadAttackerScourgesID.add(attackerScourgesID.get(attackers.get(team).indexOf(attacker)));
                    } else {
                        deadAttackersSentinels.add(attacker);
                        deadAttackerSentinelsID.add(attackerSentinelsID.get(attackers.get(team).indexOf(attacker)));
                    }
                    attackers.get(team).remove(attacker);
                    mainHashmap.remove(attackersId.get(team).get(attackers.get(team).indexOf(attacker)));
                    attackersId.get(team).remove(attackersId.get(team).get(attackers.get(team).indexOf(attacker)));
                }
                noObjectFound = false;
            }
        for (Tower tower : towers.get(team))
            if (tower.getCell().getColumn() == target.getColumn() && tower.getCell().getRow() == target.getRow()) {
                int coins;
                coins = (int) attackingHero.heroAttackTower(tower, time);
                if (coins != 0) {
                    ancients.get(attackingHero.getTeam()).addCoin(coins);
                    if (team == Judge.TEAM_SCOURGE) {
                        deadTowersScourges.add(tower);
                        deadTowerScourgesID.add(towerScourgesID.get(towersScourges.indexOf(tower)));
                    } else {
                        deadTowersSentinels.add(tower);
                        deadTowerSentinelsID.add(towerSentinelsID.get(towersSentinels.indexOf(tower)));
                    }
                    mainHashmap.remove(towersId.get(team).get(towers.get(team).indexOf(tower)));
                    towersId.get(team).remove(towersId.get(team).get(towers.get(team).indexOf(tower)));
                    towers.get(team).remove(tower);
                }
                noObjectFound = false;
            }
        if (heros.get(team).getCell().getRow() == target.getRow() && heros.get(team).getCell().getColumn() == target.getColumn()) {
            attackingHero.heroAttackHero(heros.get(team), time);
            noObjectFound = false;
        }

        for (int i = 0; i <ancients.get(team).getCell().length ; i++) {
            for (int j = 0; j <ancients.get(team).getCell()[i].length ; j++) {
                if(ancients.get(team).getCell()[i][j].getColumn()==target.getColumn()&&ancients.get(team).getCell()[i][j].getRow()==target.getRow()){
                    attackingHero.heroAttackAncient(ancients.get(team),Engine.time);
                }
            }
        }
        for (int k = 0; k <barracks.get(team).size() ; k++) {// *************************************************refactoring : we can have barrack and anceint in one abstract class
            for (int i = 0; i < barracks.get(team).get(k).getCells().length; i++) {
                for (int j = 0; j < barracks.get(team).get(k).getCells()[i].length; j++) {
                    if (barracks.get(team).get(k).getCells()[i][j].getColumn() == target.getColumn() && barracks.get(team).get(k).getCells()[i][j].getRow() == target.getRow()) {
                        attackingHero.heroAttackBarrack(barracks.get(team).get(k), Engine.time);
                        noObjectFound = false;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i <ancients.get(team).getCell().length ; i++) {
            for (int j = 0; j <ancients.get(team).getCell()[i].length ; j++) {
                if(ancients.get(team).getCell()[i][j].getColumn()==target.getColumn()&&ancients.get(team).getCell()[i][j].getRow()==target.getRow()){
                    attackingHero.heroAttackAncient(ancients.get(team),Engine.time);
                    noObjectFound=false;
                    break;
                }
            }
        }
        return noObjectFound;
    }


    public GameObjectID createAttacker(int teamID, int attackerType, GameObjectID path, GameObjectID lane, int rowNumber, int culumnNumber) throws DotaExceptionBase {
        Lane attackerLane = null;
        Path attackerPath= null;

        if (lane != null && path != null) {
            attackerLane = (Lane) mainHashmap.get(lane);
            attackerPath = (Path) mainHashmap.get(path);
        }else if(lane == null && path == null){
            for (int i=0 ; i < 3 ; i++){
                for (Lane lanes : paths.get(i).getPathLanes()){
                    if(lanes.ifExist(rowNumber,culumnNumber)){
                        attackerLane = lanes;
                        attackerPath = paths.get(i);
                    }
                }
            }
            if(attackerLane==null||attackerPath==null){
                Judge.callErr();
                return null;
            }
        }else throw new DotaExceptionBase();


        Cell cell = attackerLane.correspondingCell(rowNumber, culumnNumber);
        int index = (teamID == JudgeAbstract.TEAM_SCOURGE) ? attackerLane.laneLength()-1 : 0;

        Attacker attacker = new Attacker(teamID, attackerType, attackerLane, attackerPath, attackerLane.getLaneCells().get(index));
        if(!ancients.get(teamID).decCoin(attacker.getPrice())){
            return null;
        }
        attackers.get(teamID).add(attacker);
        attackersId.get(teamID).add(GameObjectID.create(Attacker.class));
        mainHashmap.put(attackersId.get(teamID).get(attackersId.get(teamID).size()-1),attackers.get(teamID).get(attackers.get(teamID).size() - 1));
        return attackersId.get(teamID).get(attackersId.get(teamID).size()-1);
    }


    public GameObjectID createTower(int teamID, int towerType, GameObjectID path, GameObjectID lane, int index, int rowNumber, int colNumber) throws DotaExceptionBase {
        Lane towerLane = null;
        Path towerPath= null;

        if (lane != null && path != null) {
            towerLane = (Lane) mainHashmap.get(lane);
            towerPath = (Path) mainHashmap.get(path);
        }else if(lane == null && path == null){
            for (int i=0 ; i < 3 ; i++){
                for (Lane lanes : paths.get(i).getPathLanes()){
                    if(lanes.ifExist(rowNumber,colNumber)){
                        towerLane = lanes;
                        towerPath = paths.get(i);
                    }
                }
            }
            if(towerLane==null||towerPath==null){
                Judge.callErr();
                return null;
            }
        }else throw new DotaExceptionBase();

        Cell cell = towerLane.correspondingCell(rowNumber, colNumber);

        if(towerLane!= towerPath.getPathLanes().get(2)){
            Judge.callErr();
            return null;}

        if((towerLane.getLaneCells().size()-towerLane.cellNumber(cell)<=towerLane.getLaneCells().size()/3  && teamID == JudgeAbstract.TEAM_SCOURGE) ||(towerLane.cellNumber(cell)<=towerLane.getLaneCells().size()/3 && teamID == JudgeAbstract.TEAM_SENTINEL) ){
            Tower tower = new Tower(teamID, towerType, cell, towerPath, towerPath.getPathLanes().get(2));
            if(!ancients.get(teamID).decCoin(tower.getPrice())){
                return null;
            }
            towers.get(teamID).add(tower);
            towersId.get(teamID).add(GameObjectID.create(Tower.class));
            mainHashmap.put(towersId.get(teamID).get(towersId.get(teamID).size()-1) ,towers.get(teamID).get(towers.get(teamID).size() -1));
            return towersId.get(teamID).get(towersId.get(teamID).size() -1);
        }else
            Judge.callErr();
        return null;
    }



    // finding target
    public ArrayList<GameObjectID> findTargetForAttacker(GameObjectID attackerID) {
        Attacker attacker = (Attacker) mainHashmap.get(attackerID);

        ArrayList<GameObjectID> finalTarget = new ArrayList<>();
        ArrayList<Attacker> suspectedTarget = new ArrayList<>();

        int teamID = attacker.getTeam();
        int enemyID = (teamID == JudgeAbstract.TEAM_SCOURGE ? JudgeAbstract.TEAM_SENTINEL : JudgeAbstract.TEAM_SCOURGE);


        for (Tower tower_target : towers.get(enemyID)) {
            //searching for tower
            for (int i = 0; i <= attacker.getFieldOfView(); i++) {
                if (attacker.getPath().ifContains(tower_target.getCell()) && (((Math.abs(tower_target.getCell().getRow() - attacker.getCell().getRow()) == i) && Math.abs(tower_target.getCell().getColumn() - attacker.getCell().getColumn()) <= i) ||
                        (Math.abs(tower_target.getCell().getRow() - attacker.getCell().getRow()) <= i && Math.abs(tower_target.getCell().getColumn() - attacker.getCell().getColumn()) == i))) {
                    finalTarget.add(towersId.get(enemyID).get(towers.get(enemyID).indexOf(tower_target)));
                    for (int j = 0; j < attackers.get(enemyID).size(); j++) {
                        if (attacker.getPath().ifContains(tower_target.getCell()) && attackers.get(enemyID).get(j).getCell().getRow() == tower_target.getCell().getRow() && attackers.get(enemyID).get(j).getCell().getColumn() == tower_target.getCell().getColumn()) {
                            finalTarget.add(attackersId.get(enemyID).get(j));
                        }
                    }
                    if (attacker.getPath().ifContains(tower_target.getCell()) && (heros.get(enemyID).getCell().getRow() == tower_target.getCell().getRow()) && (heros.get(enemyID).getCell().getRow() == tower_target.getCell().getColumn()))
                        finalTarget.add(herosId.get(enemyID));
                    return finalTarget;
                }
            }
        }
        int founded_ring = 0;
        boolean attackerFounded = false;
        //searching for Attacker
        for (Attacker attacker_target : attackers.get(enemyID)) {
            for (int i = founded_ring; i <= attacker.getFieldOfView(); ) {
                if (attacker.getPath().ifContains(attacker_target.getCell()) && (((Math.abs(attacker_target.getCell().getRow() - attacker.getCell().getRow()) == i) && Math.abs(attacker_target.getCell().getColumn() - attacker.getCell().getColumn()) <= i) ||
                        (Math.abs(attacker_target.getCell().getRow() - attacker.getCell().getRow()) <= i && Math.abs(attacker_target.getCell().getColumn() - attacker.getCell().getColumn()) == i))) {
                    suspectedTarget.add(attacker_target);
                    founded_ring = i;
                    attackerFounded = true;
                }
                if (suspectedTarget.size() == 0) {
                    i++;
                } else break;
            }
        }
        if (attackerFounded) {
            ArrayList<Attacker> output = new ArrayList<>();
            if (suspectedTarget.size() > 0) {
                output = findDense(suspectedTarget);
                for (Attacker selectedAttacker : output) {
                    finalTarget.add(attackersId.get(enemyID).get(attackers.get(enemyID).indexOf(selectedAttacker)));
                }
            }
            if (heros.get(enemyID).getCell().getRow() == output.get(0).getCell().getRow() && heros.get(enemyID).getCell().getRow() == output.get(0).getCell().getColumn()) {


                finalTarget.add(tinyId);
            }
            return finalTarget;
        }


        ////finding the suspection target

        Barracks suspectedBarrack = null;
        int number = -1;
        if (attacker.getPath().equals(path1)) {
            for(Barracks barrack:barracks.get(enemyID))
                if(barrack.getPathNumber()==0){
                    suspectedBarrack = barrack;
                    number = 0;}
        } else if (attacker.getPath().equals(path2)) {
            for(Barracks barrack:barracks.get(enemyID))
                if(barrack.getPathNumber()==1){
                    suspectedBarrack = barrack;
                    number = 1;}
        } else if (attacker.getPath().equals(path3)) {
            for(Barracks barrack:barracks.get(enemyID))
                if(barrack.getPathNumber()==2){
                    suspectedBarrack = barrack;
                    number = 2;}
        }


        for (int X = 0;number!=-1  && X < suspectedBarrack.getCells().length ; X++) {
            for (int Y = 0; Y < suspectedBarrack.getCells()[X].length; Y++) {
                Cell cell = suspectedBarrack.getCells()[X][Y];
                if (Math.abs(cell.getRow() - attacker.getCell().getRow()) <= attacker.getFieldOfView() && Math.abs(cell.getColumn() - attacker.getCell().getColumn()) <= attacker.getFieldOfView()) {
                    finalTarget.add(barracksId.get(enemyID).get(number));
                    return finalTarget;
                }
            }
        }



        for (int i = 0; i <ancients.get(enemyID).getCell().length ; i++) {
            for (int j = 0; j < ancients.get(enemyID).getCell()[i].length; j++) {
                if (Math.abs(ancients.get(enemyID).getCell()[i][j].getRow() - attacker.getCell().getRow()) <= attacker.getFieldOfView() && Math.abs(ancients.get(enemyID).getCell()[i][j].getColumn() - attacker.getCell().getColumn()) <= attacker.getFieldOfView()) {
                    finalTarget.add(ancientsId.get(enemyID));
                    return finalTarget;
                }
            }
        }

        return null;
    }

    public ArrayList<GameObjectID> findTargetForTower(GameObjectID towerID) {
        ArrayList<GameObjectID> finalTarget = new ArrayList<>();
        ArrayList<Attacker> suspectedTarget = new ArrayList<>();
        ArrayList<Attacker> suspectedTarget2 = new ArrayList<>();
        boolean heroIsNear = false;


        Tower mainTower = (Tower) mainHashmap.get(towerID);
        int teamID = mainTower.getTeam();
        int enemyID = (teamID == 0) ? 1 : 0;

        //first condition
        for (Attacker attacker_suspicious : attackers.get(enemyID)) {
            if (mainTower.getPath().ifContains(attacker_suspicious.getCell()) && Math.abs(attacker_suspicious.getCell().getRow() - mainTower.getCell().getRow()) <= mainTower.getFieldOfView() && Math.abs(attacker_suspicious.getCell().getColumn() - mainTower.getCell().getColumn()) <= mainTower.getFieldOfView()) {

                suspectedTarget.add(attacker_suspicious);
            }
        }


        double minimal = Integer.MAX_VALUE;
        if (suspectedTarget.size() > 0) {
            for (Attacker suspicious : suspectedTarget) {
                switch (suspicious.getType()) {
                    case JudgeAbstract.ATTACKER_INFANTRY: {
                        if ((suspicious.getLife()*1.0 / mainTower.getPowerForInfantry()*1.0) + 1 < minimal) {
                            suspectedTarget2.clear();
                            suspectedTarget2.add(suspicious);
                            minimal = ((suspicious.getLife()*1.0 )/( mainTower.getPowerForInfantry()*1.0)) + 1;
                        } else if ((suspicious.getLife()*1.0 / mainTower.getPowerForInfantry()*1.0) + 1 == minimal) {
                            suspectedTarget2.add(suspicious);
                        }
                        break;
                    }
                    case JudgeAbstract.ATTACKER_TANK: {
                        if ((suspicious.getLife()*1.0 / mainTower.getPowerForTank()*1.0) + 1 < minimal) {
                            suspectedTarget2.clear();
                            suspectedTarget2.add(suspicious);
                            minimal = (suspicious.getLife()*1.0) / (mainTower.getPowerForTank()*1.0) + 1;
                        } else if ((suspicious.getLife() / mainTower.getPowerForTank()) + 1 == minimal) {
                            suspectedTarget2.add(suspicious);
                        }
                    }
                }
            }
        }

        if (mainTower.getPath().ifContains(heros.get(enemyID).getCell()) && Math.abs(heros.get(enemyID).getCell().getRow() - mainTower.getCell().getRow()) <= mainTower.getFieldOfView() && Math.abs(heros.get(enemyID).getCell().getColumn() - mainTower.getCell().getColumn()) <= mainTower.getFieldOfView()) {
            if ((heros.get(enemyID).getLife() / mainTower.getPowerForInfantry()) + 1 < minimal) {
                {
                    finalTarget.add(herosId.get(enemyID));
                    for (Attacker attackerInSameCell : suspectedTarget) {
                        if (heros.get(enemyID).getCell().getRow() == attackerInSameCell.getCell().getRow() && heros.get(enemyID).getCell().getColumn() == attackerInSameCell.getCell().getColumn()) {
                            finalTarget.add(attackersId.get(enemyID).get(attackers.get(enemyID).indexOf(attackerInSameCell)));
                        }
                    }
                    return finalTarget;
                }
            } else if ((heros.get(enemyID).getLife() / mainTower.getPowerForInfantry()) + 1 == minimal) {
                heroIsNear = true;
            }
        }

        if (suspectedTarget.isEmpty() && !heroIsNear) {
            return null;
        } else if (suspectedTarget.isEmpty() && heroIsNear) {
            finalTarget.add(tinyId);
            return finalTarget;
        }

        if (suspectedTarget2.size() == 1) {
            for (Attacker attackerInSameCell : suspectedTarget) {
                if (suspectedTarget2.get(0).getCell().getRow() == attackerInSameCell.getCell().getRow() && suspectedTarget2.get(0).getCell().getColumn() == attackerInSameCell.getCell().getColumn()) {
                    finalTarget.add(attackersId.get(enemyID).get(attackers.get(enemyID).indexOf(attackerInSameCell)));
                }
            }
            return finalTarget;
        }

        //Second Condition
        ArrayList<Cell> suspiciousAttackerCells = new ArrayList<>();
        for (Attacker suspiciousAttacker : suspectedTarget2) {
            suspiciousAttackerCells.add(suspiciousAttacker.getCell());
        }
        if (heroIsNear) {
            suspiciousAttackerCells.add(heros.get(enemyID).getCell());
            heroIsNear = false;
        }

        ArrayList<Integer> nearstCellsIndex = findNearest(suspiciousAttackerCells, teamID);

        if (!heroIsNear) {
            if (nearstCellsIndex.contains(suspiciousAttackerCells.size() - 1)) {
                heroIsNear = true;
                nearstCellsIndex.remove(nearstCellsIndex.size() - 1);
            }
        }
        if (!heroIsNear && nearstCellsIndex.size() == 1) {
            finalTarget.add(attackersId.get(enemyID).get(attackers.get(enemyID).indexOf(suspectedTarget2.get(nearstCellsIndex.get(0)))));
            for (Attacker attackerInSameCell : suspectedTarget) {
                if (suspectedTarget2.get(nearstCellsIndex.get(0)).getCell().getRow() == attackerInSameCell.getCell().getRow() && suspectedTarget2.get(nearstCellsIndex.get(0)).getCell().getColumn() == attackerInSameCell.getCell().getColumn()) {
                    finalTarget.add(attackersId.get(enemyID).get(attackers.get(enemyID).indexOf(attackerInSameCell)));
                }
            }
            return finalTarget;

        }

        //Third condition
        ArrayList<ArrayList<Attacker>> attackerWithSamePlace = new ArrayList<>();
        ArrayList<Integer> cellPrices = new ArrayList<>();
        for (Integer i : nearstCellsIndex) {
            int number = 0;
            for (ArrayList<Attacker> att : attackerWithSamePlace) {
                if (suspectedTarget2.get(i).getCell().getRow() == att.get(0).getCell().getRow() && suspectedTarget2.get(i).getCell().getColumn() == att.get(0).getCell().getColumn()) {
                    attackerWithSamePlace.get(attackerWithSamePlace.indexOf(att)).add(suspectedTarget2.get(i));
                    cellPrices.set(attackerWithSamePlace.indexOf(att), cellPrices.get(attackerWithSamePlace.indexOf(att)) + (int) (suspectedTarget2.get(i).getPrice() * .8));
                    number++;
                }
            }
            if (number == 0) {
                ArrayList<Attacker> newList = new ArrayList<>();
                newList.add(suspectedTarget2.get(i));
                attackerWithSamePlace.add(newList);
                cellPrices.add(suspectedTarget2.get(i).getPrice());
            }
        }


        Integer indexOfCellWithMoreValue = cellPrices.indexOf(Collections.max(cellPrices));
        for (Attacker attackerInSamePlaceWithMorePrices : attackerWithSamePlace.get(indexOfCellWithMoreValue)) {
            finalTarget.add(attackersId.get(enemyID).get(attackers.get(enemyID).indexOf(attackerInSamePlaceWithMorePrices)));
        }
        if (heroIsNear && heros.get(enemyID).getCell().getRow() == attackerWithSamePlace.get(indexOfCellWithMoreValue).get(0).getCell().getRow() && heros.get(enemyID).getCell().getColumn() == attackerWithSamePlace.get(indexOfCellWithMoreValue).get(0).getCell().getColumn()) {
            finalTarget.add(herosId.get(enemyID));
        }
        if (finalTarget.size() > 0) {
            return finalTarget;
        }
        return null;
    }


    private ArrayList<Attacker> findDense(ArrayList<Attacker> suspected) {
        ArrayList<ArrayList<Attacker>> ranked = new ArrayList<>();
        ArrayList<Integer> rankedPrice = new ArrayList<>();

        for (Attacker attacker : suspected) {
            boolean notAdded = true;
            for (ArrayList<Attacker> rankedAttachers : ranked) {
                if (rankedAttachers.get(0).getCell().getRow() == attacker.getCell().getRow() &&
                        rankedAttachers.get(0).getCell().getColumn() == attacker.getCell().getColumn()) {
                    rankedAttachers.add(attacker);
                    rankedPrice.set(ranked.indexOf(rankedAttachers), rankedPrice.get(ranked.indexOf(rankedAttachers)) + attacker.getPrice());
                    notAdded = false;
                    break;
                }
            }
            if (notAdded) {
                ArrayList<Attacker> add = new ArrayList<>();
                add.add(attacker);
                ranked.add(add);
                rankedPrice.add(attacker.getPrice());
            }
        }
        int maxValIndex = rankedPrice.indexOf(Collections.max(rankedPrice));
        return ranked.get(maxValIndex);
    }

    public ArrayList<Integer> findNearest(ArrayList<Cell> cells, int team) {
        ArrayList<Integer> distances = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        Lane whichLane = null;
        int distance;
        for (Cell eachCell : cells) {
            {
                for (Path path : paths){
                    if(path.ifContains(eachCell)){
                        whichLane = path.getLane(eachCell);
                    }
                }
            }
            if (team == JudgeAbstract.TEAM_SENTINEL) {
                distance = whichLane.cellNumber(eachCell);
                distances.add(distance);//
            } else {
                distance = whichLane.laneLength() - whichLane.cellNumber(eachCell);
                distances.add(distance);
            }
        }
        int min_distance = Integer.MAX_VALUE;
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) < min_distance) {
                min_distance = distances.get(i);
                indexes.clear();
                indexes.add(i);
            } else if (distances.get(i) == min_distance) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    GameObjectID getGoldMineID(int goldMineNumber) throws DotaExceptionBase {
        if (goldMineNumber > goldMines.size()) {
            throw new DotaExceptionBase();
        }
        return goldMineID.get(goldMineNumber);
    }


    public void addCoinForGroups() {
        goldMinesOwner(0);
        if (Engine.time % 20 == 0) {
            sentinelsAncient.addCoin(10);
            scourgesAncient.addCoin(10);
            for (int i = 0; i < sentinalGoldMines.size(); i++) {
                sentinelsAncient.addCoin(100);
            }
            for (int i = 0; i < scourgeGoldMines.size(); i++) {
                scourgesAncient.addCoin(100);
            }
        }
    }

    public GameObjectID[] getBuildingID(int teamID, int buildingType) throws DotaExceptionBase {
        GameObjectID[] returnVal = new GameObjectID[1];
        if (teamID == JudgeAbstract.TEAM_SENTINEL && buildingType == JudgeAbstract.BUILDING_TYPE_ANCIENT) {
            returnVal[0] = sentinelsAncientID;
            return returnVal;
        } else if (teamID == JudgeAbstract.TEAM_SENTINEL && buildingType == JudgeAbstract.BUILDING_TYPE_BARRAKS) {
            return (GameObjectID[]) sentinelsBarrakID.toArray();
        } else if (teamID == JudgeAbstract.TEAM_SCOURGE && buildingType == JudgeAbstract.BUILDING_TYPE_ANCIENT) {
            returnVal[0] = scourgesAncientID;
            return returnVal;
        } else if (teamID == JudgeAbstract.TEAM_SCOURGE && buildingType == JudgeAbstract.BUILDING_TYPE_BARRAKS) {
            return (GameObjectID[]) scourgesBarraksID.toArray();
        } else {
            throw new DotaExceptionBase();
        }
    }

    public boolean checkForAttackersAttack(Attacker attackingAttacker) {
        int team = attackingAttacker.getTeam();
        int enemy = (team == JudgeAbstract.TEAM_SENTINEL)? JudgeAbstract.TEAM_SCOURGE : JudgeAbstract.TEAM_SENTINEL ;
        ArrayList<GameObjectID> attackerTargets = findTargetForAttacker(attackersId.get(team).get(attackers.get(team).indexOf(attackingAttacker)));

        if(attackerTargets == null){
            return false;
        }
        for (GameObjectID ID : attackerTargets){
            if(attackersId.get(enemy).contains(ID)){
                attackingAttacker.attack(attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)),Engine.time);
                if (!attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)).isAlive()){
                    deadAttackers.get(enemy).add(attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)));
                    attackers.get(enemy).remove(attackersId.get(enemy).indexOf(ID));
                    mainHashmap.remove(ID);
                    deadAttackersId.get(enemy).add(ID);
                    attackersId.get(enemy).remove(ID);
                }
            }else if(towersId.get(enemy).contains(ID)){
                attackingAttacker.attack(towers.get(enemy).get(towersId.get(enemy).indexOf(ID)),Engine.time);
                if(!towers.get(enemy).get(towersId.get(enemy).indexOf(ID)).isAlive()){
                    towers.get(enemy).remove(towersId.get(enemy).indexOf(ID));
                    mainHashmap.remove(ID);
                    towersId.get(enemy).remove(ID);
                    //          adding this ID to dead tower ramained
                }
            }else if(herosId.get(enemy) == ID){
                attackingAttacker.attackHero(heros.get(enemy),Engine.time);
            }else if(barracksId.get(enemy).contains(ID)){
                barracks.get(enemy).get(barracksId.get(enemy).indexOf(ID)).hurt(attackingAttacker.getPowerForInfantry());
                if(!barracks.get(enemy).get(barracksId.get(enemy).indexOf(ID)).isAlive()){
                    barracks.get(enemy).remove(barracksId.get(enemy).indexOf(ID));
                    barracksId.get(enemy).remove(ID);
                    mainHashmap.remove(ID);
                }
            }else if(ancientsId.get(enemy) == ID){
                ancients.get(enemy).hurt(attackingAttacker.getPowerForInfantry());
                attackingAttacker.setFirstTimeOfShout(Engine.time);
            }
        }
        return true;
    }

    public void checkForAttackersMove() {
        Attacker focusedAttacker;
        ArrayList<Attacker> toBeMoved = new ArrayList<>();
        for (int i = 0; i < attackersScourges.size(); i++) {
            focusedAttacker = attackersScourges.get(i);
            if (!checkForAttackersAttack(focusedAttacker)) {
                toBeMoved.add(focusedAttacker);//focusedAttacker.move(Engine.time);
            }
        }
        for (int i = 0; i < attackersSentinels.size(); i++) {
            focusedAttacker = attackersSentinels.get(i);
            if (!checkForAttackersAttack(focusedAttacker)) {
                toBeMoved.add(focusedAttacker);
            }
        }
        for (int i = 0; i < toBeMoved.size(); i++) {
            focusedAttacker = toBeMoved.get(i);
            if (focusedAttacker.isAlive())
                focusedAttacker.move(Engine.time);
        }
    }

    public void towersAttack(int towerType) {
        ArrayList<GameObjectID> targetsId;

        for (int team = 0; team < 2 ; team++) {
            int enemy = (team == JudgeAbstract.TEAM_SENTINEL)? JudgeAbstract.TEAM_SCOURGE : JudgeAbstract.TEAM_SENTINEL;
            for (Tower tower : towers.get(team)) {
                if(tower.getType() == towerType){
                    targetsId = findTargetForTower(towersId.get(team).get(towers.get(team).indexOf(tower)));
                    if (targetsId == null){
                        tower.setFirstTimeOfShout(Engine.time);
                        return;
                    }else{
                        for (GameObjectID ID : targetsId){
                            if(attackersId.get(enemy).contains(ID)) {
                                tower.attack(attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)), Engine.time);
                                if (!attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)).isAlive()) {
                                    deadAttackers.get(enemy).add(attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)));
                                    attackers.get(enemy).remove(attackers.get(enemy).get(attackersId.get(enemy).indexOf(ID)));

                                    mainHashmap.remove(ID);

                                    attackersId.get(enemy).remove(ID);
                                    deadAttackersId.remove(ID);
                                }
                            }else if(herosId.get(enemy) == ID){
                                tower.attackHero(heros.get(enemy),Engine.time);                           }
                        }
                    }
                }
            }
        }
    }


    public ArrayList<GameObjectID> goldMinesOwner(int team) {
        sentinalGoldMines.clear();
        scourgeGoldMines.clear();

        for (GoldMine goldMine : goldMines) {
            if (!goldMine.ifFree() && goldMine.ifBelong(JudgeAbstract.TEAM_SENTINEL))
                sentinalGoldMines.add(goldMineID.get(goldMines.indexOf(goldMine)));
            else if (!goldMine.ifFree() && goldMine.ifBelong(JudgeAbstract.TEAM_SCOURGE))
                scourgeGoldMines.add(goldMineID.get(goldMines.indexOf(goldMine)));
        }

        switch (team) {
            case JudgeAbstract.TEAM_SENTINEL: {
                return sentinalGoldMines;
            }
            case JudgeAbstract.TEAM_SCOURGE: {
                return scourgeGoldMines;
            }
        }
        return null;
    }

    public HashMap<String, Integer> getInfo(GameObjectID gameObjectID) throws DotaExceptionBase {

        if (deadAttackerSentinelsID.contains(gameObjectID))
            return deadAttackersSentinels.get(deadAttackerSentinelsID.indexOf(gameObjectID)).getInfo();
        if (attackerSentinelsID.contains(gameObjectID))
            return attackersSentinels.get(attackerSentinelsID.indexOf(gameObjectID)).getInfo();

        if (attackerScourgesID.contains(gameObjectID))
            return attackersScourges.get(attackerScourgesID.indexOf(gameObjectID)).getInfo();
        if (deadAttackerScourgesID.contains(gameObjectID))
            return deadAttackersScourges.get(deadAttackerScourgesID.indexOf(gameObjectID)).getInfo();

        if (sentinelsBarrakID.contains(gameObjectID))
            return sentinelsBarraks.get(sentinelsBarrakID.indexOf(gameObjectID)).getInfo();

        if (scourgesBarraksID.contains(gameObjectID))
            return scourgesBarraks.get(scourgesBarraksID.indexOf(gameObjectID)).getInfo();

        if (towerSentinelsID.contains(gameObjectID))
            return towersSentinels.get(towerSentinelsID.indexOf(gameObjectID)).getInfo();
        if (deadTowerSentinelsID.contains(gameObjectID))
            return deadTowersSentinels.get(deadTowerSentinelsID.indexOf(gameObjectID)).getInfo();

        if (towerScourgesID.contains(gameObjectID))
            return towersScourges.get(towerScourgesID.indexOf(gameObjectID)).getInfo();
        if (deadTowerScourgesID.contains(gameObjectID))
            return deadTowersScourges.get(deadTowerScourgesID.indexOf(gameObjectID)).getInfo();

        if (sentinelsAncientID.equals(gameObjectID))
            return sentinelsAncient.getInfo();

        if (scourgesAncientID.equals(gameObjectID))
            return scourgesAncient.getInfo();

        if (tinyId.equals(gameObjectID))
            return heroSentinels.getInfo();

        if (venomancerId.equals(gameObjectID))
            return heroScourges.getInfo();

        throw new DotaExceptionBase();
    }


    public GameObjectID getTarget(GameObjectID gameObjectID) throws DotaExceptionBase {

        if (attackerSentinelsID.contains(gameObjectID))
            return findTargetForAttacker(gameObjectID).get(0);

        if (attackerScourgesID.contains(gameObjectID))
            return findTargetForAttacker(gameObjectID).get(0);

        if (towerSentinelsID.contains(gameObjectID))
            return findTargetForTower(gameObjectID).get(0);

        if (towerScourgesID.contains(gameObjectID))
            return findTargetForTower(gameObjectID).get(0);

        throw new DotaExceptionBase();


    }

    public ArrayList<GameObjectID> getInRange(GameObjectID id) throws DotaExceptionBase {
        ArrayList<GameObjectID> possibleTargets;
        if (attackerSentinelsID.contains(id)) {
            Attacker attacker = attackersSentinels.get(attackerSentinelsID.indexOf(id));
            possibleTargets = findInRange(attacker.getPath(), attacker.getCell(), attacker.getFieldOfView(), JudgeAbstract.TEAM_SCOURGE);
        } else if (attackerScourgesID.contains(id)) {
            Attacker attacker = attackersScourges.get(attackerScourgesID.indexOf(id));
            possibleTargets = findInRange(attacker.getPath(), attacker.getCell(), attacker.getFieldOfView(), JudgeAbstract.TEAM_SENTINEL);
        } else if (towerSentinelsID.contains(id)) {
            Tower tower = towersSentinels.get(towerSentinelsID.indexOf(id));
            possibleTargets = findInRange(tower.getPath(), tower.getCell(), tower.getFieldOfView(), JudgeAbstract.TEAM_SCOURGE);
        } else if (towerScourgesID.contains(id)) {
            Tower tower = towersScourges.get(towerScourgesID.indexOf(id));
            possibleTargets = findInRange(tower.getPath(), tower.getCell(), tower.getFieldOfView(), JudgeAbstract.TEAM_SENTINEL);
        } else if (tinyId == id) {
            possibleTargets = heroFindInRange(heroSentinels.getCell(), heroSentinels.getFieldOfView(), JudgeAbstract.TEAM_SCOURGE);
        } else if (venomancerId == id) {
            possibleTargets = heroFindInRange(heroScourges.getCell(), heroScourges.getFieldOfView(), JudgeAbstract.TEAM_SENTINEL);
        } else throw new DotaExceptionBase();
        return possibleTargets;
    }

    private ArrayList<GameObjectID> findInRange(Path watchingObjectPath, Cell watchingObjectCell, int watchingObjectFieldOfView, int team) {//team to be watched
        ArrayList<GameObjectID> possibleTargets = new ArrayList<>();
        for (Attacker target : attackers.get(team)) {
            if (watchingObjectPath.ifContains(target.getCell()) &&
                    Math.abs(target.getCell().getColumn() - watchingObjectCell.getColumn()) <= watchingObjectFieldOfView &&
                    Math.abs(target.getCell().getRow() - watchingObjectCell.getRow()) <= watchingObjectFieldOfView)
                possibleTargets.add(attackersId.get(team).get(attackers.get(team).indexOf(target)));
        }
        for (Tower target : towers.get(team)) {
            if (watchingObjectPath.ifContains(target.getCell()) &&
                    Math.abs(target.getCell().getColumn() - watchingObjectCell.getColumn()) <= watchingObjectFieldOfView &&
                    Math.abs(target.getCell().getRow() - watchingObjectCell.getRow()) <= watchingObjectFieldOfView)
                possibleTargets.add(towersId.get(team).get(towers.get(team).indexOf(target)));
        }
        if (watchingObjectPath.ifContains(heros.get(team).getCell()) &&
                Math.abs(heros.get(team).getCell().getColumn() - watchingObjectCell.getColumn()) <= watchingObjectFieldOfView &&
                Math.abs(heros.get(team).getCell().getRow() - watchingObjectCell.getRow()) <= watchingObjectFieldOfView)
            possibleTargets.add(herosId.get(team));
        if (watchingObjectPath.equals(path1)) {
            for (int i = 0; i < barracks.get(team).get(0).getCells().length; i++) {
                for (int j = 0; j < barracks.get(team).get(0).getCells()[i].length; j++) {
                    if (barracks.get(team).get(0).getCells()[i][j].getColumn() - watchingObjectCell.getColumn() <= watchingObjectFieldOfView &&
                            barracks.get(team).get(0).getCells()[i][j].getRow() - watchingObjectCell.getRow() <= watchingObjectFieldOfView)
                        possibleTargets.add(barracksId.get(team).get(0));
                }
            }
        } else if (watchingObjectPath.equals(path2)) {
            for (int i = 0; i < barracks.get(team).get(1).getCells().length; i++) {
                for (int j = 0; j < barracks.get(team).get(1).getCells()[i].length; j++) {
                    if (barracks.get(team).get(1).getCells()[i][j].getColumn() - watchingObjectCell.getColumn() <= watchingObjectFieldOfView &&
                            barracks.get(team).get(1).getCells()[i][j].getRow() - watchingObjectCell.getRow() <= watchingObjectFieldOfView)
                        possibleTargets.add(barracksId.get(team).get(1));

                }
            }
        } else if (watchingObjectPath.equals(path3)) {
            for (int i = 0; i < barracks.get(team).get(2).getCells().length; i++) {
                for (int j = 0; j < barracks.get(team).get(2).getCells()[i].length; j++) {
                    if (barracks.get(team).get(2).getCells()[i][j].getColumn() - watchingObjectCell.getColumn() <= watchingObjectFieldOfView &&
                            barracks.get(team).get(2).getCells()[i][j].getRow() - watchingObjectCell.getRow() <= watchingObjectFieldOfView)
                        possibleTargets.add(barracksId.get(team).get(2));

                }
            }
        }
        for (int i = 0; i <= watchingObjectFieldOfView; i++) {
            for (Cell[] cellArrays : ancients.get(team).getCell()) {
                for (Cell cell : cellArrays) {
                    if (Math.abs(cell.getRow() - watchingObjectCell.getRow()) <= i && Math.abs(cell.getColumn() - watchingObjectCell.getColumn()) <= i) {
                        possibleTargets.add(scourgesAncientID);
                    }
                }
            }
        }
        return possibleTargets;
    }

    public Hero getHeroScourges() {
        return heroScourges;
    }

    public Hero getHeroSentinels() {
        return heroSentinels;
    }

    private ArrayList<GameObjectID> heroFindInRange(Cell watchingObjectCell, int watchingObjectFieldOfView, int team) {//team to be watched
        ArrayList<GameObjectID> possibleTargets = new ArrayList<>();
        for (Attacker target : attackers.get(team)) {
            if (
                    Math.abs(target.getCell().getColumn() - watchingObjectCell.getColumn()) <= watchingObjectFieldOfView &&
                            Math.abs(target.getCell().getRow() - watchingObjectCell.getRow()) <= watchingObjectFieldOfView)
                possibleTargets.add(attackersId.get(team).get(attackers.get(team).indexOf(target)));
        }
        for (Tower target : towers.get(team)) {
            if (
                    Math.abs(target.getCell().getColumn() - watchingObjectCell.getColumn()) <= watchingObjectFieldOfView &&
                            Math.abs(target.getCell().getRow() - watchingObjectCell.getRow()) <= watchingObjectFieldOfView)
                possibleTargets.add(towersId.get(team).get(towers.get(team).indexOf(target)));
        }
        if (
                Math.abs(heros.get(team).getCell().getColumn() - watchingObjectCell.getColumn()) <= watchingObjectFieldOfView &&
                        Math.abs(heros.get(team).getCell().getRow() - watchingObjectCell.getRow()) <= watchingObjectFieldOfView)
            possibleTargets.add(herosId.get(team));
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < barracks.get(team).get(k).getCells().length; i++) {
                for (int j = 0; j < barracks.get(team).get(k).getCells()[i].length; j++) {
                    if (barracks.get(team).get(k).getCells()[i][j].getColumn() - watchingObjectCell.getColumn() <= watchingObjectFieldOfView &&
                            barracks.get(team).get(k).getCells()[i][j].getRow() - watchingObjectCell.getRow() <= watchingObjectFieldOfView)
                        possibleTargets.add(barracksId.get(team).get(k));
                }
            }
        }
        for (int i = 0; i <= watchingObjectFieldOfView; i++) {
            for (Cell[] cellArrays : ancients.get(team).getCell()) {
                for (Cell cell : cellArrays) {
                    if (Math.abs(cell.getRow() - watchingObjectCell.getRow()) <= i && Math.abs(cell.getColumn() - watchingObjectCell.getColumn()) <= i) {
                        possibleTargets.add(scourgesAncientID);
                    }
                }
            }
        }
        return possibleTargets;
    }

}