/*
 * The MIT License
 *
 * Copyright 2013 piva.
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
package bachelorthesis.captchapreviewer;

import bachelorthesis.captchapreviewer.domain.Domain;
import bachelorthesis.captchapreviewer.ui.PreviewGUI;

/**
 * CaptchaPreviewer.java (UTF-8)
 *
 * <p>This class is the main class to start this application. This application
 * is dependant on the <em>captcha-builder</em> project source or library. This
 * project/applictaion will not run if the required dependencies are not
 * met.</p>
 *
 * <p>The captcha-builder project source and library can be downloaded here: <a
 * href="">LINK TO COME</a></p>
 *
 * 2013/04/22
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class CaptchaPreviewer {

    /**
     * Main startup method, launches the program. calls a private constructor to
     * get the call out of 'static'
     *
     * @param args the command line arguments, these will not be used
     */
    public static void main(String[] args) {
        new CaptchaPreviewer();
    }

    public CaptchaPreviewer() {
        Domain dom = new Domain();
        PreviewGUI gui = new PreviewGUI(dom);

        dom.addObserverableCaptchaObserver(gui);
    }
}
