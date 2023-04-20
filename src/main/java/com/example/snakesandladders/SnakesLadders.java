package com.example.snakesandladders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
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
        BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf("#E6E6FA"), new CornerRadii(0), new Insets(0));
        Background background = new Background(backgroundFill);
        root.setBackground(background);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img = new Image("file:/Users/ajay/IdeaProjects/Snakes-and-Ladders/src/main/resources/board_image.jpeg");
        ImageView board =new ImageView() ;
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        //Buttons
        Button playerOneButton = new Button("");
        playerOneButton.setGraphic(dice.diceImage(1));
        playerOneButton.setPadding(new Insets(0));
        playerOneButton.setTranslateX(60);
        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setDisable(true);

        Button playerTwoButton = new Button("");
        playerTwoButton.setGraphic(dice.diceImage(1));
        playerTwoButton.setPadding(new Insets(0));
        playerTwoButton.setTranslateX(400);
        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setDisable(true);

        Button startButton = new Button("Start");
        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(230);

        playerOne = new Player("RED", Color.RED);
        playerTwo = new Player("BLUE", Color.BLUE);

        //info display
        Label playerOneLabel = new Label("Welcome! "+ playerOne.getPlayerName());
        Label playerTwoLabel = new Label("Welcome! "+ playerTwo.getPlayerName());
        Label diceLabel = new Label("Start the Game");

        playerOneLabel.setTranslateY(infoLine);
        playerOneLabel.setTranslateX(30);
        playerTwoLabel.setTranslateY(infoLine);
        playerTwoLabel.setTranslateX(380);
        diceLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(210);

        //Start Button Action
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                startButton.setText("Game Started");
                startButton.setDisable(true);
                diceLabel.setText("Dice Value:  ");

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
                playerOneButton.setGraphic(dice.diceImage(diceValue));
                diceLabel.setText("Dice Value:  "+diceValue);
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
                    playerTwoButton.setGraphic(dice.diceImage(1));
                }


            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int diceValue = dice.rolledDiceValue();
                playerTwoButton.setGraphic(dice.diceImage(diceValue));
                diceLabel.setText("Dice Value:  "+diceValue);
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
                    playerOneButton.setGraphic(dice.diceImage(1));
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
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        java.awt.Image image = defaultToolkit.getImage(getClass().getResource("/icon.png"));
        final Taskbar taskbar = Taskbar.getTaskbar();
        taskbar.setIconImage(image);
        Scene scene = new Scene(createContent());
        Image icon = new Image("file:/Users/ajay/IdeaProjects/Snakes-and-Ladders/src/main/resources/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Snakes and Ladders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}