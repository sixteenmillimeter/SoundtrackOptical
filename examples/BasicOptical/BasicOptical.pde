import processing.sound.*; //requires Sound library to work
import soundtrack.optical.*;

SoundtrackOptical soundtrack;

String soundtrackFile = "../../data/barking.wav"; 	//path to your mono audio file
int dpi = 2400;                            			//the "resolution" of your soundtrack (library is designed for printing)
float volume = 1.0;                       			//volume of the output, scaled from 0 to 1
String type = "dual variable area";       			//one of the following [unilateral, single variable area, dual variable area, multiple variable area] 
String pitch = "long";                    			//whether the film is "long" or "short" pitch
boolean positive = true;                  			//whether the film is positive or negative

void setup() {
  size(213, 620);								//this will perfectly fill the frame with the soundtrack @ 2400DPI
  													//must run in P2D or P2D (acheives realtime playback easier)
  
  frameRate(24); 									//this will playback at realtime speed
  soundtrack = new SoundtrackOptical(this, soundtrackFile, dpi, volume, type, pitch, positive);
}

void draw () {
  soundtrack.draw(0, 0);
}