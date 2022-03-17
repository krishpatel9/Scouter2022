package com.example.scouter2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.scouter2022.model.TransferCode;
import com.example.scouter2022.util.Defaults;
import com.example.scouter2022.util.PreferenceUtility;
import com.google.gson.Gson;

import java.util.Map;

public class TeleActivity extends AppCompatActivity {
    private static final String TAG = "TeleActivity";
    private TextView teleTeamNumber;
    private TextView teleMatchNumber;
    private TextView teleAllianceColor;

    private GridLayout shootingGrid;
    private View phaseBarView;
    private View topView;

    private CheckBox shootingCheckBox;
    private CheckBox tarmacCheckBox;
    private TextView scored_home_top_text;
    private TextView scored_home_bot_text;
    private TextView scored_away_top_text;
    private TextView missed_home_top_text;
    private TextView missed_home_bot_text;

    private TextView topScoredMinus;
    private TextView botScoredMinus;
    private TextView topMissedMinus;
    private TextView botMissedMinus;
    private TextView awayBallsMinus;

//    private TextView missed_away_top_text;
//    private TextView missed_away_bot_text;

    private View scored_home_top_view;
    private View scored_home_bot_view;
    private View scored_away_top_view;
//    private View scored_away_bot_view;
    private View missed_home_top_view;
    private View missed_home_bot_view;
//    private View missed_away_top_view;
//    private View missed_away_bot_view;

    private ImageView toEndGame;
    private ImageView toAuto;

    int crossTarmac = 0;
    int shootAttempted = 0;
    int scored_home_top = 0;
    int missed_home_top = 0;
    int scored_away_top = 0;
//    int missed_away_top = 0;
    int scored_home_bot = 0;
    int missed_home_bot = 0;
//    int scored_away_bot = 0;
//    int missed_away_bot = 0;


    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            this.getSupportActionBar().hide();}
        catch(NullPointerException e){
        }

        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        teleTeamNumber = findViewById(R.id.qrTeamNumTextView);
        teleMatchNumber = findViewById(R.id.qrMatchNumTextView);
        teleAllianceColor = findViewById(R.id.qrColorTextView);

        shootingCheckBox = findViewById(R.id.tele_checkbox_shooting);

        scored_home_top_text = findViewById(R.id.TeleScore1);
        scored_home_bot_text = findViewById(R.id.TeleScore2);
        scored_away_top_text = findViewById(R.id.TeleAwayBallsScore);
//        scored_away_bot_text = findViewById(R.id.TeleScore4);

        missed_home_top_text = findViewById(R.id.TeleMiss1);
        missed_home_bot_text = findViewById(R.id.TeleMiss2);
//        missed_away_top_text = findViewById(R.id.TeleMiss3);
//        missed_away_bot_text = findViewById(R.id.TeleMiss4);

        scored_home_top_view = findViewById(R.id.TeleScoredView1);
        scored_home_bot_view = findViewById(R.id.TeleScoredView2);
        scored_away_top_view = findViewById(R.id.TeleAwayBallsView);
//        scored_away_bot_view = findViewById(R.id.TeleScoredView4);

        missed_home_top_view = findViewById(R.id.TeleMissedView1);
        missed_home_bot_view = findViewById(R.id.TeleMissedView2);
