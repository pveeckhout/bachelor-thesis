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

import bachelorthesis.captchabuilder.util.ArrayUtil;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;

/**
 * AbstractTextProducer.java (UTF-8)
 *
 * Abstract class template, implementing classes will generate text.
 *
 * 2013/04/14
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.2
 * @version 1.0.7
 */
public abstract class AbstractTextProducer extends ArrayUtil<Character>
        implements TextProducer {

    private final char[] _srcChars;
    private int _minLength;
    private int _maxLength;

    protected AbstractTextProducer(char[] chars, int minLenght, int maxLenght) {
        _minLength = minLenght;
        _maxLength = maxLenght;
        _srcChars = chars;
    }

    @Override
    public String getText() {
        String capText = "";
        int _length = Math.max(_minLength, CaptchaConstants.RANDOM.nextInt(
                _maxLength));
        for (int i = 0; i < _length; i++) {
            capText += _srcChars[CaptchaConstants.RANDOM.nextInt(
                    _srcChars.length)];
        }

        return capText;
    }

    /*
     * No Longer used
     *
     * private static char[] copyOf(char[] original, int newLength) {
     * char[] copy = new char[newLength];
     * System.arraycopy(original, 0, copy, 0,
     * Math.min(original.length, newLength));
     * return copy;
     * }
     */
    public void setLength(int minLength, int maxLength) {
        if (minLength < 0 || maxLength < minLength) {
            this._minLength = minLength;
        }
        this._maxLength = maxLength;
    }
}
