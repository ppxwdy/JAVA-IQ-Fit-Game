package comp1110.ass2.gui;

import comp1110.ass2.FitGame;
import comp1110.ass2.Games;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Board extends Application {

    public static String current_objective;

    private static final int SQUARE_SIZE = 75;
    private static final int BOARD_WIDTH = 933; /*12*80 = 960 original 933*/
    private static final int BOARD_HEIGHT = 700; /*640 8*/

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    private final Group gpieces = new Group();
    private TextField textField;
    public static String gameplacement = "";
    public static String tempplacement = "";
    public static String solution = "";


    void makePlacement(String placement) {
        gpieces.getChildren().clear();
        int l = placement.length();

        char[] c = placement.toCharArray();
        String s = "";
        String pname = "";

        for (int i = 0; i < l; i += 4) {
            /*load pic*/
            pname = String.valueOf(c[i]) + String.valueOf(c[i + 1]) + String.valueOf(c[i + 2]) + String.valueOf(c[i + 3]);
            makepiece(new piecepic(pname));
        }
        gpieces.toFront();
    }

    /**
     * add board pic.
     */
    public void makeboard() {
        board.getChildren().clear();

        ImageView baseboard = new ImageView();
        baseboard.setImage(new Image(Board.class.getResource(URI_BASE + "board.png").toString()));
        baseboard.setFitWidth(BOARD_WIDTH);
        baseboard.setFitHeight(BOARD_HEIGHT - 200);

        board.getChildren().add(baseboard);

        board.toBack();
    }

    public void makepiece(Board.piecepic piecepic) {
        gpieces.getChildren().add(piecepic);
    }

    static class piecepic extends ImageView {

        piecepic(String pname) {
            String s = "";
            char[] c = pname.toCharArray();

            /*load pic*/
            switch (c[0]) {
                case 'b':
                    s = "B1.png";
                    break;
                case 'B':
                    s = "B2.png";
                    break;
                case 'g':
                    s = "G1.png";
                    break;
                case 'G':
                    s = "G2.png";
                    break;
                case 'i':
                    s = "I1.png";
                    break;
                case 'I':
                    s = "I2.png";
                    break;
                case 'l':
                    s = "L1.png";
                    break;
                case 'L':
                    s = "L2.png";
                    break;
                case 'n':
                    s = "N1.png";
                    break;
                case 'N':
                    s = "N2.png";
                    break;
                case 'o':
                    s = "O1.png";
                    break;
                case 'O':
                    s = "O2.png";
                    break;
                case 'p':
                    s = "P1.png";
                    break;
                case 'P':
                    s = "P2.png";
                    break;
                case 'r':
                    s = "R1.png";
                    break;
                case 'R':
                    s = "R2.png";
                    break;
                case 's':
                    s = "S1.png";
                    break;
                case 'S':
                    s = "S2.png";
                    break;
                case 'y':
                    s = "Y1.png";
                    break;
                case 'Y':
                    s = "Y2.png";
                    break;
            }

            String URL = URI_BASE + s;
            Image image = new Image(Board.class.getResource(URL).toString());
            setImage(image);

            /* rotate pic*/
            double angle = 0;
            switch (c[3]) {
                case 'N':
                    angle = 0;
                    break;
                case 'S':
                    angle = 180;
                    break;
                case 'E':
                    angle = 90;
                    break;
                case 'W':
                    angle = 270;
                    break;
            }

            setRotate(angle);

            double w = (image.getWidth() / 100) * SQUARE_SIZE;
            double h = (image.getHeight() / 100) * SQUARE_SIZE;
            setFitHeight(h);
            setFitWidth(w);
            double x = 0;
            double y = 0;
            if (c[3] == 'N' || c[3] == 'S') {
                if (c[1] <= '3') {
                    y = (Double.parseDouble(String.valueOf(c[2]))) * SQUARE_SIZE + 50;
                    x = (Double.parseDouble(String.valueOf(c[1])) + 1) * SQUARE_SIZE + 5;
                } else {
                    y = (Double.parseDouble(String.valueOf(c[2]))) * SQUARE_SIZE + 50;
                    x = (Double.parseDouble(String.valueOf(c[1])) + 1) * SQUARE_SIZE + 20;
                }

            } else if (c[0] == 'b' || c[0] == 'B' || c[0] == 'o' || c[0] == 'O' || c[0] == 'p' || c[0] == 'P' || c[0] == 'r' || c[0] == 'R' || c[0] == 's' || c[0] == 'S' || c[0] == 'y' || c[0] == 'Y') {
                if (c[1] <= '3') {
                    y = (Double.parseDouble(String.valueOf(c[2])) + 1) * SQUARE_SIZE + 50;
                    x = (Double.parseDouble(String.valueOf(c[1]))) * SQUARE_SIZE + 5;
                } else if (c[1] >= '7') {
                    y = (Double.parseDouble(String.valueOf(c[2])) + 1) * SQUARE_SIZE + 50;
                    x = (Double.parseDouble(String.valueOf(c[1]))) * SQUARE_SIZE + 30;
                } else {
                    y = (Double.parseDouble(String.valueOf(c[2])) + 1) * SQUARE_SIZE + 50;
                    x = (Double.parseDouble(String.valueOf(c[1]))) * SQUARE_SIZE + 20;
                }
            } else {
                if (c[1] <= '4') {
                    y = (Double.parseDouble(String.valueOf(c[2])) + 1) * SQUARE_SIZE + 10;
                    x = (Double.parseDouble(String.valueOf(c[1]))) * SQUARE_SIZE + 45;
                } else {
                    y = (Double.parseDouble(String.valueOf(c[2])) + 1) * SQUARE_SIZE + 10;
                    x = (Double.parseDouble(String.valueOf(c[1]))) * SQUARE_SIZE + 65;
                }
            }
            setLayoutX(x);
            setLayoutY(y);

        }
    }

    public boolean checkColor(String placement){
        char[] placementTochar =  gameplacement.toCharArray();
        char[] pieceTochar = placement.toCharArray();
        for(int i = 0; i < gameplacement.length(); i += 4){
            if(placementTochar[i] == pieceTochar[0] || Math.abs(placementTochar[i] - pieceTochar[0]) == 32){
                return true;
            }
        }
        return false;
    }

    /**
     * The following conditions must be met for the placement string to be valid:
     * - well-formed
     * - each piece placement must be a valid placement according to the rules
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     * If one of these conditions is not met then pop up a window saying:
     * "Invalid peice placement, try again!"
     */
    public void validPlacement(String placement, boolean colorcheck) {
        if (!FitGame.isPlacementValid(tempplacement) || colorcheck) {
            Stage stage = new Stage();
            stage.setTitle("Error Message");
            StackPane root = new StackPane();
            Scene scene = new Scene(root, 550, 250);
            stage.setScene(scene);

            Text message = new Text("Invalid Piece Placement!");
            message.setFont(Font.font("Tahoma", FontWeight.BLACK, 20));
            message.setFill(Color.BLACK);
            message.setOpacity(1);
            stage.setOpacity(1);

            root.getChildren().add(message);
            stage.show();
            tempplacement = gameplacement;
        } else {
            gameplacement = tempplacement;
        }
    }
    //  pieces to be placed in valid places

    public String getChallenge(int difficulty){
        assert difficulty >= 0 && difficulty <= 4;
        Random index = new Random();
        int i = 0;
        if(difficulty == 0 ){
            //index between 1 and 6
            i = index.nextInt(24);//0-23 (1-24)
        }
        else if(difficulty == 1){
            //index between 25 and 48
            i = index.nextInt(24)+24;// 0-23 + 24 = 24 - 47
        }
        else if(difficulty == 2){
            //index between 49 and 72
            i = index.nextInt(24)+48;// 0-23 + 48 = 48-71
        }
        else if(difficulty == 3){
            //index between 73 and 96
            i = index.nextInt(24)+72;// 0-23 +72 = 72-95
        }
        else {
            //index between 97 and 120
            i = index.nextInt(24)+96;// 0-23 +96 = 96-119
        }
        current_objective = Games.SOLUTIONS[i].objective; // added for task 7
        return Games.SOLUTIONS[i].objective;
    }

    public String getHint(String gplacement,String solution){
        ArrayList<String> current_placement = getList(gplacement);
        ArrayList<String> solution_placement = getList(solution);

        solution_placement.removeAll(current_placement);

        int size = solution_placement.size();

        Random index = new Random();
        int i = index.nextInt(size);
        String s = solution_placement.get(i);
        gameplacement += s;
        return s;
    }

    public ArrayList<String> getList(String placement){
        ArrayList<String> rtn = new ArrayList<String>();
        char[] c = placement.toCharArray();
        int j = 0;
        for(int i = 0; i<placement.length() ; i += 4){
            String s = String.valueOf(c[i]) + c[i+1] + c[i+2] + c[i+3];
            rtn.add(j,s);
            j++;
        }
        return rtn;
    }

    /**
     * Serves task 11, return a challenge given the difficulty level
     * @param difficulty difficulty level
     * @return challenge
     */
    public static String createChallenge(int difficulty) {

        int removeAmount;// get number of pieces to remove
        switch (difficulty) {
            case 1://missing 2
                removeAmount = 2;
                break;
            case 2://missing 4
                removeAmount = 4;
                break;
            case 3://missing 6
                removeAmount = 6;
                break;
            case 4://missing 7
                removeAmount = 7;
                break;
            case 5://missing 8
                removeAmount = 8;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }

        boolean testUniqueness;
        String newChallenge;
        do {
            String fullBoard = FitGame.getSolution(FitGame.randomStart());
            StringBuilder challenge = new StringBuilder(fullBoard);
            for (int i = 0; i < removeAmount; i++) {
                int r = (int)(Math.random() * (10 - i));
                challenge.delete(4 * r, 4 * r + 4);
            }
            testUniqueness = FitGame.solutionUnique(challenge.toString());
            newChallenge = challenge.toString();
        } while (!testUniqueness || FitGame.p > 5000);

        return newChallenge;
    }// dFIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    public void checkFinished(String placement){
        int[][] board = FitGame.pieceRecord(placement);
        boolean key = true;
        for(int row = 0; row < 5; row ++ ){
            for(int col = 0; col < 10; col++){
                if(board[row][col] == 0){
                    key = false;
                }
            }
        }
        if(key){
            Stage stage = new Stage();
            stage.setTitle("Message");
            StackPane root = new StackPane();
            Scene scene = new Scene(root, 550, 250);
            stage.setScene(scene);

            Text message = new Text("Game Completed!");
            message.setFont(Font.font("Tahoma", FontWeight.BLACK, 20));
            message.setFill(Color.BLACK);
            message.setOpacity(1);
            stage.setOpacity(1);

            root.getChildren().add(message);
            stage.show();
        }
    }

