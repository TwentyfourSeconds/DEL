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

        try {
            json = new JSONObject(result);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        latch.countDown();
        return result;
    }

//        String urlStr = Common.EVENT_INFO_MYSQL_URL;
//        String write = "";
//        String result = "";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("id=" + id);
//        write = sb.toString();
//
//        //http接続を行うHttpURLConnectionオブジェクトを宣言。finallyで確実に解放するためにtry外で宣言。
//        HttpURLConnection con = null;
//        //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言。同じくtry外で宣言。
//        InputStream is = null;
//        try {
//            //URLオブジェクトを生成。
//            URL url = new URL(urlStr);
//            //URLオブジェクトからHttpURLConnectionオブジェクトを取得。
//            con = (HttpURLConnection) url.openConnection();
//            //http接続メソッドを設定。
//            con.setRequestMethod("POST");
//            // no Redirects
//            con.setInstanceFollowRedirects(false);
//            // データを書き込む
//            con.setDoOutput(true);
//            //接続。
//            con.connect();
//
//            // POSTデータ送信処理
//            OutputStream outStream = null;
//
//
//            try {
//                outStream = con.getOutputStream();
//                outStream.write( write.getBytes("UTF-8"));
//                outStream.flush();
//            } catch (IOException e) {
//                // POST送信エラー
//                e.printStackTrace();
//                result="POST送信エラー";
//            } finally {
//                if (outStream != null) {
//                    outStream.close();
//                }
//            }
//            final int status = con.getResponseCode();
//            if (status == HttpURLConnection.HTTP_OK) {
//                Log.d("HTTP_STATUS", "HTTP_OK");
//            }
//            else{
//                Log.d("HTTP_STATUS", String.valueOf(status));
//            }
//            //HttpURLConnectionオブジェクトからレスポンスデータを取得。
//            is = con.getInputStream();
////                レスポンスデータであるInputStreamオブジェクトを文字列に変換。
//            result = Common.is2String(is);
////            Log.d("result", result);
//
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
//
////            String[] databases = result.split(";");
////            json = new JSONObject(databases[0]);
//            //      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
//            //      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
////            Common.currentRecordsetLength = databases.length;
////                Common.total = json.getInt("COUNT(id)");
////            for(int i = 0; i < databases.length; i++) {
//////                Log.d("databases", databases[i]);
////                json = new JSONObject(databases[i]);
////                data.add(json);
//////                Log.d("json", json.toString());
////                Common.idList.add(json.getString("id"));
////                Common.imageList.add(json.getString("image"));
////                Common.titleList.add(json.getString("event_name"));
////                Common.founderList.add(json.getString("founder"));
////                Common.areaList.add(json.getString("area"));
////                Common.localList.add(json.getString("place"));
////                Common.termList.add(json.getString("event_day"));
////                Common.deadlineList.add(json.getString("deadline"));
////                Common.memberList.add(json.getInt("current_person") + "/" + json.getInt("wanted_person"));
////            Log.d("json", json.toString());
////            Log.d("id", "" + json.getInt("id"));
////            Log.d("image", json.getString("image"));
////            Log.d("title", json.getString("title"));
////            Log.d("area", json.getString("area"));
////            Log.d("local", json.getString("local"));
////            Log.d("term", json.getString("term"));
////            Log.d("deadline", json.getString("deadline"));
////            Log.d("current_num", "" + json.getInt("current_num"));
////            Log.d("sum", "" + json.getInt("sum"));
////            }
////            json = new JSONObject(result);
////            Common.id = json.getString("id");
////            Common.image = json.getString("image");
////            Common.title = json.getString("event_name");
////            Common.name = json.getString("founder");
////            Common.area = json.getString("area");
////            Common.local = json.getString("place");
////            Common.date = json.getString("event_day");
////            Common.term = "test";
////            Common.deadline = json.getString("deadline");
////            Common.member = json.getString("current_person") + "/" + json.getString("wanted_person");
////            Common.comment = json.getString("comment");
////            Common.tag_type = json.getString("tag_type");
//
//        } catch (IOException ex) {
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            //HttpURLConnectionオブジェクトがnullでないなら解放。
//            if (con != null) {
//                con.disconnect();
//            }
//            //InputStreamオブジェクトがnullでないなら解放。
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException ex) {
//                }
//            }
//        }
//        latch.countDown();
//        return result;
//    }

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

}

