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

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.text.TextProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.text.TextProducer;

/**
 * TextProducerType.java (UTF-8)
 *
 * all types of text producers
 *
 * 2013/04/14
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.1
 * @version 1.0.13
 */
public enum TextProducerType {

    ALPHANUMERIC("Generates alphanumeric strings"),
    REDUCED_ALPHANUMERIC("Generates reduced alphanumeric characterset strings to prevent ambiguities"),
    CHINESE("Generates Chinese character strings"),
    ARABIC("Generates Chinese character strings"),
    NUMBERS("Generates number strings"),
    LETTERS("Generates normal character strings"),
    LETTERS_SPECIAL("Generates normal character combined with special character strings"),
    NUMBERS_SPECIAL("Generates number strings combined with special character strings"),
    ALPHANUMERIC_SPECIAL("Generates alphanumeric strings combined with special character strings");
    private String desciption;

    private TextProducerType(String desciption) {
        this.desciption = desciption;
    }

    /**
     * returns the description.
     * @return string description
     */
    public TextProducer getTextProducer() {
        return new TextProducerBuilder(this).create();
    }

    /**
     * returns a producer of the type.
     * 
     * @return a text producer
     * @see TextProducer
     */
    public String getDescription() {
        return desciption;
    }

    @Override
    public String toString() {
        return name() + ": " + desciption;
    }
}
