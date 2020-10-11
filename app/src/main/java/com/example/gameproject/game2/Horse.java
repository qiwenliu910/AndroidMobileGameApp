package com.example.gameproject.game2;

class Horse {
    private String name;
    private float x;
    private int betAmount;
    private boolean crossLine;
    private boolean isSelected;

    Horse(String name, float x) {
        this.name = name;
        this.x = x;
        this.betAmount = 0;
        this.crossLine = false;
    }

    /**
     * Return the name of this horse
     * @return horse name.
     */
    String getName() {
        return name;
    }

    /**
     * Return the x-coordinate of this horse
     * @return x-coordinate
     */
    float getX() {
        return x;
    }

    /**
     * Return bet amount on this horse.
     * @return bet amount
     */
    int getBetAmount() {
        return betAmount;
    }

    /**
     * Return if this horse has crossed the finish lien.
     * @return true if crossed line, false otherwise.
     */
    boolean isCrossLine() {
        return crossLine;
    }

    /**
     * Return if this horse is selected.
     * @return true if selected, false otherwise.
     */
    boolean getIsSelected() {
        return isSelected;
    }

    /**
     * mark this horse to be a selected one.
     */
    void setIsSelected() {
        this.isSelected = true;
    }

    /**
     * move this horse.
     */
    void move() {
        if (this.x <= 770) {
            double distance = Math.random() * 200;
            if (this.x + distance > 770) {
                this.x = 770;
                this.crossLine = true;
            } else {
                this.x += distance;
            }
        }
    }

    void addBet(int amount) {
        this.betAmount += amount;
    }

}
