import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.MasterMindControllerLoop;

public class MasterMindConsoleLoop {

    public static void main(String[] args) {
        int gameWin = 0;
        int gameLose = 0;
        int average = 0;
        int loop = 1000;
        double percentage_win;
        Clock clock = new Clock();
        double time = 0;

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
        for (int i = 1; i <= loop; i++) {
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
            MasterMindControllerLoop control = new MasterMindControllerLoop(model, MasterMindView);
            control.setFirstStageName("MasterMind");
            try {
                clock.start();
                control.startGame(true);
                control.stageLoop();
                clock.stop();
            } catch (GameException e) {
                System.out.println("Cannot start the game. Abort");
            }
            System.out.println("Game "+i+" finished");
            if(model.getEnd() == 1){
                gameWin++;
            }else{
                gameLose++;
            }
            average += control.getPawnput();
            time += clock.getMilliSec();
        }
        percentage_win = (double) gameWin / (double) loop * 100;
        System.out.println(percentage_win+ "winning %");
        System.out.println("Average number of moves played : "+average/loop);
        System.out.println("Number of victory : "+gameWin);
        System.out.println("Number of losses : "+gameLose);
        System.out.println("Average time : "+time/loop+" ms");
        System.out.println("Total time : "+time+" ms");
    }
}

class Clock {
    long m_start;

    long m_stop;

    Clock() {
    }

    // Start the clock
    public void start() {
        m_start = System.currentTimeMillis();
    }

    // Stop the clock
    public void stop() {
        m_stop = System.currentTimeMillis();
    }

    // Returns the number of milliseconds separating the call from the start()
    // and stop() methods
    public long getMilliSec() {
        return (m_stop - m_start);
    }

}
