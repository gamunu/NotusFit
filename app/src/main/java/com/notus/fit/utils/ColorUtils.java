package com.notus.fit.utils;

import android.content.Context;
import android.graphics.Color;

import com.notus.fit.R;

import java.util.Random;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class ColorUtils {
    public ColorUtils() {
    }

    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] * (0.2f + (0.8f * hsv[2]));
        return Color.HSVToColor(hsv);
    }

    public static int getRandomColor() {
        int[] colors = new int[]{R.color.blue_grey_600, R.color.purple_600, R.color.red_500, R.color.teal_600, R.color.blue_500, R.color.pink_600, R.color.grey_700, R.color.teal_400, R.color.red_400, R.color.blue_400, R.color.brown_700, R.color.lime_600, R.color.cyan_800};
        return colors[new Random().nextInt(colors.length)];
    }

    public static int getStepsColor(Context context, int steps) {
        int stepGoal = Integer.parseInt(PrefManager.with(context).getString(context.getResources().getString(R.string.steps_goal), "10000"));
        if (((double) steps) > ((double) stepGoal) * 1.4d) {
            return context.getResources().getColor(R.color.green_800);
        }
        if (((double) steps) > ((double) stepGoal) * 1.2d) {
            return context.getResources().getColor(R.color.green_500);
        }
        if (steps > stepGoal) {
            return context.getResources().getColor(R.color.green_300);
        }
        if (((double) steps) > ((double) stepGoal) * 0.85d) {
            return context.getResources().getColor(R.color.orange_500);
        }
        if (((double) steps) > ((double) stepGoal) * 0.65d) {
            return context.getResources().getColor(R.color.orange_400);
        }
        if (((double) steps) > ((double) stepGoal) * 0.5d) {
            return context.getResources().getColor(R.color.amber_700);
        }
        if (((double) steps) > ((double) stepGoal) * 0.4d) {
            return context.getResources().getColor(R.color.red_400);
        }
        if (((double) steps) > ((double) stepGoal) * 0.2d) {
            return context.getResources().getColor(R.color.red_500);
        }
        return context.getResources().getColor(R.color.red_700);
    }

    public static int lightColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] * (1.0f - (0.8f * (1.0f - hsv[2])));
        return Color.HSVToColor(hsv);
    }
}
