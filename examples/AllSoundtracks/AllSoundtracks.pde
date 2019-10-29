import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical[] soundtracks = {null, null, null, null, null };
String[] types = { "unilateral", "variable area", "dual variable area", "maurer", "variable density" };

String soundtrackFile = "../../data/barking.wav";
int dpi = 2400;
float volume = 1.0;
String pitch = "long";
boolean positive = true;

void setup() {
  size(1065, 620, P2D);
  for (int i = 0; i < types.length; i++) {
    soundtracks[i] = new SoundtrackOptical(this, soundtrackFile, dpi, volume, types[i], pitch, positive);
  }
}

void draw () {
  for (int i = 0; i < types.length; i++) {
    soundtracks[i].frame(i * 213, 0);
  }
  
  stroke(255, 0, 0);
  for (int i = 1; i < types.length; i++) {
    line(213 * i, 0, 213 * i, height);
  }
}