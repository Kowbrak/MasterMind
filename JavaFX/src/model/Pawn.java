package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.AnimationStep;
import boardifier.view.GridGeometry;

public class Pawn extends GameElement {

    private int number;
    private int color;
    public static int PAWN_BLACK = 6;
    public static int PAWN_RED = 1;
    public static int PAWN_WHITE = 5;
    public static int PAWN_BLUE = 2;
    public static int PAWN_YELLOW = 3;
    public static int PAWN_GREEN = 4;
    public static int PAWN_CYAN = 7;
    public static int PAWN_PURPLE = 8;

    public Pawn(int number, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public void update(double width, double height, GridGeometry gridGeometry) {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step != null) {
                setLocation(step.getInt(0), step.getInt(1));
            }
            else {
                animation = null;
            }
        }
    }
}
