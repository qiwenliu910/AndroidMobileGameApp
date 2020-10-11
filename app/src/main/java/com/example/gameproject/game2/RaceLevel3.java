package com.example.gameproject.game2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.example.gameproject.R;
import com.example.gameproject.mainactivity.ScoreBoardActivity;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.sharedtimer.TimesUp;

import java.util.ArrayList;

public class RaceLevel3 extends HorseRace {
    private Button buttonAllIn;
    private Button confirm;
    private Boolean allIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horserace_level3);
        confirm = findViewById(R.id.buttonConfirm);
        confirm.setOnClickListener(this);
        buttonAllIn = findViewById(R.id.buttonAllIn);
        buttonAllIn.setOnClickListener(this);
        setUp();
        disableButtons();
        findViewById(R.id.H2).setVisibility(View.INVISIBLE);

        // if the user doesn't have money to bet when they enter the game,
        if (user.getMoney() == 0) {
            disableButtons();
            findViewById(R.id.textMessage).setVisibility(View.INVISIBLE);
            confirm.setVisibility(View.INVISIBLE);
            buttonAllIn.setEnabled(false);
            String text = "Sorry, You don't have enough money to play.";
            instruction.setText(text);
            home.setVisibility(View.VISIBLE); // move on to next level.
        }
    }
    @Override
    public void onClick(View view) {
        if(view == confirm){
            findViewById(R.id.textMessage).setVisibility(View.INVISIBLE);
            confirm.setVisibility(View.INVISIBLE);
            findViewById(R.id.H2).setVisibility(View.VISIBLE);
            buttonAllIn.setEnabled(false);
            enableButtons();
        }
        //when horse button is selected
        if (view == buttonHorse1 || view == buttonHorse2 || view == buttonHorse3) {
            disableButtons(); // disable horse horseButtons
            bet.setEnabled(true);
            buttonAllIn.setEnabled(true);
            betAmount.setEnabled(true);
            horseSelected = ((Button) view).getText().toString(); // keep track of selected horse.
            setSelected((Button) view);
            String text = "Enter bet Amount.";
            instruction.setText(text);
        }
        if (view == bet) {
            bet();
            buttonAllIn.setVisibility(View.INVISIBLE);
        }
        if (view == buttonAllIn){
            allIn = true;
            betAll();
        }
        if (view == run) {
            run(((Button) view).getText() == "See Result");
        }
        if (view == home) {
            moveToHome();
        }
    }

    /**
     * set up timer.
     */
    @Override
    void setUpTimer() {
        CountDownTimer countDownTimer =
                new CountDownTimer(user.getTimer(2), 1000) {
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
                        Intent intent = new Intent(RaceLevel3.this, TimesUp.class);
                        intent.putExtra("gameNum", "2");
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                }.start();

        timeCounter = new TimeCounter(user, countDownTimer);
    }

    /**
     * Move to ScoreBoardActivity.
     */
    @Override
    void moveToHome() {
        leaveScreen();
        Intent intent = new Intent(RaceLevel3.this, ScoreBoardActivity.class);
        intent.putExtra("gameNum", "2");
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
        user.setLevel(2, 1);
        user.setHasHistory(2, false);
        scoreManager.addToScore();
        updateObjectManager();
    }

    ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = super.getButtons();
        buttons.add(buttonAllIn);
        buttons.add(confirm);
        return buttons;
    }

    /**
     * Rules for collecting rewards when user chooses to allIn
     *
     * @param winHorse list of horses that win this race
     */
    private void collectAllInReward(ArrayList<Horse> winHorse){
        Integer reward = raceManager.collectReward(winHorse);
        if (reward == null) {
            String text = "Sorry. Your horse didn't win.";
            instruction.setText(text);
        } else {
            user.addMoney(reward * 3);
            String money = "money: " + reward;
            this.money.setText(money);
            String text = "Congrats! You've earned $" + reward;
            instruction.setText(text);
            scoreManager.addCurrentScore(3);
            String score = "score: " + scoreManager.getCurrentScore();
            this.score.setText(score);

        }
    }

    /**
     * bet on one of the horse
     */
    private void betAll() {
        String text;
        try {
            int bet = user.getMoney(); //bet amount
            text = "You've bet all your money: $" + ((Integer)user.getMoney()).toString();
            instruction.setText(text);
            updateBetting(bet);

        } catch (RuntimeException e) {
            instruction.setText("");
            return;
        }
        bet.setEnabled(false);
        buttonAllIn.setEnabled(false);
        betAmount.setText("");
        betAmount.setEnabled(false);
        text = "See Result";
        run.setText(text);
        run.setEnabled(true);
    }

    /**
     * horses move once
     *
     * @param seeResult indicates whether to show the result or not.
     */
    @Override
    void run(boolean seeResult) {
        if(allIn){
            showResult();
            collectAllInReward(raceManager.checkCrossLine());
            run.setEnabled(false);
            home.setVisibility(View.VISIBLE);
        }
        else {
            if (seeResult) {
                showResult();
                collectReward(raceManager.checkCrossLine());
                run.setEnabled(false);
                home.setVisibility(View.VISIBLE);
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
                }
            }
        }
    }

}
