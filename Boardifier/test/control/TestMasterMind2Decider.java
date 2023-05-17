package control;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;

public class TestMasterMind2Decider {

    MasterMind2Decider decider;
    private Model model;
    private Controller controller;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        controller = mock(Controller.class);
    }

    @Test
    public void testDecide() {
        String[][] detail = {{"A", "B", "C", "D"}, {"1", "2", "3", "4"}, {"0", "0", "0", "0"}};
        int step = 1;
        int currentColor = 1;
        char[] combFinal = {'0', '0', '0', '0'};
        String pawnInCombFinal = "";
        MasterMind2Decider decider = new MasterMind2Decider(model, controller, detail, step, currentColor, combFinal, pawnInCombFinal);

        // Mock the necessary objects
        MasterMindStageModel gameStage = mock(MasterMindStageModel.class);
        MasterMindBoard board = mock(MasterMindBoard.class);
        MasterMindPawnPot whitePot = mock(MasterMindPawnPot.class);
        MasterMindPawnPot redPot = mock(MasterMindPawnPot.class);
        Pawn redPawn = mock(Pawn.class);
        Pawn whitePawn = mock(Pawn.class);
        Pawn pawn1 = mock(Pawn.class);
        Pawn pawn2 = mock(Pawn.class);
        Pawn pawn3 = mock(Pawn.class);
        Pawn pawn4 = mock(Pawn.class);

        // Stub the necessary method calls
        when(model.getGameStage()).thenReturn(gameStage);
        when(gameStage.getBoard()).thenReturn(board);
        when(gameStage.getWhitePot()).thenReturn(whitePot);
        when(gameStage.getRedPot()).thenReturn(redPot);
        when(redPot.getElement(11, 0)).thenReturn(redPawn);
        when(whitePot.getElement(11, 0)).thenReturn(whitePawn);
        when(board.getElement(11, 0)).thenReturn(pawn1);
        when(board.getElement(11, 1)).thenReturn(pawn2);
        when(board.getElement(11, 2)).thenReturn(pawn3);
        when(board.getElement(11, 3)).thenReturn(pawn4);
        when(pawn1.getColor()).thenReturn(currentColor);

        // Stub the necessary method calls to avoid NullPointerException
        when(redPawn.getNumber()).thenReturn(0);
        when(whitePawn.getNumber()).thenReturn(0);

        // Perform the action
        ActionList result = decider.decide();

        // Assert the expected behavior or outcome
        assertNotNull(result);
        // Add additional assertions based on your specific requirements
    }

    @Test
    public void testPosPawn() {
        String[][] detail = {{"A", "B", "C", "D"}, {"1", "2", "3", "4"}, {"0", "0", "0", "0"}};
        int step = 1;
        int currentColor = 1;
        char[] combFinal = {'0', '0', '0', '0'};
        String pawnInCombFinal = "AB";
        MasterMind2Decider decider = new MasterMind2Decider(model, controller, detail, step, currentColor, combFinal, pawnInCombFinal);

        int result = decider.posPawn();

        Assertions.assertEquals(20, result);
    }
}

