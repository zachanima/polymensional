package com.example.polymensional;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
  public MainGamePanel(Context context) {
    super(context);
    
    // Add callback to surface holder to intercept events.
    getHolder().addCallback(this);
    
    // Make GamePanel focusable to handle events.
    setFocusable(true);
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
  }

  public void surfaceCreated(SurfaceHolder holder) {
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }
  
  @Override
  protected void onDraw(Canvas canvas) {
  }
}
