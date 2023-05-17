package control;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;

import java.util.Calendar;
import java.util.Random;
public class MasterMind2Decider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private String line;
    private String[][] detail;
    private int step;
    private int CurrentColor;
    private boolean changeCurrentColor;
    private char[] combFinal;
    private String pawnInCombFinal;
    private String lastComb;

    /**
     * Constructor of the class MasterMind2Decider
     * @param model, the model of the game
     * @param control, the controller of the game
     * @param d, the detail of the game
     * @param e, the step of the game
     * @param currentColor, the current color of the game
     * @param pawnGoodPlace, the pawn in the good place
     * @param pawnInCombFinal, the pawn in the final combination
     * */
    public MasterMind2Decider(Model model, Controller control,String[][] d, int e, int currentColor, char[] pawnGoodPlace, String pawnInCombFinal) {
        super(model, control);
        this.detail = d;
        this.step = e;
        this.CurrentColor = currentColor;
        this.line = "";
        this.changeCurrentColor = false;
        this.combFinal = pawnGoodPlace;
        this.pawnInCombFinal = pawnInCombFinal;
        this.lastComb = "";
    }

    /**
     * Method to decide the action to do
     * @return
     */
    @Override
    public ActionList decide(){
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        MasterMindBoard board = gameStage.getBoard(); // get the board
        MasterMindPawnPot whitePot = gameStage.getWhitePot(); // get the black pot
        MasterMindPawnPot redPot = gameStage.getRedPot(); // get the red pot
        Pawn redPawn = (Pawn) redPot.getElement(11, 0);
        Pawn whitePawn = (Pawn) whitePot.getElement(11, 0);
        Pawn pawn1 = (Pawn) board.getElement(11,0);
        Pawn pawn2 = (Pawn) board.getElement(11,1);
        Pawn pawn3 = (Pawn) board.getElement(11,2);
        Pawn pawn4 = (Pawn) board.getElement(11,3);

        if(step == 1){
            this.line = "RRRR";
        }else if(redPawn.getNumber() == 0 && whitePawn.getNumber() == 0 && pawn1.getColor() == this.CurrentColor){
            for(int i = 0; i < 4; i++){
                this.line += detail[0][CurrentColor];
            }
            changeCurrentColor = true;
        }else{
            if(pawnInCombFinal.equals("")){
                this.pawnInCombFinal += detail[0][CurrentColor -1];
                detail[2][CurrentColor -1] = String.valueOf(redPawn.getNumber());
            }
            if(pawn1.getColor() == pawn2.getColor() && pawn4.getColor() == pawn3.getColor() && pawn1.getColor() == pawn4.getColor()) {
                int p = 0;
                while(detail[2][p].equals("0")){
                    p++;
                }
                this.line+=detail[0][p];
                for(int i = 1; i<4; i++){
                    this.line += detail[0][CurrentColor];
                }
            }else{
                this.lastComb += this.detail[0][pawn1.getColor()-1];
                this.lastComb += this.detail[0][pawn2.getColor()-1];
                this.lastComb += this.detail[0][pawn3.getColor()-1];
                this.lastComb += this.detail[0][pawn4.getColor()-1];
                int pawnsWhite = whitePawn.getNumber();
                int pawnsReds = redPawn.getNumber();
                int pawnsWhiteRed = pawnsWhite + pawnsReds;
                /**System.out.println("\nLast combination : "+this.lastComb);
                System.out.println("pawns White : "+pawnsWhite);
                System.out.println("pawns Red : "+pawnsReds);
                System.out.println("pawns White and Red total : "+pawnsWhiteRed);**/

                if(pawnsWhiteRed == 1){
                    if(pawnsWhite ==1){
                        int tmp = posPawn();
                    }else{
                        int tmp = posPawn();
                        this.combFinal[tmp] = this.lastComb.charAt(tmp);
                        for(int i = 0; i<4; i++){
                            if(this.combFinal[i] != '0'){
                                this.line += this.combFinal[i];
                            }else{
                                this.line += detail[0][CurrentColor +1];
                            }
                        }
                        changeCurrentColor = true;
                    }
                }

            }
        }
        /**System.out.println("Final combination : "+showTab1DChar(this.combFinal));
        System.out.println("letter in the combination :"+pawnInCombFinal);
        System.out.println("Line a tester : "+this.line);**/
        return super.action(this.line);
    }

    /**
     * Method to show a 1D tab
     * @param tab, the tab to show
     * @return the tab in string
     *
     * @param tab
     * @return
     */
    public String showTab1DChar(char[] tab){
        String tmp = "";
        for(int i = 0; i<tab.length; i++){
            tmp += tab[i];
        }
        return tmp;
    }

    /**
     * Method to show a 1D tab
     * @return the tab in string
     * @return
     */
    public int posPawn() {
        int tmp = 20;
        for (int i = 0; i < this.lastComb.length(); i++) {
            for (int j = 0; j < this.pawnInCombFinal.length(); j++) {
                if (this.lastComb.charAt(i) == this.pawnInCombFinal.charAt(j)) {
                    tmp = i;
                    break;
                }
            }
            if (tmp != 20) {
                break;
            }
        }
        return tmp;
    }

    /**
     * Method to show a 2D tab
     * @param tab, the tab to show
     */
    public void showTab2D(String[][] tab){
        for(int i = 0; i<tab.length; i++){
            for(int j = 0; j<tab[i].length; j++){
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
    }

    public String getComb(){
        //System.out.println(this.line);
        return this.line;
    }

    public String[][] getDetail(){
        return this.detail;
    }

    public boolean isChangeCurrentColor(){
        return this.changeCurrentColor;
    }

    public char[] getCombFinal(){
        return this.combFinal;
    }

    public String getPawnInCombFinal(){
        return this.pawnInCombFinal;
    }
}
