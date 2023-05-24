package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import javafx.scene.shape.Rectangle;

public class HoleStageFactory extends StageElementsFactory {
    private HoleStageModel stageModel;



    public HoleStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (HoleStageModel) gameStageModel;
    }

    @Override
    public void setup() {



        // create the board
        stageModel.setBoard(new HoleBoard(263, 70, stageModel));
        //create the pots
        HolePawnPot blackPot = new HolePawnPot(220,70, stageModel, 12, 1);
        stageModel.setBlackPot(blackPot);
        HolePawnPot redPot = new HolePawnPot(403,70, stageModel, 12 ,1 );
        stageModel.setRedPot(redPot);
//        HolePawnPot invisiblePot = new HolePawnPot(50, 50, stageModel, 48, 1);
//        stageModel.setInvisiblePot(invisiblePot);



        HolePawnPot testPot = new HolePawnPot(263,400 + 72, stageModel,1,4);
        stageModel.setTestPot(testPot);

        HolePawnPot invisiblePot = new HolePawnPot(20, 20, stageModel, 48, 1);
        stageModel.setInvisiblePot(invisiblePot);
        invisiblePot.setVisible(false);



        Pawn[] testPawns = new Pawn[4];
        for(int i=0;i<4;i++) {
            testPawns[i] = new Pawn(0, Pawn.PAWN_WHITE, stageModel);
        }
        stageModel.setTestPawns(testPawns);

        Pawn[] invisiblePawns = new Pawn[48];
        for(int i=0; i<48; i++) {
            invisiblePawns[i] = new Pawn(0, Pawn.PAWN_BLACK, stageModel);
            invisiblePawns[i].setVisible(false);
        }

        Rect rectangleBas = new Rect(200, 250, stageModel);
//        stageModel.setRectanglePot(rectangleBas);





        // create the pawns
        Pawn[] blackPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            blackPawns[i] = new Pawn(0, Pawn.PAWN_WHITE, stageModel);
        }
        stageModel.setBlackPawns(blackPawns);
        Pawn[] redPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            redPawns[i] = new Pawn(0, Pawn.PAWN_RED, stageModel);
        }
        stageModel.setRedPawns(redPawns);


        // assign pawns to their pot
        for (int i=0;i<12;i++) {
            blackPot.putElement(blackPawns[i], i,0);
            redPot.putElement(redPawns[i], i,0);
        }

        for (int i=0;i<4;i++) {
            testPot.putElement(testPawns[i], 0,i);
        }

        for (int i=0; i<48; i++) {
            invisiblePot.putElement(invisiblePawns[i], i, 0);
        }
        stageModel.setInvisiblePawn(invisiblePawns);
        // create the text
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(1000,0);
        text.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setPlayerName(text);
    }
}
