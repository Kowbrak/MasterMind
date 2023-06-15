package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.*;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.MasterMindView;

import java.util.Optional;

/**
 * A basic action controller that only manages menu actions
 * Action events are mostly generated when there are user interactions with widgets like
 * buttons, checkboxes, menus, ...
 */
public class MasterMindControllerAction extends ControllerAction implements EventHandler<ActionEvent> {

    // to avoid lots of casts, create an attribute that matches the instance type.
    private MasterMindView masterMindView;

    public MasterMindControllerAction(Model model, View view, Controller control) {
        super(model, view, control);
        // take the view parameter ot define a local view attribute with the real instance type, i.e. HoleView.
        masterMindView = (MasterMindView) view;

        // set handlers dedicated to menu items
        setMenuHandlers();

        // If needed, set the general handler for widgets that may be included within the scene.
        // In this case, the current gamestage view must be retrieved and casted to the right type
        // in order to have an access to the widgets, and finally use setOnAction(this).
        // For example, assuming the current gamestage view is an instance of MyGameStageView, which
        // creates a Button myButton :
        // ((MyGameStageView)view.getCurrentGameStageView()).getMyButton().setOnAction(this).

    }

    /**
     * Set the event handlers on the menu items
     */
    private void setMenuHandlers() {

        // set event handler on the MenuStart item
        masterMindView.getMenuStart().setOnAction(e -> {
            try {
                control.startGame();
            }
            catch(GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        });
        // set event handler on the MenuIntro item
        masterMindView.getMenuIntro().setOnAction(e -> {
            Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            quitAlert.setTitle("Stop the game");
            quitAlert.setHeaderText(null);
            quitAlert.setContentText("Are you sure you want to stop the game in progress ?");
            Optional<ButtonType> result = quitAlert.showAndWait();
            if (result.get() == ButtonType.OK){
                masterMindView.resetView();
            } else {
                control.stopGame();
            }
        });
        // set event handler on the MenuQuit item
        masterMindView.getMenuQuit().setOnAction(e -> {
            Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            quitAlert.setTitle("Exit the game");
            quitAlert.setHeaderText(null);
            quitAlert.setContentText("Are you sure you want to leave the game ?");
            Optional<ButtonType> result = quitAlert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.exit(0);
            } else {
                quitAlert.close();
            }
        });
        // set event handler on the MenuRule item
        masterMindView.getMenuRule().setOnAction(e -> {
            Label secondLabel = new Label("The goal of Mastermind is to win as many rounds as possible.\n" +
                    "\n" +
                    "The player who has to find the secret combination wins a round if he manages to do so in a maximum of 12 moves.\n" +
                    "\n" +
                    "The player who has devised the secret combination wins the round when his opponent has not managed to find the combination in 12 moves."
            );

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);

            Scene secondScene = new Scene(secondaryLayout, 1000, 100);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Game Rules");
            newWindow.setScene(secondScene);
            newWindow.show();
        });

        // set event handler on the MenuHelp5 item
        masterMindView.getMenuHelp5().setOnAction(e -> {
            Label troll = new Label(
                    "You're determined, wow !\n" +
                            "Did you think I was going to help you ?\n" +
                            "You're cute but no.\n" +
                            "Manage on your own, you've got the rules of the game above.\n" +
                            "Kiss."
            );

            StackPane trollLayout = new StackPane();
            trollLayout.getChildren().add(troll);

            Scene trollScene = new Scene(trollLayout, 450, 250);

            // Troll window (Stage)
            Stage trollWindow = new Stage();
            trollWindow.setTitle("Help");
            trollWindow.setScene(trollScene);
            trollWindow.show();
        });

        // set event handler on the MenuHello item
        masterMindView.getMenuHello().setOnAction(e -> {
            Label hello = new Label("Welcome to our beautiful game: MasterMind !\n" +
                    "To begin, choose your opponent from the \"Options\" menu.\n" +
                    "Then, click on \"New Game\" to start playing.\n" +
                    "Finally, if you don't know the rules, click on \"Game Rules\" in the \"Help\" menu.\n" +
                    "Good luck ! :)"
            );

            StackPane helloLayout = new StackPane();
            helloLayout.getChildren().add(hello);

            Scene helloScene = new Scene(helloLayout, 700, 200);

            // Hello window (Stage)
            Stage helloWindow = new Stage();
            helloWindow.setTitle("Hello");
            helloWindow.setScene(helloScene);
            helloWindow.show();
        });
    }

    /**
     * The general handler for action events.
     * this handler should be used if the code to process a particular action event is too long
     * to fit in an arrow function (like with menu items above). In this case, this handler must be
     * associated to a widget w, by calling w.setOnAction(this) (see constructor).
     *
     * @param event An action event generated by a widget of the scene.
     */
    public void handle(ActionEvent event) {

        if (!model.isCaptureActionEvent()) return;
    }
}

