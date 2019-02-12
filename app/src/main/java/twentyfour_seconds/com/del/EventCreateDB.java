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
import java.net.MalformedURLException;
import java.net.URL;

import twentyfour_seconds.com.del.Common;

public class EventCreateDB extends AsyncTask<String, String, String> {

    private String EventName = null;
    private String EventRegion = null;
    private String EventPlace = null;
    private String EventDay = null;
    private int recruitmentNumbers = 0;
    private String LimitDay = null;
    private String hitokoto = null;

    EventCreateDB(String EventNameStr, String EventRegion, String EventPlaceStr, String EventDay, int recruitmentNumbers, String LimitDayStr, String hitokotoStr) {
        //コンストラクタ
        this.EventName = EventNameStr;
        this.EventRegion = EventRegion;
        this.EventPlace = EventPlaceStr;
        this.EventDay = EventDay;
        this.recruitmentNumbers = recruitmentNumbers;
        this.LimitDay = LimitDayStr;
        this.hitokoto = hitokotoStr;
    }

    @Override
    protected String doInBackground(String... string) {
        String urlStr = "http://10.0.2.2:8000/EventCreateDB";
        String write = "";
        String result = "";

        StringBuilder sb = new StringBuilder();
        sb.append("EventName=" + EventName);
        sb.append("&EventRegion=" + EventRegion);
        sb.append("&EventPlace=" + EventPlace);
        sb.append("&EventDay=" + EventDay);
        sb.append("&RecruitmentNumbers=" + recruitmentNumbers);
        sb.append("&LimitDay=" + LimitDay);
        sb.append("&Hitokoto=" + hitokoto);
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
                outStream.write(write.getBytes("UTF-8"));
                outStream.flush();
            } catch (IOException e) {
                // POST送信エラー
                e.printStackTrace();
                result = "POST送信エラー";
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
//            result = is2String(is);
//            Log.d("result", result);
        } catch (IOException ex) {
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
        return result;
    }
}

