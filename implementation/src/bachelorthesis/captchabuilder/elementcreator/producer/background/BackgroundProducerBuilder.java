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
package bachelorthesis.captchabuilder.elementcreator.producer.background;

import bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import bachelorthesis.captchabuilder.util.ColorRangeContainer;
import bachelorthesis.captchabuilder.util.enums.producer.BackgroundProducerType;

/**
 * BackgroundProducerBuilder.java (UTF-8)
 *
 * Builder for a background producer
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.4
 * @version 1.1.0
 */
public class BackgroundProducerBuilder implements CaptchaElementCreatorBuilder {

    private ColorRangeContainer colorRange1;
    private ColorRangeContainer colorRange2;
    private BackgroundProducerType type;

    /**
     * Constructor
     * <p/>
     * @param type the type of BackgroundProducer to be created
     */
    public BackgroundProducerBuilder(BackgroundProducerType type) {
        this.type = type;

        switch (type) {
            case FLATCOLOR:
                colorRange1 = new ColorRangeContainer(222, 222, 222);
                colorRange2 = new ColorRangeContainer(222, 222, 222);
                break;
            case SQUIGGLES:
                colorRange1 = new ColorRangeContainer(0);
                colorRange2 = new ColorRangeContainer(0);
                break;
            case TRANSPARENT:
                colorRange1 = new ColorRangeContainer(255, 255, 255);
                colorRange2 = new ColorRangeContainer(255, 255, 255);
                break;
            case TWOCOLORGRADIENT:
                colorRange1 = new ColorRangeContainer(0, 0, 255);
                colorRange2 = new ColorRangeContainer(0, 255, 0);
                break;
            default:
                colorRange1 = new ColorRangeContainer(211, 211, 211);
                colorRange2 = new ColorRangeContainer(169, 169, 169);
        }
    }

    public BackgroundProducerBuilder setColorRange1(ColorRangeContainer colorRange1) {
        this.colorRange1 = colorRange1;
        return this;
    }

    public BackgroundProducerBuilder setColorRange2(ColorRangeContainer colorRange2) {
        this.colorRange2 = colorRange2;
        return this;
    }

    @Override
    public BackgroundProducer create() {
        switch (type) {
            case FLATCOLOR:
                return new FlatColorBackgroundProducer(colorRange1, colorRange2);
            case SQUIGGLES:
                return new SquigglesBackgroundProducer(colorRange1, colorRange2);
            case TRANSPARENT:
                return new TransparentBackgroundProducer(colorRange1,
                                                         colorRange2);
            case TWOCOLORGRADIENT:
                return new TwoColorGradientBackgroundProducer(colorRange1,
                                                              colorRange2);
            default:
                throw new IllegalArgumentException(
                        "Background producer not found: " + type.name());
        }
    }
}
