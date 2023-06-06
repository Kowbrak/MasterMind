package model;

import boardifier.model.*;
import javafx.scene.shape.Rectangle;

public class HoleStageFactory extends StageElementsFactory {
    private HoleStageModel stageModel;
    public HoleStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (HoleStageModel) gameStageModel;
    }

    @Override
    public void setup(String comb) {
        // create the board
        stageModel.setBoard(new HoleBoard(140-85+10, 20, stageModel));
        //create the pots
        HolePawnPot blackPot = new HolePawnPot(0,20, stageModel, 12, 1, "whitePawnPot");
        stageModel.setBlackPot(blackPot);
        HolePawnPot redPot = new HolePawnPot((140-85)+(670/3)+10*2,20, stageModel, 12 ,1, "redPawnPot");
        stageModel.setRedPot(redPot);
        HolePawnPot testPot = new HolePawnPot((140-85)+10,670 + 20, stageModel,1,4, "testPawnPot");
        stageModel.setTestPot(testPot);
        HolePawnPot invisiblePot = new HolePawnPot(20, 20, stageModel, 48, 1, "invisiblePawnPot");
        stageModel.setInvisiblePot(invisiblePot);
        invisiblePot.setVisible(false);
        HolePawnPot colorPot = new HolePawnPot((140-85)+(670/3)+(140-85)+10*3,20, stageModel,8,1, "colorPawnPot");
        stageModel.setColorPot(colorPot);
        HolePawnPot combFinalPot = new HolePawnPot(20, 20, stageModel, 1, 4, "combFinalPawnPot");
        stageModel.setCombFinalPot(combFinalPot);
        combFinalPot.setVisible(false);


        Pawn[] invisiblePawns = new Pawn[48];
        for(int i=0; i<48; i++) {
            invisiblePawns[i] = new Pawn(0, Pawn.PAWN_BLACK, stageModel, Pawn.TYPE_NONE);
            invisiblePawns[i].setVisible(false);
        }
        stageModel.setInvisiblePawn(invisiblePawns);

        // create the pawns
        Pawn[] blackPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            blackPawns[i] = new Pawn(0, Pawn.PAWN_WHITE, stageModel, Pawn.TYPE_NONE);
        }
        stageModel.setBlackPawns(blackPawns);

        Pawn[] redPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            redPawns[i] = new Pawn(0, Pawn.PAWN_RED, stageModel, Pawn.TYPE_NONE);
        }
        stageModel.setRedPawns(redPawns);
        Pawn[] colorPawns = new Pawn[8];
        for(int i=0;i<8;i++) {
            colorPawns[i] = new Pawn(0, i+1, stageModel, Pawn.TYPE_SELECTPAWN);
        }
        stageModel.setColorPawns(colorPawns);

        Pawn[] combFinalPawns = new Pawn[4];
        char[] combChar = comb.toCharArray();
        for(int i=0;i<4;i++) {
            combFinalPawns[i] = new Pawn(0, findColor(combChar[i]), stageModel,Pawn.TYPE_NONE);
            combFinalPawns[i].setVisible(false);
        }
        stageModel.setCombFinalPawns(combFinalPawns);


        // assign pawns to their pot
        for (int i=0;i<12;i++) {
            blackPot.putElement(blackPawns[i], i,0);
            redPot.putElement(redPawns[i], i,0);
        }

        for (int i=0;i<4;i++) {
            combFinalPot.putElement(combFinalPawns[i], 0,i);
        }

        for (int i=0; i<48; i++) {
            invisiblePot.putElement(invisiblePawns[i], 0, 0);
        }


        for (int i=0;i<8;i++) {
            colorPot.putElement(colorPawns[i], i,0);
        }
        // create the text
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(0,10);
        text.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setPlayerName(text);

        ButtonElement buttonElement = new ButtonElement("Confirm", stageModel);
        buttonElement.setLocation((140-85)*5+30, 670 + 20 + (double) (140 - 85) /4);
        buttonElement.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setButtonElement(buttonElement);
    }

    public int findColor(char c) {
        switch(c){
            case 'N':
                return Pawn.PAWN_BLACK;
            case 'R':
                return Pawn.PAWN_RED;
            case 'B':
                return Pawn.PAWN_BLUE;
            case 'J':
                return Pawn.PAWN_YELLOW;
            case 'V':
                return Pawn.PAWN_GREEN;
            case 'W':
                return Pawn.PAWN_WHITE;
            case 'C':
                return Pawn.PAWN_CYAN;
            case 'P':
                return Pawn.PAWN_PURPLE;
            default:
                System.out.println("wrong color change (error in Pawn)");break;
        }
        return 0;
    }
}
