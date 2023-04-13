package com.example.snakesandladders;

import javafx.util.Pair;

import java.util.ArrayList;

public class Board {
    private ArrayList<Pair<Integer,Integer>> positionCoordinates;

    private ArrayList<Integer> snakeLadderPosition;

    public Board(){
        positionCoordinates = new ArrayList<>();
        populatePositionCoordinates();
        populateSnakeLadderPositions();
    }

    private void populatePositionCoordinates(){
        positionCoordinates.add(new Pair<>(0,0));   //dummy value

        for (int i = 0; i < SnakesLadders.height; i++) {
            for (int j = 0; j < SnakesLadders.width; j++) {
                //x coordinate
                int xCord =0;
                if(i%2==0){
                    xCord = (j*SnakesLadders.tileSize) + SnakesLadders.tileSize/2;
                }else{
                    xCord = SnakesLadders.tileSize*SnakesLadders.height - (j*SnakesLadders.tileSize) - SnakesLadders.tileSize/2;
                }

                //y coordinate
                int yCord = SnakesLadders.tileSize*SnakesLadders.height - (i*SnakesLadders.tileSize) - SnakesLadders.tileSize/2;
                positionCoordinates.add(new Pair<>(xCord,yCord));
            }
        }
    }

    private void populateSnakeLadderPositions(){
        snakeLadderPosition = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            snakeLadderPosition.add(i);
        }
        snakeLadderPosition.set(4,25);
        snakeLadderPosition.set(21,39);
        snakeLadderPosition.set(29,74);
        snakeLadderPosition.set(43,76);
        snakeLadderPosition.set(63,80);
        snakeLadderPosition.set(71,89);
        snakeLadderPosition.set(30,7);
        snakeLadderPosition.set(47,15);
        snakeLadderPosition.set(56,19);
        snakeLadderPosition.set(82,42);
        snakeLadderPosition.set(92,75);
        snakeLadderPosition.set(73,51);
        snakeLadderPosition.set(98,55);
    }

    public int getNewPosition(int currentPosition){
        if(currentPosition>0 && currentPosition<=100){
            return snakeLadderPosition.get(currentPosition);
        }
        return -1;
    }
    public int getXCoordinate(int pos){
        return positionCoordinates.get(pos).getKey();
    }
    public int getYCoordinate(int pos){
        return positionCoordinates.get(pos).getValue();
    }
    public void display(){
        for(Pair p: positionCoordinates){
            System.out.println("x: "+ p.getKey()+"  y: "+p.getValue());
        }
    }
}
