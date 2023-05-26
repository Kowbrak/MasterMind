package boardifier.model;

import javafx.scene.control.Button;

public class ButtonElement extends GameElement{
    protected Button button;

    public ButtonElement(String text, GameStageModel gameStageModel) {
        super(gameStageModel);
        button = new Button(text);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setText(String text) {
        button.setText(text);
    }

    public String getText() {
        return button.getText();
    }

    public void update(){
        // TODO
        // Not important
    }
}
