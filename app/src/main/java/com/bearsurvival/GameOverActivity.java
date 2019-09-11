package com.bearsurvival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView scoreLabel = (TextView) findViewById(R.id.score_label);
        TextView highscoreLabel = (TextView) findViewById(R.id.highscore_label);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText("SCORE: " + score);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if(score > highScore) {
            highscoreLabel.setText("HIGH SCORE: " + score);

             SharedPreferences.Editor editor = settings.edit();
             editor.putInt("HIGH_SCORE", score);
             editor.commit();
        } else {
            highscoreLabel.setText("HIGH SCORE: " + highScore);
        }

        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameplayIntent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(gameplayIntent);
            }
        });
    }
}
