package com.example.gameproject.datamanager.user;

import android.graphics.Color;

import java.io.Serializable;

public class User implements Serializable {
    private String name, password;
    private int level1, level2, level3;
    private int money, colorScheme, horseImage;
    private long timer1, timer2, timer3;
    private boolean hasHistory1, hasHistory2, hasHistory3;
    private boolean boughtRed, boughtBlue, boughtHorse1;

    private static final long serialVersionUID = 2L;

    public User(String name, String password) {
        this.hasHistory1 = false;
        this.hasHistory2 = false;
        this.hasHistory3 = false;
        this.name = name;
        this.password = password;
        this.level1 = 1;
        this.level2 = 1;
        this.level3 = 1;
        this.money = 0;
        this.colorScheme = Color.GRAY;
        this.horseImage = 0;
        this.timer1 = 120000;
        this.timer2 = 120000;
        this.timer3 = 120000;
        this.boughtRed = false;
        this.boughtBlue = false;
        this.boughtHorse1 = false;
    }

    public String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }

    public int getLevel(int game) {
        if (game == 1)
            return level1;
        else if (game == 2)
            return level2;
        else if (game == 3)
            return level3;
        return 0;
    }

    public void setLevel(int game, int level) {
        if (game == 1)
            this.level1 = level;
        else if (game == 2)
            this.level2 = level;
        else if (game == 3)
            this.level3 = level;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int n) {
        money += n;
    }

    public void useMoney(int n) {
        money -= n;
    }

    void setName(String name) {
        this.name = name;
    }

    void setMoney(int money) {
        this.money = money;
    }

    public void spendMoney(int money) {
        this.money -= money;
    }

    public int getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(int colorScheme) {
        this.colorScheme = colorScheme;
    }

    public int getHorseImage() {
        return horseImage;
    }

    public void setHorseImage(int horseImage) {
        this.horseImage = horseImage;
    }

    public long getTimer(int game) {
        if (game == 1)
            return this.timer1;
        else if (game == 2)
            return this.timer2;
        else if (game == 3)
            return this.timer3;
        return 0;
    }

    public void resetTimer(int game) {
        if (game == 1)
            this.timer1 = 120000;
        else if (game == 2)
            this.timer1 = 120000;
        else if (game == 3)
            this.timer1 = 120000;
    }

    public void setTimer(int game, long timer) {
        if (game == 1)
            this.timer1 = timer;
        else if (game == 2)
            this.timer2 = timer;
        else if (game == 3)
            this.timer3 = timer;
    }

    public boolean hasHistory(int game) {
        if (game == 1)
            return hasHistory1;
        else if (game == 2)
            return hasHistory2;
        else if (game == 3)
            return hasHistory3;
        return false;
    }

    public void setHasHistory(int game, boolean hasHistory) {
        if (game == 1)
            this.hasHistory1 = hasHistory;
        else if (game == 2)
            this.hasHistory2 = hasHistory;
        else if (game == 3)
            this.hasHistory3 = hasHistory;
    }

    public boolean isBoughtRed() {
        return boughtRed;
    }

    public void setBoughtRed(boolean boughtRed) {
        this.boughtRed = boughtRed;
    }

    public boolean isBoughtBlue() {
        return boughtBlue;
    }

    public void setBoughtBlue(boolean boughtBlue) {
        this.boughtBlue = boughtBlue;
    }

    public boolean isBoughtHorse1() {
        return boughtHorse1;
    }

    public void setBoughtHorse1(boolean boughtHorse1) {
        this.boughtHorse1 = boughtHorse1;
    }

    public void cleanGameData(int game) {
        if (game == 1) {
            this.hasHistory1 = false;
            this.timer1 = 120000;
            level1 = 0;
        } else if (game == 2) {
            this.hasHistory2 = false;
            this.timer2 = 120000;
            level2 = 0;
        } else if (game == 3) {
            this.hasHistory3 = false;
            this.timer3 = 120000;
            level3 = 0;
        }
    }
}
