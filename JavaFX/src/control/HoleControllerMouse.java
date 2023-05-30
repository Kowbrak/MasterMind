package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.ControllerMouse;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import model.HoleBoard;
import model.HolePawnPot;
import model.HoleStageModel;
import model.Pawn;

import java.util.Arrays;
import java.util.List;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class HoleControllerMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public HoleControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;

        // get the clic x,y in the whole scene (this includes the menu bar if it exists)
        Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());
        // get elements at that position
        List<GameElement> list = control.elementsAt(clic);
        // for debug, uncomment next instructions to display x,y and elements at that postion

        System.out.println("click in "+event.getSceneX()+","+event.getSceneY());
        for(GameElement element : list) {
            System.out.println("element : "+element);
        }

        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();

        if (stageModel.getState() == HoleStageModel.STATE_SELECTPAWN) {
            for (GameElement element : list) {
                if (element.getType() == ElementTypes.getType("pawnSelect")) {
                    element.toggleSelected();
                    stageModel.setState(HoleStageModel.STATE_SELECTDEST);
                    return; // do not allow another element to be selecte
                }
            }
        } else if (stageModel.getState() == HoleStageModel.STATE_SELECTDEST) {
            // first check if the click is on the current selected pawn. In this case, unselect it
            for (GameElement element : list) {
                if (element.isSelected()) {
                    element.toggleSelected();
                    stageModel.setState(HoleStageModel.STATE_SELECTPAWN);
                    return;
                }
            }
            // secondly, search if the board has been clicked. If not just return
            boolean testPotClicked = false;
            for (GameElement element : list) {
                if (element == stageModel.getTestPot()) {
                    testPotClicked = true; break;
                }
            }
            if (!testPotClicked) return;
            // get the board, pot,  and the selected pawn to simplify code in the following
            HolePawnPot pawnPotTest = stageModel.getTestPot();
            // by default get black pot
            HolePawnPot pot = stageModel.getColorPot();
            GameElement pawn = model.getSelected().get(0);

            // thirdly, get the clicked cell in the 3x3 board
            GridLook lookPawnPotTest = (GridLook) control.getElementLook(pawnPotTest);
            System.out.println(lookPawnPotTest.toString());
            int[] dest = lookPawnPotTest.getCellFromSceneLocation(clic);
            // get the cell in the pot that owns the selected pawn
            int[] from = pot.getElementCell(pawn);
            //System.out.println(Arrays.toString(from));
            //System.out.println(Arrays.toString(dest));
            System.out.println("try to move pawn from pot "+from[0]+","+from[1]+ " to board "+ dest[0]+","+dest[1]);
            // if the destination cell is valid for the selected pawn
            System.out.println("nb ligne : "+pawnPotTest.getNbRows()+" nb colonne : "+pawnPotTest.getNbCols());
            if (pawnPotTest.canReachCell(dest[0], dest[1])) {
                pawnPotTest.setCellReachable(dest[0], dest[1], false);
                // build the list of actions to do, and pass to the next player when done
                ActionList actions = new ActionList(false);
                // determine the destination point in the root pane
                Coord2D center = lookPawnPotTest.getRootPaneLocationForCellCenter(dest[0], dest[1]);
                // create an action with a linear move animation, with 10 pixel/frame
                GameAction move = new MoveAction(model, pawn, "holeboard", dest[0], dest[1], AnimationTypes.MOVE_LINEARPROP, center.getX(), center.getY(), 50);
                // add the action to the action list.
                actions.addSingleAction(move);
                stageModel.unselectAll();
                stageModel.setState(HoleStageModel.STATE_SELECTPAWN);
                ActionPlayer play = new ActionPlayer(model, control, actions);
                play.start();
            }
        }
    }
}

