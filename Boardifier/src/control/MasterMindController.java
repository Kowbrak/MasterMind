package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.GridElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.view.View;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Random;

public class MasterMindController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;
    private String[][] detail = {
            {"R", "B", "J", "V", "W", "N", "C", "P"},
            {"1", "2", "3", "4", "5", "6", "7", "8"},
            {"0", "0", "0", "0", "0", "0", "0", "0"}};

    private char[][] detailChar = {
            {'R', 'B', 'J', 'V', 'W', 'N', 'C', 'P'},
            {'1', '2', '3', '4', '5', '6', '7', '8'},
            {'0', '0', '0', '0', '0', '0', '0', '0'}};
    private int currentColor = 1;
    private int step = 1;
    private char[] combFinal = {'0', '0', '0', '0'};
    private String pawnInCombFinal = "";
    private static final Random lotto = new Random(Calendar.getInstance().getTimeInMillis());

    private int[][] combs;
    private int countCombs;
    private boolean generateCombs;

    /**
     * Constructor of the class MasterMindController
     *
     * @param model, the model of the game
     * @param view,  the view of the game
     */
    public MasterMindController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
        this.combs = new int[12][4];
        this.generateCombs = true;
        this.countCombs = 0;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            nextPlayer();
            update();
        }
        stopStage();
        endGame();
    }

    public void nextPlayer() {
        // for the first player, the id of the player is already set, so do not compute it
        /**if (!firstPlayer) {
         model.setNextPlayer();
         }
         else {
         firstPlayer = false;
         }**/
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            if (p.getName().equals("computerDumb")) {
                analysePlayComputer();
            } else if (p.getName().equals("computerSmart1")) {
                analysePlayComputer2();
            } else if (p.getName().equals("computerSmart2")) {
                analysePlayComputer3();
            }

        } else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName() + " > ");
                //String line = consoleIn.readLine();
                String line = input.next();
                if (line.length() == 4) {
                    ok = analyseAndPlay2(line);
                }
                if (!ok) {
                    System.out.println("incorrect instruction. retry !");
                }
            }
        }
    }

    /**
     * Defines what to do when the game is over
     * @param line
     * @return
     */
    private boolean analyseAndPlay2(String line) {
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        //VERIF
        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            stopStage();
            return true;
        } else if (!goodValEnter(line, false)) {
            return false;
        }
        GridElement board = gameStage.getBoard();
        MasterMindPawnPot whitePot = gameStage.getWhitePot();
        MasterMindPawnPot redPot = gameStage.getRedPot();
        if (!board.isEmptyAt(1, 0)) {
            stopStage();
        }

        // ACTIONS
        ActionList actions = new ActionList(false);
        // Actions that mount the pieces of 1 line
        gameStage.moveLineUp(board, actions);
        // Actions put the "pawsBoard" on the "board"
        GridElement pawnBoard = gameStage.getBoardPotPawn();
        for (int i = 0; i < 4; i++) {
            GameElement pawn = pawnBoard.getElement(pawnToPlace, 0);
            gameStage.setColors(line, pawnToPlace);
            GameAction move = new MoveAction(model, pawn, "mastermindboard", 11, i);
            actions.addSingleAction(move);
            pawnToPlace++;
        }
        // Actions that raise 1 notch the scores of the white and red pawns
        gameStage.upPawnRedWhite();

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();

        // Actions that give the score for the bottom red and white pawns
        gameStage.setNumberPawnDown(line, Combination);

        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
        return true;
    }

    /**
     * Analyse the line entered by the player and play it
     */
    public void analysePlayComputer() {
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        System.out.println("COMPUTER PLAYS");
        String line = setCombRand();
        MasterMindDecider decider = new MasterMindDecider(model, this, line);
        ActionPlayer play = new ActionPlayer(model, this, decider, null);
        play.start();
        gameStage.setNumberPawnDown(line, Combination);
        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
    }

    public void analysePlayComputer2() {
        if (this.generateCombs) {
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

                if(countCombs < 12){
                    for(int i = 0; i < combiAttempt.length; i++){
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

                            if(countCombs < 12){
                                for(int i = 0; i < combiAttempt.length; i++){
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
        String line = "";
        this.generateCombs = false;
        //showTab1D(combi);
        showTab2D(combs);

        int tmp = 0;
        for(int i = 0; i< 4 ; i++){
            for(int j = 0; j < 8; j++){
                if(combs[countCombs][i] == Integer.parseInt(detail[1][j])){
                    tmp = j;
                    break;
                }
            }
            line += detail[0][tmp];
        }
        countCombs++;
        System.out.println("Line : "+line);

        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        System.out.println("COMPUTER PLAYS");
        MasterMindDecider decider = new MasterMindDecider(model, this, line);
        ActionPlayer play = new ActionPlayer(model, this, decider, null);
        play.start();
        gameStage.setNumberPawnDown(line, Combination);
        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
    }

    /**
     * Method that returns the number of well-placed token
     *
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

    public void analysePlayComputer3() {
        if (this.generateCombs) {
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
                if(countCombs < 12){
                    for(int i = 0; i < combiTest.length; i++){
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
                                if(countCombs < 12){
                                    for(int i = 0; i < combiTest.length; i++){
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
        String line = "";
        this.generateCombs = false;
        //showTab1D(combi);
        showTab2D(combs);

        int tmp = 0;
        for(int i = 0; i< 4 ; i++){
            for(int j = 0; j < 8; j++){
                if(combs[countCombs][i] == Integer.parseInt(detail[1][j])){
                    tmp = j;
                    break;
                }
            }
            line += detail[0][tmp];
        }
        countCombs++;
        System.out.println("Line : "+line);

        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        System.out.println("COMPUTER PLAYS");
        MasterMindDecider decider = new MasterMindDecider(model, this, line);
        ActionPlayer play = new ActionPlayer(model, this, decider, null);
        play.start();
        gameStage.setNumberPawnDown(line, Combination);
        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
    }

    public void showTab2D(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            showTab1D(tab[i]);
        }
    }

    public void showTab1D(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i]);
        }
        System.out.println();
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
}
