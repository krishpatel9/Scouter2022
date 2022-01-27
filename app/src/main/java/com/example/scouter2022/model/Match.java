package com.example.scouter2022.model;

public class Match {
    private boolean isChecked;
    private int matchNumber = 0;
    private int teamNumber = 0;
    private int isRed = 0;

    public Match() {}

    public Match(boolean isChecked, int matchNumber, int teamNumber, int isRed) {
        this.isChecked = isChecked;
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
        this.isRed = isRed;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getIsRed() {
        return isRed;
    }

    public void setIsRed(int isRed) {
        this.isRed = isRed;
    }

    @Override
    public String toString() {
        return "Match{" +
                "isChecked=" + isChecked +
                ", matchNumber=" + matchNumber +
                ", teamNumber=" + teamNumber +
                ", isRed=" + isRed +
                '}';
    }
}