//        missed_away_top_view = findViewById(R.id.TeleMissedView3);
//        missed_away_bot_view = findViewById(R.id.TeleMissedView4);

        topScoredMinus = findViewById(R.id.teleTopScoredMinus);
        botScoredMinus = findViewById(R.id.teleBotScoredMinus);
        topMissedMinus = findViewById(R.id.teleTopMissedMinus);
        botMissedMinus = findViewById(R.id.teleBotMissedMinus);
        awayBallsMinus = findViewById(R.id.teleAwayBallsMinus);


        shootingGrid = findViewById(R.id.teleShootingGridLayout);
        phaseBarView = findViewById(R.id.TelePhaseViewBar);
        topView = findViewById(R.id.teleTopView);

        toEndGame = findViewById(R.id.TeleToEndGame);
        toAuto = findViewById(R.id.TeleToAuto);
        tcode = new TransferCode();

        Intent intent = getIntent();
        if (intent != null) {
            String json = intent.getStringExtra("code");
            Log.i(TAG, "onCreate: intent JSON ==> " + json);

            Gson gson = new Gson();
            tcode = gson.fromJson(json, TransferCode.class);
            Log.i(TAG, "Existing TCODE from Intent..." + tcode.toString());


            setAllValuesFromObject();
            showAllValues();
        }
        teleTeamNumber.setText("Team Number: " + tcode.getTeamNumber());
        teleMatchNumber.setText("Match Number: " + tcode.getMatchNumber());
        setComponentBackground(tcode.getIsRed());


        scored_home_top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_top<= Defaults.MAX_CARGO_NUMBER) {
                    scored_home_top += 1;
                    tcode.setTele_allianceCargo_top_s(scored_home_top);
                    scored_home_top_text.setText(String.valueOf(scored_home_top));
                }
            }
        });
        missed_home_top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_top<= Defaults.MAX_CARGO_NUMBER) {
                    missed_home_top += 1;
                    tcode.setTele_allianceCargo_top_f(missed_home_top);
                    missed_home_top_text.setText(String.valueOf(missed_home_top));
                }
            }
        });
        scored_home_bot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_bot<= Defaults.MAX_CARGO_NUMBER) {
                    scored_home_bot += 1;
                    tcode.setTele_allianceCargo_bot_s(scored_home_bot);
                    scored_home_bot_text.setText(String.valueOf(scored_home_bot));
                }
            }
        });
        missed_home_bot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_bot<= Defaults.MAX_CARGO_NUMBER) {
                    missed_home_bot += 1;
                    tcode.setTele_allianceCargo_bot_f(missed_home_bot);
                    missed_home_bot_text.setText(String.valueOf(missed_home_bot));
                }
            }
        });
        scored_away_top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_away_top<= Defaults.MAX_CARGO_NUMBER) {
                    scored_away_top += 1;
                    tcode.setTele_away_balls(scored_away_top);
                    scored_away_top_text.setText(String.valueOf(scored_away_top));
                }
            }
        });

        topScoredMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_top>0) {
                    scored_home_top -= 1;
                    tcode.setTele_allianceCargo_top_s(scored_home_top);
                    scored_home_top_text.setText(String.valueOf(scored_home_top));
                }
            }
        });
        topMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_top>0) {
                    missed_home_top -= 1;
                    tcode.setTele_allianceCargo_top_f(missed_home_top);
                    missed_home_top_text.setText(String.valueOf(missed_home_top));
                }
            }
        });
        botScoredMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_bot>0) {
                    scored_home_bot -= 1;
                    tcode.setTele_allianceCargo_bot_s(scored_home_bot);
                    scored_home_bot_text.setText(String.valueOf(scored_home_bot));
                }
            }
        });
        botMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_bot>0) {
                    missed_home_bot -= 1;
                    tcode.setTele_allianceCargo_bot_f(missed_home_bot);
                    missed_home_bot_text.setText(String.valueOf(missed_home_bot));
                }
            }
        });
        awayBallsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_away_top>0) {
                    scored_away_top -= 1;
                    tcode.setTele_away_balls(scored_away_top);
                    scored_away_top_text.setText(String.valueOf(scored_away_top));
                }
            }
        });

        shootingCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setTele_shoot_attempt(1);
                } else {
                    tcode.setTele_shoot_attempt(0);
                }
            }
        });
        toEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToEndGameActivity();
            }
        });  // End of TeleButton

        toAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToAutoActivity();
            }
        });  // End of TeleButton

        showAllValues();
    }

    private void proceedToEndGameActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "proceedToEndGameActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(TeleActivity.this, EndGameActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);

