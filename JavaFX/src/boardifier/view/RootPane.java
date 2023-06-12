package boardifier.view;

import boardifier.model.GameElement;
import control.MasterMindControllerButton;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.List;

public class RootPane extends Pane {

    protected GameStageView gameStageView;
    protected Group group; // the group that contains all game elements of the current stage
    protected RadioButton rbPater,rbViez,rbMour,rbPerr,rbsalom,rbPlayer,rbIARand,rbIA2,rbIA3,rbRandConfTrue,rbRandConfFalse;
    protected ToggleGroup tgOpponent,tgIA,tgRandConf;
    protected Button btnStart;

    public RootPane() {
        this.gameStageView = null;
        group = new Group();
        //setBackground(Background.EMPTY);
        resetToDefault();
    }

    public Button getButton(){
        return this.btnStart;
    }

    public ToggleGroup getGroupOpponent(){
        return this.tgOpponent;
    }

    public ToggleGroup getGroupIa(){
        return this.tgIA;
    }

    public ToggleGroup getGroupRandConf(){
        return this.tgRandConf;
    }

    public final void resetToDefault() {
        createDefaultGroup();
        // add the group to the pane
        getChildren().clear();
        getChildren().add(group);
    }

    /**
     * create the element of the default group
     * This method can be overriden to define a different visual aspect.
     */
    protected void createDefaultGroup() {
        Rectangle frame = new Rectangle(50, 50, Color.LIGHTGREY);
        // remove existing children
        group.getChildren().clear();
        // adding default ones
        group.getChildren().addAll(frame);
    }
    /**
     * Initialize the content of the group.
     * It takes the elements of the model, which are initialized when starting a game stage.
     * It sorts them so that the element with the highest depth are put in first in the group.
     * So they will be hidden by elements with a lower depth.
     */
    public final void init(GameStageView gameStageView) {
        if (gameStageView != null) {
            this.gameStageView = gameStageView;
            // first sort element by their depth
            Collections.sort(gameStageView.getLooks(), (a, b) -> a.getDepth() - b.getDepth());
            // remove existing children
            group.getChildren().clear();
            // add game element looks
            for (ElementLook look : gameStageView.getLooks()) {
                Group group = look.getGroup();
                this.group.getChildren().add(group);
            }
            // add the group to the pane
            getChildren().clear();
            getChildren().add(group);
        }
    }

    /* ***************************************
       TRAMPOLINE METHODS
    **************************************** */

    public ElementLook getElementLook(GameElement element) {
        if (gameStageView == null) return null;
        return gameStageView.getElementLook(element);
    }
    public void update() {
        if (gameStageView == null) return;
        gameStageView.update();
    }
}
