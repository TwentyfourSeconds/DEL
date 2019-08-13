package twentyfour_seconds.com.del.search_event;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    EventSearchDAO(CountDownLatch latch) {
        this.latch = latch;
    }

//    @Override
//    protected void onPreExecute() {
//        //ダイアログを表示させるなどのUIの準備
//        myProgressDialog = new ProgressDialog(activity);
//        myProgressDialog.setMessage("メッセージ");
//        myProgressDialog.show();
//    }

    @Override
    protected String doInBackground(String... string) {

        String result = Common.URLConnection(urlStr, write);

        String[] databases = result.split(";");
        try {
            json = new JSONObject(databases[0]);
            //      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
            //      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
            Common.currentRecordsetLength = databases.length;
//                Common.total = json.getInt("COUNT(id)");
            for(int i = 1; i < databases.length; i++) {
//                Log.d("databases", databases[i]);
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

//    private String is2String(InputStream is) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//        StringBuffer sb = new StringBuffer();
//        char[] b = new char[1024];
//        int line;
//        while(0 <= (line = reader.read(b))) {
//            sb.append(b, 0, line);
//        }
//        return sb.toString();
//    }
//
//    private void paramSet(String result) {
//
//    }

//    @Override
//    public void onPostExecute(String result) {
//        Log.d("3", "kita");
//        MainActivity.result = result;
//        latch.countDown();
//        //データベースヘルパーオブジェクトを作成。
//        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
//        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得。
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        try {
//            //インサート用SQL文字列の用意。
//            String sqlInsert = "INSERT INTO sample2 (id) VALUES (?)";
//            //SQL文字列を元にプリペアドステートメントを取得。
//            SQLiteStatement stmt = db.compileStatement(sqlInsert);
//            //SQL文字列を元にプリペアドステートメントを取得。
//            stmt = db.compileStatement(sqlInsert);
//            //変数のバイド。
//            stmt.bindString(1, result);
//            //インサートSQLの実行。
//            stmt.executeInsert();
//        }
//        finally {
//            //データベース接続オブジェクトの解放。
//            db.close();
//        }
//    }

//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
}

