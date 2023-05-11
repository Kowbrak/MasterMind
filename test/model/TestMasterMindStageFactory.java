package model;

import static org.mockito.Mockito.*;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import com.sun.istack.internal.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMasterMindStageFactory {
    private GameStageModel gameStageModel;
    private MasterMindStageModel stageModel;
    private MasterMindStageFactory stageFactory;

    @BeforeEach
    public void setUp() {
        MasterMindStageModel stageModel = mock(MasterMindStageModel.class);
        MasterMindStageFactory stageFactory = new MasterMindStageFactory(stageModel);
    }

    @Test
    public void testSetup() {
        // Appel de la méthode setup()
        stageFactory.setup();

        // Vérification des interactions avec les mocks
        verify(stageModel).setBoard(any(MasterMindBoard.class));
        verify(stageModel).setWhitePot(any(MasterMindPawnPot.class));
        verify(stageModel).setRedPot(any(MasterMindPawnPot.class));
        verify(stageModel).setBoardPotPawn(any(MasterMindPawnPot.class));
        verify(stageModel, times(12)).setWhitePawns(any());
        verify(stageModel, times(12)).setRedPawns(any());
        verify(stageModel, times(48)).setPawnsBoard(any());


        // Autres vérifications si nécessaire
        // ...
    }
}




