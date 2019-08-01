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
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.util.Common;

public class event_info_event_name_search extends AsyncTask<String, String, String> {

    //numberは、DBを読み込むときの開始位置
    private int number = 0;
    private String searchWord;
    private CountDownLatch latch;
    private ArrayList<JSONObject> data = new ArrayList<JSONObject>();
    private JSONObject json;

    event_info_event_name_search(int number, String searchWord, CountDownLatch latch) {
        this.number = number;
        this.searchWord = searchWord;
        this.latch = latch;
    }

    event_info_event_name_search(int number, CountDownLatch latch) {
        this.number = number;
        this.latch = latch;
    }

    event_info_event_name_search(String searchWord, CountDownLatch latch) {
        this.searchWord = searchWord;
        this.latch = latch;
    }

    event_info_event_name_search(CountDownLatch latch) {
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
        String urlStr = Common.STR_MYSQL_URL + ":8000/event_info_event_name_search";
        String write = "";
        String result = "";

        Common.idList.clear();
        Common.imageList.clear();
        Common.titleList.clear();
        Common.areaList.clear();
        Common.localList.clear();
        Common.termList.clear();
        Common.deadlineList.clear();
        Common.memberList.clear();

        StringBuilder sb = new StringBuilder();
        sb.append("number=" + number);
        sb.append("&searchWord=" + searchWord);
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
//                レスポンスデータであるInputStreamオブジェクトを文字列に変換。
            result = is2String(is);
            Log.d("result", result);
            String[] databases = result.split(";");
            json = new JSONObject(databases[0]);
            //      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
            //      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
            Common.currentRecordsetLength = databases.length;
//                Common.total = json.getInt("COUNT(id)");
            for(int i = 1; i < databases.length; i++) {
//                Log.d("databases", databases[i]);
                json = new JSONObject(databases[i]);
                data.add(json);
//                Log.d("json", json.toString());
                Common.idList.add(json.getString("id"));
                Common.imageList.add(json.getString("image"));
                Common.titleList.add(json.getString("event_name"));
                Common.founderList.add(json.getString("founder"));
                Common.areaList.add(json.getString("area"));
                Common.localList.add(json.getString("place"));
                Common.termList.add(json.getString("event_day"));
                Common.deadlineList.add(json.getString("deadline"));
                Common.memberList.add(json.getInt("current_person") + "/" + json.getInt("wanted_person"));
//            Log.d("json", json.toString());
//            Log.d("id", "" + json.getInt("id"));
//            Log.d("image", json.getString("image"));
//            Log.d("title", json.getString("title"));
//            Log.d("area", json.getString("area"));
//            Log.d("local", json.getString("local"));
//            Log.d("term", json.getString("term"));
//            Log.d("deadline", json.getString("deadline"));
//            Log.d("current_num", "" + json.getInt("current_num"));
//            Log.d("sum", "" + json.getInt("sum"));
            }
        } catch (MalformedURLException ex) {
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

    private void paramSet(String result) {

    }

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

