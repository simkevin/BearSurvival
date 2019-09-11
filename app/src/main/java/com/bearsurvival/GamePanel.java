package com.bearsurvival;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private GameplayScene game;

    public GamePanel (Context context) {
        super(context);
        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        game = new GameplayScene();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(!game.checkGameState()) {
            game.draw(canvas);
        }
        else {
            game.draw(canvas);
            thread.setRunning(false);

            Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
            gameOverIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            gameOverIntent.putExtra("SCORE", game.getScore());
            getContext().startActivity(gameOverIntent);
        }
    }

    public void update() {
        game.update();
    }
}
