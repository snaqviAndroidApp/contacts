package solstice;

import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

import mod.redeploy.postCh.R;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("mod.redeploy.postCh", appContext.getPackageName());
        assertEquals("Permission Rendered", appContext.getString(R.string.permission_msg));
        assertEquals("Permission Denied", appContext.getString(R.string.deniel_msg));
//        assertEquals("", appContext.databaseList());

    }
}

//    public static final String TEST_STRING = "This is a string";
//    public static final long TEST_LONG = 12345678L;
//    private LogHistory mLogHistory;
//
//    @Before
//    public void createLogHistory() {
//        mLogHistory = new LogHistory();
//    }
//
//    @Test
//    public void logHistory_ParcelableWriteRead() {
//        // Set up the Parcelable object to send and receive.
//        mLogHistory.addEntry(TEST_STRING, TEST_LONG);
//
//        // Write the data.
//        Parcel parcel = Parcel.obtain();
//        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());
//
//        // After you're done with writing, you need to reset the parcel for reading.
//        parcel.setDataPosition(0);
//
//        // Read the data.
//        LogHistory createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
//        List<Pair<String, Long>> createdFromParcelData = createdFromParcel.getData();
//
//        // Verify that the received data is correct.
//        assertThat(createdFromParcelData.size(), is(1));
//        assertThat(createdFromParcelData.get(0).first, is(TEST_STRING));
//        assertThat(createdFromParcelData.get(0).second, is(TEST_LONG));





