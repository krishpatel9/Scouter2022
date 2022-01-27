package com.example.scouter2022.model;

public class SearchResults {
    private int matchNumber = 0;
    private int teamNumber = 0;
    private int isRed = 0;

    public SearchResults() {}

    public SearchResults(int matchNumber, int teamNumber, int isRed) {
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
        this.isRed = isRed;
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
        return "SearchResults{" +
                "matchNumber=" + matchNumber +
                ", teamNumber=" + teamNumber +
                ", isRed=" + isRed +
                '}';
    }
}
