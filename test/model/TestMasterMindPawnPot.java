package model;

import org.junit.jupiter.api.Test;

public class TestMasterMindPawnPot {

    private MasterMindPawnPot pawnPot;

    @Test
    public void testConstructor() {
        pawnPot = new MasterMindPawnPot(1, 1, null, 'N');
    }

    @Test
    public void testConstructor2() {
        pawnPot = new MasterMindPawnPot(2, 1, null, 'B');
    }

    @Test
    public void testConstructor3() {
        pawnPot = new MasterMindPawnPot(3, 1, null, 'R');
    }

    @Test
    public void testConstructor4() {
        pawnPot = new MasterMindPawnPot(1, 2, null, 'Y');
    }

}
