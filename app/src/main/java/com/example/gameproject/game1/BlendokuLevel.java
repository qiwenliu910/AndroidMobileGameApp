package com.example.gameproject.game1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.shared.Updater;
import com.example.gameproject.shared.sharedtimer.TimeCounter;

import java.util.ArrayList;

public class BlendokuLevel extends Updater {
  Button next, back, confirm;
  EditText answer1, answer2, answer3;
  TextView colorError, numError, attempt;
  TextView correct, addMoney, addScore;
  View answerColor1, answerColor2, answerColor3, color0;
  int numOfColor;
  Checker checker;
  Game1Updater updater;
  ColorSetter colorSetter;
  TimeCounter timeCounter;
  ArrayList<View> views = new ArrayList<>();
  TextView timer;
  RandomColor randomColor;
  int[] order;
  // Set up a flag to determine whether the player is qualified for a bonus level. To unlock a bonus
  // level the player needs to pass the first three level with a single try.
  static boolean bonus = true;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set layout variables.
    back = findViewById(R.id.BTback);
    next = findViewById(R.id.BTnext);
    confirm = findViewById(R.id.BTconfirm);
    answer1 = findViewById(R.id.ETanswer1);
    answer2 = findViewById(R.id.ETanswer2);
    answer3 = findViewById(R.id.ETanswer3);
    colorError = findViewById(R.id.TVcolorError);
    numError = findViewById(R.id.TVnumError);
    attempt = findViewById(R.id.TVattempt);
    TextView score = findViewById(R.id.TVscore);
    TextView money = findViewById(R.id.TVmoney);
    timer = findViewById(R.id.TVtimer);
    correct = findViewById(R.id.TVcorrect);
    addMoney = findViewById(R.id.TVaddMoney);
    addScore = findViewById(R.id.TVaddScore);
    color0 = findViewById(R.id.Vcolor0);
    answerColor1 = findViewById(R.id.Vanswer1);
    answerColor2 = findViewById(R.id.Vanswer2);
    answerColor3 = findViewById(R.id.Vanswer3);

    // Set up shared variable to store data.
    gameManager = getGameManager();
    userManager = gameManager.getUserManager0();
    user = userManager.getCurrentUser();
    filepath = getFilePath();
    updater = new Game1Updater(user, gameManager.getCurrentScoreManager());

    // Create sequence of random orders
    RandomOrder randomOrder = new RandomOrder(numOfColor);
    order = randomOrder.getOrder();
    checker = new Checker(numOfColor, order);
    colorSetter = new ColorSetter(views);
    randomColor.set(order, color0, views);
    Checker.setZero();

    // First set messages as invisible.
    correct.setVisibility(View.INVISIBLE);
    addScore.setVisibility(View.INVISIBLE);
    addMoney.setVisibility(View.INVISIBLE);
    next.setVisibility(View.INVISIBLE);

    // Then set error messages and set money and score after player's operations.
    colorError.setText("");
    numError.setText(" ");
    attempt.setText("You have attempted 0 times!");
    String textScore = "Score: " + gameManager.getCurrentScoreManager().getCurrentScore();
    score.setText(textScore);
    String textMoney = "Money: " + user.getMoney();
    money.setText(textMoney);

    ArrayList<Button> buttons = getButtons();
    setColorScheme(buttons);
  }

  /**
   * return all buttons in this screen
   *
   * @return all buttons that required to set background colour
   */
  protected ArrayList<Button> getButtons() {
    ArrayList<Button> buttons = new ArrayList<>();
    buttons.add(next);
    buttons.add(back);
    buttons.add(confirm);
    return buttons;
  }
}
