package twentyfour_seconds.com.del.util;

import android.util.Log;

import org.json.JSONException;

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

public class Common {

    public static final String STR_MYSQL_URL = "http://3.15.24.173";
//    public static final String STR_MYSQL_URL = "http://" + "10.0.2.2";
    public static final String EVENT_INFO_MYSQL_URL = STR_MYSQL_URL + ":8000/event_info_id_search";
    public static final String EVENT_SEARCH_NAME_URL = STR_MYSQL_URL + ":8000/event_info_event_name_search";
    public static final String EVENT_SEARCH_TAG_URL = STR_MYSQL_URL + ":8000/recruitment_tagMap";
    public static final String EVENT_CREATE_URL = STR_MYSQL_URL + ":7000/EventCreateDB";
    public static final String COUNT_EVENT_URL = STR_MYSQL_URL + ":1050/count_event";
    public static final String SEARCH_EVENT_URL = STR_MYSQL_URL + ":1051/search_event";
    public static final String UID_SEARCH_EVENT_URL = STR_MYSQL_URL + ":1052/uid_search_event";
    public static final String ID_SEARCH_EVENT_URL = STR_MYSQL_URL + ":2050/id_search_event";
    public static final String PARTICIPANT_EVENT_URL = STR_MYSQL_URL + ":3050/participant_event";
    public static final String ADD_PARTICIPANT_EVENT_URL = STR_MYSQL_URL + ":3051/add_participant_event";
    public static final String CREATE_EVENT_URL = STR_MYSQL_URL + ":4050/create_event";
    public static final String UPDATE_EVENT_URL = STR_MYSQL_URL + ":4051/update_event";
    public static final String CREATE_NEW_EVENT_SEARCH_URL = STR_MYSQL_URL + ":4052/search_create_event";

    public static final String[] REGION_ARY = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県",
            "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県", "新潟県", "富山県",
            "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県", "滋賀県",
            "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県",
            "山口県", "徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県", "熊本県",
            "大分県", "宮崎県", "鹿児島県", "沖縄県"};
    public static final String REGION_NOTHING = "00000000000000000000000000000000000000000000000";
    public static final String REGION_FLG_ON = "1";
    public static final String REGION_FLG_OFF = "0";

    public static int total;
    public static List<String> idList = new ArrayList<>();
    public static List<String> imageList = new ArrayList<>();
    public static List<String> titleList = new ArrayList<>();
    public static List<String> areaList = new ArrayList<>();
    public static List<String> founderList = new ArrayList<>();
    public static List<String> localList = new ArrayList<>();
    public static List<String> termList = new ArrayList<>();
    public static List<String> deadlineList = new ArrayList<>();
    public static List<String> memberList = new ArrayList<>();

    //user_id_table_readで使用（ユーザーを特定するための情報群）
    public static String user_id;
    public static String unique_id;


    //detailDBにて使用
    public static String id;
    public static String image;
    public static String title;
    public static String name;
    public static String area;
    public static String local;
    public static String date;
    public static String term;
    public static String deadline;
    public static String member;
    public static String comment;
    public static String tag_type;

    public static List<String> chat = new ArrayList<>();

    //タグの名称が格納されているリスト
    public static List<String> tagList = new ArrayList<>();
    //イベントに紐づくタグを管理している文字列
    public static List<String> tagMapList = new ArrayList<>();

    public static String personId;
    public static String personName;
    public static String personLocation;
    public static int personAge;
    public static int personGender;
    public static int currentRecordsetLength;
    public static String personSelfIntroduction;

    public static List<String> nameList = new ArrayList<>();
    public static List<String> messageList = new ArrayList<>();
    public static List<Integer> joinStatusList = new ArrayList<>();

    public static String URLConnection(String urlStr, String write) {

        String result = "";

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
        } catch (MalformedURLException ex) {
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

    private static String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while(0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }


    //UserDTOの値を全画面で共有するため、コモンクラスに現在のログイン者の情報を管理
    public static String uid;
    public static String username;
    public static int age;
    public static int gender;
    public static String location;
    public static String profile;
    public static String profileImageUrl;
    public static String filename;
    public static String regionSetting;

    //イベント作成後の新しいイベントidを取得する
    public static int newEventId;

}
