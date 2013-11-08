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
package bachelorthesis.ocr;

import bachelorthesis.ocr.domain.DomainFacade;
import javax.swing.JOptionPane;

/**
 * OCR.java (UTF-8)
 *
 * Startup class for the OCR testing
 *
 * 2013/06/05
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class OCR {

    /**
     * Main
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new OCR();
    }

    public OCR() {
        DomainFacade dom = new DomainFacade();
        String input = null;
        boolean end = false;

        while (!end) {
            input = JOptionPane.showInputDialog(null, "Plesae pick the network type:\n1: Hopfield\n2: Kohonen\n3: Perceptron\n\nexit: stop");

            switch (input) {
                case "1":
                    dom.hopfield();
                    end = true;
                    break;
                case "2":
                    dom.kohonen();
                    end = true;
                    break;
                case "3":
                    dom.perceptron();
                    end = true;
                    break;
                case "exit":
                    end = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "not a valid option");
            }
        }
    }
}
