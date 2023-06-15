package model;

import boardifier.control.Controller;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;

import java.util.List;

public class MasterMindStageModel extends GameStageModel {

    // states
    public final static int STATE_SELECTPAWN = 1; // the player must select a pawn
    public final static int STATE_SELECTDEST = 2; // the player must select a destination
    public final static int STATE_FULL = 3;

    private MasterMindBoard board;
    private Pawn[] testPawns;

    private MasterMindPawnPot testPot;
    Rect rectanglePot;
    private MasterMindPawnPot blackPot;
    private MasterMindPawnPot redPot;
    private MasterMindPawnPot colorPot;
    private MasterMindPawnPot invisiblePot;
    private MasterMindPawnPot combFinalPot;


    private Pawn[] blackPawns;
    private Pawn[] redPawns;
    private Pawn[] colorPawns;
    private Pawn[] combFinalPawns;
    private Pawn[] invisiblePawn;
    private int blackPawnsToPlay;
    private int redPawnsToPlay;
    private TextElement playerName;
    private ButtonElement ButtonElementConfirm;
    private ButtonElement ButtonElementClear;

    public MasterMindStageModel(String name, Model model) {
        super(name, model);
        state = STATE_SELECTPAWN;
        blackPawnsToPlay = 12;
        redPawnsToPlay = 12;
        setupCallbacks();
    }

    //-----------------------------------------------------------------------------------\\
    //-------------------------------Getters and setters---------------------------------\\
    public MasterMindBoard getBoard() {
        return board;
    }
    public void setBoard(MasterMindBoard board) {
        this.board = board;
        addGrid(board);
    }



    public MasterMindPawnPot getBlackPot() {
        return blackPot;
    }
    public void setBlackPot(MasterMindPawnPot blackPot) {
        this.blackPot = blackPot;
        addGrid(blackPot);
    }
    public Pawn[] getBlackPawns() {
        return blackPawns;
    }
    public void setBlackPawns(Pawn[] blackPawns) {
        this.blackPawns = blackPawns;
        for(int i=0;i<blackPawns.length;i++) {
            addElement(blackPawns[i]);
        }
    }

    public MasterMindPawnPot getCombFinalPot() {
        return combFinalPot;
    }
    public void setCombFinalPot(MasterMindPawnPot CombFinalPot) {
        this.combFinalPot = CombFinalPot;
        addGrid(CombFinalPot);
    }
    public Pawn[] getCombFinalPawns() {
        return combFinalPawns;
    }
    public void setCombFinalPawns(Pawn[] CombFinalPawns) {
        this.combFinalPawns = CombFinalPawns;
        for(int i=0;i<CombFinalPawns.length;i++) {
            addElement(CombFinalPawns[i]);
        }
    }

