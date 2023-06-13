package boardifier.control;

import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.*;
import control.HoleDecider;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.stage.StageStyle;
import model.HoleBoard;
import model.HolePawnPot;
import model.HoleStageModel;
import model.Pawn;

import java.util.*;

public abstract class Controller {
    protected Model model;
    protected View view;
    protected ControllerAnimation controlAnimation;
    protected ControllerKey controlKey;
    protected ControllerMouse controlMouse;
    protected ControllerAction controlAction;
    protected ContollerButton controlButton;
    protected String firstStageName;
    protected Map<GameElement, ElementLook> mapElementLook;
    private boolean inUpdate, endTmp, generateRandComb;
    protected String Combination;
    private static final Random lotto = new Random(Calendar.getInstance().getTimeInMillis());
    protected int opponent, gameMode;
    int pawnAToPos;
    private String[][] detail = {
            {"R", "B", "J", "V", "W", "N", "C", "P"},
            {"1", "2", "3", "4", "5", "6", "7", "8"},
            {"0", "0", "0", "0", "0", "0", "0", "0"}};

    private char[][] detailChar = {
            {'R', 'B', 'J', 'V', 'W', 'N', 'C', 'P'},
            {'1', '2', '3', '4', '5', '6', '7', '8'},
            {'0', '0', '0', '0', '0', '0', '0', '0'}}; // 0 = not used, 1 = used
    private int[][] combs;
    private int countCombs,diff;
    private boolean generateCombs;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        controlAnimation = new ControllerAnimation(model, view, this);
        firstStageName = "";
        inUpdate = false;
        endTmp = false;
        this.combs = new int[12][4];
        this.generateCombs = true;
        this.countCombs = 0;
        this.pawnAToPos = 0;
        this.diff = 0;
    }

    public int getPawnAToPos(){
        return this.pawnAToPos;
    }

    public void add1PawnAToPos(){
        this.pawnAToPos++;
    }

    public void setControlKey(ControllerKey controlKey) {
        this.controlKey = controlKey;
    }

    public void setControlMouse(ControllerMouse controlMouse) {
        this.controlMouse = controlMouse;
    }

    public void setControlAction(ControllerAction controlAction) {
        this.controlAction = controlAction;
    }

    public void setFirstStageName(String firstStageName) {
        this.firstStageName = firstStageName;
    }

    public void startGame() throws GameException {
        if (firstStageName.isEmpty()) throw new GameException("The name of the first stage have not been set. Abort");
        System.out.println("START THE GAME");
        RootPane rootPane = (RootPane) view.getRootPane();
        this.opponent = getTheOpponent(rootPane);
        this.gameMode = getTheGameMode(rootPane);
        this.generateRandComb = getIfTheRandConf(rootPane);
        System.out.println("opponent : " + this.opponent + " gameMode : " + this.gameMode + " generateRandComb : " + this.generateRandComb);

        model.getPlayers().clear();
        if (this.gameMode == 1) {
            model.addHumanPlayer("player");
        } else if (this.gameMode == 2) {
            model.addComputerPlayer("computerDumb");
        } else if (this.gameMode == 3) {
            model.addComputerPlayer("computerSmart1");
        } else if (this.gameMode == 4) {
            model.addComputerPlayer("computerSmart2");
        }

        startStage(firstStageName);
    }

    public int getTheOpponent(RootPane rootPane) {
        String tmp = ((RadioButton) (rootPane.getGroupOpponent().getSelectedToggle())).getText();
        switch (tmp) {
            case "Mme.Paterlini":
                return 1;
            case "M.Viezzi":
                return 2;
            case "M.Mourad":
                return 3;
            case "M.Perrot":
                return 4;
            case "M.Salomon":
                return 5;
            default:
                return 0;
        }
    }

    public int getTheGameMode(RootPane rootPane) {
        String tmp = ((RadioButton) (rootPane.getGroupIa().getSelectedToggle())).getText();
        switch (tmp) {
            case "Player":
                return 1;
            case "IA_Random":
                return 2;
            case "IA_2":
                return 3;
            case "IA_3":
                return 4;
            default:
                return 0;
        }
    }

    public boolean getIfTheRandConf(RootPane rootPane) {
        if (((RadioButton) (rootPane.getGroupRandConf().getSelectedToggle())).getText().equals("Yes")) {
            return true;
        } else {
            return false;
        }
    }

    public void stopGame() {
        controlAnimation.stopAnimation();
        model.reset();
    }

    /**
     * Start a stage of the game.
     * This method MUST NOT BE called directly, except in the endStage() overrideen method.
     *
     * @param stageName The name of the stage, as registered in the StageFactory.
     * @throws GameException
     */
    protected void startStage(String stageName) throws GameException {
        endTmp = false;
        if (model.isStageStarted()) stopGame();
        System.out.println("START STAGE " + stageName);
        if (this.generateRandComb) {
            this.Combination = setCombRand();
        } else {
            this.Combination = "RVBN";
        }
        // create the model of the stage by using the StageFactory
        GameStageModel gameStageModel = StageFactory.createStageModel(stageName, model);
        // create the elements of the stage by getting the default factory of this stage and giving it to createElements()
        gameStageModel.createElements(gameStageModel.getDefaultElementFactory(), this.Combination);
        // create the view of the stage by using the StageFactory
        GameStageView gameStageView = StageFactory.createStageView(stageName, gameStageModel);
        // create the looks of the stage (NB: no factory this time !)
        gameStageView.createLooks();
        // start the game, from the model point of view.
        model.startGame(gameStageModel);
        // set the view so that the current pane view can integrate all the looks of the current game stage view.
        view.setView(gameStageView);
        /* CAUTION: since starting the game implies to
           remove the intro pane from root, then root has no more
           children. It seems that this removal causes a focus lost
           which must be set once again in order to catch keyboard events.
        */
        view.getRootPane().setFocusTraversable(true);
        view.getRootPane().requestFocus();
        //System.out.println(view.getRootPane().getChildren().get(0).);

        // create a map of GameElement <-> ElementLook, that helps the controller in its update() method
        mapElementLook = new HashMap<>();
        for (GameElement element : model.getElements()) {
            ElementLook look = gameStageView.getElementLook(element);
            mapElementLook.put(element, look);
        }
        controlAnimation.startAnimation();
        System.out.println("Comb : " + this.Combination);

        if (model.getCurrentPlayer().getType() == Player.COMPUTER) {
            if (model.getCurrentPlayer().getName().equals("computerDumb")) {
                System.out.println("computerDumb");
                analyseComputer1();
            } else if (model.getCurrentPlayer().getName().equals("computerSmart1")) {
                System.out.println("computerSmart1");
                analyseComputer3();
            } else if (model.getCurrentPlayer().getName().equals("computerSmart2")) {
                System.out.println("computerSmart2");
                analyseComputer2();
            }
            showComb();
            int i = 0;
            getDiff();
            while(i<12){
                playComputer(i);
                i++;
            }
        }
    }

    public void showComb(){
        for(int i = 0; i<combs.length; i++){
            for(int j = 0; j<combs[i].length; j++){
                System.out.print(combs[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public void getDiff(){
        for(int i = 0; i<combs.length; i++){
            if(combs[i][0] == 0){
                this.diff++;
            }
        }
    }

    public void analyseComputer2() {
        int k = 4; // Number of color balls in the combination
        int n = 8; // Number of different colors
        int[] combi = combiStringToInt(Combination);
        int coul = 1; // Color test�, we start with the first color : 1

        int[] combiAttempt = new int[k]; // Table containing the combination
        // IA attempt
        int[] combiFound = new int[k]; // Table containing the colours
        // found,
        int nbWellPlaced = 0;
        int nbMisplaced = 0;
        int nbFound = 0; // Number of colored balls whose position has been found
        int nbAttempt = 0;
        int pos = 0;

        /*
         * Loop generating attempts until Mastermind resolution
         */

        // as long as the number of goods placed is less than the number of boxes
        // (as long as the MM is not resolved) do
        while (nbGoodPlaced(combi, combiAttempt, k) < k /* && coul <= n */) {
            // We create the new combination to try, which determines the presence
            // or not of a color. While taking into account the positions of the color
            // balls already found
            for (int i = 0; i < k; i++) {
                if (combiFound[i] == 0)
                    combiAttempt[i] = coul;
                else
                    combiAttempt[i] = combiFound[i];
            }
            if (countCombs < 12) {
                for (int i = 0; i < combiAttempt.length; i++) {
                    combs[countCombs][i] = combiAttempt[i];
                }
                countCombs++;
            }
            nbAttempt++;

            // Determine the number of well-placed and misplaced balls
            nbWellPlaced = nbGoodPlaced(combi, combiAttempt, k);
            nbMisplaced = nbCommons(combi, combiAttempt, k)
                    - nbGoodPlaced(combi, combiAttempt, k);
            int nbBalls = nbWellPlaced - nbFound;

            // If the tested color is present (i.e. nbWellPlaced-nbFound >= 1), then do:
            if (nbBalls >= 1 && coul <= (n + 1)) {
                for (int x = 1; x <= nbBalls; x++) {
                    // Set nbMisplaced to a different number than 0
                    nbMisplaced = 1;
                    // Index of the tested position to find the location of the color coul.
                    // We don't test a position that we already know the location of.
                    // So we create a loop that searches for a possible position.
                    pos = 0;
                    // While nbMisplaced != 0, do:
                    while (nbMisplaced > 0) {
                        while ((pos < k) && combiFound[pos] != 0)
                            pos++;
                        // Create the new combination to try, which seeks the exact position
                        // of the current color ball, while taking into account the positions
                        // of the colors balls already found.
                        for (int i = 0; i < k; i++) {
                            if (combiFound[i] == 0) // If the current slot is not a slot that we know
                            // the color of (i.e. combiFound==0)
                            {
                                if (i != pos) // If the slot is not the tested slot, then put
                                    // the next color ball
                                    combiAttempt[i] = coul + 1;
                                else
                                    combiAttempt[i] = coul;
                            } else
                                combiAttempt[i] = combiFound[i];
                        }
                        if (countCombs < 12) {
                            for (int i = 0; i < combiAttempt.length; i++) {
                                combs[countCombs][i] = combiAttempt[i];
                            }
                            countCombs++;
                        }
                        // Test the new attempt
                        // Calculate the number of balls misplaced (assuming there are 0)
                        nbAttempt++;
                        nbWellPlaced = nbGoodPlaced(combi, combiAttempt, k);
                        nbMisplaced = nbCommons(combi, combiAttempt, k) - nbWellPlaced;

                        // Prepare to test the next position
                        pos++;
                    }
                    // At the end of the loop, we have the position of the color ball -> pos - 1
                    // So we add this ball to the combination of balls found.
                    combiFound[pos - 1] = coul;

                    // Increment the number of balls found.
                    nbFound++;
                }
            }
            coul++;
        }
        countCombs = 0;
    }

    public void analyseComputer3() {
        int k = 4; // Number of color balls in the combination
        int n = 8; // Number of different colors
        int[] combi = combiStringToInt(Combination);
        int coul = 1; // Color tested, we start with the 1st color: 1
        int[] combiTest = new int[k]; // Table containing the combination
        // tent� par l'ia
        int[] combiFind = new int[k]; // Table containing the colors
        // trouv�es,
        int nbGoodPlace = 0;
        int nbBadPlace = 0;
        int nbFind = 0; // Number of colored balls of which have found the
        // position
        int nbAttempt = 0;
        int pos = 0;

        int NextCol = 1; // Contains the next color to fill the combination with
        // when searching for the position of the current color

        int nbNextBalls = 0;

        // while the number of well-placed balls is less than the number of slots (i.e., while
        // the MM is not yet solved) use *** the variable nbGoodPlace ***
        while (nbGoodPlaced(combi, combiTest, k) < k && coul <= n) {

            coul = NextCol;
            // We create the new combination to try, which determines the
            // presence or absence of a color. Taking into account the
            // positions of the
            // previously found color balls
            for (int i = 0; i < k; i++) {
                if (combiFind[i] == 0)
                    combiTest[i] = coul;
                else
                    combiTest[i] = combiFind[i];
            }
            //showTab1D(combiTest);
            if (countCombs < 12) {
                for (int i = 0; i < combiTest.length; i++) {
                    combs[countCombs][i] = combiTest[i];
                }
                countCombs++;
            }


            // We verify that the combination being tried is not the one already tried recently


            // We determine the number of well-placed and misplaced balls
            nbGoodPlace = nbGoodPlaced(combi, combiTest, k);
            nbBadPlace = nbCommons(combi, combiTest, k)
                    - nbGoodPlaced(combi, combiTest, k);

            // increment the number of attempted combinations
            nbAttempt++;

            // display the tried combination and the given indications
            //System.out.print("color search : ");
            //printTab(combiTest);
            //System.out.println(" --> "+nbGoodPlace+" well placed, "+nbBadPlace+" misplaced.");
            /*
             * Analysis of the indications given by nbGoodPlace and nbBadPlace
             */

            // 1 - If the number of well-placed balls = k, no need to continue, we have finished the resolution
            if (nbGoodPlace == k)
                break;


            // Determining the number of balls of the current color
            int nbBalls = nbGoodPlace - nbFind;


            // If the color being tested is present (i.e. nbBalls >= 1),
            // then do the following
            if (nbBalls >= 1 && coul <= (n + 1)) {

                do {

                    if (nbNextBalls > 0) // if the color search has been short-circuited
                    {
                        //System.out.println("Short-circuited search");
                        coul = NextCol;
                        nbBalls = nbNextBalls;
                    }
                    nbNextBalls = 0; // initialize the number of balls of the next color

                    NextCol = coul + 1;

                    for (int x = 1; x <= nbBalls; x++) { // search for the position of each color
                        // Set nbBadPlace to a value different from 0
                        nbBadPlace = 1;

                        // index of the position being tested to find the location of the
                        // current color ball. We do not test a position whose color we
                        // already know. Therefore, we create a loop that looks for a
                        // possible position.
                        pos = 0;

                        // while nbBadPlace != 0, do the following
                        while (nbBadPlace > 0) {

                            // look for a position to test that has not already been found
                            while ((pos < k) && combiFind[pos] != 0)
                                pos++;

                            // create the new combination to be tested, which looks for
                            // the exact position of the current color ball, while taking into account the positions of the
                            // already found color balls
                            for (int i = 0; i < k; i++) {

                                if (combiFind[i] == 0) // if the current position is not a position whose color we know (i.e. combiFind==0)
                                {
                                    if (i != pos) // if the position is not the position being tested, we add the next color ball
                                        combiTest[i] = NextCol;
                                    else
                                        combiTest[i] = coul;

                                } else
                                    combiTest[i] = combiFind[i];
                            }
                            //showTab1D(combiTest);
                            if (countCombs < 12) {
                                for (int i = 0; i < combiTest.length; i++) {
                                    combs[countCombs][i] = combiTest[i];
                                }
                                countCombs++;
                            }


                            // Testing the new attempt
                            // Calculation of the number of balls incorrectly
                            // placed (assuming there are 0)
                            nbGoodPlace = nbGoodPlaced(combi, combiTest, k);
                            int nbCommuns = nbCommons(combi, combiTest, k);
                            nbBadPlace = nbCommuns - nbGoodPlace;

                            //  Display of the attempt ***
                            //displayTab(combiTest);
                            //System.out.println(" --> "+nbGoodPlace+" correctly placed, "+nbBadPlace+" incorrectly placed.");
                            nbAttempt++;

                            /*
                             * Analyse des indications donn�es
                             */
                            int nbNextCol = (nbCommuns - nbFind - 1);
                            //System.out.println("NextCol : "+ NextCol+ " nbNextCol : "+nbNextCol);
                            if (nbNextCol <= 0) {
                                NextCol++;
                                //	System.out.println(NextCol);
                            } else
                                nbNextBalls = nbNextCol;

                            pos++;
                        }

                        // At the end of the loop, we have the position of the color ball -> pos - 1
                        // We then add this ball to the combination containing the found balls
                        combiFind[pos - 1] = coul;
                        // We increment the number of found balls
                        nbFind++;
                    }

                }
                while (nbNextBalls > 0 && nbGoodPlace != k); // As long as the following color is present
            } else
                NextCol++;

        }
        countCombs = 0;
    }

    public void analyseComputer1(){
        String lineTmp = "";
        for(int i = 0; i < 12; i++){
            lineTmp = setCombRand();
            char[] line = lineTmp.toCharArray();
            for(int j = 0; j < 4; j++){
                combs[i][j] = convertColorCharToInt(line[j]);
            }
        }
    }

    public void playComputer(int row) {
        if(combs[row][0] == 0){
            return;
        }
        String lineString = "";
        String lineInt = "";
        //showTab1D(combi);
        //showTab2D(combs);
        int tmp = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (combs[countCombs][i] == Integer.parseInt(detail[1][j])) {
                    tmp = j;
                    break;
                }
            }
            lineInt += detail[1][tmp];
            lineString += detail[0][tmp];
        }
        countCombs++;
        System.out.println("LineString : " + lineString+", LineInt : " + lineInt);

        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        ActionList actions = new ActionList(false);
        actions.addActionPack();

        gameStage.setNumberPawnDown(lineInt, row+this.diff, 0);

        char[] lineChar = lineString.toCharArray();
        HoleBoard board = gameStage.getBoard();
        List<GameElement>[][] listBoard = board.getgrid();
        HolePawnPot invisiblePot = gameStage.getInvisiblePot();
        System.out.println(row+ ", " + listBoard[0].length);
        for(int i = 0; i<listBoard[0].length; i++){
            GridLook lookBoard = (GridLook) this.getElementLook(board);
            List<GameElement>[][] listPotInvisible = invisiblePot.getgrid();
            GameElement elementInvisible = listPotInvisible[0][0].get(this.getPawnAToPos());
            System.out.println("pawnAtoPos : " + this.getPawnAToPos() + " row : " + row+this.diff + " col : " + i + ", pawn:"+ elementInvisible);
            this.add1PawnAToPos();
            Coord2D center = lookBoard.getRootPaneLocationForCellCenter(row+this.diff, i);
            GameAction move = new MoveAction(model,elementInvisible,"MasterMindboard",row+this.diff,i, AnimationTypes.MOVE_TELEPORT, center.getX(), center.getY(), 50);
            actions.addPackAction(move);
            elementInvisible.setVisible(true);
            ((Pawn)elementInvisible).setColor(lineChar[i]);
        }

        ActionPlayer play = new ActionPlayer(model, this,actions);
        play.start();

        System.out.println(row);
        if (gameStage.verifWin() == 4) {
            model.stopStage();
            model.setEnd(1);
        } else if (row == 11) {
            model.stopStage();
            model.setEnd(2);
        }
    }

    public int convertColorCharToInt(char c){
        switch(c){
            case 'N':
                return Pawn.PAWN_BLACK;
            case 'R':
                return Pawn.PAWN_RED;
            case 'B':
                return Pawn.PAWN_BLUE;
            case 'J':
                return Pawn.PAWN_YELLOW;
            case 'V':
                return Pawn.PAWN_GREEN;
            case 'W':
                return Pawn.PAWN_WHITE;
            case 'C':
                return Pawn.PAWN_CYAN;
            case 'P':
                return Pawn.PAWN_PURPLE;
            default:
                return -1;
        }
    }

    /**
     * Method that returns the number of well-placed token
     * @param tab1
     * @param tab2
     * @param k
     * @return
     */
    public static int nbGoodPlaced(int[] tab1, int[] tab2, int k) {
        int nb_well_placed = 0;
        for (int i = 0; i < k; i++) {
            if (tab1[i] == tab2[i]) {
                nb_well_placed++;
            }
        }
        return nb_well_placed;
    }

    public int[] combiStringToInt(String combi) {
        int[] combiInt = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (combi.charAt(i) == this.detailChar[0][j]) {
                    combiInt[i] = Character.getNumericValue(this.detailChar[1][j]);
                }
            }
        }
        return combiInt;
    }

    public static int nbCommons(int[] tab1, int[] tab2, int k) {
        int[] t1 = new int[k];
        int[] t2 = new int[k];
        for (int i = 0; i < k; i++) {
            t1[i] = tab1[i];
            t2[i] = tab2[i];
        }

        int cpt = 0;
        boolean find = false;

        for (int i = 0; i < k; i++) {
            int j = 0;
            find = false;
            do {
                if (t1[i] == t2[j]) {
                    cpt++;
                    t2[j] = 0;
                    find = true;
                }
                j++;
            } while (j < k && !find);
        }

        return cpt;
    }

    /**
     * Execute actions needed at the end of each stage.
     * This method does nothing by default. It can be overridden to "compute" the name of the next game stage
     * and to start it. It may also be used update the model, for example by computing reward points, or somthg else.
     */
    public void stopStage() {
        model.stopStage();
        //model.reset();
    }

    /**
     * Execute actions when it is the next player to play
     * By default, this method does nothing because it is useless in sprite games.
     * In board games, it can be overridden in subclasses to compute who is the
     * next player, and then to take actions if needed. For example, a method of the model can be called to update who is the current player.
     * Then, if it is a computer, a Decider object can be used to determine what to play and then to play it.
     */
    public void nextPlayer() {
    }

    ;

    /**
     * Execute actions at the end of the game.
     * This method defines a default behaviour, which is to display a dialog box with the name of the
     * winner and that proposes to start a new game or to quit.
     */
    public void endGame() {
        if (!endTmp) {
            endTmp = true;
            System.out.println("END THE GAME");

            String message = "";
            if (model.getIdWinner() == 1) {
                message = model.getPlayers().get(0).getName() + " wins";
            } else {
                message = "You loose";
            }
            // disable all events
            model.setCaptureEvents(false);
            // create a dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            // remove the frame around the dialog
            alert.initStyle(StageStyle.UNDECORATED);
            // make it a children of the main game window => it appears centered
            alert.initOwner(view.getStage());
            // set the message displayed
            alert.setHeaderText(message);
            // define new ButtonType to fit with our needs => one type is for Quit, one for New Game
            ButtonType quit = new ButtonType("Quit");
            ButtonType newGame = new ButtonType("New Game");
            // remove default ButtonTypes
            alert.getButtonTypes().clear();
            // add the new ones
            alert.getButtonTypes().addAll(quit, newGame);
            // show the dialog and wait for the result
            Optional<ButtonType> option = alert.showAndWait();
            // check if result is quit
            if (option.get() == quit) {
                System.exit(0);
            }
            // check if result is new game
            else if (option.get() == newGame) {
                try {
                    startGame();
                } catch (GameException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            // abnormal case :-)
            else {
                System.err.println("Abnormal case: dialog closed with not choice");
                System.exit(1);
            }
        }

    }

    /**
     * Update model and view.
     * This method MUST NOT BE called directly, and is only called by the ControllerAnimation
     * at each frame. It is used to update the model and then the view.
     * It must be noticed that the process of updating follows a fixed scheme :
     * - update all game element of the current game stage,
     * - update the grid cell of element that are in a grid and that have moved in space, and thus may have changed of cell,
     * - update the looks of all elements, calling dedicated methods according the type indicators of change (location, look, selection, ...),
     * - reset the change indicators in elements,
     * - check if the sage is finished,
     * - check if the game is finished.
     */
    public void update() {
        if (inUpdate) {
            System.err.println("Abnormal case: concurrent updates");
        }
        inUpdate = true;

        // update the model of all elements :
        mapElementLook.forEach((k, v) -> {
            // get the bounds of the look
            Bounds b = v.getGroup().getBoundsInParent();
            // get the geometry of the grid that owns the element, if it exists
            if (k.getGrid() != null) {
                GridLook look = getElementGridLook(k);
                k.update(b.getWidth(), b.getHeight(), look.getGeometry());
            } else {
                k.update(b.getWidth(), b.getHeight(), null);
            }
            // if the element must be auto-localized in its cell center
            if (k.isAutoLocChanged()) {
                setElementLocationToCellCenter(k);
            }
        });
        // update the looks
        view.update();
        // reset changed indicators
        mapElementLook.forEach((k, v) -> {
            k.resetChanged();
        });

        //System.out.println("End Game : "+model.isEndGame());
        if (model.isEndStage()) {
            //controlAnimation.stopAnimation();
            Platform.runLater(() -> {
                stopStage();
            });
        } else if (model.isEndGame()) {
            //controlAnimation.stopAnimation();
            Platform.runLater(() -> {
                endGame();
            });
        }

        inUpdate = false;
    }

    /* ***************************************
       HELPERS METHODS
    **************************************** */

    /**
     * Get the look of a given element
     *
     * @param element the element for which the look is asked.
     * @return an ElementLook object that is the look of the element
     */
    public ElementLook getElementLook(GameElement element) {
        return mapElementLook.get(element);
    }

    /**
     * Get the look of the grid that owns an element
     *
     * @param element the element for which the grid llok is asked.
     * @return an ElementLook object that is the look of the grid that owns the element.
     */
    public GridLook getElementGridLook(GameElement element) {
        return (GridLook) (view.getElementGridLook(element));
    }

    /**
     * Set the location of an element at the center of the cell it is placed.
     *
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

    public void setCombinationLoop() {
        this.Combination = setCombRand();
        System.out.println("Good combination");
    }

    public String setCombRand() {
        String line = "";
        int nb;
        for (int i = 0; i < 4; i++) {
            nb = lotto.nextInt(8);
            if (nb == 0) {
                line += "N";
            } else if (nb == 1) {
                line += "R";
            } else if (nb == 2) {
                line += "B";
            } else if (nb == 3) {
                line += "J";
            } else if (nb == 4) {
                line += "V";
            } else if (nb == 5) {
                line += "W";
            } else if (nb == 6) {
                line += "C";
            } else {
                line += "P";
            }

        }
        //line = "PPPP";
        return line;
    }

    /**
     * Get all visible and clickable elements that are at a given point in the scene coordinate space.
     *
     * @param point the coordinate of a point
     * @return A list of game element
     */
    public List<GameElement> elementsAt(Coord2D point) {
        List<GameElement> list = new ArrayList<>();
        for (GameElement element : model.getElements()) {
            if ((element.isVisible()) && (element.isClickable())) {
                ElementLook look = mapElementLook.get(element);
                if ((look != null) && (look.isPointWithin(point))) {
                    list.add(element);
                }
            }
        }
        return list;
    }
}
