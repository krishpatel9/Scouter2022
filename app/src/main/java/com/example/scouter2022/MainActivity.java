package com.example.scouter2022;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.scouter2022.adapter.SearchMatchAdapter;
import com.example.scouter2022.model.SearchResults;
import com.example.scouter2022.model.TransferCode;
import com.example.scouter2022.util.Defaults;
import com.example.scouter2022.util.PreferenceUtility;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";


    private ConstraintLayout constraintLayout;
    private String selectedAlliance;
    private TransferCode tcode;
    private Map<String, TransferCode> allMatches;

    private TextView matchDetailTitle;
    private EditText teamNumber;
    private TextView teamNumberHint;
    private EditText matchNumber;
    private TextView matchNumberHint;
    private RadioGroup teamRadioGroup;
    private ImageView imageViewFlag;
    private ImageView mainBG;
    private Button autoButton;
    private Button autoSearchButton;
    private Button autoClearButton;
    private String teamNumberStrValue;
    private String matchNumberStrValue;
    private SearchResults mySelectedResults = null;

    private ArrayList<SearchResults> myResults;
    private SearchMatchAdapter searchMatchArrayAdapter;
    private ListView searchListView;

    private boolean teamError = true;
    private boolean matchError = true;
    private String teamAllianceValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //allMatches = PreferenceUtility.getAllMatches(getApplicationContext());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tcode = new TransferCode();
        myResults = new ArrayList<>();

        constraintLayout = findViewById(R.id.contraintLayout);
        matchDetailTitle = findViewById(R.id.matchDetailTitleID);
        teamNumber = findViewById(R.id.teamNumberID);
        matchNumber = findViewById(R.id.matchNumberID);
        imageViewFlag = findViewById(R.id.teleAllianceFlagID);
        mainBG = findViewById(R.id.Main_BG);
        teamRadioGroup = findViewById(R.id.teamRadioGroupID);
        autoButton = findViewById(R.id.autoButtonID);
        autoSearchButton = findViewById(R.id.autoSearchBtnID);
        autoClearButton = findViewById(R.id.autoClearBtnID);
        teamNumberHint = findViewById(R.id.teamNumberHintID);
        matchNumberHint = findViewById(R.id.matchNumberHintID);

        teamNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int teamNum = 0;

                teamNumberStrValue = teamNumber.getText().toString();
                Log.i(TAG, "Inside onKey, teamNumber: [" + teamNumberStrValue + "]");

                if (teamNumberStrValue != null && !teamNumberStrValue.isEmpty()) {
                    try {
                        teamNum = Integer.parseInt(teamNumberStrValue);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e);
                        //teamNum = myPrefs.getMaxTeamNumber();
                        teamNumber.setText(teamNum);
                    }
                    if (teamNum > Defaults.MAX_TEAM_NUMBER) {
                        teamError = true;
                        teamNumberHint.setText(String.format("Team Number must NOT exceed %d.\nPlease re-enter team number...", Defaults.MAX_TEAM_NUMBER));
                        //Log.i(TAG, "Inside onKey, color: [" + Utility.getThemePrimaryColor(MainActivity.this) + "]");
                        teamNumberHint.setTextColor(getResources().getColor(R.color.colorRed));
                    } else {
                        teamError = false;
                        teamNumberHint.setText(R.string.team_number_hint);
                        //Log.i(TAG, "Inside onKey, color: [" + Utility.getThemeColorHint(MainActivity.this) + "]");
                        teamNumberHint.setTextColor(getResources().getColor(R.color.colorHint));
                    }
                } else {
                    teamError = true;
                }
                checkSearchButton("teamNumber");
                checkClearButton("teamNumber");
                checkAutonomousButton("teamNumber");
            }
        });

        matchNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int matchNum = 0;

                matchNumberStrValue = matchNumber.getText().toString();
                Log.i(TAG, "Inside onKey, matchNumber: [" + matchNumberStrValue + "]");

                if (matchNumberStrValue != null && !matchNumberStrValue.isEmpty()) {
                    try {
                        matchNum = Integer.parseInt(matchNumberStrValue);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e);
                        matchNum = Defaults.MAX_MATCH_NUMBER;
                    }
                    if (matchNum > Defaults.MAX_MATCH_NUMBER) {
                        matchError = true;
                        matchNumberHint.setText(String.format("Match Number must NOT exceed %d.\nPlease re-enter match number...", Defaults.MAX_MATCH_NUMBER));
                        matchNumberHint.setTextColor(getResources().getColor(R.color.colorRed));
                    } else {
                        matchError = false;
                        matchNumberHint.setText(R.string.match_number_hint);
                        matchNumberHint.setTextColor(getResources().getColor(R.color.colorHint));
                    }
                } else {
                    matchError = true;
                }
                checkSearchButton("matchNumber");
                checkClearButton("matchNumber");
                checkAutonomousButton("matchNumber");
            }
        });   // End of MatchNumber

        teamRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i(TAG, "teamRadioGroup: setOnCheckedChangeListener()...");
                if (checkedId == R.id.redTeamID) {
                    imageViewFlag.setImageDrawable(getResources().getDrawable(R.drawable.flag_red));
                    teamAllianceValue = Defaults.RED_ALLIANCE;
                } else if (checkedId == R.id.blueTeamID) {
                    imageViewFlag.setImageDrawable(getResources().getDrawable(R.drawable.flag_blue));
                    teamAllianceValue = Defaults.BLUE_ALLIANCE;
                }
                checkClearButton("teamRadioGroup");
                checkAutonomousButton("teamRadioGroup");
            }

        });

        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
