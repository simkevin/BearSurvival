package com.bearsurvival;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent musicIntent = new Intent(this, BackgroundMusicService.class);
        musicIntent.setAction("BackgroundMusicService");
        startService(musicIntent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                startGame();
        }
        return true;
    }

    public void startGame() {
        Intent startIntent = new Intent(this, GameActivity.class);
        startActivity(startIntent);
    }
}
