package Test;/*java project
DefenceOfTheAncients*/

import GameEngine.Cell;

import java.util.ArrayList;

public abstract class Map {

    protected int columns;
    protected int rows;
    protected ArrayList<ArrayList<Cell>> path1;
    protected ArrayList<ArrayList<Cell>> path2;
    protected ArrayList<ArrayList<Cell>> path3;
    protected Cell[][] ancient1;
    protected Cell[][] ancient2;
    protected ArrayList<Cell[][]> barracks1;
    protected ArrayList<Cell[][]> barracks2;
    protected ArrayList<Cell> goldMines;

    public int getColumns() {
        return columns;
    }



    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ArrayList<ArrayList<Cell>> getPath1() {
        return path1;
    }

    public void setPath1(ArrayList<ArrayList<Cell>> path1) {
        this.path1 = path1;
    }

    public ArrayList<ArrayList<Cell>> getPath3() {
        return path3;
    }

    public void setPath3(ArrayList<ArrayList<Cell>> path3) {
        this.path3 = path3;
    }

    public ArrayList<ArrayList<Cell>> getPath2() {
        return path2;
    }

    public void setPath2(ArrayList<ArrayList<Cell>> path2) {
        this.path2 = path2;
    }

    public Cell[][] getAncient1() {
        return ancient1;
    }

    public void setAncient1(Cell[][] ancient1) {
        this.ancient1 = ancient1;
    }

    public Cell[][] getAncient2() {
        return ancient2;
    }

    public void setAncient2(Cell[][] ancient2) {
        this.ancient2 = ancient2;
    }

    public ArrayList<Cell[][]> getBarracks1() {
        return barracks1;
    }

    public void setBarracks1(ArrayList<Cell[][]> barracks1) {
        this.barracks1 = barracks1;
    }

    public ArrayList<Cell[][]> getBarracks2() {
        return barracks2;
    }

    public void setBarracks2(ArrayList<Cell[][]> barracks2) {
        this.barracks2 = barracks2;
    }

    public ArrayList<Cell> getGoldMines() {
        return goldMines;
    }

    public void setGoldMines(ArrayList<Cell> goldMines) {
        this.goldMines = goldMines;
    }

    public void log() {
        System.out.println("rows: " + rows);
        System.out.println("cols: " + columns);
        System.out.println("path1:");
        for (int i = 0; i < 5; i++) {
            System.out.println("\tlane" + i);
            System.out.print("\t\t");
            for (Cell cell : path1.get(i)) {
                System.out.print("(" + cell.getRow() + "," + cell.getColumn()
                        + ") ");
            }
            System.out.println();
        }
        System.out.println("path2:");
        for (int i = 0; i < 5; i++) {
            System.out.println("\tlane" + i);
            System.out.print("\t\t");
            for (Cell cell : path2.get(i)) {
                System.out.print("(" + cell.getRow() + "," + cell.getColumn()
                        + ") ");
            }
            System.out.println();
        }
        System.out.println("path3:");
        for (int i = 0; i < 5; i++) {
            System.out.println("\tlane" + i);
            System.out.print("\t\t");
            for (Cell cell : path3.get(i)) {
                System.out.print("(" + cell.getRow() + "," + cell.getColumn()
                        + ") ");
            }
            System.out.println();
        }
    }
}
