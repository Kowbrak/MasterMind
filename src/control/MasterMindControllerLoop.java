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

public class MasterMindControllerLoop extends Controller {

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
    private int currentcolor = 1;
    private int step = 1;
    private char[] combFinal = {'0', '0', '0', '0'};
    private String pawnInCombFinal = "";
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private int[][] combs;
    private int countCombs;
    private boolean generateCombs;

    public MasterMindControllerLoop(Model model, View view) {
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
        //  Actions that move the board's pawns up by 1 row
        gameStage.moveLineUp(board, actions);
        //  Actions that place the "pawsBoard" on the board
        GridElement pawnBoard = gameStage.getBoardPotPawn();
        for (int i = 0; i < 4; i++) {
            GameElement pawn = pawnBoard.getElement(pawnToPlace, 0);
            gameStage.setColors(line, pawnToPlace);
            GameAction move = new MoveAction(model, pawn, "mastermindboard", 11, i);
            actions.addSingleAction(move);
            pawnToPlace++;
        }
        // Actions that move up the scores of white and red pawns by 1 step
        gameStage.upPawnRedWhite();

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();

        // Actions that calculate the score for the pawns on the bottom row (red and white)
        gameStage.setNumberPawnDown(line, Combination);

        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
        return true;
    }

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
        System.out.println(super.getPawnToPlace()/4);
    }

    public void analysePlayComputer2() {
        if (this.generateCombs) {/*
            countCombs = 0;
            for(int i = 0; i < 12; i++) {
                for(int j = 0; j < 4; j++) {
                    this.combs[i][j] = 0;
                }
            }*/
            int k = 4; // Number of color balls in the combination
            int n = 8; // Number of different colors
            int[] combi = combiStringToInt(Combination);
            int coul = 1; //  Test color, we start with the 1st color: 1

            int[] combiTried = new int[k]; // Array containing the combination tried by the AI
            int[] combiFind = new int[k]; // Array containing the colors found
            int nbWellPlaced = 0;
            int nbMisplaced = 0;
            int nbFind = 0; // Number of colored balls whose position we have found
            int nbAttempt = 0;
            int pos = 0;


            /*
             * Loop generating attempts until the MM is solved
             */

            // while the number of well-placed balls is less than the number of
            // slots (i.e., while the MM is not solved) do
            while (nbWellPlaced(combi, combiTried, k) < k /* && coul <= n */) {

                // We create the new combination to try, which determines the
                // presence or absence of a color. While taking into account the
                // positions of the colored balls already found
                for (int i = 0; i < k; i++) {
                    if (combiFind[i] == 0)
                        combiTried[i] = coul;
                    else
                        combiTried[i] = combiFind[i];
                }

                if(countCombs < 12){
                    for(int i = 0; i < combiTried.length; i++){
                        combs[countCombs][i] = combiTried[i];
                    }
                    countCombs++;
                }

                nbAttempt++;

                // We determine the number of well-placed and misplaced balls
                nbWellPlaced = nbWellPlaced(combi, combiTried, k);
                nbMisplaced = nbCommon(combi, combiTried, k)
                        - nbWellPlaced(combi, combiTried, k);
                int nbBalls = nbWellPlaced - nbFind;

                // If the color being tested is present (i.e. nbWellPlaced-nbFind >= 1) do
                if (nbBalls >= 1 && coul <= (n + 1)) {

                    for (int x = 1; x <= nbBalls; x++) {
                        // We set nbMisplaced to a different number than 0
                        nbMisplaced = 1;

                        // index of the position being tested to find the location
                        // of the coul color ball. We don't test a position whose
                        // color we already know. So we create a loop that looks for
                        // a possible position.
                        pos = 0;

                        // while nbMisplaced != 0 do
                        while (nbMisplaced > 0) {
                            while ((pos < k) && combiFind[pos] != 0)
                                pos++;
                            // We create the new combination to try, which looks for
                            // the exact position of the current color ball. While taking
                            // into account the positions of the colored balls already found
                            for (int i = 0; i < k; i++) {
                                if (combiFind[i] == 0) // If the current slot
                                // is not a slot whose color we already know (i.e., combiFind==0)
                                {
                                    if (i != pos) // If the slot is not the slot being tested,
                                        // we put the next color ball
                                        combiTried[i] = coul + 1;
                                    else
                                        combiTried[i] = coul;

                                } else
                                    combiTried[i] = combiFind[i];
                            }

                            if(countCombs < 12){
                                for(int i = 0; i < combiTried.length; i++){
                                    combs[countCombs][i] = combiTried[i];
                                }
                                countCombs++;
                            }

                            // Testing the new attempt
                            // Calculation of the number of balls misplaced (hoping there are 0)
                            nbAttempt++;
                            nbWellPlaced = nbWellPlaced(combi, combiTried, k);
                            nbMisplaced = nbCommon(combi, combiTried, k)
                                    - nbWellPlaced;

                            // preparing to test the next position
                            pos++;

                        }

                        // At the end of the loop, we have the position of the color ball -> pos - 1
                        //// So we add this ball to the combination containing the Found balls
                        combiFind[pos - 1] = coul;

                        // incrementing the number of found balls
                        nbFind++;
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
            this.generateCombs = true;
        }
    }

    public static int nbWellPlaced(int[] tab1, int[] tab2, int k) {
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

    public static int nbCommon(int[] tab1, int[] tab2, int k) {
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
        if (this.generateCombs) {/*
            countCombs = 0;
            for(int i = 0; i < 12; i++) {
                for(int j = 0; j < 4; j++) {
                    this.combs[i][j] = 0;
                }
            }*/
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

            int Nextcol = 1; //  Contains the next color to be used to fill the combination
            //  when looking for the position of the current color

            int nbNextBalls = 0;

            // While the number of well-placed balls is less than the number of slots (i.e. while
            // the MM is not solved) ***use the variable nbGoodPlace***
            while (nbWellPlaced(combi, combiTest, k) < k && coul <= n) {

                coul = Nextcol;
                // We create the new combination to be tried, which determines the
                // presence or absence of a color. Taking into account the positions of
                // the balls of colors already found.
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

                // We check that the combination to be tried is not the one that was tried last time


                // We determine the number of well-placed and misplaced balls
                nbGoodPlace = nbWellPlaced(combi, combiTest, k);
                nbBadPlace = nbCommon(combi, combiTest, k)
                        - nbWellPlaced(combi, combiTest, k);

                // increment the number of attempted combinations
                nbAttempt++;

                // display the combination being tried and the indications given
                //System.out.print("cherche couleur : ");
                //afficheTab(combiTest);
                //System.out.println(" --> "+nbGoodPlace+" biens plac�es, "+nbBadPlace+" mal plac�es.");
                /*
                 * Analysis of the indications given by nbGoodPlace and nbBadPlace
                 */

                // 1 - If the number of well-placed balls = k, no need to continue, we have solved the puzzle.
                if (nbGoodPlace == k)
                    break;


                // Determination of the number of balls of the current color
                int nbBalls = nbGoodPlace - nbFind;


                // If the color being tested is present (i.e. nbBalls >= 1),
                // then do the following
                if (nbBalls >= 1 && coul <= (n + 1)) {

                    do {

                        if (nbNextBalls > 0) // if we have already iterated the search for a color
                        {
                            //System.out.println("Short circuit");
                            coul = Nextcol;
                            nbBalls = nbNextBalls;
                        }
                        nbNextBalls = 0; // initialize the number of balls of the next color

                        Nextcol = coul + 1;

                        for (int x = 1; x <= nbBalls; x++) { // search for the position of each color
                            // We set nbBadPlace to a number other than 0
                            nbBadPlace = 1;

                            // index of the tested position to find the location of the color coul. We do not test a position whose color we already know. So we create a loop that looks for a possible position.
                            pos = 0;

                            // while nbBadPlace != 0 do
                            while (nbBadPlace > 0) {

                                // search for a position to test that has not already been found
                                while ((pos < k) && combiFind[pos] != 0)
                                    pos++;

                                // We create the new combination to try, which looks for the exact position of the ball of the current color. While taking into account the positions of the balls of colors already found
                                for (int i = 0; i < k; i++) {

                                    if (combiFind[i] == 0) // If the current cell is not a cell whose color is already known (i.e., combiFind==0)
                                    {
                                        if (i != pos) // If the cell is not the tested cell, we put the ball of the superior color
                                            combiTest[i] = Nextcol;
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

                                // Test the new attempt
                                // Calculate the number of balls wrongly placed (assuming there are 0)
                                nbGoodPlace = nbWellPlaced(combi, combiTest, k);
                                int nbCommons = nbCommon(combi, combiTest, k);
                                nbBadPlace = nbCommons - nbGoodPlace;

                                // Display the attempt ***
                                //afficheTab(combiTest);
                                //System.out.println(" --> "+nbGoodPlace+" well placed, "+nbBadPlace+" misplaced.");
                                nbAttempt++;

                                /*
                                 * Analysis of the given indications
                                 */
                                int nbNextCol = (nbCommons - nbFind - 1);
                                //System.out.println("Nextcol : "+ Nextcol+ " nbNextCol : "+nbNextCol);
                                if (nbNextCol <= 0) {
                                    Nextcol++;
                                    //System.out.println(Nextcol);
                                } else
                                    nbNextBalls = nbNextCol;

                                pos++;
                            }


                            // At the end of the loop, we have the position of the ball of the current color -> pos - 1
                            // We add this ball to the combination containing the balls found
                            combiFind[pos - 1] = coul;
                            //  We increment the number of balls found
                            nbFind++;
                        }

                    }
                    while (nbNextBalls > 0 && nbGoodPlace != k); //  While the next color is present and there are still positions that are not well placed
                } else
                    Nextcol++;

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
        System.out.println(super.getPawnToPlace()/4-1);
    }

    public int getPawnput(){
        return super.getPawnToPlace()/4;
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
            nb = loto.nextInt(8);
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
