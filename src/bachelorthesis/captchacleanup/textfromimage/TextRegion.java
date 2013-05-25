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
package bachelorthesis.captchacleanup.textfromimage;

/**
 * TextRegion.java (UTF-8)
 *
 * generates a text area
 *
 * 2013/05/20
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class TextRegion {

    int x1;
    int y1;
    int x2;
    int y2;
    double mass;

    /**
     * Creates a new
     * <code>TextRegion</code> instance.
     *
     * @param xs   the start x coordinate
     * @param ys   the start y coordinate
     * @param xe   the end x coordinate
     * @param ye   the end y coordinate
     * @param maxx the max x coordinate
     * @param maxy the max y coordinate
     */
    public TextRegion(int xs, int ys, int xe, int ye, int maxx, int maxy,
                      double m) {
        if (xs < 0) {
            x1 = 0;
        } else if (xs > maxx) {
            x1 = maxx;
        } else {
            x1 = xs;
        }
        if (xe < 0) {
            x2 = 0;
        } else if (xe > maxx) {
            x2 = maxx;
        } else {
            x2 = xe;
        }
        if (ys < 0) {
            y1 = 0;
        } else if (ys > maxy) {
            y1 = maxy;
        } else {
            y1 = ys;
        }
        if (ye < 0) {
            y2 = 0;
        } else if (ye > maxy) {
            y2 = maxy;
        } else {
            y2 = ye;
        }
        mass = m;
    }

    int area() {
        return width() * height();
    }

    int height() {
        return y2 - y1;
    }

    int width() {
        return x2 - x1;
    }

    double density() {
        return mass / area();
    }

    double aspect() {
        return (double) height() / (double) width();
    }
}
