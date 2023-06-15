package view;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import model.MasterMindBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boardifier.model.GameStageModel;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestMasterMindView {
    @Mock
    private MasterMindStageModel mockModel;
    @Mock
    private GameStageModel GameStageModel;

    private MasterMindView masterMindView;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.masterMindView = new MasterMindView("MasterMind", mock(GameStageModel.class));
    }

    @Test
    void testCreateLooks() {
        Pawn mockPawn = mock(Pawn.class);

        // Mocking the required dependencies
        when(mockModel.getBoard()).thenReturn(mock(MasterMindBoard.class));
        when(mockModel.getWhitePot()).thenReturn(mock(MasterMindPawnPot.class));
        when(mockModel.getRedPot()).thenReturn(mock(MasterMindPawnPot.class));
        when(mockModel.getBoardPotPawn()).thenReturn(mock(MasterMindPawnPot.class));
        when(mockModel.getWhitePawns()).thenReturn(new Pawn[12]);
        when(mockModel.getRedPawns()).thenReturn(new Pawn[12]);
        Pawn[] mockPawnsBoard = new Pawn[48];
        mockPawnsBoard[0] = mockPawn; // Stubbing the first element
        when(mockModel.getPawnsBoard()).thenReturn(mockPawnsBoard);

        // Calling the method to be tested
        masterMindView.createLooks();

        // Verifying the expected interactions
        verify(mockModel).getBoard();
        verify(mockModel).getWhitePot();
        verify(mockModel).getRedPot();
        verify(mockModel).getBoardPotPawn();
        verify(mockModel, times(12)).getWhitePawns();
        verify(mockModel, times(12)).getRedPawns();
        verify(mockModel, times(48)).getPawnsBoard();
        verify(mockPawn, times(48)).setVisible(false);
        // Add more verifications as needed for other interactions
    }
}