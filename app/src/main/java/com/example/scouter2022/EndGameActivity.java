package com.example.scouter2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.scouter2022.model.TransferCode;
import com.example.scouter2022.util.PreferenceUtility;
import com.google.gson.Gson;

import java.util.Map;

public class EndGameActivity extends AppCompatActivity {
    private static final String TAG = "EndGameActivity";
    private static final float ENABLED = 1f;
    private static final float DISABLED = 0.15f;
    private TextView endGameTeamNumber;
    private TextView endGameMatchNumber;
    private TextView endGameAllianceColor;

    private View phaseBarView;
    private View topView;

    private ImageView toTele;
    private ImageView toFinal;

    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";

    private RadioGroup endGameAttempted_RG;
    private RadioGroup hang_RG;
    private RadioButton radioBtnEgAttempted, radioButtonEgNotAttempted, radioBtnPark, radioBtnHang;

    private SeekBar finishingRing_SB;
    private SeekBar contactRing_SB;
    private SeekBar timer_SB;
    private TextView timerProgressMark;


    private GridLayout attemptedGrid;
    private GridLayout timerGrid;
    private GridLayout hangGrid;
    private GridLayout contactGrid;
    private GridLayout finishingGrid;


    private Button clearBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            this.getSupportActionBar().hide();}
        catch(NullPointerException e){
        }
        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        endGameTeamNumber = findViewById(R.id.endGameTeamNumTextView);
        endGameMatchNumber = findViewById(R.id.endGameMatchNumTextView);
        endGameAllianceColor = findViewById(R.id.endGameColorTextView);

        attemptedGrid = findViewById(R.id.endGameAttemptedGridLayout);
        timerGrid = findViewById(R.id.endGameTimerGridLayout);
        hangGrid = findViewById(R.id.endGameHangGridLayout);
        finishingGrid = findViewById(R.id.endGameContactRingGridLayout);
        contactGrid = findViewById(R.id.endGameFinishingRingGridLayout);



        endGameAttempted_RG = findViewById(R.id.endGameAttempted_RG);
        hang_RG = findViewById(R.id.endGameHang_RG);
        finishingRing_SB = findViewById(R.id.seekBar_finishingRing);
        contactRing_SB = findViewById(R.id.seekBar_contactRing);
        timer_SB = findViewById(R.id.seekBar_timer);
        timerProgressMark = findViewById(R.id.endGame_timerProgressMark);
        phaseBarView = findViewById(R.id.EndGamePhaseViewBar);
        topView = findViewById(R.id.endGameTopView);

        clearBtn = findViewById(R.id.endGame_clearBtn);
        toTele = findViewById(R.id.EndGameToTele);
        toFinal = findViewById(R.id.EndGameToFinal);
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
        endGameTeamNumber.setText("Team Number: " + tcode.getTeamNumber());
        endGameMatchNumber.setText("Match Number: " + tcode.getMatchNumber());

        setComponentBackground(tcode.getIsRed());

        endGameAttempted_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.endGameAttemptedYes_RB) {
                    Log.i(TAG, "endGameAttempted_RG: YES");
                    tcode.setEndgame_attempt(1);

                    hangGrid.setAlpha(ENABLED);
                    finishingGrid.setAlpha(ENABLED);
                    contactGrid.setAlpha(ENABLED);
                    timerGrid.setAlpha(ENABLED);

                    enableRadioButtons(hang_RG);
                    finishingRing_SB.setEnabled(true);
                    contactRing_SB.setEnabled(true);
                    timer_SB.setEnabled(true);

                    saveMatch();
                } else if (checkedId == R.id.endGameAttemptedNo_RB) {
                    Log.i(TAG, "endGameAttempted_RG: YES");
                    tcode.setEndgame_attempt(0);
                    tcode.setEndgame_climbTime(0);
                    tcode.setEndgame_hang(0);
                    tcode.setEndgame_ringFinish(0);
                    tcode.setEndgame_ringContact(0);

                    timerGrid.setAlpha(DISABLED);
                    hangGrid.setAlpha(DISABLED);
                    finishingGrid.setAlpha(DISABLED);
                    contactGrid.setAlpha(DISABLED);

                    disableRadioButtons(hang_RG);
                    finishingRing_SB.setEnabled(false);
                    contactRing_SB.setEnabled(false);
                    timer_SB.setEnabled(false);

                    saveMatch();
                }
            }
        });

        hang_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.endGameHangSuccess_RB) {
                    Log.i(TAG, "hang_RG: YES");
                    tcode.setEndgame_attempt(1);
                    tcode.setEndgame_hang(1);

                    endGameAttempted_RG.check(R.id.endGameAttemptedYes_RB);

                    finishingGrid.setAlpha(ENABLED);
                    contactGrid.setAlpha(ENABLED);
                    finishingRing_SB.setEnabled(true);
                    contactRing_SB.setEnabled(true);
                    timer_SB.setEnabled(true);

                    saveMatch();
                } else if (checkedId == R.id.endGameHangFail_RB) {
                    Log.i(TAG, "hang_RG: NO");
                    tcode.setEndgame_attempt(1);
                    tcode.setEndgame_hang(0);
                    tcode.setEndgame_ringFinish(0);
                    tcode.setEndgame_ringContact(0);

                    endGameAttempted_RG.check(R.id.endGameAttemptedYes_RB);

                    finishingGrid.setAlpha(DISABLED);
                    contactGrid.setAlpha(DISABLED);
                    finishingRing_SB.setEnabled(false);
                    contactRing_SB.setEnabled(false);
                    timer_SB.setEnabled(false);

                    saveMatch();
                }
            }
        });

        contactRing_SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tcode.setEndgame_ringContact(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        finishingRing_SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tcode.setEndgame_ringFinish(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        timer_SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tcode.setEndgame_climbTime(progress);

                timerProgressMark.setText(String.valueOf(progress));

                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * seekBar.getProgress() / seekBar.getMax();

                timerProgressMark.measure(0, 0);
                int txtW = timerProgressMark.getMeasuredWidth();
                int delta = txtW / 2;
                timerProgressMark.setX((seekBar.getX() + thumbPos - delta));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timerProgressMark.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timerProgressMark.setVisibility(View.INVISIBLE);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hang_RG.clearCheck();
                endGameAttempted_RG.clearCheck();

                tcode.setEndgame_attempt(0);
                tcode.setEndgame_hang(0);
                tcode.setEndgame_ringFinish(0);
                tcode.setEndgame_ringContact(0);

                enableRadioButtons(hang_RG);
                finishingRing_SB.setEnabled(true);
                contactRing_SB.setEnabled(true);
                timer_SB.setEnabled(true);

                finishingRing_SB.setProgress(0);
                contactRing_SB.setProgress(0);
                timer_SB.setProgress(0);

                attemptedGrid.setAlpha(ENABLED);
                timerGrid.setAlpha(ENABLED);
                hangGrid.setAlpha(ENABLED);
                finishingGrid.setAlpha(ENABLED);
                contactGrid.setAlpha(ENABLED);

                saveMatch();
            }
        });  // End of endGameClearBtn
        toFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToFinalActivity();
            }
        });  // End of TeleButton
        toTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToTeleActivity();
            }
        });  // End of TeleButton
        showAllValues();
    }
    private void disableRadioButtons(RadioGroup rg) {
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(false);
        }
    }
    private void enableRadioButtons(RadioGroup rg) {
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(true);
        }
    }
    private void proceedToFinalActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "proceedToFinalActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(EndGameActivity.this, FinalActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);

