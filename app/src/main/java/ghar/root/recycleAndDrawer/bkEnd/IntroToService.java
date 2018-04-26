package ghar.root.recycleAndDrawer.bkEnd;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by root on 2/21/18.
 */

//public class IntroToService extends IntentService {
public class IntroToService extends Service {
//    public IntroToService(String name) {
//        super(name);
//    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
