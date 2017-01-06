package app.stop.start.com.startandstop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.stop.start.com.startandstop.model.ModelTime;

public class DatabaseOperations implements OperationsImpl {

    private final DatabaseHelper helper;
    private static String time = "";
    private static String name = "";
    private static String stopDate = "";

    public DatabaseOperations(Context context) {
        helper = new DatabaseHelper(context);
    }

    @Override
    public void insertTotalTime(ModelTime model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.COL_NAME, model.getName());
        values.put(helper.COL_TIME, model.getTime());
        values.put(helper.COL_START_DATE, model.getStartDate());
        values.put(helper.COL_STOP_DATE, model.getStopDate());
        db.insert(helper.TABLE_TOTAL_TIME, null, values);
        db.close();
    }

    @Override
    public void insertTime(ModelTime model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.COL_NAME, model.getName());
        values.put(helper.COL_TIME, model.getTime());
        values.put(helper.COL_START_DATE, model.getStartDate());
        values.put(helper.COL_STOP_DATE, model.getStopDate());
        values.put(helper.COL_NOTE, model.getNote());
        db.insert(helper.TABLE_TIME, null, values);
        db.close();
    }

    @Override
    public boolean hasName(ModelTime model) {
        boolean flag = false;
        String name = "";
        List<String> list = new ArrayList<>();
        String query = "SELECT " + helper.COL_NAME + " FROM " + helper.TABLE_TOTAL_TIME;

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);
                list.add(name);
            } while (cursor.moveToNext());
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(model.getName())) {
                flag = true;
            }
        }

        return flag;
    }

    @Override
    public String getNote(ModelTime model) {
        String note = "";
        String query = "SELECT note FROM " + helper.TABLE_TIME + " WHERE " + helper.COL_ID + "= '" +
                model.getId() + "'";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                note = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return note;
    }

    @Override
    public ArrayList<ModelTime> getAllTotalTime() {
        ArrayList<ModelTime> model = new ArrayList<>();
        String query = "SELECT * FROM " + helper.TABLE_TOTAL_TIME + " ORDER BY " + helper.COL_ID + " DESC";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ModelTime time = new ModelTime();
                time.setId(cursor.getString(0));
                time.setName(cursor.getString(1));
                time.setTime(cursor.getString(2));
                time.setStartDate(cursor.getString(3));
                time.setStopDate(cursor.getString(4));
                model.add(time);
            } while (cursor.moveToNext());
        }

        return model;
    }

    @Override
    public ArrayList<ModelTime> getAllTime(String name) {
        ArrayList<ModelTime> model = new ArrayList<>();
        String query = "SELECT * FROM " + helper.TABLE_TIME + " WHERE " + helper.COL_NAME + "='" + name + "'"
                + " ORDER BY " + helper.COL_ID + " DESC";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ModelTime time = new ModelTime();
                time.setId(cursor.getString(0));
                time.setName(cursor.getString(1));
                time.setTime(cursor.getString(2));
                time.setStartDate(cursor.getString(3));
                time.setStopDate(cursor.getString(4));
                time.setNote(cursor.getString(5));
                model.add(time);
            } while (cursor.moveToNext());
        }

        return model;
    }

    @Override
    public ArrayList<ModelTime> getDate() {
        return null;
    }

    @Override
    public void deleteTotalTime(ModelTime model) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(helper.TABLE_TOTAL_TIME, helper.COL_ID + "=?", new String[]{model.getId()});
        db.delete(helper.TABLE_TIME, helper.COL_NAME + "=?", new String[]{model.getName()});
    }

    public void setUpdateTimeAndDate(String time, String name, String stopDate) {
        this.time = time;
        this.name = name;
        this.stopDate = stopDate;
    }

    public void getUpdateTimeAndDate() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] parse = time.split(":");
        long oldHour = Integer.parseInt(parse[0]);
        long oldMinute = Integer.parseInt(parse[1]);
        long hour = oldHour * 3600;
        long minute = oldMinute * 60;
        long sum = hour + minute;

        String time = "";
        String query = "SELECT time FROM " + helper.TABLE_TOTAL_TIME + " WHERE " + helper.COL_NAME + "= '" +
                name + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                time = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        System.out.println("Total: " + time);
        String[] parseTime = time.split(":");
        long oldTotalHour = Integer.parseInt(parseTime[0]);
        long oldTotalMinute = Integer.parseInt(parseTime[1]);
        long totalHour = oldTotalHour * 3600;
        long totalMinute = oldTotalMinute * 60;
        long totalSum = totalHour + totalMinute;

        long result = sum + totalSum;
        long lastHour = result / 3600;
        long lastMinute = result / 60 % 60;
        String update = lastHour + ":" + lastMinute;
        ContentValues cv = new ContentValues();
        cv.put(helper.COL_TIME, update);
        cv.put(helper.COL_STOP_DATE, stopDate);
        db.update(helper.TABLE_TOTAL_TIME, cv, helper.COL_NAME + "='" + name + "'", null);
    }

    @Override
    public ArrayList<ModelTime> categoryByDay() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String startDate = dateFormat.format(date);
        ArrayList<ModelTime> model = new ArrayList<>();
        String query = "SELECT * FROM " + helper.TABLE_TIME + " WHERE " + helper.COL_START_DATE + "='" + startDate + "'"
                + " ORDER BY " + helper.COL_ID + " DESC";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ModelTime time = new ModelTime();
                time.setId(cursor.getString(0));
                time.setName(cursor.getString(1));
                time.setTime(cursor.getString(2));
                time.setStartDate(cursor.getString(3));
                time.setStopDate(cursor.getString(4));
                time.setNote(cursor.getString(5));
                model.add(time);
            } while (cursor.moveToNext());
        }

        return model;
    }

    @Override
    public ArrayList<ModelTime> categoryByMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String firstDay = df.format(c.getTime());
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = df.format(c.getTime());
        System.out.println("FirstDay: " + firstDay);
        System.out.println("LastDay: " + lastDay);
        ArrayList<ModelTime> model = new ArrayList<>();
        String query = "SELECT * FROM " + helper.TABLE_TIME + " WHERE " + helper.COL_START_DATE + " >= '"
                + firstDay + "' AND " + helper.COL_STOP_DATE  + " <= '" + lastDay +"'";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ModelTime time = new ModelTime();
                time.setId(cursor.getString(0));
                time.setName(cursor.getString(1));
                time.setTime(cursor.getString(2));
                time.setStartDate(cursor.getString(3));
                time.setStopDate(cursor.getString(4));
                time.setNote(cursor.getString(5));
                model.add(time);
            } while (cursor.moveToNext());
        }

        return model;
    }

}
