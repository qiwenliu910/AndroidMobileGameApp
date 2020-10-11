package com.example.gameproject.game1;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generate random colors for bonus level.
 */
class RandomColorBonus extends RandomColor {
    RandomColorBonus() {
        this.numOfColor = 4;
        gap = 50;
        Random random = new Random();
        r = random.nextInt(255);
        g = random.nextInt(105);
        b = random.nextInt(105);
    }

    /**
     *
     * @return a list of random clolors.
     */
    private int[] generate() {
        int[] colorList = new int[numOfColor + 1];
        colorList[0] = toColor(r, g, b);
        colorList[1] = toColor(r, g+100, b+50);
        colorList[2] = toColor(r, g+50, b+50);
        colorList[3] = toColor(r, g, b+50);
        colorList[4] = toColor(r, g, b+100);
        return colorList;
    }

    /**
     * Generate a color with given decimal rgb color.
     * @param r red index
     * @param g green index
     * @param b blueindex
     * @return an int that repesents the hex color.
     */
    private int toColor(int r, int g, int b) {
        r0 = Integer.toHexString(r).toUpperCase();
        r0 = r0.length() == 1 ? "0" + r0 : r0;
        g0 = Integer.toHexString(g).toUpperCase();
        g0 = g0.length() == 1 ? "0" + g0 : g0;
        b0 = Integer.toHexString(b).toUpperCase();
        b0 = b0.length() == 1 ? "0" + b0 : b0;
        return Color.parseColor("#" + r0 + g0 + b0);
    }

    /**
     * Set the background of Views with the colors we randomly generated.
     *
     * @param order the order of the colors.
     * @param view  the (darkest) block(View) to be filled in.
     * @param views the blocks(View) to be filled in.
     */
    void set(int[] order, View view, ArrayList<View> views) {
        int[] colors = generate();
        view.setBackgroundColor(colors[0]);
        for (int i = 0; i < order.length; i++) {
            int orderNum = order[i];
            views.get(orderNum - 1).setBackgroundColor(colors[i + 1]);
        }
    }

}
