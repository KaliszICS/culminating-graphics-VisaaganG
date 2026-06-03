import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


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

        //
        TextField guessField = new TextField();
        board.add(guessField, 0, 7, 5, 1);

        Button guessButton = new Button("Enter");
        board.add(guessButton, 0, 8, 5, 1);

        Label message = new Label("Enter a 5-letter word");
        board.add(message, 0, 9, 5, 1);


        guessButton.setOnAction(e -> {
        String guess;
        guess = guessField.getText().toUpperCase().trim();

            if (guess.length() != 5) {
                message.setText("Word must be 5 Letters!");
                return;
            }

            if (currentRow >= 6) {
                return;
            }
            //Validating input, matching guess with answer, and providing feedback
            for (int col = 0; col < 5; col++) {

                char guessLetter = guess.charAt(col);
                char answerLetter = randomWord.charAt(col);

                cells[currentRow][col].setText(
                    String.valueOf(guessLetter)
                );
                //Green
                if (guessLetter == answerLetter) {
                    cells[currentRow][col].setStyle(
                        "-fx-background-color: green;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: black;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
                    );
                }
                //Yellow
                else if (randomWord.contains(guessLetter))) {

                    cells[currentRow][col].setStyle(
                        "-fx-background-color: gold;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
                    );
                }

                //Gray
                else { 
                    cells[currentRow][col].setStyle(
                        "-fx-background-color: lightgray;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
                    );
                }
            }
        


        //makes it all visible
        stage.setScene(scene);
        stage.show();
        
        }
    );
    }
    public static void main(String[] args) {
        launch();
    }
}
