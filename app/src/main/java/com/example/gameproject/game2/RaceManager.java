package com.example.gameproject.game2;

import java.util.ArrayList;

class RaceManager {
  private ArrayList<Horse> horses;
  private boolean notCrossed;
  private boolean isTie;
  private int numBetted;

  RaceManager() {
    this.horses = new ArrayList<>();
    this.horses.add(new Horse("Horse1", 229)); // create first horse.
    this.horses.add(new Horse("Horse2", 229)); // create second horse.
    this.horses.add(new Horse("Horse3", 229)); // create third horse.
    this.notCrossed = true;
    this.isTie = false;
    this.numBetted = 0;
  }

  /** Move x-coordinate of each Horse by random int. */
  void moveHorses() {
    for (Horse horse : horses) {
      horse.move();
    }
  }

  /**
   * Return an ArrayList of Horses that crossed the finish line.
   *
   * @return a list of Horses that crossed the finish line.
   */
  ArrayList<Horse> checkCrossLine() {
    ArrayList<Horse> output = new ArrayList<>();
    for (Horse horse : horses) {
      if (horse.isCrossLine()) output.add(horse);
    }
    return output;
  }

  /**
   * Collect Reward according to the rules for betting and scoring, and update the changes. Return
   * true if any of precondition: winHorse.size() > 0
   *
   * @param winHorse list of winners of this race.
   * @return reward from this race.
   */
  Integer collectReward(ArrayList<Horse> winHorse) {
    boolean selectedWin = false; // check if any of the selected horses won.
    // create a list of horses that are selected during the game.
    ArrayList<Horse> selectedHorses = new ArrayList<>();
    for (Horse horse : horses) {
      if (horse.getIsSelected()) {
        selectedHorses.add(horse);
      }
    }
    int highest_bet = 0;
    for (Horse horse : selectedHorses) {
      // check if there is more than one winner and user's selected horse is one of them.
      if (winHorse.size() > 1 && winHorse.contains(horse)) {
        selectedWin = true;
        highest_bet = Math.max(highest_bet, horse.getBetAmount()); // take higher bet.
        this.isTie = true;
      } else if (winHorse.contains(horse)) { // check if user's selected horse is the winner.
        return horse.getBetAmount() * 2;
      }
    }
    if (selectedWin) {
      if (selectedHorses.size() == 1) { // if the user bet on one horse.
        numBetted = 1;
      } else if (selectedHorses.size() == 2) { // if the user bet on two horses.
        numBetted = 2;
      }
      return highest_bet * 2;
    } else {
      return null;
    }
  }

  /**
   * Return a list of horses in this race.
   *
   * @return list of horses in this race.
   */
  ArrayList<Horse> getHorses() {
    return this.horses;
  }

  /**
   * Return the name of the winner horse
   *
   * @return name of winners
   */
  String getWinner() {
    StringBuilder winner = new StringBuilder("Winner:");
    for (Horse horse : checkCrossLine()) {
      winner.append(" ").append(horse.getName());
    }
    return winner.toString();
  }

  /** Return if there is a tie at the end of this race. */
  boolean getIsTie() {
    return this.isTie;
  }

  /** Return number of horses that are betted on. */
  int getNumBetted() {
    return this.numBetted;
  }

  /** Finish this race by the end of current round. */
  void finishRace() {
    notCrossed = checkCrossLine().isEmpty();
    while (notCrossed) {
      moveHorses();
      notCrossed = checkCrossLine().isEmpty();
    }
  }
}
