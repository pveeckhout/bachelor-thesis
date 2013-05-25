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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.gimpy;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.ColorRangeRGBA;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.util.Random;

/**
 * ShearGimpyRenderer.java (UTF-8)
 *
 * generates shear transformations
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.6
 * @version 1.1.0
 */
public class ShearGimpyRenderer extends AbstractGimpyRenderer {
    
    private Random random;

    public ShearGimpyRenderer(double d1, double d2, ColorRangeRGBA colorRange1, ColorRangeRGBA colorRange2) {
        super(d1, d2, colorRange1, colorRange2);
        this.random = CaptchaConstants.RANDOM;
    }

    @Override
    public void gimp(BufferedImage bi) {
        Graphics2D g = bi.createGraphics();
        shearX(g, bi.getWidth(), bi.getHeight());
        shearY(g, bi.getWidth(), bi.getHeight());
        g.dispose();
    }

    private void shearX(Graphics2D g, int w1, int h1) {
        
        int period = random.nextInt(10) + 5;

        boolean borderGap = true;
        int frames = 15;
        int phase = random.nextInt(5) + 2;

        for (int i = 0; i < h1; i++) {
            double d = (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(colorRange1.getRandomColorInRange());
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private void shearY(Graphics2D g, int w1, int h1) {
        int period = random.nextInt(30) + 10; // 50;

        boolean borderGap = true;
        int frames = 15;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (period >> 1)
                    * Math.sin((float) i / period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(colorRange1.getRandomColorInRange());
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
}
