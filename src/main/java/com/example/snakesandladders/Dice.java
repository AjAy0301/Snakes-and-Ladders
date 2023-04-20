package com.example.snakesandladders;

import javafx.scene.image.ImageView;

public class Dice {
    public int rolledDiceValue(){
        return (int)(Math.random()*6 + 1);
    }
    public ImageView diceImage(int diceValue){
        ImageView diceImg = new ImageView("file:/Users/ajay/IdeaProjects/Snakes-and-Ladders/src/main/resources/"+diceValue+".png");
        diceImg.setFitHeight(30);
        diceImg.setFitWidth(30);
        return diceImg;
    }
}
