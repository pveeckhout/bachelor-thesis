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

import bachelorthesis.captchabuilder.Captcha;
import bachelorthesis.captchabuilder.builder.CaptchaBuilder;
import bachelorthesis.captchabuilder.util.CaptchaDAO;
import bachelorthesis.captchapreviewer.domain.utils.IObserver;
import bachelorthesis.captchapreviewer.domain.utils.ISubject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;

/**
 * ObservableCaptchaBuilder.java (UTF-8)
 *
 * <p>This class will serve as an observable extention, implements ISubject
 * interface.</p>
 * <p>This class is an observable layer above a CaptchaBuilder class, when the
 * buildSequence is change this class will try to parse it and send a CaptchaDAO
 * object to the observers.</p>
 *
 * <p>This class will start a timertask to call the notifyObservers method every
 * 5 seconds if the buildstring is valid. This will result in a new generate
 * being generated and passed to the observers
 *
 * @see CaptchaBuilder
 * @see ISubject
 * @see CaptchaDAO
 *
 * 2013/04/22
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public final class ObservableCaptchaBuilder implements ISubject<CaptchaDAO> {

    private CaptchaBuilder builder;
    private List<IObserver<CaptchaDAO>> observers;
    private String message;
    private boolean valid;
    private Timer timer;
    private UpdateTask updateTask;

    /**
     *
     * The constructor for this class
     *
     * @param width         the width of the captcha image
     * @param height        the height of the captcha image
     * @param buildSequence
     */
    public ObservableCaptchaBuilder(int width, int height, String buildSequence) {
        timer = new Timer("updateTimer");
        observers = new ArrayList<>();

        try {
            builder = new CaptchaBuilder(width, height, buildSequence);
            message = "Build String parsed successfully";
            valid = true;
        } catch (ParseException ex) {
            Logger.getLogger(ObservableCaptchaBuilder.class.getName()).log(
                    Level.SEVERE, null, ex);
            message = "Build String parsing failed: " + ex.getMessage();
            valid = false;
        } finally {
            setStatusChanged();
        }
    }

    /**
     *
     * Sets the buildSequenceString for the contained CaptchaBuilder Object.
     *
     * @see be.hogent.captchabuilder.builder.CaptchaBuilder
     *
     * @param buildString
     */
    public void setBuildSequence(String buildString) {
        try {
            builder.setBuildSequence(buildString);
            message = "Build String parsed successfully";
            valid = true;
        } catch (ParseException ex) {
            Logger.getLogger(ObservableCaptchaBuilder.class.getName()).log(
                    Level.SEVERE, null, ex);
            message = "Build String parsing failed: " + ex.getMessage();
            valid = false;
        } finally {
            setStatusChanged();
        }
    }

    /**
     * Sets the size of the captcha image to be generated.
     *
     * @param width  the new width of the the image to make
     * @param height the new height if the image to make
     */
    public void setImageSize(int width, int height) {
        builder.setImageSize(width, height);
        setStatusChanged();
    }

    @Override
    public void setStatusChanged() {
        if (updateTask != null) {
            updateTask.cancel();
        }
        updateTask = new UpdateTask();
        timer.scheduleAtFixedRate(updateTask, 5000, 5000);
    }

    @Override
    public void notifyObeservers() {
        BufferedImage image = new BufferedImage(builder.getWidth(), builder
                .getHeight(), BufferedImage.TYPE_INT_ARGB);
        String answer = "";

        if (valid) {
            try {
                Captcha c = builder.buildCaptcha();
                image = c.getImage();
                answer = c.getAnswer();
            } catch (ParseException ex) {
                Logger.getLogger(ObservableCaptchaBuilder.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }

        CaptchaDAO dao = new CaptchaDAO(image, answer, message);

        for (IObserver<CaptchaDAO> observer : observers) {
            observer.update(dao);
        }
    }

    @Override
    public void addObserver(IObserver<CaptchaDAO> observer) {
        observers.add(observer);
        setStatusChanged();

    }

    private class UpdateTask extends TimerTask {

        public void run() {
            if (valid) {
                notifyObeservers();
            }
        }
    }
}
