import processing.sound.*;
import soundtrack.optical.*;

SoundtrackOptical soundtrack;
SoundFile playback; 				//to be used to play audio

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
  playback = new SoundFile(this, soundtrackFile);

  playback.play(); //playback alongside image
}

void draw () {
  soundtrack.frame(0, 0);
}