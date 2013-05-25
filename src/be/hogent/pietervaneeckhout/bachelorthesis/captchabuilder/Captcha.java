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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder;

import java.io.Serializable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 * Captcha.java (UTF-8)
 *
 * Captcha object, contains the image, the answer, buildstring used to create
 * the image and the date the captcha was create.
 *
 * 2013/04/17
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.7
 * @version 1.0.7
 */
public class Captcha implements Serializable {

    private static final long serialVersionUID = 617954136L;
    private String answer;
    private String buildSequence;
    private boolean caseSensative;
    private Date timestamp;
    private BufferedImage captchaImage;

    /**
     * Constructor
     * 
     * @param buildSequence
     * @param answer
     * @param caseSensative
     * @param captchaImage
     * @param timestamp
     */
    public Captcha(String buildSequence, String answer, boolean caseSensative, BufferedImage captchaImage, Date timestamp) {
        this.buildSequence = buildSequence;
        this.answer = answer;
        this.captchaImage = captchaImage;
        this.timestamp = timestamp;
        this.caseSensative = caseSensative;
    }

    /**
     * Validates if the string passed matches the answer stored
     * 
     * @param response the response given by the user
     * @return true or false
     */
    public boolean isCorrect(String response) {
        if (caseSensative) {
            return answer.equals(response);
        } else {
            return answer.equalsIgnoreCase(response);
        }
    }

    public String getAnswer() {
        return answer;
    }

    public BufferedImage getImage() {
        return captchaImage;
    }

    public Date getTimeStamp() {
        return timestamp;
    }

    public String getBuildSequence() {
        return buildSequence;
    }

    public boolean isCaseSensative() {
        return caseSensative;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Answer: ")
                .append(answer)
                .append("][Case sensative: ")
                .append(caseSensative)
                .append("][Timestamp: ")
                .append(timestamp)
                .append("][Image: ")
                .append(captchaImage)
                .append("][Build Sequence: ")
                .append(buildSequence)
                .append("]")
                .toString();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(buildSequence);
        out.writeObject(answer);
        out.writeObject(caseSensative);
        out.writeObject(timestamp);
        ImageIO.write(captchaImage, "png", ImageIO.createImageOutputStream(out));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        buildSequence = (String) in.readObject();
        answer = (String) in.readObject();
        caseSensative = (Boolean) in.readObject();
        timestamp = (Date) in.readObject();
        captchaImage = ImageIO.read(ImageIO.createImageInputStream(in));
    }
}
