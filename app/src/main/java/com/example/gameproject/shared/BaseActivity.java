package com.example.gameproject.shared;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gameproject.datamanager.GameManager;
import com.example.gameproject.datamanager.user.User;
import com.example.gameproject.datamanager.user.UserManager;

import java.util.ArrayList;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    protected GameManager gameManager;
  protected UserManager userManager;
  protected String filepath;
  protected User user;
  protected int level;

    public GameManager getGameManager() {
    Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable("gameManager") != null)
            return (GameManager) Objects.requireNonNull(extras.getSerializable("gameManager"));
    return null;
  }

  public String getFilePath() {
    Bundle extras = getIntent().getExtras();
    if (extras != null && extras.getString("filePath") != null)
      return Objects.requireNonNull(extras.getString("filePath"));
    return null;
  }

    protected void setColorScheme(ArrayList<Button> buttons) {
        for (Button button : buttons) {

          button.setBackgroundColor(user.getColorScheme());
        }
    }
}
