package twentyfour_seconds.com.del;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class TagMapDB extends AsyncTask<String, String, String> {

    private int Tag_id;
    private CountDownLatch latch;
    private int number = 0;
    private ArrayList<JSONObject> data = new ArrayList<JSONObject>();
    private JSONObject json;

    TagMapDB(int Tag_id, CountDownLatch latch) {
        this.Tag_id = Tag_id;
        this.latch = latch;
    }

    TagMapDB(int number, int Tag_id, CountDownLatch latch) {
        this.number = number;
        this.Tag_id = Tag_id;
        this.latch = latch;
    }


    @Override
    protected String doInBackground(String... string) {
        String urlStr = "http://10.0.2.2:8000/recruitment_tagMap";
        String write = "";
        String result = "";

        //動作前にcommonの中身をクリアする
        Common.tagMapList.clear();
        Common.idList.clear();
        Common.imageList.clear();
        Common.titleList.clear();
        Common.areaList.clear();
        Common.localList.clear();
        Common.termList.clear();
        Common.deadlineList.clear();
        Common.memberList.clear();


        StringBuilder sb = new StringBuilder();
        sb.append("tag_id=" + Tag_id);
        sb.append("number=" + number);
        write = sb.toString();

        //http接続を行うHttpURLConnectionオブジェクトを宣言。finallyで確実に解放するためにtry外で宣言。
        HttpURLConnection con = null;
        //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言。同じくtry外で宣言。
        InputStream is = null;
        try {
            //URLオブジェクトを生成。
            URL url = new URL(urlStr);
            //URLオブジェクトからHttpURLConnectionオブジェクトを取得。
            con = (HttpURLConnection) url.openConnection();
            //http接続メソッドを設定。
            con.setRequestMethod("POST");
            // no Redirects
            con.setInstanceFollowRedirects(false);
            // データを書き込む
            con.setDoOutput(true);
            //接続。
            con.connect();

            // POSTデータ送信処理
            OutputStream outStream = null;


            try {
                outStream = con.getOutputStream();
                outStream.write( write.getBytes("UTF-8"));
                outStream.flush();
            } catch (IOException e) {
                // POST送信エラー
                e.printStackTrace();
                result="POST送信エラー";
            } finally {
                if (outStream != null) {
                    outStream.close();
                }
            }
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                Log.d("HTTP_STATUS", "HTTP_OK");
            }
            else{
                Log.d("HTTP_STATUS", String.valueOf(status));
            }
            //HttpURLConnectionオブジェクトからレスポンスデータを取得。
            is = con.getInputStream();

            if(is == null) {
                return "0";
            }
//                レスポンスデータであるInputStreamオブジェクトを文字列に変換。
            result = is2String(is);
            Log.d("result", result + "end");
            String[] databases = result.split(";");
            json = new JSONObject(databases[0]);
            //      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
            //      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
            Common.currentRecordsetLength = databases.length;
            for(int i = 0; i < databases.length; i++) {
//                Log.d("databases", databases[i]);
                json = new JSONObject(databases[i]);
                data.add(json);
                Common.idList.add(json.getString("id"));
                Common.imageList.add(json.getString("image"));
                Common.titleList.add(json.getString("title"));
                Common.areaList.add(json.getString("area"));
                Common.localList.add(json.getString("local"));
                Common.termList.add(json.getString("term"));
                Common.deadlineList.add(json.getString("deadline"));
                Common.memberList.add(json.getInt("current_num") + "/" + json.getInt("sum"));
            }
        } catch (IOException ex) {
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //HttpURLConnectionオブジェクトがnullでないなら解放。
            if (con != null) {
                con.disconnect();
            }
            //InputStreamオブジェクトがnullでないなら解放。
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        }
        latch.countDown();
        return result;
    }

    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while(0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }
}