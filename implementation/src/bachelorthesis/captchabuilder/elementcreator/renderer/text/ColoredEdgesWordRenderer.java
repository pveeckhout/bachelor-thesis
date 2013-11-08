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
package bachelorthesis.captchabuilder.elementcreator.renderer.text;

import bachelorthesis.captchabuilder.util.ColorRangeContainer;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.List;

/**
 * ColoredEdgesWordRenderer.java (UTF-8)
 *
 * generates coloured edges font style
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.1.0
 */
public class ColoredEdgesWordRenderer extends AbstractWordRenderer {

    public ColoredEdgesWordRenderer(ColorRangeContainer colorRange, List<Font> fonts,
                                    double xOffset, double yOffset,
                                    float strokeWidth) {
        super(colorRange, fonts, xOffset, yOffset, strokeWidth);
    }

    @Override
    public void render(String word, BufferedImage bi) {
        preRender(bi);
        int xBaseline = getXBaseline(bi);
        int yBaseline = getYBaseline(bi);

        AttributedString as = new AttributedString(word);
        as.addAttribute(TextAttribute.FONT, getRandomFont());
        AttributedCharacterIterator aci = as.getIterator();

        TextLayout tl = new TextLayout(aci, frc);

        Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(
                xBaseline, yBaseline));

        g.setColor(colorRange.getRandomColorInRange());
        g.setStroke(new BasicStroke(strokeWidth));

        g.draw(shape);
    }
}
