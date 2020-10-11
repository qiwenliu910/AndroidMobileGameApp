package com.example.gameproject.game3.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.shared.sharedtimer.TimeCounter;
import com.example.gameproject.shared.sharedtimer.TimesUp;
import com.example.gameproject.shared.Updater;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * A True False questions quiz.
 */
public class QuizLevel2 extends Updater {

    /**
     * Button for true.
     */
    private Button correct;

    /**
     * Button for false.
     */
    private Button incorrect;

    /**
     * Button for the next question.
     */
    private Button next;

    /**
     * Button to get hint.
     */
    private Button getHint;

    /**
     * Button to quit.
     */
    private Button quit;

    /**
     * TextView for score.
     */
    private TextView score;

    /**
     * TextView for money.
     */
    private TextView money;

    /**
     * TextView for the question.
     */
    private TextView question;

    /**
     * Timer for this screen.
     */
    private TextView timer;

    /**
     * TimeCounter for this screen.
     */
    private TimeCounter timeCounter;

    /**
     * ArrayList that stores all true questions.
     */
    private ArrayList<String> correctQuestionList;

    /**
     * ArrayList that stores all false questions.
     */
    private ArrayList<String> incorrectQuestionList;

    /**
     * ArrayList that stores all questions.
     */
    private ArrayList<String> totalQuestionList;

    /**
     * String that represents the current question.
     */
    private String currentQuestion;

    /**
     * ScoreManager of the whole game.
     */
    private ScoreManager scoreManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mazequiz_quiz_level1);

        // Create correct, incorrect, next, getHint, quit Buttons for playing the game.
        correct = findViewById(R.id.button8);
        incorrect = findViewById(R.id.button6);
        next = findViewById(R.id.button7);
        getHint = findViewById(R.id.button10);
        quit = findViewById(R.id.button4);


        // only enable the next button when the user just login
        switchEnable(false, false, true, false);

        // Managers, user and filepath for the whole game.
        gameManager = getGameManager();
        scoreManager = gameManager.getCurrentScoreManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();

        // Set ColourScheme for all buttons
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);

        // question, score and money for the player, and the remaining time.
        question = findViewById(R.id.Question);
        score = findViewById(R.id.score);
        money = findViewById(R.id.money);
        score.setText(scoreString());
        money.setText(moneyString());
        timer = findViewById(R.id.TVtimer);
        setUpTimer();

        // Create 3 arrayList for correct, incorrect and total questions.
        incorrectQuestionList = new ArrayList<>();
        correctQuestionList = new ArrayList<>();
        totalQuestionList = new ArrayList<>();

        // read the questions file and store all the questions in the corresponding arrayList.
        readFile(R.raw.correct_question_list, correctQuestionList);
        readFile(R.raw.incorrect_question_list, incorrectQuestionList);
        totalQuestionList.addAll(correctQuestionList);
        totalQuestionList.addAll(incorrectQuestionList);

        // When the INCORRECT button is clicked.
        // Check whether the answer to the current question is incorrect.
        incorrect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                checkCorrect(incorrectQuestionList);
            }
        });

        // When the CORRECT button is clicked.
        // Check whether the answer to the current question is correct.
        correct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                checkCorrect(correctQuestionList);
            }

        });

        // When the NEXT button is clicked.
        // Randomly selected a question from the total question arrayList.
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextHelper();
            }
        });

        // When the getHint button is clicked.
        // Give a hint of this question, and take some money from the user.
        // No hints will be given if the user doesn't have enough money.
        getHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion != null && user.getMoney() > 0) {
                    if (correctQuestionList.contains(currentQuestion)) {
                        String hintCorrect = currentQuestion + "True";
                        question.setText(hintCorrect);
                        switchEnable(true, false, false, false);
                    } else {
                        String hintIncorrect = currentQuestion + "False";
                        question.setText(hintIncorrect);
                        switchEnable(false, true, false, false);
                    }
                    user.useMoney(1);
                    money.setText(moneyString());
                } else {
                    String insufficientFund = "Insufficient Fund";
                    question.setText(insufficientFund);
                    switchEnable(false, false, true, false);
                }

            }
        });

        // go back to MazeActivity when quit is clicked.
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCounter.pause();
                Intent intent = new Intent(QuizLevel2.this, MazeLevel1.class);
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
                        Intent intent = new Intent(QuizLevel2.this, TimesUp.class);
                        intent.putExtra("gameNum", "3");
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                }.start();

        timeCounter = new TimeCounter(user, countDownTimer);
    }

    /**
     * helper function that would be called when the next button is clicked.
     */
    private void nextHelper() {
        if (totalQuestionList.size() != 0) {
            Random random = new Random();
            currentQuestion = totalQuestionList.get(random.nextInt(totalQuestionList.size()));
            question.setText(currentQuestion);
            // enable all buttons when the user click next button to go to the next
            // question.
            switchEnable(true, true, false, true);
        } else { // If the user has correctly answered all questions, setText to "The end."
            String end = "The end";
            question.setText(end);
            // disable all three buttons when the user has answered all questions and goes to next level.
            switchEnable(false, false, false, false);
            updateObjectManager();
            user.setLevel(3, 3);
            timeCounter.pause();
            Intent intent = new Intent(QuizLevel2.this, QuizLevel3.class);
            intent.putExtra("gameManager", gameManager);
            intent.putExtra("filePath", filepath);
            startActivity(intent);
        }
    }

    /**
     * helper function that switches the enable for correct, incorrect, next and getHint buttons.
     *
     * @param correctButton   the true button
     * @param incorrectButton the false button
     * @param nextButton      the next button
     * @param hintButton      the hint button
     */
    private void switchEnable(boolean correctButton, boolean incorrectButton, boolean nextButton,
                              boolean hintButton) {
        correct.setEnabled(correctButton);
        incorrect.setEnabled(incorrectButton);
        next.setEnabled(nextButton);
        getHint.setEnabled(hintButton);
    }

    /**
     * helper function that checks if the player has answered the question correctly.
     *
     * @param questionList check if the current question is the questionList.
     */
    private void checkCorrect(ArrayList<String> questionList) {
        // the user got the right answer, add one score and remove the question from the
        // total question arrayList.
        String correctString = "Correct!";
        String incorrectString = "Incorrect, try again.";

        if (questionList.contains(currentQuestion)) {
            question.setText(correctString);
            scoreManager.addCurrentScore(1);
            score.setText(scoreString());
            totalQuestionList.remove(currentQuestion);
        }
        // the user got the wrong answer.
        else {
            question.setText(incorrectString);
        }
        // only enable next button when the user has answered the question.
        switchEnable(false, false, true, false);
    }

    /**
     * helper function that reads the question file.
     *
     * @param fileToRead the id of the file that would be read.
     * @param addList    the ArrayList that stores the questions in the file.
     */
    private void readFile(int fileToRead, ArrayList<String> addList) {
        InputStream is = this.getResources().openRawResource(fileToRead);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String data = reader.readLine();
            while (data != null) {
                addList.add(data);
                data = reader.readLine();

            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
     *
     * @return all buttons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(correct);
        buttons.add(incorrect);
        buttons.add(next);
        buttons.add(getHint);
        buttons.add(quit);
        return buttons;
    }
}


