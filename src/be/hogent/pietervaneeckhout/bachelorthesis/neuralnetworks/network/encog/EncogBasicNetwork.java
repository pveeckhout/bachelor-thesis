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
package be.hogent.pietervaneeckhout.bachelorthesis.neuralnetworks.network.encog;

import be.hogent.pietervaneeckhout.bachelorthesis.neuralnetworks.network.encog.util.PropagationType;
import be.hogent.pietervaneeckhout.bachelorthesis.neuralnetworks.network.NeuralNetwork;
import static be.hogent.pietervaneeckhout.bachelorthesis.neuralnetworks.network.encog.util.PropagationType.ManhattanPropagation;
import java.util.List;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.Strategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.util.simple.EncogUtility;

/**
 * EncogBasicNetwork.java (UTF-8)
 *
 * Provides a configurable Encog BasicNetwork
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.1.0
 */
public class EncogBasicNetwork extends NeuralNetwork {
    
    private double trainingInput[][];
    private double trainingIdeal[][];
    private BasicNetwork network;
    private int[] hiddenLayers;
    private double accuracy;
    private double learningRate;
    private List<Strategy> trainingStrategies;
    private PropagationType propagationType;

    /**
     * Constructor
     *
     * @param id the id of the network
     * @param trainingInput The inputs for the training
     * @param trainingIdeal the expected results for the training
     * @param hiddenLayers the amount of neuron each hidden layer has (in order)
     * @param acuracy the desired accuracy
     * @param learningRate the learning rate (only used with
     * ManhattanPropagation)
     * @param trainingStrategies the training strategies to be used
     */
    protected EncogBasicNetwork(int id, int hSize, int vSize, double[][] trainingInput, double[][] trainingIdeal, int[] hiddenLayers, double accuracy, double learningRate, List<Strategy> trainingStrategies, PropagationType propagationType) {
        super(id, hSize, vSize);
        this.trainingInput = trainingInput;
        this.trainingIdeal = trainingIdeal;
        this.hiddenLayers = hiddenLayers;
        this.accuracy = accuracy;
        this.learningRate = learningRate;
        this.trainingStrategies = trainingStrategies;
        this.propagationType = propagationType;
    }

    @Override
    public void buildNetwork() {
        System.out.println("Building basic network");
        this.network = new BasicNetwork();

        System.out.println("Adding layers to network");
        network.addLayer(new BasicLayer(null, true, (super.getHsize() * super.getVsize())));
        if (hiddenLayers != null) {
            for (int i : hiddenLayers) {
                network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
            }
        }
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, trainingIdeal[0].length));

        network.getStructure().finalizeStructure();
        network.reset();
    }

    @Override
    public void trainNetwork() {
        network.reset();

        System.out.println("initializing network training system");
        MLDataSet trainingSet = new BasicMLDataSet(trainingInput, trainingIdeal);
        final MLTrain training;

        switch (propagationType) {
            case Backpropagation:
                training = new Backpropagation(network, trainingSet);
                break;
            case ManhattanPropagation:
                training = new ManhattanPropagation(network, trainingSet, learningRate);
                break;
            case ResilientPropagation:
                training = new ResilientPropagation(network, trainingSet);
                break;
            case ScaledConjugateGradient:
                training = new ScaledConjugateGradient(network, trainingSet);
                break;
            default:
                IllegalArgumentException e = new IllegalArgumentException("Unknown propagationType");
                throw e;
        }

        System.out.println("Propagation: " + propagationType.name());

        System.out.println("adding training strategies");

        for (Strategy strategy : trainingStrategies) {
            training.addStrategy(strategy);
        }

        System.out.println("Start training to acuracy: " + accuracy);
        int layers = network.getLayerCount();
        System.out.println("#Layer: " + layers);
        for (int i = 0; i < layers; i++) {
            System.out.println("Layer " + i + " #neurons: " + network.getLayerTotalNeuronCount(i));
        }

        long startTimeLong = System.nanoTime();
        EncogUtility.trainToError(training, accuracy);
        long endTimeLong = System.nanoTime();
        double durationInSec = (double) ((endTimeLong - startTimeLong) / Math.pow(10, 9));
        System.out.println("Finished training network in: " + durationInSec);
    }

    @Override
    public double[] evaluate(double[] input, int maxIterations) {
        double[] output = new double[trainingIdeal[0].length];
        System.out.println("Evaluating input");
        long startTimeLong = System.nanoTime();
        network.compute(input, output);
        long endTimeLong = System.nanoTime();
        double durationInSec = (double) ((endTimeLong - startTimeLong) / Math.pow(10, 9));
        System.out.println("Finished evaluating in: " + durationInSec);

        return output;
    }

    @Override
    public String getLayerLayout() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[ ");
        int layers = network.getLayerCount();
        for (int i = 0; i < layers; i++) {
            strBuilder.append(network.getLayerTotalNeuronCount(i) - 1).append(" ");
        }
       
        return strBuilder.append("]").toString();
    }
}
