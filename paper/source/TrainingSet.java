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
package bachelorthesis.neuralnetworks.util;

/**
 * TrainingSet.java (UTF-8)
 *
 * class that stores the input and output training patterns for neural network
 * training.
 *
 * 2013/06/04
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class TrainingSet {

    private final int inputCount;
    private final int outputCount;
    private double input[][];
    private double output[][];
    private int trainingSetCount;

    public TrainingSet(int inputCount, int outputCount) {
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        trainingSetCount = 0;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public void setTrainingSetCount(int trainingSetCount) {
        this.trainingSetCount = trainingSetCount;
        input = new double[trainingSetCount][inputCount];
        output = new double[trainingSetCount][outputCount];
    }

    public int getTrainingSetCount() {
        return trainingSetCount;
    }

    public void setInput(int set, int index, double value)
            throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        if ((index < 0) || (index >= inputCount)) {
            throw (new RuntimeException("Training input index out of range:" + index));
        }
        input[set][index] = value;
    }

    public void setOutput(int set, int index, double value)
            throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        if ((index < 0) || (set >= outputCount)) {
            throw (new RuntimeException("Training input index out of range:" + index));
        }
        output[set][index] = value;
    }

    public double getInput(int set, int index)
            throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        if ((index < 0) || (index >= inputCount)) {
            throw (new RuntimeException("Training input index out of range:" + index));
        }
        return input[set][index];
    }

    public double getOutput(int set, int index)
            throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        if ((index < 0) || (set >= outputCount)) {
            throw (new RuntimeException("Training input index out of range:" + index));
        }
        return output[set][index];
    }

    public double[] getOutputSet(int set)
            throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        return output[set];
    }

    public double[] getInputSet(int set)
            throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        return input[set];
    }

    public void setInputSet(int set, double[] input) throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        this.input[set] = input;
    }

    public void setOutputSet(int set, double[] output)throws RuntimeException {
        if ((set < 0) || (set >= trainingSetCount)) {
            throw (new RuntimeException("Training set out of range:" + set));
        }this.output[set] = output;
    }

    public double[][] getInput() {
        return input;
    }

    public void setInput(double[][] input) {
        this.input = input;
    }

    public double[][] getOutput() {
        return output;
    }

    public void setOutput(double[][] output) {
        this.output = output;
    }
}
