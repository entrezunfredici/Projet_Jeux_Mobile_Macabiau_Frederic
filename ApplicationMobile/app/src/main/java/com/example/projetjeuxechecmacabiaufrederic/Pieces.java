package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.util.AttributeSet;

public class Pieces {
    int ID;
    String name;
    String apparence;
    int posX;
    int posY;
    char directionMove;
    int minimalMove;
    int maximalMove;
    char directionCapture;
    int minimalCapture;
    int maximalCapture;
    int [][] movePositions={{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};
    int [][] capturePositions={{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};

    public Pieces(Context context, AttributeSet attrs) {

    }
    public int GetPosX(){
        return posX;
    }
    public int GetPosY(){
        return posY;
    }
    public int [][] GetMovePositions(){return movePositions;}
    public int [][] GetCapturePositions(){return capturePositions;}

    public int SetMovement(int newPosX, int newPosY){
        int [][] setMovePositions={{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};
        setMovePositions[newPosX][newPosY]=1;
        for(int i=1; i<9; i++){
            for(int k=1; k<9; k++){
                if(movePositions[i][k]==setMovePositions[i][k]){
                    return 1;
                }
            }
        }
        return 0;
    }
}
