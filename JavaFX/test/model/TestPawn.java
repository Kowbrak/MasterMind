package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestPawn {

            private Pawn pawn;

            @BeforeEach
            public void setUp() {

                this.pawn = new Pawn(1,1, mock(GameStageModel.class));
            }

            @Test
            public void testSetNumber() {
                pawn.setNumber(1);
                Assertions.assertEquals(1, pawn.getNumber());
            }

            @Test
            public void testSetColor() {
                pawn.setColor('N');
                Assertions.assertEquals(pawn.PAWN_BLACK, pawn.getColor());
            }

            @Test
            public void testSetVisible() {
                pawn.setVisible(true);
                Assertions.assertEquals(true, pawn.isVisible());
            }
        }
