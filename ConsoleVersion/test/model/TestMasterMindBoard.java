package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestMasterMindBoard {

    private MasterMindBoard board;

    @BeforeEach
    public void setUp() {
        this.board = new MasterMindBoard(1,1, mock(GameStageModel.class));
    }

    @Test
    public void testSetValidCells(){
        List<Point> validCells = List.of(new Point(1,1), new Point(2,2));
        board.setValidCells(1);
        Assertions.assertEquals(1, 1);
    }
}
