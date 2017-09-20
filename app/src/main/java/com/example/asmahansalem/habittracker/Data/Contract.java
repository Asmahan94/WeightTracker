package com.example.asmahansalem.habittracker.Data;

import android.provider.BaseColumns;

/**
 * Created by Asmahan Salem on 9/19/2017.
 */

/**
 * Here is a Java code source that helped me in implement this how to write this Contract
 * <p>
 * https://github.com/dkatic/HabitTracker/blob/master/app/src/main/java/com/example/david/habittracker/data/HabitContract.java
 */
public class Contract {

    public Contract() {
    }

    public static abstract class HabitTrackerEntry implements BaseColumns {

        public final static String TABLE_NAME = "weight";

        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_LOSS = "loss";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WATER = "water";

    }
}
