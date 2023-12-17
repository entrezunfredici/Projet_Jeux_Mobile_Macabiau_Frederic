package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class Pieces {
    String name;
    String apparence;
    int orientation;
    int posX;
    int posY;
    String [] directionMove= new String[8];
    int minimalMove;
    int maximalMove;
    String [] directionCapture= new String[8];
    int minimalCapture;
    int maximalCapture;
    int [][] movePositions={
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
    };
    int [][] capturePositions={
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
    };

    protected void PieceCreate(
            Context context, AttributeSet attrs,
            String initName, String initApparence,
            String initOrientation, int initPosX, int initPosY,
            String [] initDirectionMove, int initMinimalMove, int initMaximalMove,
            String [] initDirectionCapture, int initMinimalCapture, int initMaximalCapture
            ) {
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
        minimalCapture=initMinimalCapture*orientation;
        if(initMaximalCapture==-1){
            maximalCapture=8*orientation;
        }else{
            maximalCapture=initMaximalMove*orientation;
        }
        int initDeltaMove=maximalMove-minimalMove;
        int initDeltaCapture=maximalCapture-minimalCapture;
        for(int i=0; i<8; i++){
            if(initDirectionMove[i]!=""){
                directionMove[i]=initDirectionMove[i];
                int deltaMove=initDeltaMove;
                switch(directionMove[i]){
                    case "TOP":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if((posX+deltaMove+minimalMove>0)&(posX+deltaMove+minimalMove<0)){
                                movePositions[posY][posX+deltaMove+minimalMove]=1;
                            }
                        }
                        break;
                    case "BOTTOM":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if((posX-deltaMove>0)&(posX-deltaMove+minimalMove<0)){
                                movePositions[posY][posX-deltaMove+minimalMove]=1;
                            }
                        }
                        break;
                    case "RIGHT":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if((posY+deltaMove+minimalMove>0)&(posY+deltaMove+minimalMove<0)){
                                movePositions[posY+deltaMove+minimalMove][posX]=1;
                            }
                        }
                        break;
                    case "LEFT":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if((posY-deltaMove+minimalMove>0)&(posY-deltaMove+minimalMove<0)){
                                movePositions[posY-deltaMove+minimalMove][posX]=1;
                            }
                        }
                        break;
                    case "RIGHT TOP":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if(((posX+deltaMove+minimalMove>0)&(posX+deltaMove+minimalMove<0))
                            &((posY+deltaMove+minimalMove>0)&(posY+deltaMove+minimalMove<0))){
                                movePositions[posY+deltaMove+minimalMove][posX+deltaMove+minimalMove]=1;
                            }
                        }
                        break;
                    case "LEFT TOP":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if(((posX+deltaMove+minimalMove>0)&(posX+deltaMove+minimalMove<0))
                                    &((posY-deltaMove+minimalMove>0)&(posY-deltaMove+minimalMove<0))){
                                movePositions[posY-deltaMove+minimalMove][posX+deltaMove+minimalMove]=1;
                            }
                        }
                        break;
                    case "RIGHT BOTTOM":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if(((posX-deltaMove+minimalMove>0)&(posX-deltaMove+minimalMove<0))
                                    &((posY+deltaMove+minimalMove>0)&(posY+deltaMove+minimalMove<0))){
                                movePositions[posY+deltaMove+minimalMove][posX-deltaMove+minimalMove]=1;
                            }
                        }
                        break;
                    case "LEFT BOTTOM":
                        while(deltaMove!=0){
                            deltaMove-=orientation;
                            if(((posX-deltaMove+minimalMove>0)&(posX-deltaMove+minimalMove<0))
                                    &((posY-deltaMove+minimalMove>0)&(posY-deltaMove+minimalMove<0))){
                                movePositions[posY-deltaMove+minimalMove][posX-deltaMove+minimalMove]=1;
                            }
                        }
                        break;
                }
            }else{
                directionMove[i]="NULL";
            }
            if(initDirectionCapture[i]!=""){
                directionCapture[i]=initDirectionCapture[i];
                int deltaCapture=initDeltaCapture;
                switch(directionCapture[i]){
                    case "TOP":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if((posX+deltaCapture+minimalCapture>0)&(posX+deltaCapture+minimalCapture<0)){
                                capturePositions[posY][posX+deltaCapture+minimalCapture]=1;
                            }
                        }
                        break;
                    case "BOTTOM":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if((posX-deltaCapture>0)&(posX-deltaCapture+minimalCapture<0)){
                                capturePositions[posY][posX-deltaCapture+minimalCapture]=1;
                            }
                        }
                        break;
                    case "RIGHT":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if((posY+deltaCapture+minimalCapture>0)&(posY+deltaCapture+minimalCapture<0)){
                                capturePositions[posY+deltaCapture+minimalCapture][posX]=1;
                            }
                        }
                        break;
                    case "LEFT":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if((posY-deltaCapture+minimalCapture>0)&(posY-deltaCapture+minimalCapture<0)){
                                capturePositions[posY-deltaCapture+minimalCapture][posX]=1;
                            }
                        }
                        break;
                    case "RIGHT TOP":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if(((posX+deltaCapture+minimalCapture>0)&(posX+deltaCapture+minimalCapture<0))
                                    &((posY+deltaCapture+minimalCapture>0)&(posY+deltaCapture+minimalCapture<0))){
                                capturePositions[posY+deltaCapture+minimalCapture][posX+deltaCapture+minimalCapture]=1;
                            }
                        }
                        break;
                    case "LEFT TOP":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if(((posX+deltaCapture+minimalMove>0)&(posX+deltaCapture+minimalCapture<0))
                                    &((posY-deltaCapture+minimalCapture>0)&(posY-deltaCapture+minimalCapture<0))){
                                capturePositions[posY-deltaCapture+minimalCapture][posX+deltaCapture+minimalCapture]=1;
                            }
                        }
                        break;
                    case "RIGHT BOTTOM":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if(((posX-deltaCapture+minimalCapture>0)&(posX-deltaCapture+minimalCapture<0))
                                    &((posY+deltaCapture+minimalCapture>0)&(posY+deltaCapture+minimalCapture<0))){
                                capturePositions[posY+deltaCapture+minimalCapture][posX-deltaCapture+minimalCapture]=1;
                            }
                        }
                        break;
                    case "LEFT BOTTOM":
                        while(deltaCapture!=0){
                            deltaCapture-=orientation;
                            if(((posX-deltaCapture+minimalCapture>0)&(posX-deltaCapture+minimalCapture<0))
                                    &((posY-deltaCapture+minimalCapture>0)&(posY-deltaCapture+minimalCapture<0))){
                                capturePositions[posY-deltaCapture+minimalCapture][posX-deltaCapture+minimalCapture]=1;
                            }
                        }
                        break;
                }
            }else{
                directionCapture[i]="NULL";
            }
        }


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
        for(int i=0; i<8; i++){
            for(int k=0; k<8; k++){
                if(movePositions[i][k]==setMovePositions[i][k]){
                    posX=newPosX;
                    posY=newPosY;
                    int initDeltaMove=maximalMove-minimalMove;
                    for(int j=0; j<8; j++){
                        if(directionMove[j]!="NULL"){
                            int deltaMove=initDeltaMove;
                            switch(directionMove[j]){
                                case "TOP":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if((posX+deltaMove+minimalMove>0)&(posX+deltaMove+minimalMove<0)){
                                            movePositions[posY][posX+deltaMove+minimalMove]=1;
                                        }
                                    }
                                    break;
                                case "BOTTOM":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if((posX-deltaMove>0)&(posX-deltaMove+minimalMove<0)){
                                            movePositions[posY][posX-deltaMove+minimalMove]=1;
                                        }
                                    }
                                    break;
                                case "RIGHT":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if((posY+deltaMove+minimalMove>0)&(posY+deltaMove+minimalMove<0)){
                                            movePositions[posY+deltaMove+minimalMove][posX]=1;
                                        }
                                    }
                                    break;
                                case "LEFT":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if((posY-deltaMove+minimalMove>0)&(posY-deltaMove+minimalMove<0)){
                                            movePositions[posY-deltaMove+minimalMove][posX]=1;
                                        }
                                    }
                                    break;
                                case "RIGHT TOP":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if(((posX+deltaMove+minimalMove>0)&(posX+deltaMove+minimalMove<0))
                                                &((posY+deltaMove+minimalMove>0)&(posY+deltaMove+minimalMove<0))){
                                            movePositions[posY+deltaMove+minimalMove][posX+deltaMove+minimalMove]=1;
                                        }
                                    }
                                    break;
                                case "LEFT TOP":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if(((posX+deltaMove+minimalMove>0)&(posX+deltaMove+minimalMove<0))
                                                &((posY-deltaMove+minimalMove>0)&(posY-deltaMove+minimalMove<0))){
                                            movePositions[posY-deltaMove+minimalMove][posX+deltaMove+minimalMove]=1;
                                        }
                                    }
                                    break;
                                case "RIGHT BOTTOM":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if(((posX-deltaMove+minimalMove>0)&(posX-deltaMove+minimalMove<0))
                                                &((posY+deltaMove+minimalMove>0)&(posY+deltaMove+minimalMove<0))){
                                            movePositions[posY+deltaMove+minimalMove][posX-deltaMove+minimalMove]=1;
                                        }
                                    }
                                    break;
                                case "LEFT BOTTOM":
                                    while(deltaMove!=0){
                                        deltaMove-=orientation;
                                        if(((posX-deltaMove+minimalMove>0)&(posX-deltaMove+minimalMove<0))
                                                &((posY-deltaMove+minimalMove>0)&(posY-deltaMove+minimalMove<0))){
                                            movePositions[posY-deltaMove+minimalMove][posX-deltaMove+minimalMove]=1;
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                    return 1;
                }
            }
        }
        return 0;
    }
    public int SetCapture(int newPosX, int newPosY){
        int [][] setCapturePosition={{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}};
        setCapturePosition[newPosX][newPosY]=1;
        for(int i=0; i<8; i++){
            for(int k=0; k<8; k++){
                if(capturePositions[i][k]==setCapturePosition[i][k]){
                    posX=newPosX;
                    posY=newPosY;
                    int initDeltaCapture=maximalMove-minimalMove;
                    for(int j=0; j<8; j++){
                        if(directionCapture[j]!="NULL"){
                            int deltaCapture=initDeltaCapture;
                            switch(directionCapture[j]){
                                case "TOP":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if((posX+deltaCapture+minimalCapture>0)&(posX+deltaCapture+minimalCapture<0)){
                                            capturePositions[posY][posX+deltaCapture+minimalCapture]=1;
                                        }
                                    }
                                    break;
                                case "BOTTOM":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if((posX-deltaCapture>0)&(posX-deltaCapture+minimalCapture<0)){
                                            capturePositions[posY][posX-deltaCapture+minimalCapture]=1;
                                        }
                                    }
                                    break;
                                case "RIGHT":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if((posY+deltaCapture+minimalCapture>0)&(posY+deltaCapture+minimalCapture<0)){
                                            capturePositions[posY+deltaCapture+minimalCapture][posX]=1;
                                        }
                                    }
                                    break;
                                case "LEFT":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if((posY-deltaCapture+minimalCapture>0)&(posY-deltaCapture+minimalCapture<0)){
                                            capturePositions[posY-deltaCapture+minimalCapture][posX]=1;
                                        }
                                    }
                                    break;
                                case "RIGHT TOP":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if(((posX+deltaCapture+minimalCapture>0)&(posX+deltaCapture+minimalCapture<0))
                                                &((posY+deltaCapture+minimalCapture>0)&(posY+deltaCapture+minimalCapture<0))){
                                            capturePositions[posY+deltaCapture+minimalCapture][posX+deltaCapture+minimalCapture]=1;
                                        }
                                    }
                                    break;
                                case "LEFT TOP":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if(((posX+deltaCapture+minimalMove>0)&(posX+deltaCapture+minimalCapture<0))
                                                &((posY-deltaCapture+minimalCapture>0)&(posY-deltaCapture+minimalCapture<0))){
                                            capturePositions[posY-deltaCapture+minimalCapture][posX+deltaCapture+minimalCapture]=1;
                                        }
                                    }
                                    break;
                                case "RIGHT BOTTOM":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if(((posX-deltaCapture+minimalCapture>0)&(posX-deltaCapture+minimalCapture<0))
                                                &((posY+deltaCapture+minimalCapture>0)&(posY+deltaCapture+minimalCapture<0))){
                                            capturePositions[posY+deltaCapture+minimalCapture][posX-deltaCapture+minimalCapture]=1;
                                        }
                                    }
                                    break;
                                case "LEFT BOTTOM":
                                    while(deltaCapture!=0){
                                        deltaCapture-=orientation;
                                        if(((posX-deltaCapture+minimalCapture>0)&(posX-deltaCapture+minimalCapture<0))
                                                &((posY-deltaCapture+minimalCapture>0)&(posY-deltaCapture+minimalCapture<0))){
                                            capturePositions[posY-deltaCapture+minimalCapture][posX-deltaCapture+minimalCapture]=1;
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                    return 1;
                }
            }
        }
        return 0;
    }
}
