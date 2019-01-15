package com.example.newbrainapp;

public class RecoverTargetBean {
    private String recoverTime;
    private String recoverTarget;

    public RecoverTargetBean(String recoverTime, String recoverTarget){
        setRecoverTime(recoverTime);
        setRecoverTarget(recoverTarget);
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getRecoverTarget() {
        return recoverTarget;
    }

    public void setRecoverTarget(String recoverTarget) {
        this.recoverTarget = recoverTarget;
    }
}
