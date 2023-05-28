package control;

import boardifier.control.ContollerButton;
import boardifier.control.Controller;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.HoleStageModel;
import view.HoleView;

public class MasterMindControllerButton extends ContollerButton implements EventHandler<ActionEvent> {
    private HoleView holeView;
    public MasterMindControllerButton(Model model, View view) {
        super(model, view);
        holeView = (HoleView) view;
    }
    @Override
    public void handle(ActionEvent event) {
    }
}
