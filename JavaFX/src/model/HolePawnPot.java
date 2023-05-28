package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class HolePawnPot extends GridElement {
    int nbRows;
    int nbCols;
    public HolePawnPot(int x, int y, GameStageModel gameStageModel, int nbRows, int nbCols) {
        // call the super-constructor to create a 4x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, nbRows, nbCols, gameStageModel);
        this.nbRows = nbRows;
        this.nbCols = nbCols;
    }

    public int getNbPawns(){
        return nbRows * nbCols;
    }
    public int getNbRows(){
        return nbRows;
    }
    public int getNbCols(){
        return nbCols;
    }


}
