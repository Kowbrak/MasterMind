package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class testPawn {
    private Pawn pawn;
    @BeforeEach
    public void setup(){
        pawn = new Pawn(1, 1, mock(GameStageModel.class));
    }
    @Test
    public void testSetNumber(){
        pawn.setNumber(4);
        int number = pawn.getNumber();
        Assertions.assertEquals(4, number);
    }
}
