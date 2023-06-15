package control;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestMasterMindController {
    @Test
    public void testNbBienPlace() {
        // Set up the test inputs
        int[] tab1 = {1, 2, 3, 4};
        int[] tab2 = {1, 2, 5, 6};
        int k = 2;

        // Call the method under test
        int result = MasterMindController.nbGoodPlaced(tab1, tab2, k);

        // Verify the expected result
        assertEquals(2, result, "Expected 2 numbers to be correctly placed");
    }

    @Test
    public void testNbGoodPlacedWithEmptyArrays() {
        // Set up the test inputs
        int[] tab1 = {};
        int[] tab2 = {};
        int k = 0;

        // Call the method under test
        int result = MasterMindController.nbGoodPlaced(tab1, tab2, k);

        // Verify the expected result
        assertEquals(0, result, "Expected 0 numbers to be correctly placed");
    }

    @Test
    public void testNbGoodPlacedWithNullArrays() {
        // Set up the test inputs
        int[] tab1 = null;
        int[] tab2 = null;
        int k = 0;

        // Call the method under test
        int result = MasterMindController.nbGoodPlaced(tab1, tab2, k);

        // Verify the expected result
        assertEquals(0, result, "Expected 0 numbers to be correctly placed");
    }

    private TestMasterMindController combination = new TestMasterMindController();
    private char[][] detailChar = {
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
            {'0', '1', '2', '3', '4', '5', '6', '7'}
    };
    private TestMasterMindController spyCombination = spy(combination);

    @Test
    public void testCombiStringToInt() {
        // Set up the test inputs
        String combi = "ABCD";
        int[] expected = {0, 1, 2, 3};

        // Stub the method getDetailChar to return the predefined char[][] array
        doReturn(detailChar).when(spyCombination).getDetailChar();

        // Call the method under test
        int[] result = spyCombination.combiStringToInt(combi);

        // Verify the expected result
        assertArrayEquals(expected, result, "Expected the converted array to match the input string");
    }

    private int[] combiStringToInt(String combi) {
        int[] combiInt = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (combi.charAt(i) == this.detailChar[0][j]) {
                    combiInt[i] = Character.getNumericValue(this.detailChar[1][j]);
                }
            }
        }
        return combiInt;
    }

    private char[][] getDetailChar() {
        char[][] detailChar = {
                {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
                {'0', '1', '2', '3', '4', '5', '6', '7'}
        };
        return detailChar;
    }

    @Test
    public void testCombiStringToIntWithInvalidInput() {
        // Set up the test inputs
        String combi = "ABXX";
        int[] expected = {0, 1, 0, 0};

        // Stub the method getDetailChar to return the predefined char[][] array
        doReturn(detailChar).when(spyCombination).getDetailChar();

        // Call the method under test
        int[] result = spyCombination.combiStringToInt(combi);

        // Verify the expected result
        assertArrayEquals(expected, result, "Expected the converted array to have zeros for invalid input characters");
    }

    @Test
    public void testCombiStringToIntWithNullInput() {
        // Set up the test inputs
        String combi = null;
        int[] expected = {0, 0, 0, 0};

        // Stub the method getDetailChar to return the predefined char[][] array
        doReturn(detailChar).when(spyCombination).getDetailChar();

        // Call the method under test
        int[] result = spyCombination.combiStringToInt(combi);

        // Verify the expected result
        assertArrayEquals(expected, result, "Expected the converted array to have zeros for null input");
    }


    @Mock
    private int[] tab1;

    @Mock
    private int[] tab2;

    private int k = 4;

    @Test
    public void testNbCommons() {

        when(tab1[0]).thenReturn(1);
        when(tab1[1]).thenReturn(2);
        when(tab1[2]).thenReturn(3);
        when(tab1[3]).thenReturn(4);

        when(tab2[0]).thenReturn(4);
        when(tab2[1]).thenReturn(3);
        when(tab2[2]).thenReturn(2);
        when(tab2[3]).thenReturn(1);

        int nbCommons = MasterMindController.nbCommons(tab1, tab2, k);
        assertEquals(4, nbCommons);
    }
}
