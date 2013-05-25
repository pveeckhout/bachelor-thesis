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
package bachelorthesis.captchabuilder.elementcreator.renderer.gimpy;

import bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import bachelorthesis.captchabuilder.util.ColorRangeRGBA;
import bachelorthesis.captchabuilder.util.enums.renderer.GimpyRendererType;

/**
 * GimpyRendererBuilder.java (UTF-8)
 *
 * Builder for a transformation renderer
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.6
 * @version 1.1.0
 */
public class GimpyRendererBuilder implements CaptchaElementCreatorBuilder {

    private double d1;
    private double d2;
    private ColorRangeRGBA colorRange1;
    private ColorRangeRGBA colorRange2;
    private GimpyRendererType type;

    public GimpyRendererBuilder(GimpyRendererType type) {
        this.colorRange1 = new ColorRangeRGBA(211, 211, 211);
        this.colorRange2 = new ColorRangeRGBA(169, 169, 169);

        this.d1 = 3.0;
        this.d2 = 75;
        this.type = type;
        if (type.equals(GimpyRendererType.STRETCH)) {
            this.d2 = 3.0;
        }
        if (type.equals(GimpyRendererType.RIPPLE)) {
            this.d1 = 2.6;
            this.d2 = 1.7;
        }
    }

    public GimpyRendererBuilder setD1(double d1) {
        this.d1 = d1;
        return this;
    }

    public GimpyRendererBuilder setD2(double d2) {
        this.d2 = d2;
        return this;
    }

    public GimpyRendererBuilder setColorRange1(ColorRangeRGBA colorRange1) {
        this.colorRange1 = colorRange1;
        return this;
    }

    public GimpyRendererBuilder setColorRange2(ColorRangeRGBA colorRange2) {
        this.colorRange2 = colorRange2;
        return this;
    }

    @Override
    public GimpyRenderer create() {
        switch (type) {
            case BLOCK:
                return new BlockGimpyRenderer(d1, d2, colorRange1, colorRange2);
            case DROPSHADOW:
                return new DropShadowGimpyRenderer(d1, d2, colorRange1,
                                                   colorRange2);
            case FISHEYE:
                return new FishEyeGimpyRenderer(d1, d2, colorRange1, colorRange2);
            case RIPPLE:
                return new RippleGimpyRenderer(d1, d2, colorRange1, colorRange2);
            case SHEAR:
                return new ShearGimpyRenderer(d1, d2, colorRange1, colorRange2);
            case STRETCH:
                return new StretchGimpyRenderer(d1, d2, colorRange1, colorRange2);
            default:
                throw new IllegalArgumentException("GimpyRenderer not found: " +
                        type.name());
        }
    }
}
