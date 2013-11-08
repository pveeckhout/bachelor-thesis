bachelor-thesis
===============

the collection of my bachelor thesis paper, implementation and presentation.

The whole colletcion of my thesis is licensed under the MIT license, so all code (except possibly some third-party libraries) and documents are open source and can be freely reused.

Paper
=====

# Solving CAPTCHA with neural networks
This project is my bachelor thesis for University College Ghent, professional Bachelor of applied Computer Science

The thesis will be based on my personal experiences trying to device a working system to crack CAPTCHA images as well as on literature study.

The code (Java) written for this is all opensources and will be freely available on my personal website an on my BitBucket repository. All programs written for this thesis are my own intelectual work and will be released using the MIT License.

As per medium used for the thesis, I chose for LaTeX. LaTeX is still a new system for me to discover, but I feel that it will be an good system for writing a thesis and that it will be valuable learning experience to be had at the end of my academic endevours.

Implementation
==============

#CAPTCHA buildstring examples

Background:

-b :BACKGROUND!TWOCOLORGRADIENT#COLORS1@RANGE*FF0000*000000#COLORS2@LIST*00FF00*0000FFF:

Border:

-b :BORDER!SOLID#COLORS@RANGE*000000*ffffff#THICKNESS@4:

Noise:

-b :NOISE!CURVEDLINE#COLORS@RANGE*000000*ffffff#THICKNESS@2.3:

Gimp:

-b :GIMP!FISHEYE#DOUBLE1@3.7#DOUBLE2@7.6#COLORS1@RANGE*000000*ffffff#COLORS2@LIST*a5f9b3*1586df*de48af:

Text:

-b :TEXT!TEXTPRODUCER#REDUCED_ALPHANUMERIC@MINLENGTH*3@MAXLENGTH*9!WORDRENDERER#COLOREDEDGES@XOFF*0.06@YOFF*0.35@STROKE*6@COLORS*RANGE.484a2f.595ffe3@FONTS*ARIAL.1.37*COURIER.3.45:

Combine by placing in sequence and repeating... exp:

-b :BACKGROUND!TWOCOLORGRADIENT#COLORS1@RANGE*000000*ffffff#COLORS2@RANGE*000000*ffffff:BORDER!SOLID#COLORS@RANGE*ff000*ffff000#THICKNESS@4:NOISE!CURVEDLINE#COLORS@RANGE*00ff00*ffffff#THICKNESS@2.3:GIMP!FISHEYE#DOUBLE1@3.7#DOUBLE2@7.6#COLORS1@RANGE*1d285a*bce693#COLORS2@LIST*8b13f3*acea42*16e28d*a462b9:TEXT!TEXTPRODUCER#REDUCED_ALPHANUMERIC@MINLENGTH*3@MAXLENGTH*9!WORDRENDERER#DEFAULT@XOFF*0.06@YOFF*0.35@STROKE*6@COLORS*RANGE.000000.333333@FONTS*ARIAL.1.37*COURIER.3.45:GIMP!FISHEYE#DOUBLE1@3.7#DOUBLE2@7.6#COLORS1@RANGE*bbbbbb*121221#COLORS2@LIST*864ad8*ab84ef:NOISE!CURVEDLINE#COLORS@RANGE*000000*ffffff#THICKNESS@2.3:

BEWARE:
	only the last :BACKGROUND: encountered will be used.

Presentation
============

The presentation is again made by using LaTeX.