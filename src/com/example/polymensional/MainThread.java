package com.example.polymensional;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
  private SurfaceHolder surfaceHolder;
  private MainGamePanel gamePanel;
  private boolean running;
  
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
    
    while (running) {
      canvas = null;
      
      try {
        canvas = this.surfaceHolder.lockCanvas();
        synchronized (surfaceHolder) {
          // Update game state.
          // Draw canvas on panel.
          this.gamePanel.onDraw(canvas);
        }
      } finally {
        // Avoid leaving surface in inconsistent state on exception.
        if (canvas != null) {
          surfaceHolder.unlockCanvasAndPost(canvas);
        }
      }
      // Render state to the screen.
    }
  }
}
