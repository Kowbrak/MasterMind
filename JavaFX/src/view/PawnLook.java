package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Pawn;

public class PawnLook extends ElementLook {
    private Circle circle;
    private boolean numberVisible;
    private Text text;

    /**
     * Visual of the pawn
     * @param radius
     * @param element
     * @param numberVisible
     */
    public PawnLook(int radius, GameElement element,boolean numberVisible) {
        super(element);
        this.numberVisible=numberVisible;
        Pawn pawn = (Pawn)element;
        circle = new Circle();
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            circle.setFill(Color.BLACK);
        }else if(pawn.getColor() == Pawn.PAWN_RED){
            circle.setFill(Color.RED);
        }else if(pawn.getColor() == Pawn.PAWN_WHITE){
            circle.setFill(Color.WHITE);
        }else if(pawn.getColor() == Pawn.PAWN_BLUE){
            circle.setFill(Color.BLUE);
        }else if(pawn.getColor() == Pawn.PAWN_YELLOW) {
            circle.setFill(Color.YELLOW);
        }else if(pawn.getColor() == Pawn.PAWN_GREEN) {
            circle.setFill(Color.GREEN);
        }else if(pawn.getColor() == Pawn.PAWN_CYAN) {
            circle.setFill(Color.CYAN);
        }else if(pawn.getColor() == Pawn.PAWN_PURPLE) {
            circle.setFill(Color.PURPLE);
        }
        circle.setRadius(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        addShape(circle);
        // NB: text won't change so no need to put it as an attribute
        text = new Text(String.valueOf(pawn.getNumber()));
        text.setFont(new Font(20));
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            text.setFill(Color.valueOf("0xFFFFFF"));
            circle.setStroke(Color.valueOf("0xFFFFFF"));
        }
        else {
            text.setFill(Color.valueOf("0x000000"));
            circle.setStroke(Color.GRAY);
        }

        if(!this.numberVisible){
            text.setVisible(false);
        }
        Bounds bt = text.getBoundsInLocal();
        text.setX(radius - bt.getWidth()/2);
        text.setY(text.getBaselineOffset()+ bt.getHeight()/2-3);
        addShape(text);
    }

    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(1);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(Color.valueOf("0x333333"));
        }
        else {
            circle.setStrokeWidth(0);
        }
    }

    @Override
    public void onChange() {
        Pawn pawn = (Pawn)getElement();
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            circle.setFill(Color.BLACK);
        }else if(pawn.getColor() == Pawn.PAWN_RED){
            circle.setFill(Color.RED);
        }else if(pawn.getColor() == Pawn.PAWN_WHITE){
            circle.setFill(Color.WHITE);
        }else if(pawn.getColor() == Pawn.PAWN_BLUE){
            circle.setFill(Color.BLUE);
        }else if(pawn.getColor() == Pawn.PAWN_YELLOW) {
            circle.setFill(Color.YELLOW);
        }else if(pawn.getColor() == Pawn.PAWN_GREEN) {
            circle.setFill(Color.GREEN);
        }else if(pawn.getColor() == Pawn.PAWN_CYAN) {
            circle.setFill(Color.CYAN);
        }else if(pawn.getColor() == Pawn.PAWN_PURPLE) {
            circle.setFill(Color.PURPLE);
        }
        text.setText(String.valueOf(pawn.getNumber()));
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            text.setFill(Color.valueOf("0xFFFFFF"));
            circle.setStroke(Color.valueOf("0xFFFFFF"));
        }
        else {
            text.setFill(Color.valueOf("0x000000"));
            circle.setStroke(Color.GRAY);
        }
    }
}
