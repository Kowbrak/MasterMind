package boardifier.control;

import boardifier.model.*;
import boardifier.view.ElementLook;
import boardifier.view.GameStageView;
import boardifier.view.GridLook;
import boardifier.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Controller {
    protected Model model;
    protected View view;
    protected String Combination;
    protected int pawnToPlace;
    protected String firstStageName;
    protected Map<GameElement, ElementLook> mapElementLook;
    protected Scanner input = new Scanner(System.in);


    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        firstStageName = "";
        pawnToPlace = 0;
    }

    public void setFirstStageName(String firstStageName) {
        this.firstStageName = firstStageName;
    }

    public void startGame() throws GameException {
        if (firstStageName.isEmpty()) throw new GameException("The name of the first stage have not been set. Abort");
        System.out.println("START THE GAME");
        startStage(firstStageName);
    }

    /**
     * defines what must be done during a stage
     */
    public abstract void stageLoop();

    /**
     * Start a stage of the game.
     * This method MUST NOT BE called directly, except in the endStage() overrideen method.*
     * @param stageName The name of the stage, as registered in the StageFactory.
     * @throws GameException
     */
    protected void startStage(String stageName) throws GameException {
        if (model.isStageStarted()) {
            stopStage();
        }
        setCombinaison();
        model.reset();
        //System.out.println("START STAGE "+stageName);
        // create the model of the stage by using the StageFactory
        GameStageModel gameStageModel = StageFactory.createStageModel(stageName, model);
        // create the elements of the stage by getting the default factory of this stage and giving it to createElements()
        gameStageModel.createElements(gameStageModel.getDefaultElementFactory());
        // create the view of the stage by using the StageFactory
        GameStageView gameStageView = StageFactory.createStageView(stageName, gameStageModel);
        // create the looks of the stage (NB: no factory this time !)
        gameStageView.createLooks();
        // start the game, from the model point of view.
        model.startGame(gameStageModel);
        // set the view so that the current pane view can integrate all the looks of the current game stage view.
        view.setView(gameStageView);
        // create a map of GameElement <-> ElementLook, that helps the controller in its update() method
        mapElementLook = new HashMap<>();
        for(GameElement element : model.getElements()) {
            ElementLook look = gameStageView.getElementLook(element);
            if (look != null) mapElementLook.put(element, look);
        }
    }

    public void setCombinaison(){;
        boolean valide = false;
        System.out.println("Give a combination of 4 colors among the one below: : \n  Red = R\n  Blue = B\n  Yellow = J" +
                "\n  Green = V\n  White = W\n  Black = N\n  Cyan = C\n  Purple = P");
        do{
            this.Combination = input.next();
            if(goodValEnter(Combination, true)){
                valide = true;
            }
        }while(!valide);
        System.out.println("Good combination");
    }

    public boolean goodValEnter(String line, boolean mode) {
        int tmp = 0;
        if(line.length() == 4){
            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) == 'R' || line.charAt(i) == 'B' || line.charAt(i) == 'J' || line.charAt(i) == 'V' || line.charAt(i) == 'W' || line.charAt(i) == 'N' || line.charAt(i) == 'C' || line.charAt(i) == 'P'){
                    tmp++;
                }
                else if(mode){
                    System.out.println("Invalid combination, start again");
                }
            }
        }
        else if(mode){
            System.out.println("Invalid combination, start again");
        }
        if (tmp == 4) {
            return true;
        } else {
            return false;
        }
    }


    public void stopStage() {
        model.stopStage();
    }

    /**
     * Execute actions when it is the next player to play
     * By default, this method does nothing because it is useless in sprite games.
     * In board games, it can be overridden in subclasses to compute who is the
     * next player, and then to take actions if needed. For example, a method of the model can be called to update who is the current player.
     * Then, if it is a computer, a Decider object can be used to determine what to play and then to play it.
     */
    public void nextPlayer() {};

    /**
     * Execute actions at the end of the game.
     * This method defines a default behaviour, which is to display a dialog box with the name of the
     * winner and that proposes to start a new game or to quit.
     */
    public void endGame() {
        System.out.println("END THE GAME");
        if (model.getEnd() == 1) {
            System.out.println("VICTORY\nWell done you won the combination was good : " + Combination);
        } else if(model.getEnd() == 2){
            System.out.println("COMING SOON\nThis AI does not work yet, try to put in argument 3 to have a real functional AI \nor 1 to have an AI that plays randomly");
        }else{
            System.out.println("LOST\nToo bad you had lost the combination was : " + Combination);
        }

    }

    public void update() {
        // update the model of all elements :
        mapElementLook.forEach((k,v) -> {
            // update the element if needed
            k.update();
            // if the element has just been put in a grid (NB: this is different from moving within the same grid)
            if (k.isAutoLocChanged()) {
                setElementLocationToCellCenter(k);
            }
        });
        // update the looks
        view.update();
        // reset changed indicators
        mapElementLook.forEach((k,v) -> {
            k.resetChanged();
        });
    }

    /* ***************************************
       HELPERS METHODS
    **************************************** */

    /**
     * Get the look of a given element
     * @param element the element for which the look is asked.
     * @return an ElementLook object that is the look of the element
     *
     */
    public ElementLook getElementLook(GameElement element) {
        return mapElementLook.get(element);
    }
    /**
     * Get the look of the grid that owns an element
     * @param element the element for which the grid llok is asked.
     * @return an ElementLook object that is the look of the grid that owns the element.
     */
    public GridLook getElementGridLook(GameElement element) {
        return (GridLook) (view.getElementGridLook(element));
    }

    /**
     * Set the location of an element at the center of the cell it is placed.
     * @param element
     */
    public void setElementLocationToCellCenter(GameElement element) {
        if (element.getGrid() == null) return;
        int[] coords = element.getGrid().getElementCell(element); // RECALL: grid is the current grid this element is within
        GridLook gridLook = getElementGridLook(element);
        // get the center of the current cell because we can at least reach this center if Me is not already on it.
        Coord2D center = gridLook.getRootPaneLocationForCellCenter(coords[0], coords[1]);
        element.setLocation(center.getX(), center.getY());
    }

    public void add1PawnAPoser(){
        this.pawnToPlace++;
    }

    public int getPawnToPlace(){
        return this.pawnToPlace;
    }

}
