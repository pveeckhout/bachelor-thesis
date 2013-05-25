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
package bachelorthesis.captchabuilder.util;

import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * ColorRangeRGBA.java (UTF-8)
 *
 * usage and functionality here
 *
 * 2013/04/19
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.1.0
 * @version 1.1.0
 */
public class ColorRangeRGBA {

    private final int startR;
    private final int endR;
    private final int startG;
    private final int endG;
    private final int startB;
    private final int endB;
    private final int startA;
    private final int endA;
    private Random random;
    private boolean listMode;
    private List<String> hexList;

    /**
     * Constructor
     * <p/>
     * @param MSa a colour in MSaccess format
     */
    public ColorRangeRGBA(int MSa) {
        this(MSa, MSa);
    }

    /**
     * constructor
     * <p/>
     * @param hexList a list of colours in hexadecimal form
     */
    public ColorRangeRGBA(List<String> hexList) {
        this(0);
        this.listMode = true;
        this.hexList = hexList;
    }

    /**
     * constructor
     * <p/>
     * @param rgba a collection of colours in RGBA format
     */
    public ColorRangeRGBA(int[] rgba) {
        this(rgba, rgba);
    }

    /**
     * Constructor
     * <p/>
     * @param r a colour's red value
     * @param g a colour's green value
     * @param b a colour's blue value
     */
    public ColorRangeRGBA(int r, int g, int b) {
        this(r, g, b, 0);
    }

    /**
     * constructor
     * <p/>
     * @param r a colour's red value
     * @param g a colour's green value
     * @param b a colour's blue value
     * @param a a colour's alpha value
     */
    public ColorRangeRGBA(int r, int b, int g, int a) {
        this(r, r, g, g, g, g, a, a);
    }

    /**
     * constructor
     * <p/>
     * @param startRGBa the start of a colour range in RGBa format
     * @param endRGBa   the end of a colour range in RGBa format
     */
    public ColorRangeRGBA(int[] startRGBa, int[] endRGBa) {
        this(startRGBa[0], endRGBa[0], startRGBa[1], endRGBa[1], startRGBa[2],
             endRGBa[2], startRGBa[3], endRGBa[3]);
    }

    /**
     * constructor
     * <p/>
     * @param startMSa the start of a colour range in MSAcces format
     * @param endMSa   the start of a colour range in MSAcces format
     */
    public ColorRangeRGBA(int startMSa, int endMSa) {
        this(ImageUtil.msAccesToRGBa(startMSa), ImageUtil.msAccesToRGBa(endMSa));
    }

    /**
     * constructor
     * <p/>
     * @param startR the start of a colour range red value
     * @param endR   the end of a colour range red value
     * @param startG the start of a colour range green value
     * @param endG   the end of a colour range green value
     * @param startB the start of a colour range blue value
     * @param endB   the end of a colour range blue value
     * @param startA the start of a colour range alpha value
     * @param endA   the end of a colour range red value
     */
    public ColorRangeRGBA(int startR, int endR, int startG, int endG, int startB,
                          int endB, int startA, int endA) {
        this.random = CaptchaConstants.RANDOM;
        this.startR = startR;
        this.endR = endR;
        this.startG = startG;
        this.endG = endG;
        this.startB = startB;
        this.endB = endB;
        this.startA = startA;
        this.endA = endA;
        this.listMode = false;
    }

    /**
     * picks a random colour in the range and returns it
     * <p/>
     * @return a colour object
     */
    public Color getRandomColorInRange() {
        return new Color(getRandomInRangeR(), getRandomInRangeG(),
                         getRandomInRangeB(), getRandomInRangeA());
    }

    /**
     * picks a random colour in the range and returns it
     * <p/>
     * @return a colour in MSAcces format
     */
    public int getRandomMSaccesInRange() {
        return ImageUtil.rgbToMsAcces(getRandomInRangeR(), getRandomInRangeG(),
                                      getRandomInRangeB());
    }

    private int getRandomInRangeR() {
        if (listMode) {
            return ImageUtil.hexadecimalToRGBa(hexList.get(random.nextInt(
                    hexList.size())))[0];
        } else {
            return random8bitNumber(startR, endR);
        }
    }

    private int getRandomInRangeG() {
        if (listMode) {
            return ImageUtil.hexadecimalToRGBa(hexList.get(random.nextInt(
                    hexList.size())))[1];
        } else {
            return random8bitNumber(startG, endG);
        }
    }

    private int getRandomInRangeB() {
        if (listMode) {
            return ImageUtil.hexadecimalToRGBa(hexList.get(random.nextInt(
                    hexList.size())))[2];
        } else {
            return random8bitNumber(startB, endB);
        }
    }

    private int getRandomInRangeA() {
        if (listMode) {
            return ImageUtil.hexadecimalToRGBa(hexList.get(random.nextInt(
                    hexList.size())))[3];
        } else {
            return random8bitNumber(startA, endA);
        }
    }

    private int random8bitNumber(int start, int end) {
        if (start > end) {
            if (random.nextBoolean()) {
                return random8bitNumber(0, end);
            } else {
                return random8bitNumber(start, 256);
            }
        }
        if (start == end) {
            return start;
        } else {
            return random.nextInt(end - start) + start;
        }
    }
}
