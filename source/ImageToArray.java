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
package bachelorthesis.captchacleanup;

import java.awt.image.BufferedImage;

/**
 * ImageToArray.java (UTF-8)
 *
 * Utility class images
 *
 * 2013/05/20
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class ImageToArray {

    /**
     * extracts the image data, all pixels within the colour range return true
     *
     * @param image      the image to be analysed
     * @param startRange the start colour in MSA format
     * @param endRange   the end colour in MSA format
     * <p/>
     * @return an array with the boolean data
     */
    public static boolean[][] colorRangeToBooleanArray(BufferedImage image,
                                                       int startRange,
                                                       int endRange) {
        boolean[][] array = new boolean[image.getWidth()][image.getHeight()];
        int startR = (startRange >> 16) & 0x000000FF;
        int startG = (startRange >> 8) & 0x000000FF;
        int startB = (startRange) & 0x000000FF;
        int endR = (endRange >> 16) & 0x000000FF;
        int endG = (endRange >> 8) & 0x000000FF;
        int endB = (endRange) & 0x000000FF;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int RGB = image.getRGB(x, y);
                int alpha = (RGB >> 24) & 0x000000FF;
                boolean inRange = false;
                if (alpha != 0) {
                    int R = (startRange >> 16) & 0x000000FF;
                    int G = (startRange >> 8) & 0x000000FF;
                    int B = (startRange) & 0x000000FF;
                    if (startR <= R && R <= endR && startG <= G && G <= endG &&
                            startB <= B && B <= endB) {
                        inRange = true;
                    }
                }
                array[x][y] = inRange;
            }
        }
        return array;

    }

    /**
     * extracts the image data, all pixels within the colour range return 1, the
     * others return 0
     *
     * @param image      the image to be analysed
     * @param startRange the start colour in MSA format
     * @param endRange   the end colour in MSA format
     * <p/>
     * @return an array with the double data
     */
    public static double[][] colorRangeToDoubleArray(BufferedImage image,
                                                     int startRange,
                                                     int endRange, double inRangeValue, double outRangeValue) {
        double[][] array = new double[image.getWidth()][image.getHeight()];
        int startR = (startRange >> 16) & 0x000000FF;
        int startG = (startRange >> 8) & 0x000000FF;
        int startB = (startRange) & 0x000000FF;
        int endR = (endRange >> 16) & 0x000000FF;
        int endG = (endRange >> 8) & 0x000000FF;
        int endB = (endRange) & 0x000000FF;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int RGB = image.getRGB(x, y);
                int alpha = (RGB >> 24) & 0x000000FF;
                if (alpha != 0) {
                    int R = (startRange >> 16) & 0x000000FF;
                    int G = (startRange >> 8) & 0x000000FF;
                    int B = (startRange) & 0x000000FF;
                    if (startR <= R && R <= endR && startG <= G && G <= endG &&
                            startB <= B && B <= endB) {
                        array[x][y] = inRangeValue;
                    } else {
                        array[x][y] = outRangeValue;
                    }
                }
            }
        }
        return array;
    }
}
