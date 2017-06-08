package com.example.m_alrajab.week10_demo3;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.m_alrajab.week10_demo3.model.LeaderBoardJson;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    StartDraggingLsntr myStartDraggingLsnr;
    EndDraggingLsntr myEndDraggingLsntr;
    Button rectBtn, ovalBtn, btn1, btn2;
    MyPanel panel;
    ImageView img;
    Animation animation;
    SharedPreferences sharedPref;
    DatabaseReference DbRef;
    RelativeLayout p;
    int width = 0;
    int height = 0;

    MediaPlayer waves, splash;
    Boolean check_sound = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.hockey); //character declaration

        myStartDraggingLsnr=new StartDraggingLsntr();
        myEndDraggingLsntr=new EndDraggingLsntr();

        rectBtn=(Button) findViewById(R.id.rectBtn);
        panel = new MyPanel(this);
        findViewById(R.id.upBtn).setOnLongClickListener(myStartDraggingLsnr);
        findViewById(R.id.downBtn).setOnLongClickListener(myStartDraggingLsnr);
        findViewById(R.id.leftBtn).setOnLongClickListener(myStartDraggingLsnr);
        findViewById(R.id.rightBtn).setOnLongClickListener(myStartDraggingLsnr);

        findViewById(R.id.Btn1).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.Btn2).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.Btn3).setOnDragListener(myEndDraggingLsntr);
        findViewById(R.id.Btn4).setOnDragListener(myEndDraggingLsntr);

        p = (RelativeLayout) findViewById(R.id.myPanelL);

        /*
        http://stackoverflow.com/questions/8257530/how-to-make-the-music-loop-after-the-music-ends-android
        http://stackoverflow.com/questions/7291731/how-to-play-audio-file-in-android
        https://www.freesound.org/people/Luftrum/sounds/48412
        http://bugmenot.com/view/freesound.org
        http://developer.android.com/guide/appendix/media-formats.html
        https://www.youtube.com/watch?v=V1ocJmXeQ28
        */

        waves = MediaPlayer.create(MainActivity.this, R.raw.oceanwavescrushing);
        waves.setLooping(true);
        waves.start();      //plays music



        ViewTreeObserver vto = p.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if(p.getMeasuredHeight()> 0){
                    p.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    width = p.getMeasuredWidth();
                    height = p.getMeasuredHeight();

                    RelativeLayout.LayoutParams mp = new RelativeLayout.LayoutParams((int)((width/2)*.09), (int)(height/2));
                    mp.setMargins(0, (int)((height/2) - (height/2)*.4), 0, 0);
                    img.setLayoutParams(mp);
                }
            }
        });

    }

    public void play(View view) { //when the play button is clicked
        sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
        final String username = sharedPref.getString("username", "");
        final String kidsname = Parent.player;
        final String Str_Stage1Beaten = sharedPref.getString(username + "/kid/"+ kidsname + "/StageOneBeaten", "");
        final String Str_GameBeaten = sharedPref.getString(username + "/kid/"+ kidsname + "/GameBeaten", "");
        DbRef = FirebaseDatabase.getInstance().getReference("leaders");

        //margin of image relative to screen size
        //marginParams.height = (int)((height/2) - (height/2)*.1); //height of the image relative to screen size
        //marginParams.width = (int)((width *.1)); //width of the image relative to screen size


        switch(SharedValuesXY.level){//check answers of each level
            case 1: //answers for level 1
                if(findViewById(R.id.Btn1).getContentDescription().equals("right")&&
                   findViewById(R.id.Btn2).getContentDescription().equals("down"))
                {//if the correct answer
                    AnimationSet set = new AnimationSet(true);

                    RotateAnimation animation1 = new RotateAnimation(0,360,
                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    animation1.setDuration(6000);
                    set.addAnimation(animation1);

                    Animation animation2 = new TranslateAnimation(0,(int)((width/2) - (width/2)*.1),0,0);
                    animation2.setDuration(3000);
                    animation2.setFillAfter(true);
                    set.addAnimation(animation2);

                    Animation animation3 = new TranslateAnimation(0,0,0,(int)((height/2)-(height/2)*.2));
                    animation3.setDuration(3000);
                    animation3.setFillAfter(true);
                    animation3.setStartOffset(3000);
                    set.addAnimation(animation3);
                    set.setFillAfter(true);

                    img.startAnimation(set);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() { //delay the alertbox until animation ends
                            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("You have successfully Beaten the Level!").setMessage("Go to next Level!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedValuesXY.level++; //go to next level
                                            img.clearAnimation();
                                        }
                                    });
                            clearBtns(); //remove all dragables added to the 4 buttons
                            alert.show();
                        }
                    },6000);
                }

                else{ //answer was not correct
                    splash = MediaPlayer.create(MainActivity.this, R.raw.splash);
                    splash.start(); //adds a splash sound effect when they mess up

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Your Path was not Correct").setMessage("Please Try Again!");
                    clearBtns(); //remove all dragables added to the 4 buttons
                    alert.show();
                }

                break;

            case 2://answers for level 2
                if(findViewById(R.id.Btn1).getContentDescription().equals("right")&&
                        findViewById(R.id.Btn2).getContentDescription().equals("down")&&
                        findViewById(R.id.Btn3).getContentDescription().equals("right"))
                {//if the correct answer

                    AnimationSet set = new AnimationSet(true);

                    RotateAnimation animation1 = new RotateAnimation(0,720,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    animation1.setDuration(9000);
                    set.addAnimation(animation1);

                    Animation animation2 = new TranslateAnimation(0,(int)((width/2) - (width/2)*.1),0,0);
                    animation2.setDuration(3000);
                    animation2.setFillAfter(true);
                    set.addAnimation(animation2);

                    Animation animation3 = new TranslateAnimation(0,0,0,(int)((height/2)-(height/2)*.2));
                    animation3.setDuration(3000);
                    animation3.setFillAfter(true);
                    animation3.setStartOffset(3000);
                    set.addAnimation(animation3);
                    set.setFillAfter(true);

                    Animation animation4 = new TranslateAnimation(0,((width/2)),0,0);
                    animation4.setDuration(3000);
                    animation4.setFillAfter(true);
                    animation4.setStartOffset(6000);
                    set.addAnimation(animation4);
                    set.setFillAfter(true);

                    img.startAnimation(set);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() { //delay the alertbox to next for the length of the animation
                            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("You have successfully Beaten the Level!").setMessage("Go to next Level!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedValuesXY.level++; //go to next level
                                            img.clearAnimation();
                                        }
                                    });
                            clearBtns(); //remove all dragables added to the 4 buttons
                            alert.show();
                        }
                    },9000);

                }

                else{ //answer was not correct
                    splash = MediaPlayer.create(MainActivity.this, R.raw.splash);
                    splash.start(); //adds a splash sound effect when they mess up

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Your Path was not Correct").setMessage("Please Try Again!");
                    clearBtns(); //remove all dragables added to the 4 buttons
                    alert.show();
                }
                break;

            case 3: //answers for level 3
                if(findViewById(R.id.Btn1).getContentDescription().equals("right")&&
                        findViewById(R.id.Btn2).getContentDescription().equals("down")&&
                        findViewById(R.id.Btn3).getContentDescription().equals("right")&&
                        findViewById(R.id.Btn4).getContentDescription().equals("up"))
                {//if the correct answer

                    AnimationSet set = new AnimationSet(true);

                    RotateAnimation animation1 = new RotateAnimation(0,1080,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    animation1.setDuration(12000);
                    set.addAnimation(animation1);

                    Animation animation2 = new TranslateAnimation(0,(int)((width/2) - (width/2)*.1),0,0);
                    animation2.setDuration(3000);
                    animation2.setFillAfter(true);
                    set.addAnimation(animation2);

                    Animation animation3 = new TranslateAnimation(0,0,0,(int)((height/2)-(height/2)*.2));
                    animation3.setDuration(3000);
                    animation3.setFillAfter(true);
                    animation3.setStartOffset(3000);
                    set.addAnimation(animation3);
                    set.setFillAfter(true);

                    Animation animation4 = new TranslateAnimation(0,((width/2)),0,0);
                    animation4.setDuration(3000);
                    animation4.setFillAfter(true);
                    animation4.setStartOffset(6000);
                    set.addAnimation(animation4);
                    set.setFillAfter(true);

                    Animation animation5 = new TranslateAnimation(0,0,0,(int)(-((height-height*.1))));
                    animation5.setDuration(3000);
                    animation5.setFillAfter(true);
                    animation5.setStartOffset(9000);
                    set.addAnimation(animation5);
                    set.setFillAfter(true);

                    img.startAnimation(set);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() { //delay the dialog until the animation ends

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                    alert.setTitle("Congratulations You beat Stage 1!").setMessage("Go to Stage 2")
                            .setNegativeButton("Stage 2", new DialogInterface.OnClickListener() {
                                @Override //redirects user to home screen
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedValuesXY.level++; //sets the level board back to 1

                                    int Stage1Beaten = Integer.parseInt(Str_Stage1Beaten);
                                    Stage1Beaten++;
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(username + "/kid/"+ kidsname + "/StageOneBeaten", "" + Stage1Beaten);
                                    editor.apply();

                                    clearBtns(); //remove all dragables added to the 4 buttons
                                    img.clearAnimation();
                                }
                            });
                    alert.show();
                        }
                    },12000);
                }

                else{ //answer was not correct
                    splash = MediaPlayer.create(MainActivity.this, R.raw.splash);
                    splash.start(); //adds a splash sound effect when they mess up

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Your Path was not Correct").setMessage("Please Try Again!");
                    alert.show();
                    clearBtns(); //remove all dragables added to the 4 buttons
                }

                break;

            case 4:
                if(findViewById(R.id.Btn1).getContentDescription().equals("down")&&
                        findViewById(R.id.Btn2).getContentDescription().equals("right")&&
                        findViewById(R.id.Btn3).getContentDescription().equals("up")&&
                        findViewById(R.id.Btn4).getContentDescription().equals("left"))
                {//if the correct answer

                    AnimationSet set = new AnimationSet(true);

                    RotateAnimation animation1 = new RotateAnimation(0,1080,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    animation1.setDuration(12000);
                    set.addAnimation(animation1);

                    Animation animation2 = new TranslateAnimation(0,0,0,(int)(height/2 - ((height/2)*.2)));
                    animation2.setDuration(3000);
                    animation2.setFillAfter(true);
                    set.addAnimation(animation2);

                    Animation animation3 = new TranslateAnimation(0,(int)((width/2) - (width/2)*.1),0,0);
                    animation3.setDuration(3000);
                    animation3.setFillAfter(true);
                    animation3.setStartOffset(3000);
                    set.addAnimation(animation3);
                    set.setFillAfter(true);

                    Animation animation4 = new TranslateAnimation(0,0,0,(int)(-(height - height*.1)));
                    animation4.setDuration(3000);
                    animation4.setFillAfter(true);
                    animation4.setStartOffset(6000);
                    set.addAnimation(animation4);
                    set.setFillAfter(true);

                    Animation animation5 = new TranslateAnimation(0,(int)(-((width/2)-(width/2)*.1)),0,0);
                    animation5.setDuration(3000);
                    animation5.setFillAfter(true);
                    animation5.setStartOffset(9000);
                    set.addAnimation(animation5);
                    set.setFillAfter(true);

                    img.startAnimation(set);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() { //delay the alertbox to next for the length of the animation
                            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("You have successfully Beaten the Level!").setMessage("Go to next Level!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedValuesXY.level++; //go to next level
                                            img.clearAnimation();
                                        }
                                    });
                            clearBtns(); //remove all dragables added to the 4 buttons
                            alert.show();
                        }
                    },12000);

                }

                else{ //answer was not correct
                    splash = MediaPlayer.create(MainActivity.this, R.raw.splash);
                    splash.start(); //adds a splash sound effect when they mess up

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Your Path was not Correct").setMessage("Please Try Again!");
                    clearBtns(); //remove all dragables added to the 4 buttons
                    alert.show();
                }

                break;

            case 5:
                if(findViewById(R.id.Btn1).getContentDescription().equals("up")&&
                        findViewById(R.id.Btn2).getContentDescription().equals("right")&&
                        findViewById(R.id.Btn3).getContentDescription().equals("down")&&
                        findViewById(R.id.Btn4).getContentDescription().equals("right"))
                {//if the correct answer

                    AnimationSet set = new AnimationSet(true);

                    RotateAnimation animation1 = new RotateAnimation(0,1080,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    animation1.setDuration(12000);
                    set.addAnimation(animation1);

                    Animation animation2 = new TranslateAnimation(0,0,0,-height/2);
                    animation2.setDuration(3000);
                    animation2.setFillAfter(true);
                    set.addAnimation(animation2);

                    Animation animation3 = new TranslateAnimation(0,(int)((width/2) - (width/2)*.1),0,0);
                    animation3.setDuration(3000);
                    animation3.setFillAfter(true);
                    animation3.setStartOffset(3000);
                    set.addAnimation(animation3);
                    set.setFillAfter(true);

                    Animation animation4 = new TranslateAnimation(0,0,0,(int)((height/2 - (height/2)*.2)));
                    animation4.setDuration(3000);
                    animation4.setFillAfter(true);
                    animation4.setStartOffset(6000);
                    set.addAnimation(animation4);
                    set.setFillAfter(true);

                    Animation animation5 = new TranslateAnimation(0,width/2,0,0);
                    animation5.setDuration(3000);
                    animation5.setFillAfter(true);
                    animation5.setStartOffset(9000);
                    set.addAnimation(animation5);
                    set.setFillAfter(true);

                    img.startAnimation(set);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() { //delay the alertbox to next for the length of the animation
                            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("You have successfully Beaten the Level!").setMessage("Go to next Level!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedValuesXY.level++; //go to next level
                                            img.clearAnimation();
                                        }
                                    });
                            clearBtns(); //remove all dragables added to the 4 buttons
                            alert.show();
                        }
                    },12000);

                }

                else{ //answer was not correct
                    splash = MediaPlayer.create(MainActivity.this, R.raw.splash);
                    splash.start(); //adds a splash sound effect when they mess up

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Your Path was not Correct").setMessage("Please Try Again!");
                    clearBtns(); //remove all dragables added to the 4 buttons
                    alert.show();
                }

                break;

            case 6:

                if(findViewById(R.id.Btn1).getContentDescription().equals("right")&&
                        findViewById(R.id.Btn2).getContentDescription().equals("down")&&
                        findViewById(R.id.Btn3).getContentDescription().equals("left")&&
                        findViewById(R.id.Btn4).getContentDescription().equals("up"))
                {//if the correct answer

                    AnimationSet set = new AnimationSet(true);

                    RotateAnimation animation1 = new RotateAnimation(0,1080,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    animation1.setDuration(12000);
                    set.addAnimation(animation1);

                    Animation animation2 = new TranslateAnimation(0,(int)(width - width*.05),0,0);
                    animation2.setDuration(3000);
                    animation2.setFillAfter(true);
                    set.addAnimation(animation2);

                    Animation animation3 = new TranslateAnimation(0,0,0,(int)(height*.33));
                    animation3.setDuration(3000);
                    animation3.setFillAfter(true);
                    animation3.setStartOffset(3000);
                    set.addAnimation(animation3);
                    set.setFillAfter(true);

                    Animation animation4 = new TranslateAnimation(0,(int)(-(width - width*.11)),0,0);
                    animation4.setDuration(3000);
                    animation4.setFillAfter(true);
                    animation4.setStartOffset(6000);
                    set.addAnimation(animation4);
                    set.setFillAfter(true);

                    Animation animation5 = new TranslateAnimation(0,0,0,(int)(-(height-height*.1)));
                    animation5.setDuration(3000);
                    animation5.setFillAfter(true);
                    animation5.setStartOffset(9000);
                    set.addAnimation(animation5);
                    set.setFillAfter(true);

                    img.startAnimation(set);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() { //delay the dialog until the animation ends

                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                            alert.setTitle("Congratulations You win!").setMessage("Play again or Go Home")
                                    .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                                        @Override //redirects user to home screen
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedValuesXY.level =1; //sets the level board back to 1

                                            int GameBeaten = Integer.parseInt(Str_GameBeaten);
                                            GameBeaten++;
                                            final int gb = GameBeaten;
                                            DbRef.orderByChild("childName").equalTo(kidsname).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                        if (dataSnapshot.getValue() != null) { //player exists in db
                                                            for(DataSnapshot data : dataSnapshot.getChildren()) {
                                                                String key = data.getKey();
                                                                DbRef.child(key).child("timesGameBeaten").setValue(gb);
                                                            }
                                                        }
                                                        else { //player does not exist in db
                                                            //push the new players data to the database
                                                            DbRef.push().setValue(new LeaderBoardJson(kidsname, gb, username)); //adds to db
                                                        }
                                                }//end onDataChange
                                                @Override
                                                public void onCancelled(DatabaseError error) {}
                                            });

                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString(username + "/kid/"+ kidsname + "/GameBeaten", "" + GameBeaten);
                                            editor.apply();

                                            Intent intent = new Intent(MainActivity.this, Start_Screen.class);
                                            startActivity(intent);
                                            waves.setLooping(false);
                                            waves.stop();
                                            check_sound = false;
                                            clearBtns(); //remove all dragables added to the 4 buttons
                                            img.clearAnimation();
                                        }
                                    });
                            alert.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                                @Override //redirects user back to level 1
                                public void onClick(DialogInterface dialog, int which) {

                                    int GameBeaten = Integer.parseInt(Str_GameBeaten);
                                    GameBeaten++;

                                    final int gb = GameBeaten;
                                    DbRef.orderByChild("childName").equalTo(kidsname).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.getValue() != null) { //player exists in db
                                                for(DataSnapshot data : dataSnapshot.getChildren()) {
                                                    String key = data.getKey();
                                                    DbRef.child(key).child("timesGameBeaten").setValue(gb); //update their score
                                                }
                                            }
                                            else { //player does not exist in db
                                                //push the new players data to the database
                                                DbRef.push().setValue(new LeaderBoardJson(kidsname, gb, username)); //adds to db
                                            }
                                        }//end onDataChange
                                        @Override
                                        public void onCancelled(DatabaseError error) {}
                                    });

                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(username + "/kid/"+ kidsname + "/GameBeaten", "" + GameBeaten);
                                    editor.apply();

                                    SharedValuesXY.level = 1; //sets the level board back to 1
                                    img.clearAnimation();
                                    clearBtns(); //remove all dragables added to the 4 buttons
                                }
                            });
                            alert.show();
                        }
                    },12000);
                }

                else{ //answer was not correct
                    splash = MediaPlayer.create(MainActivity.this, R.raw.splash);
                    splash.start(); //adds a splash sound effect when they mess up

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Your Path was not Correct").setMessage("Please Try Again!");
                    alert.show();
                    clearBtns(); //remove all dragables added to the 4 buttons
                }

                break;
        } //end switch
    }//end play method

    public void check_sound(View view) { //toggles sound on and off
        if(check_sound){//checks if sound is on
            //if sound is on, pause the music
            waves.pause();
            check_sound = false;
            Toast.makeText(MainActivity.this, "Sound was paused!", Toast.LENGTH_SHORT).show();
        }

        else{//if sound is off turn it on
            waves.start();
            check_sound = true;
        }
    }

    public void clearBtns(){ //clears the info and background of the four buttons that you can drag too
        findViewById(R.id.Btn1).setContentDescription("N/A");
        findViewById(R.id.Btn1).setBackgroundResource(R.drawable.border);

        findViewById(R.id.Btn2).setContentDescription("N/A");
        findViewById(R.id.Btn2).setBackgroundResource(R.drawable.border);

        findViewById(R.id.Btn3).setContentDescription("N/A");
        findViewById(R.id.Btn3).setBackgroundResource(R.drawable.border);

        findViewById(R.id.Btn4).setContentDescription("N/A");
        findViewById(R.id.Btn4).setBackgroundResource(R.drawable.border);

    }

    public void exit(View view) { //creates a dialog to prompt the user if they want to exit the game

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Are you sure you want to exit?").setMessage("You will have to start back from level 1!")
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Start_Screen.class);
                        startActivity(intent);
                        waves.setLooping(false);
                        waves.stop();
                        check_sound = false;
                    }
                });
        alert.show();
        SharedValuesXY.level = 1; //resets the level back to 1 upon exit
    }

    private class EndDraggingLsntr implements View.OnDragListener{
        @Override
        public boolean onDrag(View view, DragEvent event) {
            if (event.getAction()==DragEvent.ACTION_DROP)
            {
                ((Button) view).setBackground( ((Button) event.getLocalState()).getBackground());
                ((Button) view).setContentDescription( ((Button) event.getLocalState()).getContentDescription());
            }

            return true;
        }
    }

    private class StartDraggingLsntr implements View.OnLongClickListener{
        @Override
        public boolean onLongClick(View view) {
            WithDraggingShadow shadow = new WithDraggingShadow(view);
            ClipData data=ClipData.newPlainText("","");
            view.startDrag( data, shadow, view, 0);
            return false;
        }
    }

    //Bitmap image;
    private class WithDraggingShadow extends View.DragShadowBuilder{
        //public WithDraggingShadow(View view, Bitmap draggingPicture){
        public WithDraggingShadow(View view){
            super(view);
            //image=draggingPicture;
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        }
    }
}
