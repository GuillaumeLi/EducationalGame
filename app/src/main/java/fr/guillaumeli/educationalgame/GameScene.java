package fr.guillaumeli.educationalgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class GameScene extends AppCompatActivity {

    private final static int FLY_SPEED = 1500;
    private final static float FLY_Y_TRANSLATION = 0.25f;

    private ImageButton homeBtn;
    private Button cryBtn, sadBtn, followBtn, happyBtn;
    private ImageView beeLead, beeTop, beeBottom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view_init();
        testBtn_init();
        button_config();
        bee_flying();
    }

    /**
     * Initialization of our objects with the corresponding ID in the activity
     */
    private void view_init() {
        setContentView(R.layout.activity_game_scene);
        homeBtn = findViewById(R.id.homeBtn);
        beeLead = findViewById(R.id.leadBee);
        beeTop = findViewById(R.id.topBee);
        beeBottom = findViewById(R.id.bottomBee);
    }

    /**
     * Configuration of our buttons
     * When we press the button we switch to the corresponding activity
     *
     * N.B.
     * The finish() method close this activity and go back to the previous one
     */
    private void button_config() {
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Animation for the bees
     */
    private void bee_flying() {
        beeLead.startAnimation(translate_animation_init(FLY_SPEED, FLY_Y_TRANSLATION));

        //Delayed animation of the top bee
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                beeTop.startAnimation(translate_animation_init(FLY_SPEED, FLY_Y_TRANSLATION));
            }}, 1500);

        //Delayed animation of the bottom bee
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                beeBottom.startAnimation(translate_animation_init(FLY_SPEED,FLY_Y_TRANSLATION));
            }}, 2700);
    }

    /**
     * Method to init the translation infinitely
     *
     * @param duration The speed of the translation
     * @param toYvalue The height of the translation
     */
    private TranslateAnimation translate_animation_init(int duration, float toYvalue){
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,toYvalue
        );
        animation.setStartTime(5000);
        animation.setDuration(duration);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());

        return animation;
    }

    /**
     * Method to test the switch of Imageview of the bee
     */
    private void testBtn_init() {
        sadBtn = findViewById(R.id.sadBtn);
        happyBtn = findViewById(R.id.happyBtn);
        followBtn = findViewById(R.id.followBtn);
        cryBtn = findViewById(R.id.cryBtn);

        sadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beeLead.setImageResource(R.drawable.bee_sad);
            }
        });

        happyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beeLead.setImageResource(R.drawable.bee_happy);
            }
        });

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beeLead.setImageResource(R.drawable.bee_follow);
            }
        });

        cryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beeLead.setImageResource(R.drawable.bee_cry);
            }
        });

    }
}
