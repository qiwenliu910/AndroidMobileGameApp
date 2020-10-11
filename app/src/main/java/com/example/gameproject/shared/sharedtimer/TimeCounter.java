package com.example.gameproject.shared.sharedtimer;

import android.os.CountDownTimer;

import com.example.gameproject.datamanager.user.User;

public class TimeCounter {
  private CountDownTimer countDownTimer;
  private User user;

  public TimeCounter(User user, CountDownTimer countDownTimer) {
    this.countDownTimer = countDownTimer;
    this.user = user;
  }

  public void pause() {
    countDownTimer.cancel();
  }

  public void reset(int num) {
    user.resetTimer(num);
  }
}
