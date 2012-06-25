package com.example.polymensional;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.polymensional.model.Ship;
import com.example.polymensional.model.components.Speed;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
  private MainThread thread;
  private Ship ship;
  
  public MainGamePanel(Context context) {
    super(context);
    
    // Add callback to surface holder to intercept events.
    getHolder().addCallback(this);
    
    // Create ship and load bitmap
    ship = new Ship(BitmapFactory.decodeResource(getResources(), R.drawable.ship), 50, 50);
    
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
      // Delegate event handling to ship.
      ship.handleActionDown((int)event.getX(), (int)event.getY());
      
      // Exit if 50 bottom pixels are touched.
      if (event.getY() > getHeight() - 50) {
        thread.setRunning(false);
        ((Activity)getContext()).finish();
      }
    }
    if (event.getAction() == MotionEvent.ACTION_MOVE) {
      if (ship.isTouched()) {
        ship.setX((int)event.getX());
        ship.setY((int)event.getY());
      }
    }
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (ship.isTouched()) {
        ship.setTouched(false);
      }
    }
    return true;
  }
  
  protected void render(Canvas canvas) {
    canvas.drawColor(Color.BLACK);
    ship.draw(canvas);
  }
  
  public void update() {
    // Check collision with right wall if heading right.
    if (ship.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
        && ship.getX() + ship.getBitmap().getWidth() / 2 >= getWidth()) {
      ship.getSpeed().toggleXDirection();
    }
    
    // Check collision with left wall if heading left.
    if (ship.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
        && ship.getX() - ship.getBitmap().getWidth() / 2 <= 0) {
      ship.getSpeed().toggleXDirection();
    }
    
    // Check collision with bottom wall if heading down.
    if (ship.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
        && ship.getY() + ship.getBitmap().getHeight() / 2 >= getHeight()) {
      ship.getSpeed().toggleYDirection();
    }

    // Check collision with top wall if heading up.
    if (ship.getSpeed().getyDirection() == Speed.DIRECTION_UP
        && ship.getY() - ship.getBitmap().getHeight() / 2 <= 0) {
      ship.getSpeed().toggleYDirection();
    }
    
    // Update the lone ship.
    ship.update();
  }
}
