package com.example.scouter2022;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.scouter2022.model.TransferCode;
import com.example.scouter2022.util.PreferenceUtility;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Map;

public class QRCodeActivity extends AppCompatActivity {
    private static final String TAG = "QRCodeActivity";
    private TextView qrTeamNumber;
    private TextView qrMatchNumber;
    private TextView qrAllianceColor;
    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";

    private TextView regFoulsTextView;
    private TextView redFoulsTextView;
    private TextView yellowCardTextView,redCardTextView;
    private TextView techFoulsTextView;
    private TextView disabledTextView;
    private TextView disqualifiedTextView;
    private TextView codeTextView;
    private View phaseBarView;
    private View topView;
    private ImageView QRCODE;
    private ImageView toMain;
    private ImageView toFinal;
    
    private ConstraintLayout layout1,layout2,layout3,layout4;
    private View foulsView, infoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        qrTeamNumber = findViewById(R.id.qrTeamNumTextView);
        qrMatchNumber = findViewById(R.id.qrMatchNumTextView);
        qrAllianceColor = findViewById(R.id.qrColorTextView);
        topView = findViewById(R.id.qrTopView);

        regFoulsTextView = findViewById(R.id.NumRegFouls_textView);
        techFoulsTextView = findViewById(R.id.NumTechFouls_textView);

        yellowCardTextView = findViewById(R.id.yellowFouls_textView);

        redCardTextView = findViewById(R.id.redFouls_textView);

        disabledTextView = findViewById(R.id.disabled_YN_textView);
        disqualifiedTextView = findViewById(R.id.disqualified_YN_textView);
        
        foulsView = findViewById(R.id.qrFouls_View);
        infoView = findViewById(R.id.qrInfo_View);
        codeTextView = findViewById(R.id.qrCodeTextView);
        layout1 = findViewById(R.id.qrInfoLayout);
        layout2 = findViewById(R.id.qrDisabledLayout);
        layout3 = findViewById(R.id.qrDisqualifiedLayout);
        layout4 = findViewById(R.id.qrFoulsLayout);
        
        toFinal = findViewById(R.id.QRtoFinal);
        toMain = findViewById(R.id.QRtoMain);
        QRCODE = findViewById(R.id.QRcode_imageView);
        Intent intent = getIntent();
        if (intent != null) {
            String json = intent.getStringExtra("code");
            Log.i(TAG, "onCreate: intent JSON ==> " + json);

            Gson gson = new Gson();
            tcode = gson.fromJson(json, TransferCode.class);
            Log.i(TAG, "Existing TCODE from Intent..." + tcode.toString());
            
            showAllValues();
        }
        qrTeamNumber.setText(String.valueOf(tcode.getTeamNumber()));
        qrMatchNumber.setText(String.valueOf(tcode.getMatchNumber()));

        setComponentBackground(tcode.getIsRed());

        toFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToFinalActivity();
            }
        });  // End of toFinal
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMatch();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });  // End of endGameBtn
        showAllValues();
    }
    private void proceedToMainActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "proceedToMainActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(QRCodeActivity.this, MainActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);
    }
    private void backToFinalActivity(){
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "backToFinalActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(QRCodeActivity.this, FinalActivity.class);
        intent.putExtra("code", json);
        startActivity(intent);
    }
    private void showQRcode(){
        String alphaCode = tcode.GenerateCode(tcode.getBinaryString());

        codeTextView.setText(alphaCode);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(alphaCode, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QRCODE.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void showAllValues(){
        regFoulsTextView.setText(String.valueOf(tcode.getFinal_numRegFouls()));
        techFoulsTextView.setText(String.valueOf(tcode.getFinal_numTechFouls()));

        if (tcode.getFinal_redCardCreated() == 1){
            redCardTextView.setText("Red: Yes");
        }else{
            redCardTextView.setText("Red: No");
        }

        if (tcode.getFinal_yellowCardCreated() == 1){
            yellowCardTextView.setText("Yellow: Yes");
        }else{
            yellowCardTextView.setText("Yellow: No");
        }

        if(tcode.getFinal_disabled() == 1){
            disabledTextView.setText("Yes");
        }
        else{
            disabledTextView.setText("No");
        }
        if(tcode.getFinal_disqualified() == 1){
            disqualifiedTextView.setText("Yes");
        }
        else{
            disqualifiedTextView.setText("No");
        }
    }
    private void saveMatch() {
        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
        Log.i(TAG, "saveMatch, KEY: " + key + ", TCODE..: " + tcode.toString());
        allMatches.put(key, tcode);
        PreferenceUtility.saveAllMatches(getApplicationContext(), allMatches);
    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            qrAllianceColor.setText("Red");
            topView.setScaleX(-1);
            layout1.setBackgroundResource(R.drawable.card_bg);
            layout2.setBackgroundResource(R.drawable.card_bg);
            layout3.setBackgroundResource(R.drawable.card_bg);
            layout4.setBackgroundResource(R.drawable.card_bg);

            infoView.setBackgroundResource(R.drawable.red_button_bg);
            foulsView.setBackgroundResource(R.drawable.red_button_bg);
            qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            qrAllianceColor.setText("Blue");
            topView.setScaleX(1);
            layout1.setBackgroundResource(R.drawable.card_bg_blue);
            layout2.setBackgroundResource(R.drawable.card_bg_blue);
            layout3.setBackgroundResource(R.drawable.card_bg_blue);
            layout4.setBackgroundResource(R.drawable.card_bg_blue);

            infoView.setBackgroundResource(R.drawable.blue_button_bg);
            foulsView.setBackgroundResource(R.drawable.blue_button_bg);
            qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);
        }
        showQRcode();
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
        showAllValues();
    }
    @Override
    public boolean onSupportNavigateUp() {
        saveMatch();
        onBackPressed();
        return true;
    }
}