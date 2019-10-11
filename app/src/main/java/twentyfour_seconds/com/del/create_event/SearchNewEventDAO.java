package twentyfour_seconds.com.del.create_event;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.util.Common;

public class SearchNewEventDAO extends AsyncTask<String, String, String> {

    private String urlStr;
    private String write = "";
    private CountDownLatch latch2;
    private JSONObject json;


    public SearchNewEventDAO(String urlStr, String write, CountDownLatch latch2) {
        this.urlStr = urlStr;
        this.write = write;
        this.latch2 = latch2;
    }

    public SearchNewEventDAO(String urlStr, String write) {
        this.urlStr = urlStr;
        this.write = write;
    }

    @Override
    protected String doInBackground(String... string) {

        String result = Common.URLConnection(urlStr, write);

        try {
            json = new JSONObject(result);
            Log.d("json","json" + json);
            Common.newEventId = json.getInt("event_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        latch2.countDown();
        Log.d("SearchNewEventDAO", "latchCountDown2");
        return result;
    }
}

