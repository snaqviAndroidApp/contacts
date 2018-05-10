package everyDayWeather.Model;

/**
 * Created by root on 2/7/18.
 */

public class ForecastData {

    WeekDays mWeekDays;              //

    private String db_Mon;
    private String db_Tue;
    private String db_Wed;
    private String db_Thu;
    private String db_Fri;

    /**
     * @param wDaysIn
     */
    public ForecastData(WeekDays wDaysIn){
        this.mWeekDays = wDaysIn;
    }

    public ForecastData(String Mon_In, String Tue_In,String Wed_In, String Thur_In,String Fri_In) {
        this.db_Mon = Mon_In;
        this.db_Tue = Tue_In;
        this.db_Wed = Wed_In;
        this.db_Thu = Thur_In;
        this.db_Fri = Fri_In;
//        this.mWeekDays = wDaysIn;
    }

//    public String getDb_Mon() {
//        return db_Mon;
//    }
    public String getDb_Mon() {
        return db_Mon;
    }

    public String getDb_Wed() {
        return db_Wed;
    }

    public String getDb_Thu() {
        return db_Thu;
    }

    public String getDb_Fri() {
        return db_Fri;
    }


    public String getDb_Tue() {
        return db_Tue;
    }

//    public void setDb_Mon(final String db_Mon) { this.db_Mon = db_Mon }
//    public void setDb_Tue(final String db_Tue) {
//        this.db_Tue = db_Tue;
//    }
//    public void setDb_Wed(final String db_Wed) {  this.db_Wed = db_Wed; }
//    public void setDb_Thu(final String db_Thu) {  this.db_Thu = db_Thu; }
//    public void setDb_Fri(final String db_Fri) {  this.db_Fri = db_Fri; }

    public void setDb_Mon(String db_Mon) { this.db_Mon = WeekDays.MON.toString(); }
    public void setDb_Tue(String db_Mon) { this.db_Mon = WeekDays.TUE.toString(); }
    public void setDb_Wed(String db_Mon) { this.db_Mon = WeekDays.WED.toString(); }
    public void setDb_Thu(String db_Mon) { this.db_Mon = WeekDays.THU.toString(); }
    public void setDb_Fri(String db_Mon) { this.db_Mon = WeekDays.FRI.toString(); }

    @Override
    public String toString() {
        return "ForecastData{" +
                "mWeekDays=" + mWeekDays +
                ", db_Mon='" + db_Mon + '\'' +
                ", db_Tue='" + db_Tue + '\'' +
                ", db_Wed='" + db_Wed + '\'' +
                ", db_Thu='" + db_Thu + '\'' +
                ", db_Fri='" + db_Fri + '\'' +
                '}';
    }
}

