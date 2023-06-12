package boardifier.control;

import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import boardifier.model.action.ActionList;
import boardifier.model.Model;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.view.GridLook;
import model.HolePawnPot;
import model.HoleStageModel;
import model.Pawn;

public abstract class Decider {
    protected Model model;
    protected Controller control;

    public Decider(Model model, Controller control) {
        this.model = model;
        this.control = control;
    }

    public abstract ActionList decide();

    public ActionList action(String line){
        int step = 0;
        char[] lineChar = line.toCharArray();
        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();
        ActionList actions = new ActionList(false);
        actions.addActionPack();
        HolePawnPot pawnPotTest = stageModel.getTestPot();
        HolePawnPot pawnPotInvisible = stageModel.getInvisiblePot();
        GridLook lookPawnPotTest = (GridLook) control.getElementLook(pawnPotTest);
        GameElement elementColor;
        GameElement elementInvincible;
        Coord2D center = null;
        //Pawn[] pawns = new Pawn[4];
        Pawn tmp = null;
        for(int i = 0; i<4; i++){
            for(int j= 0; j<8; j++){
                elementColor = pawnPotTest.getElement(j,0);
                elementInvincible = pawnPotInvisible.getElement(control.getPawnAToPos(),0);
                if(elementColor.getClass() == Pawn.class && elementInvincible.getClass() == Pawn.class){


                }
            }
        }

        return actions;
    }
}