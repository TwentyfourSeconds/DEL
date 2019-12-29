package twentyfour_seconds.com.del.event_management;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.util.Common;


class EventStatusUpdateDAO extends AsyncTask<String, String, String> {

    //numberは、DBを読み込むときの開始位置
    private String write;
    private CountDownLatch latch;
    private String urlStr;


    EventStatusUpdateDAO(String urlStr, String write, CountDownLatch latch) {
        this.urlStr = urlStr;
        this.write = write;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(String... string) {
        String result = Common.URLConnection(urlStr, write);
        latch.countDown();
        return result;
    }
}

