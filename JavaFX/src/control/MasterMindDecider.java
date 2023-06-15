package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.HoleStageModel;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MasterMindDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private String line;
    private int row;

    public MasterMindDecider(Model model, Controller control, String line, int row) {
        super(model, control);
        this.line = line;
        this.row = row;
    }

    /**
     * Decide what to do
     * @return
     */
    @Override
    public ActionList decide() {
        char[] lineChar = line.toCharArray();
        ActionList actions = new ActionList(false);
        actions.addActionPack();
        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();

        MasterMindPawnPot invisiblePot = stageModel.getInvisiblePot();
        MasterMindBoard board = stageModel.getBoard();
        List<GameElement>[][] listBoard = board.getgrid();
        stageModel.moveLineUp(listBoard, actions,control);
        System.out.println(this.row + ", " + listBoard[0].length);
        for(int i = 0; i<listBoard[0].length; i++){
            GridLook lookBoard = (GridLook) control.getElementLook(board);
            List<GameElement>[][] listPotInvisible = invisiblePot.getgrid();
            GameElement elementInvisible = listPotInvisible[0][0].get(control.getPawnAToPos());
            System.out.println("pawnAtoPos : " + control.getPawnAToPos() + " row : " + this.row + " col : " + i + ", pawn:"+ elementInvisible);
            control.add1PawnAToPos();
            Coord2D center = lookBoard.getRootPaneLocationForCellCenter(this.row, i);
            GameAction move = new MoveAction(model,elementInvisible,"MasterMindboard",this.row,i, AnimationTypes.MOVE_TELEPORT, center.getX(), center.getY(), 50);
            actions.addPackAction(move);
            elementInvisible.setVisible(true);
        }

        return actions;
    }
}
