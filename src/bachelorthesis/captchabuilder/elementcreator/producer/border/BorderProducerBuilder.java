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
package bachelorthesis.captchabuilder.elementcreator.producer.border;

import bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import bachelorthesis.captchabuilder.util.ColorRangeRGBA;
import bachelorthesis.captchabuilder.util.enums.producer.BorderProducerType;

/**
 * BorderProducerBuilder.java (UTF-8)
 *
 * Builder for a background producer
 *
 * 2013/04/12
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.12
 * @version 1.0.12
 */
public class BorderProducerBuilder implements CaptchaElementCreatorBuilder {

    private ColorRangeRGBA colorRange;
    private int thickness;
    private BorderProducerType type;

    /**
     * constructor
     * <p/>
     * @param type the type of border producer to be created
     */
    public BorderProducerBuilder(BorderProducerType type) {
        this.type = type;
        this.colorRange = new ColorRangeRGBA(0);
        this.thickness = 1;
    }

    public BorderProducerBuilder setColorRange(ColorRangeRGBA colorRange) {
        this.colorRange = colorRange;
        return this;
    }

    public BorderProducerBuilder setThickness(int thickness) {
        this.thickness = thickness;
        return this;
    }

    @Override
    public BorderProducer create() {
        switch (type) {
            case SOLID:
                return new SolidBorderProducer(colorRange, thickness);
            default:
                throw new IllegalArgumentException(
                        "Border producer not found: " + type.name());
        }
    }
}
