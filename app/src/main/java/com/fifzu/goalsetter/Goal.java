package com.fifzu.goalsetter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Goal {

    private Integer goalType;
    private LocalDateTime validUntil;

    private String name;
    private Boolean fixed;
    private Integer value;
    private Integer goalClass;
    private Integer goalIcon;
    private Integer uniqueID;

    private static Integer[] goalDatabase = {R.drawable.ic_attach_money,
            R.drawable.ic_school,R.drawable.ic_group,
            R.drawable.ic_favorite,R.drawable.ic_wc};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer dur) {

        goalType = dur;
        validUntil = LocalDateTime.now();

        switch (goalType) {

            case 0:
                validUntil = validUntil.plusDays(10);
                value = 10;
                break;

            case 1:
                validUntil = validUntil.plusDays(40);
                value = 40;
                break;

            case 2:
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

    public String getValidUntil() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = validUntil.format(formatter);
        return str;
    }
    public LocalDateTime getValidUntilDate() {
        return validUntil;
    }

    public Integer getGoalType() {
        return goalType;
    }

    public Integer getGoalClass() {
        return goalClass;
    }

    public void setGoalClass(Integer goalClass) {
        this.goalClass = goalClass;
        goalIcon = goalDatabase[goalClass];
    }

    public Integer getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(Integer uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Integer getGoalIcon() {
        return goalIcon;
    }
}