package com.example.gameproject.mainactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.gameproject.game1.BlendokuLevel2;
import com.example.gameproject.game1.BlendokuLevel3;
import com.example.gameproject.game1.Game1Activity;
import com.example.gameproject.game2.Game2Activity;
import com.example.gameproject.game2.RaceLevel2;
import com.example.gameproject.game2.RaceLevel3;
import com.example.gameproject.game3.presenter.QuizLevelBonus;
import com.example.gameproject.game3.view.Game3MazeFront;
import com.example.gameproject.shared.BaseActivity;
import com.example.gameproject.R;
import com.example.gameproject.game3.view.Game3QuizFront;
import com.example.gameproject.game3.presenter.QuizLevel2;
import com.example.gameproject.game3.presenter.QuizLevel3;

import java.util.ArrayList;
import java.util.Objects;


public class HomeActivity extends BaseActivity {

    private Button newgame;
    private Button resume;
    private Button logout;
    private Button seeRecord;
    private TextView historyErr;
    private int gameNum;

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

    private void setUpVariables() {
        setContentView(R.layout.activity_home);
        gameManager = getGameManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();
        gameNum = getGameNum();
        TextView gameName = findViewById(R.id.TVwelcome);
        String text = "Welcome to ";
        if (gameNum == 1)
            text += "Blendoku";
        else if (gameNum == 2)
            text += "Horse Race";
        else if (gameNum == 3)
            text += "Maze";
        gameName.setText(text);
        newgame = findViewById(R.id.BTNnewgame);
        resume = findViewById(R.id.BTNresume);
        logout = findViewById(R.id.BTNlogout);
        seeRecord = findViewById(R.id.BTNrecord);
        historyErr = findViewById(R.id.TVerrorinfo);
        historyErr.setText("");
        //updateUserManager(user);
    }

    private void setOnClickListener() {
        newgame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                newGame();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resume();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                jumpToScreen(LevelAcitivity.class);
            }
        });

        seeRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ScoreBoardActivity.class);
                intent.putExtra("gameNum", "" + getGameNum());
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        });
    }

    private void newGame() {
        user.cleanGameData(gameNum);
        System.out.println("current score manager: " + gameManager.getCurrentScoreManager());
        gameManager.getCurrentScoreManager().cleanGameData();
        //updateUserManager(user);
        jumpToGame(gameNum);
    }

    @SuppressLint("SetTextI18n")
    private void resume() {
        if (!user.hasHistory(gameNum)) {
            historyErr.setText("No history found. Please start a new game.");
        } else {
            int level = user.getLevel(gameNum);
            System.out.println("!!!!!!!!" + gameNum + " level: " + level);
            if (gameNum == 1) {
                if (level == 1)
                    jumpToScreen(Game1Activity.class);
                else if (level == 2)
                    jumpToScreen(BlendokuLevel2.class);
                else if (level == 3)
                    jumpToScreen(BlendokuLevel3.class);
            } else if (gameNum == 2) {
                if (level == 1)
                    jumpToScreen(Game2Activity.class);
                else if (level == 2)
                    jumpToScreen(RaceLevel2.class);
                else if (level == 3)
                    jumpToScreen(RaceLevel3.class);
            } else if (gameNum == 3) {
                if (level == 1)
                    jumpToScreen(Game3MazeFront.class);
                else if (level == 2)
                    jumpToScreen(Game3QuizFront.class);
                else if (level == 3)
                    jumpToScreen(QuizLevel2.class);
                else if (level == 4)
                    jumpToScreen(QuizLevel3.class);
                else if (level == 5)
                    jumpToScreen(QuizLevelBonus.class);
            }
        }
    }

    private void jumpToScreen(Class nextScreen) {
        Intent intent = new Intent(HomeActivity.this, nextScreen);
        intent.putExtra("gameManager", gameManager);
        intent.putExtra("filePath", this.filepath);
        startActivity(intent);
    }

    private int getGameNum() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("gameNum") != null)
            return Integer.parseInt(Objects.requireNonNull(extras.getString("gameNum")));
        return 0;
    }

    private void jumpToGame(int gameNum) {
        if (gameNum == 1) {
            jumpToScreen(Game1Activity.class);
        } else if (gameNum == 2) {
            jumpToScreen(Game2Activity.class);
        } else if (gameNum == 3) {
            jumpToScreen(Game3MazeFront.class);
        }
    }

    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(seeRecord);
        return buttons;
    }
}
