package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.view.View;

public class MasterMindController extends Controller {

    public MasterMindController(Model model, View view) {
        super(model, view);
        setControlKey(new MasterMindControllerKey(model, view, this));
        setControlMouse(new MasterMindControllerMouse(model, view, this));
        setControlAction (new MasterMindControllerAction(model, view, this));
    }

    public void nextPlayer() {
        // use the default method to compute next player
        //model.setNextPlayer();
        // get the new player
        /*Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();
        //stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            HoleDecider decider = new HoleDecider(model,this,);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        }*/
    }
}
