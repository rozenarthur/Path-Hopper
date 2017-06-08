package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by m_alrajab on 3/28/16.
 */
public class MyPanel extends View {

    Paint paint = new Paint();
    private Drawable bg, bg2;

    public static int cWidth = 0;
    public static int cHeight = 0;


    final float scale = getResources().getDisplayMetrics().densityDpi; //gets the density of screen

    public MyPanel(Context context){
        super(context);
    }

    public MyPanel(Context context, AttributeSet set) {
        super(context, set);
        bg = context.getResources().getDrawable(R.raw.ocean); //ocean background
        bg2 = context.getResources().getDrawable(R.raw.ocean2); //ocean background
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawARGB(255, 0, 255, 0);
        paint.setARGB(200, 173, 216, 230); //gives light blue color


        Rect bounds = canvas.getClipBounds();

            switch(SharedValuesXY.level){ // draw a different obstacle based on the level user is on
                case 1: //level 1 obstacles
                    bg.setBounds(bounds);
                    bg.draw(canvas);//adds background to the canvas

                    cWidth = canvas.getWidth();
                    cHeight = canvas.getHeight();

                   canvas.drawRect(
                            0, //left side pixel
                            (canvas.getHeight()/2), //top side pixel
                            (canvas.getWidth()/2), //right side pixel
                           (float)((canvas.getHeight()/2) + ((canvas.getHeight()/2) * .21))//300//bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2) - (canvas.getWidth()/2)*.1),//550,
                            (float)((canvas.getHeight()/2) - (canvas.getHeight()/2)*.001),//300,
                            (canvas.getWidth()/2),
                            (canvas.getHeight())
                            , paint);
                    invalidate();
                    break;

                case 2: //level 2 obstacles
                    bg.setBounds(bounds);
                    bg.draw(canvas);//adds background to the canvas

                    canvas.drawRect(
                            0, //left side pixel
                            (canvas.getHeight()/2), //top side pixel
                            (canvas.getWidth()/2), //right side pixel
                            (float)((canvas.getHeight()/2) + ((canvas.getHeight()/2) * .21))//300//bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2) - (canvas.getWidth()/2)*.1),//550,
                            (float)((canvas.getHeight()/2) - (canvas.getHeight()/2)*.001),//300,
                            (canvas.getWidth()/2),
                            (canvas.getHeight())
                            , paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2) - (canvas.getWidth()/2)*.1), //550,
                            (float)((canvas.getHeight()) - canvas.getHeight()*.1), //canvas.getHeight()-50,
                            canvas.getWidth(),
                            canvas.getHeight()
                            , paint);
                    invalidate();
                    break;

                case 3: //level 3 obstacles
                    bg.setBounds(bounds);
                    bg.draw(canvas);//adds background to the canvas

