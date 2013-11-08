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
package bachelorthesis.captchapreviewer.domain;

import bachelorthesis.captchabuilder.util.CaptchaDAO;
import bachelorthesis.captchapreviewer.domain.utils.IObserver;

/**
 * Domain.java (UTF-8)
 *
 * <p>This class will serve as the single access point for the whole domain
 * layer.</p>
 *
 * 2013/04/22
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class Domain {

    private ObservableCaptchaBuilder captchaBuilder;

    /**
     *
     * Default constructor.
     */
    public Domain() {
        this.captchaBuilder = new ObservableCaptchaBuilder(200, 50, "");
    }

    /**
     *
     * Passes the received buildString to the CaptchaBuilder.
     * <p/>
     * @param buildString
     */
    public void setBuildString(String buildString) {
        captchaBuilder.setBuildSequence(buildString);
    }

    /**
     *
     * Passes the received size to the CaptchaBuilder.
     * <p/>
     * @param width  the new width of the captchas to be made, must be strictly
     *               positive.
     * @param height the new height of the captchas to be made, must be strictly
     *               positive.
     */
    public void setImageSize(int width, int height) {
        captchaBuilder.setImageSize(width, width);
    }

    public void addObserverableCaptchaObserver(IObserver<CaptchaDAO> observer) {
        captchaBuilder.addObserver(observer);
    }
}
