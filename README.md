# soundtrack.optical

Library for generating 16mm optical soundtracks with Processing.

Install library by downloading library as .zip, uncompressing and placing SoundtrackOptical in your Processing library directory. Start up (or restart) Processing to use in a sketch.

Supports mono audio only (at the moment, feel free to contribute).

Draws various kids of 16mm soundtracks. [Read about them here.](http://www.paulivester.com/films/filmstock/guide.htm).

* unilateral
* *dual unilateral (in progress!)*
* single variable area
* dual variable area
* multiple variable area (Maurer)

### Example Usage

```java
import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical soundtrack;

String soundtrackFile = "./data/barking.wav";
int dpi = 2400;
float volume = 1.0;
String type = "dual variable area";
String pitch = "long";
boolean positive = true;

void setup() {
  size(213, 620);
  frameRate(24);
  soundtrack = new SoundtrackOptical(this, soundtrackFile, dpi, volume, type, pitch, positive);
}

void draw () {
  soundtrack.frame(0, 0);
}
```
