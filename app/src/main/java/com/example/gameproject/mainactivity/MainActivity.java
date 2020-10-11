package com.example.gameproject.mainactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gameproject.datamanager.GameManager;
import com.example.gameproject.R;
import com.example.gameproject.datamanager.user.User;
import com.example.gameproject.datamanager.user.UserManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  private EditText Name;
  private EditText Password;
  private TextView Info;
  private Button Login;
  private Button Register;
    GameManager gameManager;
  protected UserManager userManager;
  protected String filePath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setUp();
  }

  private void setUp() {
    setUpVariables();
    setOnClickListener();
  }

  private void setUpVariables() {
    setContentView(R.layout.activity_main);
      filePath = getFilesDir().getPath() + "gameManager.txt";
      gameManager = getGameManager();
      userManager = gameManager.getUserManager0();
    Name = findViewById(R.id.ETusername);
    Password = findViewById(R.id.ETpassword);
    Login = findViewById(R.id.BTNlogin);
    Register = findViewById(R.id.BTNregister);
    Info = findViewById(R.id.TVerrorinfo);
    Info.setText("");
  }

  private void setOnClickListener() {
    Login.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            login(Name.getText().toString(), Password.getText().toString());
          }
        });

    Register.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            register();
          }
        });
  }

    private GameManager getGameManager() {
        // If intent contains "gameManager" extra, use getExtra
    Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable("gameManager") != null) {
            return (GameManager) Objects.requireNonNull(extras.getSerializable("gameManager"));
    }
    // else read from local storage
    return readUserManagerFromFile();
  }

  @SuppressLint("SetTextI18n")
  private void login(String username, String password) {
    String password_ = userManager.getPasswordByUsername(username);
    if (password_ == null) {
      Info.setText("The user is not registered.");
    } else if (!password_.equals(password)) {
      Info.setText("The username and password do not match.");
    } else {
      User user = userManager.getUserByUsername(username);
        gameManager.setCurrentUser(user);
        Intent intent = new Intent(MainActivity.this, LevelAcitivity.class);
        intent.putExtra("gameManager", this.gameManager);
      intent.putExtra("filePath", this.filePath);
      startActivity(intent);
    }
  }

  private void register() {
    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
      intent.putExtra("gameManager", this.gameManager);
    intent.putExtra("filePath", this.filePath);
    startActivity(intent);
  }

  /**
   * Deserialized GameManager from file
   *
   * @return GameManager
   */
  private GameManager readUserManagerFromFile() {
    File file = new File(filePath);
      GameManager gameManager = new GameManager();
    try {
      if (file.createNewFile()) { // create new file
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream os = new ObjectOutputStream(fos);
          os.writeObject(gameManager);
        os.close();
        fos.close();
      } else { // file already existed, read file
        FileInputStream fis = new FileInputStream(new File(filePath));
        ObjectInputStream oi = new ObjectInputStream(fis);
          gameManager = (GameManager) oi.readObject();
        oi.close();
        fis.close();
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }

      return gameManager;
  }
}
