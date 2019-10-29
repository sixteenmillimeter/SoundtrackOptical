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

  int RAW_RATE = 0;
  int RAW_FRAME_H = 0;
  int RAW_FRAME_W = 0;
  
  int LINE_W        = 0;
  float DENSITY     = 0;
  int LEFT          = 0;
  int FRAMES        = 0;
  int i = 0;
  
  float max = 0;
  float min = 0;
  float compare;
  
  float[] frameSample;
  SoundFile soundfile;
  PGraphics raw;
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

    RAW_RATE = soundfile.sampleRate();
    RAW_FRAME_H = Math.round(RAW_RATE / 24);
    RAW_FRAME_W = Math.round(((RAW_RATE / 24) / FRAME_H ) * FRAME_W);
    FRAMES = (int) Math.ceil(soundfile.frames() / RAW_FRAME_H);
    
    frameSample = new float[RAW_FRAME_H];
    raw = parent.createGraphics(RAW_FRAME_W, RAW_FRAME_H);

        
    for (int x = 0; x < soundfile.frames(); x++) {
        compare = soundfile.read(x);
        if (compare > max) {
          max = compare;
        }
        if (compare < min) {
          min = compare;
        }
      }
  }
  @SuppressWarnings("static-access")
  public void frame(int X, int Y) {
    if (i >= FRAMES) {
      return;
    }
    raw.beginDraw();
    //draw bg
    raw.noStroke();
    if (POSITIVE) {
      raw.fill(0);
    } else {
      raw.fill(255);
    }

    if (TYPE != "variable density") {
      raw.rect(0, 0, RAW_FRAME_W, RAW_FRAME_H);
    }
    
    //draw top
    if (POSITIVE) {
      raw.stroke(255);
    } else {
      raw.stroke(0);
    }
    
    soundfile.read(i * RAW_FRAME_H, frameSample, 0, RAW_FRAME_H);
  
    for (int y = 0; y < RAW_FRAME_H; y++) {
      if (TYPE != "variable density") {
        LINE_W = Math.round(parent.map(frameSample[y], min, max, (float) 0, RAW_FRAME_W * VOLUME));
      }
      if (TYPE == "unilateral") {
        unilateral(y, LINE_W);
      } else if (TYPE == "dual unilateral") {
        /* TODO!!!! */
      } else if (TYPE == "single variable area" || TYPE == "variable area") {
        variableArea(y, LINE_W);
      } else if (TYPE == "dual variable area") {
        dualVariableArea(y, LINE_W);
      } else if (TYPE == "multiple variable area" || TYPE == "maurer") {
        multipleVariableArea(y, LINE_W);
      } else if (TYPE == "variable density") {
        variableDensity(y);
      }
    }
    raw.endDraw();
    parent.image(raw, X, Y, DEPTH, FRAME_H_PIXELS);
    i++;
  }

  private void unilateral (int y, int LINE_W) {
    raw.line(0, y, LINE_W, y);
  }

  private void variableArea (int y, int LINE_W) {
    LEFT = Math.round((RAW_FRAME_W - LINE_W) / 2);
    raw.line(LEFT, y, LEFT + LINE_W, y);
  }

  private void dualVariableArea (int y, int LINE_W) {
    LEFT = Math.round((RAW_FRAME_W / 4) - (LINE_W / 4));
    raw.line(LEFT, y, LEFT + (LINE_W / 2), y);
    raw.line(LEFT + (RAW_FRAME_W / 2), y, LEFT + (RAW_FRAME_W / 2) + (LINE_W / 2), y);
  }

  private void multipleVariableArea (int y, int LINE_W) {
    LEFT = Math.round((RAW_FRAME_W / 16) - (LINE_W / 16));
    for (int x = 1; x < 7; x++) {
      raw.line(LEFT + ((x * RAW_FRAME_W) / 8), y, LEFT + ((x * RAW_FRAME_W) / 8) + (LINE_W / 8), y);
    }
  }
  
  private void variableDensity(int y) {
    DENSITY = parent.map(frameSample[y], min, max, (float) 0, 255 * VOLUME);
    if (POSITIVE) {
      raw.stroke(DENSITY);
    } else {
      raw.stroke(255 - DENSITY);
    }
    raw.line(0, y, RAW_FRAME_W, y);
  }
}
