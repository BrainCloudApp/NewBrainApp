package com.example.newbrainapp;

public class TreatmentRecordBean {
    private String treatmentProject;
    private String treatmentDate;
    private String treatmentResult;

    public TreatmentRecordBean(String treatmentProject, String treatmentDate, String treatmentResult){
        setTreatmentProject(treatmentProject);
        setTreatmentDate(treatmentDate);
        setTreatmentResult(treatmentResult);
    }

    public String getTreatmentProject() {
        return treatmentProject;
    }

    public void setTreatmentProject(String treatmentProject) {
        this.treatmentProject = treatmentProject;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentResult() {
        return treatmentResult;
    }

    public void setTreatmentResult(String treatmentResult) {
        this.treatmentResult = treatmentResult;
    }
}
