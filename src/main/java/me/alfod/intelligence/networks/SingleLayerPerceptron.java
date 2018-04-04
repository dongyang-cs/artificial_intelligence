package me.alfod.intelligence.networks;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Arvin Alfod on 2017/9/12.
 */
public class SingleLayerPerceptron {
    private final double rate = 0.1;
    private double[] weight;
    private double[] x;

    public SingleLayerPerceptron() {
        weight = new double[3];
        x = new double[3];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = Math.random();
        }
        x[0] = -1;
    }


    public static int step(double in) {
        if (in < 0) {
            return 0;
        }
        return 1;
    }

    public double[] getWeight() {
        return weight;
    }

    public double[] getX() {
        return x;
    }

    public void setX(int i, double value) {
        x[i] = value;
    }

    public double calculate() {
        if (weight.length != x.length) {
            return 0;
        }
        double sum = 0;
        for (int i = 0; i < weight.length; i++) {
            sum += weight[i] * x[i];
        }
        return sum;
    }

    public void adjust(double actual, double forecast) {
        for (int i = 0; i < weight.length; i++) {
            weight[i] = weight[i] + (actual - forecast) * rate * x[i];
        }
    }
}
