package com.example.gameproject.game1;

import android.view.View;

import java.util.ArrayList;

/**
 * Fill in the blocks() with the colors corresponding to the answers filled in by the player.
 */
class ColorSetter {
    /**
     * A list of Blocks(Views) to be filled.
     */
    private ArrayList<View> views;

    /**
     * @param views A list of Blocks(Views) to be filled.
     */
    ColorSetter(ArrayList<View> views) {
        this.views = views;
    }

    /**
     * Fill in one block with the color corresponding to the given answer.
     *
     * @param view   A list of Blocks(Views) to be filled.
     * @param answer the player's answer.
     */
    private void set(View view, int answer) {
        View color = views.get(answer - 1);
        view.setBackground(color.getBackground());
    }

    /**
     * Fill in all the blocks with the colors corresponding to the given answers.
     *
     * @param answerColors The list of answer blocks to be filled in.
     * @param answers      the player's answer.
     */
    void setAll(ArrayList<View> answerColors, int[] answers) {
        for (int i = 0; i < answers.length; i++) {
            set(answerColors.get(i), answers[i]);
        }
    }
}
