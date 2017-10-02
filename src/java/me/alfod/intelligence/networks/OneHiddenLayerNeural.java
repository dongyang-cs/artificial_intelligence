package me.alfod.intelligence.networks;

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
    private double[] gamma = new double[HIDDEN_NODE_NUMBER];
    private double[] beta = new double[Y_NUMBER];
    private double[] alpha = new double[HIDDEN_NODE_NUMBER];
    private double[][] omega = new double[HIDDEN_NODE_NUMBER][Y_NUMBER];
    private double[] y = new double[Y_NUMBER];
    private double[] b = new double[HIDDEN_NODE_NUMBER];
    private double[] theta = new double[Y_NUMBER];
    private double[] yActual = new double[Y_NUMBER];
    private double[] g = new double[Y_NUMBER];
    private double[] e = new double[HIDDEN_NODE_NUMBER];

    private double tau = 0;

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

    private static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
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
        double[] y = new double[]{0.49, 0.49, 0.51, 0.51};

        boolean trained;
        while (true) {
            trained = true;
            for (int i = 0; i < 4; ++i) {
                neural.x = x[i];
                if (Math.abs(step(neural.forward()[0]) - y[i]) > 0.00001) {
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

    private static void trainSingle(OneHiddenLayerNeural neural, double[] x, double y) {
        double neuralY;
        double originY;
        while (true) {
            neural.x = x;
            neural.yActual[0] = y;
            originY=neural.forward()[0];
            neuralY=step(originY);
            if (Math.abs(neuralY- y) > 0.0001) {
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
        for (int h = 0; h < alpha.length; ++h) {
            for (int i = 0; i < x.length; ++i) {
                alpha[h] += x[i] * nu[i][h];
            }
        }
        for (int h = 0; h < b.length; ++h) {
            b[h] = sigmoid(alpha[h] - gamma[h]);
        }
        for (int j = 0; j < beta.length; ++j) {
            for (int h = 0; h < b.length; ++h) {
                beta[j] += b[h] * omega[h][j];
            }
        }
        for (int j = 0; j < y.length; ++j) {
            y[j] = sigmoid(beta[j] - theta[j]);
        }

        return y;
    }

    private void backward() {
        for (int j = 0; j < Y_NUMBER; ++j) {
            g[j] = y[j] * (1 - y[j]) * (yActual[j] - y[j]);
        }
        tau = 0.001;

        for (int j = 0; j < Y_NUMBER; ++j) {
            for (int h = 0; h < HIDDEN_NODE_NUMBER; ++h) {
                omega[h][j] += g[j] * tau * b[h];
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
        for (int j = 0; j < array.length; ++j) {
            initDoubleArray(array[j]);
        }
    }

    private void initDoubleArray(double[]... array) {
        for (int j = 0; j < array.length; ++j) {
            for (int i = 0; i < array[j].length; ++i) {
                array[j][i] = (Math.random() - 0.5) * 2;
            }
        }
    }

}
