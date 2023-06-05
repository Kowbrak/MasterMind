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
    private int pawnAPoser;
    private double[] pawnAPoserCoord = new double[2];

    public HoleControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
        this.pawnAPoser = 0;
    }

    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;
        if(!model.getCurrentPlayer().getName().equals("player")) return;

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

        if((list.size() == 1) && (list.get(0).getClass() == ButtonElement.class)){
            actionButton(clic, stageModel, event);
        }else{
            actionPawn(clic, list, stageModel, event);
        }
    }

    public void actionPawn(Coord2D clic, List<GameElement> list, HoleStageModel stageModel,MouseEvent event){
        if (stageModel.getState() == HoleStageModel.STATE_SELECTPAWN) {
            for (GameElement element : list) {
                if (element.getType() == ElementTypes.getType("pawnSelect")) {
                    element.toggleSelected();
                    stageModel.setState(HoleStageModel.STATE_SELECTDEST);
                    pawnAPoserCoord[0] = event.getSceneX();
                    pawnAPoserCoord[1] = event.getSceneY();
                    return; // do not allow another element to be selecte
                }
            }
        } else if (stageModel.getState() == HoleStageModel.STATE_SELECTDEST) {
            // first check if the click is on the current selected pawn. In this case, unselect it
            for (GameElement element : list) {
                if (element.isSelected()) {
                    element.toggleSelected();
                    stageModel.setState(HoleStageModel.STATE_SELECTPAWN);
                    pawnAPoserCoord[0] = 0.00;
                    pawnAPoserCoord[1] = 0.00;
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
            HolePawnPot invisiblePot = stageModel.getInvisiblePot();
            GameElement pawn = model.getSelected().get(0);

            // thirdly, get the clicked cell in the 3x3 board
            GridLook lookPawnPotTest = (GridLook) control.getElementLook(pawnPotTest);
            GridLook lookColorPot = (GridLook) control.getElementLook(pot);
            GridLook lookInvisiblePot = (GridLook) control.getElementLook(invisiblePot);
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
                List<GameElement> InvisiblePotPawn = invisiblePot.getElements(0, 0);
                System.out.println("InvisiblePotPawn : "+InvisiblePotPawn.toString());
                GameElement invisiblePawn = null;
                for (GameElement element : InvisiblePotPawn) {
                    if(element.getClass() == Pawn.class) {
                        invisiblePawn = element;
                        System.out.println("OUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
                        break;
                    }
                }
                System.out.println("Invisible Pawn : "+invisiblePawn.toString());
                double[] from2 = {pawnAPoserCoord[0], pawnAPoserCoord[1]};
                pawnPotTest.setCellReachable(dest[0], dest[1], false);
                // build the list of actions to do, and pass to the next player when done
                ActionList actions = new ActionList(false);
                actions.addActionPack();
                // determine the destination point in the root pane
                Coord2D center = lookPawnPotTest.getRootPaneLocationForCellCenter(dest[0], dest[1]);
                // create an action with a linear move animation, with 10 pixel/frame
                GameAction move = new MoveAction(model, pawn, "holeboard", dest[0], dest[1], AnimationTypes.MOVE_LINEARPROP, center.getX(), center.getY(), 50);
                // add the action to the action list.
                actions.addPackAction(move);

                // determine the destination point in the root pane
                center = lookColorPot.getRootPaneLocationForCellCenter(from[0], from[1]);
                // create an action with a linear move animation, with 10 pixel/frame
                move = new MoveAction(model, invisiblePawn, "colorPawnPot", from[0], from[1], AnimationTypes.MOVE_TELEPORT, center.getX(), center.getY(), 50);
                // add the action to the action list.
                actions.addPackAction(move);
                System.out.println("From Invisible Pawn : ["+ this.pawnAPoser+",0]"+" to ["+from[0]+","+from[1]+"]");
                this.pawnAPoser++;
                invisiblePawn.setVisible(true);
                invisiblePawn.setType(ElementTypes.getType("pawnSelect"));

                ((Pawn)invisiblePawn).setColor(((Pawn)pawn).getColor());
                System.out.println("Couleurs : "+((Pawn)pawn).getColor());

                stageModel.unselectAll();
                stageModel.setState(HoleStageModel.STATE_SELECTPAWN);
                ActionPlayer play = new ActionPlayer(model, control, actions);
                System.out.println("-----------------------------------------------------------------------");
                play.start();
            }
        }
    }

    public void actionButton(Coord2D clic, HoleStageModel stageModel,MouseEvent event){
        HolePawnPot pawnPotTest = stageModel.getTestPot();
        GridLook lookPawnPotTest = (GridLook) control.getElementLook(pawnPotTest);
        List<GameElement> PawnPotPawn = pawnPotTest.getElements(0,0);
        List<GameElement>[][] test = pawnPotTest.getgrid();

        HolePawnPot test2 = stageModel.getInvisiblePot();
        List<GameElement> listTest = test2.getElements(0, 0);

        System.out.println(test.toString());
        System.out.println(lookPawnPotTest.getElement().toString());

        System.out.println("---------------------------------------------------------------------");
    }
}

