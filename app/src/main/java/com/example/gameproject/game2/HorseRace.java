package com.example.gameproject.game2;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.Updater;

import java.util.ArrayList;
import java.util.Objects;

public abstract class HorseRace extends Updater implements View.OnClickListener {
    Button home;
    Button buttonHorse1, buttonHorse2, buttonHorse3;
    Button run, bet;
    TextView instruction;
    TextView money, score, timer;
    TextView horse1Bet, horse2Bet, horse3Bet;
    ImageView horse1, horse2, horse3;
    TextView winner;
    EditText betAmount;
    Button[] horseButtons = new Button[3];
    RaceManager raceManager;
    ArrayList<Horse> horses;
    String horseSelected;
    TimeCounter timeCounter;
    ScoreManager scoreManager;


    /**
     * horses move once
     *
     * @param seeResult indicates whether to show the result or not.
     */
    abstract void run(boolean seeResult);

    /**
     * set up Timer
     */
    abstract void setUpTimer();

    /**
     * Move to HomeActivity.
     */
    abstract void moveToHome();

    /**
     * Leave this screen.
     */
    abstract void leaveScreen();

    void setUp() {
        setUpVariable();
        setOnClickListener();
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);
        setHorseImage();
        setUpTimer();
    }

    ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(home);
        buttons.add(buttonHorse1);
        buttons.add(buttonHorse2);
        buttons.add(buttonHorse3);
        buttons.add(run);
        buttons.add(bet);
        return buttons;
    }

    /**
     * Link layouts with instance variables
     */
    void setUpVariable() {
        home = findViewById(R.id.BTNHome);
        gameManager = getGameManager();
        userManager = gameManager.getUserManager0();
        scoreManager = gameManager.getCurrentScoreManager();
        user = userManager.getCurrentUser();
        filepath = getFilePath();
        home.setVisibility(View.INVISIBLE); // hide the button
        buttonHorse1 = findViewById(R.id.buttonHorse1);
        horseButtons[0] = buttonHorse1;
        buttonHorse2 = findViewById(R.id.buttonHorse2);
        horseButtons[1] = buttonHorse2;
        buttonHorse3 = findViewById(R.id.buttonHorse3);
        horseButtons[2] = buttonHorse3;
        run = findViewById(R.id.buttonRun);
        bet = findViewById(R.id.buttonBet);
        instruction = findViewById(R.id.textInstruction);
        String text = "Choose a horse to bet!";
        instruction.setText(text);
        money = findViewById(R.id.textMoney);
        String display = "money: " + user.getMoney();
        money.setText(display);
        score = findViewById(R.id.textScore);
        String score = "score: " + scoreManager.getCurrentScore();
        this.score.setText(score);
        timer = findViewById(R.id.TVtimer);
        horse1Bet = findViewById(R.id.textHorse1Bet);
        horse2Bet = findViewById(R.id.textHorse2Bet);
        horse3Bet = findViewById(R.id.textHorse3Bet);
        horse1 = findViewById(R.id.H1);
        horse2 = findViewById(R.id.H2);
        horse3 = findViewById(R.id.H3);
        winner = findViewById(R.id.textWinner);
        winner.setVisibility(View.INVISIBLE);
        betAmount = findViewById(R.id.textBetAmount);
        raceManager = new RaceManager();
        horses = Objects.requireNonNull(raceManager).getHorses();
    }

    /**
     * Set onClickListener for horseButtons
     */
    void setOnClickListener() {
        buttonHorse1.setOnClickListener(this);
        buttonHorse2.setOnClickListener(this);
        buttonHorse3.setOnClickListener(this);
        bet.setOnClickListener(this);
        run.setOnClickListener(this);
        home.setOnClickListener(this);
    }

    /**
     * Enable every Horse horseButtons.
     */
    void enableButtons() {
        for (int i = 0; i < 3; i++) {
            horseButtons[i].setEnabled(true);
        }
    }

    /**
     * Disable every Horse horseButtons.
     */
    void disableButtons() {
        for (int i = 0; i < 3; i++) {
            horseButtons[i].setEnabled(false);
        }
    }

    /**
     * bet on one of the horse
     */
    void bet() {
        String betstr = betAmount.getText().toString();
        try {
            // if the user bets 0 or less
            if (Integer.parseInt(betstr) <= 0) {
                String text = "Invalid input. You should bet some money!";
                instruction.setText(text);
                return;
            }
            int bet = Integer.parseInt(betstr); // bet amount
            if (bet <= user.getMoney()) { // if the user has enough money to bet
                instruction.setText("");
                updateBetting(bet);
            }
            //user has some money, but the amount of money user has is less than the bet amount
            else if (bet > user.getMoney() && user.getMoney() > 0) {
                String text = "You don't have enough money!";
                instruction.setText(text);
                return;
            } else {// user has no money
                String text = "You don't have enough money!";
                instruction.setText(text);
                text = "See Result";
                run.setText(text);
            }

        } catch (RuntimeException e) {
            String text = "Invalid input. You should bet some money!";
            instruction.setText(text);
            return;
        }
        bet.setEnabled(false);
        betAmount.setText("");
        betAmount.setEnabled(false);
        run.setEnabled(true);
    }

    /**
     * Show result of the race.
     */
    void showResult() {
        raceManager.finishRace();
        moveHorses();
        showWinner();
    }

    /**
     * Show winner of the race.
     */
    void showWinner() {
        String winner = raceManager.getWinner();
        this.winner.setText(winner);
        this.winner.setVisibility(View.VISIBLE);
    }

    /**
     * Collect Reward according to the rules for betting and scoring, and update the changes.
     * precondition: winHorse.size() > 0
     *
     * @param winHorse list of horses that win this race
     */
    void collectReward(ArrayList<Horse> winHorse) {
        Integer reward = raceManager.collectReward(winHorse);
        boolean isTie = raceManager.getIsTie();
        if (reward == null) {
            String text = "Sorry. Your horse didn't win.";
            instruction.setText(text);
        } else {
            if (isTie) {
                user.addMoney(reward);
                String money = ((Integer)user.getMoney()).toString();
                this.money.setText(money);
                String text = "Tie! You've earned $" + reward;
                instruction.setText(text);
            } else {
                user.addMoney(reward);
                String money = ((Integer)user.getMoney()).toString();
                this.money.setText(money);
                String text = "Congrats! You've earned $" + reward;
                instruction.setText(text);
            }

            if (raceManager.getNumBetted() == 1) { // if the user has bet on only one Horse
                scoreManager.addCurrentScore(2);
            } else if (raceManager.getNumBetted() == 2) { // if the user has bet on two Horses
                scoreManager.addCurrentScore(1);
            }
            String score = "score: " + scoreManager.getCurrentScore();
            this.score.setText(score);
        }
    }

    /**
     * Mark the Horse corresponding to the parameter view, if it is selected.
     *
     * @param view input button
     */
    void setSelected(Button view) {
        for (Horse horse : horses) {
            if (view.getText() == horse.getName()) {
                horse.setIsSelected();
            }
        }
    }

    /**
     * Move x-coordinate of each Horse by random int.
     */
    void moveHorses() {
        raceManager.moveHorses();
        for (Horse horse : horses) {
            switch (horse.getName()) {
                case "Horse1":
                    horse1.setX(horse.getX());
                    break;
                case "Horse2":
                    horse2.setX(horse.getX());
                    break;
                case "Horse3":
                    horse3.setX(horse.getX());
                    break;
            }
        }
    }

    /**
     * Use player's money to bet and update the bet amount on each horse.
     *
     * @param bet input bet amount
     */
    void updateBetting(int bet) {
        user.useMoney(bet);
        String money = "money: " + user.getMoney();
        this.money.setText(money);
        for (Horse horse : horses) {
            if (horse.getName().equals(horseSelected)) {
                horse.setIsSelected();
                horse.addBet(bet);
                String text = horse.getBetAmount() + "";
                switch (horse.getName()) {
                    case "Horse1":
                        horse1Bet.setText(text);
                        break;
                    case "Horse2":
                        horse2Bet.setText(text);
                        break;
                    case "Horse3":
                        horse3Bet.setText(text);
                        break;
                }
            }
        }
    }

    /**
     * Set horses' image
     */
    void setHorseImage() {
        if (user.getHorseImage() == 0) {
            horse1.setImageResource(R.drawable.h0_1);
            horse2.setImageResource(R.drawable.h0_2);
            horse3.setImageResource(R.drawable.h0_3);
        } else if (user.getHorseImage() == 1) {
            horse1.setImageResource(R.drawable.horse1);
            horse2.setImageResource(R.drawable.horse1);
            horse3.setImageResource(R.drawable.horse1);
        }
    }
}

