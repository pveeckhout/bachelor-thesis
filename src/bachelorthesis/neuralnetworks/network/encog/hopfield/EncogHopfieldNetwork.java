/*
 * The MIT License
 *
 * Copyright 2013 Pieter Van Eeckhout.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package bachelorthesis.neuralnetworks.network.encog.hopfield;

import bachelorthesis.neuralnetworks.network.NeuralNetwork;
import org.encog.ml.data.specific.BiPolarNeuralData;
import org.encog.neural.thermal.HopfieldNetwork;

/**
 * EncogBasicNetwork.java (UTF-8)
 *
 * Provides a configurable Encog HopfieldNetwork
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class EncogHopfieldNetwork extends NeuralNetwork {

    private double trainingInput[][];
    private HopfieldNetwork network;
    private final int neuroncount;

    /**
     * Constructor
     * <p/>
     * @param trainingInput the inputs for training the network
     * @param id            the network id
     * @param hSize         the horizontal size of the network
     * @param vSize         the vertical size of the network
     */
    protected EncogHopfieldNetwork(double[][] trainingInput, int id, int hSize,
                                   int vSize) {
        super(id, hSize, vSize);
        this.trainingInput = trainingInput;
        neuroncount = vSize * hSize;

        if (neuroncount != trainingInput[0].length) {
            IllegalArgumentException e = new IllegalArgumentException(
                    "the length of the trainingsinputs and the neuroncount do not match");
            throw e;
        }
    }

    @Override
    public void buildNetwork() {
        System.out.println("Building hopfield network");
        network = new HopfieldNetwork(neuroncount);
    }

    @Override
    public void trainNetwork() {
        network.reset();
        System.out.println("Training hopfield network");
        long startTimeLong = System.nanoTime();
        for (double[] ds : trainingInput) {
            network.addPattern(doubleArrayToBiPolarNeuralData(ds));
        }
        long endTimeLong = System.nanoTime();
        double durationInSec = (double) ((endTimeLong - startTimeLong) / Math
                .pow(10, 9));
        System.out.println("Finished training network in: " + durationInSec);
    }

    private BiPolarNeuralData doubleArrayToBiPolarNeuralData(double[] data) {
        BiPolarNeuralData patternData = new BiPolarNeuralData(neuroncount);
        if (data.length != neuroncount) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException(
                    "the size of the traingsinputs is different from the amount of input neurons");
            throw e;
        }
        patternData.setData(data);
        return patternData;
    }

    @Override
    public double[] evaluate(double[] input, int maxIterations) {
        System.out.println("hopfield network evaluating with max iterations: " +
                maxIterations);
        BiPolarNeuralData inputPattern = doubleArrayToBiPolarNeuralData(input);
        network.setCurrentState(inputPattern);
        int cycles = network.runUntilStable(maxIterations);
        System.out.println("Cycles until stable(max " + maxIterations + "): " +
                cycles + ", result=");
        BiPolarNeuralData outputPattern = (BiPolarNeuralData) network
                .getCurrentState();
        System.out.println(convertForDisplay(inputPattern, outputPattern));
        return outputPattern.getData();
    }

    private String convertForDisplay(BiPolarNeuralData inputPattern,
                                     BiPolarNeuralData outputPattern) {
        int index1 = 0;
        int index2 = 0;
        StringBuilder block = new StringBuilder();

        for (int row = 0; row < super.getVsize(); row++) {


            for (int col = 0; col < super.getHsize(); col++) {
                if (inputPattern.getBoolean(index1++)) {
                    block.append('O');
                } else {
                    block.append(' ');
                }
            }

            block.append("   ->   ");

            for (int col = 0; col < super.getHsize(); col++) {
                if (outputPattern.getBoolean(index2++)) {
                    block.append('O');
                } else {
                    block.append(' ');
                }
            }

            block.append("\n");
        }

        return block.toString();
    }

    @Override
    public String getLayerLayout() {
        return "[ " + getHsize() + " X " + getVsize() + " ]";
    }
}