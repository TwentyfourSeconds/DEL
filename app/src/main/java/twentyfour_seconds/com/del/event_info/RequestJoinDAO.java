package twentyfour_seconds.com.del.event_info;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.util.Common;

public class RequestJoinDAO extends AsyncTask<String, String, String> {

    private String urlStr;
    private String write;

    public RequestJoinDAO(String urlStr, String write) {
        this.urlStr = urlStr;
        this.write = write;
    }

    @Override
    protected String doInBackground(String... string) {
        String result = Common.URLConnection(urlStr, write);
        return result;
    }
}

