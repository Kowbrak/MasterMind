package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestMasterMindDecider {

    @Mock
    private Model mockModel;

    @Mock
    private Controller mockController;

    private MasterMindDecider decider;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        decider = new MasterMindDecider(mockModel, mockController, "test line");
    }

    @Test
    public void testDecide() {
        // Définir le comportement attendu des mocks
        Mockito.when(decider.action("test line")).thenReturn(new ActionList(false));

        // Appeler la méthode à tester
        ActionList result = decider.decide();

        // Vérifier le résultat
        Assertions.assertEquals(new ActionList(true), result);
    }
}
