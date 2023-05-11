import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.MasterMindController;
import control.MasterMindControllerBoucle;

public class MasterMindConsoleBoucle {

    public static void main(String[] args) {
        int gameWin = 0;
        int gameLose = 0;
        int moyenne = 0;
        int boucle = 1000;
        double pourcentage_win;
        Chrono chrono = new Chrono();
        double temps = 0;

        int mode = 0;
        /**if (args.length == 1) {
         try {
         mode = Integer.parseInt(args[0]);
         if ((mode <0) || (mode>2)) mode = 0;
         }
         catch(NumberFormatException e) {
         mode = 0;
         }
         }**/
        if (args.length == 1) {
            if (args[0].equals("1")) {
                mode = 1;
            } else if (args[0].equals("2")) {
                mode = 2;
            } else if (args[0].equals("3")) {
                mode = 3;
            } else {
                mode = 0;
            }
        } else {
            mode = 0;
        }
        for (int i = 1; i <= boucle; i++) {
            Model model = new Model();
            if (mode == 1) {
                model.addComputerPlayer("computerDumb");
            } else if (mode == 2) {
                model.addComputerPlayer("computerSmart1");
            } else if (mode == 3) {
                model.addComputerPlayer("computerSmart2");
            } else {
                model.addHumanPlayer("player");
            }
            StageFactory.registerModelAndView("MasterMind", "model.MasterMindStageModel", "view.MasterMindView");
            View MasterMindView = new View(model);
            MasterMindControllerBoucle control = new MasterMindControllerBoucle(model, MasterMindView);
            control.setFirstStageName("MasterMind");
            try {
                chrono.start();
                control.startGame(true);
                control.stageLoop();
                chrono.stop();
            } catch (GameException e) {
                System.out.println("Cannot start the game. Abort");
            }
            System.out.println("Partie "+i+" terminée");
            if(model.getEnd() == 1){
                gameWin++;
            }else{
                gameLose++;
            }
            moyenne += control.getPawnput();
            temps += chrono.getMilliSec();
        }
        pourcentage_win = (double) gameWin / (double) boucle * 100;
        System.out.println(pourcentage_win+ "% de victoire");
        System.out.println("Moyenne de coups : "+moyenne/boucle);
        System.out.println("Nombre de victoire : "+gameWin);
        System.out.println("Nombre de défaite : "+gameLose);
        System.out.println("Temps moyen : "+temps/boucle+" ms");
        System.out.println("Temps total : "+temps+" ms");
    }
}

class Chrono {
    long m_start;

    long m_stop;

    Chrono() {
    }

    // Lance le chronomtre
    public void start() {
        m_start = System.currentTimeMillis();
    }

    // Arrte le chronomtre
    public void stop() {
        m_stop = System.currentTimeMillis();
    }

    // Retourne le nombre de millisecondes sparant l'appel des mthode start()
    // et stop()
    public long getMilliSec() {
        return (m_stop - m_start);
    }

}
