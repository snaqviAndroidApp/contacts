package ghar.root.weather_Test.bkEnd;

/**
 * Created by root on 2/7/18.
 */

public class ForecastData {

    private String db_Mon;
    private String db_Tue;

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

    private String db_Wed;
    private String db_Thu;
    private String db_Fri;

    public ForecastData(String Mon_In, String Tue_In,String Wed_In, String Thur_In,String Fri_In ) {
        this.db_Mon = Mon_In;
        this.db_Tue = Tue_In;
        this.db_Wed = Wed_In;
        this.db_Thu = Thur_In;
        this.db_Fri = Fri_In;
    }

    public String getDb_Tue() {
        return db_Tue;
    }

    public void setDb_Mon(final String db_Mon) {
        this.db_Mon = db_Mon;
    }
    public void setDb_Tue(final String db_Tue) {
        this.db_Tue = db_Tue;
    }
    public void setDb_Wed(final String db_Wed) {  this.db_Wed = db_Wed; }
    public void setDb_Thu(final String db_Thu) {  this.db_Thu = db_Thu; }
    public void setDb_Fri(final String db_Fri) {  this.db_Fri = db_Fri; }

}

