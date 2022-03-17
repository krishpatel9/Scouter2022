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

public class AutoActivity extends AppCompatActivity {
    private static final String TAG = "TeleActivity";
    private TextView autoTeamNumber;
    private TextView autoMatchNumber;
    private TextView autoAllianceColor;
    
    private GridLayout shootingGrid;
    private GridLayout tarmacGrid;
    private View phaseBarView;
    private View topView;

    private CheckBox shootingCheckBox;
    private CheckBox tarmacCheckBox;
    private TextView scored_home_top_text;
    private TextView scored_home_bot_text;
    private TextView away_balls_text;
    private TextView missed_home_top_text;
    private TextView missed_home_bot_text;

    private View scored_home_top_view;
    private View scored_home_bot_view;
    private View away_balls_view;
    private View missed_home_top_view;
    private View missed_home_bot_view;

    private View human_missed_view;
    private View human_scored_view;
    private TextView human_missed_TextView;
    private TextView human_scored_TextView;

    private TextView topScoredMinus;
    private TextView botScoredMinus;
    private TextView topMissedMinus;
    private TextView botMissedMinus;
    private TextView awayBallsMinus;
    private TextView humanScoredMinus;
    private TextView humanMissedMinus;

    private ImageView toTele;
    private ImageView toMain;

