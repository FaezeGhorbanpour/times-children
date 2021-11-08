package GamePanel;

import GameController.Controller;
import GameEngine.Judge;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainPanel extends JPanel {
    private CellButton[][] cells;

    public MainPanel(Judge judge, Controller controller,int team){
        this.cells = new CellButton[41][41];

        setLayout(new GridLayout(41, 41));
        setBorder(new LineBorder(new Color(160, 82, 45), 5));

        for (int i = 0; i <41 ; i++) {
            for (int j = 0; j <41; j++) {
                cells[i][j]=new CellButton(judge,i,j,team);
                cells[i][j].addMouseListener(controller);
                this.add(cells[i][j]);
            }
        }
    }
}