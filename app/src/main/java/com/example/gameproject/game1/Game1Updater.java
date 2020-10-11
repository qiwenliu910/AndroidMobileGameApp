package com.example.gameproject.game1;

import com.example.gameproject.datamanager.score.ScoreManager;
import com.example.gameproject.datamanager.user.User;

class Game1Updater {
  private User user;
  private ScoreManager scoreManager;
  private int addScore;
  private int addMoney;

  Game1Updater(User user, ScoreManager scoreManager) {
    this.user = user;
    this.scoreManager = scoreManager;
  }

  /**
   * Update score and money depending on the attempts made.
   * @param numOfAttempt total number of attempts
   */
  void update(int numOfAttempt) {
    if (numOfAttempt == 1) {
      addMoney = 1;
    }
    if (numOfAttempt < 5) {
      addScore = 6 - numOfAttempt;
    } else {
      addScore = 1;
    }
    user.addMoney(addMoney);
    scoreManager.addCurrentScore(addScore);
  }

  int getAddScore() {
    return addScore;
  }

  int getAddMoney() {
    return addMoney;
  }
}
