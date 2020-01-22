# soundtrack.optical

[Download library](https://github.com/sixteenmillimeter/SoundtrackOptical/archive/master.zip)

Library for generating 16mm optical soundtracks with Processing.

Install library by downloading library as .zip, uncompressing and [placing SoundtrackOptical in your Processing library directory](https://github.com/processing/processing/wiki/How-to-Install-a-Contributed-Library). Start up (or restart) Processing to use in a sketch.

Supports mono audio only (at the moment, feel free to contribute).

Draws various kinds of 16mm soundtracks. [Read about them here.](http://www.paulivester.com/films/filmstock/guide.htm).

* unilateral
* *dual unilateral (in progress!)*
* single variable area
* dual variable area
* multiple variable area (Maurer)
* variable density

### Example Usage

```java
import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical soundtrack;

String soundtrackFile = "../../data/barking.wav";
int dpi = 2400;
float volume = 1.0;
String type = "dual variable area";
String pitch = "long";
boolean positive = true;

void setup() {
  size(213, 620, P2D);
  frameRate(24);
  soundtrack = new SoundtrackOptical(this, soundtrackFile, dpi, volume, type, pitch, positive);
}

void draw () {
  soundtrack.draw(0, 0);
}
```

### Alternate usages

Use the `frame(int x, int y, int frameNumber)` method to draw specific frames--used for laying out multiple frames of soundtrack on a single screen.

```java
void draw () {
	soundtrack.frame(0, 0, frameCount);
}

```

Use the `buffer(int frameNumber)` method to return the internal `PGraphics` object that contains the specific frame of soundtrack data specified by the frameNumber. You can then draw onto the canvas, address the pixels directly, or as in the provided BufferExample.pde assign the image data to a PImage to be manipulated using the built-in transformation methods.

```java
void draw () {
	PGraphics soundtrackBuffer = soundtrack.buffer(frameCount);
	image(soundtrackBuffer, 0, 0);
}

```
