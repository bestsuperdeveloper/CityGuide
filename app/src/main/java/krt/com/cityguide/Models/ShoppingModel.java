package krt.com.cityguide.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bryden on 1/9/17.
 */

public class ShoppingModel implements Serializable{
    private List<TimeModel> workTime;
    private String phoneNumber;
    private String currentStatus;
    private String imageURL;
    private String currentAddress;

    private String name;
    private String distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public ShoppingModel(List<TimeModel> workTime, String phoneNumber, String currentStatus, String imageURL, String currentAddress) {
        this.workTime = workTime;
        this.phoneNumber = phoneNumber;
        this.currentStatus = currentStatus;
        this.imageURL = imageURL;
        this.currentAddress = currentAddress;
    }

    public List<TimeModel> getWorkTime() {
        return workTime;
    }

    public void setWorkTime(List<TimeModel> workTime) {
        this.workTime = workTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
}
