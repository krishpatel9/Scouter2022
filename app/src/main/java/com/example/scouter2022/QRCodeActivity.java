package com.example.scouter2022;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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
    private static final int ENCODED = 0;
    private static final int DECODED = 1;

    private TextView qrTeamNumber;
    private TextView qrMatchNumber;
    private TextView qrAllianceColor;
    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;
    private String INSTANCE_STATE = "INSTANCE_STATE";

    private TextView disabledYNTextView;
    private TextView disqualifiedYNTextView;
    private TextView defenseNumTextView;
    private TextView disabledTextView;
    private TextView disqualifiedTextView;
    private TextView defenseTextView;
    private TextView noShowTextView;
    private TextView codeTextView;
    private View phaseBarView;
    private View topView;
    private ImageView QRCODE;
    private ImageView toMain;
    private ImageView toFinal;
    private Switch settingsSwitch;
    private int settingNum;
    
    private ConstraintLayout layout1,layout2,layout3,layout4;
    private View foulsView, infoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            this.getSupportActionBar().hide();}
        catch(NullPointerException e){
        }
        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        qrTeamNumber = findViewById(R.id.qrTeamNumTextView);
        qrMatchNumber = findViewById(R.id.qrMatchNumTextView);
        qrAllianceColor = findViewById(R.id.qrColorTextView);
        topView = findViewById(R.id.qrTopView);

        disabledYNTextView = findViewById(R.id.disabled_YN_textView);
        disqualifiedYNTextView = findViewById(R.id.disqualified_YN_textView);
        defenseNumTextView = findViewById(R.id.defense_num_textView);

        disabledTextView = findViewById(R.id.disabled_textView);
        disqualifiedTextView = findViewById(R.id.disqualified_textView);
        defenseTextView = findViewById(R.id.defense_textView);
        noShowTextView = findViewById(R.id.noShow_textView);
        infoView = findViewById(R.id.qrInfo_View);
        codeTextView = findViewById(R.id.qrCodeTextView);
        layout1 = findViewById(R.id.qrInfoLayout);
        settingsSwitch = findViewById(R.id.settingSwitch);
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
        if(settingsSwitch.isChecked()){
            settingsSwitch.setText("Encoded");
            showQRcode(ENCODED);
        }
        else{
            settingsSwitch.setText("Decoded");
            showQRcode(DECODED);
        }
        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //ENCODED
                    settingsSwitch.setText("Encoded");
                    showQRcode(ENCODED);
                }
                else{
                    settingsSwitch.setText("Decoded");
                    showQRcode(DECODED);
                }
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });
//        showQRcode(DECODED);
        qrTeamNumber.setText(String.valueOf(tcode.getTeamNumber()));
        qrMatchNumber.setText(String.valueOf(tcode.getMatchNumber()));
        if(tcode.getIsNoShow() ==0){
            disabledTextView.setText("Disabled: ");
            disqualifiedTextView.setText("Disqualified: ");
            defenseTextView.setText("Defense: ");
            noShowTextView.setText("0");
        }
        else{
            disabledTextView.setText("");
            disqualifiedTextView.setText("");
            defenseTextView.setText("");
            noShowTextView.setText("NO SHOW");
        }

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
    private void showQRcode(int setting){
        String alphaCode = tcode.GenerateCode(tcode.getBinaryString());
        String commaCode = tcode.toTabComma();
        codeTextView.setText(alphaCode);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        if (setting == ENCODED){
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(alphaCode, BarcodeFormat.QR_CODE, 300, 300);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                QRCODE.setImageBitmap(createInvertedBitmap(bitmap));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else if (setting == DECODED){
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(commaCode, BarcodeFormat.QR_CODE, 300, 300);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                QRCODE.setImageBitmap(createInvertedBitmap(bitmap));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
    private Bitmap createInvertedBitmap(Bitmap src) {
        ColorMatrix colorMatrix_Inverted =
                new ColorMatrix(new float[] {
                        -1,  0,  0,  0, 255,
                        0, -1,  0,  0, 255,
                        0,  0, -1,  0, 255,
                        0,  0,  0,  1,   0});

        ColorFilter ColorFilter_Sepia = new ColorMatrixColorFilter(
                colorMatrix_Inverted);

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setColorFilter(ColorFilter_Sepia);
        canvas.drawBitmap(src, 0, 0, paint);

        return bitmap;
    }
    private void showAllValues(){
        if(tcode.getIsNoShow() ==0) {
            if (tcode.getFinal_disabled() == 1) {
                disabledYNTextView.setText("Yes");
            } else {
                disabledYNTextView.setText("No");
            }
            if (tcode.getFinal_disqualified() == 1) {
                disqualifiedYNTextView.setText("Yes");
            } else {
                disqualifiedYNTextView.setText("No");
            }
            if(tcode.getFinal_defense() == 0){
                defenseNumTextView.setText("N/A");
            }
            else{
                defenseNumTextView.setText(Integer.toString(tcode.getFinal_defense()));
            }
            disabledTextView.setText("Disabled: ");
            disqualifiedTextView.setText("Disqualified: ");
            defenseTextView.setText("Defense: ");
            noShowTextView.setText("");
            toFinal.setEnabled(true);
            toFinal.setVisibility(View.VISIBLE);

        }
        else{
            disabledTextView.setText("");
            disqualifiedTextView.setText("");
            defenseTextView.setText("");
            disabledYNTextView.setText("");
            disqualifiedYNTextView.setText("");
            defenseNumTextView.setText("");
            noShowTextView.setText("NO\nSHOW");
            toFinal.setEnabled(false);
            toFinal.setVisibility(View.INVISIBLE);
        }

        // 1 = Home Win     Away Loss
        // 0 = Home Loss    Away Win
        // 2 = Tie          Tie
//        if(tcode.getIsRed() == 0){ // BLUE
//            if(tcode.getFinal_winningAlliance() == 0){ // Blue Loss
//                qrAllianceColor.setText("Red");
//                qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);
//            }
//            else if(tcode.getFinal_winningAlliance() == 1){ //Blue Win
//                qrAllianceColor.setText("Blue");
//                qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);
//            }
//            else if(tcode.getFinal_winningAlliance() == 2){ //Tie
//                qrAllianceColor.setText("Tie");
//                qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_icon, 0);
//            }
//        }
//        else if(tcode.getIsRed() == 1){ //RED
//            if(tcode.getFinal_winningAlliance() == 0){ //Red Loss
//                qrAllianceColor.setText("Blue");
//                qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);
//            }
//            else if(tcode.getFinal_winningAlliance() == 1){ //Red Win
//                qrAllianceColor.setText("Red");
//                qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);
//            }
//            else if(tcode.getFinal_winningAlliance() == 2){ //Tie
//                qrAllianceColor.setText("Tie");
//                qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_icon, 0);
//            }
//        }

    }
    private void saveMatch() {
        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
        Log.i(TAG, "saveMatch, KEY: " + key + ", TCODE..: " + tcode.toString());
        allMatches.put(key, tcode);
        PreferenceUtility.saveAllMatches(getApplicationContext(), allMatches);
    }
    private void setComponentBackground(int isRed) {
        if (isRed == 1) {
            topView.setScaleX(-1);
            layout1.setBackgroundResource(R.drawable.card_bg_red);
//            layout2.setBackgroundResource(R.drawable.card_bg_red);
//            layout3.setBackgroundResource(R.drawable.card_bg_red);
//            layout4.setBackgroundResource(R.drawable.card_bg_red);

            infoView.setBackgroundResource(R.drawable.button_bg_red);
//            foulsView.setBackgroundResource(R.drawable.button_bg_red);
            qrAllianceColor.setText("Red");
            qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_red, 0);

        } else {
            topView.setScaleX(1);
            layout1.setBackgroundResource(R.drawable.card_bg_blue);
//            layout2.setBackgroundResource(R.drawable.card_bg_blue);
//            layout3.setBackgroundResource(R.drawable.card_bg_blue);
//            layout4.setBackgroundResource(R.drawable.card_bg_blue);

            infoView.setBackgroundResource(R.drawable.button_bg_blue);
//            foulsView.setBackgroundResource(R.drawable.button_bg_blue);
            qrAllianceColor.setText("Blue");
            qrAllianceColor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.flag_blue, 0);
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
        showAllValues();
    }
    @Override
    public boolean onSupportNavigateUp() {
        saveMatch();
        onBackPressed();
        return true;
    }
}