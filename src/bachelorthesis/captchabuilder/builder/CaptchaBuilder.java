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
package bachelorthesis.captchabuilder.builder;

import bachelorthesis.captchabuilder.elementcreator.producer.background.BackgroundProducer;
import bachelorthesis.captchabuilder.elementcreator.producer.background.BackgroundProducerBuilder;
import bachelorthesis.captchabuilder.elementcreator.producer.border.BorderProducer;
import bachelorthesis.captchabuilder.elementcreator.producer.noise.NoiseProducer;
import bachelorthesis.captchabuilder.elementcreator.producer.text.TextProducer;
import bachelorthesis.captchabuilder.Captcha;
import bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import bachelorthesis.captchabuilder.elementcreator.renderer.gimpy.GimpyRenderer;
import bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRenderer;
import bachelorthesis.captchabuilder.util.enums.producer.BackgroundProducerType;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Date;
import org.apache.commons.cli.ParseException;

/**
 * CaptchaBuilder.java (UTF-8)
 *
 * Builder class to create Captcha objects.
 *
 * 2013/04/17
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.7
 * @version 1.0.13
 */
public class CaptchaBuilder {

    private BufferedImage img;
    private BufferedImage bg;
    private boolean caseSensative;
    private String answer;
    private String buildSequence;
    private ArrayDeque<CaptchaElementCreatorBuilder> builders;

    /**
     * Constructor
     *
     * @param width         the width of the captcha to be created
     * @param height        the width of the captcha to be created
     * @param buildSequence string arguments to create the captcha
     * <p/>
     * @throws ParseException
     */
    public CaptchaBuilder(int width, int height, String buildSequence) throws
            ParseException {
        this.builders = new ArrayDeque<>();
        this.setBuildSequence(buildSequence);
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        answer = "";
    }

    protected CaptchaBuilder addBackground(BackgroundProducer backgroundProducer) {
        bg = backgroundProducer.getBackground(img.getWidth(), img.getHeight());
        return this;
    }

    protected CaptchaBuilder addText(TextProducer textProducer,
                                     WordRenderer wordRenderer) {
        answer += textProducer.getText();
        wordRenderer.render(answer, img);
        return this;
    }

    protected CaptchaBuilder addNoise(NoiseProducer noiseProducer) {
        noiseProducer.makeNoise(img);
        return this;
    }

    protected CaptchaBuilder gimp(GimpyRenderer gimpyRenderer) {
        gimpyRenderer.gimp(img);
        return this;
    }

    protected CaptchaBuilder addBorder(BorderProducer borderProducer) {
        borderProducer.addBorder(img);
        return this;
    }

    public CaptchaBuilder setImageSize(int width, int height) {
        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return this;
    }

    /**
     * Sets, validates and parses the string arguments for creating the captcha.
     *
     * @param buildSequence the string arguments for building a captcha
     * <p/>
     * @return the CaptchaBuilder
     * <p/>
     * @throws ParseException
     */
    public final CaptchaBuilder setBuildSequence(String buildSequence) throws
            ParseException {
        if (!buildSequence.equalsIgnoreCase(this.buildSequence)) {
            this.buildSequence = buildSequence.toUpperCase();

            // If the buildSequence has changed then longParse it
            // Before longparsing, empty the elementbuilderDeque
            this.builders.clear();
            // start parsing
            long startTimeLong = System.nanoTime();
            CaptchaBuildSequenceParser.longParse(this);
            long endTimeLong = System.nanoTime();
            double duration = (double) ((endTimeLong - startTimeLong) / Math
                    .pow(10, 9));
            System.out.println("Long buildSequence parsed in " + duration +
                    " seconds");
        }

        return this;
    }

    private Captcha build() {
        return new Captcha(buildSequence, answer, caseSensative, flattenImage(),
                           new Date());
    }

    /**
     * parses the buildstring and creates the captcha.
     * <p/>
     * @return a captcha object
     * <p/>
     * @throws ParseException
     * @see Captcha
     */
    public Captcha buildCaptcha() throws ParseException {
        img = new BufferedImage(img.getWidth(), img.getHeight(),
                                BufferedImage.TYPE_INT_ARGB);
        answer = "";
        long startTimeShort = System.nanoTime();
        CaptchaBuildSequenceParser.shortParse(this);
        long endTimeShort = System.nanoTime();
        double duration = (double) ((endTimeShort - startTimeShort) / Math.pow(
                10, 9));
        System.out.println("Short buildSequence parsed in " + duration +
                " seconds");

        return build();
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    public String getBuildSequence() {
        return buildSequence;
    }

    public final ArrayDeque<CaptchaElementCreatorBuilder> getBuilders() {
        return builders;
    }

    public void addBuildSequence(CaptchaElementCreatorBuilder elementBuilder) {
        builders.offer(elementBuilder);
    }

    private BufferedImage flattenImage() {
        BufferedImage rImage;
        if (bg == null) {
            rImage = new BackgroundProducerBuilder(
                    BackgroundProducerType.TRANSPARENT).create().getBackground(
                    img.getWidth(), img.getHeight());
        } else {
            rImage = bg;
        }

        // Paint the main image over the background
        Graphics2D g = rImage.createGraphics();
        g
                .setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.drawImage(img, null, null);

        return rImage;
    }
}
