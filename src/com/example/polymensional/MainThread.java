package com.example.polymensional;

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
    while (running) {
      // Update game state.
      // Render state to the screen.
    }
  }
}
