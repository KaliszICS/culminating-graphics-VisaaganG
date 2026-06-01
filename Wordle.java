import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;



public class Wordle extends Application {

    Label[][] cells = new Label[6][5];
    int currentRow = 0;
    String randomWord = "";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Visaagan's Wordle");
        

        //Creates the grid that holds the letters
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(5);
        board.setVgap(5);

        //creates 6 rows and 5 columns
        for (int row = 0; row < 6; row++) {

            for (int col = 0; col < 5; col++) {

                Label square = new Label();

                square.setPrefSize(60, 60);
                square.setAlignment(Pos.CENTER);

                square.setStyle(
                    "-fx-border-color: black;" +
                    "-fx-font-size: 24px;" +
                    "-fx-font-weight: black;"
                );

                cells[row][col] = square;

                board.add(square, col, row);
            }
        }

        //creates the window to play in and adds the "board" onto it
        Scene scene = new Scene(board, 600, 700);
        
        //makes it visible
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
