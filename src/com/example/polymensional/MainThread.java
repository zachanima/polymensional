package com.example.polymensional;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
  private SurfaceHolder surfaceHolder;
  private MainGamePanel gamePanel;
  private boolean running;
  
  private final static int MAX_FPS = 50;
  private final static int MAX_FRAME_SKIPS = 5;
  private final static int FRAME_PERIOD = 1000 / MAX_FPS;
  
  public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
    super();
    this.surfaceHolder = surfaceHolder;
    this.gamePanel = gamePanel;
  }
  
  public void setRunning(boolean running) {
    this.running = running;
  }
  
  @Override
  public void run() {
    Canvas canvas;
    
    long beginTime;
    long timeDiff;
    int sleepTime;
    int framesSkipped;
    
    sleepTime = 0;
    
    while (running) {
      canvas = null;
      
      try {
        canvas = this.surfaceHolder.lockCanvas();
        synchronized (surfaceHolder) {
          beginTime = System.currentTimeMillis();
          framesSkipped = 0;
          
          // Update game state.
          this.gamePanel.update();

          // Render state to the screen.
          
          // Draw canvas on panel.
          this.gamePanel.onDraw(canvas);
          
          timeDiff = System.currentTimeMillis() - beginTime;
          sleepTime = (int)(FRAME_PERIOD - timeDiff);
          
          if (sleepTime > 0) {
            try {
              Thread.sleep(sleepTime);
            } catch (InterruptedException e) {}
          }
          
          while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
            this.gamePanel.update();
            sleepTime += FRAME_PERIOD;
            framesSkipped++;
          }
        }
      } finally {
        // Avoid leaving surface in inconsistent state on exception.
        if (canvas != null) {
          surfaceHolder.unlockCanvasAndPost(canvas);
        }
      }
    }
  }
}
