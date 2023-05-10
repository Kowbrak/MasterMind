package view;

import boardifier.model.GridElement;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class TestPawnPotLook {

    @Mock
    private GridElement mockGridElement;

    @Test
    void createShape() {
        int cellWidth = 3;
        int cellHeight = 2;

        when(mockGridElement.getNbRows()).thenReturn(2); // Définissez le comportement du mockGridElement selon vos besoins

        PawnPotLook pawnPotLook = new PawnPotLook(cellWidth, cellHeight, mockGridElement);
        pawnPotLook.createShape();

        // Vérifiez la forme générée
        String[][] shape = pawnPotLook.getShape();

        // Vérification des coins
        assertEquals("\u250F", shape[0][0]); // Coin supérieur gauche
        assertEquals("\u2513", shape[0][cellWidth]); // Coin supérieur droit
        assertEquals("\u2517", shape[cellHeight][0]); // Coin inférieur gauche
        assertEquals("\u251B", shape[cellHeight][cellWidth]); // Coin inférieur droit

        // Vérification des lignes horizontales
        for (int i = 0; i < cellHeight; i++) {
            for (int j = 1; j < cellWidth; j++) {
                assertEquals("\u2501", shape[i][j]); // Ligne horizontale supérieure
                assertEquals("\u2501", shape[cellHeight + i][j]); // Ligne horizontale inférieure
            }
        }

        // Vérification des lignes verticales
        for (int i = 0; i < cellHeight; i++) {
            assertEquals("\u2503", shape[i][0]); // Ligne verticale gauche
            assertEquals("\u2503", shape[i][cellWidth]); // Ligne verticale droite
        }

        // Vérification des intersections
        for (int i = 1; i < cellHeight; i++) {
            assertEquals("\u2523", shape[i * cellHeight][0]); // Intersection verticale gauche
            assertEquals("\u252B", shape[i * cellHeight][cellWidth]); // Intersection verticale droite
        }
    }
}

