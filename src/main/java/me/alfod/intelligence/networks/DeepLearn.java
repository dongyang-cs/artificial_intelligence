package me.alfod.intelligence.networks;

/**
 * Created by Arvin Alfod on 2017/11/19.
 */
public class DeepLearn {



    double[] input;
    /**
     * omega array
     */
    private double[][][] omega;
    private double[][] gamma;
    private double[] x;

    private DeepLearn(int[] layer) {
        int layerNum = layer.length;
        omega = new double[layerNum][][];
        for (int i = 0; i < layerNum - 1; ++i) {
            omega[i] = new double[layer[i]][layer[i + 1]];
        }
        gamma = new double[layerNum][];
        for (int i = 0; i < layerNum ; ++i) {
            gamma[i] = new double[layer[i]];
        }
    }

    private void setInput(double[] input) {
        this.input = input;
    }

    private void forward(double[] input) {

    }

    private void matrixMult() {

    }
}
