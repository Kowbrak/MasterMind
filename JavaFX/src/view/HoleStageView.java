package view;

import boardifier.model.GameStageModel;
import boardifier.view.ButtonLook;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import control.MasterMindControllerButton;
import model.HoleStageModel;
import model.Rect;

public class HoleStageView extends GameStageView {
    public HoleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
        width = 650;
        height = 550;
    }

    @Override
    public void createLooks() {
        HoleStageModel model = (HoleStageModel)gameStageModel;

        addLook(new HoleBoardLook(670, model.getBoard()));
        addLook(new PawnPotLook(140,420,model.getBlackPot(), true));
        addLook(new PawnPotLook(140,420,model.getRedPot(), true));
        addLook(new PawnPotLook(140,420,model.getTestPot(), false));
        addLook(new PawnPotLook(140, 420, model.getInvisiblePot(), true));
        addLook(new PawnPotLook(140, 420, model.getColorPot(), true));


        for(int i=0;i<12;i++) {
            addLook(new PawnLook(20,model.getBlackPawns()[i], true));
            addLook(new PawnLook(20, model.getRedPawns()[i], true));
        }

        for(int i=0;i<4;i++) {
            addLook(new PawnLook(20, model.getTestPawns()[i], false));
        }

        for(int i=0;i<48;i++) {
            addLook(new PawnLook(10, model.getInvisiblePawn()[i], false));
        }

        for(int i=0;i<8;i++) {
            addLook(new PawnLook(20, model.getColorPawn()[i], true));
        }

        addLook(new TextLook(12, "0x000000", model.getPlayerName()));
        addLook(new ButtonLook(12, "0x000000", 100, 20, model.getButtonElement()));
    }
}
