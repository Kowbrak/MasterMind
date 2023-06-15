package model;

import boardifier.model.*;

public class MasterMindStageFactory extends StageElementsFactory {
    private MasterMindStageModel stageModel;
    public MasterMindStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (MasterMindStageModel) gameStageModel;
    }

    /**
     * Setup the stage
     * @param comb
     */
    @Override
    public void setup(String comb) {
        // create the board
        stageModel.setBoard(new MasterMindBoard(140-85+10, 20, stageModel));
        //create the pots
        MasterMindPawnPot blackPot = new MasterMindPawnPot(0,20, stageModel, 12, 1, "whitePawnPot");
        stageModel.setBlackPot(blackPot);
        MasterMindPawnPot redPot = new MasterMindPawnPot((140-85)+(670/3)+10*2,20, stageModel, 12 ,1, "redPawnPot");
        stageModel.setRedPot(redPot);
        MasterMindPawnPot testPot = new MasterMindPawnPot((140-85)+10,670 + 20, stageModel,1,4, "testPawnPot");
        stageModel.setTestPot(testPot);
        MasterMindPawnPot invisiblePot = new MasterMindPawnPot(1000, 20, stageModel, 48, 1, "invisiblePawnPot");
        stageModel.setInvisiblePot(invisiblePot);
        invisiblePot.setVisible(false);
        MasterMindPawnPot colorPot = new MasterMindPawnPot((140-85)+(670/3)+(140-85)+10*3,20, stageModel,8,1, "colorPawnPot");
        stageModel.setColorPot(colorPot);
        MasterMindPawnPot combFinalPot = new MasterMindPawnPot(20, 20, stageModel, 1, 4, "combFinalPawnPot");
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

        ButtonElement buttonElementConfirm = new ButtonElement("Confirm", stageModel);
        buttonElementConfirm.setLocation((140-85)*5+30, 670 + 20 + (double) (140 - 85) /4);
        buttonElementConfirm.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setButtonElementConfirm(buttonElementConfirm);

        ButtonElement buttonElementClear = new ButtonElement("Clear", stageModel);
        buttonElementClear.setLocation(0, 670 + 14 + (double) (140 - 85) /4);
        buttonElementClear.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setButtonElementClear(buttonElementClear);
    }

    /**
     * Find the color of the pawn
     * @param c
     * @return
     */
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
