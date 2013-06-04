package bachelorthesis.neuralnetworks.network.encog.kohonen;

import java.util.Random;

/**
 * NeuralNetwork.java (UTF-8)
 *
 * Abstract class providing some basic methods and variables all neural networks
 * need
 *
 * 2013/06/04
 *
 * @author Encog
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class NeuralNetwork {

    public final static double NEURON_ON = 0.9;
    public final static double NEURON_OFF = 0.1;
    protected double output[];
    protected double totalError;
    protected int inputNeuronCount;
    protected int outputNeuronCount;
    protected Random random = new Random(System.currentTimeMillis());

    /**
     * Calculate the length of a vector
     * <p/>
     * @param vector vector
     * <p/>
     * @return the length.
     */
    public static double vectorLength(double vector[]) {
        double length = 0.0;
        for (int i = 0; i < vector.length; i++) {
            length += vector[i] * vector[i];
        }
        return length;
    }

    /**
     * Called to calculate a dot product.
     * <p/>
     * @param vector1 one vector
     * @param vector2 another vector
     * <p/>
     * @return The dot product.
     */
    double dotProduct(double vector1[], double vector2[]) {
        int k, v;
        double product;

        product = 0.0;
        k = vector1.length;

        v = 0;
        while ((k--) > 0) {
            product += vector1[v] * vector2[v];
            v++;
        }

        return product;
    }

    /**
     * randomises the connection weights
     * <p/>
     * @param weight A weight matrix.
     */
    void randomizeWeights(double weight[][]) {
        double r;

        int temp = (int) (3.464101615 / (2. * Math.random())); //2*sqrt(3)

        for (int y = 0; y < weight.length; y++) {
            for (int x = 0; x < weight[0].length; x++) {
                r = (double) random.nextInt(Integer.MAX_VALUE) + (double) random
                        .nextInt(Integer.MAX_VALUE) -
                        (double) random.nextInt(Integer.MAX_VALUE) -
                        (double) random.nextInt(Integer.MAX_VALUE);
                weight[y][x] = temp * r;
            }
        }
    }
}
