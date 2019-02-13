package twentyfour_seconds.com.del;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventCreateDB extends AsyncTask<String, String, String> {

    private String eventName = null;
    private String area = null;
    private String place = null;
    private String eventDay = null;
    private int wantedPerson = 0;
    private String deadline = null;
    private String comment = null;







    EventCreateDB(String eventNameStr, String area, String placeStr, String eventDay, int wantedPerson, String deadline, String commentStr) {
        //コンストラクタ
        this.eventName = eventNameStr;
        this.area = area;
        this.place = placeStr;
        this.eventDay = eventDay;
        this.wantedPerson = wantedPerson;
        this.deadline = deadline;
        this.comment = commentStr;
    }

    @Override
    protected String doInBackground(String... string) {
        String urlStr = "http://10.0.2.2:8000/EventCreateDB";
        String write = "";
        String result = "";

        StringBuilder sb = new StringBuilder();
        sb.append("eventName=" + eventName);
        sb.append("&area=" + area);
        sb.append("&place=" + place);
        sb.append("&eventDay=" + eventDay);
        sb.append("&wantedPerson=" + wantedPerson);
        sb.append("&deadline=" + deadline);
        sb.append("&comment=" + comment);
        write = sb.toString();

        Log.i("write",write);

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

