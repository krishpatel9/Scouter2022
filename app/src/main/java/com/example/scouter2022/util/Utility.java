package com.example.scouter2022.util;

import android.content.Context;
import android.util.TypedValue;

import com.example.scouter2022.R;

import java.text.SimpleDateFormat;

/**
 * Created by Amaury Valdes on 1/21/19.
 */

public class Utility {
    private static final String TAG = "Utility";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSS");
    //private static FirebaseDatabase mDatabase;
    //private static FirebaseAuth mAuth;
    private static final String EMAIL = "frcscouter@roboraiders.com";
    private static final String PASSWORD = "Team75Rocks!";

//    public static void showCodeDialog(final Context context, final TransferCode tcode) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.code_dialog);
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//        // set the custom dialog components - text, image and button
//        TextView codeTeamNumber = dialog.findViewById(R.id.codeTeamNumberID);
//        TextView codeMatchNumber = dialog.findViewById(R.id.codeMatchNumberID);
//
//        TextView codeTextView = dialog.findViewById(R.id.codeID);
//        ImageView imageView = dialog.findViewById(R.id.qrCodeImageID);
//        Button codeButton = dialog.findViewById(R.id.codeBtnID);
//
//        String teamNumber = String.valueOf(tcode.getTeamNumber());
//        String matchNumber = String.valueOf(tcode.getMatchNumber());
//
//        if (teamNumber != null && !teamNumber.isEmpty()) {
//            teamNumber = "Team Number: " + teamNumber;
//        } else {
//            teamNumber = "Team Number is Missing !!!";
//        }
//
//        if (matchNumber != null && !matchNumber.isEmpty()) {
//            matchNumber = "Match Number: " + matchNumber;
//        } else {
//            matchNumber = "Match Number is Missing !!!";
//        }
//
//        String alphaCode = tcode.GenerateCode(tcode.getBinaryString());
//
//        codeTeamNumber.setText(teamNumber);
//        codeMatchNumber.setText(matchNumber);
//        codeTextView.setText(alphaCode);
//
//        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//        try {
//            BitMatrix bitMatrix = multiFormatWriter.encode(alphaCode, BarcodeFormat.QR_CODE, 300, 300);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//            imageView.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//        codeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        Log.i(TAG, "TCODE ==> " + alphaCode);
//        Log.i(TAG, "TCODE COMMA VALUES ==> " + tcode.toComma());
//        dialog.show();
//    }

    public static int getThemePrimaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static int getThemePrimaryColorDark(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        return value.data;
    }

    public static int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }

    public static int getThemeColorRed(final Context context){
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorRed, value, true);
        return value.data;
    }
    public static int getThemeColorBlue(final Context context){
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorBlue, value, true);
        return value.data;
    }
    public static int getThemeSecondaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorSecondary, value, true);
        return value.data;
    }

    public static int getThemeColorText(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorText, value, true);
        return value.data;
    }

    public static int getThemeColorHint(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorHint, value, true);
        return value.data;
    }

    public static int getThemeRippleColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.rippleColor, value, true);
        return value.data;
    }

    public static int getThemeColorTextDisabledButton(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorTextDisabledButton, value, true);
        return value.data;
    }

    public static int getThemeColorBackgroundDisabledButton(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorBackgroundDisabledButton, value, true);
        return value.data;
    }
}
