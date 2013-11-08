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
package org.encog.engine.network.activation;

import org.encog.mathutil.BoundMath;
import org.encog.ml.factory.MLActivationFactory;
import org.encog.util.obj.ActivationUtil;

/**
 * An activation function based on the sin function, with a double period.
 * <p/>
 * This activation is typically part of a CPPN neural network, such as
 * HyperNEAT.
 * <p/>
 * The idea for this activation function was developed by Ken Stanley, of
 * the University of Texas at Austin.
 * http://www.cs.ucf.edu/~kstanley/
 * <p/>
 * @author jheaton
 */
public class ActivationSIN implements ActivationFunction {

    /**
     *
     */
    private static final long serialVersionUID = 5301501177778271284L;

    /**
     * Construct the sin activation function.
     */
    public ActivationSIN() {
        this.params = new double[0];
    }
    /**
     * The parameters.
     */
    private double[] params;

    /**
     * @return The object cloned;
     */
    @Override
    public final ActivationFunction clone() {
        return new ActivationSIN();
    }

    /**
     * @return Return true, sin has a derivative.
     */
    @Override
    public final boolean hasDerivative() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void activationFunction(final double[] x, final int start,
                                         final int size) {
        for (int i = start; i < start + size; i++) {
            x[i] = BoundMath.sin(2.0 * x[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double derivativeFunction(final double b, final double a) {
        return BoundMath.cos(2.0 * b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String[] getParamNames() {
        final String[] result = {};
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getParams() {
        return this.params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setParam(final int index, final double value) {
        this.params[index] = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFactoryCode() {
        return ActivationUtil.generateActivationFactory(
                MLActivationFactory.AF_SIN, this);
    }
}