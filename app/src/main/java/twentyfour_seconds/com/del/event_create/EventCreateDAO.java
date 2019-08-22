package twentyfour_seconds.com.del.event_create;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.util.Common;

public class EventCreateDAO extends AsyncTask<String, String, String> {

    private String urlStr;
    private String write = "";
    private CountDownLatch latch;
//    private String eventName = null;
//    private String founder = null;
//    private String area = null;
//    private String place = null;
//    private String eventDay = null;
//    private String deadline = null;
//    private int current_person = 0;
//    private int wanted_person = 0;
//    private String comment = null;
//    private int delete_flg = 0;

    public EventCreateDAO(String urlStr, CountDownLatch latch) {
        this.urlStr = urlStr;
        this.latch = latch;
    }

    public EventCreateDAO(String urlStr, String write) {
        this.urlStr = urlStr;
        this.write = write;
    }

    @Override
    protected String doInBackground(String... string) {

        String result = Common.URLConnection(urlStr, write);

        return result;
    }
}

