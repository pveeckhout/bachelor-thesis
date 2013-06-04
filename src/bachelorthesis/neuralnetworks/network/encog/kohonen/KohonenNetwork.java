package bachelorthesis.neuralnetworks.network.encog.kohonen;

/**
 * KohonenNetwork.java (UTF-8)
 *
 * an implementation of the Kohonen self organising maps
 *
 * 2013/06/04
 *
 * @author Encog
 * @since 1.0.0
 * @version 1.0.0
 */
public class KohonenNetwork extends NeuralNetwork {

    double outputWeights[][];
    protected int learnMethod = 1;
    protected double learnRate = 0.5;
    protected double quitError = 0.1;
    protected int retries = 10000;
    protected double reduction = .99;
    protected NeuralReportable owner;
    public boolean halt = false;
    protected TrainingSet train;

    /**
     * The constructor.
     * <p/>
     * @param inputCount  Number of input neurons
     * @param outputCount Number of output neurons
     * @param owner       The owner object, for updates.
     */
    public KohonenNetwork(int inputCount, int outputCount,
                          NeuralReportable owner) {
        int n;

        totalError = 1.0;

        this.inputNeuronCount = inputCount;
        this.outputNeuronCount = outputCount;
        this.outputWeights = new double[outputNeuronCount][inputNeuronCount + 1];
        this.output = new double[outputNeuronCount];
        this.owner = owner;
    }

    public static void copyWeights(KohonenNetwork dest, KohonenNetwork source) {
        for (int i = 0; i < source.outputWeights.length; i++) {
            System.arraycopy(source.outputWeights[i],
                             0,
                             dest.outputWeights[i],
                             0,
                             source.outputWeights[i].length);
        }
    }

    /**
     * Clear the weights.
     */
    public void clearWeights() {
        totalError = 1.0;
        for (int y = 0; y < outputWeights.length; y++) {
            for (int x = 0; x < outputWeights[0].length; x++) {
                outputWeights[y][x] = 0;
            }
        }
    }

    /**
     * Called to initialize the Kononen network.
     */
    public void initialize() {
        int i;
        double optr[];

        clearWeights();
        randomizeWeights(outputWeights);
        for (i = 0; i < outputNeuronCount; i++) {
            optr = outputWeights[i];
            normalizeWeight(optr);
        }
    }

    /**
     * Normalize the input.
     * <p/>
     * @param input   input pattern
     * @param normfac the result
     * @param synth   synthetic last input
     */
    void normalizeInput(
            final double input[],
            double normfac[],
            double synth[]) {
        double length, d;

        length = vectorLength(input);
        // just in case it gets too small
        if (length < 1.E-30) {
            length = 1.E-30;
        }


        normfac[0] = 1.0 / Math.sqrt(length);
        synth[0] = 0.0;

    }

    /**
     * Normalize weights
     * <p/>
     * @param w Input weights
     */
    void normalizeWeight(double w[]) {
        int i;
        double len;

        len = vectorLength(w);
        // just incase it gets too small
        if (len < 1.E-30) {
            len = 1.E-30;
        }


        len = 1.0 / Math.sqrt(len);
        for (i = 0; i < inputNeuronCount; i++) {
            w[i] *= len;
        }
        w[inputNeuronCount] = 0;
    }

    /**
     * Try an input pattern. This can be used to present an input pattern
     * to the network. Usually its best to call winner to get the winning
     * neuron though.
     * <p/>
     * @param input Input pattern.
     */
    void trial(double input[]) {
        int i;
        double normfac[] = new double[1], synth[] = new double[1], optr[];

        normalizeInput(input, normfac, synth);

        for (i = 0; i < outputNeuronCount; i++) {
            optr = outputWeights[i];
            output[i] = dotProduct(input, optr) * normfac[0] +
                    synth[0] * optr[inputNeuronCount];
            // Remap to bipolar (-1,1 to 0,1)
            output[i] = 0.5 * (output[i] + 1.0);
            // account for rounding
            if (output[i] > 1.0) {
                output[i] = 1.0;
            }
            if (output[i] < 0.0) {
                output[i] = 0.0;
            }
        }
    }

