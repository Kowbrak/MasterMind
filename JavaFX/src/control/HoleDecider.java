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
import javafx.geometry.Point2D;
import model.HoleBoard;
import model.HolePawnPot;
import model.HoleStageModel;
import model.Pawn;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class HoleDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private String line;

    public HoleDecider(Model model, Controller control, String line) {
        super(model, control);
        this.line = line;
    }

    @Override
    public ActionList decide() {
        return super.action(line);
        // do a cast get a variable of the real type to get access to the attributes of HoleStageModel
        /*HoleStageModel stage = (HoleStageModel)model.getGameStage();
        HoleBoard board = stage.getBoard(); // get the board
        HolePawnPot pot = null; // the pot where to take a pawn
        GameElement pawn = null; // the pawn that is moved
        int rowDest = 0; // the dest. row in board
        int colDest = 0; // the dest. col in board

        if (model.getIdPlayer() == Pawn.PAWN_BLACK) {
            pot = stage.getBlackPot();
        }
        else {
            pot = stage.getRedPot();
        }

        for(int i=0;i<4;i++) {
            Pawn p = (Pawn)pot.getElement(i,0);
            // if there is a pawn in i.
            if (p != null) {
                // get the valid cells
                List<Point> valid = board.computeValidCells(p.getNumber());
                if (valid.size() != 0) {
                    // choose at random one of the valid cells
                    int id = loto.nextInt(valid.size());
                    pawn = p;
                    rowDest = valid.get(id).y;
                    colDest = valid.get(id).x;
                    break; // stop the loop
                }
            }
        }

        // create action list. After the last action, it is next player's turn.
        ActionList actions = new ActionList(true);
        // get the dest. cell center in space.
        GridLook look = (GridLook) control.getElementLook(board);
        Coord2D center = look.getRootPaneLocationForCellCenter(rowDest, colDest);
        // create the move action
        GameAction move = new MoveAction(model, pawn, "holeboard", rowDest, colDest, AnimationTypes.MOVE_LINEARPROP, center.getX(), center.getY(), 10);
        actions.addSingleAction(move);
        return actions;*/
    }
}
