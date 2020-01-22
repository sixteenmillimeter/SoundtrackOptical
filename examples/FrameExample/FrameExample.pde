import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical soundtrack;
String type = "dual variable area";

String soundtrackFile = "../../data/barking.wav";
int dpi = 2400;
float volume = 1.0;
String pitch = "long";
boolean positive = true;

void setup() {
  size(1065, 620, P2D);
  soundtrack = new SoundtrackOptical(this, soundtrackFile, dpi, volume, type, pitch, positive);
}

void draw () {
  for (int i = 0; i < 5; i++) {
    soundtrack.frame(i * 213, 0, frameCount + i);
  }
  
  stroke(255, 0, 0);
  for (int i = 1; i < 5; i++) {
    line(213 * i, 0, 213 * i, height);
  }
}