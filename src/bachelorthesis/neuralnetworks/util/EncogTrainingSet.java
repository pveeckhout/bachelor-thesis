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
package bachelorthesis.neuralnetworks.util;

import bachelorthesis.captchabuilder.elementcreator.renderer.text.AbstractWordRenderer;
import bachelorthesis.captchabuilder.elementcreator.renderer.text.DefaultWordRenderer;
import bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRenderer;
import bachelorthesis.captchabuilder.util.ColorRangeContainer;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * EncogTrainingSet.java (UTF-8)
 *
 * Utility class to help generate the input and output trainingsets for an encog
 * Neural Network.
 *
 * 2013/05/20
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class EncogTrainingSet {

    /**
     * builds the input set from a collection of chars
     * <p/>
     * @param chars the collection of chars to train for
     * @param hSize the width of the char image
     * @param vSize the height of the char image
     * <p/>
     * @return
     */
    public static double[][] buildTrainingInputSet(char[] chars, int hSize,
                                                   int vSize) {
        double[][] inputTrainingsSet = new double[chars.length][];
        System.out.println("building Trainingsets");
        BufferedImage img;
        WordRenderer renderer = new DefaultWordRenderer(new ColorRangeContainer(0, 0,
                                                                           0,
                                                                           255),
                                                        AbstractWordRenderer.DEFAULT_FONTS,
                                                        0, 0.25,
                                                        CaptchaConstants.DEFAULT_STROKE_WIDTH);
        int index = 0;

        for (char c : chars) {
            img = new BufferedImage(40, 50, BufferedImage.TYPE_INT_ARGB);
            renderer.render(String.valueOf(c), img);

            // check if size == the default size (40*50) if not scale
            if (hSize != 40 || vSize != 50) {
                BufferedImage resized = new BufferedImage(hSize, vSize, img
                        .getType());
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                   RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(img, 0, 0, hSize, vSize, 0, 0, img.getWidth(), img
                        .getHeight(), null);
                g.dispose();

                //replace the origal with the resized
                img = resized;
            }

            try {
                String path = "TrainingsetImages/";
                // if the directory does not exist, create it and it's parents
                File theDir = new File(path);
                if (!theDir.exists()) {
                    System.out.println("creating directory: " + path);
                    boolean result = theDir.mkdirs();
                    if (result) {
                        System.out.println("Directory created");
                    }
                }

                ImageIO.write(img, "png", new File(path + Character.getName(c) +
                        "-" + hSize + "X" + vSize + ".png"));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

            inputTrainingsSet[index++] = ImageToInputPattern
                    .colorRangeToDoubleInputPattern(img, 0, 0);
        }

        return inputTrainingsSet;
    }

    /**
     * builds the ideal response set from a collection of chars
     * <p/>
     * @param chars the collection of chars to train for
     * <p/>
     * @return
     */
    public static double[][] buildTrainingIdealSet(char[] chars) {
        double[][] outputTrainingsSet = new double[chars.length][];
        System.out.println("building TrainingIdealSet");
        int index = 0;

        for (char c : chars) {
            outputTrainingsSet[index++] = CharacterPatternUtils
                    .characterToBitArray(c);
        }

        return outputTrainingsSet;
    }
}
