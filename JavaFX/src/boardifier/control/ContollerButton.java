package boardifier.control;

import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ContollerButton implements EventHandler<ActionEvent> {
    protected Model model;
    protected View view;

    public ContollerButton(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    // by default, do nothing. Must be overridden in subclasses
    public void handle(ActionEvent event) {}
}