    int crossTarmac = 0;
    int shootAttempted = 0;
    int scored_home_top = 0;
    int missed_home_top = 0;
    int away_balls = 0;
    int scored_home_bot = 0;
    int missed_home_bot = 0;
    int human_missed = 0;
    int human_scored = 0;


    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            this.getSupportActionBar().hide();}
        catch(NullPointerException e){
        }
        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        autoTeamNumber = findViewById(R.id.qrTeamNumTextView);
        autoMatchNumber = findViewById(R.id.qrMatchNumTextView);
        autoAllianceColor = findViewById(R.id.qrColorTextView);

        shootingCheckBox = findViewById(R.id.checkbox_shooting);
        tarmacCheckBox = findViewById(R.id.checkbox_tarmac);
        scored_home_top_text = findViewById(R.id.autoScore1);
        scored_home_bot_text = findViewById(R.id.autoScore2);
        away_balls_text = findViewById(R.id.autoAwayBallsScore);

        missed_home_top_text = findViewById(R.id.autoMiss1);
        missed_home_bot_text = findViewById(R.id.autoMiss2);

        scored_home_top_view = findViewById(R.id.autoScoredView1);
        scored_home_bot_view = findViewById(R.id.autoScoredView2);

        away_balls_view = findViewById(R.id.autoAwayBallsView);

        missed_home_top_view = findViewById(R.id.autoMissedView);
        missed_home_bot_view = findViewById(R.id.autoMissedView2);

        human_missed_view = findViewById(R.id.autoHumanMissedView);
        human_scored_view = findViewById(R.id.autoHumanScoredView);
        human_missed_TextView = findViewById(R.id.humanMissedNumText);
        human_scored_TextView = findViewById(R.id.humanScoredNumText);

        topScoredMinus = findViewById(R.id.autoTopScoredMinus);
        botScoredMinus = findViewById(R.id.autoBotScoredMinus);
        topMissedMinus = findViewById(R.id.autoTopMissedMinus);
        botMissedMinus = findViewById(R.id.autoBotMissedMinus);
        awayBallsMinus = findViewById(R.id.autoAwayBallsMinus);
        humanScoredMinus = findViewById(R.id.autoHPScoredMinus);
        humanMissedMinus = findViewById(R.id.autoHPMissedMinus);


        shootingGrid = findViewById(R.id.qrInfoLayout);
        tarmacGrid = findViewById(R.id.tarmacGridLayout);
        phaseBarView = findViewById(R.id.autoPhaseViewBar);
        topView = findViewById(R.id.autoTopView);

        toTele = findViewById(R.id.AutoToTele);
        toMain = findViewById(R.id.AutoToMain);
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
        autoTeamNumber.setText("Team Number: " + tcode.getTeamNumber());
        autoMatchNumber.setText("Match Number: " + tcode.getMatchNumber());
        setComponentBackground(tcode.getIsRed());

        human_missed_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(human_missed<= Defaults.MAX_CARGO_NUMBER) {
                    human_missed += 1;
                    tcode.setAuto_humanMissed(human_missed);
                    human_missed_TextView.setText(String.valueOf(human_missed));
                }
            }
        });
        human_scored_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(human_scored<= Defaults.MAX_CARGO_NUMBER) {
                    human_scored += 1;
                    tcode.setAuto_humanScored(human_scored);
                    human_scored_TextView.setText(String.valueOf(human_scored));
                }
            }
        });
        
        scored_home_top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_top<= Defaults.MAX_CARGO_NUMBER) {
                    scored_home_top += 1;
                    tcode.setAuto_allianceCargo_top_s(scored_home_top);
                    scored_home_top_text.setText(String.valueOf(scored_home_top));
                }
            }
        });
        missed_home_top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_top<= Defaults.MAX_CARGO_NUMBER) {
                    missed_home_top += 1;
                    tcode.setAuto_allianceCargo_top_f(missed_home_top);
                    missed_home_top_text.setText(String.valueOf(missed_home_top));
                }
            }
        });
        scored_home_bot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_bot<= Defaults.MAX_CARGO_NUMBER) {
                    scored_home_bot += 1;
                    tcode.setAuto_allianceCargo_bot_s(scored_home_bot);
                    scored_home_bot_text.setText(String.valueOf(scored_home_bot));
                }
            }
        });
        missed_home_bot_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_bot<= Defaults.MAX_CARGO_NUMBER) {
                    missed_home_bot += 1;
                    tcode.setAuto_allianceCargo_bot_f(missed_home_bot);
                    missed_home_bot_text.setText(String.valueOf(missed_home_bot));
                }
            }
        });
        away_balls_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(away_balls <= Defaults.MAX_CARGO_NUMBER) {
                    away_balls += 1;
                    tcode.setAuto_away_balls(away_balls);
                    away_balls_text.setText(String.valueOf(away_balls));
                }
            }
        });

        topScoredMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_top>0) {
                    scored_home_top -= 1;
                    tcode.setAuto_allianceCargo_top_s(scored_home_top);
                    scored_home_top_text.setText(String.valueOf(scored_home_top));
                }
            }
        });
        topMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_top>0) {
                    missed_home_top -= 1;
                    tcode.setAuto_allianceCargo_top_f(missed_home_top);
                    missed_home_top_text.setText(String.valueOf(missed_home_top));
                }
            }
        });
        botScoredMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scored_home_bot>0) {
                    scored_home_bot -= 1;
                    tcode.setAuto_allianceCargo_bot_s(scored_home_bot);
                    scored_home_bot_text.setText(String.valueOf(scored_home_bot));
                }
            }
        });
        botMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(missed_home_bot>0) {
                    missed_home_bot -= 1;
                    tcode.setAuto_allianceCargo_bot_f(missed_home_bot);
                    missed_home_bot_text.setText(String.valueOf(missed_home_bot));
                }
            }
        });
        awayBallsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(away_balls>0) {
                    away_balls -= 1;
                    tcode.setAuto_away_balls(away_balls);
                    away_balls_text.setText(String.valueOf(away_balls));
                }
            }
        });
        humanScoredMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(human_scored>0) {
                    human_scored -= 1;
                    tcode.setAuto_away_balls(human_scored);
                    human_scored_TextView.setText(String.valueOf(human_scored));
                }
            }
        });
        humanMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(human_missed>0) {
                    human_missed -= 1;
                    tcode.setAuto_away_balls(human_missed);
                    human_missed_TextView.setText(String.valueOf(human_missed));
                }
            }
        });
        

        shootingCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setAuto_shoot_attempt(1);
                } else {
                    tcode.setAuto_shoot_attempt(0);
                }
            }
        });
        tarmacCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setAuto_cross_line(1);
                } else {
                    tcode.setAuto_cross_line(0);
                }
            }
        });
        toTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedTeleOpActivity();
            }
        });  // End of AutoButton

        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });  // End of AutoButton

        showAllValues();
    }

    private void proceedTeleOpActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "proceedTeleOpActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(AutoActivity.this, TeleActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);

