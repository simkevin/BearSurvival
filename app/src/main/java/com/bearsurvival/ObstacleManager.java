package com.bearsurvival;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class ObstacleManager {

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private float speed;
    private Stack<Integer> intervalTracker;
    private long startTime;
    private int score;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;

        obstacles = new ArrayList<>();
        speed = Constants.SCREEN_HEIGHT / 10000.0f;
        startTime = System.currentTimeMillis();

        intervalTracker = new Stack<>();
        intervalTracker.push(0);

        createObstacles();
    }

    public int getScore() {
        return score;
    }

    public boolean playerCollides(Player player) {
        for(Obstacle ob: obstacles) {
            if(ob.playerCollides(player)) {
                return true;
            }
        }
        return false;
    }

    private void createObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        Random rand = new Random();
        while(currY < 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            int randomColor = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            obstacles.add(new Obstacle(obstacleHeight, randomColor, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        if(startTime < Constants.INIT_TIME) {
            startTime = Constants.INIT_TIME;
        }
        int timeElapsed = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        if (score % 10 == 0 && score != intervalTracker.peek()) {
            intervalTracker.push(score);
            speed = 1.25f * speed;
        }

        for(Obstacle ob: obstacles) {
            ob.incrementY(speed * timeElapsed);
        }

        if(obstacles.get(obstacles.size() - 1).getRect().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            Random rand = new Random();
            int randomColor = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            obstacles.add(0, new Obstacle(obstacleHeight, randomColor, xStart, obstacles.get(0).getRect().top
                    - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle ob: obstacles) {
            ob.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.CYAN);
        canvas.drawText("SCORE: " + score, 50, 150, paint);
    }
}
