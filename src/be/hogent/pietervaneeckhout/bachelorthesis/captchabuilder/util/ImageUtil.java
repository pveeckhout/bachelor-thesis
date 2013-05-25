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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

/**
 * ImageUtil.java (UTF-8)
 *
 * Uitl class for image operations
 *
 * 2013/04/15
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.6
 * @version 1.0.8
 */
public class ImageUtil {

    /**
     * applies a filter to an image
     * 
     * @param img the image
     * @param filter the filter
     */
    public static final void applyFilter(BufferedImage img, ImageFilter filter) {
        FilteredImageSource src = new FilteredImageSource(img.getSource(), filter);
        Image fImg = Toolkit.getDefaultToolkit().createImage(src);
        Graphics2D g = img.createGraphics();
        g.drawImage(fImg, 0, 0, null, null);
        g.dispose();
    }

    /**
     * converts colour format
     * 
     * @param r a colour's red value
     * @param g a colour's green value
     * @param b a colour's blue value
     * @param a a colour's alpha value
     * @return the colour in MSAccess format
     */
    public static final int rgbaToMsAcces(int r, int g, int b, int a) {
         Color c = new Color(r, g, b, a);
        return c.getRGB();
    }

    /**
     * converts colour format
     * 
     * @param r a colour's red value
     * @param g a colour's green value
     * @param b a colour's blue value
     * @return the colour in MSAccess format
     */
    public static final int rgbToMsAcces(int r, int g, int b) {
        return rgbaToMsAcces(r, g, b, 0);
    }

    /**
     * converts colour format
     * 
     * @param code @return the colour in MSAccess format
     * @return an array containing the RGBa values
     */
    public static final int[] msAccesToRGBa(int code) {
        Color c = new Color(code);
        return colorToRGBa(c);
    }

    /**
     * converts colour format
     * 
     * @param hex @return the colour in hexadecimal format
     * @return an array containing the RGBa values
     */
    public static int[] hexadecimalToRGBa(String hex) {
        Color c = Color.decode(hex);
        return colorToRGBa(c);
    }

    private static int[] colorToRGBa(Color c) {
        int[] rgba = new int[4];

        rgba[0] = c.getRed();
        rgba[1] = c.getGreen();
        rgba[2] = c.getBlue();
        rgba[3] = c.getAlpha();

        return rgba;
    }
}
