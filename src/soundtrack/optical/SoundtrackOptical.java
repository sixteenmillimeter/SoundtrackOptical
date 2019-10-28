package soundtrack.optical;

import processing.sound.*;
import processing.core.*;

public class SoundtrackOptical {
  String TYPE = "dual variable area"; //type of soundtrack
  int DPI = 2880; //dots/in
  boolean POSITIVE = true;
  float VOLUME = 1.0;
  String pitch = "long";
  //String filePath = "wavTones.com.unregistred.sweep_100Hz_6000Hz_-3dBFS_5s.wav";
  String FILEPATH = "barking.wav";
  
  float IN      = 25.4; //mm/in
  float FRAME_H = 7.62; //mm
  float FRAME_W = 12.52 - 10.26;; //mm
  
  float DPMM = DPI / IN;
  int FRAME_H_PIXELS = round(DPMM * FRAME_H);
  int SAMPLE_RATE    = FRAME_H_PIXELS * 24;
  int DEPTH          = round(DPMM * FRAME_W);
  
  int LINE_W        = 0;
  float DENSITY     = 0;
  int LEFT          = 0;
  int FRAMES        = 0;
  int i = 0;
  
  float max = 0;
  float min = 0;
  float compare;
  
  Sound s;
  AudioSample sample;
  float[] frameSample = new float[FRAME_H_PIXELS];
  SoundFile soundfile;
  
  Soundtrack (PApplet parent, String soundtrackFile, int dpi, float volume, String type, String pitch, boolean positive ) {
    FILEPATH = soundtrackFile;
    TYPE = type;
    DPI = dpi;
    VOLUME = volume;
    POSITIVE = positive;
    FRAME_H = pitch == "long" ? 7.62 : 7.605;
    
    DPMM = DPI / IN;
    FRAME_H_PIXELS = round(DPMM * FRAME_H);
    SAMPLE_RATE    = FRAME_H_PIXELS * 24;
    DEPTH          = floor(DPMM * FRAME_W);
    
    soundfile = new SoundFile(parent, FILEPATH);
    soundfile.rate(SAMPLE_RATE);
  
    FRAMES = ceil(soundfile.frames() / FRAME_H_PIXELS);
    
    /*
      println("SAMPLE RATE ");
      println(SAMPLE_RATE);
      println("FRAME_H");
      println(FRAME_H_PIXELS);
      println("DEPTH ");
      println(DEPTH);
      println("FRAMES");
      println(FRAMES);
    */
  }
  public void frame(int X, int Y) {
    if (i >= FRAMES) {
      return;
    }
    
   if (POSITIVE) {
      background(0);
      stroke(255);
    } else {
      background(255);
      stroke(0);
    }
    
    soundfile.read(i * FRAME_H_PIXELS, frameSample, 0, FRAME_H_PIXELS);
  
    for (int y = 0; y < FRAME_H_PIXELS; y++) {
      LINE_W = round(map(frameSample[y], min, max, 0, DEPTH * VOLUME));
      //println();
      if (TYPE == "unilateral") {
        line(X + 0, Y + y, X + LINE_W, Y + y);
      } else if (TYPE == "dual unilateral") {
        /* TODO!!!! */
      } else if (TYPE == "single variable area") {
        LEFT = X + round((DEPTH - LINE_W) / 2);
        line(LEFT, Y + y, LEFT + LINE_W, Y + y);
      } else if (TYPE == "dual variable area") {
        LEFT = X + round((DEPTH / 4) - (LINE_W / 4));
        line(LEFT, Y + y, LEFT + (LINE_W / 2), Y + y);
        line(LEFT + (DEPTH / 2), Y + y, LEFT + (DEPTH / 2) + (LINE_W / 2), Y + y);
      } else if (TYPE == "multiple variable area" || TYPE == "maurer") {
        LEFT = X + round((DEPTH / 16) - (LINE_W / 16));
        for (int x = 1; x < 7; x++) {
           line(LEFT + ((x * DEPTH) / 8), Y + y, LEFT + ((x * DEPTH) / 8) + (LINE_W / 8), Y + y);
        }
      } else if (TYPE == "variable density") {
        DENSITY = map(frameSample[y], min, max, 0, 255 * VOLUME);
        if (POSITIVE) {
          stroke(DENSITY);
        } else {
          stroke(255 - DENSITY);
        }
        line(X + 0, Y + y, X + DEPTH, Y + y);
      }
    }
    i++;
  }
}
