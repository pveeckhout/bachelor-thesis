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
package bachelorthesis.neuralnetworks.network.encog.kohonen;

import bachelorthesis.neuralnetworks.network.NeuralNetwork;
import java.util.HashMap;
import java.util.Map;
import org.encog.mathutil.rbf.RBFEnum;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.som.SOM;
import org.encog.neural.som.training.basic.BasicTrainSOM;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodBubble;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodFunction;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodRBF;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodRBF1D;
import org.encog.neural.som.training.basic.neighborhood.NeighborhoodSingle;

/**
 * EncogKohonenNetwork.java (UTF-8)
 *
 * Provides a configurable Encog SOM
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class EncogKohonenNetwork extends NeuralNetwork {

    private double trainingInput[][];
    private Map<Integer, Double[]> output;
    private SOM network;
    private NeighborhoodFunctionType neighborhoodFunctionType;
    private RBFEnum radialBiasFunction;
    private int radius;
    private double learningRate;
    private int[] neighborhoodSize;
    private double error;
    private boolean forceWinner;

    protected EncogKohonenNetwork(double[][] trainingInput,
            NeighborhoodFunctionType neighborhoodFunction,
            RBFEnum radialBiasFunction, int radius, double learningRate, double error, boolean forceWinner, int id, int hSize,
            int vSize) {
        super(id, hSize, vSize);
        this.trainingInput = trainingInput;
        this.neighborhoodFunctionType = neighborhoodFunction;
        this.radialBiasFunction = radialBiasFunction;
        this.radius = radius;
        this.learningRate = learningRate;
        this.forceWinner = forceWinner;
        this.error = error;
    }

    @Override
    public String getLayerLayout() {
        return "[" + network.getInputCount() + "|" + network.getOutputCount() + "]";
    }

    @Override
    public void buildNetwork() {
        network = new SOM((super.getHsize() * super
                .getVsize()), trainingInput.length);
        network.reset();
    }

    @Override
    public void trainNetwork() {
        MLDataSet training = new BasicMLDataSet(trainingInput, null);

        NeighborhoodFunction neighborhoodFunction;

        switch (neighborhoodFunctionType) {
            case BUBLLE:
                neighborhoodFunction = new NeighborhoodBubble(radius);
                break;
            case RBF:
                neighborhoodFunction =
                        new NeighborhoodRBF(neighborhoodSize, radialBiasFunction);
                break;
            case RBF1D:
                neighborhoodFunction = new NeighborhoodRBF1D(radialBiasFunction);
                break;
            case SINGLE:
                neighborhoodFunction = new NeighborhoodSingle();
                break;
            default:
                neighborhoodFunction = new NeighborhoodSingle();
        }

        BasicTrainSOM train = new BasicTrainSOM(network, learningRate, training,
                neighborhoodFunction);
        
        train.setForceWinner(forceWinner);
        
        double err = 1;
        int iteration = 0;
        
        while (err>error) {
            train.iteration();
            err = train.getError();
            System.out.println("Iteration: " + iteration + ", Error:" + err + ", target error: " + error);
        }
        
        
        //couple each output neuron to a trained pattern #output neurons == #trained pattterns
        output = new HashMap<>();
         MLData data;
        for (int i = 0; i < trainingInput.length; i++) {
            double[] input = trainingInput[i];
           data = new BasicMLData(input);
           int outneuron = network.winner(data);
           
           //JAVA PRIMITIVE TYPES.... JUST WHY????
           Double[] outPattern = new Double[input.length];
            for (int j = 0; j < input.length; j++) {
                outPattern[j] = new Double(input[j]);
            }
           
           output.put(outneuron, outPattern);
        }
    }

    @Override
    public double[] evaluate(double[] input, int maxIterations) {
        MLData data = new BasicMLData(input);
        Double[] outPattern = output.get(network.winner(data));
        double[] out = new double[outPattern.length];
        
        //JAVA PRIMITIVE TYPES.... JUST WHY???
        for (int i = 0; i < outPattern.length; i++) {
            out[i] = outPattern[i];
        }
        
        return out;
    }
}
