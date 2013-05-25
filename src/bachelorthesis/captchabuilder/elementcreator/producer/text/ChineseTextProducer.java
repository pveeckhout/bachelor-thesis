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
package bachelorthesis.captchabuilder.elementcreator.producer.text;

/**
 * ChineseTextProducer.java (UTF-8)
 *
 * generates Chinese tokens
 *
 * 2013/04/14
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.2
 * @version 1.0.7
 */
public class ChineseTextProducer extends AbstractTextProducer {

    /**
     * constructor
     * <p/>
     * @param minLenght the minimum text length
     * @param maxLenght the maximum text length
     */
    public ChineseTextProducer(int minLenght, int maxLenght) {
        super(buildChineseCharset(), minLenght, maxLenght);
    }

    private static char[] buildChineseCharset() {
        // Here's hoping none of the characters in this range are offensive.
        int CODE_POINT_START = 0x4E00;
        int CODE_POINT_END = 0x4F6F;
        int NUM_CHARS = CODE_POINT_END - CODE_POINT_START;
        char[] CHARS;

        CHARS = new char[NUM_CHARS];
        for (char c = (char) CODE_POINT_START, i = 0; c < CODE_POINT_END; c++, i++) {
            CHARS[i] = Character.valueOf(c);
        }

        return CHARS;
    }
}