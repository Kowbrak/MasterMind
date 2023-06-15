import control.MasterMindController;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MasterMindRootPane;
import view.MasterMindView;

public class Mastermind extends Application {

    private static int mode;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // create the global model
        Model model = new Model();
        // register a single stage for the game, called hole
        StageFactory.registerModelAndView("MasterMind", "model.MasterMindStageModel", "view.MasterMindStageView");
        // create the root pane, using the subclass HoleRootPane
        MasterMindRootPane rootPane = new MasterMindRootPane();
        // create the global view.
        MasterMindView view = new MasterMindView(model, stage, rootPane);
        // create the controllers.
        MasterMindController control = new MasterMindController(model,view);
        // set the name of the first stage to create when the game is started
        rootPane.setController(control);
        control.setFirstStageName("MasterMind");
        // set the stage title
        stage.setTitle("MasterMind");
        // show the JavaFx main stage
        stage.show();
        //view.resetView();
    }
}