//        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
//        tcode = allMatches.get(key);
//        Log.i(TAG, "After proceedEndGameOpActivity(): " + tcode.toString());
    }
    private void backToTeleActivity(){
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "backToAutoActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(EndGameActivity.this, TeleActivity.class);
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
        if (tcode.getEndgame_attempt() == 0) {
            endGameAttempted_RG.check(R.id.endGameAttemptedNo_RB);
//            parkGrid.setAlpha(DISABLED);
//            hangGrid.setAlpha(DISABLED);
//            finishingGrid.setAlpha(DISABLED);
//            contactGrid.setAlpha(DISABLED);
//            disableRadioButtons(hang_RG);
//            disableRadioButtons(park_RG);
//            finishingRing_SB.setEnabled(false);
//            contactRing_SB.setEnabled(false);
        }
        else{
            endGameAttempted_RG.check(R.id.endGameAttemptedYes_RB);
            if(tcode.getEndgame_hang() ==1){
                hang_RG.check(R.id.endGameHangSuccess_RB);
//                finishingGrid.setAlpha(ENABLED);
//                contactGrid.setAlpha(ENABLED);
//                parkGrid.setAlpha(DISABLED);
//                disableRadioButtons(park_RG);
//                finishingRing_SB.setEnabled(true);
//                contactRing_SB.setEnabled(true);
//                park_RG.clearCheck();

                finishingRing_SB.setProgress(tcode.getEndgame_ringFinish());
                contactRing_SB.setProgress(tcode.getEndgame_ringContact());
            }
        }
        timer_SB.setProgress(tcode.getEndgame_climbTime());
    }
    private void setAllValuesFromObject() {

    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            endGameAllianceColor.setText("Red Alliance");
//            topView.setScaleX(-1);
            attemptedGrid.setBackgroundResource(R.drawable.card_bg_red);
//            phaseBarView.setBackgroundResource(R.drawable.bottom_view_red);
            timerGrid.setBackgroundResource(R.drawable.card_bg_red);
            hangGrid.setBackgroundResource(R.drawable.card_bg_red);
            finishingGrid.setBackgroundResource(R.drawable.card_bg_red);
            contactGrid.setBackgroundResource(R.drawable.card_bg_red);
            endGameAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            endGameAllianceColor.setText("Blue Alliance");
//            topView.setScaleX(1);
            attemptedGrid.setBackgroundResource(R.drawable.card_bg_blue);
//            phaseBarView.setBackgroundResource(R.drawable.bottom_view_blue);
            timerGrid.setBackgroundResource(R.drawable.card_bg_blue);
            hangGrid.setBackgroundResource(R.drawable.card_bg_blue);
            finishingGrid.setBackgroundResource(R.drawable.card_bg_blue);
            contactGrid.setBackgroundResource(R.drawable.card_bg_blue);
            endGameAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);

        }
    }

    protected void updateValues() {
        Log.i(TAG, "updateValues: ");
        if (tcode.getEndgame_attempt() == 1) {
            endGameAttempted_RG.check(R.id.endGameAttemptedYes_RB);
        }
        else{
            endGameAttempted_RG.check(R.id.endGameAttemptedNo_RB);
        }
        if (tcode.getEndgame_hang() == 1) {
            hang_RG.check(R.id.endGameHangSuccess_RB);
        }
        else{
            hang_RG.check(R.id.endGameHangFail_RB);
        }
        finishingRing_SB.setProgress(tcode.getEndgame_ringFinish());
        contactRing_SB.setProgress(tcode.getEndgame_ringContact());
        timer_SB.setProgress(tcode.getEndgame_climbTime());
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
        Log.i(TAG, "onResume: Inside of EndGameActivity...");
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