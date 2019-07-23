package twentyfour_seconds.com.del;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventCreateDBWrite extends AsyncTask<String, String, String> {

    private String eventName = null;
    private String founder = null;
    private String area = null;
    private String place = null;
    private String eventDay = null;
    private String deadline = null;
    private int current_person = 0;
    private int wanted_person = 0;
    private String comment = null;
    private int delete_flg = 0;


    EventCreateDBWrite(String eventNameStr,String founder, String area, String placeStr, String eventDay, String deadline, int current_person, int wantedPerson, String commentStr, int delete_flg) {
        //コンストラクタ
        this.eventName = eventNameStr;
        this.founder = founder;
        this.area = area;
        this.place = placeStr;
        this.eventDay = eventDay;
        this.deadline = deadline;
        this.current_person = current_person;
        this.wanted_person = wantedPerson;
        this.comment = commentStr;
        this.delete_flg = delete_flg;
    }

    @Override
    protected String doInBackground(String... string) {
        String urlStr = Common.STR_MYSQL_URL + ":7000/EventCreateDB";
        String write = "";
        String result = "";

        StringBuilder sb = new StringBuilder();
        sb.append("event_name=" + eventName);
        sb.append("&founder=" + founder);
        sb.append("&area=" + area);
        sb.append("&place=" + place);
        sb.append("&event_day=" + eventDay);
        sb.append("&deadline=" + deadline);
        sb.append("&current_person=" + current_person);
        sb.append("&wanted_person=" + wanted_person);
        sb.append("&comment=" + comment);
        sb.append("&delete_flg=" + delete_flg);
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
            result = is2String(is);
            Log.d("result", result);
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

