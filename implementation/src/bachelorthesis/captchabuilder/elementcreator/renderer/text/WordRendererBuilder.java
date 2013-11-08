/*
 * The MIT License
 *
 * Copyright 2013 piva.
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
package bachelorthesis.captchabuilder.elementcreator.renderer.text;

import bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import bachelorthesis.captchabuilder.util.ColorRangeContainer;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import bachelorthesis.captchabuilder.util.enums.renderer.WordRendererType;
import java.awt.Font;
import java.util.List;

/**
 * WordRendererBuilder.java (UTF-8)
 *
 * Builder for a word style renderer
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.1.0
 */
public class WordRendererBuilder implements CaptchaElementCreatorBuilder {

    private ColorRangeContainer colorRange;
    private List<Font> fonts;
    private double xOffset;
    private double yOffset;
    private float strokeWidth;
    private WordRendererType type;

    public WordRendererBuilder(WordRendererType type) {
        this.strokeWidth = CaptchaConstants.DEFAULT_STROKE_WIDTH;
        this.yOffset = CaptchaConstants.DEFAULT_YOFFSET;
        this.xOffset = CaptchaConstants.DEFAULT_XOFFSET;
        this.fonts = AbstractWordRenderer.DEFAULT_FONTS;
        this.colorRange = AbstractWordRenderer.DEFAULT_COLOR_RANGE;
        this.type = type;
    }

    public WordRendererBuilder setColorRange(ColorRangeContainer colorRange) {
        this.colorRange = colorRange;
        return this;
    }

    public WordRendererBuilder setFonts(List<Font> fonts) {
        this.fonts = fonts;
        return this;
    }

    public WordRendererBuilder setXOffset(double xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public WordRendererBuilder setYOffset(double yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public WordRendererBuilder setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    @Override
    public WordRenderer create() {
        switch (type) {
            case DEFAULT:
                return new DefaultWordRenderer(colorRange, fonts, xOffset,
                                               yOffset, strokeWidth);
            case COLOREDEDGES:
                return new ColoredEdgesWordRenderer(colorRange, fonts, xOffset,
                                                    yOffset, strokeWidth);
            default:
                throw new IllegalArgumentException("WordRenderer not found: " +
                        type.name());
        }
    }
}
