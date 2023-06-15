package view;

import boardifier.view.RootPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MasterMindRootPane extends RootPane {

    public MasterMindRootPane() {
        super();
    }

    /**
     * Create the page of the game, initialization of the components
     */
    @Override
    public void createDefaultGroup() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: lightgrey;");
        vbox.getStyleClass().add("vbox");
        vbox.getStylesheets().add("stylesheet.css");
        //Rectangle frame = new Rectangle(600, 100, Color.LIGHTGREY);

        HBox hbox1 = new HBox();
        Text text1 = new Text("Welcome to our MasterMind game !");
        text1.getStyleClass().add("text1");
        text1.setFont(new Font(30));
        hbox1.getChildren().add(text1);
        hbox1.setPadding(new Insets(10,10,0,10));

        VBox vbox2 = new VBox();
        HBox hbox2_1 = new HBox();
        HBox hbox2_2 = new HBox();
        hbox2_1.setAlignment(Pos.CENTER);
        hbox2_2.setAlignment(Pos.CENTER);
        Text text2 = new Text("Select the opponent : ");
        text2.setFont(new Font(20));
        text2.getStyleClass().add("text2");
        rbPater = new RadioButton("Mme.Paterlini");
        rbPater.setSelected(true);
        rbViez = new RadioButton("M.Viezzi");
        rbMour = new RadioButton("M.Hakem");
        rbPerr = new RadioButton("M.Perrot");
        rbsalom = new RadioButton("M.Salomon");
        rbPater.getStyleClass().add("radio-button");
        rbViez.getStyleClass().add("radio-button");
        rbMour.getStyleClass().add("radio-button");
        rbPerr.getStyleClass().add("radio-button");
        rbsalom.getStyleClass().add("radio-button");
        tgOpponent = new ToggleGroup();
        tgOpponent.getToggles().addAll(rbPater,rbViez,rbMour,rbPerr,rbsalom);
        hbox2_1.getChildren().add(text2);
        hbox2_2.getChildren().addAll(rbPater,rbViez,rbMour,rbPerr,rbsalom);
        hbox2_2.setSpacing(10);
        vbox2.getChildren().addAll(hbox2_1,hbox2_2);
        vbox2.setSpacing(10);

        VBox vbox3 = new VBox();
        HBox hbox3_1 = new HBox();
        HBox hbox3_2 = new HBox();
        hbox3_1.setAlignment(Pos.CENTER);
        hbox3_2.setAlignment(Pos.CENTER);
        Text text3 = new Text("Select the gamemode : ");
        text3.getStyleClass().add("text3");
        text3.setFont(new Font(20));
        text3.setFill(Color.BLACK);
        rbPlayer = new RadioButton("Player");
        rbPlayer.setSelected(true);
        rbIARand = new RadioButton("AI_Random");
        rbIA2 = new RadioButton("AI_2");
        rbIA3 = new RadioButton("AI_3");
        tgIA = new ToggleGroup();
        tgIA.getToggles().addAll(rbPlayer,rbIARand,rbIA2,rbIA3);
        hbox3_1.getChildren().add(text3);
        hbox3_2.getChildren().addAll(rbPlayer,rbIARand,rbIA2,rbIA3);
        hbox3_2.setSpacing(10);
        vbox3.getChildren().addAll(hbox3_1,hbox3_2);
        vbox3.setSpacing(10);

        VBox vbox4 = new VBox();
        HBox hbox4_1 = new HBox();
        HBox hbox4_2 = new HBox();
        hbox4_1.setAlignment(Pos.CENTER);
        hbox4_2.setAlignment(Pos.CENTER);
        Text text4 = new Text("Random generation of the combination : ");
        text4.getStyleClass().add("text4");
        text4.setFont(new Font(20));
        text4.setFill(Color.BLACK);
        rbRandConfTrue = new RadioButton("Yes");
        rbRandConfTrue.setSelected(true);
        rbRandConfFalse = new RadioButton("No");
        tgRandConf = new ToggleGroup();
        tgRandConf.getToggles().addAll(rbRandConfTrue,rbRandConfFalse);
        hbox4_1.getChildren().add(text4);
        hbox4_2.getChildren().addAll(rbRandConfTrue,rbRandConfFalse);
        hbox4_2.setSpacing(10);
        vbox4.getChildren().addAll(hbox4_1,hbox4_2);
        vbox4.setSpacing(10);

        VBox vbox5 = new VBox();
        btnStart = new Button("Start");
        btnStart.setId("button");
        btnStart.setFont(new Font(15));
        btnStart.setTextFill(Color.BLACK);
        btnStart.setAlignment(Pos.CENTER);
        vbox5.getChildren().add(btnStart);
        vbox5.setAlignment(Pos.CENTER);
        vbox5.setPadding(new Insets(0,0,20,0));


        vbox.getChildren().addAll(hbox1,vbox2,vbox3,vbox4,vbox5);
        vbox.setSpacing(40);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().add(vbox);
    }
}
