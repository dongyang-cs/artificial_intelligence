package me.alfod.intelligence.networks;

import me.alfod.intelligence.util.Squash;

/**
 * Created by Arvin Alfod on 2017/9/17.
 */
public class OneHiddenLayerNeural {
    public final int HIDDEN_NODE_NUMBER = 2;
    public final int X_NUMBER = 2;
    public final int Y_NUMBER = 1;
    private final double tau = 0.1;
    public double[] x = new double[X_NUMBER];
    public double[] y = new double[Y_NUMBER];
    public double[] yActual = new double[Y_NUMBER];
    private double[][] nu = new double[X_NUMBER][HIDDEN_NODE_NUMBER];
    private double[][] omega = new double[HIDDEN_NODE_NUMBER][Y_NUMBER];
    private double[] beta = new double[Y_NUMBER];
    private double[] theta = new double[Y_NUMBER];
    private double[] g = new double[Y_NUMBER];
    private double[] alpha = new double[HIDDEN_NODE_NUMBER];
    private double[] b = new double[HIDDEN_NODE_NUMBER];
    private double[] e = new double[HIDDEN_NODE_NUMBER];
    private double[] gamma = new double[HIDDEN_NODE_NUMBER];

    public OneHiddenLayerNeural() {
        initDoubleArray(gamma, theta);
        initDoubleArray(nu, omega);
    }

    public double[] forward(double... x_array) {
        if (x_array.length > 0) {
            x = x_array;
        }
        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            alpha[h] = 0;
            for (int i = 0; i < X_NUMBER; ++i) {
                alpha[h] += x[i] * nu[i][h];
            }
        }
        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            b[h] = Squash.sigmoid(alpha[h] - gamma[h]);
        }
        double delta_beta;
        for (int j = 0; j < Y_NUMBER; ++j) {
            beta[j] = 0;
            for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
                delta_beta = b[h] * omega[h][j];
                beta[j] += delta_beta;
            }
        }
        for (int j = 0; j < Y_NUMBER; ++j) {
            y[j] = Squash.sigmoid(beta[j] - theta[j]);
        }
        return y;
    }

    public void backward(double... y_array) {
        if (y_array.length > 0) {
            yActual = y_array;
        }
        for (int j = 0; j < Y_NUMBER; ++j) {
            g[j] = y[j] * (1 - y[j]) * (yActual[j] - y[j]);
        }

        double delta_omega;
        double[][] new_omega = new double[HIDDEN_NODE_NUMBER][Y_NUMBER];
        for (int j = 0; j < Y_NUMBER; ++j) {
            for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
                delta_omega = g[j] * tau * b[h];
                new_omega[h][j] = omega[h][j];
                new_omega[h][j] += delta_omega;
            }
        }
        for (int j = 0; j < Y_NUMBER; ++j) {
            theta[j] -= g[j] * tau;
        }
        double wg;
        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            wg = 0;
            for (int j = 0; j < Y_NUMBER; ++j) {
                wg += omega[h][j] * g[j];
            }
            e[h] = b[h] * (1 - b[h]) * wg;
        }

        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            gamma[h] -= e[h] * tau;
        }
        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            for (int i = 0; i < X_NUMBER; ++i) {
                nu[i][h] += tau * e[h] * x[i];
            }
        }
        omega = new_omega;
    }

    private void initDoubleArray(double[][]... array) {
        for (double[][] anArray : array) {
            initDoubleArray(anArray);
        }
    }

    private void initDoubleArray(double[]... array) {
        for (int j = 0; j < array.length; ++j) {
            for (int i = 0; i < array[j].length; ++i) {
                array[j][i] = Math.random();
            }
        }
    }

}
