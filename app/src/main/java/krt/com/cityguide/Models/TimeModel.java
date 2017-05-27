package krt.com.cityguide.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import krt.com.cityguide.Utils.Constants;

/**
 * Created by bryden on 1/9/17.
 */

public class TimeModel {
    private String workDay;
    private String startTime;
    private String endTime;

    public TimeModel(JSONObject jsonObject) {
        try {
            this.workDay = Constants.weekDays[jsonObject.getJSONObject("close").getInt("day")];// + "";
            this.startTime = getAMPMString(jsonObject.getJSONObject("open").getString("time").substring(0, 2) + ":" + jsonObject.getJSONObject("open").getString("time").substring(2, 4));
            this.endTime = getAMPMString(jsonObject.getJSONObject("close").getString("time").substring(0, 2) + ":" + jsonObject.getJSONObject("close").getString("time").substring(2, 4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TimeModel() {
    }

    public TimeModel(String workDay, String startTime, String endTime) {
        this.workDay = workDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String getAMPMString(String str)
    {

        System.out.println(str);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date dateObj = sdf.parse(str);
//            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("hh:mm a").format(dateObj));
            return new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
