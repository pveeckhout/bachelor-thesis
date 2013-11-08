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
package bachelorthesis.captchabuilder.elementcreator.renderer.text;

import bachelorthesis.captchabuilder.util.ColorRangeContainer;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractWordRenderer.java (UTF-8)
 *
 * Abstract class template, implementing classes will generate word styles.
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.1.0
 */
public abstract class AbstractWordRenderer implements WordRenderer {

    public static final ColorRangeContainer DEFAULT_COLOR_RANGE;
    public static final List<Font> DEFAULT_FONTS = new ArrayList<>();

    static {
        DEFAULT_COLOR_RANGE = new ColorRangeContainer(0);
        DEFAULT_FONTS.add(new Font("Arial", Font.BOLD, 40));
//        DEFAULT_FONTS.add(new Font("Courier", Font.BOLD, 40));
    }
    protected ColorRangeContainer colorRange;
    protected List<Font> fonts;
    private double xOffset;
    private double yOffset;
    protected float strokeWidth;
    protected Graphics2D g;
    protected FontRenderContext frc;

    /**
     * Build a
     * <code>WordRenderer</code> using the given
     * <code>Color</code>s and
     * <code>Font</code>s.
     *
     * @param colorRange
     * @param fonts
     */
    public AbstractWordRenderer(ColorRangeContainer colorRange, List<Font> fonts,
                                double xOffset, double yOffset,
                                float strokeWidth) {
        this.colorRange = colorRange;
        this.fonts = fonts;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.strokeWidth = strokeWidth;
    }

    /**
     * Render a word onto a BufferedImage.
     *
     * @param word  The word to be rendered.
     * @param image The BufferedImage onto which the word will be painted.
     */
    protected void preRender(BufferedImage image) {
        g = image.createGraphics();

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                                     RenderingHints.VALUE_RENDER_QUALITY));
        g.setRenderingHints(hints);

        frc = g.getFontRenderContext();
    }

    protected int getXBaseline(BufferedImage image) {
        return (int) Math.round(image.getWidth() * xOffset);
    }

    protected int getYBaseline(BufferedImage image) {
        return image.getHeight() - (int) Math.round(image.getHeight() * yOffset);
    }

    protected Font getRandomFont() {
        return (Font) getRandomObject(fonts);
    }

    public Object getRandomObject(List<? extends Object> objs) {
        if (objs.size() == 1) {
            return objs.get(0);
        }

        int i = CaptchaConstants.RANDOM.nextInt(objs.size());
        return objs.get(i);
    }
}
