package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.MasterMindStageModel;

public class MasterMindView extends View {

    private MenuItem menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    public Menu menu, option, help, opponents, help1, help2, help3, help4;
    public MenuItem newGame, stop, quit, rule, hello, Pater, Perrot, Salom, Mourad, Viezzi, help5;

    public MasterMindView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    /**
     * Create the menu bar, nav bar
     */
    @Override
    protected void createMenuBar() {
        /* menuBar = new MenuBar();
        Menu menu1 = new Menu("Game");
        menuStart = new MenuItem("New game");
        menuIntro = new MenuItem("Intro");
        menuQuit = new MenuItem("Quit");
        menu1.getItems().add(menuStart);
        menu1.getItems().add(menuIntro);
        menu1.getItems().add(menuQuit);
        menuBar.getMenus().add(menu1);*/

        // MENU
        menu = new Menu("Menu");
        newGame = new MenuItem("New Game");
        stop = new MenuItem("Stop");
        quit = new MenuItem("Quit");
        menu.getStyleClass().add("menu");
        newGame.getStyleClass().add("menu-item");
        stop.getStyleClass().add("menu-item");
        quit.getStyleClass().add("menu-item");
        menu.getItems().addAll(newGame, stop, quit);

        // OPTION
        option = new Menu("Options");
        opponents = new Menu("Switch opponents");
        Mourad = new MenuItem("Mr Hakem");
        Perrot = new MenuItem("Mr Perrot");
        Pater = new MenuItem("Mrs Paterlini");
        Salom = new MenuItem("Mr Salomon");
        Viezzi = new MenuItem("Mr Viezzi");
        option.getItems().addAll(opponents);
        option.getStyleClass().add("menu");
        opponents.getStyleClass().add("menu");
        Mourad.getStyleClass().add("menu-item");
        Perrot.getStyleClass().add("menu-item");
        Pater.getStyleClass().add("menu-item");
        Salom.getStyleClass().add("menu-item");
        Viezzi.getStyleClass().add("menu-item");
        opponents.getItems().addAll(Mourad, Perrot, Pater, Salom, Viezzi);

        // AIDE
        help = new Menu("Help");
        hello = new MenuItem("Hello");
        rule = new MenuItem("Game Rules");
        help1 = new Menu("Help");
        help2 = new Menu("Help");
        help3 = new Menu("Help");
        help4 = new Menu("Help");
        help5 = new MenuItem("Help");
        help.getItems().addAll(hello, rule, help1);
        help1.getItems().addAll(help2);
        help2.getItems().addAll(help3);
        help3.getItems().addAll(help4);
        help4.getItems().addAll(help5);
        help.getStyleClass().add("menu");
        hello.getStyleClass().add("menu-item");
        rule.getStyleClass().add("menu-item");
        help1.getStyleClass().add("menu-item");
        help2.getStyleClass().add("menu-item");
        help3.getStyleClass().add("menu-item");
        help4.getStyleClass().add("menu-item");
        help5.getStyleClass().add("menu-item");

        // MENUBAR
        menuBar = new MenuBar();
        menuBar.getStyleClass().add("menu-bar");

        menuBar.getMenus().addAll(menu, option, help);

    }

    public MenuItem getMenuStart() {
        return newGame;
    }

    public MenuItem getMenuIntro() {
        return stop;
    }

    public MenuItem getMenuQuit() {
        return quit;
    }

    public MenuItem getMenuRule() {
        return rule;
    }

    public MenuItem getMenuHelp5() {
        return help5;
    }

    public MenuItem getMenuHello() {
        return hello;
    }


    public Button getButton() {
        return ((MasterMindStageModel) model.getGameStage()).getButtonElementConfirm().getButton();
    }
}
