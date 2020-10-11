package com.example.gameproject.game2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.example.gameproject.R;
import com.example.gameproject.mainactivity.HomeActivity;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.sharedtimer.TimesUp;

import java.util.ArrayList;

public class RaceLevel2 extends HorseRace {

    private Button nextLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horserace_level2);

        nextLevel = findViewById(R.id.BTNnextLevel);
        nextLevel.setVisibility(View.INVISIBLE);
        setUp();
        // if the user doesn't have money to bet when they enter the game,
        if (user.getMoney() == 0) {
            disableButtons();
            String text = "Sorry, You don't have enough money to play.";
            instruction.setText(text);
            home.setVisibility(View.VISIBLE); // move on to next game.
        }
    }

    @Override
    public void onClick(View view) {
        //when horse button is selected
        if (view == buttonHorse1 || view == buttonHorse2 || view == buttonHorse3) {
            disableButtons(); // disable horse horseButtons
            bet.setEnabled(true);
            betAmount.setEnabled(true);
            horseSelected = ((Button) view).getText().toString(); // keep track of selected horse.
            setSelected((Button) view);
            String text = "Enter bet Amount";
            instruction.setText(text);
        }
        if (view == bet) {
            bet();
        }
        if (view == run) {
            run(((Button) view).getText() == "See Result");
        }
        if (view == home) {
            moveToHome();
        }
        if (view == nextLevel){
            moveToNextLevel();
        }
    }

    /**
     * horses move once
     *
     * @param seeResult indicates whether to show the result or not.
     */
    @Override
    void run(boolean seeResult) {
        if (seeResult) {
            showResult();
            collectReward(raceManager.checkCrossLine());
            run.setEnabled(false);
            home.setVisibility(View.VISIBLE);
            nextLevel.setVisibility(View.VISIBLE);
        } else {
            moveHorses();
            ArrayList<Horse> winningHorses = raceManager.checkCrossLine();
            if (winningHorses.isEmpty()) { //when there's no winner yet, game continues
                if (user.getMoney() == 0) {
                    String text = "You don't have enough money!";
                    instruction.setText(text);
                    text = "See Result";
                    run.setText(text);
                } else {
                    enableButtons();
                    run.setEnabled(false);
                }
            } else {// if there is at least one Horse that crossed the finish line
                showWinner();
                collectReward(winningHorses);
                disableButtons();
                run.setEnabled(false);
                home.setVisibility(View.VISIBLE);
                nextLevel.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Set up Timer.
     */
    @Override
    void setUpTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(user.getTimer(2), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                user.setTimer(2, millisUntilFinished);
                updateTimerText(2, timer);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                user.cleanGameData(2);
                scoreManager.addToScore();
                updateObjectManager();
                Intent intent = new Intent(RaceLevel2.this, TimesUp.class);
                intent.putExtra("gameNum", "2");
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        }.start();

        timeCounter = new TimeCounter(user, countDownTimer);
    }

    /**
     * move to HomeActivity
     */
    @Override
    void moveToHome() {
        leaveScreen();
        Intent intent = new Intent(RaceLevel2.this, HomeActivity.class);
        intent.putExtra("gameNum", "2");
        intent.putExtra("gameManager", gameManager);
        intent.putExtra("filePath", filepath);
        startActivity(intent);
    }

    /**
     * move to nextLevel
     */
    void moveToNextLevel() {
        leaveScreen();
        Intent intent = new Intent(RaceLevel2.this, RaceLevel3.class);
        intent.putExtra("gameManager", gameManager);
        intent.putExtra("filePath", filepath);
        startActivity(intent);
    }

    /**
     * leave this screen.
     */
    @Override
    void leaveScreen() {
        timeCounter.pause();
        user.setLevel(2, 3);
        user.setHasHistory(2, true);
        updateObjectManager();
    }

    ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = super.getButtons();
        buttons.add(nextLevel);
        return buttons;
    }
}
