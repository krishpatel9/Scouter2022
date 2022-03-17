package com.example.scouter2022.model;

import android.util.Log;

public class TransferCode {
    private static String TAG = "TransferCode";

    private int matchNumber = 0;    // 7 bits    (max 127)
    private int teamNumber = 0;     // 14 bits   (max 16383)
    private int isRed = 0;         // 1 bit     (0=blue, 1=red)
    private int isNoShow = 0;       // 1 bit

    private int auto_cross_line = 0;   // 1 bit
    private int auto_shoot_attempt = 0;   // 1 bit

    private int auto_allianceCargo_top_s = 0; // 4 bits
    private int auto_allianceCargo_bot_s = 0; // 4 bits
    private int auto_allianceCargo_top_f = 0; // 4 bits
    private int auto_allianceCargo_bot_f = 0; // 4 bits
    private int auto_away_balls = 0; // 4 bits

    private int auto_humanMissed = 0;
    private int auto_humanScored = 0;

    private int tele_shoot_attempt = 0;   // 1 bit
    private int tele_allianceCargo_top_s = 0; // 4 bits
    private int tele_allianceCargo_bot_s = 0; // 4 bits
    private int tele_allianceCargo_top_f = 0; // 4 bits
    private int tele_allianceCargo_bot_f = 0; // 4 bits
    private int tele_away_balls = 0; // 4 bits

    private int endgame_attempt = 0;   // 1 bit
    private int endgame_park = 0;        // 1 bits
    private int endgame_hang = 0;        // 4 bits
    private int endgame_ringContact = 0;     // 4 bits
    private int endgame_ringFinish = 0;        // 4 bits
    private int endgame_climbTime = 0;        // 4 bits

    private int final_disabled = 0;         // 1 bits
    private int final_disqualified = 0;     // 1 bits
    private int final_defense = 0;     // 1 bits

    private int final_zone = 0; //1,2,or 3


    private String[] map = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String mapString = "ABCDEFGHJKLMNOPRSTUWXYZ123456789";

