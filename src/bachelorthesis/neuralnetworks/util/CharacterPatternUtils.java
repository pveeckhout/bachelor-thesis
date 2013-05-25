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

import java.util.Arrays;

/**
 * CharacterPatternUtils.java (UTF-8)
 *
 * Utility class for operations concerning network training and testing.
 *
 * 2013/05/20
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class CharacterPatternUtils {

    /**
     * converts a character to an array of doubles, each double in the array
     * represents a bit from the byte defining the character
     *
     * @param c the character
     * <p/>
     * @return an array of doubles representing the char
     */
    public static double[] characterToBitArray(char c) {
        String bitString = Integer.toBinaryString((int) c);
        System.err.println(c + " bitstring: " + bitString);

        // leftpad the string with 0 so it is atleast 8 bit long;
        while (bitString.length() < 8) {
            bitString = "0" + bitString;
        }

        double bit = 0;
        double[] result = new double[8];
        int resultIndex = 7;

        for (int i = result.length - 1; i > 0; i--) {
            if (bitString.charAt(i) == '1') {
                bit = 1;
            } else {
                bit = 0;
            }
            result[resultIndex--] = bit;
        }

        System.err.println(c + " bitArray: " + Arrays.toString(result));
        return result;
    }
}
