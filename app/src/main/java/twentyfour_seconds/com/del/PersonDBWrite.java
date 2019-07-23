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

public class PersonDBWrite extends AsyncTask<String, String, String> {

    private int id;
    private CountDownLatch latch;
    private JSONObject json;

    private String name;
    private String self_introduction;
    private String area;
    private int age;
    private String gender;

    PersonDBWrite(int id, String name, String selfintroduction, String area, int age, String gender, CountDownLatch latch) {
        this.id = id;
        this.name = name;
        this.self_introduction = selfintroduction;
        this.area = area;
        this.age = age;
        this.gender = gender;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(String... string) {
        String urlStr = "http://10.0.2.2:4000/person_write";
        String write = "";
        String result = "";

        StringBuilder sb = new StringBuilder();
        sb.append("id=" + id);
        sb.append("&name=" + name);
        sb.append("&self_introduction=" + self_introduction);
        sb.append("&area=" + area);
        sb.append("&age=" + age);
        sb.append("&gender=" + gender);

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
        Log.d("ラッチ前", "ラッチ前");
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

