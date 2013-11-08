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
package bachelorthesis.captchabuilder.builder;

import bachelorthesis.captchabuilder.elementcreator.producer.noise.NoiseProducerBuilder;
import bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import bachelorthesis.captchabuilder.util.enums.producer.NoiseProducerType;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * NoiseParser.java (UTF-8)
 *
 * Parses the string arguments for rendering noise
 *
 * 2013/04/17
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.8
 * @version 1.0.13
 */
public class NoiseParser {

    /**
     * Parses the string arguments for rendering noise, creates a
     * NoiseProducer and passes it to the CaptchaBuilder
     *
     * @param buildSequenceOptions the string arguments for building noise
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
            //return builder.addNoise();
            builder.addBuildSequence(new NoiseProducerBuilder(
                    NoiseProducerType.CURVEDLINE));
            return builder;
        }

        if (buildSequenceOptions.length > NoiseOptions.values().length) {
            throw new ParseException("Noise takes a max of " + NoiseOptions
                    .values().length + " arguments");
        }

        for (String noiseOption : buildSequenceOptions) {
            if (!noiseOption.isEmpty()) {
                try {
                    String[] optionArgs = noiseOption.split(
                            CaptchaConstants.buildSequencelvl3Delim);
                    NoiseProducerType bgProdBuilder = NoiseProducerType.valueOf(
                            optionArgs[0]);
                    String[] noiseOptions = Arrays.copyOfRange(optionArgs, 1,
                                                               optionArgs.length);
                    return parseNoiseProducer(bgProdBuilder, noiseOptions,
                                              builder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }

        return builder;
    }

    private static CaptchaBuilder parseNoiseProducer(
            NoiseProducerType noiseProducerType, String[] noiseProducerOptions,
            CaptchaBuilder builder) throws ParseException {
        NoiseProducerBuilder noiseProducerBuilder = new NoiseProducerBuilder(
                noiseProducerType);

        if (noiseProducerOptions.length == 0) {
            //return builder.addNoise(noiseProducerBuilder.create());
            builder.addBuildSequence(noiseProducerBuilder);
            return builder;
        }

        if (noiseProducerOptions.length > NoiseProducerOptions.values().length) {
            throw new ParseException("NoiseProducer takes a max of " +
                    NoiseProducerOptions.values().length + " arguments");
        }

        for (String noiseProducerOption : noiseProducerOptions) {
            String[] optionArgs = noiseProducerOption.split(
                    CaptchaConstants.buildSequencelvl4Delim);
            try {
                NoiseProducerOptions noiseProducerOptionType =
                        NoiseProducerOptions.valueOf(optionArgs[0]);
                String[] noiseProducerOptionArgs = Arrays
                        .copyOfRange(optionArgs, 1, optionArgs.length);
                noiseProducerBuilder = parseNoiseProducerOption(
                        noiseProducerOptionType, noiseProducerOptionArgs,
                        noiseProducerBuilder);
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage());
            }
        }

        //return builder.addNoise(noiseProducerBuilder.create());
        builder.addBuildSequence(noiseProducerBuilder);
        return builder;
    }

    private static NoiseProducerBuilder parseNoiseProducerOption(
            NoiseProducerOptions noiseProducerOptionType,
            String[] noiseProducerOptionArgs,
            NoiseProducerBuilder noiseProducerBuilder) throws ParseException {
        if (noiseProducerOptionArgs.length != 1) {
            throw new ParseException("NoiseProducer option " +
                    noiseProducerOptionType.name() + " only takes 1 argument");
        }

        switch (noiseProducerOptionType) {
            case COLORS:
                try {
                    return noiseProducerBuilder.setColorRange(ColorsParser
                            .parse(noiseProducerOptionArgs[0].split(
                            CaptchaConstants.buildSequencelvl5Delim)));
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            "Noise colors has invalid formatted numbers");
                }
            case THICKNESS:
                try {
                    return noiseProducerBuilder.setThickness(Float.parseFloat(
                            noiseProducerOptionArgs[0]));
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            "Noise thickness argument has an invalid number format");
                }
            default:
                throw new ParseException("NoiseProducer option not found: " +
                        noiseProducerOptionType.name());
        }
    }

    enum NoiseOptions {

        DEFAULT;
    }

    enum NoiseProducerOptions {

        COLORS,
        THICKNESS;
    }
}
