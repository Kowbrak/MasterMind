package boardifier.view;

import boardifier.model.ButtonElement;
import boardifier.model.GameElement;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ButtonLook extends ElementLook {
    protected Button button;
    protected int fontSize;
    protected String color;
    protected double width;
    protected double height;

    public ButtonLook(int fontSize, String color, int width, int height, GameElement element) {
        super(element);
        this.fontSize = fontSize;
        this.color = color;
        ButtonElement be = (ButtonElement) element;
        button = new Button(be.getText());
        button.setPrefSize(width, height);
        button.setFont(new Font(fontSize));
        button.setTextFill(Color.valueOf(color));
        addNode(button);
    }

    @Override
    public void onChange() {
        update();
    }

    public void updateText() {
        ButtonElement be = (ButtonElement) getElement();
        button.setText(be.getText());
    }

    public void updateSize() {
        button.setPrefSize(width, height);
    }

    public void updateColor() {
        button.setTextFill(Color.valueOf(color));
    }

    public void update() {
        updateText();
        updateSize();
        updateColor();
    }

    public void setWidth(double width) {
        this.width = width;
        button.setPrefWidth(width);
    }

    public void setHeight(double height) {
        this.height = height;
        button.setPrefHeight(height);
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        button.setFont(new Font(fontSize));
    }

    public void setColor(String color) {
        this.color = color;
        button.setTextFill(Color.valueOf(color));
    }

    public void setText(String text) {
        button.setText(text);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getColor() {
        return color;
    }

    public String getText() {
        return button.getText();
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void update(double width, double height) {
        this.width = width;
        this.height = height;
        update();
    }

    public void update(double width, double height, String color) {
        this.width = width;
        this.height = height;
        this.color = color;
        update();
    }

    public void update(double width, double height, String color, int fontSize) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.fontSize = fontSize;
        update();
    }

    public void update(double width, double height, String color, int fontSize, String text) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.fontSize = fontSize;
        button.setText(text);
        update();
    }

    public void update(double width, double height, String color, int fontSize, String text, String font) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.fontSize = fontSize;
        button.setText(text);
        button.setFont(new Font(font, fontSize));
        update();
    }
}
