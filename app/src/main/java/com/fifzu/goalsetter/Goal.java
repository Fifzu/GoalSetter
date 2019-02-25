package com.fifzu.goalsetter;

import android.os.Parcel;
import android.os.Parcelable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Goal implements Parcelable {

    private Integer goalType;
    private LocalDateTime validUntil;

    private String name;
    private Boolean fixed;
    private Integer value;
    private Integer goalClass;
    private Integer goalIcon;

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

    public Integer getGoalIcon() {
        return goalIcon;
    }

    protected Goal(Parcel in) {
        goalType = in.readByte() == 0x00 ? null : in.readInt();
        validUntil = (LocalDateTime) in.readValue(LocalDateTime.class.getClassLoader());
        name = in.readString();
        byte fixedVal = in.readByte();
        fixed = fixedVal == 0x02 ? null : fixedVal != 0x00;
        value = in.readByte() == 0x00 ? null : in.readInt();
        goalClass = in.readByte() == 0x00 ? null : in.readInt();
        goalIcon = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (goalType == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(goalType);
        }
        dest.writeValue(validUntil);
        dest.writeString(name);
        if (fixed == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (fixed ? 0x01 : 0x00));
        }
        if (value == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(value);
        }
        if (goalClass == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(goalClass);
        }
        if (goalIcon == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(goalIcon);
        }
    }

    public Goal() {
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Goal> CREATOR = new Parcelable.Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };
}