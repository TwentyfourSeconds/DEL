package twentyfour_seconds.com.del.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Common {

    public static final String STR_MYSQL_URL = "http://3.15.24.173";
    public static final String EVENT_INFO_MYSQL_URL = STR_MYSQL_URL + ":8000/event_info_id_search";
    public static final String EVENT_SEARCH_NAME_URL = STR_MYSQL_URL + ":8000/event_info_event_name_search";

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

    public static String is2String(InputStream is) throws IOException {
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
