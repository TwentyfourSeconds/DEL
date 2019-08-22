package twentyfour_seconds.com.del.event_management;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.util.Common;

public class UidSearchEventDAO extends AsyncTask<String, String, String> {

    //numberは、DBを読み込むときの開始位置
    private String write;
    private CountDownLatch latch;
    private EventInfoDTOList eventInfoDTOList;
    private String urlStr;
    private JSONObject json;

    public UidSearchEventDAO(String urlStr, String write, EventInfoDTOList eventInfoDTOList, CountDownLatch latch) {
        this.urlStr = urlStr;
        this.write = write;
        this.eventInfoDTOList = eventInfoDTOList;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(String... string) {

        String result = Common.URLConnection(urlStr, write);

        String[] databases = result.split(";");
        try {

            json = new JSONObject(databases[0]);
            for(int i = 0; i < databases.length; i++) {
                json = new JSONObject(databases[i]);
                EventInfoDTO eventInfoDTO = new EventInfoDTO();
                eventInfoDTO.setEventId(json.getString("event_id"));
                eventInfoDTO.setEventerUid(json.getString("eventer_uid"));
                eventInfoDTO.setEventName(json.getString("event_name"));
                eventInfoDTO.setLargeArea(json.getString("large_area"));
                eventInfoDTO.setSmallArea(json.getString("small_area"));
                eventInfoDTO.setEventDay(json.getString("event_day_on"));
                eventInfoDTO.setClosedDay(json.getString("closed_on"));
                eventInfoDTO.setMember("" + json.getInt("max_persons"));
                eventInfoDTO.setComment(json.getString("comment"));
                eventInfoDTO.setEventTag(json.getString("event_tag"));
                eventInfoDTOList.addDtoArrayList(eventInfoDTO);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        latch.countDown();
        return result;
    }
}

