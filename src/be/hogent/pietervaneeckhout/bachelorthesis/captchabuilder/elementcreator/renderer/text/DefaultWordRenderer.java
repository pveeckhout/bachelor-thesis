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

package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.text;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.ColorRangeRGBA;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import java.awt.Font;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * DefaultWordRenderer.java (UTF-8)
 *
 * generates solid font style
 *
 * 2013/04/16
 * 
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.1.0
 */
public class DefaultWordRenderer extends AbstractWordRenderer {

    public DefaultWordRenderer(ColorRangeRGBA colorRange, List<Font> fonts, double xOffset, double yOffset, float strokeWidth) {
        super(colorRange, fonts, xOffset, yOffset, strokeWidth);
    }

    @Override
    public void render(String word, BufferedImage bi) {
        preRender(bi);
        int xBaseline = getXBaseline(bi);
        int yBaseline =  getYBaseline(bi);
        
        char[] chars = new char[1];
        for (char c : word.toCharArray()) {
            chars[0] = c;
            
            g.setColor(colorRange.getRandomColorInRange());

            int choiceFont = CaptchaConstants.RANDOM.nextInt(fonts.size());
            Font font = fonts.get(choiceFont);
            g.setFont(font);
            
            GlyphVector gv = font.createGlyphVector(frc, chars);
            g.drawChars(chars, 0, chars.length, xBaseline, yBaseline);

            int width = (int) gv.getVisualBounds().getWidth();
            xBaseline = xBaseline + width;
        }
    }
    
    
}
