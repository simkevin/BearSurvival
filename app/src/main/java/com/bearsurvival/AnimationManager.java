package com.bearsurvival;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimationManager {

    private Animation[] animations;
    private int animationIdx;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    public void playAnimation(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if(!animations[index].isPlaying()) {
                    animations[i].play();
                }
            }
            else {
                animations[i].stop();
            }
        }
        animationIdx = index;
    }

    public void draw(Canvas canvas, Rect rect) {
        if(animations[animationIdx].isPlaying()) {
            animations[animationIdx].draw(canvas, rect);
        }
    }

    public void update() {
        if(animations[animationIdx].isPlaying()) {
            animations[animationIdx].update();
        }
    }
}
