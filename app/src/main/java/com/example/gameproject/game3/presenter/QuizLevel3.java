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
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * A multiple choice questions quiz.
 */
public class QuizLevel3 extends Updater {

    /**
     * Button for choice A.
     */
    private Button a;

    /**
     * Button for choice B.
     */
    private Button b;

    /**
     * Button for choice C.
     */
    private Button c;

    /**
     * Button for choice D.
     */
    private Button d;

    /**
     * TextView for choice A.
     */
    private TextView answerA;

    /**
     * TextView for choice B.
     */
    private TextView answerB;

    /**
     * TextView for choice C.
     */
    private TextView answerC;

    /**
     * TextView for choice D.
     */
    private TextView answerD;

    /**
     * Button for the next question.
     */
    private Button next;

    /**
     * Button for hint.
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
     * Timer for this screen.
     */
    private TextView timer;

    /**
     * TimeCounter for this screen.
     */
    private TimeCounter timeCounter;

    /**
     * TextView for the current question.
     */
    private TextView question;

    /**
     * ArrayList that stores all questions.
     */
    private ArrayList<String> totalQuestionList;

    /**
     * String that represents the correct answer.
     */
    private String correctAnswer;

    /**
     * String that represents the current question.
     */
    private String currentQuestionString;

    /**
     * scoreManager for the whole game.
     */
    private ScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mazequiz_quiz_level2);

        // Create 4 buttons for ABCD choices, and ABCD answer textView
        a = findViewById(R.id.choiceA);
        b = findViewById(R.id.choiceB);
        c = findViewById(R.id.choiceC);
        d = findViewById(R.id.choiceD);

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);

        // Managers, user and filepath for the whole game.
        gameManager = getGameManager();
        scoreManager = gameManager.getCurrentScoreManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();

        // next, getHint, and quit buttons for the quiz.
        next = findViewById(R.id.next);
        getHint = findViewById(R.id.hint);
        quit = findViewById(R.id.button88);

        //set ColourScheme for this screen
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);

        // score and money for the player, and the remaining time.
        score = findViewById(R.id.textView14);
        money = findViewById(R.id.textView13);
        score.setText(scoreString());
        money.setText(moneyString());
        timer = findViewById(R.id.TVtimer);
        setUpTimer();

        // question and total questions for the quiz.
        question = findViewById(R.id.question);
        totalQuestionList = new ArrayList<>();
        readFile(totalQuestionList);

        // disable all buttons except next button
        switchEnable(false, false, false, false, true, false);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextHelper();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // the user got the right answer, add one score and remove the question from the
                // total question arrayList
                checkCorrect(answerA, correctAnswer);
            }

        });
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // the user got the right answer, add one score and remove the question from the
                // total question arrayList
                checkCorrect(answerB, correctAnswer);
            }

        });
        c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // the user got the right answer, add one score and remove the question from the
                // total question arrayList
                checkCorrect(answerC, correctAnswer);
            }

        });
        d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // the user got the right answer, add one score and remove the question from the
                // total question arrayList
                checkCorrect(answerD, correctAnswer);
            }

        });
        getHint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // the user got the right answer, add one score and remove the question from the
                // total question arrayList
                if (user.getMoney() > 0) {
                    question.setText(correctAnswer);
                    user.useMoney(1);
                    money.setText(moneyString());
                } else {
                    String insufficientFund = "insufficient fund.";
                    question.setText(insufficientFund);
                }
                switchEnable(true, true, true, true, false, false);
            }

        });
        // go back to MazeActivity when the quit button is clicked.
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateObjectManager();
                Intent intent = new Intent(QuizLevel3.this, MazeLevel1.class);
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
                        Intent intent = new Intent(QuizLevel3.this, TimesUp.class);
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

            // Randomly selected a question from the totalQuestionList
            Random random = new Random();
            currentQuestionString = totalQuestionList.get(random.nextInt(totalQuestionList.size()));
            ArrayList<String> currentQuestion = new ArrayList<>(Arrays.asList(currentQuestionString.split("/")));

            // set the question, remember the correct answer, shuffle the answers and assign
            // them to the different choices.
            // the question is at index 0, the correct answer is at index 1
            question.setText(currentQuestion.get(0));
            correctAnswer = currentQuestion.get(1);
            currentQuestion.remove(0);
            Collections.shuffle(currentQuestion);

            answerA.setText(currentQuestion.get(0));
            answerB.setText(currentQuestion.get(1));
            answerC.setText(currentQuestion.get(2));
            answerD.setText(currentQuestion.get(3));

            // enable all buttons when the user click next button to go to the next
            // question.
            switchEnable(true, true, true, true, false, true);
        } else { // If the user has correctly answered all questions, setText to "The end."
            String endString = "The end.";
            question.setText(endString);
            // disable all three buttons and go to BonusLevel when the user has answered all questions.
            switchEnable(false, false, false, false, false, false);
            user.setLevel(3, 4);
            timeCounter.pause();
            updateObjectManager();
            Intent intent = new Intent(QuizLevel3.this, QuizLevelBonus.class);
            intent.putExtra("gameManager", gameManager);
            intent.putExtra("filePath", filepath);
            startActivity(intent);
        }
    }

    /**
     * helper function that checks if the player got the right answer.
     *
     * @param text    the TextView that represents the player's answer to the current question.
     * @param correct the String that represents the correct answer to the current question.
     */
    private void checkCorrect(TextView text, String correct) {
        if (text.getText() == correct) {
            String correctString = "Correct!";
            question.setText(correctString);
            scoreManager.addCurrentScore(1);
            score.setText(scoreString());
            totalQuestionList.remove(currentQuestionString);
        } else {
            String incorrectString = "Incorrect, try again.";
            question.setText(incorrectString);
        }
        switchEnable(false, false, false, false, true, false);
        // only enable next button when the user has answered the question.

    }

    /**
     * helper function that reads the question file.
     *
     * @param addList    the ArrayList that stores the questions in the file.
     */
    private void readFile(ArrayList<String> addList) {
        InputStream is = this.getResources().openRawResource(R.raw.multiple_choice_question);
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
     * helper function that switches the enable for correct, incorrect, next and getHint buttons.
     *
     * @param choiceA    the choice A button
     * @param choiceB    the choice B button
     * @param choiceC    the choice C button
     * @param choiceD    the choice D button
     * @param nextButton the next button
     * @param hintButton the hint button
     */
    private void switchEnable(boolean choiceA, boolean choiceB, boolean choiceC, boolean choiceD,
                              boolean nextButton, boolean hintButton) {
        next.setEnabled(nextButton);
        getHint.setEnabled(hintButton);
        a.setEnabled(choiceA);
        b.setEnabled(choiceB);
        c.setEnabled(choiceC);
        d.setEnabled(choiceD);
    }

    /**
     * return all buttons in this screen
     *
     * @return all buttons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(a);
        buttons.add(b);
        buttons.add(c);
        buttons.add(d);
        buttons.add(next);
        buttons.add(getHint);
        buttons.add(quit);
        return buttons;
    }
}

