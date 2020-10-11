package com.example.gameproject.game3.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.mainactivity.ScoreBoardActivity;
import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.sharedtimer.TimesUp;
import com.example.gameproject.shared.Updater;

import java.util.ArrayList;

/**
 * A bonus question for the quiz.
 */
@SuppressLint("Registered")
public class QuizLevelBonus extends Updater {

    /**
     * boxes for answering BonusLevel questions.
     */
    private EditText tf, mc;

    /**
     * Button for submitting the answer.
     */
    private Button submit;

    /**
     * TextView for the question.
     */
    private TextView answered;

    /**
     * scoreManager for the game.
     */
    private ScoreManager scoreManager;

    private TextView timer;
    private TimeCounter timeCounter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mazequiz_bonus_level);

        // Buttons View and Boxes for answering BonusLevel questions.
        submit = findViewById(R.id.submit);
        tf = findViewById(R.id.tf);
        mc = findViewById(R.id.mc);
        answered = findViewById(R.id.answer);

        // Managers, user and filepath for the whole game.
        gameManager = getGameManager();
        scoreManager = gameManager.getCurrentScoreManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();

        //Set Colour scheme for all buttons
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);

        // Score and money for the player, and the remaining time.
        TextView score = findViewById(R.id.TVScore);
        TextView money = findViewById(R.id.TVmoney);
        score.setText(scoreString());
        money.setText(moneyString());
        timer = findViewById(R.id.TVtimer);
        setUpTimer();

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // answers entered by the player.
                        int num1 = Integer.valueOf(tf.getText().toString());
                        int num2 = Integer.valueOf(mc.getText().toString());

                        // check if the player entered the correct answer.
                        if (num1 == 6 && num2 == 2) {
                            String right = "That's right! Score + 10";
                            answered.setText(right);
                            user.addMoney(10);
                        } else {
                            String wrong = "That's wrong!";
                            answered.setText(wrong);
                        }

                        // finished the game, go to ScoreBoardActivity.
                        scoreManager.addToScore();
                        timeCounter.pause();
                        updateObjectManager();
                        Intent intent = new Intent(QuizLevelBonus.this, ScoreBoardActivity.class);
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                });
    }

    /**
     * helper method to set timeCounter
     */
    private void setUpTimer() {
        CountDownTimer countDownTimer =
                new CountDownTimer(user.getTimer(3), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        user.setTimer(3, millisUntilFinished);
                        updateTimerText(3, timer);
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFinish() {
                        user.cleanGameData(3);
                        scoreManager.addToScore();
                        updateObjectManager();
                        Intent intent = new Intent(QuizLevelBonus.this, TimesUp.class);
                        intent.putExtra("gameNum", "3");
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                }.start();

        timeCounter = new TimeCounter(user, countDownTimer);
    }

    /**
     * Helper method that returns the String of the score.
     *
     * @return the String of the score.
     */
    private String scoreString() {
        return String.format("Score:%s", String.valueOf(scoreManager.getCurrentScore()));
    }

    /**
     * Helper method that returns the String of the money.
     *
     * @return the String of the money.
     */
    private String moneyString() {
        return String.format("Money:%s", String.valueOf(user.getMoney()));
    }

    /**
     * return all buttons in this screen
     * @return all buttons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(submit);
        return buttons;
    }
}
