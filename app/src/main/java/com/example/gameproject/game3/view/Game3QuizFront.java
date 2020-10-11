package com.example.gameproject.game3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gameproject.R;
import com.example.gameproject.game3.presenter.QuizLevel2;
import com.example.gameproject.shared.Updater;

import java.util.ArrayList;

public class Game3QuizFront extends Updater {

  private Button start;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mazequiz_instruction_quiz);

    start = findViewById(R.id.buttonstart);
      gameManager = getGameManager();
      user = gameManager.getUserManager0().getCurrentUser();
    filepath = getFilePath();

    ArrayList<Button> buttons = getButtons();
    setColorScheme(buttons);

    start.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                user.setLevel(3, 2);
                user.setHasHistory(3, true);
                Intent intent = new Intent(Game3QuizFront.this, QuizLevel2.class);
                  intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
              }
            });
  }

  private ArrayList<Button> getButtons() {
    ArrayList<Button> buttons = new ArrayList<>();
    buttons.add(start);
    return buttons;
  }
}
