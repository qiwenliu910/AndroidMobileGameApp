package com.example.gameproject.mainactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class StoreActivity extends BaseActivity {
  private TextView money;
  private Button back;
  private Button buyRed;
  private Button buyBlue;
  private Button buyDefault;
  private Button buyHorse1;
  private Button buyDefaultHorse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setUp();
  }

  private void setUp() {
    setUpVariables();
    setOnClickListener();
    setButtonsName();
    setColorScheme();
  }

  private void setUpVariables() {
    setContentView(R.layout.activity_store);
      gameManager = getGameManager();
      userManager = gameManager.getUserManager0();
    user = userManager.getCurrentUser();
    filepath = getFilePath();
    money = findViewById(R.id.TVmoney);
    String text = "money:" + user.getMoney();
    money.setText(text);
    back = findViewById(R.id.BTNback);
    buyRed = findViewById(R.id.BTNbuy_red);
    buyBlue = findViewById(R.id.BTNbuy_blue);
    buyDefault = findViewById(R.id.BTNbuy_default);
    buyHorse1 = findViewById(R.id.BTNbuy_horse);
    buyDefaultHorse = findViewById(R.id.BTNbuy_default_horse);
  }

  private void setOnClickListener() {
    back.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            updateObjectManager();
            Intent intent = new Intent(StoreActivity.this, LevelAcitivity.class);
              intent.putExtra("gameManager", gameManager);
            intent.putExtra("filePath", filepath);
            startActivity(intent);
          }
        });

    buyDefault.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            buyDefault();
          }
        });

    buyRed.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            buyRed();
          }
        });

    buyBlue.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            buyBlue();
          }
        });

    buyDefaultHorse.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            buyDefaultHorse();
          }
        });

    buyHorse1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            buyHorse1();
          }
        });
  }

  private void buyDefault() {
    user.setColorScheme(Color.GRAY);
    setColorScheme();
    String text = "use";
    if (user.isBoughtRed()) buyRed.setText(text);
    if (user.isBoughtBlue()) buyBlue.setText(text);
  }

  private void buyRed() {
    if (user.isBoughtRed() || user.getMoney() >= 1) {
      if (!user.isBoughtRed()) {
        user.setBoughtRed(true);
        user.spendMoney(1);
        String text = "money:" + user.getMoney();
        money.setText(text);
      }
      user.setColorScheme(Color.RED);
      setColorScheme();
      String text = "current";
      buyRed.setText(text);
      text = "use";
      buyDefault.setText(text);
      if (user.isBoughtBlue()) // bought blue
      buyBlue.setText(text);
    }
  }

  private void buyBlue() {
    if (user.isBoughtBlue() || user.getMoney() >= 1) {
      if (!user.isBoughtRed()) {
        user.setBoughtBlue(true);
        user.spendMoney(1);
        String text = "money:" + user.getMoney();
        money.setText(text);
      }
      user.setColorScheme(Color.BLUE);
      setColorScheme();
      String text = "current";
      buyBlue.setText(text);
      text = "use";
      buyDefault.setText(text);
      if (user.isBoughtRed()) buyRed.setText(text);
    }
  }

  private void buyHorse1() {
    if (user.isBoughtHorse1() || user.getMoney() >= 1) {
      if (!user.isBoughtHorse1()) {
        user.setBoughtHorse1(true);
        user.spendMoney(1);
        String text = "money:" + user.getMoney();
        money.setText(text);
      }
      user.setHorseImage(1);
      String text = "current";
      buyHorse1.setText(text);
      text = "use";
      buyDefaultHorse.setText(text);
    }
  }

  private void buyDefaultHorse() {
    String text = "use";
    if (user.isBoughtHorse1()) buyHorse1.setText(text);
    user.setHorseImage(0);
    text = "current";
    buyDefaultHorse.setText(text);
  }

  public void updateObjectManager() {
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(filepath);
      ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(gameManager);
      os.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void setColorScheme() {
    back.setBackgroundColor(this.user.getColorScheme());
    buyDefault.setBackgroundColor(this.user.getColorScheme());
    buyRed.setBackgroundColor(this.user.getColorScheme());
    buyBlue.setBackgroundColor(this.user.getColorScheme());
    buyDefaultHorse.setBackgroundColor(this.user.getColorScheme());
    buyHorse1.setBackgroundColor(this.user.getColorScheme());
  }

  private void setButtonsName() {
    String text = "use";
    if (user.isBoughtRed()) buyRed.setText(text);
    if (user.isBoughtBlue()) buyBlue.setText(text);
    if (user.isBoughtHorse1()) buyHorse1.setText(text);
  }
}
