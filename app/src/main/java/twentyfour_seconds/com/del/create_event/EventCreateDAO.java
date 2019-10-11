package twentyfour_seconds.com.del.create_event;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

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

    public EventCreateDAO(String urlStr, String write, CountDownLatch latch) {
        this.urlStr = urlStr;
        this.write = write;
        this.latch = latch;
    }

    public EventCreateDAO(String urlStr, String write) {
        this.urlStr = urlStr;
        this.write = write;
    }

    @Override
    protected String doInBackground(String... string) {

        String result = Common.URLConnection(urlStr, write);
        latch.countDown();
        return result;
    }
}

