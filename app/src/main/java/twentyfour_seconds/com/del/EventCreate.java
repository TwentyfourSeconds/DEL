package twentyfour_seconds.com.del;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class EventCreate extends CustomActivity {

    //未入力チェックエラーフラグ(1の時、エラー）
    int EventNameErrFlg = 0;
    int EventRegionErrFlg = 0;
    int EventPlaceErrFlg = 0;
    int EventDayErrFlg = 0;
    int recruitmentNumbersErrFlg = 0;
    int LimitDayErrFlg = 0;
//    int hitokotoErrFlg = 0;（一言は未入力でもokとする）

    //カレンダーを読み込む処理はEventDay、LimitDayで共通で使用する
    //最後の出力時、どちらをクリックしたかを判断するために、以下のフラグて判断する。（１がクリックした）
    int EventDayClickFlg = 0;
    int LimitDayClickFlg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreate);

        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.tab4).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.tab4).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.tab4).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.tab4).findViewById(R.id.menu_bar_mypage);

        CustomActivity.menuClickListener menuClickListener = new menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

        //登録ボタンを押下時、情報を取得して、DetectionDBに登録する

        Button entrybutton = findViewById(R.id.entrybutton);
        View.OnClickListener entrybuttonClick = new entrybuttonClickListener();
        entrybutton.setOnClickListener(entrybuttonClick);

        // イベント開催日時と、締切日については、カレンダーから取得する
        TextView EventDay = (TextView) findViewById(R.id.EventDay);
        TextView LimitDay = (TextView) findViewById(R.id.LimitDay);

        carenderClickListener carenderClickListener = new carenderClickListener();
        EventDay.setOnClickListener(carenderClickListener);
        LimitDay.setOnClickListener(carenderClickListener);
    }

    //カレンダークリックイベント（カレンダーフラグメントを実行）
    public class carenderClickListener implements View.OnClickListener {
        @Override
        // クリックしたらダイアログを表示する処理
        public void onClick(View view) {
            // EventDay、LimitDayのどちらから押されたかを制御
            EventDayClickFlg = 0;
            LimitDayClickFlg = 0;

            int id = view.getId();
            switch (id) {
                case R.id.EventDay:
                    EventDayClickFlg = 1;
                    break;
                case R.id.LimitDay:
                    LimitDayClickFlg = 1;
                    break;
            }
            // ダイアログクラスをインスタンス化
            calender calender = new calender();
            // 表示  getSupportFragmentManager()は固定、sampleは識別タグ
            calender.show(getSupportFragmentManager(), "calender");
        }
    }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setTextView(String value) {
        if(EventDayClickFlg == 1) {
            TextView EventDay = (TextView) findViewById(R.id.EventDay);
            EventDay.setText(value);
        }
        if(LimitDayClickFlg == 1) {
            TextView LimitDay = (TextView) findViewById(R.id.LimitDay);
            LimitDay.setText(value);
        }
    }

    //登録ボタン押下時の動き
    public class entrybuttonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


            //イベント名を取得
            EditText EventName = findViewById(R.id.EventName);
            String EventNameStr = EventName.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (EventNameStr == null) {
                EventNameErrFlg = 1;
            }

            //イベントの開催県を取得
            Spinner EventRegionSpinner = (Spinner) findViewById(R.id.EventRegion);
            String EventRegion = (String) EventRegionSpinner.getSelectedItem();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (EventRegion.equals("未選択")) {
                EventRegionErrFlg = 1;
            }

            //イベントの開催場所を取得
            EditText EventPlace = findViewById(R.id.EventPlace);
            String EventPlaceStr = EventPlace.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (EventPlaceStr == null) {
                EventPlaceErrFlg = 1;
            }

            //イベントの開催日付を取得（〇/〇〇の形にして登録）
            //EventDayの戻り値は　2019年2月6日　のように帰ってくる。
            TextView EventDay = findViewById(R.id.EventDay);
            String EventDayStr = EventDay.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (EventDayStr == null) {
                EventDayErrFlg = 1;
            }
            DateFormat EventDaydf1 = new SimpleDateFormat("yyyy年MM月dd日");
            DateFormat EventDaydf2 = new SimpleDateFormat("y/M/d");
            try {
                Date d = EventDaydf1.parse(EventDayStr);
                System.out.println(EventDaydf2.format(d)); // => 2014年05月11日
            } catch (ParseException e) {
                e.printStackTrace();
            }
            EventDayStr = EventDaydf2.toString();


            //選択されている人数を取得（２人→２の形にして登録）
            Spinner recruitmentNumbersSpinner = (Spinner) findViewById(R.id.recruitmentNumbers);
            String recruitmentNumbersStr = (String) recruitmentNumbersSpinner.getSelectedItem();
            int size = recruitmentNumbersStr.length();
            int cut_length = 1;
            String recruitmentNumbersStr2 = null;
            Log.d(recruitmentNumbersStr, recruitmentNumbersStr);
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (recruitmentNumbersStr.equals("未選択")) {
                recruitmentNumbersErrFlg = 1;
            } else {
                //substring(int beginIndex, int endIndex):beginIndex～endIndexまでの文字列を抜き出す
            recruitmentNumbersStr2 = recruitmentNumbersStr.substring(0, size - cut_length);
            int recruitmentNumbers = parseInt(recruitmentNumbersStr2);
            }

            //イベント締切日付を取得（〇/〇〇の形にして登録）
            TextView LimitDay = findViewById(R.id.LimitDay);
            String LimitDayStr = LimitDay.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if( LimitDayStr == null){
                LimitDayErrFlg = 1;
            }
            DateFormat LimitDaydf1 = new SimpleDateFormat("yyyy年MM月dd日");
            DateFormat LimitDaydf2 = new SimpleDateFormat("y/M/d");
            try {
                Date d = LimitDaydf1.parse(LimitDayStr);
                System.out.println(LimitDaydf2.format(d)); // => 2014年05月11日
            } catch (ParseException e) {
                e.printStackTrace();
            }
            LimitDayStr = LimitDaydf2.toString();


            //一言を取得
            EditText hitokoto = findViewById(R.id.hitokoto);
            String hitokotoStr = hitokoto.getText().toString();

////            //DBに書き込みに行く
//            EventCreateDB EventCreateDB = new EventCreateDB(EventNameStr,EventRegion,EventPlaceStr,EventDayStr,recruitmentNumbers, LimitDayStr,hitokotoStr);
        }
    }
}



