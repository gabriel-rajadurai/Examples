package com.example.administrator.test;

/**
 * Created by Administrator on 8/1/2017.
 */

public class TaskData
{
    public String activityName;
    public String location;
    //public String currentStatus;
    public String startTime;
    public String expCount;





    public TaskData(String activityName, String location, String expCount, String startTime)
    {

        this.activityName = activityName;
        this.location = location;
        this.expCount = expCount;
        this.startTime = startTime;

    }

    public TaskData()
    {

    }

    public String getExpCount() {
        return expCount;
    }

    public void setExpCount(String expCount) {
        this.expCount = expCount;
    }



    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

   /* public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }*/

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


}
