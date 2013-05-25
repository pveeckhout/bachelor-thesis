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
package be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.builder;

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.background.BackgroundProducer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.background.BackgroundProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.border.BorderProducer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.border.BorderProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.noise.NoiseProducer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.noise.NoiseProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.text.TextProducer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.text.TextProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.CaptchaElementCreatorBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.gimpy.GimpyRenderer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.gimpy.GimpyRendererBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRenderer;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRendererBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import java.util.ArrayDeque;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * CaptchaBuildSequenceParser.java (UTF-8)
 *
 * receives the buildstring, splits it up it pieces and passes those to the
 * other parsers
 *
 * 2013/04/16
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.3
 * @version 1.0.8
 */
public class CaptchaBuildSequenceParser {

    /**
     * Modifies the CaptchaBuilder to store a list of ElementCreatorBuilders the parsers made
     * from the buildstring.
     *
     * @param builder the CaptchBuilder object to be modified
     * @throws ParseException
     * @see CaptchaBuilder
     */
    public static void longParse(CaptchaBuilder builder) throws ParseException {

        for (String lvl1Arg : builder.getBuildSequence().split(CaptchaConstants.buildSequencelvl1Delim)) {
            if (!lvl1Arg.isEmpty()) {
                try {
                    String[] optionArgs = lvl1Arg.split(CaptchaConstants.buildSequencelvl2Delim);
                    BuildSequenceOptions buildSequenceOptionType = BuildSequenceOptions.valueOf(optionArgs[0]);
                    String[] buildSequenceOptions = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);

                    builder = parseBuildSequenceOption(buildSequenceOptionType, buildSequenceOptions, builder);

                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }
    }

    private static CaptchaBuilder parseBuildSequenceOption(BuildSequenceOptions option, String[] buildSequenceOptions, CaptchaBuilder builder) throws ParseException {
        switch (option) {
            case BACKGROUND:
                return BackgroundParser.parse(buildSequenceOptions, builder);
            case BORDER:
                return BorderParser.parse(buildSequenceOptions, builder);
            case GIMP:
                return GimpyParser.parse(buildSequenceOptions, builder);
            case NOISE:
                return NoiseParser.parse(buildSequenceOptions, builder);
            case TEXT:
                return TextParser.parse(buildSequenceOptions, builder);
            default:
                throw new ParseException("argument not found: " + option.name());
        }
    }

    /**
     * Creates new elementCreators from the list CaptchaElementCreatorBuilders
     * in the CaptchaBuilder. 
     *
     * @param builder
     * @throws ParseException
     */
    public static void shortParse(CaptchaBuilder builder) throws ParseException {
        ArrayDeque<CaptchaElementCreatorBuilder> elementBuilders = builder.getBuilders().clone();
        ArrayDeque<BuildSequenceOptions> sequence = new ArrayDeque<>();
        for (String lvl1Arg : builder.getBuildSequence().split(CaptchaConstants.buildSequencelvl1Delim)) {
            if (!lvl1Arg.isEmpty()) {
                try {
                    String[] optionArgs = lvl1Arg.split(CaptchaConstants.buildSequencelvl2Delim);
                    sequence.offer(BuildSequenceOptions.valueOf(optionArgs[0]));
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }

        for (BuildSequenceOptions buildSequence : sequence) {
            switch (buildSequence) {
                case BACKGROUND: {
                    CaptchaElementCreatorBuilder elementBuilder = elementBuilders.poll();
                    if (elementBuilder instanceof BackgroundProducerBuilder) {
                        builder.addBackground((BackgroundProducer) elementBuilder.create());
                    } else {
                        throw new ParseException("ShortParse Failed ... How is that possible?\n"
                                + "Class Mismatch: Got " + elementBuilder.getClass().getSimpleName()
                                + " and expected " + BackgroundProducerBuilder.class.getSimpleName());
                    }
                }
                break;
                case BORDER: {
                    CaptchaElementCreatorBuilder elementBuilder = elementBuilders.poll();
                    if (elementBuilder instanceof BorderProducerBuilder) {
                        builder.addBorder((BorderProducer) elementBuilder.create());
                    } else {
                        throw new ParseException("ShortParse Failed ... How is that possible?\n"
                                + "Class Mismatch: Got " + elementBuilder.getClass().getSimpleName()
                                + " and expected " + BorderProducerBuilder.class.getSimpleName());
                    }
                }
                break;
                case GIMP: {
                    CaptchaElementCreatorBuilder elementBuilder = elementBuilders.poll();
                    if (elementBuilder instanceof GimpyRendererBuilder) {
                        builder.gimp((GimpyRenderer) elementBuilder.create());
                    } else {
                        throw new ParseException("ShortParse Failed ... How is that possible?\n"
                                + "Class Mismatch: Got " + elementBuilder.getClass().getSimpleName()
                                + " and expected " + GimpyRendererBuilder.class.getSimpleName());
                    }
                }
                break;
                case NOISE: {
                    CaptchaElementCreatorBuilder elementBuilder = elementBuilders.poll();
                    if (elementBuilder instanceof NoiseProducerBuilder) {
                        builder.addNoise((NoiseProducer) elementBuilder.create());
                    } else {
                        throw new ParseException("ShortParse Failed ... How is that possible?\n"
                                + "Class Mismatch: Got " + elementBuilder.getClass().getSimpleName()
                                + " and expected " + NoiseProducerBuilder.class.getSimpleName());
                    }
                }
                break;
                case TEXT: {
                    CaptchaElementCreatorBuilder elementBuilder1 = elementBuilders.poll();
                    CaptchaElementCreatorBuilder elementBuilder2 = elementBuilders.poll();
                    if (elementBuilder1 instanceof TextProducerBuilder && elementBuilder2 instanceof WordRendererBuilder) {
                        builder.addText((TextProducer) elementBuilder1.create(), (WordRenderer) elementBuilder2.create());
                    } else {
                        throw new ParseException("ShortParse Failed ... How is that possible?\n"
                                + "Class Mismatch: Got " + elementBuilder1.getClass().getSimpleName()
                                + " and expected " + TextProducerBuilder.class.getSimpleName()
                                + "\n"
                                + "Class Mismatch: Got " + elementBuilder2.getClass().getSimpleName()
                                + " and expected " + WordRendererBuilder.class.getSimpleName());
                    }
                }
            }
        }
    }

    enum BuildSequenceOptions {

        BACKGROUND,
        BORDER,
        GIMP,
        NOISE,
        TEXT;
    }
}
