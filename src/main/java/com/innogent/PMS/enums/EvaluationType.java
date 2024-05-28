package com.innogent.PMS.enums;

public enum EvaluationType {
    SELF_EVALUATION("Self Evaluation"),
    LINE_MANAGER("Line Manager"),
    FEEDBACK_360("360 Degree Feedback");

    private String displayName;

    EvaluationType(String displayName){
        this.displayName=displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
