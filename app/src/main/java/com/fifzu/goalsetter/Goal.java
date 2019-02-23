package com.fifzu.goalsetter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Goal {

    private Integer goalType;
    private LocalDateTime validUntil;
    private LocalDateTime lastUpdated;
    private String name;
    private Boolean fixed;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer dur) {

        goalType = dur;
        validUntil = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");
        String str = validUntil.format(formatter);
        return str;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    public Integer getGoalType() {
        return goalType;
    }
}