    /**
     * Present an input pattern and get the
     * winning neuron.
     * <p/>
     * @param input   input pattern
     * @param normfac the result
     * @param synth   synthetic last input
     * <p/>
     * @return The winning neuron number.
     */
    public int winner(double input[], double normfac[], double synth[]) {
        int i, win = 0;
        double biggest, optr[];

        normalizeInput(input, normfac, synth);  // Normalize input

        biggest = -1.E30;
        for (i = 0; i < outputNeuronCount; i++) {
            optr = outputWeights[i];
            output[i] = dotProduct(input, optr) * normfac[0] +
                    synth[0] * optr[inputNeuronCount];
            // Remap to bipolar(-1,1 to 0,1)
            output[i] = 0.5 * (output[i] + 1.0);
            if (output[i] > biggest) {
                biggest = output[i];
                win = i;
            }
            // account for rounding
            if (output[i] > 1.0) {
                output[i] = 1.0;
            }
            if (output[i] < 0.0) {
                output[i] = 0.0;
            }
        }

        return win;
    }

    /**
     * This method is called to train the network. It can run
     * for a very long time and will report progress back to the
     * owner object.
     * <p/>
     * @exception java.lang.RuntimeException
     */
    public void learn()
            throws RuntimeException {
        int i, key, tset, iter, n_retry, nwts;
        int won[], winners;
        double work[], correc[][], rate, best_err, dptr[];
        double bigerr[] = new double[1];
        double bigcorr[] = new double[1];
        KohonenNetwork bestnet;  // Preserve best here

        totalError = 1.0;

        bestnet = new KohonenNetwork(inputNeuronCount, outputNeuronCount, owner);

        won = new int[outputNeuronCount];
        correc = new double[outputNeuronCount][inputNeuronCount + 1];
        if (learnMethod == 0) {
            work = new double[inputNeuronCount + 1];
        } else {
            work = null;
        }

        rate = learnRate;

        initialize();
        best_err = 1.e30;

// main loop:

        n_retry = 0;
        for (iter = 0;; iter++) {

            evaluateErrors(rate, learnMethod, won,
                           bigerr, correc, work);

            totalError = bigerr[0];

            if (totalError < best_err) {
                best_err = totalError;
                copyWeights(bestnet, this);
            }

            winners = 0;
            for (i = 0; i < won.length; i++) {
                if (won[i] != 0) {
                    winners++;
                }
            }


            if (bigerr[0] < quitError) {
                break;
            }


            if ((winners < outputNeuronCount) &&
                    (winners < train.getTrainingSetCount())) {
                forceWin(won);
                continue;
            }

            adjustWeights(rate, learnMethod, won, bigcorr, correc);

            owner.update(n_retry, totalError, best_err);
            if (halt) {
                owner.update(n_retry, totalError, best_err);
                break;
            }
            Thread.yield();

            if (bigcorr[0] < 1E-5) {
                if (++n_retry > retries) {
                    break;
                }
                initialize();
                iter = -1;
                rate = learnRate;
                continue;
            }

            if (rate > 0.01) {
                rate *= reduction;
            }

        }

        // done
        copyWeights(this, bestnet);

        for (i = 0; i < outputNeuronCount; i++) {
            normalizeWeight(outputWeights[i]);
        }

        halt = true;
        n_retry++;
        owner.update(n_retry, totalError, best_err);
    }

