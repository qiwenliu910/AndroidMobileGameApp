package com.example.gameproject.game1;

import java.util.Random;

/**
 * Takes in a total number of Colors and generate an int array (from 1 to total number of colors) in
 * random order.
 */
class RandomOrder {
    /**
     * The total number of colors.
     */
    private int numOfColor;

    /**
     * @param numOfColor the total number of colors.
     */
    RandomOrder(int numOfColor) {
        this.numOfColor = numOfColor;
    }


    /**
     * Get an integer array which contains integers from 1 to numOfColor in random order.
     *
     * @return the order of the colors.
     */
    int[] getOrder() {
        int[] order = new int[numOfColor];
        Random r = new Random();
        for (int i = 0; i < numOfColor; ) {
            boolean contain = false;
            int num = r.nextInt(numOfColor);
            num += 1;
            for (int n : order) {
                if (n == num) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                order[i] = num;
                i++;
            }
        }
        return order;
    }

}
