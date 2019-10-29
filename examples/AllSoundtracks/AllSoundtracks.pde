import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical soundtrack1;
SoundtrackOptical soundtrack2;
SoundtrackOptical soundtrack3;
SoundtrackOptical soundtrack4;
SoundtrackOptical soundtrack5;

//String soundtrackFile = "../../data/barking.wav";
String soundtrackFile = "/Users/matthewmcwilliams9/Documents/Processing/optical_soundtrack/barking.wav";
int dpi = 2400;
float volume = 1.0;
String pitch = "long";
boolean positive = true;

void setup() {
  size(1065, 620);
  soundtrack1 = new SoundtrackOptical(this, soundtrackFile, dpi, volume, "unilateral", pitch, positive);
  soundtrack2 = new SoundtrackOptical(this, soundtrackFile, dpi, volume, "variable area", pitch, positive);
  soundtrack3 = new SoundtrackOptical(this, soundtrackFile, dpi, volume, "dual variable area", pitch, positive);
  soundtrack4 = new SoundtrackOptical(this, soundtrackFile, dpi, volume, "maurer", pitch, positive);
  soundtrack5 = new SoundtrackOptical(this, soundtrackFile, dpi, volume, "variable density", pitch, positive);
}

void draw () {
  soundtrack1.frame(0, 0);
  soundtrack2.frame(213, 0);
  soundtrack3.frame(213 * 2, 0);
  soundtrack4.frame(213 * 3, 0);
  soundtrack5.frame(213 * 4, 0);
  
  stroke(255, 0, 0);
  for (int i = 1; i < 5; i++) {
    line(213 * i, 0, 213 * i, height);
  }
}