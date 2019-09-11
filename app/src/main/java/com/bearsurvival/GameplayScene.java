package com.bearsurvival;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

public class GameplayScene {

    private Player player;
    private Point playerPt;
    private ObstacleManager obManager;
    private boolean gameOver = false;
    private OrientationData orientationData;
    private long frameTime;

    private long gameOverTime;

    public GameplayScene() {
        player = new Player(new Rect(0, 0, 150, 150));
        playerPt = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 150);
        player.update(playerPt);

        obManager = new ObstacleManager(300, 500, 75);

        orientationData = new OrientationData();
        orientationData.register();

        frameTime = System.currentTimeMillis();
    }

    public boolean checkGameState() {
        return gameOver;
    }

    public int getScore() {
        return obManager.getScore();
    }

    public void update() {
        if(!gameOver) {
            if(frameTime < Constants.INIT_TIME) {
                frameTime = Constants.INIT_TIME;
            }
            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;
                playerPt.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
            }

            if(playerPt.x < 0) {
                playerPt.x = 0;
            }
            else if(playerPt.x > Constants.SCREEN_WIDTH) {
                playerPt.x = Constants.SCREEN_WIDTH;
            }

            player.update(playerPt);
            obManager.update();
            if(obManager.playerCollides(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        obManager.draw(canvas);
    }
}
