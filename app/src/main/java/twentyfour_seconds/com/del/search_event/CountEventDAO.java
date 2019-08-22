package twentyfour_seconds.com.del.search_event;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.util.Common;

class CountEventDAO extends AsyncTask<String, String, String> {

    //numberは、DBを読み込むときの開始位置
    private String urlStr;
    private String write;
    private CountDownLatch latch;
    private JSONObject json;

    CountEventDAO(String urlStr, String write, CountDownLatch latch) {
        this.urlStr = urlStr;
        this.write = write;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(String... string) {

        String result = Common.URLConnection(urlStr, write);

        try {
            json = new JSONObject(result);
            //      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
            //      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
            Common.currentRecordsetLength = json.getInt("COUNT(*)");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        latch.countDown();
        return result;
    }
}

