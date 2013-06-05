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
package bachelorthesis.neuralnetworks.network.encog.perceptron;

import java.util.ArrayList;
import java.util.List;
import org.encog.ml.train.strategy.Strategy;

/**
 * EncogPerceptronNetworkBuilder.java (UTF-8)
 *
 * Provides a builder for a configurable Encog BasicNetwork
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.1.0
 */
public class EncogPerceptronNetworkBuilder {

    private int id;
    private double[][] trainingInput;
    private double[][] trainingIdeal;
    private int[] hiddenLayers;
    private double accuracy;
    private double learningRate;
    private List<Strategy> trainingStrategies;
    private PropagationType propagationType;
    private int hSize;
    private int vSize;

    /**
     * builderConstructor
     * <p/>
     * @param trainingInput The inputs for the training
     * @param trainingIdeal the expected results for the training
     */
    public EncogPerceptronNetworkBuilder(double[][] trainingInput,
                                    double[][] trainingIdeal) {
        this.id = -1;
        this.accuracy = 0.00000000001;
        this.learningRate = 2;
        this.trainingStrategies = new ArrayList<>();
        this.propagationType = PropagationType.ResilientPropagation;
        this.trainingInput = trainingInput;
        this.trainingIdeal = trainingIdeal;
        this.hSize = 40;
        this.vSize = 50;
    }

    public EncogPerceptronNetworkBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EncogPerceptronNetworkBuilder setHsize(int hSize) {
        this.hSize = hSize;
        return this;
    }

    public EncogPerceptronNetworkBuilder setVsize(int vSize) {
        this.vSize = vSize;
        return this;
    }

    public EncogPerceptronNetworkBuilder setHiddenLayers(int[] hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
        return this;
    }

    public EncogPerceptronNetworkBuilder setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public EncogPerceptronNetworkBuilder setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public EncogPerceptronNetworkBuilder setTrainingStrategies(
            List<Strategy> trainingStrategies) {
        this.trainingStrategies = trainingStrategies;
        return this;
    }

    public EncogPerceptronNetworkBuilder setPropagationType(
            PropagationType propagationType) {
        this.propagationType = propagationType;
        return this;
    }

    public EncogPerceptronNetwork createEncogBasicLetterRecognitionNetwork() {
        return new EncogPerceptronNetwork(id, hSize, vSize, trainingInput,
                                     trainingIdeal, hiddenLayers, accuracy,
                                     learningRate, trainingStrategies,
                                     propagationType);
    }
}
