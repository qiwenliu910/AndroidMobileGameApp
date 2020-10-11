package com.example.gameproject.game1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;

import com.example.gameproject.R;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.sharedtimer.TimesUp;

import java.util.ArrayList;

public class BlendokuLevel2 extends BlendokuLevel {

    private EditText answer4;
    private View answerColor4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_blendoku_level2);
        numOfColor = 4;
        answer4 = findViewById(R.id.ETanswer4);
        View color1 = findViewById(R.id.Vcolor1);
        View color2 = findViewById(R.id.Vcolor2);
        View color3 = findViewById(R.id.Vcolor3);
        View color4 = findViewById(R.id.Vcolor4);
        answerColor4 = findViewById(R.id.Vanswer4);
        views.add(color1);
        views.add(color2);
        views.add(color3);
        views.add(color4);
        randomColor = new RandomColor(numOfColor);
        super.onCreate(savedInstanceState);
        randomColor.set(order, color0, views);

        CountDownTimer countDownTimer =
                new CountDownTimer(user.getTimer(1), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        user.setTimer(1, millisUntilFinished);
                        updateTimerText(1, timer);
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFinish() {
                        user.cleanGameData(1);
                        updateObjectManager();
                        Intent intent = new Intent(BlendokuLevel2.this, TimesUp.class);
                        intent.putExtra("gameNum", 1);
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                }.start();
        timeCounter = new TimeCounter(user, countDownTimer);

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeCounter.reset(1);
                        updateObjectManager();
                        //Turn back to Game1 homepage.
                        Intent intent = new Intent(BlendokuLevel2.this, Game1Activity.class);
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                });

        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeCounter.pause();
                        user.setLevel(1, 3);
                        user.setHasHistory(1, true);
                        updateObjectManager();
                        //Unlock level3.
                        Intent intent = new Intent(BlendokuLevel2.this, BlendokuLevel3.class);
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                });

        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        int[] answer = new int[numOfColor];
                        answer[0] = Integer.valueOf(answer1.getText().toString());
                        answer[1] = Integer.valueOf(answer2.getText().toString());
                        answer[2] = Integer.valueOf(answer3.getText().toString());
                        answer[3] = Integer.valueOf(answer4.getText().toString());
                        ArrayList<View> answerColor = new ArrayList<>();
                        answerColor.add(answerColor1);
                        answerColor.add(answerColor2);
                        answerColor.add(answerColor3);
                        answerColor.add(answerColor4);
                        //Fill in the answer blocks corresponding to the player's answer.
                        colorSetter.setAll(answerColor, answer);
                        //Check the answer.
                        int result = checker.check(answer);
                        //update numOfAttempt.
                        int numOfAttempt = Checker.getNumOfAttempt();
                        updater.update(numOfAttempt);
                        //Give feedback.
                        if (result == 1) { // Invalid input.
                            numError.setText("Your input is invalid.");
                        } else if (result == 2) { // Incorrect answers.
                            attempt.setText("You have attempted " + numOfAttempt + " times!");
                            colorError.setText("Please try again!");
                        } else { // Correct answers.
                            updater.update(numOfAttempt);
                            addMoney.setText("money+" + updater.getAddMoney());
                            addScore.setText("Score" + updater.getAddScore());
                            correct.setVisibility(View.VISIBLE);
                            addMoney.setVisibility(View.VISIBLE);
                            addScore.setVisibility(View.VISIBLE);
                            next.setVisibility(View.VISIBLE);
                            // Player is not qualified to unlock the bonus level if they give more
                            // than one try.
                            if (numOfAttempt > 1) {
                                bonus = false;
                            }
                        }
                    }
                });
    }
}
