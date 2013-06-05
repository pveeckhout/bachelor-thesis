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
package bachelorthesis.ocr.domain;

import bachelorthesis.captchabuilder.Captcha;
import bachelorthesis.captchabuilder.builder.CaptchaBuilder;
import bachelorthesis.captchabuilder.util.ArrayUtil;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import bachelorthesis.neuralnetworks.network.encog.hopfield.EncogHopfieldNetwork;
import bachelorthesis.neuralnetworks.network.encog.hopfield.EncogHopfieldNetworkBuilder;
import bachelorthesis.neuralnetworks.network.encog.kohonen.EncogKohonenNetwork;
import bachelorthesis.neuralnetworks.network.encog.kohonen.EncogKohonenNetworkBuilder;
import bachelorthesis.neuralnetworks.util.TrainingSet;
import bachelorthesis.ocr.util.CharacterPatternUtils;
import bachelorthesis.ocr.util.ImageToInputPattern;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.cli.ParseException;

/**
 * DomainFacade.java (UTF-8)
 *
 * Acts as the entrypoint for the domain layer. All calls toward the domain
 * should pass here. Will contain the repositories and the controller instances,
 * if there are any.
 *
 * 2013/06/04
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class DomainFacade {

    public static final String BUILDSTRING = ":TEXT!TEXTPRODUCER#ALPHANUMERIC_SPECIAL@MINLENGTH*1@MAXLENGTH*1";
    private CharactersTrainingSet trainingSet;
    private NeuralNetworkController networkController;

    public DomainFacade() {
        char[] chars = ArrayUtil.concat(CaptchaConstants.LETTERS,
                CaptchaConstants.NUMBERS,
                CaptchaConstants.SPECIAL);
        trainingSet = new CharactersTrainingSet(chars, 40, 50);

        networkController = new DefaultNeuralNetworkController();
    }

    public DomainFacade(CharactersTrainingSet trainingSet, NeuralNetworkController networkController) {
        this.trainingSet = trainingSet;
        this.networkController = networkController;

        networkController.setTrainingSet(trainingSet);
    }

    public CharactersTrainingSet getTrainingSet() {
        return trainingSet;
    }

    public void setTrainingSet(CharactersTrainingSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public NeuralNetworkController getNetworkController() {
        return networkController;
    }

    public void setNetworkController(NeuralNetworkController networkController) {
        this.networkController = networkController;
    }

    public void hopfield() {
        networkController.setNetwork(new EncogHopfieldNetworkBuilder().createEncogHopfieldNetwork());

        trainingSet.buildSet();
        networkController.buildAndTrainNetwork(trainingSet);
        Captcha captcha = createCaptcha(BUILDSTRING, trainingSet.getWidth(), trainingSet.getHeight());
        double[] expectedResult = CharacterPatternUtils.characterToBitArray(captcha.getAnswer().charAt(0));
        double[] inputPattern = ImageToInputPattern.colorRangeToDoubleInputPattern(captcha.getImage(), 0, 0, 1, -1);
        double[] result = networkController.evaluate(inputPattern, 0);

        System.out.println("Processing output");
        for (int j = 0; j < result.length; j++) {
            result[j] = (result[j] >= 0) ? 1 : 0;
        }

        boolean correct = Arrays.equals(result, expectedResult);

        System.out.println(captcha.getAnswer().charAt(0) + " recognized? " + correct + "\n\n" + EncogHopfieldNetwork.convertForDisplay(inputPattern, result, trainingSet.getWidth(), trainingSet.getHeight()));

    }

    public void kohonen() {
        networkController.setNetwork(new EncogKohonenNetworkBuilder().setForceWinner(true).setRadius(20).setLearningRate(5).setError(0.17).createEncogKohonenNetwork());
        
        char[] chars = CaptchaConstants.NUMBERS
                ;
        trainingSet = new CharactersTrainingSet(chars, 40, 50);
        trainingSet.buildSet();
        networkController.buildAndTrainNetwork(trainingSet);
        Captcha captcha = createCaptcha(BUILDSTRING, trainingSet.getWidth(), trainingSet.getHeight());
        double[] expectedResult = CharacterPatternUtils.characterToBitArray(captcha.getAnswer().charAt(0));
        double[] inputPattern = ImageToInputPattern.colorRangeToDoubleInputPattern(captcha.getImage(), 0, 0, 1, -1);
        double[] result = networkController.evaluate(inputPattern, 0);
        
        boolean correct = Arrays.equals(result, expectedResult);

        System.out.println(captcha.getAnswer().charAt(0) + " recognized? " + correct);
    }

    public void perceptron() {
    }

    private Captcha createCaptcha(String buildString, int width, int height) throws RuntimeException {
        try {
            CaptchaBuilder captchaBuilder = new CaptchaBuilder(40, 50, buildString);
            Captcha c = captchaBuilder.buildCaptcha();
            BufferedImage img = c.getImage();

            // check if size == the default size (40*50) if not scale
            if (width != 40 || height != 50) {
                BufferedImage resized =
                        new BufferedImage(width, height, img.getType());
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(), img.getHeight(),
                        null);
                g.dispose();

                //build new CAPTCHA WITH THE NEW IMAGE SIZE
                c = new Captcha(c.getBuildSequence(), c.getAnswer(), c.isCaseSensative(), img, new Date());
            }

            return c;

        } catch (ParseException ex) {
            Logger.getLogger(DomainFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("error creating CAPTCHA");
        }
    }
}
