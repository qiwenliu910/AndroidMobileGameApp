package com.example.gameproject.datamanager;

import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.datamanager.user.User;
import com.example.gameproject.datamanager.user.UserManager;

import java.io.Serializable;

public class GameManager implements Serializable {

    // GameManager is used to passing user's data through activities.

  private UserManager userManager;
  private ScoreManager[] scoreManagers;
  private ScoreManager currentScoreManager;

    public GameManager() {

      // Set this User's userData and scoreData.
      // ScoreData is separated between three game.

    this.userManager = new UserManager();
    this.scoreManagers = new ScoreManager[3];
    scoreManagers[0] = new ScoreManager();
    scoreManagers[1] = new ScoreManager();
    scoreManagers[2] = new ScoreManager();
  }

  public UserManager getUserManager0() {
    return userManager;
  }

  public ScoreManager getCurrentScoreManager() {
    return currentScoreManager;
  }

  public void setCurrentScoreManager(int num, User user) {
    this.currentScoreManager = scoreManagers[num - 1];
    currentScoreManager.setCurrentPairByUser(user);
  }

  public void setCurrentUser(User user) {
    userManager.setCurrentUser(user);
  }
}
