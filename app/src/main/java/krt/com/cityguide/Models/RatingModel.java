package krt.com.cityguide.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import krt.com.cityguide.Utils.Constants;

/**
 * Created by bryden on 1/9/17.
 */

public class RatingModel {
    private String userId;
    private String createAt;
    private String objectId;
    private String comment;
    private String score;
    private String name;


    public RatingModel(String userId, String createAt, String objectId, String comment, String score) {
        this.userId = userId;
        this.createAt = createAt;
        this.objectId = objectId;
        this.comment = comment;
        this.score = score;
    }

    public RatingModel(String userId, String createAt, String objectId, String comment, String score, String name) {
        this.userId = userId;
        this.createAt = createAt;
        this.objectId = objectId;
        this.comment = comment;
        this.score = score;
        this.name = name;
    }

    public RatingModel() {
    }

    public String getUserId() {
        return userId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getComment() {
        return comment;
    }

    public String getScore() {
        return score;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public static RatingModel getFromHashMap(HashMap<String, String> hashMap)
    {
        RatingModel ratingModel = new RatingModel();
        ratingModel.setObjectId(hashMap.get("objectId"));
        ratingModel.setScore(hashMap.get("score"));
        ratingModel.setComment(hashMap.get("comment"));
        ratingModel.setCreateAt(hashMap.get("createAt"));
        ratingModel.setUserId(hashMap.get("userId"));
        ratingModel.setName(hashMap.get("name"));
        return ratingModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getHashMap()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("objectId", this.objectId);
        map.put("score", this.score);
        map.put("userId", this.userId);
        map.put("comment", this.comment);

        map.put("name", this.name);
        map.put("createAt", this.createAt);
        return map;

    }

}
