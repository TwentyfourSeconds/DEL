package twentyfour_seconds.com.del.event_info;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.util.Common;

public class EventInfoDAO extends AsyncTask<String, String, String> {

    private String urlStr;
    private String write;
    private EventInfoDTO eventInfoDTO;
    private CountDownLatch latch;
    private JSONObject json;

    public EventInfoDAO(String urlStr, String write, EventInfoDTO eventInfoDTO, CountDownLatch latch) {
        this.urlStr = urlStr;
        this.write = write;
        this.eventInfoDTO = eventInfoDTO;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(String... string) {
        String result = Common.URLConnection(urlStr, write);

        //新DB用
        try {
            json = new JSONObject(result);
            eventInfoDTO.setEventId(json.getString("event_id"));
            eventInfoDTO.setEventerUid(json.getString("eventer_uid"));
            eventInfoDTO.setEventName(json.getString("event_name"));
            eventInfoDTO.setLargeArea(json.getString("large_area"));
            eventInfoDTO.setSmallArea(json.getString("small_area"));
            eventInfoDTO.setEventDay(json.getString("event_day_on"));
            eventInfoDTO.setClosedDay(json.getString("closed_on"));
            eventInfoDTO.setMember(json.getString("max_persons"));
            eventInfoDTO.setComment(json.getString("comment"));
            eventInfoDTO.setEventTag(json.getString("event_tag"));
            eventInfoDTO.setEventStatus(json.getString("event_status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        latch.countDown();
        return result;


//        try {
//            json = new JSONObject(result);
//            eventInfoDTO.setId(json.getString("id"));
//            eventInfoDTO.setImage(json.getString("image"));
//            eventInfoDTO.setTitle(json.getString("event_name"));
//            eventInfoDTO.setName(json.getString("founder"));
//            eventInfoDTO.setArea(json.getString("area"));
//            eventInfoDTO.setLocal(json.getString("place"));
//            eventInfoDTO.setDate(json.getString("event_day"));
//            eventInfoDTO.setDeadline(json.getString("deadline"));
//            eventInfoDTO.setMember(json.getInt("current_person") + "/" + json.getInt("wanted_person"));
//            eventInfoDTO.setComment(json.getString("comment"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        latch.countDown();
//        return result;
    }

}