    /**
     * This method does much of the work of the learning process.
     * This method evaluates the weights against the training
     * set.
     * <p/>
     * @param rate         learning rate
     * @param learn_method method(0=additive, 1=subtractive)
     * @param won          a Holds how many times a given neuron won
     * @param bigerr       a returns the error
     * @param correc       a returns the correction
     * @param work         a work area
     * <p/>
     * @exception java.lang.RuntimeException
     */
    void evaluateErrors(
            double rate,
            int learn_method,
            int won[],
            double bigerr[],
            double correc[][],
            double work[])
            throws RuntimeException {
        int best, size, tset;
        double dptr[], normfac[] = new double[1];
        double synth[] = new double[1], cptr[], wptr[], length, diff;

        // reset correction and winner counts
        for (int y = 0; y < correc.length; y++) {
            for (int x = 0; x < correc[0].length; x++) {
                correc[y][x] = 0;
            }
        }

        for (int i = 0; i < won.length; i++) {
            won[i] = 0;
        }

        bigerr[0] = 0.0;

        // loop through all training sets to determine correction
        for (tset = 0; tset < train.getTrainingSetCount(); tset++) {
            dptr = train.getInputSet(tset);
            best = winner(dptr, normfac, synth);
            won[best]++;
            wptr = outputWeights[best];
            cptr = correc[best];
            length = 0.0;

            for (int i = 0; i < inputNeuronCount; i++) {
                diff = dptr[i] * normfac[0] - wptr[i];
                length += diff * diff;
                if (learn_method != 0) {
                    cptr[i] += diff;
                } else {
                    work[i] = rate * dptr[i] * normfac[0] + wptr[i];
                }
            }
            diff = synth[0] - wptr[inputNeuronCount];
            length += diff * diff;
            if (learn_method != 0) {
                cptr[inputNeuronCount] += diff;
            } else {
                work[inputNeuronCount] = rate * synth[0] +
                        wptr[inputNeuronCount];
                if (length > bigerr[0]) {
                    bigerr[0] = length;
                }

                if (learn_method == 0) {
                    normalizeWeight(work);
                    for (int i = 0; i <= inputNeuronCount; i++) {
                        cptr[i] += work[i] - wptr[i];
                    }
                }

            }

            bigerr[0] = Math.sqrt(bigerr[0]);
        }
    }

    /**
     * This method is called at the end of a training iteration.
     * This method adjusts the weights based on the previous trial.
     * <p/>
     * @param rate         learning rate
     * @param learn_method method(0=additive, 1=subtractive)
     * @param won          a holds number of times each neuron won
     * @param bigcorr      holds the error
     * @param correc       holds the correction
     */
    void adjustWeights(
            double rate,
            int learn_method,
            int won[],
            double bigcorr[],
            double correc[][]) {
        double corr, cptr[], wptr[], length, f;

        bigcorr[0] = 0.0;

        for (int i = 0; i < outputNeuronCount; i++) {

            if (won[i] == 0) {
                continue;
            }

            wptr = outputWeights[i];
            cptr = correc[i];

            f = 1.0 / (double) won[i];
            if (learn_method != 0) {
                f *= rate;
            }

            length = 0.0;

            for (int j = 0; j <= inputNeuronCount; j++) {
                corr = f * cptr[j];
                wptr[j] += corr;
                length += corr * corr;
            }

            if (length > bigcorr[0]) {
                bigcorr[0] = length;
            }
        }
        // scale the correction
        bigcorr[0] = Math.sqrt(bigcorr[0]) / rate;
    }

    /**
     * If no neuron wins, then force a winner.
     * <p/>
     * @param won how many t but for him to a imes each neuron won
     * <p/>
     * @exception java.lang.RuntimeException
     */
    void forceWin(
            int won[])
            throws RuntimeException {
        int i, tset, best, size, which = 0;
        double dptr[], normfac[] = new double[1];
        double synth[] = new double[1], dist, optr[];

        size = inputNeuronCount + 1;

        dist = 1.E30;
        for (tset = 0; tset < train.getTrainingSetCount(); tset++) {
            dptr = train.getInputSet(tset);
            best = winner(dptr, normfac, synth);
            if (output[best] < dist) {
                dist = output[best];
                which = tset;
            }
        }

        dptr = train.getInputSet(which);
        best = winner(dptr, normfac, synth);

        dist = -1.e30;
        i = outputNeuronCount;
        while ((i--) > 0) {
            if (won[i] != 0) {
                continue;
            }
            if (output[i] > dist) {
                dist = output[i];
                which = i;
            }
        }

        optr = outputWeights[which];

        System.arraycopy(dptr,
                         0,
                         optr,
                         0,
                         dptr.length);

        optr[inputNeuronCount] = synth[0] / normfac[0];
        normalizeWeight(optr);
    }
}
