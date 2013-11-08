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
package bachelorthesis.captchabuilder.util.enums.producer;

import bachelorthesis.captchabuilder.elementcreator.producer.border.BorderProducer;
import bachelorthesis.captchabuilder.elementcreator.producer.border.BorderProducerBuilder;

/**
 * BorderProducerType.java (UTF-8)
 *
 * all types of border producers
 *
 * 2013/04/18
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.12
 * @version 1.0.13
 */
public enum BorderProducerType {

    SOLID("Creates a solid border");
    private String description;

    private BorderProducerType(String description) {
        this.description = description;
    }

    /**
     * returns the description.
     * <p/>
     * @return string description
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns a producer of the type.
     * <p/>
     * @return a border producer
     * <p/>
     * @see BorderProducer
     */
    public BorderProducer getBorderProducer() {
        return new BorderProducerBuilder(this).create();
    }
}
