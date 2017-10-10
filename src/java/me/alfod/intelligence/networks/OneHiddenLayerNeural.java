package me.alfod.intelligence.networks;

import me.alfod.intelligence.util.Squash;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Arvin Alfod on 2017/9/17.
 */
public class OneHiddenLayerNeural {
    private final int HIDDEN_NODE_NUMBER = 2;
    private final int X_NUMBER = 2;
    private final int Y_NUMBER = 1;

    private double[] x = new double[X_NUMBER];

    private double[][] nu = new double[X_NUMBER][HIDDEN_NODE_NUMBER];
    private double[][] omega = new double[HIDDEN_NODE_NUMBER][Y_NUMBER];

    private double[] beta = new double[Y_NUMBER];
    private double[] theta = new double[Y_NUMBER];
    private double[] g = new double[Y_NUMBER];
    private double[] y = new double[Y_NUMBER];
    private double[] yActual = new double[Y_NUMBER];

    private double[] alpha = new double[HIDDEN_NODE_NUMBER];
    private double[] b = new double[HIDDEN_NODE_NUMBER];
    private double[] e = new double[HIDDEN_NODE_NUMBER];
    private double[] gamma = new double[HIDDEN_NODE_NUMBER];

    private final double tau = 0.5;

    public OneHiddenLayerNeural() {
        initDoubleArray(gamma, theta);
        initDoubleArray(nu, omega);
    }

    public static void main(String[] args) {
        train();
    }

    private static void print(Object o) {
        System.out.println(o);
    }

    private static void print(double[] o) {
        System.out.println(Arrays.toString(o));
    }



    public static double step(double in) {
        if (in < 0.5) {
            return 0;
        }
        return 1;
    }

    private static void train() {
        OneHiddenLayerNeural neural = new OneHiddenLayerNeural();
        double[][] x = new double[][]{{0, 0}, {1, 1}, {1, 0}, {0, 1}};
        double[][] y = new double[][]{{0.4}, {0.4}, {0.6}, {0.6}};

        boolean trained;
        while (true) {
            trained = true;
            for (int i = 0; i < 4; ++i) {
                neural.x = x[i];
                neural.yActual = y[i];
                if (Math.abs(neural.forward()[0] - y[i][0]) > 0.01) {
                    trained = false;
                    trainSingle(neural, x[i], y[i]);
                }
            }
            if (trained) {
                break;
            }
        }
        scanner();
    }

    private static void trainSingle(OneHiddenLayerNeural neural, double[] x, double[] y) {
        double originY;
        while (true) {
            neural.x = x;
            neural.yActual = y;
            originY = neural.forward()[0];
            if (Math.abs(originY - y[0]) > 0.01) {
                neural.backward();
            } else {
                break;
            }
        }
    }

    private static void scanner() {
        Scanner scanner = new Scanner(System.in);
        String[] parameters;
        OneHiddenLayerNeural neural = new OneHiddenLayerNeural();
        while (true) {
            print("x1,x2,x3,,,");
            while (true) {
                parameters = scanner.nextLine().split(",");
                if (parameters.length != neural.X_NUMBER) {
                    print("x number error, need " + neural.X_NUMBER + " , actual " + parameters.length);
                } else {
                    break;
                }
            }

            for (int i = 0; i < neural.X_NUMBER; ++i) {
                neural.x[i] = Integer.valueOf(parameters[i]);
            }
            print(neural.forward());


            print("y1,y2,y3,,,");
            while (true) {
                parameters = scanner.nextLine().split(",");
                if (parameters.length != neural.Y_NUMBER) {
                    print("y number error, need " + neural.Y_NUMBER + " , actual " + parameters.length);
                } else {
                    break;
                }
            }

            double ySum = 0;
            for (int i = 0; i < neural.Y_NUMBER; ++i) {
                neural.yActual[i] = Integer.valueOf(parameters[i]);
                ySum += Math.abs(neural.yActual[i] - step(neural.y[i]));
            }
            if (ySum == 0) {
                print("success ");
            } else {
                neural.backward();
            }

        }
    }

    public double[] forward() {
        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            for (int i = 0; i <X_NUMBER; ++i) {
                alpha[h] += x[i] * nu[i][h];
            }
        }
        for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
            b[h] = Squash.sigmoid(alpha[h] - gamma[h]);
        }
        double delta_beta;
        for (int j = 0; j < Y_NUMBER; ++j) {
            for (int h = 0; h <HIDDEN_NODE_NUMBER; ++h) {
                delta_beta=b[h] * omega[h][j];
                beta[j] += delta_beta;
            }
        }
        for (int j = 0; j < Y_NUMBER; ++j) {
            y[j] = Squash.sigmoid(beta[j] - theta[j]);
        }
        return y;
    }

    private void backward() {
        for (int j = 0; j < Y_NUMBER; ++j) {
            g[j] = y[j] * (1 - y[j]) * (yActual[j] - y[j]);
        }

        double delta_omega;
        for (int j = 0; j < Y_NUMBER; ++j) {
            for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
                delta_omega=g[j] * tau * b[h];
                omega[h][j] += delta_omega;
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
        for (int i = 0; i < X_NUMBER; ++i) {
            for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
                nu[i][h] += tau * e[h] * x[i];
            }
        }
    }

    private void initDoubleArray(double[][]... array) {
        for (double[][] anArray : array) {
            initDoubleArray(anArray);
        }
    }

    private void initDoubleArray(double[]... array) {
        for (int j = 0; j < array.length; ++j) {
            for (int i = 0; i < array[j].length; ++i) {
                array[j][i] = Math.random() ;
            }
        }
    }

}
