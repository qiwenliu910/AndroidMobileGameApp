package com.example.gameproject.shared;

import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

public class Updater extends BaseActivity {

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

    public void updateTimerText(int game, TextView timer) {

        // Update timer in each activity.

        int minutes = (int) (user.getTimer(game) / 1000) / 60;
        int seconds = (int) (user.getTimer(game) / 1000) % 60;
        String timeleftFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeleftFormat);
    }
}
