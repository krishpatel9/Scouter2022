package com.example.scouter2022.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scouter2022.R;
import com.example.scouter2022.model.Match;


import java.util.ArrayList;


public class TeamMatchAdapter extends ArrayAdapter<Match> {

    public TeamMatchAdapter(Context context, ArrayList<Match> matches) {
        super(context, 0, matches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Match match = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_details, parent, false);
        }
        // Lookup view for data population
        CheckBox isChecked = convertView.findViewById(R.id.manageCheckBoxID);
        TextView teamNumber = convertView.findViewById(R.id.manageTeamID);
        TextView matchNumber = convertView.findViewById(R.id.manageMatchID);
        ImageView alliance = convertView.findViewById(R.id.manageAllianceID);

        // Populate the data into the template view using the data object
        isChecked.setChecked(match.isChecked());
        teamNumber.setText(String.valueOf(match.getTeamNumber()));
        matchNumber.setText(String.valueOf(match.getMatchNumber()));
        if (match.getIsRed() == 1) {
            alliance.setImageDrawable(alliance.getResources().getDrawable(R.drawable.flag_red));
        } else {
            alliance.setImageDrawable(alliance.getResources().getDrawable(R.drawable.flag_blue));
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
