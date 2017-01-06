package app.stop.start.com.startandstop.controller;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ClickState {

    File file;
    private static final String FILENAME = Environment.getExternalStorageDirectory().toString();

    public ClickState() {
        file = new File(FILENAME + "/startandstop.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
                file = new File(FILENAME + "/startandstop.txt");
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.append("name" + "::" + "0");
                bw.close();
            }
        } catch (IOException e) {
        }
    }

    public boolean isCreated() {
        boolean flag = false;
        file = new File(FILENAME + "/startandstop.txt");

        if (file.exists()) {
            flag = true;
        }

        return flag;
    }

    public void setState(String name, String value) {

        BufferedWriter bw;
        try {
            file = new File(FILENAME + "/startandstop.txt");
            bw = new BufferedWriter(new FileWriter(file));
            bw.append(name + "::" + value);
            bw.close();
        } catch (IOException e) {
        }
    }

    public String getState() {
        BufferedReader br;
        String flag = "";
        try {
            file = new File(FILENAME + "/startandstop.txt");
            br = new BufferedReader(new FileReader(file));

            String line = "";
            while ((line = br.readLine()) != null) {
                flag += line;
            }
        } catch (IOException e) {
        }
        return flag;
    }

}
