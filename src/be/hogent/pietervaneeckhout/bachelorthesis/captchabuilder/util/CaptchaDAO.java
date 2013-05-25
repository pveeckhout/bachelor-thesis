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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util;

import java.awt.image.BufferedImage;

/**
 * CaptchaDAO.java (UTF-8)
 *
 * A data access object were all data is read only, used to pass the captcha
 * info to a GUI
 *
 * 2013/04/15
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.2.0
 * @version 1.2.0
 */
public class CaptchaDAO {
    private final BufferedImage image;
    private final String answer;
    private final String parserMessage;

    /**
     * Constructor
     * 
     * @param image the generated image
     * @param answer the answer
     * @param parserMessage the message the parse generated
     */
    public CaptchaDAO(BufferedImage image, String answer, String parserMessage) {
        this.image = image;
        this.answer = answer;
        this.parserMessage = parserMessage;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getAnswer() {
        return answer;
    }

    public String getParserMessage() {
        return parserMessage;
    }
}
