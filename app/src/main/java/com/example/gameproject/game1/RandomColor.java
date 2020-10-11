package com.example.gameproject.game1;

import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Set the backgrounds of the blocks with a series of dark to light random colors.
 */
class RandomColor {
    /**
     * The RGB index of the darkest block.
     */
    String r0, b0, g0;
    /**
     * Red, green, blue index.
     */
    int r, b, g;
    /**
     * The total number of colors.
     */
    int numOfColor;
    /**
     * The number increased on blue index each time.
     */
    int gap;

    /**
     * Randomly choose a (darkest) color for the first block.
     *
     * @param numOfColor The total number of colors.
     */
    RandomColor(int numOfColor) {
        this.numOfColor = numOfColor;
        gap = 180 / (numOfColor);
        Random random = new Random();
        r = random.nextInt(255);
        g = random.nextInt(255);
        b = random.nextInt(75);
        r0 = Integer.toHexString(r).toUpperCase();
        g0 = Integer.toHexString(g).toUpperCase();
        r0 = r0.length() == 1 ? "0" + r0 : r0;
        g0 = g0.length() == 1 ? "0" + g0 : g0;
    }

    RandomColor() {
    }

    /**
     * Generate colors (lighter and lighter) for other blocks.
     *
     * @return a list of colors for other blocks.
     */
    private int[] generate() {
        int[] colorList = new int[numOfColor + 1];
        for (int i = 0; i < colorList.length; i++) {
            int newb = b + i * gap;
            b0 = Integer.toHexString(newb).toUpperCase();
            b0 = b0.length() == 1 ? "0" + b0 : b0;
            colorList[i] = Color.parseColor("#" + r0 + g0 + b0);
        }
        return colorList;
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
