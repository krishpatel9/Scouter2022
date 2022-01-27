package com.example.scouter2022;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.scouter2022.adapter.TeamMatchAdapter;
import com.example.scouter2022.model.Match;
import com.example.scouter2022.model.TransferCode;
import com.example.scouter2022.util.PreferenceUtility;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Amaury Valdes on 2/6/2019.
 */

public class ManageMatchActivity extends AppCompatActivity {

    private static final String TAG = "ManageMatchActivity";

    private String INSTANCE_STATE = "INSTANCE_STATE";

    private ConstraintLayout constraintLayout;
    private Button selectAllButton;
    private Button deselectAllButton;
    private Button removeSelectedButton;

    private ListView listTeamMatches;

    private TeamMatchAdapter teamMatchArrayAdapter;
    private ArrayList<Match> arrayOfTeamMatches;
    private Map<String, TransferCode> allMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_match);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allMatches = PreferenceUtility.getAllMatches(getApplicationContext());
        arrayOfTeamMatches = new ArrayList<>();

        constraintLayout = findViewById(R.id.constraintLayout);
        selectAllButton = findViewById(R.id.selectAllID);
        deselectAllButton = findViewById(R.id.deselectAllID);
        removeSelectedButton = findViewById(R.id.removeSelectedID);
        listTeamMatches = findViewById(R.id.listViewID);

        // Create the adapter to convert the array to views
        teamMatchArrayAdapter = new TeamMatchAdapter(this, arrayOfTeamMatches);
        View header = getLayoutInflater().inflate(R.layout.manage_header, null);
        listTeamMatches.addHeaderView(header);
        listTeamMatches.setAdapter(teamMatchArrayAdapter);

        listTeamMatches.setOnItemClickListener(teamMatchesItemClickListener);

        populateAllTeamMatches();

        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: selectAllButton...");
                if (arrayOfTeamMatches.size() > 0) {
                    for (Match match : arrayOfTeamMatches) {
                        match.setChecked(true);
                    }
                    teamMatchArrayAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(constraintLayout, "There are no matches...", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        deselectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: deselectAllButton...");
                if (arrayOfTeamMatches.size() > 0) {
                    for (Match match: arrayOfTeamMatches) {
                        match.setChecked(false);
                    }
                    teamMatchArrayAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(constraintLayout, "There are no matches...", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        removeSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: removeSelectedButton...");
                if (arrayOfTeamMatches.size()> 0) {
                    showConnectDialog();
                } else {
                    Snackbar.make(constraintLayout, "There are no matches...", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }

    // The on-click listener for all matches in the listView
    private AdapterView.OnItemClickListener teamMatchesItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView adapter, View v, int position, long id) {

            Match match = (Match) adapter.getItemAtPosition(position);

            if (match != null) {
                Log.i(TAG, "onItemClick: Match ==> " + match);
                CheckBox cb = v.findViewById(R.id.manageCheckBoxID);

                if (match.isChecked()) {
                    match.setChecked(false);
                    cb.setChecked(false);
                } else {
                    match.setChecked(true);
                    cb.setChecked(true);
                }
            }
        }
    };

    private void showConnectDialog() {
        final Dialog dialog = new Dialog(ManageMatchActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.manage_dialog);

        // set the custom dialog components - text, image and button

        Button yesButton = dialog.findViewById(R.id.dialogManageYesBtnID);
        Button noButton = dialog.findViewById(R.id.dialogManageNoBtnID);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;

                int id = btn.getId();

                switch (id) {
                    case R.id.dialogManageYesBtnID:
                        Log.i(TAG, "Inside of Yes...");
                        removeSelectedMatches();
                        dialog.cancel();
                        break;

                    case R.id.dialogManageNoBtnID:
                        Log.i(TAG, "Inside of No...");
                        dialog.cancel();
                        break;

                    default:  Log.e(TAG, "Something is wrong...");
                        break;
                }
            }
        };

        yesButton.setOnClickListener(listener);
        noButton.setOnClickListener(listener);

        dialog.show();
    }

    private void removeSelectedMatches() {
        Log.i(TAG, "inside removeSelectedMatches()...");
        for (Match match: arrayOfTeamMatches) {
            Log.i(TAG, "Looping " + match.getTeamNumber() + ", " + match.getMatchNumber() + ", " + match.isChecked() + "...");
            if (match.isChecked()) {
                String key = match.getTeamNumber() + "/" + match.getMatchNumber();
                Log.i(TAG, "removing match with key: " + key);
                if (allMatches.containsKey(key)) {
                    allMatches.remove(key);
                }
            }
        }
        PreferenceUtility.saveAllMatches(getApplicationContext(), allMatches);
        populateAllTeamMatches();
    }

    private void populateAllTeamMatches() {
        ArrayList<TransferCode> allCodes = new ArrayList<>(allMatches.values());

        arrayOfTeamMatches.clear();
        for (TransferCode code: allCodes) {
            Match match = new Match();
            match.setChecked(false);
            match.setTeamNumber(code.getTeamNumber());
            match.setMatchNumber(code.getMatchNumber());
            match.setIsRed(code.getIsRed());
            arrayOfTeamMatches.add(match);
        }
        teamMatchArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}