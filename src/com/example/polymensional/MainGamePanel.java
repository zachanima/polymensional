package com.example.polymensional;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
  private MainThread thread;
  
  public MainGamePanel(Context context) {
    super(context);
    
    // Add callback to surface holder to intercept events.
    getHolder().addCallback(this);
    
    // Create the game loop thread.
    thread = new MainThread(getHolder(), this);
    
    // Make GamePanel focusable to handle events.
    setFocusable(true);
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
  }

  public void surfaceCreated(SurfaceHolder holder) {
    thread.setRunning(true);
    thread.start();
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
    boolean retry = true;
    while (retry) {
      try {
        thread.join();
        retry = false;
      } catch (InterruptedException e) {
        // Try to shut down thread again.
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      if (event.getY() > getHeight() - 50) {
        thread.setRunning(false);
        ((Activity)getContext()).finish();
      }
    }
    return super.onTouchEvent(event);
  }
  
  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship), 10, 10, null);
  }
}
