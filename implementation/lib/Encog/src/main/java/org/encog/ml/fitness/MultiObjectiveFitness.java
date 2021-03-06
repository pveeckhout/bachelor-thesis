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
package org.encog.ml.fitness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.encog.EncogError;
import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;

public class MultiObjectiveFitness implements CalculateScore, Serializable {

    private final List<FitnessObjective> objectives =
            new ArrayList<FitnessObjective>();
    private boolean min;

    public void addObjective(double weight, CalculateScore fitnessFunction) {
        if (this.objectives.size() == 0) {
            this.min = fitnessFunction.shouldMinimize();
        } else {
            if (fitnessFunction.shouldMinimize() != this.min) {
                throw new EncogError(
                        "Multi-objective mismatch, some objectives are min and some are max.");
            }
        }
        this.objectives.add(new FitnessObjective(weight, fitnessFunction));
    }

    @Override
    public double calculateScore(MLMethod method) {
        double result = 0;

        for (FitnessObjective obj : this.objectives) {
            result += obj.getScore().calculateScore(method) * obj.getWeight();
        }

        return result;
    }

    @Override
    public boolean shouldMinimize() {
        return this.min;
    }

    @Override
    public boolean requireSingleThreaded() {
        for (FitnessObjective obj : this.objectives) {
            if (obj.getScore().requireSingleThreaded()) {
                return true;
            }
        }
        return false;
    }
}
