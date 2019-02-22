package com.fifzu.goalsetter;

import java.time.LocalDateTime;

public class Goal {

    private GoalType goalType;
    private LocalDateTime validUntil;
    private LocalDateTime lastUpdated;
    private String name;
    private Boolean fixed;
    private Integer value;

    private enum GoalType {
        SHORT, MEDIUM, LONG
    }

/*
    public Goal() {
        validUntil = LocalDateTime.now();
        validUntil = validUntil.plusDays(7);
    }
*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Long dur) {
        int dirInt = dur.intValue();
        goalType = GoalType.values()[dirInt];
        validUntil = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();

        switch (goalType) {

            case SHORT:
                validUntil = validUntil.plusDays(10);
                value = 10;
                break;

            case MEDIUM:
                validUntil = validUntil.plusDays(40);
                value = 40;
                break;

            case LONG:
                validUntil = validUntil.plusDays(200);
                value = 200;
                break;
        }
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Integer getValue() {
        return value;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    public GoalType getGoalType() {
        return goalType;
    }
}
