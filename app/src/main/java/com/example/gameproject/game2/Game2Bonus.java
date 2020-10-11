package com.example.gameproject.game2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;

import java.util.ArrayList;

public class Game2Bonus extends BaseActivity {

    private Button animal1, animal2, animal3;
    private Button playGame;
    private TextView showMessage;
    private int selectedAnimal;
    private int moneyEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUp();
    }

    /**
     * Set up for this game.
     */
    private void setUp() {
        setUpVariables();
        setOnClickListener();
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);
    }

    /**
     * Set up variables for bonus game.
     */
    private void setUpVariables() {
        setContentView(R.layout.activity_horserace_bonus_level);
        gameManager = getGameManager();
        user = gameManager.getUserManager0().getCurrentUser();
        animal1 = findViewById(R.id.BTNanimal1);
        animal2 = findViewById(R.id.BTNanimal2);
        animal3 = findViewById(R.id.BTNanimal3);
        showMessage = findViewById(R.id.TVmoneyearned);
        playGame = findViewById(R.id.BTNplaygame2);
        playGame.setVisibility(View.INVISIBLE);
    }

    /**
     * Set up OnClickListener
     */
    private void setOnClickListener() {
        animal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAnimal = 1;
                moneyEarned = setResult();
                showResult(moneyEarned);
            }
        });

        animal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAnimal = 2;
                moneyEarned = setResult();
                showResult(moneyEarned);
            }
        });

        animal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAnimal = 3;
                moneyEarned = setResult();
                showResult(moneyEarned);
            }
        });

        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game2Bonus.this, RaceLevel1.class);
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", getFilePath());
                startActivity(intent);
            }
        });
    }

    /**
     * Set up result for this bonus game.
     * @return reward the user will receive
     */
    private int setResult() {
        double random = Math.random();
        int add;
        if (random <= 0.33) {
            add = selectedAnimal * 2;
        } else if (random > 0.33 && random <= 0.66) {
            add = (4 - selectedAnimal) * 2;
        } else {
            add = 6;
        }
        user.addMoney(add);
        return add;
    }

    /**
     * Show the result of this bonus game.
     * @param moneyEarned amount of reward that the user earned
     */
    private void showResult(int moneyEarned) {
        String text = "You have earned " + moneyEarned;
        showMessage.setText(text);
        playGame.setVisibility(View.VISIBLE);
        animal1.setEnabled(false);
        animal2.setEnabled(false);
        animal3.setEnabled(false);
    }

    /**
     * return all horseButtons in this screen
     * @return all horseButtons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(playGame);
        return buttons;
    }
}
