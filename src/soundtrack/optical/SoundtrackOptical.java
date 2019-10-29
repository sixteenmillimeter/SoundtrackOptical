package soundtrack.optical;

import processing.core.*;
import processing.sound.*;

public class SoundtrackOptical {
  String TYPE = "dual variable area"; //type of soundtrack
  int DPI = 2880; //dots/in
  boolean POSITIVE = true;
  float VOLUME = (float) 1.0;
  String pitch = "long";
  //String filePath = "wavTones.com.unregistred.sweep_100Hz_6000Hz_-3dBFS_5s.wav";
  String FILEPATH;
  
  float IN      = (float) 25.4; //mm/in
  float FRAME_H = (float) 7.62; //mm
  float FRAME_W = (float) (12.52 - 10.26); //mm
  
  float DPMM = (float) (DPI / IN);
  int FRAME_H_PIXELS = (int) Math.round(DPMM * FRAME_H);
  int SAMPLE_RATE    = FRAME_H_PIXELS * 24;
  int DEPTH          = (int) Math.round(DPMM * FRAME_W);
  
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
  PApplet parent;
  
  @SuppressWarnings("static-access")
  public SoundtrackOptical (PApplet parent, String soundtrackFile, int dpi, float volume, String type, String pitch, boolean positive ) {
	this.parent = parent;
	  
	FILEPATH = soundtrackFile;
    TYPE = type;
    DPI = dpi;
    VOLUME = volume;
    POSITIVE = positive;
    FRAME_H = (float) ((pitch == "long") ? 7.62 : 7.605);
    DPMM = DPI / IN;
    FRAME_H_PIXELS = (int) Math.round(DPMM * FRAME_H);
    SAMPLE_RATE    = FRAME_H_PIXELS * 24;
    DEPTH          = (int) Math.floor(DPMM * FRAME_W);
    
    soundfile = new SoundFile(parent, FILEPATH);

    parent.println("OLD SAMPLE_RATE: " + soundfile.sampleRate());

    soundfile.rate(SAMPLE_RATE);
  
    FRAMES = (int) Math.ceil(soundfile.frames() / FRAME_H_PIXELS);
    

    parent.println("SAMPLE_RATE: " + SAMPLE_RATE);
    parent.println("FRAMES: " + FRAMES);
    parent.println("WIDTH: " + DEPTH);
    parent.println("HEIGHT: " + FRAME_H_PIXELS);
    
    for (int x = 0; x < soundfile.frames(); x++) {
        compare = soundfile.read(x);
        if (compare > max) {
          max = compare;
        }
        if (compare < min) {
          min = compare;
        }
      }
    parent.println(min);
    parent.println(max);
  }
  @SuppressWarnings("static-access")
  public void frame(int X, int Y) {
    if (i >= FRAMES) {
      return;
    }

    //draw bg
    parent.noStroke();
    if (POSITIVE) {
      parent.fill(0);
    } else {
      parent.fill(255);
    }

    if (TYPE != "variable density") {
      parent.rect(X, Y, DEPTH, FRAME_H_PIXELS);
    }
    
    //draw top
    if (POSITIVE) {
      parent.stroke(255);
    } else {
      parent.stroke(0);
    }
    
    soundfile.read(i * FRAME_H_PIXELS, frameSample, 0, FRAME_H_PIXELS);
  
    for (int y = 0; y < FRAME_H_PIXELS; y++) {
      LINE_W = Math.round(parent.map(frameSample[y], min, max, (float) 0, DEPTH * VOLUME));
      if (TYPE == "unilateral") {
        parent.line(X + 0, Y + y, X + LINE_W, Y + y);
      } else if (TYPE == "dual unilateral") {
        /* TODO!!!! */
      } else if (TYPE == "variable area" || TYPE == "single variable area") {
        LEFT = X + Math.round((DEPTH - LINE_W) / 2);
        parent.line(LEFT, Y + y, LEFT + LINE_W, Y + y);
      } else if (TYPE == "dual variable area") {
        LEFT = X + Math.round((DEPTH / 4) - (LINE_W / 4));
        parent.line(LEFT, Y + y, LEFT + (LINE_W / 2), Y + y);
        parent.line(LEFT + (DEPTH / 2), Y + y, LEFT + (DEPTH / 2) + (LINE_W / 2), Y + y);
      } else if (TYPE == "multiple variable area" || TYPE == "maurer") {
        LEFT = X + Math.round((DEPTH / 16) - (LINE_W / 16));
        for (int x = 1; x < 7; x++) {
           parent.line(LEFT + ((x * DEPTH) / 8), Y + y, LEFT + ((x * DEPTH) / 8) + (LINE_W / 8), Y + y);
        }
      } else if (TYPE == "variable density") {
        DENSITY = parent.map(frameSample[y], min, max, (float) 0, 255 * VOLUME);
        if (POSITIVE) {
          parent.stroke(DENSITY);
        } else {
          parent.stroke(255 - DENSITY);
        }
        parent.line(X + 0, Y + y, X + DEPTH, Y + y);
      }
    }
    i++;
  }
}
