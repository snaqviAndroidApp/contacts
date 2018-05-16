package one.Model;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by root on 2/7/18.
 */

public class BottlesD_Data {
    private String db_Mon;
    private String db_Tue;
    private String db_imgUrl;
    private ImageView mImageView;
    private double[] pojoLat_Long;


//    public BottlesD_Data(String Mon_In, String Tue_In, String imgUrl, double[][] setLatLong) {
    public BottlesD_Data(String Mon_In, String Tue_In, String imgUrl, double[] setLatLong) {
        this.db_Mon = Mon_In;
        this.db_Tue = Tue_In;
        this.db_imgUrl = imgUrl;
        this.pojoLat_Long = setLatLong;
    }

    public String getDb_Mon() {
        return db_Mon;
    }
    public String getDb_Tue() {
        return "Tel: " + db_Tue;
    }
    public String getDb_imgUrl() {return db_imgUrl; }
    public double[] getPojoLat_Long() { return pojoLat_Long; }

    public void setDb_Mon(String db_Mon) { this.db_Mon = "Dummy_Mon"; }
    public void setDb_Tue(String db_Mon) { this.db_Mon = "Dummy_Tue"; }
    public void setPojoLat_Long(final double[] pojoLat_Long) { this.pojoLat_Long = pojoLat_Long; }
    public void setDb_imgUrl(final String db_imgUrl, ImageView imageViewIn) {
        this.mImageView = imageViewIn;
        Picasso.get().load(db_imgUrl).into(this.mImageView);
        this.db_imgUrl = db_imgUrl;
    }

    @Override
    public String toString() {
        return "ForecastData{" +
                ", db_Mon='" + db_Mon + '\'' +
                ", db_Tue='" + db_Tue + '\'' +
                ", db_imgUrl='" + db_imgUrl + '\'' +
                '}';
    }
}

