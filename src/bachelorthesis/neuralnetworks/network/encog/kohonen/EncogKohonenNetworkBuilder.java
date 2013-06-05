/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bachelorthesis.neuralnetworks.network.encog.kohonen;

import org.encog.mathutil.rbf.RBFEnum;


public class EncogKohonenNetworkBuilder {
    private double[][] trainingInput;
    private NeighborhoodFunctionType neighborhoodFunction;
    private RBFEnum radialBiasFunction;
    private int radius;
    private double learningRate;
    private int id;
    private int hSize;
    private int vSize;
    private double error;
    private boolean forceWinner;

    public EncogKohonenNetworkBuilder() {
        neighborhoodFunction = NeighborhoodFunctionType.SINGLE;
        radialBiasFunction = RBFEnum.Gaussian;
        radius = 2;
        learningRate = 0.7;
        id = -1;
        hSize = 40;
        vSize = 50;
        forceWinner =  false;
        error = 0.01;
    }

    public EncogKohonenNetworkBuilder setTrainingInput(double[][] trainingInput) {
        this.trainingInput = trainingInput;
        return this;
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

    public EncogKohonenNetworkBuilder setHSize(int hSize) {
        this.hSize = hSize;
        return this;
    }

    public EncogKohonenNetworkBuilder setVSize(int vSize) {
        this.vSize = vSize;
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
        return new EncogKohonenNetwork(trainingInput, neighborhoodFunction, radialBiasFunction, radius, learningRate, error, forceWinner, id, hSize, vSize);
    }
    
}
