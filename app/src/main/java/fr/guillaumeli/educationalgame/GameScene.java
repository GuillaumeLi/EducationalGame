package fr.guillaumeli.educationalgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameScene extends AppCompatActivity {

    private static final String TAG = GameScene.class.getSimpleName();
    private final static int FLY_SPEED = 1500;
    private final static float FLY_Y_TRANSLATION = 0.25f;

    private ImageButton homeBtn;
    private Button rightBtn, leftBtn;
    private ImageView beeLead, beeTop, beeBottom;
    private TextView word, answer;
    private ProgressBar progressBar;

    private DataBaseHelper dataBaseHelper = new DataBaseHelper(GameScene.this);

    private ArrayList<Integer> listOfWordAsked = new ArrayList<>();
    private int idNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);

        view_init();
        button_config();
        startAnimationFromBackgroundThread();
        chooseWord();
    }

    /**
     * Initialization of our objects with the corresponding ID in the activity
     */
    private void view_init() {
        homeBtn = findViewById(R.id.homeBtn);
        beeLead = findViewById(R.id.leadBee);
        beeTop = findViewById(R.id.topBee);
        beeBottom = findViewById(R.id.bottomBee);
        word = findViewById(R.id.word_txt);
        answer = findViewById(R.id.answer_txt);
        rightBtn = findViewById(R.id.rightBtn);
        leftBtn = findViewById(R.id.leftBtn);
        progressBar = findViewById(R.id.progressBar);
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

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBaseHelper.getData().get(idNumber).getRightLetter().matches(rightBtn.getText().toString().trim())) {
                    rightAnswer();
                } else {
                    falseAnswer();
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBaseHelper.getData().get(idNumber).getRightLetter().matches(leftBtn.getText().toString().trim())) {
                    rightAnswer();
                } else {
                    falseAnswer();
                }
            }
        });
    }

    /**
     * Animation for the bees
     */
    private void bee_flying() {
        beeLead.startAnimation(translate_animation_init(FLY_SPEED, FLY_Y_TRANSLATION));

        /*//Delayed animation of the top bee
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
            }}, 2700);*/
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

    private void rightAnswer() {
        /*beeLead.setImageResource(R.drawable.bee_follow);
        beeTop.setImageResource(R.drawable.bee_happy);
        beeTop.setImageResource(R.drawable.bee_happy);*/
        answer.setVisibility(View.VISIBLE);
        answer.setText("Good answer !");
        answer.setTextColor(Color.GREEN);
        if(progressBar.getProgress()!=100){
            progressBar.setProgress(progressBar.getProgress()+20);
        }
        /*answer.postDelayed(new Runnable() {
            @Override
            public void run() {
                answer.setVisibility(View.INVISIBLE);
            }
        }, 2000);*/

        chooseWord();
    }

    private void falseAnswer() {
        /*beeLead.setImageResource(R.drawable.bee_cry);
        beeTop.setImageResource(R.drawable.bee_sad);
        beeBottom.setImageResource(R.drawable.bee_sad);*/
        answer.setText("False answer...");
        answer.setTextColor(Color.RED);
        if(progressBar.getProgress()!=0){
            progressBar.setProgress(progressBar.getProgress()-20);
        }
        /*answer.setVisibility(View.VISIBLE);
        answer.postDelayed(new Runnable() {
            @Override
            public void run() {
                answer.setVisibility(View.INVISIBLE);
            }
        }, 3000);*/
        chooseWord();
    }

    private boolean chooseWord() {
        //If every words is used we ask again every words
        if(dataBaseHelper.getData().size()-1 == listOfWordAsked.size()) {
            listOfWordAsked.clear();
        }
        //Choose the word to ask
        Random random = new Random();
        //The bound is the following one : lower bound + random.nextInt(upper bound - lower bound)
        do {
            idNumber = 1 + random.nextInt(dataBaseHelper.getData().size() - 1);
        } while(listOfWordAsked.contains(idNumber));
        listOfWordAsked.add(idNumber);
        word.setText(dataBaseHelper.getData().get(idNumber).getWord());

        //Choose if the right answer is on the right or left button
        int rightLeftNumber = random.nextInt(30);
        if(rightLeftNumber <= 15) {
            rightBtn.setText(dataBaseHelper.getData().get(idNumber).getRightLetter());
            leftBtn.setText(dataBaseHelper.getData().get(idNumber).getFalseLetter());
            return true;
        } else {
            rightBtn.setText(dataBaseHelper.getData().get(idNumber).getFalseLetter());
            leftBtn.setText(dataBaseHelper.getData().get(idNumber).getRightLetter());
            return true;
        }
    }

    public void startAnimationFromBackgroundThread() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // this runs on a background thread
                Log.v(TAG, "Worker thread id:" + Thread.currentThread().getId());
                GameScene.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v(TAG, "Animation thread id:" + Thread.currentThread().getId());
                        bee_flying();
                    }
                });
            }
        });
    }

}
