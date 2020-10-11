package com.example.gameproject.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;

import java.util.ArrayList;

public class LevelAcitivity extends BaseActivity {

  private Button level1;
  private Button level2;
  private Button level3;

  private Button logout;
  private Button store;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setUp();
  }

  private void setUp() {
    setUpVariables();
    setOnClickListener();
      ArrayList<Button> buttons = getButtons();
      setColorScheme(buttons);
  }

  private void setUpVariables() {
      setContentView(R.layout.activity_level_home);
      gameManager = getGameManager();
      user = gameManager.getUserManager0().getCurrentUser();
    filepath = getFilePath();
    TextView welcomeMessage = findViewById(R.id.levelWelcome);
    String text = "Hi, " + user.getName() + "!";
    welcomeMessage.setText(text);
    level1 = findViewById(R.id.levelHome1);
    level2 = findViewById(R.id.levelHome2);
    level3 = findViewById(R.id.levelHome3);
    logout = findViewById(R.id.levelLogout);
    store = findViewById(R.id.levelStore);
  }

  private void setOnClickListener() {
    level1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            levelHelper("1");
          }
        });
    level2.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            levelHelper("2");
          }
        });
    level3.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            levelHelper("3");
          }
        });

    logout.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
              Intent intent = new Intent(LevelAcitivity.this, MainActivity.class);
            startActivity(intent);
          }
        });

    store.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
              Intent intent = new Intent(LevelAcitivity.this, StoreActivity.class);
              intent.putExtra("gameManager", gameManager);
            intent.putExtra("filePath", filepath);
            startActivity(intent);
          }
        });
  }

  private void levelHelper(String gameNum) {
    setScoreManager(gameNum);
      gameManager.getCurrentScoreManager().setCurrentPairByUser(user);
    Intent intent =
            new Intent(LevelAcitivity.this, com.example.gameproject.mainactivity.HomeActivity.class);
      intent.putExtra("gameManager", gameManager);
    intent.putExtra("filePath", filepath);
    intent.putExtra("gameNum", gameNum);
    startActivity(intent);
  }

  private void setScoreManager(String gameNum) {
    switch (gameNum) {
      case "1":
          gameManager.setCurrentScoreManager(1, user);
        break;
      case "2":
          gameManager.setCurrentScoreManager(2, user);
        break;
      case "3":
          gameManager.setCurrentScoreManager(3, user);
        break;
    }
  }

    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(logout);
        buttons.add(store);
        return buttons;
  }
}
