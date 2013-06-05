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
package bachelorthesis.ocr.util;

import bachelorthesis.captchacleanup.ImageToArray;
import java.awt.image.BufferedImage;
import org.encog.ml.data.specific.BiPolarNeuralData;

/**
 * ImageToInputPattern.java (UTF-8)
 *
 * Utility class to convert an Image to a usable pattern for input to a network
 * This will reduce a 2-dimensional image to 1-dimensional array of doubles
 *
 * 2013/05/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class ImageToInputPattern {

    /**
     * reduce a 2-dimensional image to 1-dimensional array of doubles based on
     * the colour range supplied.
     * <p/>
     * @param img        the image to be transformed
     * @param startRange the numerical (!NOT HEX) value of the range start
     *                   (inclusive)
     * @param endRange   the numerical (!NOT HEX) value of the range end
     *                   (inclusive)
     * <p/>
     * @return the neural network input pattern based on the image.
     */
    public static double[] colorRangeToDoubleInputPattern(BufferedImage img,
                                                          int startRange,
                                                          int endRange) {
        return reduceDimension(ImageToArray.colorRangeToDoubleArray(img,
                                                                    startRange,
                                                                    endRange));
    }

    private static double[] reduceDimension(double[][] data) {
        int resultIndex = 0;
        double[] result = new double[data.length * data[0].length];
        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                result[resultIndex++] = data[x][y];
            }
        }
        return result;
    }

    /**
     * converts an image into BiPolarNeuralData based on a colour range
     * <p/>
     * @param img        the image to be transformed
     * @param startRange the start colour range in MSA format
     * @param endRange   the end colour range in MSA format
     * <p/>
     * @return BiPolarNeuralData
     * <p/>
     * @see BiPolarNeuralData
     */
    public static BiPolarNeuralData colorRangeToBiPolarNeuralData(
            BufferedImage img, int startRange, int endRange) {
        return booleanArrayToBiPolarNeuralData(ImageToArray
                .colorRangeToBooleanArray(img, startRange, endRange));
    }

    private static BiPolarNeuralData booleanArrayToBiPolarNeuralData(
            boolean[][] data) {
        int resultIndex = 0;
        int width = data.length;
        int height = data[0].length;
        BiPolarNeuralData result = new BiPolarNeuralData(width * height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setData(resultIndex++, data[x][y]);
            }
        }
        return result;
    }
}
