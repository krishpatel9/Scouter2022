package com.example.scouter2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.scouter2022.model.TransferCode;
import com.example.scouter2022.util.Defaults;
import com.example.scouter2022.util.PreferenceUtility;
import com.google.gson.Gson;

import java.util.Map;

public class FinalActivity extends AppCompatActivity {
    private static final String TAG = "FinalActivity";
    private static final float ENABLED = 1f;
    private static final float DISABLED = 0.15f;
    private TextView finalTeamNumber;
    private TextView finalMatchNumber;
    private TextView finalAllianceColor;

    private View phaseBarView;
    private View topView;

    private ImageView toEndGame;
    private ImageView toQr;
    private ImageView clearBtn1;
    private TextView clearBtn2;

    private GridLayout foulsGrid;
    private GridLayout disabledGrid;
    private GridLayout disqualifiedGrid;
    private ConstraintLayout foulsConstraint;
    private ConstraintLayout winningConstraint;

    private TextView yellowTextView;
    private TextView redTextView;
    private TextView techTextView;
    
    private View yellowView;
    private View redView;
    private View techView;
    private int numYellow,numRed,numTech;
    
    private ImageView yellowPlusBtn, yellowMinusBtn, redPlusBtn, redMinusBtn, techPlusBtn, techMinusBtn;
    private CheckBox foulsCheck, disabledCheck, disqualifiedCheck;
    private RadioGroup winning_RG;
    private RadioButton redWinBtn;
    private RadioButton blueWinBtn;
    private RadioButton tieBtn;

    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        finalTeamNumber = findViewById(R.id.finalTeamNumTextView);
        finalMatchNumber = findViewById(R.id.finalMatchNumTextView);
        finalAllianceColor = findViewById(R.id.finalColorTextView);
        
        phaseBarView = findViewById(R.id.FinalPhaseViewBar);
        topView = findViewById(R.id.finalTopView);
        
        toQr = findViewById(R.id.FinalToQr);
        toEndGame = findViewById(R.id.FinalToEndGame);
        
        foulsGrid = findViewById(R.id.finalFoulsCheckGridLayout);
        foulsConstraint = findViewById(R.id.finalFoulsContraintLayout);
        disabledGrid = findViewById(R.id.finalDisabledGridLayout);
        disqualifiedGrid = findViewById(R.id.finalDisqualifiedGridLayout);
        winningConstraint = findViewById(R.id.finalWinningContraintLayout);
        
        yellowTextView = findViewById(R.id.finalYellowTextView);
        yellowMinusBtn = findViewById(R.id.finalYellowMinusView);
        yellowPlusBtn = findViewById(R.id.finalYellowPlusView);
        yellowView = findViewById(R.id.finalYellowView);

        redTextView = findViewById(R.id.finalRedTextView);
        redMinusBtn = findViewById(R.id.finalRedMinusView);
        redPlusBtn = findViewById(R.id.finalRedPlusView);
        redView = findViewById(R.id.finalRedView);

        techTextView = findViewById(R.id.finalTechTextView);
        techMinusBtn = findViewById(R.id.finalTechMinusView);
        techPlusBtn = findViewById(R.id.finalTechPlusView);
        techView = findViewById(R.id.finalTechView);
        
        clearBtn1 = findViewById(R.id.finalClearBtn);

        clearBtn2 = findViewById(R.id.finalClearBtn2);

        foulsCheck = findViewById(R.id.finalFoulsCheckBox);
        disabledCheck = findViewById(R.id.finalDisabledCheckBox);
        disqualifiedCheck = findViewById(R.id.finalDisqualifiedCheckBox);

        winning_RG = findViewById(R.id.finalWinningAlliance_RG);
        redWinBtn = findViewById(R.id.finalRedWinBtn);
        blueWinBtn = findViewById(R.id.finalBlueWinBtn);
        tieBtn = findViewById(R.id.finalDrawWinBtn);


        Intent intent = getIntent();
        if (intent != null) {
            String json = intent.getStringExtra("code");
            Log.i(TAG, "onCreate: intent JSON ==> " + json);

            Gson gson = new Gson();
            tcode = gson.fromJson(json, TransferCode.class);
            Log.i(TAG, "Existing TCODE from Intent..." + tcode.toString());

//
            setAllValuesFromObject();
            showAllValues();
        }
        finalTeamNumber.setText("Team Number: " + tcode.getTeamNumber());
        finalMatchNumber.setText("Match Number: " + tcode.getMatchNumber());

        setComponentBackground(tcode.getIsRed());

        foulsCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setFinal_foulsCreated(1);
                } else {
                    tcode.setFinal_foulsCreated(0);

                }
                saveMatch();
            }
        });

        disabledCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setFinal_disabled(1);
                } else {
                    tcode.setFinal_disabled(0);
                }
                saveMatch();
            }
        });
        disqualifiedCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setFinal_disqualified(1);
                } else {
                    tcode.setFinal_disqualified(0);
                }
                saveMatch();
            }
        });
        yellowPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numYellow<= Defaults.MAX_FOULS) {
                    numYellow += 1;
                    tcode.setFinal_numYellowFouls(numYellow);
                    yellowTextView.setText(String.valueOf(numYellow));
                }
                saveMatch();
            }
        });
        yellowMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numYellow > 0) {
                    numYellow -= 1;
                    tcode.setFinal_numYellowFouls(numYellow);
                    yellowTextView.setText(String.valueOf(numYellow));
                }
                saveMatch();
            }
        });
        redPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numRed<= Defaults.MAX_FOULS) {
                    numRed += 1;
                    tcode.setFinal_numRedFouls(numRed);
                    redTextView.setText(String.valueOf(numRed));
                }
                saveMatch();
            }
        });
        redMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numRed > 0) {
                    numRed -= 1;
                    tcode.setFinal_numRedFouls(numRed);
                    redTextView.setText(String.valueOf(numRed));
                }
                saveMatch();
            }
        });
        techPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numTech<= Defaults.MAX_FOULS) {
                    numTech += 1;
                    tcode.setFinal_numTechFouls(numTech);
                    techTextView.setText(String.valueOf(numTech));
                }
                saveMatch();
            }
        });
        techMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numTech > 0) {
                    numTech -= 1;
                    tcode.setFinal_numTechFouls(numTech);
                    techTextView.setText(String.valueOf(numTech));
                }
                saveMatch();
            }
        });
        winning_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 1 = Home Win     Away Loss
                // 0 = Home Loss    Away Win
                // 2 = Tie          Tie

                if (checkedId == R.id.finalRedWinBtn) {
                    Log.i(TAG, "winning_RG: finalRedWin");
                    if (tcode.getIsRed()==1){
                        tcode.setFinal_winningAlliance(1); //Home Win (Red)
                    }
                    else if(tcode.getIsRed()==0){
                        tcode.setFinal_winningAlliance(0); //Away Win (Blue)

                    }
                    blueWinBtn.setAlpha(0.5f);
                    tieBtn.setAlpha(0.5f);
                    redWinBtn.setAlpha(1.0f);
                    saveMatch();
                } else if (checkedId == R.id.finalBlueWinBtn) {
                    Log.i(TAG, "winning_RG: finalBlueWin");
                    if (tcode.getIsRed()==0){
                        tcode.setFinal_winningAlliance(1); //Home Win (Blue)
                    }
                    else if(tcode.getIsRed()==1) {
                        tcode.setFinal_winningAlliance(0); //Away Win (Red)
                    }
                    redWinBtn.setAlpha(0.5f);
                    tieBtn.setAlpha(0.5f);
                    blueWinBtn.setAlpha(1.0f);
                    saveMatch();
                }
                else if (checkedId == R.id.finalDrawWinBtn) {
                    Log.i(TAG, "winning_RG: finalTieWin");
                    if (tcode.getIsRed()==0){
                        tcode.setFinal_winningAlliance(2); // Tie
                    }
                    else if(tcode.getIsRed()==1) {
                        tcode.setFinal_winningAlliance(2); // Tie
                    }
                    redWinBtn.setAlpha(0.5f);
                    blueWinBtn.setAlpha(0.5f);
                    tieBtn.setAlpha(1.0f);
                    saveMatch();
                }

            }
        });
        clearBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tcode.setFinal_winningAlliance(-1);
                winning_RG.clearCheck();
                redWinBtn.setAlpha(1.0f);
                blueWinBtn.setAlpha(1.0f);
                tieBtn.setAlpha(1.0f);
                saveMatch();}
        });

        toQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToQrActivity();
            }
        });  // End of toQr

        toEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToEndGameActivity();
            }
        });  // End of toEndGame


        showAllValues();
    }
    private void proceedToQrActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "proceedToQrActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(FinalActivity.this, QRCodeActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);
    }
    private void backToEndGameActivity(){
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "backToEndGameActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(FinalActivity.this, EndGameActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);
    }
    private void saveMatch() {
        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
        Log.i(TAG, "saveMatch, KEY: " + key + ", TCODE..: " + tcode.toString());
        allMatches.put(key, tcode);
        PreferenceUtility.saveAllMatches(getApplicationContext(), allMatches);
    }
    private void showAllValues() {
        if (tcode.getFinal_foulsCreated() == 1) {
            foulsCheck.setChecked(true);
        }
        yellowTextView.setText(String.valueOf(numYellow));
        redTextView.setText(String.valueOf(numRed));
        techTextView.setText(String.valueOf(numTech));

        if (tcode.getFinal_disabled() == 1) {
            disabledCheck.setChecked(true);
        }
        if (tcode.getFinal_disqualified() == 1) {
            disqualifiedCheck.setChecked(true);
        }

        if(tcode.getIsRed() == 0){                              //BLUE
            if(tcode.getFinal_winningAlliance() == 1){
                winning_RG.check(R.id.finalBlueWinBtn);
                redWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() == 0){
                winning_RG.check(R.id.finalRedWinBtn);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                redWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() == 2){
                winning_RG.check(R.id.finalDrawWinBtn);
                redWinBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(1.0f);
            }
        }
        else if(tcode.getIsRed() == 1){                         //RED
            if(tcode.getFinal_winningAlliance() == 1){
                winning_RG.check(R.id.finalRedWinBtn);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                redWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() ==0){
                winning_RG.check(R.id.finalBlueWinBtn);
                redWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() == 2){
                winning_RG.check(R.id.finalDrawWinBtn);
                redWinBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(1.0f);
            }
        }
    }
    private void setAllValuesFromObject() {
        numYellow = tcode.getFinal_numYellowFouls();
        numRed = tcode.getFinal_numRedFouls();
        numTech = tcode.getFinal_numTechFouls();
    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            finalAllianceColor.setText("Red Alliance");
            topView.setScaleX(-1);
            foulsGrid.setBackgroundResource(R.drawable.card_bg);
            disabledGrid.setBackgroundResource(R.drawable.card_bg);
            disqualifiedGrid.setBackgroundResource(R.drawable.card_bg);
            foulsConstraint.setBackgroundResource(R.drawable.card_bg);
            winningConstraint.setBackgroundResource(R.drawable.card_bg);
            phaseBarView.setBackgroundResource(R.drawable.bottom_view);
            redView.setBackgroundResource(R.drawable.red_button_bg);
            yellowView.setBackgroundResource(R.drawable.red_button_bg);
            techView.setBackgroundResource(R.drawable.red_button_bg);
            
            blueWinBtn.setBackgroundResource(R.drawable.red_button_bg);
            redWinBtn.setBackgroundResource(R.drawable.red_button_bg);
            clearBtn1.setBackgroundResource(R.drawable.red_button_bg);
            tieBtn.setBackgroundResource(R.drawable.red_button_bg);
            finalAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            finalAllianceColor.setText("Blue Alliance");
            topView.setScaleX(1);
            foulsGrid.setBackgroundResource(R.drawable.card_bg_blue);
            disabledGrid.setBackgroundResource(R.drawable.card_bg_blue);
            disqualifiedGrid.setBackgroundResource(R.drawable.card_bg_blue);
            foulsConstraint.setBackgroundResource(R.drawable.card_bg_blue);
            winningConstraint.setBackgroundResource(R.drawable.card_bg_blue);
            phaseBarView.setBackgroundResource(R.drawable.bottom_view_blue);

            redView.setBackgroundResource(R.drawable.blue_button_bg);
            yellowView.setBackgroundResource(R.drawable.blue_button_bg);
            techView.setBackgroundResource(R.drawable.blue_button_bg);

            blueWinBtn.setBackgroundResource(R.drawable.blue_button_bg);
            redWinBtn.setBackgroundResource(R.drawable.blue_button_bg);
            clearBtn1.setBackgroundResource(R.drawable.blue_button_bg);
            tieBtn.setBackgroundResource(R.drawable.blue_button_bg);
            finalAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);
        }
    }
    protected void updateValues() {
        Log.i(TAG, "updateValues: ");

        if (tcode.getFinal_foulsCreated() == 1) {
            foulsCheck.setChecked(true);
        }
        numYellow = tcode.getFinal_numYellowFouls();
        numRed = tcode.getFinal_numRedFouls();
        numTech = tcode.getFinal_numTechFouls();

        if (tcode.getFinal_disabled() == 1) {
            disabledCheck.setChecked(true);
        }
        if (tcode.getFinal_disqualified() == 1) {
            disqualifiedCheck.setChecked(true);
        }

        if(tcode.getIsRed() == 0){                              //BLUE
            if(tcode.getFinal_winningAlliance() == 1){
                winning_RG.check(R.id.finalBlueWinBtn);
                redWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() == 0){
                winning_RG.check(R.id.finalRedWinBtn);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                redWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() == 2){
                winning_RG.check(R.id.finalDrawWinBtn);
                redWinBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(1.0f);
            }
        }
        else if(tcode.getIsRed() == 1){                         //RED
            if(tcode.getFinal_winningAlliance() == 1){
                winning_RG.check(R.id.finalRedWinBtn);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                redWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() ==0){
                winning_RG.check(R.id.finalBlueWinBtn);
                redWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(1.0f);
            }
            else if(tcode.getFinal_winningAlliance() == 2){
                winning_RG.check(R.id.finalDrawWinBtn);
                redWinBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(1.0f);
            }
        }

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