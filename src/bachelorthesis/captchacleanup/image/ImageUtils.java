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
package bachelorthesis.captchacleanup.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * DomainFacade.java (UTF-8)
 *
 * This class will be used a container for static access methods manipulating
 * images
 *
 * 2013/04/23
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class ImageUtils {

    /**
     * sets a colour to transparent
     * <p/>
     * @param bufImage the image
     * @param cString  the colour to set transparent
     * <p/>
     * @return the image with colour set to transparent
     */
    public static BufferedImage setColorTransparent(BufferedImage bufImage,
                                                    String cString) {
        Color c = Color.decode(cString);
        return setColorRangeTransparent(bufImage, c, c);
    }

    /**
     * sets a colour to transparent
     * <p/>
     * @param bufImage the image
     * @param cInt     the colour to set transparent in MSA format
     * <p/>
     * @return the image with colour set to transparent
     */
    public static BufferedImage setColorTransparent(BufferedImage bufImage,
                                                    int cInt) {
        Color c = new Color(cInt);
        return setColorRangeTransparent(bufImage, c, c);
    }

    /**
     * sets a colour to transparent
     * <p/>
     * @param bufImage the image
     * @param c1       the colour to set transparent
     * @param c2       the colour to set transparent
     * <p/>
     * @return the image with colour set to transparent
     */
    public static BufferedImage setColorRangeTransparent(BufferedImage bufImage,
                                                         String c1, String c2) {
        return setColorRangeTransparent(bufImage, Color.decode(c1), Color
                .decode(c2));
    }

    /**
     * sets a colour to transparent
     * <p/>
     * @param bufImage the image
     * @param c1       the start colour to set transparent in MSA format
     * @param c2       the end colour to set transparent in MSA format
     * <p/>
     * @return the image with colour set to transparent
     */
    public static BufferedImage setColorRangeTransparent(BufferedImage bufImage,
                                                         int c1, int c2) {
        return setColorRangeTransparent(bufImage, new Color(c1), new Color(c2));
    }

    /**
     * sets a colour to transparent
     * <p/>
     * @param bufImage the image
     * @param c1       the colour to set transparent
     * @param c2       the colour to set transparent
     * <p/>
     * @return the image with colour set to transparent
     */
    public static BufferedImage setColorRangeTransparent(BufferedImage bufImage,
                                                         Color c1, Color c2) {
        // Primitive test, just an example
        final int r1 = c1.getRed();
        final int g1 = c1.getGreen();
        final int b1 = c1.getBlue();
        final int r2 = c2.getRed();
        final int g2 = c2.getGreen();
        final int b2 = c2.getBlue();
        ImageFilter filter = new RGBImageFilter() {
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                Color c = new Color(rgb);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                if (r >= r1 && r <= r2 && g >= g1 && g <= g2 && b >= b1 && b <=
                        b2) {
                    // Set fully transparent but keep color
                    return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(bufImage.getSource(), filter);
        Image image = Toolkit.getDefaultToolkit().createImage(ip);

        bufImage = new BufferedImage(bufImage.getWidth(), bufImage.getHeight(),
                                     BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufImage;
    }
}
