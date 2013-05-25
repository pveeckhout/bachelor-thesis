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
import java.awt.image.BufferedImage;

import java.awt.Graphics2D;


/**
 * StretchGimpyRenderer.java (UTF-8)
 *
 * generates fisheye transformations
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.6
 * @version 1.1.0
 */
public class FishEyeGimpyRenderer extends AbstractGimpyRenderer {

    public FishEyeGimpyRenderer(double d1, double d2, ColorRangeRGBA colorRange1, ColorRangeRGBA colorRange2) {
        super(d1, d2, colorRange1, colorRange2);
    }

    @Override
    public void gimp(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();

        int hstripes = (int) (height / d1);
        int vstripes = (int) (width / d2);

        // Calculate space between lines
        int hspace = height / (hstripes + 1);
        int vspace = width / (vstripes + 1);

        Graphics2D graph = (Graphics2D) image.getGraphics();
        // Draw the horizontal stripes
        for (int i = hspace; i < height; i = i + hspace) {
            graph.setColor(colorRange1.getRandomColorInRange());
            graph.drawLine(0, i, width, i);
        }

        // Draw the vertical stripes
        for (int i = vspace; i < width; i = i + vspace) {
            graph.setColor(colorRange2.getRandomColorInRange());
            graph.drawLine(i, 0, i, height);
        }

        // Create a pixel array of the original image.
        // we need this later to do the operations on..
        int pix[] = new int[height * width];
        int j = 0;

        for (int j1 = 0; j1 < width; j1++) {
            for (int k1 = 0; k1 < height; k1++) {
                pix[j] = image.getRGB(j1, k1);
                j++;
            }
        }

        double distance = ranInt(width / 4, width / 3);

        // put the distortion in the (dead) middle
        int wMid = image.getWidth() / 2;
        int hMid = image.getHeight() / 2;

        // again iterate over all pixels..
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                int relX = x - wMid;
                int relY = y - hMid;

                double d1 = Math.sqrt(relX * relX + relY * relY);
                if (d1 < distance) {

                    int j2 = wMid
                            + (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (x - wMid));
                    int k2 = hMid
                            + (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (y - hMid));
                    image.setRGB(x, y, pix[j2 * height + k2]);
                }
            }
        }

        graph.dispose();
    }

    private final int ranInt(int i, int j) {
        double d = Math.random();
        return (int) (i + ((j - i) + 1) * d);
    }

    private final double fishEyeFormula(double s) {
        // implementation of:
        // g(s) = - (3/4)s3 + (3/2)s2 + (1/4)s, with s from 0 to 1.
        if (s < 0.0D) {
            return 0.0D;
        }
        if (s > 1.0D) {
            return s;
        }

        return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
    }
}
