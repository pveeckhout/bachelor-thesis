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

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.producer.text.TextProducerBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.text.WordRendererBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.producer.TextProducerType;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.renderer.WordRendererType;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * TextParser.java (UTF-8)
 *
 * Parses the string arguments for creating the text
 *
 * 2013/04/18
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.8
 * @version 1.0.13
 */
public class TextParser {

    private static TextProducerBuilder textProducerBuilder = new TextProducerBuilder(TextProducerType.REDUCED_ALPHANUMERIC);
    private static WordRendererBuilder wordRendererBuilder = new WordRendererBuilder(WordRendererType.DEFAULT);

    /**
     * Parses the string arguments for rendering Text, creates a
     * TextProducer and WordRenderer passes these to the CaptchaBuilder
     *
     * @param buildSequenceOptions the string arguments for adding text
     * @param builder the CaptchaBuilder Object to be modified
     * @return a modified CaptchaBuilder object
     * @throws org.apache.commons.cli.ParseException
     * @see CaptchaBuilder
     */
    public static CaptchaBuilder parse(String[] buildSequenceOptions, CaptchaBuilder builder) throws ParseException {

        for (String textOptionArg : buildSequenceOptions) {
            if (!textOptionArg.isEmpty()) {
                try {
                    String[] optionArgs = textOptionArg.split(CaptchaConstants.buildSequencelvl3Delim);
                    TextOptions textOptionType = TextOptions.valueOf(optionArgs[0]);
                    String[] textOptions = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);

                    parseTextOption(textOptionType, textOptions, builder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }

            }
        }

        //return builder.addText(textProducerBuilder.create(), wordRendererBuilder.create());
        builder.addBuildSequence(textProducerBuilder);
        builder.addBuildSequence(wordRendererBuilder);
        return builder;
    }

    private static void parseTextOption(TextOptions textOptionType, String[] textOptions, CaptchaBuilder builder) throws ParseException {

        switch (textOptionType) {
            case TEXTPRODUCER:
                textProducerBuilder = TextProducerParser.parse(textOptions, textProducerBuilder);
                break;
            case WORDRENDERER:
                wordRendererBuilder = WordRendererParser.parse(textOptions, wordRendererBuilder);
                break;
            default:
                throw new ParseException("Text argument not found: " + textOptionType.name());
        }
    }

    private static class TextProducerParser {

        private static TextProducerBuilder parse(String[] textProducerOptions, TextProducerBuilder builder) throws ParseException {
            if (textProducerOptions.length == 0) {
                builder = new TextProducerBuilder(TextProducerType.REDUCED_ALPHANUMERIC);
            }

            if (textProducerOptions.length > 1) {
                throw new ParseException("TextProducer takes a max of 1 argument");
            }

            for (String textProducerOption : textProducerOptions) {
                if (!textProducerOption.isEmpty()) {
                    String[] optionArgs = textProducerOption.split(CaptchaConstants.buildSequencelvl4Delim);
                    TextProducerType textProducerType = TextProducerType.valueOf(optionArgs[0]);
                    String[] textProducerOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);

                    builder = new TextProducerBuilder(textProducerType);
                    builder = parseTextProducerOption(textProducerType, textProducerOptionArgs, builder);
                }
            }

            return builder;
        }

        private static TextProducerBuilder parseTextProducerOption(TextProducerType textProducerType, String[] textProducerOptionArgs, TextProducerBuilder builder) throws ParseException {
            if (textProducerOptionArgs.length == 0) {
                builder = new TextProducerBuilder(textProducerType);
            }

            if (textProducerOptionArgs.length > TextProducerOptions.values().length) {
                throw new ParseException("TextProducerType takes a max of " + TextProducerOptions.values().length + " arguments");
            }

            for (String textProducerTypeOption : textProducerOptionArgs) {
                if (!textProducerTypeOption.isEmpty()) {
                    String[] optionArgs = textProducerTypeOption.split(CaptchaConstants.buildSequencelvl5Delim);
                    TextProducerOptions textProducerOptionType = TextProducerOptions.valueOf(optionArgs[0]);
                    String[] textProducerTypeOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);

                    builder = parseTextProducerTypeOption(textProducerOptionType, textProducerTypeOptionArgs, builder);
                }
            }

