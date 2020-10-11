package com.example.gameproject.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.datamanager.score.UserScorePair;
import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoardActivity extends BaseActivity {
  private Button home;
  private ScoreManager scoreManager;

  @Override
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

    /**
     * Assign layout to variables
     */
    private void setUpVariables() {
        setContentView(R.layout.activity_scoreboard);
        gameManager = getGameManager();
        scoreManager = gameManager.getCurrentScoreManager();
        user = gameManager.getUserManager0().getCurrentUser();
        home = findViewById(R.id.BTNhome);
        TextView score1 = findViewById(R.id.TVscore1);
        TextView score2 = findViewById(R.id.TVscore2);
        TextView score3 = findViewById(R.id.TVscore3);
        score1.setText(getTopScore(1));
        score2.setText(getTopScore(2));
        score3.setText(getTopScore(3));
        filepath = getFilePath();
    }

    /**
     * set up onClickListener
     */
    private void setOnClickListener() {
        home.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ScoreBoardActivity.this, HomeActivity.class);
                        intent.putExtra("gameNum", "" + getGameNum());
                        intent.putExtra("gameManager", gameManager);
                        intent.putExtra("filePath", filepath);
                        startActivity(intent);
                    }
                });
    }

    /**
     * get top "top" score
     * @param top "top" score we need to get.
     * @return A string that will be shown on scoreBoard
     */
    private String getTopScore(int top) {
        UserScorePair scoreInfo = this.scoreManager.getTopScore(top);
        if (scoreInfo != null) {
            String userName = scoreInfo.getUser().getName();
            int score = scoreInfo.getScore();
            return userName + "  " + score;
        }
        return null;
    }

    /**
     * get game numbers from intent
     * @return a number represent one of three games
     */
    private int getGameNum() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("gameNum") != null)
            return Integer.parseInt(Objects.requireNonNull(extras.getString("gameNum")));
        return 0;
    }

    /**
     * return all buttons in this screen
     *
     * @return all buttons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(home);
        return buttons;
  }
}
