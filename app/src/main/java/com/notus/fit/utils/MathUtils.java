package com.notus.fit.utils;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 8:15 PM
 */
public class MathUtils {
    public MathUtils() {
    }

    public static int max(int ai[]) {
        int i = 0;
        int j = ai[0];
        for (int k = ai.length; i < k; i++) {
            j = Math.max(j, ai[i]);
        }
        return j;
    }
}