    public MasterMindPawnPot getRedPot() {
        return redPot;
    }
    public void setRedPot(MasterMindPawnPot redPot) {
        this.redPot = redPot;
        addGrid(redPot);
    }
    public Pawn[] getRedPawns() {
        return redPawns;
    }
    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for(int i=0;i<redPawns.length;i++) {
            addElement(redPawns[i]);
        }
    }


    public MasterMindPawnPot getInvisiblePot() {
        return invisiblePot;
    }
    public void setInvisiblePot(MasterMindPawnPot invisiblePot) {
        this.invisiblePot = invisiblePot;
        addGrid(invisiblePot);
    }
    public Pawn[] getInvisiblePawn() {
        return invisiblePawn;
    }
    public void setInvisiblePawn(Pawn[] invisiblePawn) {
        this.invisiblePawn = invisiblePawn;
        for(int i=0;i<invisiblePawn.length;i++) {
            addElement(invisiblePawn[i]);
        }
    }

    public MasterMindPawnPot getTestPot() {
        return testPot;
    }
    public void setTestPot(MasterMindPawnPot testPot) {
        this.testPot = testPot;
        addGrid(testPot);
    }
    public Pawn[] getTestPawns() {
        return testPawns;
    }
    public void setTestPawns(Pawn[] testPawns) {
        this.testPawns = testPawns;
        for(int i=0;i<testPawns.length;i++) {
            addElement(testPawns[i]);
        }
    }

    public MasterMindPawnPot getColorPot() {
        return colorPot;
    }
    public void setColorPot(MasterMindPawnPot colorPot) {
        this.colorPot = colorPot;
        addGrid(colorPot);
    }
    public Pawn[] getColorPawn() {
        return colorPawns;
    }
    public void setColorPawns(Pawn[] colorPawns) {
        this.colorPawns = colorPawns;
        for(int i=0;i<colorPawns.length;i++) {
            addElement(colorPawns[i]);
        }
    }

    public TextElement getPlayerName() {
        return playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public ButtonElement getButtonElementConfirm() {
        return ButtonElementConfirm;
    }
    public void setButtonElementConfirm(ButtonElement ButtonElement) {
        this.ButtonElementConfirm = ButtonElement;
        addElement(ButtonElement);
    }

    public ButtonElement getButtonElementClear() {
        return ButtonElementClear;
    }
    public void setButtonElementClear(ButtonElement ButtonElement) {
        this.ButtonElementClear = ButtonElement;
        addElement(ButtonElement);
    }


    private void setupCallbacks() {
        onSelectionChange( () -> {
            // get the selected pawn if any
            if (selected.size() == 0) {
                board.resetReachableCells(false);
                return;
            }
            Pawn pawn = (Pawn) selected.get(0);
            board.setValidCells(pawn.getNumber());
        });

        onPutInGrid( (element, gridDest, rowDest, colDest) -> {
            // just check when pawns are put in 3x3 board
            if (gridDest != board) return;
            Pawn p = (Pawn) element;
            if (p.getColor() == 0) {
                blackPawnsToPlay--;
            }
            else {
                redPawnsToPlay--;
            }
            if ((blackPawnsToPlay == 0) && (redPawnsToPlay == 0)) {
                computePartyResult();
            }
        });
    }
    //-----------------------------------------------------------------------------------\\

    /**
     * Compute the result of the party
     */
    private void computePartyResult() {
        int idWinner = -1;
        // get the empty cell, which should be in 2D at [0,0], [0,2], [1,1], [2,0] or [2,2]
        // i.e. or in 1D at index 0, 2, 4, 6 or 8
        int i = 0;
        int nbBlack = 0;
        int nbRed = 0;
        int countBlack = 0;
        int countRed = 0;
        Pawn p = null;
        int row, col;
        for (i = 0; i < 9; i+=2) {
            if (board.isEmptyAt(i / 3, i % 3)) break;
        }
        // get the 4 adjacent cells (if they exist) starting by the upper one
        row = (i / 3) - 1;
        col = i % 3;
        for (int j = 0; j < 12; j++) {
            // skip invalid cells
            if ((row >= 0) && (row <= 2) && (col >= 0) && (col <= 2)) {
                p = (Pawn) (board.getElement(row, col));
                if (p.getColor() == Pawn.PAWN_BLACK) {
                    nbBlack++;
                    countBlack += p.getNumber();
                } else {
                    nbRed++;
                    countRed += p.getNumber();
                }
            }
            // change row & col to set them at the correct value for the next iteration
            if ((j==0) || (j==2)) {
                row++;
                col--;
            }
            else if (j==1) {
                col += 2;
            }
        }
        System.out.println("nb black: "+nbBlack+", nb red: "+nbRed+", count black: "+countBlack+", count red: "+countRed);
        // decide whose winning
        if (nbBlack < nbRed) {
            idWinner = 0;
        }
        else if (nbBlack > nbRed) {
            idWinner = 1;
        }
        else {
            if (countBlack < countRed) {
                idWinner = 0;
            }
            else if (countBlack > countRed) {
                idWinner = 1;
            }
        }
        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopGame();
    }

    public void moveLineUp(List<GameElement>[][] list2,ActionList actions, Controller control){
        MasterMindBoard board = this.getBoard();
        GridLook lookBoard = (GridLook) control.getElementLook(board);
        boolean find = false;
        for(int i = 0; i<list2.length-1; i++){
            // for debug
            //System.out.println("i : "+list2[i][0].isEmpty()+", i+1 : "+!list2[i+1][0].isEmpty());
            if(list2[i][0].isEmpty() && !list2[i+1][0].isEmpty() || find){
                find = true;
                for(int j= 0; j<list2[i].length; j++){
                    GameElement element = list2[i+1][j].get(0);
                    //System.out.println("element : "+element);
                    Coord2D center = lookBoard.getRootPaneLocationForCellCenter(i, j);
                    GameAction move = new MoveAction(model,element,"MasterMindboard",i,j, AnimationTypes.MOVE_LINEARPROP, center.getX(), center.getY(), 10);
                    actions.addPackAction(move);
                }
            }
        }
    }

    public void upPawnRedWhite() {
        int tmp;
        for (int i = 0; i < 11; i++) {
            Pawn p1 = (Pawn) (blackPot.getElement(i + 1, 0));
            tmp = p1.getNumber();
            Pawn p2 = (Pawn) (blackPot.getElement(i, 0));
            p2.setNumber(tmp);
            Pawn p3 = (Pawn) (redPot.getElement(i + 1, 0));
            tmp = p3.getNumber();
            Pawn p4 = (Pawn) (redPot.getElement(i, 0));
            p4.setNumber(tmp);
        }
    }

    public void setNumberPawnDown() {
        String line = "";
        for(int i = 0; i<4; i++){
            Pawn p2 = (Pawn) (testPot.getElement(0, i));
            line += p2.getColor();
        }
        setNumberPawnDown(line);
    }

    public void setNumberPawnDown(String line) {
        setNumberPawnDown(line, 11, 0);
    }

    public void setNumberPawnDown(String line, int row, int col) {
        Pawn redPawn = (Pawn) (redPot.getElement(row, col));
        Pawn whitePawn = (Pawn) (blackPot.getElement(row, col));
        String comb = "";

        for(int i = 0; i<4; i++){
            Pawn p = (Pawn) (combFinalPot.getElement(0, i));
            comb += p.getColor();
        }

        char[] combCharArray = comb.toCharArray();
        char[] lineCharArray = line.toCharArray();
        int tmpred = 0;
        int tmpWhite = 0;
        for (int i = 0; i < combCharArray.length; i++) {
            if (combCharArray[i] == lineCharArray[i]) {
                tmpred++;
                combCharArray[i] = ' ';
                lineCharArray[i] = ' ';
            }
        }
        for (int i = 0; i < combCharArray.length; i++) {
            if (lineCharArray[i] != ' ') {
                for (int j = 0; j < combCharArray.length; j++) {
                    if (lineCharArray[i] == combCharArray[j]) {
                        tmpWhite++;
                        combCharArray[j] = ' ';
                        break;
                    }
                }
            }
        }
        System.out.println("red : "+tmpred+", white : "+tmpWhite+", combFQINAL : "+comb+", line : "+line);
        redPawn.setNumber(tmpred);
        whitePawn.setNumber(tmpWhite);
    }

    /**
     * Check if the player won
     * @return
     */
    public int verifWin(){
        Pawn p = (Pawn) (redPot.getElement(11, 0));
        return p.getNumber();
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new MasterMindStageFactory(this);
    }
}
