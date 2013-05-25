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

package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.producer;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.noise.NoiseProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.noise.NoiseProducer;

/**
 * NoiseProducerType.java (UTF-8)
 *
 * all types of noise producers
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.5
 * @version 1.0.13
 */
public enum NoiseProducerType {
    CURVEDLINE("creates a curved line on the image to serve as noise"),
    STRAIGHTLINE("creates a straight line on the image to serve as noise");
    private String description;

    private NoiseProducerType(String description) {
        this.description = description;
    }

    /**
     * returns the description.
     * @return string description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * returns a producer of the type.
     * 
     * @return a noise producer
     * @see NoiseProducer
     */
    public NoiseProducer getNoiseProducer() {
        return new NoiseProducerBuilder(this).create();
    }
}
