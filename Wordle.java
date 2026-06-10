/**
	* Lesson: Culminating Project
	* Author: Visaagan Gunabalachandran
	* Date Created: May 25, 2026
 	* Date Last Modified: June 10, 2026
	*/
    
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;             

public class Wordle extends Application {

    // 2D array that holds the 6 by 5 letter cells
    Label[][] cells = new Label[6][5];

    // Tracks the row the player is currently on
    int currentRow = 0;
    
    // Stores the randomly selected word
    String randomWord = "";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Visaagan's Wordle");

        //Array List that holds all valid 5-letter words loaded from the file
        ArrayList<String> words = new ArrayList<>();

        File allWordsList = new File("5-letter-words.txt");

        try (Scanner input = new Scanner(allWordsList)) {
            while (input.hasNextLine()) {
                String word = input.nextLine().trim().toUpperCase();
                
            // Only add words that are exactly 5 letters
            if (word.length() == 5) {
                words.add(word);
            }  

      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
        
   // Picks a random word from the list as the "answer" word
   Random r = new Random();
   randomWord = words.get(r.nextInt(words.size()));



    //Creates the grid that holds the letters
    GridPane board = new GridPane();
    board.setAlignment(Pos.CENTER);
    board.setHgap(5);
    board.setVgap(5);
    board.setStyle("-fx-background-color:white;");


    // Creates 6 rows and 5 columns
    for (int row = 0; row < 6; row++) {

        for (int col = 0; col < 5; col++) {

                Label square = new Label();

                square.setPrefSize(60, 60);
                square.setAlignment(Pos.CENTER);

                square.setStyle(
                    "-fx-border-color: #d3d6da ;" +
                    "-fx-font-size: 24px;"
                    
                );

                cells[row][col] = square;

                board.add(square, col, row);
            }
        }

        

        //Adds the textfield, button, and status label under the grid
        TextField guessField = new TextField();
        board.add(guessField, 0, 7, 5, 1);

        Button guessButton = new Button("Enter");
        board.add(guessButton, 0, 8, 5, 1);

        Label message = new Label("Enter a 5-letter word");
        board.add(message, 0, 9, 5, 1);

        
        guessButton.setOnAction(e -> {
            
        String guess;
        guess = guessField.getText().toUpperCase().trim();

            //Rejects guess that are not exactly 5 letters
            if (guess.length() != 5) {
                message.setText("Word must be 5 Letters!");
                return;
            }

            //Rejects words that are not in the valid word list
            if (!words.contains(guess)) {

                message.setText("Not a valid word!");
                return;

            }
            //Makes sure it doesnt go past six guesses
            if (currentRow >= 6) {
                return;
            }

            // Stores the colour result for each letter position as "green", "yellow", or "gray"
            String[] result = new String[5];

            HashMap<Character, Integer> remainingLetters = new HashMap<Character, Integer>();

            // Finds all exact matches first (Green)
            for (int col = 0; col < 5; col++) {
                char guessLetter = guess.charAt(col);
                char answerLetter = randomWord.charAt(col);

                if (guessLetter == answerLetter) {
                    //exact positing means green
                    result[col] = "green";
                } else {
                    //else means its not green, now to see if its yellow or gray we leave as "pending" 
                    result[col] = "pending";
                    remainingLetters.put(answerLetter, remainingLetters.getOrDefault(answerLetter, 0) + 1);
                }
            }

            for (int col = 0; col < 5; col++) {
                if (result[col].equals("pending")) {
                    char guessLetter = guess.charAt(col);

                    if (remainingLetters.getOrDefault(guessLetter, 0) > 0) {
                        result[col] = "yellow";
                        remainingLetters.put(guessLetter, remainingLetters.get(guessLetter) - 1);
                    } else {
                        result[col] = "gray";
                    }
            }
        }

        // Applies the colour to it's status
            for (int col = 0; col < 5; col++) {
                char guessLetter = guess.charAt(col);
                cells[currentRow][col].setText(String.valueOf(guessLetter));

                if (result[col].equals("green")) {
                     cells[currentRow][col].setStyle(
                    "-fx-background-color: #538d4e;" +
                    "-fx-text-fill: white;" +
                    "-fx-border-color: #538d4e;" +
                    "-fx-font-size: 24px;" +
                    "-fx-font-weight: bold;"
        );
               } else if (result[col].equals("yellow")) {
                    cells[currentRow][col].setStyle(
                        "-fx-background-color: #b59f3b;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #b59f3b;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );
                } else {
                    cells[currentRow][col].setStyle(
                        "-fx-background-color: #3a3a3c;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #3a3a3c;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
                    );
                }
            }

            // Checks if player guessed the word correctly
            if (guess.equals(randomWord)) {
                message.setText("You Win!");
                guessButton.setDisable(true);
                return;
            }
            
            //Move to the next row for the next guess
            currentRow++;

            // If all the rows are used and theres still no correct guess, Game Over!
            if (currentRow == 6) {
                message.setText("Game Over! Word was " + randomWord);
                guessButton.setDisable(true);
            }

            guessField.clear();
        });

        //creates the window to play in and adds the "board" onto it
        Scene scene = new Scene(board, 600, 700);        

        //makes it all visible
        stage.setScene(scene);
        stage.show();
    
    }
    
    public static void main(String[] args) {
        launch();
    }
}
