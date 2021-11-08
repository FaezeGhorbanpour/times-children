package GamePanel;

import GameEngine.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class CellButton extends JButton {
    private Judge judge;
    private Image image = null;
    private static Image backImage = null;
    private static Image hiddenImage = null;
    private int column;
    private int row;
    private int team;

    static {
        try {
            backImage = ImageIO.read(new File(("image/Background.jpg")));
            hiddenImage = ImageIO.read(new File(("image/brush.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CellButton(Judge judge, int row, int column, int team) {
        this.judge = judge;
        this.row = row;
        this.column = column;
        findHiddenCellImage();
        setFocusable(false);
        this.team = team;
    }

    private Image findCellImage() {
        for (Hero hero : judge.getEngine().getHeros())
            if (hero.getCell().getRow() == row && hero.getCell().getColumn() == column && hero.getLife() > 0) {
                this.setToolTipText(writingInfo(hero.getInfo()));
                return hero.getImage();

            }
        for (int team = 0; team < 2; team++) {
            for (Attacker attacker : judge.getEngine().getAttackers().get(team)) {
                if (attacker.getCell().getRow() == row && attacker.getCell().getColumn() == column) {
                    this.setToolTipText(writingInfo(attacker.getInfo()));
                    return attacker.getImage();
                }
            }
            for (Tower tower : judge.getEngine().getTowers().get(team)) {
                if (tower.getCell().getColumn() == column && tower.getCell().getRow() == row) {
                    this.setToolTipText(writingInfo(tower.getInfo()));
                    return tower.getImage();
                }
            }
        }
        for (GoldMine goldMine : judge.getEngine().getGoldMines()) {
            if (goldMine.getCell().getColumn() == column && goldMine.getCell().getRow() == row) {
                return goldMine.getImage();
            }
        }
        for (Barracks barracks : judge.getEngine().getBarracks().get(0)) {
            if (barracks.ifContains(row, column)) {
                return barracks.getImage();
            }
        }
        for (Barracks barracks : judge.getEngine().getBarracks().get(1)) {
            if (barracks.ifContains(row, column)) {
                this.setToolTipText(writingInfo(barracks.getInfo()));
                return barracks.getImage();
            }
        }
        for (Ancient ancient : judge.getEngine().getAncients()) {
            if (ancient.ifContains(row, column)) {
                this.setToolTipText(writingInfo(ancient.getInfo()));
                return ancient.getImage();
            }
        }
        for (Path path : judge.getEngine().getPaths()) {
            if (path.ifContains(row, column)) {
                return Path.image;
            }
        }
        return backImage;
    }

    private void findHiddenCellImage() {
        if (inSumFieldOfOurTeam())
            image = findCellImage();
        else
            image = hiddenImage;
    }

    private boolean inSumFieldOfOurTeam() {
        if (judge.getEngine().getHeros().get(team).isInFieldOfView(row, column) && judge.getEngine().getHeros().get(team).getLife() > 0)
            return true;
        for (Attacker attacker : judge.getEngine().getAttackers().get(team)) {
            if (attacker.isInFieldOfView(row, column)) {
                return true;
            }
        }
        for (Tower tower : judge.getEngine().getTowers().get(team)) {
            if (tower.isInFieldOfView(row, column)) {
                return true;
            }
        }
        for (Path path : judge.getEngine().getPaths()) {
            if ((path.ifIsInFirstOneThird(row, column) && team == Judge.TEAM_SENTINEL) || (path.ifIsInLastOneThird(row, column) && team == Judge.TEAM_SCOURGE)) {
                return true;
            }
        }
        if (judge.getEngine().getAncients().get(team).ifContains(row, column)) {
            return true;
        }
        for (Barracks barracks : judge.getEngine().getBarracks().get(team)) {
            if (barracks.ifContains(row, column)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        findHiddenCellImage();
        g2d.drawImage(image, 0, 0, this.getWidth(),
                this.getHeight(), this);

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String writingInfo(HashMap hashMap) {
        String string = "";
        for (Object keys : hashMap.keySet()) {
            string +=  keys + " = " + hashMap.get(keys) + ".\n";
        }
        return string;
    }

}
