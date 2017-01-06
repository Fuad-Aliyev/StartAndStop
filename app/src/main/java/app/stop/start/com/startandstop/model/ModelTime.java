package app.stop.start.com.startandstop.model;

/**
 * Created by Senani Recebov on 12/28/2016.
 */

public class ModelTime {

    private String id;
    private String name;
    private String time;
    private String startDate;
    private String stopDate;
    private String note;

    public ModelTime() {
    }

    public ModelTime(String name, String time, String startDate, String stopDate, String note) {
        this.name = name;
        this.time = time;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
