package com.example.scouter2022;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
    private ConstraintLayout zoneContstraint;


    private TextView foulsTextView;
    private TextView techTextView;
    
    
    private CheckBox yellowCardCheckBox;
    private CheckBox redCardCheckBox;
    private View foulView;
    private View techView;
    
    
    private int numFouls,numTech;
    
    private ImageView foulsPlusBtn, foulsMinusBtn, techPlusBtn, techMinusBtn;
    private ImageView zoneClearBtn1;
    private TextView zoneClearBtn2;

    private CheckBox foulsCheck, disabledCheck, disqualifiedCheck;
    private RadioGroup winning_RG;
    private RadioButton redWinBtn;
    private RadioButton blueWinBtn;
    private RadioButton tieBtn;
    private RadioGroup zone_RG;
    private RadioButton zone1btn;
    private RadioButton zone2btn;
    private RadioButton zone3btn;
    private RadioButton zone4btn;


    private Button showEdit;
    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            this.getSupportActionBar().hide();}
        catch(NullPointerException e){
        }
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
        
        foulsTextView = findViewById(R.id.finalFoulsTextView);
        foulsMinusBtn = findViewById(R.id.finalFoulsMinusView);
        foulsPlusBtn = findViewById(R.id.finalFoulsPlusView);
        foulView = findViewById(R.id.finalRegFoulView);


        yellowCardCheckBox = findViewById(R.id.yellowCardCheckBox);
        redCardCheckBox = findViewById(R.id.redCardCheckBox);

        techTextView = findViewById(R.id.autoHTS);
        techMinusBtn = findViewById(R.id.autoHTSM);
        techPlusBtn = findViewById(R.id.autoHTSP);
        techView = findViewById(R.id.finalTechView);
        
        clearBtn1 = findViewById(R.id.finalClearBtn);

        clearBtn2 = findViewById(R.id.finalClearBtn2);

        foulsCheck = findViewById(R.id.finalFoulsCheckBox);
        disabledCheck = findViewById(R.id.finalDisabledCheckBox);
        disqualifiedCheck = findViewById(R.id.finalDisqualifiedCheckBox);

        winning_RG = findViewById(R.id.finalWinningAlliance_RG);
        redWinBtn = findViewById(R.id.finalRedWinBtn);
        blueWinBtn = findViewById(R.id.finalBlueWinBtn);
        tieBtn = findViewById(R.id.finalTieBtn);
        showEdit = findViewById(R.id.showEditShotsBtn);

        zone_RG = findViewById(R.id.finalZone_RG);
        zone1btn = findViewById(R.id.finalZone1Btn);
        zone2btn = findViewById(R.id.finalZone2Btn);
        zone3btn = findViewById(R.id.finalZone3Btn);
        zone4btn = findViewById(R.id.finalZone4Btn);
        zoneClearBtn1 = findViewById(R.id.clear_zone);
        zoneClearBtn2 = findViewById(R.id.zoneClearText);
        zoneContstraint = findViewById(R.id.zoneSelectionLayout);

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
        yellowCardCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setFinal_yellowCardCreated(1);
                    yellowCardCheckBox.setAlpha(1.0f);
                } else {
                    tcode.setFinal_yellowCardCreated(0);
                    yellowCardCheckBox.setAlpha(0.5f);                }
                saveMatch();
            }
        });
        redCardCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setFinal_redCardCreated(1);
                    redCardCheckBox.setAlpha(1.0f);
                } else {
                    tcode.setFinal_redCardCreated(0);
                    redCardCheckBox.setAlpha(0.5f);                }
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

        foulsPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numFouls<= Defaults.MAX_FOULS) {
                    numFouls += 1;
                    tcode.setFinal_numRegFouls(numFouls);
                    foulsTextView.setText(String.valueOf(numFouls));
                }
                saveMatch();
            }
        });
        foulsMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numFouls > 0) {
                    numFouls -= 1;
                    tcode.setFinal_numRegFouls(numFouls);
                    foulsTextView.setText(String.valueOf(numFouls));
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
                else if (checkedId == R.id.finalTieBtn) {
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
        zone_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 1 = Home Win     Away Loss
                // 0 = Home Loss    Away Win
                // 2 = Tie          Tie

                if (checkedId == R.id.finalZone1Btn) {
                    Log.i(TAG, "zone_RG: finalZone1Btn");
                    tcode.setFinal_zone(1);
                    zone1btn.setAlpha(1.0f);
                    zone2btn.setAlpha(0.5f);
                    zone3btn.setAlpha(0.5f);
                    zone4btn.setAlpha(0.5f);
                    saveMatch();
                } else if (checkedId == R.id.finalZone2Btn) {
                    Log.i(TAG, "zone_RG: finalZone2Btn");
                    tcode.setFinal_zone(2);
                    zone1btn.setAlpha(0.5f);
                    zone2btn.setAlpha(1.0f);
                    zone3btn.setAlpha(0.5f);
                    zone4btn.setAlpha(0.5f);
                    saveMatch();
                }
                else if (checkedId == R.id.finalZone3Btn) {
                    Log.i(TAG, "zone_RG: finalZone3Btn");
                    tcode.setFinal_zone(3);
                    zone1btn.setAlpha(0.5f);
                    zone2btn.setAlpha(0.5f);
                    zone3btn.setAlpha(1.0f);
                    zone4btn.setAlpha(0.5f);
                    saveMatch();
                }
                else if (checkedId == R.id.finalZone4Btn) {
                    Log.i(TAG, "zone_RG: finalZone4Btn");
                    tcode.setFinal_zone(4);
                    zone1btn.setAlpha(0.5f);
                    zone2btn.setAlpha(0.5f);
                    zone3btn.setAlpha(0.5f);
                    zone4btn.setAlpha(1.0f);
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

        zoneClearBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tcode.setFinal_zone(0);
                zone_RG.clearCheck();
                zone1btn.setAlpha(1.0f);
                zone2btn.setAlpha(1.0f);
                zone3btn.setAlpha(1.0f);
                zone4btn.setAlpha(1.0f);
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

        showEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditVals();
            }
        });  // End of toEndGame
        showAllValues();
    }
    private void showEditVals() {
        final Dialog dialog = new Dialog(FinalActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.editshots_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // set the custom dialog components - text, image and button
        TextView auto_HTS = dialog.findViewById(R.id.autoHTS);
        ImageView auto_HTSM = dialog.findViewById(R.id.autoHTSM);
        ImageView auto_HTSP = dialog.findViewById(R.id.autoHTSP);

        TextView auto_HBS = dialog.findViewById(R.id.autoHBS);
        ImageView auto_HBSM = dialog.findViewById(R.id.autoHBSM);
        ImageView auto_HBSP = dialog.findViewById(R.id.autoHBSP);

        TextView auto_HTF = dialog.findViewById(R.id.autoHTF);
        ImageView auto_HTFM = dialog.findViewById(R.id.autoHTFM);
        ImageView auto_HTFP = dialog.findViewById(R.id.autoHTFP);

        TextView auto_HBF = dialog.findViewById(R.id.autoHBF);
        ImageView auto_HBFM = dialog.findViewById(R.id.autoHBFM);
        ImageView auto_HBFP = dialog.findViewById(R.id.autoHBFP);

        TextView auto_ATS = dialog.findViewById(R.id.autoATS);
        ImageView auto_ATSM = dialog.findViewById(R.id.autoATSM);
        ImageView auto_ATSP = dialog.findViewById(R.id.autoATSP);

        TextView auto_ABS = dialog.findViewById(R.id.autoABS);
        ImageView auto_ABSM = dialog.findViewById(R.id.autoABSM);
        ImageView auto_ABSP = dialog.findViewById(R.id.autoABSP);

        TextView auto_ATF = dialog.findViewById(R.id.autoATF);
        ImageView auto_ATFM = dialog.findViewById(R.id.autoATFM);
        ImageView auto_ATFP = dialog.findViewById(R.id.autoATFP);

        TextView auto_ABF = dialog.findViewById(R.id.autoABF);
        ImageView auto_ABFM = dialog.findViewById(R.id.autoABFM);
        ImageView auto_ABFP = dialog.findViewById(R.id.autoABFP);

        TextView auto_HPF = dialog.findViewById(R.id.autoHPF);
        ImageView auto_HPFM = dialog.findViewById(R.id.autoHPFM);
        ImageView auto_HPFP = dialog.findViewById(R.id.autoHPFP);

        TextView auto_HPS = dialog.findViewById(R.id.autoHPS);
        ImageView auto_HPSM = dialog.findViewById(R.id.autoHPSM);
        ImageView auto_HPSP = dialog.findViewById(R.id.autoHPSP);

        TextView tele_HTS = dialog.findViewById(R.id.teleHTS);
        ImageView tele_HTSM = dialog.findViewById(R.id.teleHTSM);
        ImageView tele_HTSP = dialog.findViewById(R.id.teleHTSP);

        TextView tele_HBS = dialog.findViewById(R.id.teleHBS);
        ImageView tele_HBSM = dialog.findViewById(R.id.teleHBSM);
        ImageView tele_HBSP = dialog.findViewById(R.id.teleHBSP);

        TextView tele_HTF = dialog.findViewById(R.id.teleHTF);
        ImageView tele_HTFM = dialog.findViewById(R.id.teleHTFM);
        ImageView tele_HTFP = dialog.findViewById(R.id.teleHTFP);

        TextView tele_HBF = dialog.findViewById(R.id.teleHBF);
        ImageView tele_HBFM = dialog.findViewById(R.id.teleHBFM);
        ImageView tele_HBFP = dialog.findViewById(R.id.teleHBFP);

        TextView tele_ATS = dialog.findViewById(R.id.teleATS);
        ImageView tele_ATSM = dialog.findViewById(R.id.teleATSM);
        ImageView tele_ATSP = dialog.findViewById(R.id.teleATSP);

        TextView tele_ABS = dialog.findViewById(R.id.teleABS);
        ImageView tele_ABSM = dialog.findViewById(R.id.teleABSM);
        ImageView tele_ABSP = dialog.findViewById(R.id.teleABSP);

        TextView tele_ATF = dialog.findViewById(R.id.teleATF);
        ImageView tele_ATFM = dialog.findViewById(R.id.teleATFM);
        ImageView tele_ATFP = dialog.findViewById(R.id.teleATFP);

        TextView tele_ABF = dialog.findViewById(R.id.teleABF);
        ImageView tele_ABFM = dialog.findViewById(R.id.teleABFM);
        ImageView tele_ABFP = dialog.findViewById(R.id.teleABFP);
        
        Button done = dialog.findViewById(R.id.aboutBtnID2);

        auto_HTS.setText(String.valueOf(tcode.getAuto_allianceCargo_top_s()));
        auto_HBS.setText(String.valueOf(tcode.getAuto_allianceCargo_bot_s()));
        auto_HTF.setText(String.valueOf(tcode.getAuto_allianceCargo_top_f()));
        auto_HBF.setText(String.valueOf(tcode.getAuto_allianceCargo_bot_f()));
        auto_ATS.setText(String.valueOf(tcode.getAuto_opponentCargo_top_s()));
        auto_ABS.setText(String.valueOf(tcode.getAuto_opponentCargo_bot_s()));
        auto_ATF.setText(String.valueOf(tcode.getAuto_opponentCargo_top_f()));
        auto_ABF.setText(String.valueOf(tcode.getAuto_opponentCargo_bot_f()));

        auto_HPS.setText(String.valueOf(tcode.getAuto_humanScored()));
        auto_HPF.setText(String.valueOf(tcode.getAuto_humanMissed()));

        tele_HTS.setText(String.valueOf(tcode.getTele_allianceCargo_top_s()));
        tele_HBS.setText(String.valueOf(tcode.getTele_allianceCargo_bot_s()));
        tele_HTF.setText(String.valueOf(tcode.getTele_allianceCargo_top_f()));
        tele_HBF.setText(String.valueOf(tcode.getTele_allianceCargo_bot_f()));
        tele_ATS.setText(String.valueOf(tcode.getTele_opponentCargo_top_s()));
        tele_ABS.setText(String.valueOf(tcode.getTele_opponentCargo_bot_s()));
        tele_ATF.setText(String.valueOf(tcode.getTele_opponentCargo_top_f()));
        tele_ABF.setText(String.valueOf(tcode.getTele_opponentCargo_bot_f()));

        auto_HTSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_top_s()>0){
                    tcode.setAuto_allianceCargo_top_s(tcode.getAuto_allianceCargo_top_s()-1);
                }
                auto_HTS.setText(String.valueOf(tcode.getAuto_allianceCargo_top_s()));
            }
        });
        auto_HTSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_top_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_allianceCargo_top_s(tcode.getAuto_allianceCargo_top_s()+1);
                }
                auto_HTS.setText(String.valueOf(tcode.getAuto_allianceCargo_top_s()));
            }
        });

        auto_HBSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_bot_s()>0){
                    tcode.setAuto_allianceCargo_bot_s(tcode.getAuto_allianceCargo_bot_s()-1);
                }
                auto_HBS.setText(String.valueOf(tcode.getAuto_allianceCargo_bot_s()));
            }
        });
        auto_HBSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_bot_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_allianceCargo_bot_s(tcode.getAuto_allianceCargo_bot_s()+1);
                }
                auto_HBS.setText(String.valueOf(tcode.getAuto_allianceCargo_bot_s()));
            }
        });

        auto_HTFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_top_f()>0){
                    tcode.setAuto_allianceCargo_top_f(tcode.getAuto_allianceCargo_top_f()-1);
                }
                auto_HTF.setText(String.valueOf(tcode.getAuto_allianceCargo_top_f()));
            }
        });
        auto_HTFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_top_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_allianceCargo_top_f(tcode.getAuto_allianceCargo_top_f()+1);
                }
                auto_HTF.setText(String.valueOf(tcode.getAuto_allianceCargo_top_f()));
            }
        });

        auto_HBFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_bot_f()>0){
                    tcode.setAuto_allianceCargo_bot_f(tcode.getAuto_allianceCargo_bot_f()-1);
                }
                auto_HBF.setText(String.valueOf(tcode.getAuto_allianceCargo_bot_f()));

            }
        });
        auto_HBFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_allianceCargo_bot_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_allianceCargo_bot_f(tcode.getAuto_allianceCargo_bot_f()+1);
                }
                auto_HBF.setText(String.valueOf(tcode.getAuto_allianceCargo_bot_f()));

            }
        });


        auto_ATSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_top_s()>0){
                    tcode.setAuto_opponentCargo_top_s(tcode.getAuto_opponentCargo_top_s()-1);
                }
                auto_ATS.setText(String.valueOf(tcode.getAuto_opponentCargo_top_s()));

            }
        });
        auto_ATSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_top_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_opponentCargo_top_s(tcode.getAuto_opponentCargo_top_s()+1);
                }
                auto_ATS.setText(String.valueOf(tcode.getAuto_opponentCargo_top_s()));

            }
        });

        auto_ABSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_bot_s()>0){
                    tcode.setAuto_opponentCargo_bot_s(tcode.getAuto_opponentCargo_bot_s()-1);
                }
                auto_ABS.setText(String.valueOf(tcode.getAuto_opponentCargo_bot_s()));

            }
        });
        auto_ABSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_bot_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_opponentCargo_bot_s(tcode.getAuto_opponentCargo_bot_s()+1);
                }
                auto_ABS.setText(String.valueOf(tcode.getAuto_opponentCargo_bot_s()));

            }
        });

        auto_ATFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_top_f()>0){
                    tcode.setAuto_opponentCargo_top_f(tcode.getAuto_opponentCargo_top_f()-1);
                }
                auto_ATF.setText(String.valueOf(tcode.getAuto_opponentCargo_top_f()));

            }
        });
        auto_ATFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_top_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_opponentCargo_top_f(tcode.getAuto_opponentCargo_top_f()+1);
                }
                auto_ATF.setText(String.valueOf(tcode.getAuto_opponentCargo_top_f()));

            }
        });

        auto_ABFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_bot_f()>0){
                    tcode.setAuto_opponentCargo_bot_f(tcode.getAuto_opponentCargo_bot_f()-1);
                }
                auto_ABF.setText(String.valueOf(tcode.getAuto_opponentCargo_bot_f()));

            }
        });
        auto_ABFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_opponentCargo_bot_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_opponentCargo_bot_f(tcode.getAuto_opponentCargo_bot_f()+1);
                }
                auto_ABF.setText(String.valueOf(tcode.getAuto_opponentCargo_bot_f()));

            }
        });

        auto_HPFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_humanMissed()>0){
                    tcode.setAuto_humanMissed(tcode.getAuto_humanMissed()-1);
                }
                auto_HPF.setText(String.valueOf(tcode.getAuto_humanMissed()));
            }
        });
        auto_HPFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_humanMissed()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_humanMissed(tcode.getAuto_humanMissed()+1);
                }
                auto_HPF.setText(String.valueOf(tcode.getAuto_humanMissed()));
            }
        });

        auto_HPSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_humanScored()>0){
                    tcode.setAuto_humanScored(tcode.getAuto_humanScored()-1);
                }
                auto_HPS.setText(String.valueOf(tcode.getAuto_humanScored()));

            }
        });
        auto_HPSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getAuto_humanScored()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setAuto_humanScored(tcode.getAuto_humanScored()+1);
                }
                auto_HPS.setText(String.valueOf(tcode.getAuto_humanScored()));

            }
        });


        tele_HTSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_top_s()>0){
                    tcode.setTele_allianceCargo_top_s(tcode.getTele_allianceCargo_top_s()-1);
                }
                tele_HTS.setText(String.valueOf(tcode.getTele_allianceCargo_top_s()));
            }
        });
        tele_HTSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_top_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_allianceCargo_top_s(tcode.getTele_allianceCargo_top_s()+1);
                }
                tele_HTS.setText(String.valueOf(tcode.getTele_allianceCargo_top_s()));
            }
        });

        tele_HBSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_bot_s()>0){
                    tcode.setTele_allianceCargo_bot_s(tcode.getTele_allianceCargo_bot_s()-1);
                }
                tele_HBS.setText(String.valueOf(tcode.getTele_allianceCargo_bot_s()));
            }
        });
        tele_HBSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_bot_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_allianceCargo_bot_s(tcode.getTele_allianceCargo_bot_s()+1);
                }
                tele_HBS.setText(String.valueOf(tcode.getTele_allianceCargo_bot_s()));
            }
        });

        tele_HTFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_top_f()>0){
                    tcode.setTele_allianceCargo_top_f(tcode.getTele_allianceCargo_top_f()-1);
                }
                tele_HTF.setText(String.valueOf(tcode.getTele_allianceCargo_top_f()));
            }
        });
        tele_HTFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_top_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_allianceCargo_top_f(tcode.getTele_allianceCargo_top_f()+1);
                }
                tele_HTF.setText(String.valueOf(tcode.getTele_allianceCargo_top_f()));
            }
        });

        tele_HBFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_bot_f()>0){
                    tcode.setTele_allianceCargo_bot_f(tcode.getTele_allianceCargo_bot_f()-1);
                }
                tele_HBF.setText(String.valueOf(tcode.getTele_allianceCargo_bot_f()));

            }
        });
        tele_HBFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_allianceCargo_bot_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_allianceCargo_bot_f(tcode.getTele_allianceCargo_bot_f()+1);
                }
                tele_HBF.setText(String.valueOf(tcode.getTele_allianceCargo_bot_f()));

            }
        });


        tele_ATSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_top_s()>0){
                    tcode.setTele_opponentCargo_top_s(tcode.getTele_opponentCargo_top_s()-1);
                }
                tele_ATS.setText(String.valueOf(tcode.getTele_opponentCargo_top_s()));

            }
        });
        tele_ATSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_top_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_opponentCargo_top_s(tcode.getTele_opponentCargo_top_s()+1);
                }
                tele_ATS.setText(String.valueOf(tcode.getTele_opponentCargo_top_s()));

            }
        });

        tele_ABSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_bot_s()>0){
                    tcode.setTele_opponentCargo_bot_s(tcode.getTele_opponentCargo_bot_s()-1);
                }
                tele_ABS.setText(String.valueOf(tcode.getTele_opponentCargo_bot_s()));

            }
        });
        tele_ABSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_bot_s()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_opponentCargo_bot_s(tcode.getTele_opponentCargo_bot_s()+1);
                }
                tele_ABS.setText(String.valueOf(tcode.getTele_opponentCargo_bot_s()));

            }
        });

        tele_ATFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_top_f()>0){
                    tcode.setTele_opponentCargo_top_f(tcode.getTele_opponentCargo_top_f()-1);
                }
                tele_ATF.setText(String.valueOf(tcode.getTele_opponentCargo_top_f()));

            }
        });
        tele_ATFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_top_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_opponentCargo_top_f(tcode.getTele_opponentCargo_top_f()+1);
                }
                tele_ATF.setText(String.valueOf(tcode.getTele_opponentCargo_top_f()));

            }
        });

        tele_ABFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_bot_f()>0){
                    tcode.setTele_opponentCargo_bot_f(tcode.getTele_opponentCargo_bot_f()-1);
                }
                tele_ABF.setText(String.valueOf(tcode.getTele_opponentCargo_bot_f()));

            }
        });
        tele_ABFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tcode.getTele_opponentCargo_bot_f()<Defaults.MAX_CARGO_NUMBER){
                    tcode.setTele_opponentCargo_bot_f(tcode.getTele_opponentCargo_bot_f()+1);
                }
                tele_ABF.setText(String.valueOf(tcode.getTele_opponentCargo_bot_f()));

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: aboutButton has been clicked...");
                dialog.cancel();
            }
        });

        dialog.show();
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
        foulsTextView.setText(String.valueOf(numFouls));
        techTextView.setText(String.valueOf(numTech));

        if (tcode.getFinal_disabled() == 1) {
            disabledCheck.setChecked(true);
        }
        if (tcode.getFinal_disqualified() == 1) {
            disqualifiedCheck.setChecked(true);
        }

        if(tcode.getFinal_yellowCardCreated() == 1){
            yellowCardCheckBox.setAlpha(1.0f);
            yellowCardCheckBox.setChecked(true);
        }else{
            yellowCardCheckBox.setAlpha(0.5f);
            yellowCardCheckBox.setChecked(false);
        }
        if(tcode.getFinal_redCardCreated() == 1){
            redCardCheckBox.setAlpha(1.0f);
            redCardCheckBox.setChecked(true);
        }else{
            redCardCheckBox.setAlpha(0.5f);
            redCardCheckBox.setChecked(false);
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
                winning_RG.check(R.id.finalTieBtn);
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
                winning_RG.check(R.id.finalTieBtn);
                redWinBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(1.0f);
            }
        }

        if(tcode.getFinal_zone() == 1){
            zone1btn.setAlpha(1.0f);
            zone2btn.setAlpha(0.5f);
            zone3btn.setAlpha(0.5f);
            zone4btn.setAlpha(0.5f);
        }
        else if(tcode.getFinal_zone() == 2){
            zone1btn.setAlpha(0.5f);
            zone2btn.setAlpha(1.0f);
            zone3btn.setAlpha(0.5f);
            zone4btn.setAlpha(0.5f);
        }
        else if(tcode.getFinal_zone() == 3){
            zone1btn.setAlpha(0.5f);
            zone2btn.setAlpha(0.5f);
            zone3btn.setAlpha(1.0f);
            zone4btn.setAlpha(0.5f);
        }
        else if(tcode.getFinal_zone() == 4){
            zone1btn.setAlpha(0.5f);
            zone2btn.setAlpha(0.5f);
            zone3btn.setAlpha(0.5f);
            zone4btn.setAlpha(1.0f);
        }
        else{
            zone1btn.setAlpha(1.0f);
            zone2btn.setAlpha(1.0f);
            zone3btn.setAlpha(1.0f);
            zone4btn.setAlpha(1.0f);
        }
    }
    private void setAllValuesFromObject() {
        numFouls = tcode.getFinal_yellowCardCreated();
        numTech = tcode.getFinal_numTechFouls();
    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            finalAllianceColor.setText("Red Alliance");
            topView.setScaleX(-1);
            foulsGrid.setBackgroundResource(R.drawable.card_bg_red);
            disabledGrid.setBackgroundResource(R.drawable.card_bg_red);
            disqualifiedGrid.setBackgroundResource(R.drawable.card_bg_red);
            foulsConstraint.setBackgroundResource(R.drawable.card_bg_red);
            winningConstraint.setBackgroundResource(R.drawable.card_bg_red);
            zoneContstraint.setBackgroundResource(R.drawable.card_bg_red);
            phaseBarView.setBackgroundResource(R.drawable.bottom_view_red);
            foulView.setBackgroundResource(R.drawable.button_bg_red);
            techView.setBackgroundResource(R.drawable.button_bg_red);


            yellowCardCheckBox.setBackgroundResource(R.drawable.button_bg_red);
            redCardCheckBox.setBackgroundResource(R.drawable.button_bg_red);
            blueWinBtn.setBackgroundResource(R.drawable.button_bg_red);
            redWinBtn.setBackgroundResource(R.drawable.button_bg_red);
            clearBtn1.setBackgroundResource(R.drawable.button_bg_red);
            zoneClearBtn1.setBackgroundResource(R.drawable.button_bg_red);
            tieBtn.setBackgroundResource(R.drawable.button_bg_red);
            finalAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            finalAllianceColor.setText("Blue Alliance");
            topView.setScaleX(1);
            foulsGrid.setBackgroundResource(R.drawable.card_bg_blue);
            disabledGrid.setBackgroundResource(R.drawable.card_bg_blue);
            disqualifiedGrid.setBackgroundResource(R.drawable.card_bg_blue);
            foulsConstraint.setBackgroundResource(R.drawable.card_bg_blue);
            winningConstraint.setBackgroundResource(R.drawable.card_bg_blue);
            zoneContstraint.setBackgroundResource(R.drawable.card_bg_blue);
            phaseBarView.setBackgroundResource(R.drawable.bottom_view_blue);

            foulView.setBackgroundResource(R.drawable.button_bg_blue);
            techView.setBackgroundResource(R.drawable.button_bg_blue);
            yellowCardCheckBox.setBackgroundResource(R.drawable.button_bg_blue);
            redCardCheckBox.setBackgroundResource(R.drawable.button_bg_blue);
            blueWinBtn.setBackgroundResource(R.drawable.button_bg_blue);
            redWinBtn.setBackgroundResource(R.drawable.button_bg_blue);
            clearBtn1.setBackgroundResource(R.drawable.button_bg_blue);
            zoneClearBtn2.setBackgroundResource(R.drawable.button_bg_blue);
            tieBtn.setBackgroundResource(R.drawable.button_bg_blue);
            finalAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);
        }
    }
    protected void updateValues() {
        Log.i(TAG, "updateValues: ");

        if (tcode.getFinal_foulsCreated() == 1) {
            foulsCheck.setChecked(true);
        }
        numFouls = tcode.getFinal_yellowCardCreated();
        numTech = tcode.getFinal_numTechFouls();

        if (tcode.getFinal_disabled() == 1) {
            disabledCheck.setChecked(true);
        }
        if (tcode.getFinal_disqualified() == 1) {
            disqualifiedCheck.setChecked(true);
        }
        if(tcode.getFinal_yellowCardCreated() == 1){
            yellowCardCheckBox.setAlpha(1.0f);
            yellowCardCheckBox.setChecked(true);
        }else{
            yellowCardCheckBox.setAlpha(0.5f);
            yellowCardCheckBox.setChecked(false);
        }
        if(tcode.getFinal_redCardCreated() == 1){
            redCardCheckBox.setAlpha(1.0f);
            redCardCheckBox.setChecked(true);
        }else{
            redCardCheckBox.setAlpha(0.5f);
            redCardCheckBox.setChecked(false);
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
                winning_RG.check(R.id.finalTieBtn);
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
                winning_RG.check(R.id.finalTieBtn);
                redWinBtn.setAlpha(0.5f);
                blueWinBtn.setAlpha(0.5f);
                tieBtn.setAlpha(1.0f);
            }
        }
        if(tcode.getFinal_zone() == 1){
            zone1btn.setAlpha(1.0f);
            zone2btn.setAlpha(0.5f);
            zone3btn.setAlpha(0.5f);
            zone4btn.setAlpha(0.5f);
        }
        else if(tcode.getFinal_zone() == 2){
            zone1btn.setAlpha(0.5f);
            zone2btn.setAlpha(1.0f);
            zone3btn.setAlpha(0.5f);
            zone4btn.setAlpha(0.5f);
        }
        else if(tcode.getFinal_zone() == 3){
            zone1btn.setAlpha(0.5f);
            zone2btn.setAlpha(0.5f);
            zone3btn.setAlpha(1.0f);
            zone4btn.setAlpha(0.5f);
        }
        else if(tcode.getFinal_zone() == 4){
            zone1btn.setAlpha(0.5f);
            zone2btn.setAlpha(0.5f);
            zone3btn.setAlpha(0.5f);
            zone4btn.setAlpha(1.0f);
        }
        else{
            zone1btn.setAlpha(1.0f);
            zone2btn.setAlpha(1.0f);
            zone3btn.setAlpha(1.0f);
            zone4btn.setAlpha(1.0f);
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