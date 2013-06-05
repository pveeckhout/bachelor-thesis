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

/**
 * EncogHopfieldNetworkBuilder.java (UTF-8)
 *
 * Provides a builder for a configurable Encog HopfieldNetwork
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class EncogHopfieldNetworkBuilder {

    private double[][] trainingInput;
    private int id;
    private int hSize;
    private int vSize;

    /**
     * builder constructor
     * <p/>
     * @param trainingInput the input data for training
     * @param hSize         the width of the network
     * @param vSize         the height of the network
     */
    public EncogHopfieldNetworkBuilder(double[][] trainingInput, int hSize,
                                       int vSize) {
        this.trainingInput = trainingInput;
        this.hSize = hSize;
        this.vSize = vSize;
        this.id = -1;
    }

    public EncogHopfieldNetworkBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EncogHopfieldNetwork createEncogHopfieldNetwork() {
        return new EncogHopfieldNetwork(trainingInput, id, hSize, vSize);
    }
}
