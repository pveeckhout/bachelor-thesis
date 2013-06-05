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
package bachelorthesis.ocr.domain;

import bachelorthesis.captchabuilder.elementcreator.renderer.text.AbstractWordRenderer;
import bachelorthesis.captchabuilder.elementcreator.renderer.text.DefaultWordRenderer;
import bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRenderer;
import bachelorthesis.captchabuilder.util.ColorRangeContainer;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import bachelorthesis.ocr.util.CharacterPatternUtils;
import bachelorthesis.ocr.util.ImageToInputPattern;
import bachelorthesis.neuralnetworks.util.TrainingSet;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * TrainingSet.java (UTF-8)
 *
 * class that stores the input and output training patterns for neural network
 * training.
 *
 * 2013/06/04
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class CharactersTrainingSet extends TrainingSet {

    private char[] chars;
    private int height, width;

    public CharactersTrainingSet(char[] chars, int height, int width) {
        super(width*height, 8);
        this.chars = chars;
        super.setTrainingSetCount(chars.length);
    }

    @Override
    public void setTrainingSetCount(int trainingSetCount) {
        throw new UnsupportedOperationException("trianingSetCount is now "
                + "defined from the input character array");
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
        setInput(new double[chars.length][super.getInputCount()]);
    }
    
    public char getChar(int set) throws RuntimeException {
        if ((set < 0) || (set >= getTrainingSetCount())) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        return chars[set];
    }
    
    public void setChar (int set, char character) throws RuntimeException {
        if ((set < 0) || (set >= getTrainingSetCount())) {
            throw (new RuntimeException("Training set out of range:" + set));
        }
        this.chars[set] = character;
    }

    public void buildSet() {
        BufferedImage img;
        WordRenderer renderer = new DefaultWordRenderer(new ColorRangeContainer(0, 0,
                0,
                255),
                AbstractWordRenderer.DEFAULT_FONTS,
                0, 0.25,
                CaptchaConstants.DEFAULT_STROKE_WIDTH);
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            
            img = new BufferedImage(40, 50, BufferedImage.TYPE_INT_ARGB);
            renderer.render(String.valueOf(c), img);

            // check if size == the default size (40*50) if not scale
            if (width != 40 || height != 50) {
                BufferedImage resized = new BufferedImage(width, height, img
                        .getType());
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                   RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(), img
                        .getHeight(), null);
                g.dispose();

                //replace the origal with the resized
                img = resized;
                
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
                        "-" + width + "X" + height + ".png"));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

            setInputSet(i, ImageToInputPattern
                    .colorRangeToDoubleInputPattern(img, 0, 0));
            setOutputSet(i, CharacterPatternUtils
                    .characterToBitArray(c));
            }
        }
    }
}
