package model;

import boardifier.model.*;

public class HoleStageModel extends GameStageModel {

    // states
    public final static int STATE_SELECTPAWN = 1; // the player must select a pawn
    public final static int STATE_SELECTDEST = 2; // the player must select a destination

    private HoleBoard board;
    private Pawn[] testPawns;

    private HolePawnPot testPot;
    Rect rectanglePot;
    private HolePawnPot blackPot;
    private HolePawnPot redPot;
    private HolePawnPot colorPot;
    private HolePawnPot invisiblePot;


    private Pawn[] blackPawns;
    private Pawn[] redPawns;
    private Pawn[] colorPawns;

    private Pawn[] invisiblePawn;
    private int blackPawnsToPlay;
    private int redPawnsToPlay;
    private TextElement playerName;
    private ButtonElement ButtonElement;

    public HoleStageModel(String name, Model model) {
        super(name, model);
        state = STATE_SELECTPAWN;
        blackPawnsToPlay = 12;
        redPawnsToPlay = 12;
        setupCallbacks();
    }

    public HoleBoard getBoard() {
        return board;
    }
    public void setBoard(HoleBoard board) {
        this.board = board;
        addGrid(board);
    }



    public HolePawnPot getBlackPot() {
        return blackPot;
    }
    public void setBlackPot(HolePawnPot blackPot) {
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

    public HolePawnPot getRedPot() {
        return redPot;
    }
    public void setRedPot(HolePawnPot redPot) {
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


    public HolePawnPot getInvisiblePot() {
        return invisiblePot;
    }
    public void setInvisiblePot(HolePawnPot invisiblePot) {
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

    public HolePawnPot getTestPot() {
        return testPot;
    }
    public void setTestPot(HolePawnPot testPot) {
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

    public HolePawnPot getColorPot() {
        return colorPot;
    }
    public void setColorPot(HolePawnPot colorPot) {
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

    public ButtonElement getButtonElement() {
        return ButtonElement;
    }
    public void setButtonElement(ButtonElement ButtonElement) {
        this.ButtonElement = ButtonElement;
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

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new HoleStageFactory(this);
    }
}