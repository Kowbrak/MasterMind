package view;

import boardifier.view.ConsoleColor;
import model.Pawn;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestPawnLook {
    // test pion
    @Mock
    private Pawn mockPawn;

    public void PawnLookTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPawnLookBlack() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_BLACK);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.BLACK_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookRed() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_RED);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.RED_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookWhite() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_WHITE);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookBlue() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_BLUE);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.BLUE_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookYellow() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_YELLOW);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.YELLOW_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookGreen() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_GREEN);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.GREEN_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookPurple() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_PURPLE);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.PURPLE_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }

    @Test
    void testPawnLookCyan() {
        MockitoAnnotations.initMocks(this);

        when(mockPawn.getColor()).thenReturn(Pawn.PAWN_CYAN);
        PawnLook pawnLook = new PawnLook(mockPawn);
        assertEquals(ConsoleColor.BLACK + ConsoleColor.CYAN_BACKGROUND + mockPawn.getNumber() + ConsoleColor.RESET, pawnLook.shape[0][0]);
    }
}
