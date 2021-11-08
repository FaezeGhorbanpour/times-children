package Test;


import GameEngine.Cell;

import java.util.ArrayList;

public class MapReader2 extends Map{


    public void init1() {
        columns = 41;
        rows = 41;
        init1_path1();
        init1_path2();
        init1_path3();
        init1_ancient1();
        init1_ancient2();
        init1_barracks();
        init1_goldmines();
    }
    private void init1_ancient1() {
        Cell[][] ancient1Cells = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell c1 = new Cell();
                c1.setRow(24 + i);
                c1.setColumn(1 + j);
                ancient1Cells[i][j] = c1;
            }
        }
        ancient1 = ancient1Cells;
    }

    private void init1_ancient2() {
        Cell[][] ancient2Cells = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell c1 = new Cell();
                c1.setRow(1 + i);
                c1.setColumn(27 + j);
                ancient2Cells[i][j] = c1;
            }
        }
        ancient2 = ancient2Cells;
    }



    private void init1_barracks() {
        Cell[][] barracks11 = new Cell[3][5];
        Cell[][] barracks23 = new Cell[3][5];
        Cell[][] barracks12 = new Cell[5][3];
        Cell[][] barracks13 = new Cell[5][3];
        Cell[][] barracks21 = new Cell[5][3];
        Cell[][] barracks22 = new Cell[3][5];
        ArrayList<Cell[][]> barracksTeam1 = new ArrayList<>();
        ArrayList<Cell[][]> barracksTeam2 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Cell c11 = new Cell();
                Cell c22 = new Cell();
                Cell c23 = new Cell();
                c11.setRow(21+i);
                c11.setColumn(0+j);
                c22.setRow(6 + i);
                c22.setColumn(25 + j);
                c23.setRow(6 + i);
                c23.setColumn(31+j);
                barracks11[i][j] = c11;
                barracks22[i][j] = c22;
                barracks23[i][j] = c23;
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                Cell c12 = new Cell();
                Cell c13 = new Cell();
                Cell c21 = new Cell();
                c12.setRow(i + 21);
                c12.setColumn(6 + j);
                c13.setRow(i + 28);
                c13.setColumn(6 + j);
                c21.setRow(i);
                c21.setColumn(24 + j);
                barracks12[i][j] = c12;
                barracks13[i][j] = c13;
                barracks21[i][j] = c21;
            }
        }
        barracksTeam1.add(barracks11);
        barracksTeam1.add(barracks12);
        barracksTeam1.add(barracks13);
        barracksTeam2.add(barracks21);
        barracksTeam2.add(barracks22);
        barracksTeam2.add(barracks23);
        barracks1 = barracksTeam1;
        barracks2 = barracksTeam2;
    }


    private void init1_goldmines() {
        Cell c1 = new Cell();
        Cell c2 = new Cell();
        Cell c3 = new Cell();
        Cell c4 = new Cell();
        Cell c5 = new Cell();
        Cell c6 = new Cell();
        ArrayList<Cell> golds = new ArrayList<>();
        c1.setRow(3);
        c1.setColumn(3);
        c2.setRow(39);
        c2.setColumn(7);
        c3.setRow(0);
        c3.setColumn(40);
        c4.setRow(39);
        c4.setColumn(25);
        c5.setRow(35);
        c5.setColumn(38);
        c6.setRow(24);
        c6.setColumn(27);
        golds.add(c1);
        golds.add(c2);
        golds.add(c3);
        golds.add(c4);
        golds.add(c5);
        golds.add(c6);
        goldMines = golds;
    }


    private void init1_path3() {
        ArrayList<Cell> lane1 = new ArrayList<>();
        ArrayList<Cell> lane2 = new ArrayList<>();
        ArrayList<Cell> lane3 = new ArrayList<>();
        ArrayList<Cell> lane4 = new ArrayList<>();
        ArrayList<Cell> lane5 = new ArrayList<>();
        for (int i = 9; i <= 30; i++) {
            Cell c1 = new Cell();
            c1.setRow(28);
            c1.setColumn(i);
            lane1.add(c1);
        }
        for (int i = 9; i <= 31; i++) {
            Cell c1 = new Cell();
            c1.setRow(29);
            c1.setColumn(i);
            lane2.add(c1);
        }
        for (int i = 9; i <= 32; i++) {
            Cell c1 = new Cell();
            c1.setRow(30);
            c1.setColumn(i);
            lane3.add(c1);
        }
        for (int i = 9; i <= 33; i++) {
            Cell c1 = new Cell();
            c1.setRow(31);
            c1.setColumn(i);
            lane4.add(c1);
        }
        for (int i = 9; i <= 34; i++) {
            Cell c1 = new Cell();
            c1.setRow(32);
            c1.setColumn(i);
            lane5.add(c1);
        }
        //
        for (int i = 28; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(31);
            lane1.add(c1);
        }
        for (int i = 29; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(32);
            lane2.add(c1);
        }
        for (int i = 30; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(33);
            lane3.add(c1);
        }
        for (int i = 31; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(34);
            lane4.add(c1);
        }
        for (int i = 32; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(35);
            lane5.add(c1);
        }
        ArrayList<ArrayList<Cell>> path3Cells = new ArrayList<>();
        path3Cells.add(lane1);
        path3Cells.add(lane2);
        path3Cells.add(lane3);
        path3Cells.add(lane4);
        path3Cells.add(lane5);
        path3 = path3Cells;
    }

    private void init1_path2() {
        ArrayList<Cell> lane1 = new ArrayList<>();
        ArrayList<Cell> lane2 = new ArrayList<>();
        ArrayList<Cell> lane3 = new ArrayList<>();
        ArrayList<Cell> lane4 = new ArrayList<>();
        ArrayList<Cell> lane5 = new ArrayList<>();
        for (int i = 9; i <= 20; i++) {
            Cell c1 = new Cell();
            c1.setRow(21);
            c1.setColumn(i);
            lane1.add(c1);
        }
        for (int i = 9; i <= 21; i++) {
            Cell c1 = new Cell();
            c1.setRow(22);
            c1.setColumn(i);
            lane2.add(c1);
        }
        for (int i = 9; i <= 22; i++) {
            Cell c1 = new Cell();
            c1.setRow(23);
            c1.setColumn(i);
            lane3.add(c1);
        }
        for (int i = 9; i <= 23; i++) {
            Cell c1 = new Cell();
            c1.setRow(24);
            c1.setColumn(i);
            lane4.add(c1);
        }
        for (int i = 9; i <= 24; i++) {
            Cell c1 = new Cell();
            c1.setRow(25);
            c1.setColumn(i);
            lane5.add(c1);
        }
        //
        for (int i = 20; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(20);
            lane1.add(c1);
        }
        for (int i = 21; i >= 10; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(21);
            lane2.add(c1);
        }
        for (int i = 22; i >=11; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(22);
            lane3.add(c1);
        }
        for (int i = 23; i >= 12; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(23);
            lane4.add(c1);
        }
        for (int i = 24; i >= 13; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(24);
            lane5.add(c1);
        }
        //
        for (int i = 21; i <= 24; i++) {
            Cell c1 = new Cell();
            c1.setRow(9);
            c1.setColumn(i);
            lane1.add(c1);
        }
        for (int i = 22; i <= 25; i++) {
            Cell c1 = new Cell();
            c1.setRow(10);
            c1.setColumn(i);
            lane2.add(c1);
        }
        for (int i = 23; i <= 26; i++) {
            Cell c1 = new Cell();
            c1.setRow(11);
            c1.setColumn(i);
            lane3.add(c1);
        }
        for (int i = 24; i <= 27; i++) {
            Cell c1 = new Cell();
            c1.setRow(12);
            c1.setColumn(i);
            lane4.add(c1);
        }
        for (int i = 25; i <= 28; i++) {
            Cell c1 = new Cell();
            c1.setRow(13);
            c1.setColumn(i);
            lane5.add(c1);
        }
        //
        for (int i = 9; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(25);
            lane1.add(c1);
        }
        for (int i = 10; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(26);
            lane2.add(c1);
        }
        for (int i = 11; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(27);
            lane3.add(c1);
        }
        for (int i = 12; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(28);
            lane4.add(c1);
        }
        for (int i = 13; i >= 9; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(29);
            lane5.add(c1);
        }
        ArrayList<ArrayList<Cell>> path2Cells = new ArrayList<>();
        path2Cells.add(lane1);
        path2Cells.add(lane2);
        path2Cells.add(lane3);
        path2Cells.add(lane4);
        path2Cells.add(lane5);
        path2 = path2Cells;
    }

    private void init1_path1() {
        ArrayList<Cell> lane1 = new ArrayList<>();
        ArrayList<Cell> lane2 = new ArrayList<>();
        ArrayList<Cell> lane3 = new ArrayList<>();
        ArrayList<Cell> lane4 = new ArrayList<>();
        ArrayList<Cell> lane5 = new ArrayList<>();

        for (int i = 20; i >= 15; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(0);
            lane1.add(c1);
        }
        for (int i = 20; i >= 16; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(1);
            lane2.add(c1);
        }
        for (int i = 20; i >= 17; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(2);
            lane3.add(c1);
        }
        for (int i = 20; i >= 18; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(3);
            lane4.add(c1);
        }
        for (int i = 20; i >= 19; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(4);
            lane5.add(c1);
        }
        //
        for (int i = 1; i <= 13; i++) {
            Cell c1 = new Cell();
            c1.setRow(15);
            c1.setColumn(i);
            lane1.add(c1);
        }
        for (int i = 2; i <= 14; i++) {
            Cell c1 = new Cell();
            c1.setRow(16);
            c1.setColumn(i);
            lane2.add(c1);
        }
        for (int i = 3; i <= 15; i++) {
            Cell c1 = new Cell();
            c1.setRow(17);
            c1.setColumn(i);
            lane3.add(c1);
        }
        for (int i = 4; i <= 16; i++) {
            Cell c1 = new Cell();
            c1.setRow(18);
            c1.setColumn(i);
            lane4.add(c1);
        }
        for (int i = 5; i <= 17; i++) {
            Cell c1 = new Cell();
            c1.setRow(19);
            c1.setColumn(i);
            lane5.add(c1);
        }
        //
        for (int i = 15; i >= 0; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(14);
            lane1.add(c1);
        }
        for (int i = 16; i >= 1; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(15);
            lane2.add(c1);
        }
        for (int i = 17; i >= 2; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(16);
            lane3.add(c1);
        }
        for (int i = 18; i >= 3; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(17);
            lane4.add(c1);
        }
        for (int i = 19; i >= 4; i--) {
            Cell c1 = new Cell();
            c1.setRow(i);
            c1.setColumn(18);
            lane5.add(c1);
        }
        //
        for (int i = 15; i <= 23; i++) {
            Cell c1 = new Cell();
            c1.setRow(0);
            c1.setColumn(i);
            lane1.add(c1);
        }
        for (int i = 16; i <= 23; i++) {
            Cell c1 = new Cell();
            c1.setRow(1);
            c1.setColumn(i);
            lane2.add(c1);
        }
        for (int i = 17; i <= 23; i++) {
            Cell c1 = new Cell();
            c1.setRow(2);
            c1.setColumn(i);
            lane3.add(c1);
        }
        for (int i = 18; i <= 23; i++) {
            Cell c1 = new Cell();
            c1.setRow(3);
            c1.setColumn(i);
            lane4.add(c1);
        }
        for (int i = 19; i <= 23; i++) {
            Cell c1 = new Cell();
            c1.setRow(4);
            c1.setColumn(i);
            lane5.add(c1);
        }
        ArrayList<ArrayList<Cell>> path1Cells = new ArrayList<>();
        path1Cells.add(lane1);
        path1Cells.add(lane2);
        path1Cells.add(lane3);
        path1Cells.add(lane4);
        path1Cells.add(lane5);
        path1 = path1Cells;
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
}
