package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Cases extends View {
    private int [] color={255,0,0,0};

    private int apparence=0;//R.drawable.black_pion
    public Cases(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int hauteur = getMeasuredHeight();
        int largeur = getMeasuredWidth();
        Paint paint = new Paint();
        paint.setARGB(color[0],color[1],color[2],color[3]);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(largeur,hauteur,0,0, paint);
        if(apparence!=0){
            Bitmap monImage = BitmapFactory.decodeResource(getResources(), apparence);
            canvas.drawBitmap(monImage, 0, 0, null);
            invalidate(); //Efface pour redessiner.
        }
    }

    public void defColor(int[] newColor){
        Log.v("MyActivity","new color");
        for(int i=1; i<4; i++){
            color[i]=newColor[i];
            refreshPaint();
        }
    }
    public void setApparence(int initApparence){
        apparence=initApparence;
        refreshPaint();
    }

    void refreshPaint(){
        Paint paint = new Paint();
        paint.setARGB(color[0],color[1],color[2],color[3]);
        paint.setStyle(Paint.Style.FILL);
        invalidate();
    }
}