            return builder;
        }

        private static TextProducerBuilder parseTextProducerTypeOption(TextProducerOptions textProducerOptionType, String[] textProducerTypeOptionArgs, TextProducerBuilder builder) throws ParseException {
            if (textProducerTypeOptionArgs.length != 1) {
                throw new ParseException("TextProducerOption " + textProducerOptionType.name() + " only takes one argument");
            }

            switch (textProducerOptionType) {
                case MINLENGTH:
                    try {
                        return builder.setMinLenght(Integer.parseInt(textProducerTypeOptionArgs[0]));
                    } catch (NumberFormatException e) {
                        throw new ParseException("Text TextProducer MinLength argument has an invalid number format");
                    }
                case MAXLENGTH:
                    try {
                        return builder.setMaxLenght(Integer.parseInt(textProducerTypeOptionArgs[0]));
                    } catch (NumberFormatException e) {
                        throw new ParseException("Text TextProducer MaxLength argument has an invalid number format");
                    }
                default:
                    throw new ParseException("TextProducerOptionType not found: " + textProducerOptionType.name());
            }
        }
    }

    private static class WordRendererParser {

        private static WordRendererBuilder parse(String[] wordRendererOptions, WordRendererBuilder builder) throws ParseException {
            if (wordRendererOptions.length == 0) {
                builder = new WordRendererBuilder(WordRendererType.DEFAULT);
            }

            if (wordRendererOptions.length > 1) {
                throw new ParseException("WordRenderer takes a max of 1 argument");
            }

            for (String wordRendererOption : wordRendererOptions) {
                if (!wordRendererOption.isEmpty()) {
                    String[] optionArgs = wordRendererOption.split(CaptchaConstants.buildSequencelvl4Delim);
                    WordRendererType wordRendererType = WordRendererType.valueOf(optionArgs[0]);
                    String[] wordRendererOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);

                    builder = parseWordRendererOption(wordRendererType, wordRendererOptionArgs, builder);
                }
            }

            return builder;
        }

        private static WordRendererBuilder parseWordRendererOption(WordRendererType wordRendererType, String[] wordRendererOptionArgs, WordRendererBuilder builder) throws ParseException {
            if (wordRendererOptionArgs.length == 0) {
                return builder;
            }

            if (wordRendererOptionArgs.length > WordRendererOptions.values().length) {
                throw new ParseException("WordRendererType takes a max of " + WordRendererOptions.values().length + " arguments");
            }

            for (String wordRendererTypeOption : wordRendererOptionArgs) {
                if (!wordRendererTypeOption.isEmpty()) {
                    String[] optionArgs = wordRendererTypeOption.split(CaptchaConstants.buildSequencelvl5Delim);
                    WordRendererOptions wordRendererOptionType = WordRendererOptions.valueOf(optionArgs[0]);
                    String[] wordRendererTypeOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);

                    builder = parseWordRendererTypeOption(wordRendererOptionType, wordRendererTypeOptionArgs, builder);
                }
            }

            return builder;
        }

        private static WordRendererBuilder parseWordRendererTypeOption(WordRendererOptions wordRendererOptionType, String[] wordRendererTypeOptionArgs, WordRendererBuilder builder) throws ParseException {
            switch (wordRendererOptionType) {
                case COLORS:
                    try {
                        if (wordRendererTypeOptionArgs.length != 1) {
                            throw new ParseException("WordRendererOption " + wordRendererOptionType.name() + " only takes one argument");
                        }
                        String[] colorArgs = wordRendererTypeOptionArgs[0].split(CaptchaConstants.buildSequencelvl6Delim);
                        return builder.setColorRange(ColorsParser.parse(colorArgs));
                    } catch (NumberFormatException e) {
                        throw new ParseException("Text WordRenderer colors has invalid formatted numbers");
                    }
                case FONTS:
                    if (wordRendererTypeOptionArgs.length < 1) {
                        throw new ParseException("WordRendererOption " + wordRendererOptionType.name() + " only takes one argument");
                    }
                    ArrayList<Font> fonts = new ArrayList<>();
                    for (String fontString : wordRendererTypeOptionArgs) {
                        String[] fontArgs = fontString.split(CaptchaConstants.buildSequencelvl6Delim);
                        fonts.add(new Font(fontArgs[0], Integer.parseInt(fontArgs[1]), Integer.parseInt(fontArgs[2])));
                    }
                    return builder.setFonts(fonts);
                case STROKE:
                    if (wordRendererTypeOptionArgs.length != 1) {
                        throw new ParseException("WordRendererOption " + wordRendererOptionType.name() + " only takes one argument");
                    }
                    try {
                        return builder.setStrokeWidth(Float.parseFloat(wordRendererTypeOptionArgs[0]));
                    } catch (NumberFormatException e) {
                        throw new ParseException("Text WordRenderer Stroke argument has an invalid number format");
                    }
                case XOFF:
                    if (wordRendererTypeOptionArgs.length != 1) {
                        throw new ParseException("WordRendererOption " + wordRendererOptionType.name() + " only takes one argument");
                    }
                    try {
                        return builder.setXOffset(Double.parseDouble(wordRendererTypeOptionArgs[0]));
                    } catch (NumberFormatException e) {
                        throw new ParseException("Text WordRenderer XOFF argument has an invalid number format");
                    }
                case YOFF:
                    if (wordRendererTypeOptionArgs.length != 1) {
                        throw new ParseException("WordRendererOption " + wordRendererOptionType.name() + " only takes one argument");
                    }
                    try {
                        return builder.setYOffset(Double.parseDouble(wordRendererTypeOptionArgs[0]));
                    } catch (NumberFormatException e) {
                        throw new ParseException("Text WordRenderer YOFF argument has an invalid number format");
                    }
                default:
                    throw new ParseException("WordRendeereOptionType not found: " + wordRendererOptionType.name());
            }
        }
    }

    enum TextOptions {

        TEXTPRODUCER,
        WORDRENDERER;
    }

    enum TextProducerOptions {

        MINLENGTH,
        MAXLENGTH;
    }

    enum WordRendererOptions {

        COLORS,
        FONTS,
        STROKE,
        XOFF,
        YOFF;
    }

    enum FontOptions {

        FONTNAME,
        FONTSTYLE,
        FONTSIZE;
    }
}