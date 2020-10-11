package com.example.gameproject.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gameproject.datamanager.GameManager;
import com.example.gameproject.R;
import com.example.gameproject.datamanager.user.User;
import com.example.gameproject.datamanager.user.UserManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

  private EditText username, password1, password2;
  private Button register;
  private TextView nameErr;
  private TextView passwordErr;
  private Button back;
    private GameManager gameManager;
  private UserManager userManager;
  private String filePath;

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
    setContentView(R.layout.activity_register);
    username = findViewById(R.id.ETusername);
    password1 = findViewById(R.id.ETpassword1);
    password2 = findViewById(R.id.ETpassword2);
    register = findViewById(R.id.BTregister_);
    nameErr = findViewById(R.id.TVname_err);
    nameErr.setText("");
    passwordErr = findViewById(R.id.TVpassword_err);
    passwordErr.setText("");
    back = findViewById(R.id.BTbacktomain);
      gameManager = getGameManager();
      userManager = gameManager.getUserManager0();
    filePath = getFilePath();
  }

  private void setOnClickListener() {
    register.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            createUser(
                username.getText().toString(),
                password1.getText().toString(),
                password2.getText().toString());
          }
        });

    back.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
              intent.putExtra("gameManager", gameManager);
            startActivity(intent);
          }
        });
  }

    private GameManager getGameManager() {
    Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable("gameManager") != null) {
            return (GameManager) Objects.requireNonNull(extras.getSerializable("gameManager"));
    }
        return new GameManager();
  }

  private String getFilePath() {
    Bundle extras = getIntent().getExtras();
    if (extras != null && extras.getString("filePath") != null) {
      return Objects.requireNonNull(extras.getString("filePath"));
    }
    return null;
  }

  private boolean check_password(String password1, String password2) {
    return password1.equals(password2);
  }

  @SuppressLint("SetTextI18n")
  public void createUser(String name, String password1, String password2) {
    if (name.isEmpty() | password1 == null | password2 == null) { // user must fill in all fields
      register.setEnabled(false);
    } else if (!userManager.isValidUsername(name)) {
      nameErr.setText("This username already exists.");
    } else if (!check_password(password1, password2)) {
      passwordErr.setText("Passwords don't match.");
    } else {
      User user = new User(name, password1);
      userManager.addUser(user);
        writeToFile(gameManager);
      Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.putExtra("gameManager", gameManager);
      startActivity(intent);
    }
  }

    private void writeToFile(GameManager gameManager) {
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(filePath);
      ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(gameManager);
      os.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
