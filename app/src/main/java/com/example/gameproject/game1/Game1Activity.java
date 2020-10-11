package com.example.gameproject.game1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gameproject.R;
import com.example.gameproject.shared.BaseActivity;
import com.example.gameproject.mainactivity.HomeActivity;

import java.util.ArrayList;

public class Game1Activity extends BaseActivity {

    private Button start;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blendoku_instruction);

        gameManager = getGameManager();
        user = gameManager.getUserManager0().getCurrentUser();
        filepath = getFilePath();

        start = findViewById(R.id.BTstart1);
        home = findViewById(R.id.BTNhome);
        ArrayList<Button> buttons = getButtons();
        setColorScheme(buttons);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start level1.
                Intent intent = new Intent(Game1Activity.this, BlendokuLevel1.class);
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Turn back to Game1 homepage.
                Intent intent = new Intent(Game1Activity.this, HomeActivity.class);
                intent.putExtra("gameNum", "1");
                intent.putExtra("gameManager", gameManager);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        });

    }

    /**
     * return all buttons in this screen
     *
     * @return all buttons that required to set background colour
     */
    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(start);
        buttons.add(home);
        return buttons;
    }
}
