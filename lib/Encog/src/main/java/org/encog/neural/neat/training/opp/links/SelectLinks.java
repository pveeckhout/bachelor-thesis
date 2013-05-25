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
package org.encog.neural.neat.training.opp.links;

import java.util.List;
import java.util.Random;

import org.encog.ml.ea.train.EvolutionaryAlgorithm;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.neural.neat.training.NEATLinkGene;

/**
 * This interface defines ways that NEAT links can be chosen for mutation.
 * <p/>
 * -----------------------------------------------------------------------------
 * http://www.cs.ucf.edu/~kstanley/ Encog's NEAT implementation was drawn from
 * the following three Journal Articles. For more complete BibTeX sources, see
 * NEATNetwork.java.
 * <p/>
 * Evolving Neural Networks Through Augmenting Topologies
 * <p/>
 * Generating Large-Scale Neural Networks Through Discovering Geometric
 * Regularities
 * <p/>
 * Automatic feature selection in neuroevolution
 */
public interface SelectLinks {

    /**
     * @return The trainer being used.
     */
    EvolutionaryAlgorithm getTrainer();

    /**
     * Setup the selector.
     * <p/>
     * @param theTrainer The trainer.
     */
    void init(EvolutionaryAlgorithm theTrainer);

    /**
     * Select links from the specified genome.
     * <p/>
     * @param rnd    A random number generator.
     * @param genome The genome to select from.
     * <p/>
     * @return A List of link genomes.
     */
    List<NEATLinkGene> selectLinks(Random rnd, NEATGenome genome);
}
