package one.Model;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by root on 2/7/18.
 */

public class BottlesD_Data {

    WeekDays mWeekDays;              //
    private String db_Mon;
    private String db_Tue;
    private String db_imgUrl;
    ImageView mImageView;
    public BottlesD_Data(String Mon_In, String Tue_In, String imgUrl) {
        this.db_Mon = Mon_In;
        this.db_Tue = Tue_In;
        this.db_imgUrl = imgUrl;
    }

    public String getDb_Mon() {
        return db_Mon;
    }
    public String getDb_Tue() {
        return "Tel: " + db_Tue;
    }
    public String getDb_imgUrl() {return db_imgUrl; }

    public void setDb_Mon(String db_Mon) { this.db_Mon = WeekDays.MON.toString(); }
    public void setDb_Tue(String db_Mon) { this.db_Mon = WeekDays.TUE.toString(); }
    public void setDb_imgUrl(final String db_imgUrl, ImageView imageViewIn) {
        this.mImageView = imageViewIn;
        Picasso.get().load(db_imgUrl).into(this.mImageView);
        this.db_imgUrl = db_imgUrl;
    }

    @Override
    public String toString() {
        return "ForecastData{" +
                "mWeekDays=" + mWeekDays +
                ", db_Mon='" + db_Mon + '\'' +
                ", db_Tue='" + db_Tue + '\'' +
                ", db_imgUrl='" + db_imgUrl + '\'' +
                '}';
    }
}

