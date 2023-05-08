package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestMasterMindStageFactory {

    private MasterMindStageFactory factory;


    @BeforeEach
    public void setUp() {
        this.factory = new MasterMindStageFactory(mock(GameStageModel.class));
    }
}
