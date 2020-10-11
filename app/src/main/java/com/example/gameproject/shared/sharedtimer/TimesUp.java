package com.example.gameproject.shared.sharedtimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.gameproject.mainactivity.ScoreBoardActivity;
import com.example.gameproject.mainactivity.HomeActivity;
import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;

import java.util.Objects;

public class TimesUp extends BaseActivity {
    private Button home;
    private Button seeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("here!");
        setUp();
    }

    private void setUp() {
        setUpVariables();
        setColorScheme();
        setOnClickListener();
    }

    private void setUpVariables() {
        setContentView(R.layout.activity_timesup);
        home = findViewById(R.id.BTNhome);
        seeResult = findViewById(R.id.BTNseeResult);
        gameManager = getGameManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();
    }

    private void setOnClickListener() {
        home.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToScreen(HomeActivity.class);
                    }
                });

        seeResult.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToScreen(ScoreBoardActivity.class);
                    }
                });
    }

    private void jumpToScreen(Class nextScreen) {
        Intent intent = new Intent(TimesUp.this, nextScreen);
        intent.putExtra("gameNum", "" + getGameNum());
        intent.putExtra("gameManager", gameManager);
        intent.putExtra("filePath", filepath);
        startActivity(intent);
    }

    private int getGameNum() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("gameNum") != null)
            return Integer.parseInt(Objects.requireNonNull(extras.getString("gameNum")));
        return 0;
    }

    protected void setColorScheme() {
        home.setBackgroundColor(this.user.getColorScheme());
        seeResult.setBackgroundColor(this.user.getColorScheme());
    }
}

