package comp1110.ass2.gui;

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
import javafx.stage.Stage;

/**
 * A very simple viewer for piece placements in the IQ-Fit game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    /*the grid is 10*5*/
    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;/*12*/
    private static final int VIEWER_HEIGHT = 480;/*8*/

    private static final String URI_BASE = "assets/";


    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    private final Group gpieces = new Group();
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // gpieces.getChildren().clear();
        int l = placement.length();

        char[] c = placement.toCharArray();
        String s = "";
        String pname = "";

        for(int i = 0; i < l ; i += 4){
            /*load pic*/
            pname = String.valueOf(c[i])+String.valueOf(c[i+1])+String.valueOf(c[i+2])+String.valueOf(c[i+3]);
            makepiece(new piecepic(pname));

        }
        gpieces.toFront();

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    /**
     * add board pic.
     */
    public void makeboard(){
        // board.getChildren().clear();

        ImageView baseboard = new ImageView();
        baseboard.setImage(new Image(Viewer.class.getResource(URI_BASE + "board.png" ).toString()));
        baseboard.setFitWidth(VIEWER_WIDTH);
        baseboard.setFitHeight(VIEWER_HEIGHT-2*SQUARE_SIZE);

        board.getChildren().add(baseboard);

        board.toBack();
    }

    public void makepiece(piecepic piecepic){
        gpieces.getChildren().add(piecepic);
    }

    class piecepic extends ImageView{

        piecepic(String pname){
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
            Image image = new Image(Viewer.class.getResource( URL ).toString());
            setImage(image);

            /* rotate pic*/
            double angle = 0;
            switch (c[3]){
                case'N': angle = 0;break;
                case'S': angle = 180;break;
                case'E': angle = 90;break;
                case'W': angle = 270;break;
            }

            setRotate(angle);

            double w = (image.getWidth()/100) * SQUARE_SIZE;
            double h = (image.getHeight()/100) * SQUARE_SIZE;
            setFitHeight(h);
            setFitWidth(w);
            double x = 0;
            double y = 0;
            if(c[3] == 'N' || c[3] == 'S') {
                y = (Double.parseDouble(String.valueOf(c[2]))) * SQUARE_SIZE + 18;
                x = (Double.parseDouble(String.valueOf(c[1])) + 1) * SQUARE_SIZE;
            }
            else {
                y = (Double.parseDouble(String.valueOf(c[2]))) * SQUARE_SIZE + 45;
                x = (Double.parseDouble(String.valueOf(c[1])) + 1) * SQUARE_SIZE - 35;
            }
            setLayoutX(x);
            setLayoutY(y);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("FitGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(gpieces);
        root.getChildren().add(controls);
        root.getChildren().add(board);

        makeControls();
        makeboard();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
