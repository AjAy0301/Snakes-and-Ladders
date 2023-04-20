package com.example.snakesandladders;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player {
    private String playerName;
    private Circle coin;
    private int currentPosition;

    private static Board gameBoard = new Board();

    public Player(String name, Color coinColor){
        playerName = name;
        coin = new Circle(SnakesLadders.tileSize/3);
        coin.setFill(coinColor);
        currentPosition = 1;
        movePlayer(0);
    }

    public void movePlayer(int diceValue){
        if(diceValue+currentPosition<=100){
            currentPosition += diceValue;

            TranslateTransition firstTransition = translateAnimation();
            TranslateTransition secondTransition = null;

            int newPosition = gameBoard.getNewPosition(currentPosition);
            if(newPosition != currentPosition && currentPosition != -1){
                currentPosition = newPosition;
                secondTransition = translateAnimation();
            }
            if(secondTransition == null){
                firstTransition.play();
            }else{
                SequentialTransition sequentialTransition = new SequentialTransition(firstTransition,
                        new PauseTransition(Duration.millis(400)), secondTransition
                );
                sequentialTransition.play();
            }
        }
    }

    private TranslateTransition translateAnimation(){
        TranslateTransition animate = new TranslateTransition(Duration.millis(800),coin);
        animate.setToX(gameBoard.getXCoordinate(currentPosition));
        animate.setToY(gameBoard.getYCoordinate(currentPosition));
        animate.setAutoReverse(false);
        return animate;
    }

    boolean checkWinner(){
        if(currentPosition==100){
            showDialogInformation("Player "+playerName+" Won the Game");
            return true;
        }
        return false;
    }

    public void resetPlayer(){
        currentPosition = 0;
        movePlayer(1);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Circle getCoin() {
        return coin;
    }

    private void showDialogInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Winner");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
