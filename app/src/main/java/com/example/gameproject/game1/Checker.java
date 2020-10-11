package com.example.gameproject.game1;

class Checker {
    /**
     * The total number of colors.
     */
    private int numOfColor;
    /**
     * The actual order of the colors.
     */
    private int[] order;
    /**
     * The total number of attempts.
     */
    private static int numOfAttempt;

    /**
     * @param numOfColor total number of colors.
     * @param order      The actual order of the colors.
     */
    Checker(int numOfColor, int[] order) {
    this.numOfColor = numOfColor;
    this.order = order;
  }

    /**
     * Check if the answer is valid.
     *
     * @param answer the array of int the player answered.
     * @return true iff the answer is valid.
     */
    private boolean checkNum(int[] answer) {
        for (int i = 0; i < numOfColor; i++) {
      if (answer[i] > numOfColor | answer[i] < 1) {
        return false;
      } else {
        for (int j = i + 1; j < numOfColor; j++) {
          if (answer[i] == answer[j]) {
            return false;
          }
        }
      }
    }
    return true;
    }

    /**
     * check if the answer is in correct order.
     *
     * @param answer the array of int the player answered.
     * @return true iff every answer is correct, false if at least one of the answer is incorrect.
     */
    private boolean checkColor(int[] answer) {
    for (int i = 0; i < numOfColor; i++) {
      if (answer[i] != order[i]) {
        return false;
      }
    }
    return true;
    }

    /**
     * Classify the answers(invalid/incorrect/correct)
     *
     * @param answer the array of int the player answered.
     * @return 1 iff the answers are invalid, 2 iff the answers are vaild but incorrect, 0 if the
     * answer is correct.
     */
    int check(int[] answer) {
        if (!checkNum(answer)) {
            return 1;
        } else if (!checkColor(answer)) {
            numOfAttempt += 1;
            return 2;
        } else {
            numOfAttempt += 1;
            return 0;
    }
  }
  static int getNumOfAttempt() {
      return numOfAttempt;
    }

  static void setZero() {
    numOfAttempt = 0;
  }
}
