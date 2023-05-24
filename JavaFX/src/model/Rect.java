package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class Rect extends GridElement {
    public Rect(int x, int y, GameStageModel gameStageModel) {
        super("rectangle", x, y, 1, 4, gameStageModel);
    }


}
