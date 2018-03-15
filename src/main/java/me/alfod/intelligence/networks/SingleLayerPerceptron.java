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

    public static void main(String[] args) {
        SingleLayerPerceptron perceptron = new SingleLayerPerceptron();
        Scanner scanner = new Scanner(System.in);
        int forecast;
        double y;
        while (true) {
            println();
            println("please enter you number in format: x1 x2 y");
            for (int i = 1; i < perceptron.getX().length; i++) {
                perceptron.setX(i, scanner.nextDouble());
            }
            forecast = step(perceptron.calculate());
            println(perceptron.getWeight());
            println("forecast: " + forecast);
            y = scanner.nextDouble();
            if (forecast != y) {
                perceptron.adjust(y, forecast);
            } else {
                println("success");
            }
        }
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void println(double[] o) {
        System.out.println(Arrays.toString(o));
    }

    public static void println() {
        System.out.println();
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
