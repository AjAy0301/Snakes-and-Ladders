package com.example.snakesandladders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakesLadders extends Application {
    public static int tileSize = 50, height = 10, width = 10;

    public static int buttonLine = height*tileSize + 60;
    public static int infoLine = buttonLine - 30;

    private Player playerOne, playerTwo;

    private boolean playerOneTurn, playerTwoTurn, gameStarted;
    private static Dice dice = new Dice();
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(tileSize*width, tileSize*height+100);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img = new Image("file:/Users/ajay/IdeaProjects/Snakes-and-Ladders/src/board_image.jpeg");
        ImageView board =new ImageView() ;
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        //Buttons
        Button playerOneButton = new Button("Roll Dice");
        playerOneButton.setTranslateX(30);
        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setDisable(true);

        Button playerTwoButton = new Button("Roll Dice");
        playerTwoButton.setTranslateX(400);
        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setDisable(true);

        Button startButton = new Button("Start");
        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(230);

        playerOne = new Player("Player 1", Color.RED);
        playerTwo = new Player("Player 2", Color.BLUE);

        //info display
        Label playerOneLabel = new Label("Welcome! "+ playerOne.getPlayerName());
        Label playerTwoLabel = new Label("Welcome! "+ playerTwo.getPlayerName());
        Label diceLabel = new Label("Start the Game");

        playerOneLabel.setTranslateY(infoLine);
        playerOneLabel.setTranslateX(30);
        playerTwoLabel.setTranslateY(infoLine);
        playerTwoLabel.setTranslateX(380);
        diceLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(230);

        //Start Button Action
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                startButton.setText("Game Started");
                startButton.setDisable(true);
                playerOne.resetPlayer();
                playerTwo.resetPlayer();
                playerOneLabel.setText("Your Turn " + playerOne.getPlayerName());
                playerOneButton.setDisable(false);
                playerOneTurn = true;
                playerTwoLabel.setText("Wait for your Turn");
                playerTwoTurn = false;
            }
        });

        //players action
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int diceValue = dice.rolledDiceValue();
                diceLabel.setText("Dice Value: "+diceValue);
                playerOne.movePlayer(diceValue);
                playerOneTurn = false;
                playerOneButton.setDisable(true);
                if(playerOne.checkWinner()){
                    playerOneLabel.setText("");
                    diceLabel.setText(playerOne.getPlayerName()+" Won the Game!");
                    playerTwoLabel.setText("");
                    startButton.setDisable(false);
                    startButton.setText("Restart");
                }else{
                    playerOneLabel.setText("Wait for your Turn");
                    playerTwoTurn = true;
                    playerTwoButton.setDisable(false);
                    playerTwoLabel.setText("Your Turn " + playerTwo.getPlayerName());
                }

            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int diceValue = dice.rolledDiceValue();
                diceLabel.setText("Dice Value: "+diceValue);
                playerTwo.movePlayer(diceValue);
                playerTwoTurn = false;
                playerTwoButton.setDisable(true);
                if(playerTwo.checkWinner()){
                    playerTwoLabel.setText("");
                    diceLabel.setText(playerTwo.getPlayerName()+" Won the Game!");
                    playerOneLabel.setText("");
                    startButton.setDisable(false);
                    startButton.setText("Restart");
                }else{
                    playerTwoLabel.setText("Wait for your Turn");
                    playerOneTurn = true;
                    playerOneButton.setDisable(false);
                    playerOneLabel.setText("Your Turn " + playerOne.getPlayerName());
                }

            }
        });

        root.getChildren().addAll(board,
                playerOneButton, playerTwoButton, startButton,
                playerOneLabel, playerTwoLabel, diceLabel,
                playerOne.getCoin(), playerTwo.getCoin()
        );
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        Image icon = new Image("file:/Users/ajay/IdeaProjects/Snakes-and-Ladders/src/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Snakes and Ladders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}