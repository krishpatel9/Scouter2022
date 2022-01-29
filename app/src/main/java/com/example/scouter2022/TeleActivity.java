package com.example.scouter2022;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TeleActivity extends AppCompatActivity {
    int scored_red_top = 0;
    int missed_red_top = 0;
    int scored_blue_top = 0;
    int missed_blue_top = 0;
    int scored_red_bottom = 0;
    int missed_red_bottom = 0;
    int scored_blue_bottom = 0;
    int missed_blue_bottom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele);

        View scored_red_top_button = findViewById(R.id.TeleScoredView1);
        TextView scored_red_top_text = findViewById(R.id.TeleScore1);

        scored_red_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scored_red_top += 1;
                scored_red_top_text.setText(String.valueOf(scored_red_top));
            }
        });

        View missed_red_top_button = findViewById(R.id.TeleMissedView1);
        TextView missed_red_top_text = findViewById(R.id.TeleMiss1);

        missed_red_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missed_red_top += 1;
                missed_red_top_text.setText(String.valueOf(missed_red_top));
            }
        });

        View scored_blue_top_button = findViewById(R.id.TeleScoredView3);
        TextView scored_blue_top_text = findViewById(R.id.TeleScore3);

        scored_blue_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scored_blue_top += 1;
                scored_blue_top_text.setText(String.valueOf(scored_blue_top));
            }
        });

        View missed_blue_top_button = findViewById(R.id.TeleMissedView3);
        TextView missed_blue_top_text = findViewById(R.id.TeleMiss3);

        missed_blue_top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missed_blue_top += 1;
                missed_blue_top_text.setText(String.valueOf(missed_blue_top));
            }
        });

        View scored_red_bottom_button = findViewById(R.id.TeleScoredcard_bg);
        TextView scored_red_bottom_text = findViewById(R.id.TeleScore2);

        scored_red_bottom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scored_red_bottom += 1;
                scored_red_bottom_text.setText(String.valueOf(scored_red_bottom));
            }
        });

        View missed_red_bottom_button = findViewById(R.id.TeleMissedcard_bg);
        TextView missed_red_bottom_text = findViewById(R.id.TeleMiss2);

        missed_red_bottom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missed_red_bottom += 1;
                missed_red_bottom_text.setText(String.valueOf(missed_red_bottom));
            }
        });

        View scored_blue_bottom_button = findViewById(R.id.TeleScoredView4);
        TextView scored_blue_bottom_text = findViewById(R.id.TeleScore4);

        scored_blue_bottom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scored_blue_bottom += 1;
                scored_blue_bottom_text.setText(String.valueOf(scored_blue_bottom));
            }
        });

        View missed_blue_bottom_button = findViewById(R.id.TeleMissedView4);
        TextView missed_blue_bottom_text = findViewById(R.id.TeleMiss4);

        missed_blue_bottom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missed_blue_bottom += 1;
                missed_blue_bottom_text.setText(String.valueOf(missed_blue_bottom));
            }
        });
    }
}
