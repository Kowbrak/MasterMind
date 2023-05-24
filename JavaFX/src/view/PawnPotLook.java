package view;

import boardifier.model.GameElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import model.HolePawnPot;

public class PawnPotLook extends GridLook {

    // the array of rectangle composing the grid
    private Rectangle[] cells;

    public PawnPotLook(int width, int height, GameElement element, boolean isVertical) {
        super(width, height, (width-85), (width-87), 0, "0X000000", element);
        int nbPanws = ((HolePawnPot)element).getNbPawns();
        cells = new Rectangle[nbPanws];
        // create the rectangles.
        for(int i=0;i<nbPanws;i++) {
            cells[i] = new Rectangle(cellWidth, cellHeight, Color.WHITE);
            cells[i].setStrokeWidth(3);
            cells[i].setStrokeMiterLimit(10);
            cells[i].setStrokeType(StrokeType.CENTERED);
            cells[i].setStroke(Color.valueOf("0x333333"));
            if(isVertical){
                cells[i].setX(borderWidth);
                cells[i].setY(i*cellHeight+borderWidth);
            }
            else{
                cells[i].setX(i*cellWidth+borderWidth);
                cells[i].setY(borderWidth);
            }
            addShape(cells[i]);
        }
    }

    @Override
    public void onChange() {
    }
}
