package com.example.scouter2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.example.scouter2022.model.TransferCode;
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

    private GridLayout foulsGrid;
    private GridLayout disabledGrid;
    private GridLayout disqualifiedGrid;
    private ConstraintLayout foulsContraint;
    private ConstraintLayout winningConstraint;

    private TextView yellowTextView;
    private TextView redTextView;
    private TextView techTextView;
    
    private View yellowView;
    private View redView;
    private View techView;
    
    private ImageView yellowPlusBtn, yellowMinusBtn, redPlusBtn, redMinusBtn, techPlusBtn, techMinusBtn;
    private CheckBox foulsCheck, disabledCheck, disqualifiedCheck;
    private RadioGroup winning_RG;
    private Button redWinBtn;
    private Button blueWinBtn;
    private Button tieBtn;

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
        foulsContraint = findViewById(R.id.finalFoulsContraintLayout);
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
//            setAllValuesFromObject();
//            showAllValues();
        }
        finalTeamNumber.setText("Team Number: " + tcode.getTeamNumber());
        finalMatchNumber.setText("Match Number: " + tcode.getMatchNumber());

//        setComponentBackground(tcode.getIsRed());

        foulsCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    tcode.setFinal_foulsCreated(1);
                } else {
                    tcode.setFinal_foulsCreated(0);

                }
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
            }
        });


        
    }
}