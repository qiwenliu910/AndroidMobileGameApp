package com.example.gameproject.game3.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.game3.view.Game3MazeView;
import com.example.gameproject.game3.view.Game3QuizFront;
import com.example.gameproject.mainactivity.HomeActivity;
import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.shared.Updater;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.sharedtimer.TimesUp;

import java.util.ArrayList;

public class MazeLevel1 extends Updater {

    private TextView money;
    private TextView hint;

    private Button yes;
    private Button no;

    private Game3MazeView gameMazeView;
    private ScoreManager scoreManager;

    private TextView timer;
    private TimeCounter timeCounter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mazequiz_mazeview);

        // Set up Shared Variable to store data.
        gameManager = getGameManager();
        userManager = gameManager.getUserManager0();
        scoreManager = gameManager.getCurrentScoreManager();
        user = userManager.getCurrentUser();
        filepath = getFilePath();
        timer = findViewById(R.id.TVtimer);

        // Set up Move Button.
        Button up = findViewById(R.id.button11);
        Button left = findViewById(R.id.button12);
        Button right = findViewById(R.id.button13);
        Button down = findViewById(R.id.button14);
        Button quit = findViewById(R.id.button3);

        // Set Yes No and Hint Paint.
        setButtonText();

        // Set Yes No and Hint Paint visible or not.
        visible(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);

        // Set up Move Button ColorScheme.
        ArrayList<Button> buttons = getButtons(up, down, right, left, quit);
        setColorScheme(buttons);

        // Set timer
        setUpTimer();


        gameMazeView = findViewById(R.id.gameMazeView2);
        //Link to activity_mazequiz_mazeview view

        if (scoreManager.getCurrentScore() < 5) {
            gameMazeView.createMaze(10, 10);
        } else if (scoreManager.getCurrentScore() < 7) {
            gameMazeView.createMaze(12, 12);
        } else {
            gameMazeView.createMaze(15, 15);
        }
        //IF the Player get high score in the previous game, we will increase the level of hardness.

        // Once click on these Button:
        up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveHelper("Up");
                    }
                });
        down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveHelper("Down");
                    }
                });
        left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveHelper("Left");
                    }
                });
        right.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveHelper("Right");
                    }
                });

        quit.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        timeCounter.pause();
//                        saveRecord();
                        updateObjectManager();
                        Intent intent = new Intent(MazeLevel1.this, HomeActivity.class);
                        intent.putExtra("gameNum", "3");
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCounter.pause();
//                saveRecord();
                user.setLevel(3, 2);
                updateObjectManager();
                Intent intent = new Intent(MazeLevel1.this, Game3QuizFront.class);
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
            }
        });

    }

    private void setUpTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(user.getTimer(3), 1000) {
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
                Intent intent = new Intent(MazeLevel1.this, TimesUp.class);
                intent.putExtra("gameNum", "3");
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        }.start();

        timeCounter = new TimeCounter(user, countDownTimer);
    }

    private void checkQuestion() {

        // Check whether player step on question active point.
        // If they do, give some hint.

        if (gameMazeView.checkQuestion().equals("q1")) {
            hint.setText(
                    "Wow, you found a Quiz box!\n Do you want to open it?");
            visible(View.VISIBLE, View.VISIBLE, View.VISIBLE);


        } else if (gameMazeView.checkQuestion().equals("q2")) {
            hint.setText(
                    "Wow, you found a huge Quiz box!\n Do you want to open it?");
            visible(View.VISIBLE, View.VISIBLE, View.VISIBLE);
        } else {
            visible(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        }

    }

    private void checkMoney() {

        // Check whether player step on money active point.
        // If they do, give hint and change money.

        String moneyFeedback = gameMazeView.checkMoney();
        switch (moneyFeedback) {
            case "+1":
                user.addMoney(1);
                hint.setText("Congratulate, you find a treasure box!\n money + 1.");
                hint.setVisibility(View.VISIBLE);
                money.setText(String.format("Money:%s", String.valueOf(user.getMoney())));
                break;
            case "+2":
                user.addMoney(2);
                hint.setText("Congratulate, you find a treasure box!\n money + 2.");
                hint.setVisibility(View.VISIBLE);
                money.setText(String.format("Money:%s", String.valueOf(user.getMoney())));
                break;
            case "-1":
                user.spendMoney(1);
                hint.setText("Oops, you are robbed!\n money - 1.");
                hint.setVisibility(View.VISIBLE);
                money.setText(String.format("Money:%s", String.valueOf(user.getMoney())));
                break;
            case "-2":
                user.spendMoney(2);
                hint.setText("Oops, you are robbed!\n money - 2.");
                hint.setVisibility(View.VISIBLE);
                money.setText(String.format("Money:%s", String.valueOf(user.getMoney())));
                break;
            default:
                break;
        }

    }

    /**
     * return all buttons in this screen
     *
     * @return all buttons that required to set background colour
     */
    private ArrayList<Button> getButtons(Button up, Button down, Button right, Button left, Button quit) {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(yes);
        buttons.add(no);
        buttons.add(up);
        buttons.add(down);
        buttons.add(right);
        buttons.add(left);
        buttons.add(quit);
        return buttons;
    }

    protected void moveHelper(String direction) {
        // Helper function about move player
        gameMazeView.move(direction);
        checkQuestion();
        checkMoney();
    }

    private void visible(int yesVisible, int noVisible, int hintVisible) {
        // Set up Button and Text Visibility helper.
        hint.setVisibility(hintVisible);
        no.setVisibility(noVisible);
        yes.setVisibility(yesVisible);

    }

    private void setButtonText() {
        // Set up Yes No Money and hint Paint helper.
        yes = findViewById(R.id.Maze_Yes);
        yes.setEnabled(true);

        no = findViewById(R.id.button2);
        no.setEnabled(true);

        TextView score = findViewById(R.id.score);
        money = findViewById(R.id.money);
        score.setText(String.format("Score:%s", String.valueOf(scoreManager.getCurrentScore())));
        money.setText(String.format("Money:%s", String.valueOf(user.getMoney())));

        hint = findViewById(R.id.textView6);
        hint.setTypeface(null, Typeface.BOLD);
        hint.setTextSize(18);
        hint.setTextColor(Color.BLACK);
    }

}
