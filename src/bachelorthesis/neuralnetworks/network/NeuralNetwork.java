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
package bachelorthesis.neuralnetworks.network;

import java.io.Serializable;

/**
 * NeuralNetwork.java (UTF-8)
 *
 * Abstract class that all neural networks should extend, this is to streamline
 * the testing and building statics phase. The actions of the networks are
 * defined by NeuralNetworkActions interface implements serialisable for saving
 * the networks.
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.1.2
 * @see NeuralNetworkActions
 */
public abstract class NeuralNetwork implements NeuralNetworkActions,
        Serializable {

    private int id, hSize, vSize;

    /**
     * Default constructor, sets the id to -1, hSize to 40 and vSize to 50.
     *
     */
    public NeuralNetwork() {
        this(-1, 40, 50);
    }

    /**
     * Constructor
     *
     * @param id    the id of the network
     * @param hSize the horizontal size (width)
     * @param vSize the vertical size (height)
     */
    public NeuralNetwork(int id, int hSize, int vSize) {
        this.id = id;
        this.hSize = hSize;
        this.vSize = vSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHsize() {
        return hSize;
    }

    public void setHsize(int hSize) {
        this.hSize = hSize;
    }

    public int getVsize() {
        return vSize;
    }

    public void setVsize(int vSize) {
        this.vSize = vSize;
    }

    /**
     * generates a string representation of the layers layout
     * <p/>
     * @return a string representation of the layers layout
     */
    public abstract String getLayerLayout();
}
