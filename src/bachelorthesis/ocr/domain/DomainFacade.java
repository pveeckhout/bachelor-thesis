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

import bachelorthesis.captchabuilder.util.ArrayUtil;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import bachelorthesis.neuralnetworks.network.encog.hopfield.EncogHopfieldNetworkBuilder;
import bachelorthesis.neuralnetworks.util.TrainingSet;

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

    private TrainingSet trainingSet;
    private NeuralNetworkController networkController;
    private int width, height;
    

    public DomainFacade() {
        width = 40;
        height = 50;
        char[] chars = ArrayUtil.concat(CaptchaConstants.LETTERS,
                CaptchaConstants.NUMBERS,
                CaptchaConstants.SPECIAL);
        trainingSet = new CharactersTrainingSet(chars, width, height);
        
        networkController = new DefaultNeuralNetworkController();
    }

    public DomainFacade(TrainingSet trainingSet, NeuralNetworkController networkController) {
        this.trainingSet = trainingSet;
        this.networkController = networkController;
        
        networkController.setTrainingSet(trainingSet);
    }    
}
