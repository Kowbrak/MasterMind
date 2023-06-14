package control;

import boardifier.control.ContollerButton;
import boardifier.control.Controller;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.HoleStageModel;
import view.HoleView;

public class MasterMindControllerButton implements EventHandler<ActionEvent> {
    RootPane rootPane;
    public MasterMindControllerButton(RootPane rootPane) {
        this.rootPane = rootPane;
    }
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == rootPane.getButtonStart()){
            try {
                rootPane.getController().startGame();
            }
            catch(GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        }
    }
}
