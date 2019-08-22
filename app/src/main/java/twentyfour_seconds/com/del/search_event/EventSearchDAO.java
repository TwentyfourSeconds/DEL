package twentyfour_seconds.com.del.search_event;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.util.Common;

class EventSearchDAO extends AsyncTask<String, String, String> {

    //numberは、DBを読み込むときの開始位置
    private String write;
    private CountDownLatch latch;
    private EventInfoDTOList eventInfoDTOList;
    private String urlStr;
    private JSONObject json;

    EventSearchDAO(String urlStr, String write, EventInfoDTOList eventInfoDTOList, CountDownLatch latch) {
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

//            //新DB用
//            json = new JSONObject(databases[0]);
//            for(int i = 0; i < databases.length; i++) {
//                json = new JSONObject(databases[i]);
//                EventInfoDTO eventInfoDTO = new EventInfoDTO();
//                eventInfoDTO.setEventId(json.getString("event_id"));
//                eventInfoDTO.setEventerUid(json.getString("eventer_uid"));
//                eventInfoDTO.setEventName(json.getString("event_name"));
//                eventInfoDTO.setLargeArea(json.getString("large_area"));
//                eventInfoDTO.setSmallArea(json.getString("small_area"));
//                eventInfoDTO.setEventDay(json.getString("event_day_on"));
//                eventInfoDTO.setClosedDay(json.getString("closed_on"));
//                eventInfoDTO.setMember("" + json.getInt("max_persons"));
//                eventInfoDTO.setComment(json.getString("comment"));
//                eventInfoDTO.setEventTag(json.getString("event_tag"));
//                eventInfoDTOList.addDtoArrayList(eventInfoDTO);
//            }


            json = new JSONObject(databases[0]);
            //      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
            //      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
            Common.currentRecordsetLength = databases.length;

            for(int i = 1; i < databases.length; i++) {
                json = new JSONObject(databases[i]);
                EventInfoDTO eventInfoDTO = new EventInfoDTO();
                eventInfoDTO.setId(json.getString("id"));
                eventInfoDTO.setImage(json.getString("image"));
                eventInfoDTO.setTitle(json.getString("event_name"));
                eventInfoDTO.setName(json.getString("founder"));
                eventInfoDTO.setArea(json.getString("area"));
                eventInfoDTO.setLocal(json.getString("place"));
                eventInfoDTO.setDate(json.getString("event_day"));
                eventInfoDTO.setDeadline(json.getString("deadline"));
                eventInfoDTO.setMember(json.getInt("current_person") + "/" + json.getInt("wanted_person"));
                eventInfoDTO.setComment(json.getString("comment"));
                Log.d("DTO", eventInfoDTO.getTitle());
                eventInfoDTOList.addDtoArrayList(eventInfoDTO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        latch.countDown();
        return result;
    }
}

