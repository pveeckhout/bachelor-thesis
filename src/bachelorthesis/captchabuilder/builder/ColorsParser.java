/*
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
package bachelorthesis.captchabuilder.builder;

import bachelorthesis.captchabuilder.util.ColorRangeRGBA;
import bachelorthesis.captchabuilder.util.ImageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * ColorsParser.java (UTF-8)
 *
 * Parses the string arguments for rendering colors
 *
 * 2013/04/18
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.8
 * @version 1.1.0
 */
public class ColorsParser {

    /**
     * Returns a ColorRangRGBA based on string arguments
     * <p/>
     * @param colorArgs the string arguments
     * <p/>
     * @return a ColorRangRGBA object
     * <p/>
     * @throws ParseException
     * @see ColorRangeRGBA
     */
    public static ColorRangeRGBA parse(String[] colorArgs) throws ParseException {

        System.out.println("parsing colors option: " + Arrays.deepToString(
                colorArgs));
        ColorOptions colorOptionType = ColorOptions.valueOf(colorArgs[0]);

        switch (colorOptionType) {
            case RANGE:
                if (colorArgs.length != 3) {
                    throw new ParseException(
                            "Colors range Option only takes 2 argumenst");
                }
                String startHex = "#" + colorArgs[1].toUpperCase();
                String endHex = "#" + colorArgs[2].toUpperCase();
                return new ColorRangeRGBA(ImageUtil.hexadecimalToRGBa(startHex),
                                          ImageUtil.hexadecimalToRGBa(endHex));
            case LIST:
                if (colorArgs.length < 2) {
                    throw new ParseException(
                            "Colors list Option takes at least 2 argumenst");
                }
                ArrayList<String> hexList = new ArrayList<>();
                for (int i = 1; i < colorArgs.length; i++) {
                    String colorHex = "#" + colorArgs[i].toUpperCase();
                    hexList.add(colorHex);
                }

                return new ColorRangeRGBA(hexList);
            default:
                throw new ParseException("Colors option not found: " +
                        colorOptionType.name());
        }
    }

    enum ColorOptions {

        RANGE,
        LIST;
    }
}
