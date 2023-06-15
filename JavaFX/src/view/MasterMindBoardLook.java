package view;

import boardifier.model.GameElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import model.MasterMindBoard;

public class MasterMindBoardLook extends GridLook {

    // the array of rectangle composing the grid
    private Rectangle[][] cells;

    /**
     * Visual of the board
     * @param size the size of the board
     * @param element the element to be displayed
     */
    public MasterMindBoardLook(int size, GameElement element) {
        // NB: To have more liberty in the design, GridLook does not compute the cell size from the dimension of the element parameter.
        // If we create the 3x3 board by adding a border of 10 pixels, with cells occupying all the available surface,
        // then, cells have a size of (size-20)/3
        super(size/4, size, 0, "0X000000", element);
        cells = new Rectangle[12][4];
        // create the rectangles.
        for (int i=0;i<12;i++) {
            for(int j=0;j<4;j++) {
                Color c;
                if ((i+j)%2 == 0) {
                    c = Color.BEIGE;
                }
                else {
                    c = Color.DARKGRAY;
                }
                cells[i][j] = new Rectangle(cellWidth, cellHeight, c);
                cells[i][j].setX(j*cellWidth+borderWidth);
                cells[i][j].setY(i*cellHeight+borderWidth);
                addShape(cells[i][j]);
            }
        }
    }

    /**
     * Update the look of the board
     */
    @Override
    public void onChange() {
        // in a pawn is selected, reachableCells changes. Thus, the look of the board must also changes.
        MasterMindBoard board = (MasterMindBoard)element;
        boolean[][] reach = board.getReachableCells();
        for(int i=0;i<12;i++) {
            for(int j=0;j<4;j++) {
                if (reach[i][j]) {
                    cells[i][j].setStrokeWidth(3);
                    cells[i][j].setStrokeMiterLimit(10);
                    cells[i][j].setStrokeType(StrokeType.CENTERED);
                    cells[i][j].setStroke(Color.valueOf("0x333333"));
                } else {
                    cells[i][j].setStrokeWidth(0);
                }
            }
        }
    }
}
