package com.example.projetjeuxechecmacabiaufrederic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Element extends View {
    private int [] color={255,0,0,0};
    public Element(Context context, AttributeSet attrs) {
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
    }
}