//        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
//        tcode = allMatches.get(key);
//        Log.i(TAG, "After proceedTeleOpActivity(): " + tcode.toString());
    }
    private void backToAutoActivity(){
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "backToAutoActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(TeleActivity.this, AutoActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);

//        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
//        tcode = allMatches.get(key);
//        Log.i(TAG, "After proceedTeleOpActivity(): " + tcode.toString());
    }
    private void saveMatch() {
        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
        Log.i(TAG, "saveMatch, KEY: " + key + ", TCODE..: " + tcode.toString());
        allMatches.put(key, tcode);
        PreferenceUtility.saveAllMatches(getApplicationContext(), allMatches);
    }
    private void showAllValues() {
        if (tcode.getTele_shoot_attempt() == 1) {
            shootingCheckBox.setChecked(true);
        }

//        missed_away_top_text.setText(String.valueOf(missed_away_top));
        missed_home_bot_text.setText(String.valueOf(missed_home_bot));
        missed_home_top_text.setText(String.valueOf(missed_home_top));
//        missed_away_bot_text.setText(String.valueOf(missed_away_bot));
        scored_away_top_text.setText(String.valueOf(scored_away_top));
        scored_home_bot_text.setText(String.valueOf(scored_home_bot));
        scored_home_top_text.setText(String.valueOf(scored_home_top));
//        scored_away_bot_text.setText(String.valueOf(scored_away_bot));

    }
    private void setAllValuesFromObject() {
        scored_home_top = tcode.getTele_allianceCargo_top_s();
        missed_home_top = tcode.getTele_allianceCargo_top_f();
        scored_away_top = tcode.getTele_away_balls();
//        missed_away_top = tcode.getTele_opponentCargo_top_f();

        scored_home_bot = tcode.getTele_allianceCargo_bot_s();
        missed_home_bot = tcode.getTele_allianceCargo_bot_f();
//        scored_away_bot = tcode.getTele_opponentCargo_bot_s();
//        missed_away_bot = tcode.getTele_opponentCargo_bot_f();
    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            teleAllianceColor.setText("Red Alliance");
//            topView.setScaleX(-1);
            shootingGrid.setBackgroundResource(R.drawable.card_bg_red);
//            phaseBarView.setBackgroundResource(R.drawable.bottom_view_red);
            scored_home_top_view.setBackgroundResource(R.drawable.card_bg_red);
            scored_home_bot_view.setBackgroundResource(R.drawable.card_bg_red);
            missed_home_top_view.setBackgroundResource(R.drawable.card_bg_red);
            missed_home_bot_view.setBackgroundResource(R.drawable.card_bg_red);
            scored_away_top_view.setBackgroundResource(R.drawable.card_bg_blue);
//            scored_away_bot_view.setBackgroundResource(R.drawable.card_bg_blue);
//            missed_away_top_view.setBackgroundResource(R.drawable.card_bg_blue);
//            missed_away_bot_view.setBackgroundResource(R.drawable.card_bg_blue);
            teleAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            teleAllianceColor.setText("Blue Alliance");
//            topView.setScaleX(1);
            shootingGrid.setBackgroundResource(R.drawable.card_bg_blue);
//            phaseBarView.setBackgroundResource(R.drawable.bottom_view_blue);
            scored_home_top_view.setBackgroundResource(R.drawable.card_bg_blue);
            scored_home_bot_view.setBackgroundResource(R.drawable.card_bg_blue);
            missed_home_top_view.setBackgroundResource(R.drawable.card_bg_blue);
            missed_home_bot_view.setBackgroundResource(R.drawable.card_bg_blue);
            scored_away_top_view.setBackgroundResource(R.drawable.card_bg_red);
//            scored_away_bot_view.setBackgroundResource(R.drawable.card_bg_red);
//            missed_away_top_view.setBackgroundResource(R.drawable.card_bg_red);
//            missed_away_bot_view.setBackgroundResource(R.drawable.card_bg_red);
            teleAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);

        }
    }

    protected void updateValues() {
        Log.i(TAG, "updateValues: ");
        if (tcode.getTele_shoot_attempt() == 1) {
            shootingCheckBox.setChecked(true);
        }
        scored_home_top = tcode.getTele_allianceCargo_top_s();
        missed_home_top = tcode.getTele_allianceCargo_top_f();
        scored_away_top = tcode.getTele_away_balls();
//        missed_away_top = tcode.getTele_opponentCargo_top_f();

        scored_home_bot = tcode.getTele_allianceCargo_bot_s();
        missed_home_bot = tcode.getTele_allianceCargo_bot_f();
//        scored_away_bot = tcode.getTele_opponentCargo_bot_s();
//        missed_away_bot = tcode.getTele_opponentCargo_bot_f();

    }
    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart: ");
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }
    @Override
    public void onResume() {
        Log.i(TAG, "onResume: Inside of TeleActivity...");
        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
        tcode = allMatches.get(key);

        super.onResume();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: ");
        Gson gson = new Gson();
        String json = gson.toJson(tcode);
        outState.putString(INSTANCE_STATE, json);
        saveMatch();
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);
        String json = savedInstanceState.getString(INSTANCE_STATE);
        Gson gson = new Gson();
        tcode = gson.fromJson(json, TransferCode.class);
        updateValues();
    }
    @Override
    public boolean onSupportNavigateUp() {
        saveMatch();
        onBackPressed();
        return true;
    }

}
