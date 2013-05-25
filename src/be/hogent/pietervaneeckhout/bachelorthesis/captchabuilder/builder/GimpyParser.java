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

import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.elementcreator.renderer.gimpy.GimpyRendererBuilder;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.CaptchaConstants;
import be.hogent.pietervaneeckhout.bachelorthesis.captchabuilder.util.enums.renderer.GimpyRendererType;
import java.util.Arrays;
import org.apache.commons.cli.ParseException;

/**
 * GimpyParser.java (UTF-8)
 *
 * Parses the string arguments for rendering transformations
 *
 * 2013/04/17
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.8
 * @version 1.0.13
 */
public class GimpyParser {

    /**
     * Parses the string arguments for rendering transformations, creates a
     * GimpyRenderer and passes it to the CaptchaBuilder
     *
     * @param buildSequenceOptions the string arguments for building
     * transformations
     * @param builder the CaptchaBuilder Object to be modified
     * @return a modified CaptchaBuilder object
     * @throws org.apache.commons.cli.ParseException
     * @see CaptchaBuilder
     */
    public static CaptchaBuilder parse(String[] buildSequenceOptions, CaptchaBuilder builder) throws ParseException {
        if (buildSequenceOptions.length == 0) {
            //return builder.gimp();
            builder.addBuildSequence(new GimpyRendererBuilder(GimpyRendererType.RIPPLE));
            return builder;
        }

        if (buildSequenceOptions.length > GimpyRendererOptions.values().length) {
            throw new ParseException("Background takes a max of " + GimpyRendererOptions.values().length + " arguments");
        }

        for (String gimpyOption : buildSequenceOptions) {
            if (!gimpyOption.isEmpty()) {
                try {
                    String[] optionArgs = gimpyOption.split(CaptchaConstants.buildSequencelvl3Delim);
                    GimpyRendererType gimpyRenenderType = GimpyRendererType.valueOf(optionArgs[0]);
                    String[] gimpyOptions = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);
                    return parseGimpyRenderer(gimpyRenenderType, gimpyOptions, builder);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(e.getMessage());
                }
            }
        }

        return builder;
    }

    private static CaptchaBuilder parseGimpyRenderer(GimpyRendererType gimpyRendererType, String[] gimpyOptions, CaptchaBuilder builder) throws ParseException {
        GimpyRendererBuilder gimpyRendererBuilder = new GimpyRendererBuilder(gimpyRendererType);

        if (gimpyOptions.length == 0) {
            //return builder.gimp(gimpyRendererBuilder.create());
            builder.addBuildSequence(gimpyRendererBuilder);
            return builder;
        }

        if (gimpyOptions.length > GimpyRendererOptions.values().length) {
            throw new ParseException("BackgroundProducer takes a max of " + GimpyRendererOptions.values().length + " arguments");
        }

        for (String gimpyRendererOption : gimpyOptions) {
            String[] optionArgs = gimpyRendererOption.split(CaptchaConstants.buildSequencelvl4Delim);
            try {
                GimpyRendererOptions gimpyRendererOptionType = GimpyRendererOptions.valueOf(optionArgs[0]);
                String[] gimpyRendererOptionArgs = Arrays.copyOfRange(optionArgs, 1, optionArgs.length);
                gimpyRendererBuilder = parseGimpyRendererOption(gimpyRendererOptionType, gimpyRendererOptionArgs, gimpyRendererBuilder);
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage());
            }
        }

        //return builder.gimp(gimpyRendererBuilder.create());
        builder.addBuildSequence(gimpyRendererBuilder);
        return builder;
    }

    private static GimpyRendererBuilder parseGimpyRendererOption(GimpyRendererOptions gimpyRendererOptionType, String[] gimpyRendererOptionArgs, GimpyRendererBuilder gimpyRendererBuilder) throws ParseException {
        if (gimpyRendererOptionArgs.length != 1) {
            throw new ParseException("GimpyRenderer option " + gimpyRendererOptionType.name() + " only takes 1 argument");
        }

        String arg = gimpyRendererOptionArgs[0];
        String[] colorArgs;

        switch (gimpyRendererOptionType) {
            case DOUBLE1:
                try {
                    return gimpyRendererBuilder.setD1(Double.parseDouble(arg));
                } catch (NumberFormatException e) {
                    throw new ParseException("Gimp double1 argument has an invalid number format");
                }
            case DOUBLE2:
                try {
                    return gimpyRendererBuilder.setD2(Double.parseDouble(arg));
                } catch (NumberFormatException e) {
                    throw new ParseException("Gimp double2 argument has an invalid number format");
                }
            case COLORS1:
                try {
                    colorArgs = arg.split(CaptchaConstants.buildSequencelvl5Delim);
                    return gimpyRendererBuilder.setColorRange1(ColorsParser.parse(colorArgs));
                } catch (NumberFormatException e) {
                    throw new ParseException("Gimp colors1 has invalid formatted numbers");
                }
            case COLORS2:
                try {
                    colorArgs = arg.split(CaptchaConstants.buildSequencelvl5Delim);
                    return gimpyRendererBuilder.setColorRange2(ColorsParser.parse(colorArgs));
                } catch (NumberFormatException e) {
                    throw new ParseException("Border colors2 has invalid formatted numbers");
                }
            default:
                throw new ParseException("GimpyRenderer option not found: " + gimpyRendererOptionType.name());
        }
    }

    enum GimpyOptions {

        DEFAULT;
    }

    enum GimpyRendererOptions {

        DOUBLE1,
        DOUBLE2,
        COLORS1,
        COLORS2;
    }
}
