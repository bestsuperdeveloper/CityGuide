package krt.com.cityguide.Models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/25/17.
 */

public class ChurchTimeModel {
    private String duration;
    private String starttime;
    private String churchName;
    private int sectionIndex;
    private int listIndex;

    public ChurchTimeModel(String duration, String starttime, String churchName) {
        this.duration = duration;
        this.starttime = starttime;
        this.churchName = churchName;
    }

    public int getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(int sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public int getListIndex() {
        return listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public int isOpned(Date date)
    {
        int dur1 = Utils.getSecondsFromAMPM(this.starttime);
        int dur2 = Utils.getSecondsFromHours(this.duration);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(date);
        int dur = Utils.getSecondsFromHours(dateString);
        Log.d("hour_duration", this.starttime + dur1 + " " + this.starttime + " " + dur2 + " " + dur + " " + dateString);
        if (dur >= dur1 && dur <= dur1 + dur2) return 0;
        if (dur < dur1) return 1;
        return 2;
    }
}
