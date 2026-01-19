package com.example.pz13_moldovanov;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TwoPlayersActivity extends AppCompatActivity {

    private ImageView carPlayer1;
    private ImageView carPlayer2;
    private Button btnPlayer1;
    private Button btnPlayer2;
    private Button btnExit;
    private TextView tvResult;
    private int player1Position = 0;
    private int player2Position = 0;
    private boolean raceStarted = false;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_players);

        carPlayer1 = findViewById(R.id.carPlayer1);
        carPlayer2 = findViewById(R.id.carPlayer2);
        btnPlayer1 = findViewById(R.id.btnPlayer1);
        btnPlayer2 = findViewById(R.id.btnPlayer2);
        btnExit = findViewById(R.id.btnExit);
        tvResult = findViewById(R.id.tvResult);

        btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!raceStarted) {
                    startRace();
                } else {
                    movePlayer1Car();
                }
            }
        });

        btnPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!raceStarted) {
                    startRace();
                } else {
                    movePlayer2Car();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();
            }
        });
    }

    private void startRace() {
        raceStarted = true;
        player1Position = 0;
        player2Position = 0;
        tvResult.setVisibility(View.GONE);
        carPlayer1.setTranslationX(0);
        carPlayer2.setTranslationX(0);
    }

    private void movePlayer1Car() {
        player1Position += 10;
        updateCarPosition(carPlayer1, player1Position);
        checkRaceEnd();
    }

    private void movePlayer2Car() {
        player2Position += 10;
        updateCarPosition(carPlayer2, player2Position);
        checkRaceEnd();
    }

    private void updateCarPosition(ImageView car, int position) {
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        float maxMove = screenWidth - 350;
        float move = Math.min(position, maxMove);
        car.setTranslationX(move);
    }

    private void checkRaceEnd() {
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        if (player1Position >= screenWidth - 350) {
            endRace("PLAYER 1 WINS!");
        } else if (player2Position >= screenWidth - 350) {
            endRace("PLAYER 2 WINS!");
        }
    }

    private void endRace(String message) {
        raceStarted = false;
        tvResult.setText(message);
        tvResult.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainMenu();
            }
        }, 2000);
    }

    private void goToMainMenu() {
        Intent intent = new Intent(TwoPlayersActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}