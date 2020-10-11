package com.example.gameproject.game3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gameproject.R;
import com.example.gameproject.game3.presenter.MazeLevel1;
import com.example.gameproject.shared.Updater;

import java.util.ArrayList;

public class Game3MazeFront extends Updater {

    // Build the front page of game activity_mazequiz_mazeview.

  Button start;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_mazequiz_instruction);
    // Connected to the MazeFront view
      gameManager = getGameManager();
      userManager = gameManager.getUserManager0();
    user = userManager.getCurrentUser();
    filepath = getFilePath();

    start = findViewById(R.id.Maze_start);

    ArrayList<Button> buttons = getButtons();
    setColorScheme(buttons);
  }

  public void startGame(View v) {
    user.setLevel(3, 1);
    user.setHasHistory(3, true);
    // Once the player start this game, create game history

    Intent intent = new Intent(Game3MazeFront.this, MazeLevel1.class);
    // This homepage direct to the Game3MazeFront game.

      intent.putExtra("gameManager", gameManager);
    intent.putExtra("filePath", filepath);
    startActivity(intent);
      // put data like "user", "userManager"...together with this activity_mazequiz_mazeview game.
  }

  private ArrayList<Button> getButtons() {
    ArrayList<Button> buttons = new ArrayList<>();
    buttons.add(start);
    return buttons;
  }
}