//            mainBG.setBackgroundResource(R.drawable.main_bg_p);
        }
        else{
//            mainBG.setBackgroundResource(R.drawable.main_bg_l);
        }

        autoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int teamNum = 0;
                int matchNum = 0;

                allMatches = PreferenceUtility.getAllMatches(getApplicationContext());

                tcode = new TransferCode();

                String teamNumberValue = teamNumber.getText().toString();
                String matchNumberValue = matchNumber.getText().toString();
                String key = teamNumberValue + "/" + matchNumberValue;
                if (teamAllianceValue.equalsIgnoreCase(Defaults.RED_ALLIANCE)) {
                    tcode.setIsRed(1);
                }
                Log.i(TAG, "autoButton clicked...");
                Log.i(TAG, "teamNumberValue  ==> " + teamNumberValue);
                Log.i(TAG, "matchNumberValue ==> " + matchNumberValue);
                Log.i(TAG, "teamAllianceValue ==> " + teamAllianceValue);

                if (teamNumberValue != null && !teamNumberValue.isEmpty()) {
                    try {
                        teamNum = Integer.parseInt(teamNumberStrValue);
                        tcode.setTeamNumber(teamNum);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e);
                    }
                }

                if (matchNumberValue != null && !matchNumberValue.isEmpty()) {
                    try {
                        matchNum = Integer.parseInt(matchNumberStrValue);
                        tcode.setMatchNumber(matchNum);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e);
                    }
                }

                if (allMatches.containsKey(key)) {
                    showMatchFoundDialog();
                } else {
                    proceedSandstormActivity();
                }
            }
        });  // End of AutoButton

        autoSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> allKeys = new ArrayList<>(allMatches.keySet());
                myResults.clear();

                String teamNumberValue = teamNumber.getText().toString();
                String matchNumberValue = matchNumber.getText().toString();
                String key = teamNumberValue + "/" + matchNumberValue;

                Log.i(TAG, "onClick: clicked...");
                Log.i(TAG, "teamNumberValue  ==> " + teamNumberValue);
                Log.i(TAG, "matchNumberValue ==> " + matchNumberValue);
                Log.i(TAG, "key ===============> " + key);
                Log.i(TAG, "allKeys ===========> " + allKeys);

        /*for (String k : allKeys) {
          if (k.contains(key)) {
            myList.add(k);
          }
        }*/

                for (String k : allKeys) {
                    StringTokenizer token = new StringTokenizer(k, "/");
                    String leftKey = token.nextToken();
                    String rightKey = token.nextToken();
                    boolean isFound = false;

                    if (teamNumberValue.length() > 0 && matchNumberValue.length() == 0) {
                        if (leftKey.contains(teamNumberValue)) {
                            isFound = true;
                        } else {
                            isFound = false;
                        }
                    }

                    if (matchNumberValue.length() > 0 && teamNumberValue.length() == 0) {
                        if (rightKey.contains(matchNumberValue)) {
                            isFound = true;
                        } else {
                            isFound = false;
                        }
                    }

                    if (teamNumberValue.length() > 0 && matchNumberValue.length() > 0) {
                        if (leftKey.contains(teamNumberValue) && rightKey.contains(matchNumberValue)) {
                            isFound = true;
                        } else {
                            isFound = false;
                        }
                    }

                    if (isFound) {
                        if (allMatches.containsKey(k)) {
                            TransferCode code = allMatches.get(k);
                            SearchResults results = new SearchResults();
                            results.setIsRed(code.getIsRed());
                            results.setMatchNumber(code.getMatchNumber());
                            results.setTeamNumber(code.getTeamNumber());
                            myResults.add(results);
                        }
                    }
                }

                if (myResults.size() > 0) {
                    Log.i(TAG, "myResults  ==> " + myResults);
                    showSearchDialog(MainActivity.this, myResults);
                } else {
                    showNotFoundDialog(MainActivity.this, teamNumberValue, matchNumberValue);
                }
            }
        });  // End of endGameQRCodeBtn

        autoClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "autoClearButton: clicked...");
                teamRadioGroup.clearCheck();
                teamAllianceValue = null;
                teamNumber.setText("");
                matchNumber.setText("");
                teamError = false;
                matchError = false;
                teamNumberHint.setText(R.string.team_number_hint);
                teamNumberHint.setTextColor(getResources().getColor(R.color.colorHint));
                matchNumberHint.setText(R.string.match_number_hint);
                matchNumberHint.setTextColor(getResources().getColor(R.color.colorHint));
                imageViewFlag.setImageDrawable(getResources().getDrawable(R.drawable.flag_icon));
                teamNumber.requestFocus();
            }
        });
    }

    private void checkSearchButton(String caller) {
        Log.i(TAG, "checkSearchButton(" + caller + "):  teamError==> " + teamError);

        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());

        String teamNumberValue = teamNumber.getText().toString();
        String matchNumberValue = matchNumber.getText().toString();

        if (teamNumberValue.length() > 0 || matchNumberValue.length() > 0) {
            autoSearchButton.setEnabled(true);
            autoSearchButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search_white, 0, 0, 0);
            //autoSearchButton
            autoSearchButton.setTextColor(getResources().getColor(R.color.colorText));
            autoSearchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            autoSearchButton.setEnabled(false);
            autoSearchButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search_black, 0, 0, 0);
            autoSearchButton.setTextColor(getResources().getColor(R.color.colorTextDisabledButton));
            autoSearchButton.setBackgroundColor(getResources().getColor(R.color.colorBackgroundDisabledButton));
        }

    }

    private void checkClearButton(String caller) {
        Log.i(TAG, "checkClearButton(" + caller + "):         teamError==> " + teamError);
        Log.i(TAG, "checkClearButton(" + caller + "):        matchError==> " + matchError);
        Log.i(TAG, "checkClearButton(" + caller + "): teamAllianceValue==> " + teamAllianceValue);

        String teamNumberValue = teamNumber.getText().toString();
        String matchNumberValue = matchNumber.getText().toString();

        if (teamNumberValue.length() > 0 || matchNumberValue.length() > 0 || teamAllianceValue != null) {
            autoClearButton.setEnabled(true);
            autoClearButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross_white, 0, 0, 0);
            //autoClearButton
            autoClearButton.setTextColor(getResources().getColor(R.color.colorText));
            autoClearButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            autoClearButton.setEnabled(false);
            autoClearButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross_black, 0, 0, 0);
            autoClearButton.setTextColor(getResources().getColor(R.color.colorTextDisabledButton));
            autoClearButton.setBackgroundColor(getResources().getColor(R.color.colorBackgroundDisabledButton));
        }
    }

    private void checkAutonomousButton(String caller) {
        Log.i(TAG, "checkAutonomousButton(" + caller + "):  teamError==> " + teamError);
        Log.i(TAG, "checkAutonomousButton(" + caller + "): matchError==> " + matchError);

        String teamNum = teamNumber.getText().toString();
        String matchNum = matchNumber.getText().toString();

        if (!teamError && !matchError && teamNum.length() > 0 && matchNum.length() > 0 && teamAllianceValue != null) {
            autoButton.setEnabled(true);
            autoButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right_white, 0, 0, 0);
            autoButton.setTextColor(getResources().getColor(R.color.colorText));
            autoButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            autoButton.setEnabled(false);
            autoButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right_black, 0, 0, 0);
            autoButton.setTextColor(getResources().getColor(R.color.colorTextDisabledButton));
            autoButton.setBackgroundColor(getResources().getColor(R.color.colorBackgroundDisabledButton));
        }
    }

    private void showSearchDialog(final Context context, ArrayList<SearchResults> myResults) {
        final Dialog dialog = new Dialog(context);
        mySelectedResults = null;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button okButton = dialog.findViewById(R.id.searchOkID);
        Button cancelButton = dialog.findViewById(R.id.searchCancelID);

        ListView searchListView = dialog.findViewById(R.id.searchListViewID);

        // Create the adapter to convert the array to views
        SearchMatchAdapter searchMatchArrayAdapter = new SearchMatchAdapter(this, myResults);
        View header = getLayoutInflater().inflate(R.layout.search_header, null);
        searchListView.addHeaderView(header);
        searchListView.setAdapter(searchMatchArrayAdapter);
        searchListView.setOnItemClickListener(searchMatchesItemClickListener);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySelectedResults != null) {
                    Log.i(TAG, "okButton: mySelectedResults ==> " + mySelectedResults);

                    if (mySelectedResults.getIsRed() == 1) {
                        teamRadioGroup.check(R.id.redTeamID);
                    } else {
                        teamRadioGroup.check(R.id.blueTeamID);
                    }
                    teamNumber.setText(String.format("%d", mySelectedResults.getTeamNumber()));
                    matchNumber.setText(String.format("%d", mySelectedResults.getMatchNumber()));
                    autoButton.performClick();
                }
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySelectedResults = null;
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // The on-click listener for all results in the listView
    private AdapterView.OnItemClickListener searchMatchesItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView adapter, View v, int position, long id) {

            mySelectedResults = (SearchResults) adapter.getItemAtPosition(position);

            Log.i(TAG, "AdapterView.OnItemClickListener: inside...");

            if (mySelectedResults != null) {
                Log.i(TAG, "onItemClick: mySelectedResults ==> " + mySelectedResults);
            }
        }
    };

    private void showNotFoundDialog(final Context context, String team, String match) {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_not_found);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView teamDetails = dialog.findViewById(R.id.notFoundTeamID);
        TextView matchDetails = dialog.findViewById(R.id.notFoundMatchID);
        teamDetails.setText("Team Details: " + team);
        matchDetails.setText("Match Details: " + match);
        Button okButton = dialog.findViewById(R.id.notFoundOkBtnID);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void proceedSandstormActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(tcode);

        Log.i(TAG, "proceedSandstormActivity: intent JSON ==> " + json);
        saveMatch();

        Intent intent = new Intent(MainActivity.this, AutoActivity.class);
        intent.putExtra("code", json);
        //startActivityForResult(intent, resultCode);
        startActivity(intent);

        String key = tcode.getTeamNumber()+ "/" + tcode.getMatchNumber();
        tcode = allMatches.get(key);
        Log.i(TAG, "After proceedSandstormActivity(): " + tcode.toString());
    }

    private void saveMatch() {
        String key = tcode.getTeamNumber() + "/" + tcode.getMatchNumber();
        allMatches.put(key, tcode);

        Snackbar.make(constraintLayout, "Match has been saved...", Snackbar.LENGTH_LONG).show();
        PreferenceUtility.saveAllMatches(getApplicationContext(), allMatches);
    }

    private void showMatchFoundDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.match_found_dialog);

        // set the custom dialog components - text, image and button
        TextView teamDetails = dialog.findViewById(R.id.dialogTeamID);
        TextView matchDetails = dialog.findViewById(R.id.dialogMatchID);
        teamDetails.setText("Team Details: " + tcode.getTeamNumber());
        matchDetails.setText("Match Details: " + tcode.getMatchNumber());

        Button existingButton = dialog.findViewById(R.id.dialogExistingBtnID);
        Button overwriteButton = dialog.findViewById(R.id.dialogOverwriteBtnID);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                int id = btn.getId();

                String teamNumberValue = teamNumber.getText().toString();
                String matchNumberValue = matchNumber.getText().toString();
                String key = teamNumberValue + "/" + matchNumberValue;

                switch (id) {
                    case R.id.dialogExistingBtnID:
                        Log.i(TAG, "Inside of use existing...");
                        tcode = allMatches.get(key);
                        Log.i(TAG, "Existing TCODE..." + tcode.toString());
                        dialog.cancel();
                        proceedSandstormActivity();
                        break;

                    case R.id.dialogOverwriteBtnID:
                        Log.i(TAG, "Inside of overwrite...");
                        tcode = new TransferCode();
                        tcode.setTeamNumber(Integer.valueOf(teamNumberValue));
                        tcode.setMatchNumber(Integer.valueOf(matchNumberValue));
                        if (teamAllianceValue.equalsIgnoreCase(Defaults.RED_ALLIANCE)) {
                            tcode.setIsRed(1);
                        } else {
                            tcode.setIsRed(0);
                        }
                        dialog.cancel();
                        proceedSandstormActivity();
                        break;

                    default:
                        Log.e(TAG, "Something is wrong...");
                        break;
                }
            }
        };

        existingButton.setOnClickListener(listener);
        overwriteButton.setOnClickListener(listener);

        dialog.show();
    }

    private void showSoftwareDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.software_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // set the custom dialog components - text, image and button
        TextView aboutTitle = dialog.findViewById(R.id.softwareTitleID);
        Button aboutButton = dialog.findViewById(R.id.softwareBtnID);
        final TextView urlText = dialog.findViewById(R.id.softwareURLID);

        urlText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick event...");
                Uri uri = Uri.parse(urlText.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: aboutButton has been clicked...");
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void showAboutDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // set the custom dialog components - text, image and button
        TextView aboutTitle = dialog.findViewById(R.id.aboutTitleID);
        TextView aboutTextView = dialog.findViewById(R.id.aboutTextViewID);
        Button aboutButton = dialog.findViewById(R.id.aboutBtnID);
        TextView versionName = dialog.findViewById(R.id.versionNameID);
        TextView versionCode = dialog.findViewById(R.id.versionCodeID);

        String title = getResources().getString(R.string.about_team_title);
        String members = getResources().getString(R.string.about_team_details);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            int verCode = pInfo.versionCode;
            String verName = pInfo.versionName;

            versionName.setText("Version Code: " + verCode);
            versionCode.setText("Version Name: " + verName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "showAboutDialog: Title ====> " + title);
        Log.i(TAG, "showAboutDialog: Members ==> " + members);

        aboutTitle.setText(Html.fromHtml(title));
        aboutTextView.setText(Html.fromHtml(members));

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: aboutButton has been clicked...");
                dialog.cancel();
            }
        });

        dialog.show();
    }


    private void showManageMatchActivity() {
        Intent intent = new Intent(MainActivity.this, ManageMatchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_matches) {
            showManageMatchActivity();
        } else if (id == R.id.nav_code) {
//            Utility.showCodeDialog(MainActivity.this, tcode);
        } else if (id == R.id.nav_settings) {
            //showSettingsActivity();
        } else if (id == R.id.nav_software) {
            showSoftwareDialog();
        } else if (id == R.id.nav_about) {
            showAboutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
