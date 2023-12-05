package com.example.projetjeuxechecmacabiaufrederic;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Cases extends View {
    private String position;
    private int size;
    private int top_margin;
    private int left_margin;
    private int [] color={255,0,0,0};
    private String imageLink;
    public Cases(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void SetImage(String comparePosition, String addImageLink){
        if(position==comparePosition){
            imageLink=addImageLink;
        }
    }

    public void SetColor(String comparePosition, int[] newColor){
        if(position==comparePosition){
            for(int i=1; i<4; i++){
                color[i]=newColor[i];
            }
        }
    }

    public String GetImage(){
        return imageLink;
    }

    public int[] GetColor(){
        return color;
    }
}
