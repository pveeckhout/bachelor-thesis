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

import bachelorthesis.captchabuilder.elementcreator.producer.background.BackgroundProducerBuilder;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import bachelorthesis.captchabuilder.util.enums.producer.BackgroundProducerType;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * BackgroundParser.java (UTF-8)
 *
 * Parses the string arguments for rendering a background
 *
 * 2013/04/17
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.8
 * @version 1.0.13
 */
public class BackgroundParser {

    /**
     * Parses the string arguments for rendering a background, creates a
     * BackgroundProducer and passes it to the CaptchaBuilder
     *
     * @param buildSequenceOptions the string arguments for building a
     *                             background
     * @param builder              the CaptchaBuilder Object to be modified
     * <p/>
     * @return a modified CaptchaBuilder object
     * <p/>
     * @throws org.apache.commons.cli.ParseException
     * @see CaptchaBuilder
     */
    public static CaptchaBuilder parse(String[] buildSequenceOptions,
                                       CaptchaBuilder builder) throws
            ParseException {
        if (buildSequenceOptions.length == 0) {
            //return builder.addBackground();
            builder.addBuildSequence(new BackgroundProducerBuilder(
                    BackgroundProducerType.TRANSPARENT));
            return builder;
        }

        if (buildSequenceOptions.length > 1) {
            throw new ParseException("Background takes a max of 1 arguments");
        }

        for (String backgroundOption : buildSequenceOptions) {
            if (!backgroundOption.isEmpty()) {
                try {
                    String[] optionArgs = backgroundOption.split(
                            CaptchaConstants.buildSequencelvl3Delim);
                    BackgroundProducerType backgroundProducerType =
                            BackgroundProducerType.valueOf(optionArgs[0]);
                    String[] backgroundOptionArgs = Arrays.copyOfRange(
                            optionArgs, 1, optionArgs.length);
                    return parseBackgroundProducer(backgroundProducerType,
                                                   backgroundOptionArgs, builder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }

        return builder;
    }

    private static CaptchaBuilder parseBackgroundProducer(
            BackgroundProducerType backgroundProducerType,
            String[] backgroundProducerOptions, CaptchaBuilder builder) throws
            ParseException {
        BackgroundProducerBuilder backgroundProducerBuilder =
                new BackgroundProducerBuilder(backgroundProducerType);

        if (backgroundProducerOptions.length == 0) {
            //return builder.addBackground(backgroundProducerBuilder.create());
            builder.addBuildSequence(backgroundProducerBuilder);
            return builder;
        }

        if (backgroundProducerOptions.length > BackgroundProducerOptions
                .values().length) {
            throw new ParseException("BackgroundProducer takes a max of " +
                    BackgroundProducerOptions.values().length + " arguments");
        }

        for (String backgroundProducerOption : backgroundProducerOptions) {
            if (!backgroundProducerOption.isEmpty()) {
                try {
                    String[] optionArgs = backgroundProducerOption.split(
                            CaptchaConstants.buildSequencelvl4Delim);
                    BackgroundProducerOptions backgroundProducerOptionType =
                            BackgroundProducerOptions.valueOf(optionArgs[0]);
                    String[] backgroundProducerOptionArgs = Arrays.copyOfRange(
                            optionArgs, 1, optionArgs.length);
                    backgroundProducerBuilder = parseBackgroundProducerOption(
                            backgroundProducerOptionType,
                            backgroundProducerOptionArgs,
                            backgroundProducerBuilder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }

        //return builder.addBackground(backgroundProducerBuilder.create());
        builder.addBuildSequence(backgroundProducerBuilder);
        return builder;
    }

    private static BackgroundProducerBuilder parseBackgroundProducerOption(
            BackgroundProducerOptions backgroundProducerOptionType,
            String[] backgroundProducerOptionArgs,
            BackgroundProducerBuilder backgroundProducerBuilder) throws
            ParseException {
        if (backgroundProducerOptionArgs.length != 1) {
            throw new ParseException("BackgroundProducer option " +
                    backgroundProducerOptionType.name() +
                    " only takes 1 argument");
        }

        String[] colorArgs = backgroundProducerOptionArgs[0].split(
                CaptchaConstants.buildSequencelvl5Delim);

        switch (backgroundProducerOptionType) {
            case COLORS1:
                try {
                    return backgroundProducerBuilder.setColorRange1(ColorsParser
                            .parse(colorArgs));
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            "Background colors1 has invalid formatted numbers");
                }
            case COLORS2:
                try {
                    return backgroundProducerBuilder.setColorRange2(ColorsParser
                            .parse(colorArgs));
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            "Background colors2 has invalid formatted numbers");
                }
            default:
                throw new ParseException(
                        "BackgroundProducer option not found: " +
                        backgroundProducerOptionType.name());
        }
    }

    enum BackgroundProducerOptions {

        COLORS1,
        COLORS2;
    }
}
