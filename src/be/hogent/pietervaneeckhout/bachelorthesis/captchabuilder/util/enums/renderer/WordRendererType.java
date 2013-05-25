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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.renderer;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRenderer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRendererBuilder;


/**
 * WordRendererType.java (UTF-8)
 *
 * all types of Word renderers
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.0.13
 */
public enum WordRendererType {

    COLOREDEDGES("Description"),
    DEFAULT("The default word renderer");
    private String desciption;

    private WordRendererType(String desciption) {
        this.desciption = desciption;
    }

    /**
     * returns the description.
     * @return string description
     */
    public WordRenderer getWordRenderer() {
        return new WordRendererBuilder(this).create();
    }

    /**
     * returns a renderer of the type.
     * 
     * @return a word renderer
     * @see WordRenderer
     */
    public String getDescription() {
        return desciption;
    }

    @Override
    public String toString() {
        return name() + ": " + desciption;
    }
}
