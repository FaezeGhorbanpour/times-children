package GameEngine;
import java.util.ArrayList;


public class Lane {
    private ArrayList<Cell> laneCells;

    public Lane(ArrayList<Cell> cells) {
        laneCells = cells;
    }

    public ArrayList<Cell> getLaneCells() {
        return laneCells;
    }

    public boolean ifExist(Cell cell) {
       return ifExist(cell.getRow(),cell.getColumn());
    }
    public boolean ifExist(int X,int Y){
        for (Cell i : laneCells)
            if (i.getColumn() == Y && i.getRow() == X) {
                return true;
            }
        return false;
    }

    public Cell nextCell(Cell cell, boolean dir) {
        if (dir) {
                return laneCells.get(laneCells.indexOf(cell) + 1);

        } else {

                return laneCells.get(laneCells.indexOf(cell) -1);
        }
    }

    public int cellNumber(Cell cell) {
        for (Cell laneCell : laneCells) {
            if (laneCell.getColumn() == cell.getColumn() && laneCell.getRow() == cell.getRow()) {
                return laneCells.indexOf(laneCell);
            }
        }
        return -1;
    }

    public int laneLength() {
        return laneCells.size();
    }

    public Cell correspondingCell(int x, int y) {
        for (Cell cell : laneCells) {
            if (cell.getColumn() == y && cell.getRow() == x)
                return cell;
        }
        return null;
    }

}
