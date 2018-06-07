import me.alfod.intelligence.networks.TwoHiddenLayerNeural;

import java.util.Arrays;
import java.util.Scanner;

import static me.alfod.intelligence.networks.SingleLayerPerceptron.step;

/**
 * Created by Arvin Alfod on 2017/10/10.
 */
public class OneHiddenTest {

    public static void main(String[] args) {
        TwoHiddenLayerNeural neural = new TwoHiddenLayerNeural();
        double[][] x = new double[][]{{0, 0}, {1, 1}, {1, 0}, {0, 1}};
        double[][] y = new double[][]{{0.4}, {0.4}, {0.6}, {0.6}};
        while (true) {
            if (valid(neural, x[0], y[0])
                    && valid(neural, x[1], y[1])
                    && valid(neural, x[2], y[2])
                    && valid(neural, x[3], y[3])) {
                scanner(neural);
                break;
            }
            for (int i = 0; i < 4; ++i) {
                if (!valid(neural, x[i], y[i])) {
                    trainSingle(neural, x[i], y[i]);
                }
            }
        }
    }

    private static void trainSingle(TwoHiddenLayerNeural neural, double[] x, double[] y) {
        while (true) {
            if (valid(neural, x, y)) {
                return;
            } else {
                neural.backward(y);
            }
        }
    }

    private static void scanner(TwoHiddenLayerNeural neural) {
        Scanner scanner = new Scanner(System.in);
        String[] parameters;
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
                neural.x[i] = Double.valueOf(parameters[i]);
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
                neural.yActual[i] = Double.valueOf(parameters[i]);
                ySum += Math.abs(neural.yActual[i] - step(neural.y[i]));
            }
            if (ySum == 0) {
                print("success ");
            } else {
                neural.backward();
            }

        }
    }

    private static void print(Object o) {
        System.out.println(o);
    }

    private static void print(double[] o) {
        System.out.println(Arrays.toString(o));
    }

    private static boolean valid(TwoHiddenLayerNeural neural, double[] x, double[] y) {
        return Math.abs(neural.forward(x)[0] - y[0]) < 0.01;
    }


}
