package com.example.asmahansalem.habittracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmahansalem.habittracker.Data.DbHelper;
import com.example.asmahansalem.habittracker.Data.Contract.HabitTrackerEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Here is a Java code source that helped me in implement this how to write this MainActivity class
 * <p>
 * https://github.com/dkatic/HabitTracker/blob/master/app/src/main/java/com/example/david/habittracker/MainActivity.java
 */
public class MainActivity extends AppCompatActivity {

    private EditText WeightEdit;
    private EditText lossEdit;
    private EditText dateEdit;
    private EditText waterEdit;
    private Button insertButton;
    private Button refreshButton;
    private Button resetButton;
    private TextView weightList;

    private Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeightEdit = (EditText) findViewById(R.id.editTextWeight);
        lossEdit = (EditText) findViewById(R.id.editTextLoss);
        dateEdit = (EditText) findViewById(R.id.editTextDate);
        waterEdit = (EditText) findViewById(R.id.editTextWater);
        insertButton = (Button) findViewById(R.id.buttonSave);
        refreshButton = (Button) findViewById(R.id.buttonRefresh);
        resetButton = (Button) findViewById(R.id.buttonReset);
        weightList = (TextView) findViewById(R.id.weightList);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateSetListener, year, month, day).show();
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewHabit();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshHabits();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeightEdit.setText("");
                lossEdit.setText("");
                dateEdit.setText("");
                waterEdit.setText("");
            }
        });

    }

    private void refreshHabits() {
        List<HabitTracker> weight = new ArrayList<>();
        DbHelper db = new DbHelper(this);
        try {
            weight = db.readAllHabits();
        } catch (Exception e) {
            Log.d("exception", e.toString());
        } finally {
            db.close();
        }

        if (weight != null && weight.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format(Locale.getDefault(), "%4s|%-10s|%-15s|%-11s|%-8s\n", HabitTrackerEntry._ID, HabitTrackerEntry.COLUMN_WEIGHT, HabitTrackerEntry.COLUMN_LOSS, HabitTrackerEntry.COLUMN_DATE, HabitTrackerEntry.COLUMN_WATER));

            for (HabitTracker w : weight) {
                String myFormat = "dd.MM.yyyy.";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                builder.append(String.format(Locale.getDefault(), "%4s|%-10s|%-15s|%-11s|%-8s\n", w.getId(), w.getWeight(), w.getLoss(), sdf.format(w.getDate()), w.getWater()));
            }


            weightList.setText(builder.toString());
        }
    }

    private void insertNewHabit() {
        DbHelper db = new DbHelper(MainActivity.this);
        try {
            HabitTracker habit = new HabitTracker(
                    WeightEdit.getText().toString(),
                    lossEdit.getText().toString(),
                    date.getTimeInMillis(),
                    waterEdit.getText().toString()
            );

            if (db.insertHabit(habit)) {
                Toast.makeText(this, R.string.insert_successful, Toast.LENGTH_SHORT).show();
                WeightEdit.setText("");
                lossEdit.setText("");
                dateEdit.setText("");
                waterEdit.setText("");
            } else {
                Toast.makeText(this, R.string.insert_failed, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.insert_failed, Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            String myFormat = "dd.MM.yyyy.";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            dateEdit.setText(sdf.format(date.getTimeInMillis()));
        }
    };
}
