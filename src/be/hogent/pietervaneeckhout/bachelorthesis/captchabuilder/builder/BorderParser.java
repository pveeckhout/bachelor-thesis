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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.builder;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.border.BorderProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.producer.BorderProducerType;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * BorderParser.java (UTF-8)
 *
 * Parses the string arguments for rendering a border
 *
 * 2013/04/17
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.8
 * @version 1.0.13
 */
public class BorderParser {

    /**
     * Parses the string arguments for rendering a border, creates a
     * BorderProducer and passes it to the CaptchaBuilder
     * 
     * @param borderOptions the string arguments for building a border
     * @param builder the CaptchaBuilder Object to be modified
     * @return a modified CaotchaBuilder object
     * @throws ParseException
     * @see CaptchaBuilder
     */
    public static CaptchaBuilder parse(String[] borderOptions, CaptchaBuilder builder) throws ParseException {
        if (borderOptions.length == 0) {
            //return builder.addBorder();
            builder.addBuildSequence(new BorderProducerBuilder(BorderProducerType.SOLID));
            return builder;
        }

        if (borderOptions.length > 1) {
            throw new ParseException("Border takes a max of 1 arguments");
        }

        for (String borderOption : borderOptions) {
            if (!borderOption.isEmpty()) {
                try {
                    String[] optionArgs = borderOption.split(CaptchaConstants.buildSequencelvl3Delim);
                    BorderProducerType borderProducerType = BorderProducerType.valueOf(optionArgs[0]);
                    String[] borderOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);
                    return parseBorderProducer(borderProducerType, borderOptionArgs, builder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }

        return builder;
    }

    private static CaptchaBuilder parseBorderProducer(BorderProducerType borderProducerType, String[] borderProducerOptions, CaptchaBuilder builder) throws ParseException {
        BorderProducerBuilder borderProducerBuilder = new BorderProducerBuilder(borderProducerType);

        if (borderProducerOptions.length == 0) {
            //return builder.addBorder(borderProducerBuilder.create());
            builder.addBuildSequence(borderProducerBuilder);
            return builder;
        }

        if (borderProducerOptions.length > BorderProducerOptions.values().length) {
            throw new ParseException("BorderProducer takes a max of " + BorderProducerOptions.values().length + " arguments");
        }

        for (String boderproducerOption : borderProducerOptions) {
            if (!boderproducerOption.isEmpty()) {
                try {
                    String[] optionArgs = boderproducerOption.split(CaptchaConstants.buildSequencelvl4Delim);
                    BorderProducerOptions borderProducerOptionType = BorderProducerOptions.valueOf(optionArgs[0]);
                    String[] borderProducerOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);
                    borderProducerBuilder = parseBorderProducerOption(borderProducerOptionType, borderProducerOptionArgs, borderProducerBuilder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }

        }

        //return builder.addBorder(borderProducerBuilder.create());
        builder.addBuildSequence(borderProducerBuilder);
        return builder;
    }

    private static BorderProducerBuilder parseBorderProducerOption(BorderProducerOptions borderProducerOptionType, String[] borderProducerOptionArgs, BorderProducerBuilder borderProducerBuilder) throws ParseException {
        if (borderProducerOptionArgs.length != 1) {
            throw new ParseException("BorderProducer option " + borderProducerOptionType.name() + " only takes 1 argument");
        }

        switch (borderProducerOptionType) {
            case COLORS:
                try {
                    String[] colorArgs = borderProducerOptionArgs[0].split(CaptchaConstants.buildSequencelvl5Delim);
                    return borderProducerBuilder.setColorRange(ColorsParser.parse(colorArgs));
                } catch (NumberFormatException e) {
                    throw new ParseException("Border colors has invalid formatted numbers");
                }
            case THICKNESS:
                try {
                    return borderProducerBuilder.setThickness(Integer.parseInt(borderProducerOptionArgs[0]));
                } catch (NumberFormatException e) {
                    throw new ParseException("Border thickness argument has an invalid number format");
                }
            default:
                throw new ParseException("BorderProducer option not found: " + borderProducerOptionType.name());

        }
    }

    enum BorderProducerOptions {

        COLORS,
        THICKNESS;
    }
}