                    canvas.drawRect(
                            0, //left side pixel
                            (canvas.getHeight()/2), //top side pixel
                            (canvas.getWidth()/2), //right side pixel
                            (float)((canvas.getHeight()/2) + ((canvas.getHeight()/2) * .21))//300//bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2) - (canvas.getWidth()/2)*.1),//550,
                            (float)((canvas.getHeight()/2) - (canvas.getHeight()/2)*.001),//300,
                            (canvas.getWidth()/2),
                            (canvas.getHeight())
                            , paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2) - (canvas.getWidth()/2)*.1), //550,
                            (float)((canvas.getHeight()) - canvas.getHeight()*.1), //canvas.getHeight()-50,
                            canvas.getWidth(),
                            canvas.getHeight()
                            , paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()) - (canvas.getWidth())*.05), //canvas.getWidth()-50,
                            0,
                            canvas.getWidth(),
                            canvas.getHeight()
                            , paint);
                    invalidate();
                    break;

                case 4: //level 4 obstacles
                    bg2.setBounds(bounds);
                    bg2.draw(canvas);

                    canvas.drawRect(
                            0, //left side pixel
                            canvas.getHeight()/2, //top side pixel
                            (float)(canvas.getWidth()*.045), //50, //right side pixel
                            canvas.getHeight() //bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            0, //left side pixel
                            (float)(canvas.getHeight() - (canvas.getHeight()*.1)),//canvas.getHeight() - 50, //top side pixel
                            canvas.getWidth()/2, //right side pixel
                            canvas.getHeight() //bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2) - (canvas.getWidth()/2)*.1), //(canvas.getWidth()/2) - 50, //left side pixel
                            0, //top side pixel
                            canvas.getWidth()/2, //right side pixel
                            canvas.getHeight() //bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            0, //left side pixel
                            0, //top side pixel
                            canvas.getWidth()/2, //right side pixel
                            (float)(canvas.getHeight()*.1)//50 //bottom side pixel
                            ,paint);

                    invalidate();
                    break;

                case 5:
                    bg2.setBounds(bounds);
                    bg2.draw(canvas);

                    canvas.drawRect(
                            0, //left side pixel
                            0, //top side pixel
                            (float)(canvas.getWidth()*.045), //50, //right side pixel
                            (float)((canvas.getHeight()/2) + (canvas.getHeight()/2)*.2) //(canvas.getHeight()/2)+50//bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            0, //left side pixel
                            0, //top side pixel
                            canvas.getWidth()/2, //right side pixel
                            (float)(canvas.getHeight()*.1) //50
                            ,paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2)-(canvas.getWidth()/2)*.1), //(canvas.getWidth()/2) - 50, //left side pixel
                            0, //top side pixel
                            canvas.getWidth()/2, //right side pixel
                            canvas.getHeight()/2
                            ,paint);

                    canvas.drawRect(
                            (float)((canvas.getWidth()/2)-(canvas.getWidth()/2)*.1), //(canvas.getWidth()/2) - 50, //left side pixel
                            (float)((canvas.getHeight()/2) - (canvas.getHeight()/2)*.2), //(canvas.getHeight()/2) - 50, //top side pixel
                            canvas.getWidth(), //right side pixel
                            canvas.getHeight()/2
                            ,paint);

                    invalidate();
                    break;

                case 6:

                    bg2.setBounds(bounds);
                    bg2.draw(canvas);

                    canvas.drawRect(
                            0, //left side pixel
                            (canvas.getHeight()/2), //top side pixel
                            canvas.getWidth(), //right side pixel
                            (float)((canvas.getHeight()/2) + (canvas.getHeight()/2)*.2) //300//bottom side pixel
                            ,paint);
                    canvas.drawRect(
                            (float)(canvas.getWidth()-(canvas.getWidth()*.05)), //canvas.getWidth() - 50, //left side pixel
                            (canvas.getHeight()/2), //top side pixel
                            canvas.getWidth(), //right side pixel
                            (float)(canvas.getHeight() - canvas.getHeight()*.07)//canvas.getHeight()-50//bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            (float)(canvas.getWidth()*.06), //100, //left side pixel
                            (float)(canvas.getHeight() - (canvas.getHeight()*.2)), //canvas.getHeight()-100, //top side pixel
                            canvas.getWidth(), //right side pixel
                            (float)(canvas.getHeight() - canvas.getHeight()*.07)//canvas.getHeight()-50//bottom side pixel
                            ,paint);

                    canvas.drawRect(
                            (float)(canvas.getWidth()*.06),//100, //left side pixel
                            0, //top side pixel
                            (float)(canvas.getWidth()*.11), //150, //right side pixel
                            (float)(canvas.getHeight() - canvas.getHeight()*.07)//canvas.getHeight()-50//bottom side pixel
                            ,paint);

                    invalidate();
                    break;

            } //end switch

    } //end onDraw

    public int getWidth(Canvas canvas)
    {
        cWidth = canvas.getWidth();
        return cWidth;
    }

    public int getHeight(Canvas canvas)
    {
        cHeight = canvas.getHeight();
        return cHeight;
    }
}
