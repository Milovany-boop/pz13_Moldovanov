package com.example.pz13_moldovanov;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SinglePlayerActivity extends AppCompatActivity {

    private ImageView carPlayer1;
    private ImageView carPlayer2;
    private Button btnMove;
    private Button btnExit;
    private TextView tvResult;
    private int playerPosition = 0;
    private int opponentPosition = 0;
    private boolean raceStarted = false;
    private final Handler handler = new Handler();
    private Runnable opponentMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player);

        carPlayer1 = findViewById(R.id.carPlayer1);
        carPlayer2 = findViewById(R.id.carPlayer2);
        btnMove = findViewById(R.id.btnMove);
        btnExit = findViewById(R.id.btnExit);
        tvResult = findViewById(R.id.tvResult);

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!raceStarted) {
                    startRace();
                } else {
                    movePlayerCar();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();
            }
        });

        opponentMove = new Runnable() {
            @Override
            public void run() {
                if (raceStarted) {
                    moveOpponentCar();
                    handler.postDelayed(this, 50);
                }
            }
        };
    }

    private void startRace() {
        raceStarted = true;
        playerPosition = 0;
        opponentPosition = 0;
        tvResult.setVisibility(View.GONE);
        carPlayer1.setTranslationX(0);
        carPlayer2.setTranslationX(0);
        btnMove.setText("DRIVE");
        handler.post(opponentMove);
    }

    private void movePlayerCar() {
        playerPosition += 15;
        updateCarPosition(carPlayer1, playerPosition);
        checkRaceEnd();
    }

    private void moveOpponentCar() {
        opponentPosition += 5;
        updateCarPosition(carPlayer2, opponentPosition);
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
        if (playerPosition >= screenWidth - 350) {
            endRace("YOU WIN!");
        } else if (opponentPosition >= screenWidth - 350) {
            endRace("OPPONENT WINS!");
        }
    }

    private void endRace(String message) {
        raceStarted = false;
        handler.removeCallbacks(opponentMove);
        tvResult.setText(message);
        tvResult.setVisibility(View.VISIBLE);
        btnMove.setText("RESTART");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainMenu();
            }
        }, 2000);
    }

    private void goToMainMenu() {
        Intent intent = new Intent(SinglePlayerActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}