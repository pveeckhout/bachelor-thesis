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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums;

import java.security.SecureRandom;
import java.util.Random;

/**
 * CaptchaConstants.java (UTF-8)
 *
 * Class to contain some of the constants
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.7
 * @version 1.0.7
 */
public class CaptchaConstants {

    public static final Random RANDOM = new SecureRandom();
    public static final char[] LETTERS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final char[] NUMBERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final char[] SPECIAL = new char[]{'&', '!', '@', '?', '#', '$', '%', '+', '='};
    public static final char[] REDUCEDALPHANUMERIC = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y', '2', '3', '4', '5', '6', '7', '8',};
    public static final char[] ARABIC_CHARS = {'\u0627', '\u0628', '\u062a', '\u062b', '\u062c', '\u062d', '\u062e', '\u062f', '\u0630', '\u0631', '\u0632', '\u0633', '\u0634', '\u0635', '\u0636', '\u0637', '\u0638', '\u0639', '\u063a', '\u0641', '\u0642', '\u0643', '\u0644', '\u0645', '\u0646', '\u0647', '\u0648', '\u064a'};
    public static final int DEFAULT_LENGTH = 5;
    public static final double DEFAULT_YOFFSET = 0.25;
    public static final double DEFAULT_XOFFSET = 0.05;
    public static final float DEFAULT_STROKE_WIDTH = 0f;
    public static final String buildSequencelvl1Delim = "[:]+";
    public static final String buildSequencelvl2Delim = "[!]+";
    public static final String buildSequencelvl3Delim = "[#]+";
    public static final String buildSequencelvl4Delim = "[@]+";
    public static final String buildSequencelvl5Delim = "[*]+";
    public static final String buildSequencelvl6Delim = "[.]+";
    public static final String buildSequencelvl7Delim = "[?]+";
}
