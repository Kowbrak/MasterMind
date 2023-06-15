package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestMasterMindStageModel {

    private MasterMindStageModel master;
    private Pawn pawn;

    @BeforeEach
    public void setUp() {
        this.master = new MasterMindStageModel("MasterMind", mock(Model.class));
    }

    @Test
    public void testSetBoard(){
        MasterMindBoard board = new MasterMindBoard(1,1, mock(GameStageModel.class));
        master.setBoard(board);
        Assertions.assertEquals(board, master.getBoard());
    }

    @Test
    public void testSetWhitePot(){
        MasterMindPawnPot whitePot = new MasterMindPawnPot(1,1, mock(GameStageModel.class), 'N');
        master.setWhitePot(whitePot);
        Assertions.assertEquals(whitePot, master.getWhitePot());
    }

    @Test
    public void testSetRedPot(){
        MasterMindPawnPot redPot = new MasterMindPawnPot(1,1, mock(GameStageModel.class), 'N');
        master.setRedPot(redPot);
        Assertions.assertEquals(redPot, master.getRedPot());
    }

    @Test
    public void testSetWhitePawns(){
        Pawn[] whitePawns = new Pawn[4];
        for (int i = 0; i < whitePawns.length; i++) {
            whitePawns[i] = new Pawn(1,1, mock(GameStageModel.class));
        }
        master.setWhitePawns(whitePawns);
        Assertions.assertEquals(whitePawns, master.getWhitePawns());
    }

    @Test
    public void testSetRedPawns(){
        Pawn[] redPawns = new Pawn[4];
        for (int i = 0; i < redPawns.length; i++) {
            redPawns[i] = new Pawn(1,1, mock(GameStageModel.class));
        }
        master.setRedPawns(redPawns);
        Assertions.assertEquals(redPawns, master.getRedPawns());
    }

    @Test
    public void testSetPawnsBoard(){
        Pawn[] pawnsBoard = new Pawn[4];
        for (int i = 0; i < pawnsBoard.length; i++) {
            pawnsBoard[i] = new Pawn(1,1, mock(GameStageModel.class));
        }
        master.setPawnsBoard(pawnsBoard);
        Assertions.assertEquals(pawnsBoard, master.getPawnsBoard());
    }

     @Test
    public void testSetBoardPotPawn(){
        MasterMindBoard board = new MasterMindBoard(1,1, mock(GameStageModel.class));
        MasterMindPawnPot whitePot = new MasterMindPawnPot(1,1, mock(GameStageModel.class), 'N');
        MasterMindPawnPot redPot = new MasterMindPawnPot(1,1, mock(GameStageModel.class), 'N');
        Pawn[] whitePawns = new Pawn[4];
        for (int i = 0; i < whitePawns.length; i++) {
            whitePawns[i] = new Pawn(1,1, mock(GameStageModel.class));
        }
        Pawn[] redPawns = new Pawn[4];
        for (int i = 0; i < redPawns.length; i++) {
            redPawns[i] = new Pawn(1,1, mock(GameStageModel.class));
        }
        Pawn[] pawnsBoard = new Pawn[4];
        for (int i = 0; i < pawnsBoard.length; i++) {
            pawnsBoard[i] = new Pawn(1,1, mock(GameStageModel.class));
        }
        master.setBoard(board);
        master.setWhitePot(whitePot);
        master.setRedPot(redPot);
        master.setWhitePawns(whitePawns);
        master.setRedPawns(redPawns);
        master.setPawnsBoard(pawnsBoard);
        Assertions.assertEquals(board, master.getBoard());
        Assertions.assertEquals(whitePot, master.getWhitePot());
        Assertions.assertEquals(redPot, master.getRedPot());
        Assertions.assertEquals(whitePawns, master.getWhitePawns());
        Assertions.assertEquals(redPawns, master.getRedPawns());
        Assertions.assertEquals(pawnsBoard, master.getPawnsBoard());
    }

    @Test
    public void testSetColors(){
        char[] colors = new char[6];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = 'N';
        }
        master.setColors("N", 1);
        Assertions.assertEquals(1, pawn.getColor());
    }


}