//        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
//        tcode = allMatches.get(key);
//        Log.i(TAG, "After proceedTeleOpActivity(): " + tcode.toString());
    }
    private void backToMain(){
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "backToMain: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(AutoActivity.this, MainActivity.class);
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
        if (tcode.getAuto_cross_line() == 1) {
            tarmacCheckBox.setChecked(true);
        }
        if (tcode.getAuto_shoot_attempt() == 1) {
            shootingCheckBox.setChecked(true);
        }

        missed_home_bot_text.setText(String.valueOf(missed_home_bot));
        missed_home_top_text.setText(String.valueOf(missed_home_top));
        away_balls_text.setText(String.valueOf(away_balls));
        scored_home_bot_text.setText(String.valueOf(scored_home_bot));
        scored_home_top_text.setText(String.valueOf(scored_home_top));
        human_missed_TextView.setText(String.valueOf(human_missed));
        human_scored_TextView.setText(String.valueOf(human_scored));

    }
    private void setAllValuesFromObject() {
        scored_home_top = tcode.getAuto_allianceCargo_top_s();
        missed_home_top = tcode.getAuto_allianceCargo_top_f();
        away_balls = tcode.getAuto_away_balls();

        scored_home_bot = tcode.getAuto_allianceCargo_bot_s();
        missed_home_bot = tcode.getAuto_allianceCargo_bot_f();

        human_scored = tcode.getAuto_humanScored();
        human_missed = tcode.getAuto_humanMissed();
    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            autoAllianceColor.setText("Red Alliance");
//            topView.setScaleX(-1);
            shootingGrid.setBackgroundResource(R.drawable.card_bg_red);
            tarmacGrid.setBackgroundResource(R.drawable.card_bg_red);
//            phaseBarView.setBackgroundResource(R.drawable.bottom_view_red);
            scored_home_top_view.setBackgroundResource(R.drawable.card_bg_red);
            scored_home_bot_view.setBackgroundResource(R.drawable.card_bg_red);
            missed_home_top_view.setBackgroundResource(R.drawable.card_bg_red);
            missed_home_bot_view.setBackgroundResource(R.drawable.card_bg_red);
            human_scored_view.setBackgroundResource(R.drawable.card_bg_red);
            human_missed_view.setBackgroundResource(R.drawable.card_bg_red);
            away_balls_view.setBackgroundResource(R.drawable.card_bg_blue);
            autoAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            autoAllianceColor.setText("Blue Alliance");
//            topView.setScaleX(1);
            shootingGrid.setBackgroundResource(R.drawable.card_bg_blue);
            tarmacGrid.setBackgroundResource(R.drawable.card_bg_blue);
//            phaseBarView.setBackgroundResource(R.drawable.bottom_view_blue);
            scored_home_top_view.setBackgroundResource(R.drawable.card_bg_blue);
            scored_home_bot_view.setBackgroundResource(R.drawable.card_bg_blue);
            missed_home_top_view.setBackgroundResource(R.drawable.card_bg_blue);
            missed_home_bot_view.setBackgroundResource(R.drawable.card_bg_blue);
            human_scored_view.setBackgroundResource(R.drawable.card_bg_blue);
            human_missed_view.setBackgroundResource(R.drawable.card_bg_blue);
            away_balls_view.setBackgroundResource(R.drawable.card_bg_red);
            autoAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);

        }
    }

    protected void updateValues() {
        Log.i(TAG, "updateValues: ");
        if (tcode.getAuto_cross_line() == 1) {
            tarmacCheckBox.setChecked(true);
        }
        if (tcode.getAuto_shoot_attempt() == 1) {
            shootingCheckBox.setChecked(true);
        }
        scored_home_top = tcode.getAuto_allianceCargo_top_s();
        missed_home_top = tcode.getAuto_allianceCargo_top_f();
        away_balls = tcode.getAuto_away_balls();

        scored_home_bot = tcode.getAuto_allianceCargo_bot_s();
        missed_home_bot = tcode.getAuto_allianceCargo_bot_f();

        human_scored = tcode.getAuto_humanScored();
        human_missed = tcode.getAuto_humanMissed();

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
