package com.example.projetjeuxechecmacabiaufrederic;

import android.util.Log;

public class Pieces {
    static String name;
    static int apparence;
    static int orientation;
    static int posX;
    static int posY;
    static String [] directionMove= new String[8];
    static int minimalMove;
    static int maximalMove;
    static String [] directionCapture= new String[8];
    static int minimalCapture;
    static int maximalCapture;
    static int [][] movePositions={
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
    };
    static int [][] capturePositions={
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
    };
    public Pieces(
            String initName, int initApparence,
            String initOrientation, int initPosX, int initPosY,
            String [] initDirectionMove, int initMinimalMove, int initMaximalMove,
            String [] initDirectionCapture, int initMinimalCapture, int initMaximalCapture
            ) {
        super();
        Log.d("nouvelle piece","creation");
        name=initName;
        apparence=initApparence;
        if(initOrientation=="North"){
            orientation=1;
        }else{
            orientation=-1;
        }
        posX=initPosX;
        posY=initPosY;
        minimalMove=initMinimalMove*orientation;
        if(initMaximalMove==-1){
            maximalMove=8*orientation;
        }else{
            maximalMove=initMaximalMove*orientation;
        }
        Log.d("nouvelle piece","maxmove"+maximalMove+"minMove"+minimalMove);
        minimalCapture=initMinimalCapture*orientation;
        if(initMaximalCapture==-1){
            maximalCapture=8*orientation;
        }else{
            maximalCapture=initMaximalMove*orientation;
        }
        directionMove=initDirectionMove;
        directionCapture=initDirectionCapture;
        setCases(movePositions, posX, posY, minimalMove, maximalMove, orientation, directionMove);
        setCases(capturePositions, posX, posY, minimalCapture, maximalCapture, orientation, directionCapture);
    }

    static void setCases(int[][] casesToSet, int posX, int posY, int minimalDistance, int maximalDistance, int initOrientation, String[] directions){
        casesToSet[posY][posX]=2;
        for(int i=0; i<8; i++) {
            if (directions[i] != null) {
                switch (directions[i]) {
                    case "TOP":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if ((posY + n > 0) & (posY + n < 7)) {
                                casesToSet[posY + n][posX] = 1;
                            }
                        }
                        break;
                    case "BOTTOM":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if ((posY - n > 0) & (posY - n < 7)) {
                                casesToSet[posY - n][posX] = 1;
                            }
                        }
                    case "RIGHT":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if ((posX + n > 0) & (posX + n < 7)) {
                                casesToSet[posY][posX + n] = 1;
                            }
                        }
                        break;
                    case "LEFT":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if ((posX - n > 0) & (posX - n < 7)) {
                                casesToSet[posY][posX - n] = 1;
                            }
                        }
                        break;
                    case "RIGHT TOP":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if (((posX + n > 0) & (posX + n < 7)) & ((posY + n > 0) & (posY + n < 7))) {
                                casesToSet[posY + n][posX + n] = 1;
                            }
                        }
                        break;
                    case "LEFT TOP":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if (((posX - n > 0) & (posX - n < 7)) & ((posY + n > 0) & (posY + n < 7))) {
                                casesToSet[posY + n][posX - n] = 1;
                            }
                        }
                        break;
                    case "RIGHT BOTTOM":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if (((posX + n > 0) & (posX + n < 7)) & ((posY - n > 0) & (posY - n < 7))) {
                                casesToSet[posY - n][posX + n] = 1;
                            }
                        }
                        break;
                    case "LEFT BOTTOM":
                        for (int n = minimalDistance; n != maximalDistance + initOrientation; n += initOrientation) {
                            if (((posX - n > 0) & (posX - n < 7)) & ((posY - n > 0) & (posY - n < 7))) {
                                casesToSet[posY - n][posX - n] = 1;
                            }
                        }
                        break;
                }
            }
        }
    }

    public static int GetPosX(){
        return posX;
    }
    public static int GetPosY(){
        return posY;
    }
    public static int GetApparence(){return apparence;}
    public static int [][] GetMovePositions(){return movePositions;}
    public static int [][] GetCapturePositions(){return capturePositions;}

    public static int SetMovement(int newPosX, int newPosY){
        int [][] setMovePositions={{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};
        setMovePositions[newPosX][newPosY]=1;
        for(int i=0; i<8; i++){
            for(int k=0; k<8; k++){
                if(movePositions[i][k]==setMovePositions[i][k]){
                    posX=newPosX;
                    posY=newPosY;
                    setCases(movePositions, posX, posY, minimalMove, maximalMove, orientation, directionMove);
                    setCases(capturePositions, posX, posY, minimalCapture, maximalCapture, orientation, directionCapture);
                    return 1;
                }
            }
        }
        return 0;
    }
    public static int SetCapture(int newPosX, int newPosY){
        int [][] setCapturePosition={{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};
        setCapturePosition[newPosX][newPosY]=1;
        for(int i=0; i<8; i++){
            for(int k=0; k<8; k++){
                if(capturePositions[i][k]==setCapturePosition[i][k]){
                    posX=newPosX;
                    posY=newPosY;
                    setCases(movePositions, posX, posY, minimalMove, maximalMove, orientation, directionMove);
                    setCases(capturePositions, posX, posY, minimalCapture, maximalCapture, orientation, directionCapture);
                    return 1;
                }
            }
        }
        return 0;
    }
    public static void setMaximalMove(int newMaximalMove){maximalMove=newMaximalMove;}
}
