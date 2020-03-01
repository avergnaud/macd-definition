package com.poc.macddefinition.kraken;

public enum Intervals {

    ONE_MINUTE(1),
    FIVE_MINUTES(5),
    FIFTEEN_MINUTES(15),
    THIRTY_MINUTES(30),
    ONE_HOUR(60),
    FOUR_HOURS(240),
    ONE_DAY(1440),
    ONE_WEEK(10080),
    FIFTEEN_DAYS(21600);

    private int minutes;

    Intervals(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return this.minutes;
    }
}