    public TransferCode() {
        Log.i(TAG, "Inside TransferCode()...");
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

    public void setIsNoShow(int isNoShow) {
        this.isNoShow = isNoShow;
    }

    public int getIsNoShow() {
        return isNoShow;
    }
    public void setNoShow(){
        this.isNoShow = 1;
        this.auto_cross_line =0;
        this.auto_shoot_attempt = 0;
        this.auto_allianceCargo_bot_s = 0;
        this.auto_allianceCargo_top_s = 0;
        this.auto_allianceCargo_bot_f = 0;
        this.auto_allianceCargo_top_f = 0;
        this.auto_humanScored = 0;
        this.auto_humanMissed = 0;
        this.auto_away_balls = 0;
        this.tele_shoot_attempt = 0;
        this.tele_allianceCargo_bot_s = 0;
        this.tele_allianceCargo_top_s = 0;
        this.tele_allianceCargo_bot_f = 0;
        this.tele_allianceCargo_top_f = 0;
        this.tele_away_balls = 0;
        this.endgame_attempt = 0;
        this.endgame_park = 0;
        this.endgame_hang = 0;
        this.endgame_ringContact = 0;
        this.endgame_ringFinish = 0;
        this.final_defense = 0;
        this.final_zone = 0;
        this.final_disqualified = 0;
        this.final_disabled = 0;

}
    public int getIsRed() {
        return isRed;
    }

    public void setIsRed(int isRed) {
        this.isRed = isRed;
    }

    public int getAuto_cross_line() {
        return auto_cross_line;
    }

    public void setAuto_cross_line(int auto_cross_line) {
        this.auto_cross_line = auto_cross_line;
    }

    public int getAuto_shoot_attempt() {
        return auto_shoot_attempt;
    }

    public void setAuto_shoot_attempt(int auto_shoot_attempt) {
        this.auto_shoot_attempt = auto_shoot_attempt;
    }

    public int getAuto_humanMissed() {
        return auto_humanMissed;
    }

    public void setAuto_humanMissed(int auto_humanMissed) {
        this.auto_humanMissed = auto_humanMissed;
    }

    public int getAuto_humanScored() {
        return auto_humanScored;
    }

    public void setAuto_humanScored(int auto_humanScored) {
        this.auto_humanScored = auto_humanScored;
    }

    public int getAuto_allianceCargo_bot_f() {
        return auto_allianceCargo_bot_f;
    }

    public void setAuto_allianceCargo_bot_f(int auto_allianceCargo_bot_f) {
        this.auto_allianceCargo_bot_f = auto_allianceCargo_bot_f;
    }

    public int getAuto_allianceCargo_top_s() {
        return auto_allianceCargo_top_s;
    }

    public void setAuto_allianceCargo_top_s(int auto_allianceCargo_top_s) {
        this.auto_allianceCargo_top_s = auto_allianceCargo_top_s;
    }

    public int getAuto_allianceCargo_bot_s() {
        return auto_allianceCargo_bot_s;
    }

    public void setAuto_allianceCargo_bot_s(int auto_allianceCargo_bot_s) {
        this.auto_allianceCargo_bot_s = auto_allianceCargo_bot_s;
    }

    public int getAuto_away_balls() {
        return auto_away_balls;
    }

    public void setAuto_away_balls(int auto_away_balls) {
        this.auto_away_balls = auto_away_balls;
    }

    public int getAuto_allianceCargo_top_f() {
        return auto_allianceCargo_top_f;
    }

    public void setAuto_allianceCargo_top_f(int auto_allianceCargo_top_f) {
        this.auto_allianceCargo_top_f = auto_allianceCargo_top_f;
    }

    public int getTele_shoot_attempt() {
        return tele_shoot_attempt;
    }

    public void setTele_shoot_attempt(int tele_shoot_attempt) {
        this.tele_shoot_attempt = tele_shoot_attempt;
    }

    public int getTele_allianceCargo_top_s() {
        return tele_allianceCargo_top_s;
    }

    public void setTele_allianceCargo_top_s(int tele_allianceCargo_top_s) {
        this.tele_allianceCargo_top_s = tele_allianceCargo_top_s;
    }

    public int getTele_allianceCargo_bot_s() {
        return tele_allianceCargo_bot_s;
    }

    public void setTele_allianceCargo_bot_s(int tele_allianceCargo_bot_s) {
        this.tele_allianceCargo_bot_s = tele_allianceCargo_bot_s;
    }

    public int getTele_away_balls() {
        return tele_away_balls;
    }

    public void setTele_away_balls(int tele_away_balls) {
        this.tele_away_balls = tele_away_balls;
    }

    public int getTele_allianceCargo_top_f() {
        return tele_allianceCargo_top_f;
    }

    public void setTele_allianceCargo_top_f(int tele_allianceCargo_top_f) {
        this.tele_allianceCargo_top_f = tele_allianceCargo_top_f;
    }

    public int getTele_allianceCargo_bot_f() {
        return tele_allianceCargo_bot_f;
    }

    public void setTele_allianceCargo_bot_f(int tele_allianceCargo_bot_f) {
        this.tele_allianceCargo_bot_f = tele_allianceCargo_bot_f;
    }

    public int getEndgame_attempt() {
        return endgame_attempt;
    }

    public void setEndgame_attempt(int endgame_attempt) {
        this.endgame_attempt = endgame_attempt;
    }

    public int getEndgame_park() {
        return endgame_park;
    }

    public void setEndgame_park(int endgame_park) {
        this.endgame_park = endgame_park;
    }

    public int getEndgame_hang() {
        return endgame_hang;
    }

    public void setEndgame_hang(int endgame_hang) {
        this.endgame_hang = endgame_hang;
    }

    public int getEndgame_ringContact() {
        return endgame_ringContact;
    }

    public void setEndgame_ringContact(int endgame_ringContact) {
        this.endgame_ringContact = endgame_ringContact;
    }

    public int getEndgame_ringFinish() {
        return endgame_ringFinish;
    }

    public void setEndgame_ringFinish(int endgame_ringFinish) {
        this.endgame_ringFinish = endgame_ringFinish;
    }

    public int getFinal_defense() {
        return final_defense;
    }

    public void setFinal_defense(int final_defense) {
        this.final_defense = final_defense;
    }

    public int getFinal_disabled() {
        return final_disabled;
    }

    public void setFinal_disabled(int final_disabled) {
        this.final_disabled = final_disabled;
    }

    public int getFinal_disqualified() {
        return final_disqualified;
    }

    public void setFinal_disqualified(int final_disqualified) {
        this.final_disqualified = final_disqualified;
    }

    public int getFinal_zone() {
        return final_zone;
    }

    public void setFinal_zone(int final_zone) {
        this.final_zone = final_zone;
    }

    public void setEndgame_climbTime(int endgame_climbTime) {
        this.endgame_climbTime = endgame_climbTime;
    }

    public int getEndgame_climbTime() {
        return endgame_climbTime;
    }
    public static void main(String[] args) {
        TransferCode t = new TransferCode();
        t.setTeamNumber(75);
        t.setMatchNumber(23);
        t.setIsRed(0);
        t.setAuto_cross_line(1);
        t.setAuto_allianceCargo_bot_f(1);
        t.setAuto_allianceCargo_bot_s(2);
        t.setEndgame_hang(1);
        String n = t.getBinaryString();
        String s = t.GenerateCode(n);
        TransferCode tc2 = t.Decode(s);
        boolean b = t.issame(tc2);
        if (b)
            System.out.println("they are the same");
        System.out.println(s);
    }
    public String[] getMap() {
        return map;
    }
    public void setMap(String[] map) {
        this.map = map;
    }
    public static String getMapString() {
        return mapString;
    }
    public static void setMapString(String mapString) {
        TransferCode.mapString = mapString;
    }
    private int getPos(String s) {
        int pos = 0;
        for (int i = 0; i < 32; i++) {
            if (s.equals(map[i])) {
                pos = i;
                return pos;
            }
        }
        return pos;
    }
    private TransferCode Decode(String code) {//loop thru code string, make binary string, use binary string to assign each field value
        int pos = 0;
        StringBuilder bitPattern = new StringBuilder();
        while (pos < code.length()) {
            String s = code.substring(pos, pos + 1);
            int l = getPos(s);
            StringBuilder b2 = new StringBuilder(Integer.toBinaryString(l));
            while (b2.length() < 5)
                b2.insert(0, "0");
            bitPattern.append(b2);
            pos++;
        }
        TransferCode tc = new TransferCode();
        GetFieldsFromBinaryString(tc, bitPattern.toString());
        return tc;
    }
    private static String GetIntBinaryString(int n) {
        char[] b = new char[32];
        int pos = 31;
        int i = 0;

        while (i < 32) {
            if ((n & (1 << i)) != 0) {
                b[pos] = '1';
            } else {
                b[pos] = '0';
            }
            pos--;
            i++;
        }
        return new String(b);
    }
    public String GenerateCode(String bstring) {
        //loop thru , take 5 bits at a time, convert to int and lookup character, handle last char may be less than 5
        int pos = 0;
        StringBuilder code = new StringBuilder();
        while (pos < bstring.length()) {
            int l = 5;
            int nl = bstring.length() - pos;
            if (nl < 5)
                l = nl;
            String s = bstring.substring(pos, pos + l);
            int a = Integer.parseInt(s, 2);
            code.append(map[a]);
            pos = pos + 5;
        }
        return code.toString().toUpperCase();
    }
    private int rangeCheck(int low, int high, int val) {
        if (val > high)
            val = high;
        if ((val < low))
            val = low;
        return val;
    }

    public String getBinaryString() {
        String s = "";
        s += TransferCode.GetIntBinaryString(matchNumber).substring(25, 32);
        s += TransferCode.GetIntBinaryString(teamNumber).substring(18, 32);
        s += TransferCode.GetIntBinaryString(isRed).substring(31, 32);
        s += TransferCode.GetIntBinaryString(isNoShow).substring(31, 32);


        s += TransferCode.GetIntBinaryString(auto_cross_line).substring(31, 32);
        s += TransferCode.GetIntBinaryString(auto_shoot_attempt).substring(31, 32);

        s += TransferCode.GetIntBinaryString(auto_allianceCargo_top_s).substring(26, 32);
        s += TransferCode.GetIntBinaryString(auto_allianceCargo_bot_s).substring(26, 32);
        s += TransferCode.GetIntBinaryString(auto_allianceCargo_top_f).substring(26, 32);
        s += TransferCode.GetIntBinaryString(auto_allianceCargo_bot_f).substring(26, 32);
        s += TransferCode.GetIntBinaryString(auto_away_balls).substring(26, 32);

        s += TransferCode.GetIntBinaryString(auto_humanMissed).substring(26, 32);
        s += TransferCode.GetIntBinaryString(auto_humanScored).substring(26, 32);

        s += TransferCode.GetIntBinaryString(tele_shoot_attempt).substring(31, 32);

        s += TransferCode.GetIntBinaryString(tele_allianceCargo_top_s).substring(26, 32);
        s += TransferCode.GetIntBinaryString(tele_allianceCargo_bot_s).substring(26, 32);
        s += TransferCode.GetIntBinaryString(tele_allianceCargo_top_f).substring(26, 32);
        s += TransferCode.GetIntBinaryString(tele_allianceCargo_bot_f).substring(26, 32);
        s += TransferCode.GetIntBinaryString(tele_away_balls).substring(26, 32);

        s += TransferCode.GetIntBinaryString(endgame_attempt).substring(31, 32);
        s += TransferCode.GetIntBinaryString(endgame_park).substring(31, 32);
        s += TransferCode.GetIntBinaryString(endgame_hang).substring(26, 32);
        s += TransferCode.GetIntBinaryString(endgame_ringContact).substring(26, 32);
        s += TransferCode.GetIntBinaryString(endgame_ringFinish).substring(26, 32);
        s += TransferCode.GetIntBinaryString(endgame_climbTime).substring(26, 32);

        s += TransferCode.GetIntBinaryString(final_defense).substring(26, 32);
        s += TransferCode.GetIntBinaryString(final_disqualified).substring(31, 32);
        s += TransferCode.GetIntBinaryString(final_disabled).substring(31, 32);

        s += TransferCode.GetIntBinaryString(final_zone).substring(26, 32);


        s += TransferCode.GetIntBinaryString(0).substring(27 , 32);//filler
        return s;
    }
    private void GetFieldsFromBinaryString(TransferCode tc, String src) {
        int offset = 0;
        tc.matchNumber = Integer.parseInt(src.substring(0, 7), 2);
        offset = offset + 7;
        tc.teamNumber = Integer.parseInt(src.substring(offset, offset + 14), 2);
        offset = offset + 14;
        tc.isRed = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;
        tc.isNoShow = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;

        tc.auto_cross_line = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;
        tc.auto_shoot_attempt = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;

        tc.auto_allianceCargo_top_s = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.auto_allianceCargo_bot_s = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.auto_allianceCargo_top_f = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.auto_allianceCargo_bot_f = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;

        tc.auto_away_balls = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;

        tc.auto_humanMissed = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.auto_humanScored = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;

        tc.tele_shoot_attempt = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;
        tc.tele_allianceCargo_top_s = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.tele_allianceCargo_bot_s = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.tele_allianceCargo_top_f = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.tele_allianceCargo_bot_f = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.tele_away_balls = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;

        tc.endgame_attempt = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;
        tc.endgame_park = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;
        tc.endgame_hang = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.endgame_ringContact = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.endgame_ringFinish = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;
        tc.endgame_climbTime = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;

        tc.final_defense = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset +6;

        tc.final_disabled = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;
        tc.final_disqualified = Integer.parseInt(src.substring(offset, offset + 1), 2);
        offset++;

        tc.final_zone = Integer.parseInt(src.substring(offset, offset + 6), 2);
        offset = offset + 6;

    }
    private boolean issame(TransferCode tc) {
        if (matchNumber != tc.matchNumber)
            return false;
        if (teamNumber != tc.teamNumber)
            return false;
        if (isRed != tc.isRed)
            return false;
        if (isNoShow != tc.isNoShow)
            return false;
        if (auto_cross_line != tc.auto_cross_line)
            return false;
        if (auto_shoot_attempt != tc.auto_shoot_attempt)
            return false;
        if (auto_allianceCargo_bot_s != tc.auto_allianceCargo_bot_s)
            return false;
        if (auto_allianceCargo_top_s != tc.auto_allianceCargo_top_s)
            return false;

        if (auto_away_balls != tc.auto_away_balls)
            return false;
        if (auto_allianceCargo_bot_f != tc.auto_allianceCargo_bot_f)
            return false;
        if (auto_allianceCargo_top_f != tc.auto_allianceCargo_top_f)
            return false;
        if (auto_humanMissed != tc.auto_humanMissed)
            return false;
        if (auto_humanScored != tc.auto_humanScored)
            return false;

        if (tele_shoot_attempt != tc.tele_shoot_attempt)
            return false;
        if (tele_allianceCargo_bot_s != tc.tele_allianceCargo_bot_s)
            return false;
        if (tele_allianceCargo_top_s != tc.tele_allianceCargo_top_s)
            return false;

        if (tele_away_balls != tc.tele_away_balls)
            return false;
        if (tele_allianceCargo_bot_f != tc.tele_allianceCargo_bot_f)
            return false;
        if (tele_allianceCargo_top_f != tc.tele_allianceCargo_top_f)
            return false;



        if (endgame_attempt != tc.endgame_attempt)
            return false;
        if (endgame_park != tc.endgame_park)
            return false;
        if (endgame_hang != tc.endgame_hang)
            return false;
        if (endgame_ringContact != tc.endgame_ringContact)
            return false;
        if (endgame_ringFinish != tc.endgame_ringFinish)
            return false;
        if (endgame_climbTime != tc.endgame_climbTime)
            return false;

        if (final_defense != tc.final_defense)
            return false;
        if (final_disabled != tc.final_disabled)
            return false;
        if (final_disqualified != tc.final_disqualified)
            return false;

        if (final_zone!= tc.final_zone)
            return false;
        return true;
    }
    public String toString() {
        return "TransferCode{" +
                ", matchNumber=" + matchNumber +
                "teamNumber=" + teamNumber +
                ", isRed=" + isRed +
                ", isNoShow=" + isNoShow +
                ", auto_cross_line=" + auto_cross_line +
                ", auto_shoot_attempt=" + auto_shoot_attempt +
                ", auto_allianceCargo_bot_s=" + auto_allianceCargo_bot_s +
                ", auto_allianceCargo_top_s=" + auto_allianceCargo_top_s +
//                ", auto_opponentCargo_bot_s=" + auto_opponentCargo_bot_s +
                ", auto_opponentCargo_top_s=" + auto_away_balls +
                ", auto_allianceCargo_bot_f=" + auto_allianceCargo_bot_f +
                ", auto_allianceCargo_top_f=" + auto_allianceCargo_top_f +
//                ", auto_opponentCargo_bot_f=" + auto_opponentCargo_bot_f +
//                ", auto_opponentCargo_top_f=" + auto_opponentCargo_top_f +
                ", auto_humanMissed=" + auto_humanMissed +
                ", auto_humanScored=" + auto_humanScored +

                ", tele_shoot_attempt=" + tele_shoot_attempt +
                ", tele_allianceCargo_bot_s=" + tele_allianceCargo_bot_s +
                ", tele_allianceCargo_top_s=" + tele_allianceCargo_top_s +
//                ", tele_opponentCargo_bot_s=" + tele_opponentCargo_bot_s +
                ", tele_opponentCargo_bot_s=" + tele_away_balls +
                ", tele_allianceCargo_bot_f=" + tele_allianceCargo_bot_f +
                ", tele_allianceCargo_top_f=" + tele_allianceCargo_top_f +
//                ", tele_opponentCargo_bot_f=" + tele_opponentCargo_bot_f +
//                ", tele_opponentCargo_top_f=" + tele_opponentCargo_top_f +

                ", endgame_attempt=" + endgame_attempt +
                ", endgame_park=" + endgame_park +
                ", endgame_hang=" + endgame_hang +
                ", endgame_ringContact=" + endgame_ringContact +
                ", endgame_ringFinish=" + endgame_ringFinish +
                ", endgame_climbTime=" + endgame_climbTime +

                ", final_defense=" + final_defense +
                ", final_disabled=" + final_disabled +
                ", final_disqualified=" + final_disqualified +
//                ", final_foulsCreated=" + final_foulsCreated +
//                ", final_yellowCardCreated=" + final_yellowCardCreated +
//                ", final_redCardCreated=" + final_redCardCreated +
//                ", final_numTechFouls=" + final_numTechFouls +
//                ", final_numRegFouls=" + final_numRegFouls +
                ", final_zone=" + final_zone +
                '}';
    }
    public String toComma() {
        return matchNumber +
                ", " + teamNumber +
                ", " + isRed +
                ", " + isNoShow +

                ", " + auto_cross_line +
                ", " + auto_shoot_attempt +

                ", " + auto_allianceCargo_bot_s +
                ", " + auto_allianceCargo_top_s +
                ", " + auto_allianceCargo_bot_f +
                ", " + auto_allianceCargo_top_f +
                ", " + auto_away_balls +

                ", " + auto_humanMissed +
                ", " + auto_humanScored +

                ", " + tele_shoot_attempt +

                ", " + tele_allianceCargo_bot_s +
                ", " + tele_allianceCargo_top_s +
                ", " + tele_allianceCargo_bot_f +
                ", " + tele_allianceCargo_top_f +
                ", " + tele_away_balls +

                ", " + endgame_attempt +
                ", " + endgame_park +
                ", " + endgame_hang+
                ", " + endgame_ringContact +
                ", " + endgame_ringFinish +
                ", " + endgame_climbTime +

                ", " + final_defense +
                ", " + final_disabled+
                ", " + final_disqualified +

                ", " + final_zone

        ;
    }
}

