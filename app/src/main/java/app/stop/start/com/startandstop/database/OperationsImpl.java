package app.stop.start.com.startandstop.database;

import java.util.ArrayList;

import app.stop.start.com.startandstop.model.ModelTime;

public interface OperationsImpl {

    void insertTotalTime(ModelTime model);
    void insertTime(ModelTime model);
    ArrayList<ModelTime> getAllTotalTime();
    ArrayList<ModelTime> getAllTime(String name);
    ArrayList<ModelTime> getDate();
    void deleteTotalTime(ModelTime model);
    ArrayList<ModelTime> categoryByDay();
    ArrayList<ModelTime> categoryByMonth();
    boolean hasName(ModelTime model);
    String getNote(ModelTime model);
}
