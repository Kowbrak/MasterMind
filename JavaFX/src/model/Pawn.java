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
    public static int TYPE_NONE = 0;
    public static int TYPE_SELECTPAWN = 1;
    public static int TYPE_DESTPAWN = 2;

    public Pawn(int number, int color, GameStageModel gameStageModel, int typePawn) {
        super(gameStageModel);
        // registering element types defined especially for this game
        if(typePawn == TYPE_NONE) {
            ElementTypes.register("pawn", 50);
            type = ElementTypes.getType("pawn");
        }else if(typePawn == TYPE_SELECTPAWN) {
            ElementTypes.register("pawnSelect", 60);
            type = ElementTypes.getType("pawnSelect");
        }else if(typePawn == TYPE_DESTPAWN){
            ElementTypes.register("pawnDest",70);
            type = ElementTypes.getType("pawnDest");
        }
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

    public void setNumber(int number){
        this.number = number;
        this.lookChanged = true;
    }

    public void setColor(char c) {
        switch(c){
            case 'N':
                this.color = PAWN_BLACK;
                break;
            case 'R':
                this.color = PAWN_RED;
                break;
            case 'B':
                this.color = PAWN_BLUE;
                break;
            case 'J':
                this.color = PAWN_YELLOW;
                break;
            case 'V':
                this.color = PAWN_GREEN;
                break;
            case 'W':
                this.color = PAWN_WHITE;
                break;
            case 'C':
                this.color = PAWN_CYAN;
                break;
            case 'P':
                this.color = PAWN_PURPLE;
                break;
            default:
                System.out.println("wrong color change (error in Pawn)");break;
        }
        this.lookChanged = true;
    }
}
