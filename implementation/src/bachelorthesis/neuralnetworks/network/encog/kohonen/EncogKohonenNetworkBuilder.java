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

import org.encog.mathutil.rbf.RBFEnum;
import static org.encog.mathutil.rbf.RBFEnum.InverseMultiquadric;

/**
 * EncogHopfieldNetworkBuilder.java (UTF-8)
 *
 * Provides a builder for a configurable Encog SOM
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class EncogKohonenNetworkBuilder {
    private NeighborhoodFunctionType neighborhoodFunction;
    private RBFEnum radialBiasFunction;
    private int radius;
    private int[] neighborhoodSize;
    private double learningRate;
    private int id;
    private double error;
    private boolean forceWinner;

    public EncogKohonenNetworkBuilder() {
        neighborhoodFunction = NeighborhoodFunctionType.BUBLLE;
        radialBiasFunction = RBFEnum.InverseMultiquadric;
        radius = 2;
        learningRate = 0.7;
        id = -1;
        forceWinner =  false;
        error = 0.01;
        neighborhoodSize = new int[]{40,50};
    }

    public EncogKohonenNetworkBuilder setNeighborhoodFunction(NeighborhoodFunctionType neighborhoodFunction) {
        this.neighborhoodFunction = neighborhoodFunction;
        return this;
    }

    public EncogKohonenNetworkBuilder setRadialBiasFunction(RBFEnum radialBiasFunction) {
        this.radialBiasFunction = radialBiasFunction;
        return this;
    }

    public EncogKohonenNetworkBuilder setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public EncogKohonenNetworkBuilder setLearningRate(int learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public EncogKohonenNetworkBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EncogKohonenNetworkBuilder setError(double error) {
        this.error = error;
        return this;
    }

    public EncogKohonenNetworkBuilder setForceWinner(boolean forceWinner) {
        this.forceWinner = forceWinner;
        return this;
    }
    
    

    public EncogKohonenNetwork createEncogKohonenNetwork() {
        return new EncogKohonenNetwork(neighborhoodFunction, radialBiasFunction, radius, learningRate, error, forceWinner, id);
    }
    
}
