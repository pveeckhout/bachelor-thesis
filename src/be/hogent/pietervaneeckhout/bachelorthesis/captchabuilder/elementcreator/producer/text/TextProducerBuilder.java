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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.text;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.producer.TextProducerType;

/**
 * TextProducerBuilder.java (UTF-8)
 *
 * Builder for a text producer
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.0.13
 */
public class TextProducerBuilder implements CaptchaElementCreatorBuilder {

    private int minLenght;
    private int maxLenght;
    private TextProducerType type;

    /**
     * constructor 
     * 
     * @param type the type of text producer to be created
     */
    public TextProducerBuilder(TextProducerType type) {
        this.minLenght = CaptchaConstants.DEFAULT_LENGTH;
        this.maxLenght = CaptchaConstants.DEFAULT_LENGTH;
        this.type = type;
    }

    public TextProducerBuilder setLenght(int minLenght, int maxLenght) {
        this.minLenght = minLenght;
        this.maxLenght = maxLenght;
        return this;
    }
    
    public TextProducerBuilder setMinLenght(int minLenght) {
        this.minLenght = minLenght;
        return this;
    }
    
    public TextProducerBuilder setMaxLenght(int maxLenght) {
        this.maxLenght = maxLenght;
        return this;
    }

    @Override
    public TextProducer create() {
        switch (type) {
            case ALPHANUMERIC:
                return new AlphanumericTextProducer(minLenght, maxLenght);
            case REDUCED_ALPHANUMERIC:
                return new ReducedAlphanumericTextProducer(minLenght, maxLenght);
            case CHINESE:
                return new ChineseTextProducer(minLenght, maxLenght);
            case ARABIC:
                return new ArabicTextProducer(minLenght, maxLenght);
            case NUMBERS:
                return new NumbersProducer(minLenght, maxLenght);
            case LETTERS:
                return new LetterTextProducer(minLenght, maxLenght);
            case LETTERS_SPECIAL:
                return new SpecialLetterTextProducer(minLenght, maxLenght);
            case NUMBERS_SPECIAL:
                return new SpecialNumbersProducer(minLenght, maxLenght);
            case ALPHANUMERIC_SPECIAL:
                return new SpecialAlphanumericTextProducer(minLenght, maxLenght);
            default:
                throw new IllegalArgumentException("TextProducer not found: " + type.name());
        }
    }
}
