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

import java.util.Arrays;

/**
 * ArrayUtil.java (UTF-8)
 *
 * utility for array operations
 *
 * 2013/04/15
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.2
 * @version 1.0.2
 */
public abstract class ArrayUtil<T> {

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param <T>   the class of the objects inside the arrays.
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] concat(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static char[] concat(char[] first, char[]... rest) {
        int totalLength = first.length;
        for (char[] array : rest) {
            totalLength += array.length;
        }
        char[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (char[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static int[] concat(int[] first, int[]... rest) {
        int totalLength = first.length;
        for (int[] array : rest) {
            totalLength += array.length;
        }
        int[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (int[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static double[] concat(double[] first, double[]... rest) {
        int totalLength = first.length;
        for (double[] array : rest) {
            totalLength += array.length;
        }
        double[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (double[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static float[] concat(float[] first, float[]... rest) {
        int totalLength = first.length;
        for (float[] array : rest) {
            totalLength += array.length;
        }
        float[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (float[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static byte[] concat(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static short[] concat(short[] first, short[]... rest) {
        int totalLength = first.length;
        for (short[] array : rest) {
            totalLength += array.length;
        }
        short[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (short[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static long[] concat(long[] first, long[]... rest) {
        int totalLength = first.length;
        for (long[] array : rest) {
            totalLength += array.length;
        }
        long[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (long[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Conactenates the arrays past as arguments.
     * <p/>
     * @param first the first array
     * @param rest  the following arrays
     * <p/>
     * @return a new array comprising the ones passed as arguments
     */
    public static boolean[] concat(boolean[] first, boolean[]... rest) {
        int totalLength = first.length;
        for (boolean[] array : rest) {
            totalLength += array.length;
        }
        boolean[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (boolean[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
