package GameEngine;

import Util.Utils;

import java.awt.*;
import java.util.HashMap;

public class Barracks {

	private Cell[][] cells;
	private int life ;
	private boolean isAlive = true;
	private int teamID;
	private int buildingTYPE;
    private Image image;
	private int  pathNumber;

	public boolean isAlive() {
		return isAlive;
	}

	public Barracks(Cell[][] cells, int teamID, int type,int pathNumber) {
		this.cells = cells;
		buildingTYPE = type;
		this.teamID = teamID;
		life = 5000;
		this.pathNumber=pathNumber;

		if(teamID==Judge.TEAM_SENTINEL)
			image = Utils.getImage("barrackSentinels.png");
		else
			image = Utils.getImage("barrackScourges.png");

	}

	public void hurt(int lifedec){
		life -= lifedec;
		if(life <= 0){
			isAlive = false;
		}
	}

	public Cell[][] getCells() {
		return cells;
	}

	public HashMap<String, Integer> getInfo() {
		HashMap<String, Integer> barracksInfo = new HashMap<>();
		barracksInfo.put(JudgeAbstract.TEAM_ID,teamID);
		barracksInfo.put(JudgeAbstract.HEALTH,life);
		if (isAlive)
			barracksInfo.put(JudgeAbstract.IS_ALIVE,1);
		else
			barracksInfo.put(JudgeAbstract.IS_ALIVE, 0);
		return barracksInfo;
	}

	public boolean ifContains(int row,int column){
		for (int i = 0; i <cells.length ; i++) {
			for (int j = 0; j <cells[i].length ; j++) {
				if(cells[i][j].getRow()==row&&cells[i][j].getColumn()==column)
					return true;
			}
		}
		return false;
	}

	public Image getImage() {
		return image;
	}

	public int getPathNumber() {
		return pathNumber;
	}
}
