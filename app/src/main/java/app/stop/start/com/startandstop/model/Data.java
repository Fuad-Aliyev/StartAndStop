package app.stop.start.com.startandstop.model;

import android.widget.Button;
import android.widget.Switch;

import app.stop.start.com.startandstop.R;

/**
 * Created by Senani Recebov on 12/14/2016.
 */

public class Data {
    private Button btnCar, btnBus, btnSubway, btnWork, btnStudy, btnTeach, btnRead, btnRelax;
    private Button btnSleep, btnSport, btnRun, btnWalk, btnChat, btnCook, btnMeet, btnMakeup;
    private Button btnEat, btnShop, btnAirplane, btnTravel;

    private Button[] buttons = {btnCar, btnBus, btnSubway, btnWork, btnStudy, btnTeach, btnRead,
            btnRelax, btnSleep, btnSport, btnRun, btnWalk, btnChat, btnCook, btnMeet, btnMakeup,
            btnEat, btnShop, btnAirplane, btnTravel};

    private String[] colors = {"#99adb7", "#90A4AE", "#78909C", "#607D8B", "#546E7A", "#455A64",
            "#395563", "#2f4450", "#47545b", "#3b4f59", "#4b6471", "#485860", "#49585c",
            "#3a4e58", "#3f606c", "#354c58", "#395563", "#394f59", "#39545e", "#2e3e46"};

    private String[] names = {"car", "bus", "subway", "work", "study", "teach",
            "read", "relax", "sleep", "sport", "run", "walk", "chat", "cook",
            "meet", "makeup", "eat", "shop", "airplane", "travel"};

    private int ids[] = {R.id.btnCar, R.id.btnBus, R.id.btnSubway, R.id.btnWork, R.id.btnStudy,
            R.id.btnTeach, R.id.btnRead, R.id.btnRelax, R.id.btnSleep, R.id.btnSport, R.id.btnRun,
            R.id.btnWalk, R.id.btnChat, R.id.btnCook, R.id.btnMeet, R.id.btnMakeup, R.id.btnEat,
            R.id.btnShop, R.id.btnAirplane, R.id.btnTravel};

    public Button[] getButtons() {
        return buttons;
    }

    public String[] getColors() {
        return colors;
    }

    public String[] getNames() {
        return names;
    }

    public int[] getIds(){
        return ids;
    }
}
