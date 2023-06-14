import control.HoleController;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import view.HoleRootPane;
import view.HoleView;

public class Hole extends Application {

    private static int mode;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // create the global model
        Model model = new Model();
        // register a single stage for the game, called hole
        StageFactory.registerModelAndView("hole", "model.HoleStageModel", "view.HoleStageView");
        // create the root pane, using the subclass HoleRootPane
        HoleRootPane rootPane = new HoleRootPane();
        // create the global view.
        HoleView view = new HoleView(model, stage, rootPane);
        // create the controllers.
        HoleController control = new HoleController(model,view);
        // set the name of the first stage to create when the game is started
        rootPane.setController(control);
        control.setFirstStageName("hole");
        // set the stage title
        stage.setTitle("MasterMind");
        // show the JavaFx main stage
        stage.show();
        //view.resetView();
    }
}
