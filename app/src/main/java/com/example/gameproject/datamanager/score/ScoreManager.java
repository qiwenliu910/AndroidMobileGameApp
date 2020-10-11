package com.example.gameproject.datamanager.score;

import com.example.gameproject.datamanager.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreManager implements Serializable {

  private UserScorePair currentPair;
  private ArrayList<UserScorePair> pairs;
  private ArrayList<UserScorePair> top3Pairs;

  /** Stores top three UserScorePair for each game */
  public ScoreManager() {
    this.pairs = new ArrayList<>();
    this.top3Pairs = new ArrayList<>();
  }

  private void addNewPair(UserScorePair userScorePair) {
    pairs.add(userScorePair);
  }

  public void setCurrentPairByUser(User user) {
    if (findPairByUser(user) != null) {
      currentPair = findPairByUser(user);
    } else {
      currentPair = new UserScorePair(user, 0);
      addNewPair(currentPair);
    }
  }

  public int getCurrentScore() {
    return currentPair.getScore();
  }

  public void addCurrentScore(int score) {
    currentPair.addScore(score);
  }

  private UserScorePair findPairByUser(User user) {
    for (UserScorePair pair : pairs) {
      if (pair.getUser() == user) return pair;
    }
    return null;
  }

  public UserScorePair getTopScore(int top) {
    if (top3Pairs.size() >= top) return top3Pairs.get(top - 1);
    return null;
  }

  /**
   * Precondition: given arr is sorted in non-increasing order add given score to given arr
   * Postcondition: given arr is sorted in non-increasing order
   */
  public void addToScore() {
    int index = -1;
    for (int i = 0; i < top3Pairs.size(); i++) {
      if (currentPair.compareTo(top3Pairs.get(i)) > 0) index = i;
    }
    if (index != -1) {
      top3Pairs.add(index, currentPair);
      if (top3Pairs.size() > 3) top3Pairs.remove(3);
    } else if (top3Pairs.size() <= 3) top3Pairs.add(currentPair);
    removeFromPairs();
  }

  private void removeFromPairs() {
    UserScorePair userScorePair = null;
    User user = null;
    for (UserScorePair pair : pairs) {
      if (pair.getUser() == currentPair.getUser()) {
        userScorePair = pair;
        user = pair.getUser();
      }
    }
    if (userScorePair != null && user != null) {
      pairs.remove(userScorePair);
      currentPair = new UserScorePair(user, 0);
    }
  }

  public void cleanGameData() {
    currentPair.cleanScore();
  }
}
