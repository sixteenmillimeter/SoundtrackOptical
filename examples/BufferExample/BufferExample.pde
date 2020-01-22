import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical soundtrack;
String type = "variable density";
PGraphics soundtrackBuffer;
PImage transform;

String soundtrackFile = "../../data/barking.wav";
int dpi = 2400;
float volume = 1.0;
String pitch = "long";
boolean positive = true;

void setup() {
  size(620, 213, P2D);
  soundtrack = new SoundtrackOptical(this, soundtrackFile, dpi, volume, type, pitch, positive);
  imageMode(CENTER);
}

void draw () {
  soundtrackBuffer = soundtrack.buffer(frameCount);
  transform = soundtrackBuffer.get();
  translate(width>>1, height>>1);
  rotate(HALF_PI);
  image(transform, 0, 0);
}