// Input -> a placement string, check if its off board - Yes or No
// helper -> take 1 piece string (cxyR)
    private void makeControls() {
        Label label1 = new Label("Placement:");
        Label label2 = new Label("Difficulty");
        Label label3 = new Label("Generating Challenges");
        textField = new TextField();
        textField.setPrefWidth(100);
        Button button = new Button("Place");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 String s = textField.getText();
                 boolean colorcheck = checkColor(s);
                 tempplacement = FitGame.addPieceToPlacement(tempplacement,s);
                 validPlacement(tempplacement,colorcheck);
                 makePlacement(gameplacement);
                 textField.clear();
                 checkFinished(gameplacement);
            }
        });

        /*set a "clear" button to clear the board*/
        Button button1 = new Button("Clear");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textField.clear();
                gameplacement = "";
                tempplacement = "";
                makePlacement(textField.getText());
            }
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button, button1);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(BOARD_HEIGHT - 50);
        controls.getChildren().add(hb);

        /*set 5 "difficulty" buttons to implement challenges starter, junior, expert, master, and wizard*/
        Button button2 = new Button("Starter");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 0;
                gameplacement = getChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button3 = new Button("Junior");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 1;
                gameplacement = getChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button4 = new Button("Expert");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 2;
                gameplacement = getChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button5 = new Button("Master");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 3;
                gameplacement = getChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button6 = new Button("Wizard");
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 4;
                gameplacement = getChallenge(i);
                tempplacement = gameplacement;
                makePlacement(tempplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });

        HBox hb7 = new HBox();
        hb7.getChildren().add(label2);
        hb7.setSpacing(10);
        hb7.setLayoutX(750);
        hb7.setLayoutY(BOARD_HEIGHT-150);
        controls.getChildren().add(hb7);

        HBox hb2 = new HBox();
        hb2.getChildren().add(button2);
        hb2.setSpacing(10);
        hb2.setLayoutX(820);
        hb2.setLayoutY(BOARD_HEIGHT-150);
        controls.getChildren().add(hb2);

        HBox hb3 = new HBox();
        hb3.getChildren().add(button3);
        hb3.setSpacing(10);
        hb3.setLayoutX(820);
        hb3.setLayoutY(BOARD_HEIGHT-120);
        controls.getChildren().add(hb3);

        HBox hb4 = new HBox();
        hb4.getChildren().add(button4);
        hb4.setSpacing(10);
        hb4.setLayoutX(820);
        hb4.setLayoutY(BOARD_HEIGHT-90);
        controls.getChildren().add(hb4);

        HBox hb5 = new HBox();
        hb5.getChildren().add(button5);
        hb5.setSpacing(10);
        hb5.setLayoutX(820);
        hb5.setLayoutY(BOARD_HEIGHT-60);
        controls.getChildren().add(hb5);

        HBox hb6 = new HBox();
        hb6.getChildren().add(button6);
        hb6.setSpacing(10);
        hb6.setLayoutX(820);
        hb6.setLayoutY(BOARD_HEIGHT-30);
        controls.getChildren().add(hb6);

        HBox hb8 = new HBox();
        hb8.getChildren().add(label3);
        hb8.setSpacing(10);
        hb8.setLayoutY(BOARD_HEIGHT-150);
        hb8.setLayoutX(550);
        controls.getChildren().add(hb8);

        /*set 5 "difficulty" buttons to implement challenges starter, junior, expert, master, and wizard which are all generated*/
        Button button7 = new Button("Starter");
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 1;
                gameplacement = createChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button8 = new Button("Junior");
        button8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 2;
                gameplacement = createChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button9 = new Button("Expert");
        button9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 3;
                gameplacement = createChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button10 = new Button("Master");
        button10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 4;
                gameplacement = createChallenge(i);
                tempplacement = gameplacement;
                makePlacement(gameplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });
        Button button11 = new Button("Wizard");
        button11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int i = 5;
                gameplacement = createChallenge(i);
                tempplacement = gameplacement;
                makePlacement(tempplacement);
                solution = FitGame.getSolution(gameplacement);
            }
        });

        HBox hb9 = new HBox();
        hb9.getChildren().add(button7);
        hb9.setSpacing(10);
        hb9.setLayoutX(680);
        hb9.setLayoutY(BOARD_HEIGHT-150);
        controls.getChildren().add(hb9);

        HBox hb10 = new HBox();
        hb10.getChildren().add(button8);
        hb10.setSpacing(10);
        hb10.setLayoutX(680);
        hb10.setLayoutY(BOARD_HEIGHT-120);
        controls.getChildren().add(hb10);

        HBox hb11 = new HBox();
        hb11.getChildren().add(button9);
        hb11.setSpacing(10);
        hb11.setLayoutX(680);
        hb11.setLayoutY(BOARD_HEIGHT-90);
        controls.getChildren().add(hb11);

        HBox hb12 = new HBox();
        hb12.getChildren().add(button10);
        hb12.setSpacing(10);
        hb12.setLayoutX(680);
        hb12.setLayoutY(BOARD_HEIGHT-60);
        controls.getChildren().add(hb12);

        HBox hb13 = new HBox();
        hb13.getChildren().add(button11);
        hb13.setSpacing(10);
        hb13.setLayoutX(680);
        hb13.setLayoutY(BOARD_HEIGHT-30);
        controls.getChildren().add(hb13);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FitGame");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(gpieces);
        root.getChildren().add(controls);
        root.getChildren().add(board);

        makeControls();
        makeboard();
        scene.setOnKeyTyped(keyEvent -> {
            if(keyEvent.getCharacter().equals("/")){
                String s;
                s = FitGame.addPieceToPlacement(gameplacement,getHint(gameplacement,solution));
                makePlacement(s);
                gameplacement = s;
                tempplacement = gameplacement;
                checkFinished(gameplacement);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
