package com.example.gameproject.datamanager.score;

import com.example.gameproject.datamanager.user.User;

import java.io.Serializable;

/**
 * StoreActivity an User object and a int score
 */
public class UserScorePair implements Serializable, Comparable<UserScorePair> {

  private User user;
  private int score;

  UserScorePair(User user, int score) {
    this.user = user;
    this.score = score;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  void addScore(int score) {
    this.score += score;
  }

  /**
   * Compare two UserScorePair object by their score
   *
   * @param o another UserScorePair object
   * @return int
   */
  @Override
  public int compareTo(UserScorePair o) {
    return this.getScore() - o.getScore();
  }

  void cleanScore() {
    this.score = 0;
  }
}
