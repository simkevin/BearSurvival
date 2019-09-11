package com.bearsurvival;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


public class Player implements GameObject {

    private Rect player;
    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animManager;

    public Player(Rect player) {
        this.player = player;

        /* images created by doudoulolita
        link to licenses: https://opengameart.org/content/miks-a-2d-teddy-bear
        modifications include flipping of the images for walking left animations
         */
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bear);
        Bitmap walk1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walk1);
        Bitmap walk2 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walk2);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }

    public Rect getPlayerRect() {
        return player;
    }

    @Override
    public void draw(Canvas canvas) {
        animManager.draw(canvas, player);
    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = player.left;

        player.set(point.x - player.width() / 2, point.y - player.height() / 2,
                point.x + player.width() / 2, point.y + player.height() / 2);

        int state = 0;
        if(player.left - oldLeft > 5) {
            state = 1;
        } else if(player.left - oldLeft < -5) {
            state = 2;
        }

        animManager.playAnimation(state);
        animManager.update();
    }
}
