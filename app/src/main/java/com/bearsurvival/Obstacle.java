package com.bearsurvival;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {

    private Rect rectLeft;
    private Rect rectRight;
    private int color;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        rectLeft = new Rect(0, startY, startX, startY + rectHeight);
        rectRight = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public Rect getRect() {
        return rectLeft;
    }

    public void incrementY(float y) {
        rectLeft.top += y;
        rectLeft.bottom += y;
        rectRight.top += y;
        rectRight.bottom += y;
    }

    public boolean playerCollides(Player player) {
        return Rect.intersects(rectLeft, player.getPlayerRect()) || Rect.intersects(rectRight, player.getPlayerRect());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectLeft, paint);
        canvas.drawRect(rectRight, paint);
    }

    @Override
    public void update() {
    }
}
