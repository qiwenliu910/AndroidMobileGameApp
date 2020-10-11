package com.example.gameproject.game2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;

import java.util.ArrayList;

public class Game2Activity extends BaseActivity {

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp();
    }

    /**
     * Set up variables, onClickListeners and colorSchemes for this activity.
     */
    private void setUp() {
        setUpVariables();
        setOnClickListener();
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);
    }

    /**
     * Set up variables for this activity.
     */
    private void setUpVariables() {
        setContentView(R.layout.activity_horserace_instruction);
        gameManager = getGameManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();
        start = findViewById(R.id.Game2BTstart);
    }

    /**
     * Set up onClickListener for this activity.
     */
    private void setOnClickListener() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    /**
     * Start Game2.
     */
    private void start() {
        Intent intent;
        if (user.getMoney() <= 0) {
            intent = new Intent(Game2Activity.this, com.example.gameproject.game2.Game2Bonus.class);
        } else {
            intent = new Intent(Game2Activity.this, com.example.gameproject.game2.RaceLevel1.class);
        }
        intent.putExtra("gameManager", gameManager);
        intent.putExtra("filePath", filepath);
        startActivity(intent);
    }

    /**
     * return all horseButtons in this screen
     * @return all horseButtons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(start);
        return buttons;
    }

}
