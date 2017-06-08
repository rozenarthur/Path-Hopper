package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by Arthur on 4/7/2017.
 */

public class GraphsPanel extends View {
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    Paint paint3 = new Paint();


    public GraphsPanel(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(255, 255, 255, 255);
        paint.setARGB(255, 242, 16, 16); //red color
        paint2.setARGB(255, 0, 0, 128); //blue color
        paint3.setARGB(255, 153, 0, 204); //purple color

        int users = ParentReport.n;
        int stage1 = ParentReport.k;
        int game = ParentReport.games;

        canvas.drawRect(0, (float)(canvas.getHeight()*.12), users, (float)(canvas.getHeight()*.25), paint); //bar graph for number of users
        canvas.drawRect(0, (float)(canvas.getHeight()*.34), stage1, (float)(canvas.getHeight()*.47), paint2); //bar graph for total times stage 1 beaten
        canvas.drawRect(0, (float)(canvas.getHeight()*.53), game, (float)(canvas.getHeight()*.66), paint3); //bar graph for total times stage 1 beaten


} //end onDraw

    public int getWidth(Canvas canvas)
    {
        return canvas.getWidth();
    }

    public int getHeight(Canvas canvas)
    {
        return canvas.getHeight();
    }
}
