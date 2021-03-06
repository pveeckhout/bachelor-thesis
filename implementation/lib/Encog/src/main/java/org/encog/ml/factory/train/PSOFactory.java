/*
 * Encog(tm) Core v3.2 - Java Version
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-core

 * Copyright 2008-2013 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information on Heaton Research copyrights, licenses
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.ml.factory.train;

import java.util.Map;

import org.encog.mathutil.randomize.NguyenWidrowRandomizer;
import org.encog.mathutil.randomize.Randomizer;
import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.factory.MLTrainFactory;
import org.encog.ml.factory.parse.ArchitectureParse;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.pso.NeuralPSO;
import org.encog.util.ParamsHolder;

/**
 * A factory for quick propagation training.
 * <p/>
 */
public class PSOFactory {

    /**
     * Create a PSO trainer.
     * <p/>
     * @param method
     *                 The method to use.
     * @param training
     *                 The training data to use.
     * @param argsStr
     *                 The arguments to use.
     * <p/>
     * @return The newly created trainer.
     */
    public MLTrain create(final MLMethod method,
                          final MLDataSet training, final String argsStr) {

        final Map<String, String> args = ArchitectureParse.parseParams(argsStr);
        final ParamsHolder holder = new ParamsHolder(args);

        final int particles = holder.getInt(
                MLTrainFactory.PROPERTY_PARTICLES, false, 20);

        CalculateScore score = new TrainingSetScore(training);
        Randomizer randomizer = new NguyenWidrowRandomizer();

        final MLTrain train = new NeuralPSO((BasicNetwork) method, randomizer,
                                            score, particles);

        return train;
    }
}
