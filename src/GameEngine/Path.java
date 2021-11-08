package GameEngine;

import Util.Utils;

import java.awt.*;
import java.util.ArrayList;

public class Path {
    private ArrayList<Lane> pathLanes = new ArrayList<>();
    private ArrayList<ArrayList<Cell>> pathCells;
    public static Image image ;
    static {
      image = Utils.getImage("path.png");
    }
    public Path(ArrayList<ArrayList<Cell>> cells, Lane[] lanes) {/////////////////lane haye new shode+cellha
        pathCells = cells;
        pathLanes.add(lanes[0]);
        pathLanes.add(lanes[1]);
        pathLanes.add(lanes[2]);
        pathLanes.add(lanes[3]);
        pathLanes.add(lanes[4]);
    }

    public Lane getLane(Cell cell){
        for (Lane lane:pathLanes){
            for (Cell laneCells:lane.getLaneCells()){
                if(laneCells.getRow()==cell.getRow()&&laneCells.getColumn()==cell.getColumn()){
                    return lane;
                }
            }
        }
        return null;
    }

    public Cell nextCell(Cell cell, boolean direction) {
        for (int i = 0; i < 5; i++) {
            if (pathLanes.get(i).ifExist(cell)) {
                return pathLanes.get(i).nextCell(cell, direction);
            }
        }
        return null;
    } //for right and up enter true as direction ,for left and down enter false as direction

    public boolean ifContains(Cell cell) {
        return ifContains(cell.getRow(),cell.getColumn());
    }

    public ArrayList<Cell> getPathCells() {
        ArrayList<Cell> returnVal=new ArrayList<>();
        for (int i = 0; i <pathCells.size() ; i++) {
            for (int j = 0; j <pathCells.get(i).size() ; j++) {
                returnVal.add(pathCells.get(i).get(j));
            }
        }
        return returnVal;
    }

    public boolean ifContains(int row,int column){
        for (ArrayList<Cell> lane : pathCells) {
            for (Cell insideCell : lane) {
                if (insideCell.getColumn() == column && insideCell.getRow() == row)
                    return true;
            }
        }
        return false;
    }

    public boolean ifIsInFirstOneThird(int row,int column){
        for (Lane lane : pathLanes) {
            for (Cell insideCell : lane.getLaneCells()) {
                if (insideCell.getColumn() == column && insideCell.getRow() == row) {
                    if(lane.cellNumber(insideCell)<=lane.laneLength()/3)
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ifIsInLastOneThird(int row,int column){
        for (Lane lane : pathLanes) {
            for (Cell insideCell : lane.getLaneCells()) {
                if (insideCell.getColumn() == column && insideCell.getRow() == row) {
                    if((lane.laneLength()-lane.cellNumber(insideCell))<=lane.laneLength()/3)
                        return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Lane> getPathLanes() {
        return pathLanes;
    }
}
