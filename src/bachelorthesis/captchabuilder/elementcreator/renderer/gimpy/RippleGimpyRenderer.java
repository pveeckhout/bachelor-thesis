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

import bachelorthesis.captchabuilder.util.ColorRangeRGBA;
import bachelorthesis.captchabuilder.util.ImageUtil;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;
import java.awt.image.BufferedImage;

/**
 * RippleGimpyRenderer.java (UTF-8)
 *
 * generates ripple transformations
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.6
 * @version 1.1.0
 */
public class RippleGimpyRenderer extends AbstractGimpyRenderer {

    public RippleGimpyRenderer(double d1, double d2, ColorRangeRGBA colorRange1,
                               ColorRangeRGBA colorRange2) {
        super(d1, d2, colorRange1, colorRange2);
    }

    @Override
    public void gimp(BufferedImage image) {
        RippleFilter filter = new RippleFilter();
        filter.setWaveType(RippleFilter.SINGLEFRAME);
        filter.setXAmplitude(d1);
        filter.setYAmplitude(d2);
        filter.setXWavelength((5.77) * d1);
        filter.setYWavelength((2.94) * d2);

        filter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

        ImageUtil.applyFilter(image, filter);
    }
}
