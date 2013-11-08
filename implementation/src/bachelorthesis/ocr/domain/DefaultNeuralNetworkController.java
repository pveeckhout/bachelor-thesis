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
package bachelorthesis.ocr.domain;

import bachelorthesis.neuralnetworks.network.NeuralNetwork;

/**
 * NeuralNetworkController.java (UTF-8)
 *
 * Default controller implementation.
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class DefaultNeuralNetworkController implements NeuralNetworkController<CharactersTrainingSet> {
    
    private NeuralNetwork network;
    private CharactersTrainingSet trainingSet;

    public DefaultNeuralNetworkController() {
        super();
    }
    
    @Override
    public NeuralNetwork getNetwork() {
        return network;
    }

    @Override
    public void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    @Override
    public void buildAndTrainNetwork(CharactersTrainingSet trainingSet) {
        network.buildAndTrainNetwork(trainingSet);
    }

    @Override
    public double[] evaluate(double[] input, int maxIterations) {
        return network.evaluate(input, maxIterations);
    }

    @Override
    public CharactersTrainingSet getTrainingSet() {
        return this.trainingSet;
    }

    @Override
    public void setTrainingSet(CharactersTrainingSet trainingSet) {
        this.trainingSet = trainingSet;
    }
    
}
