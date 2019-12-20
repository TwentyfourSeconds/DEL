package twentyfour_seconds.com.del.create_event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.util.calender;

import static java.lang.Integer.parseInt;

public class EventCreate1 extends CustomActivity {


    //未入力チェックエラーフラグ(1の時、エラー）
    int eventNameErrFlg = 0;
    int areaErrFlg = 0;
    int placeErrFlg = 0;
    int eventDayErrFlg = 0;
    int wantedPersonErrFlg = 0;
    int deadlineErrFlg = 0;
//    int hitokotoErrFlg = 0;（一言は未入力でもokとする）

    //カレンダーを読み込む処理はEventDay、LimitDayで共通で使用する
    //最後の出力時、どちらをクリックしたかを判断するために、以下のフラグて判断する。（１がクリックした）
    int eventDayClickFlg = 0;
    int deadlineClickFlg = 0;

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
        TextView eventDay = (TextView) findViewById(R.id.EventDay);
        TextView limitDay = (TextView) findViewById(R.id.LimitDay);

        carenderClickListener carenderClickListener = new carenderClickListener();
        eventDay.setOnClickListener(carenderClickListener);
        limitDay.setOnClickListener(carenderClickListener);
    }

    //カレンダークリックイベント（カレンダーフラグメントを実行）
    public class carenderClickListener implements View.OnClickListener {
        @Override
        // クリックしたらダイアログを表示する処理
        public void onClick(View view) {
            // EventDay、LimitDayのどちらから押されたかを制御
            eventDayClickFlg = 0;
            deadlineClickFlg = 0;

            int id = view.getId();
            switch (id) {
                case R.id.EventDay:
                    eventDayClickFlg = 1;
                    break;
                case R.id.LimitDay:
                    deadlineClickFlg = 1;
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
        if(eventDayClickFlg == 1) {
            TextView EventDay = (TextView) findViewById(R.id.EventDay);
            EventDay.setText(value);
        }
        if(deadlineClickFlg == 1) {
            TextView LimitDay = (TextView) findViewById(R.id.LimitDay);
            LimitDay.setText(value);
        }
    }

    //登録ボタン押下時の動き
    public class entrybuttonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //エラーメッセージ、エラーフラグを初期化
            eventNameErrFlg = 0;
            areaErrFlg = 0;
            placeErrFlg = 0;
            eventDayErrFlg = 0;
            wantedPersonErrFlg = 0;
            deadlineErrFlg = 0;

            TextView eventNameErrMessage = findViewById(R.id.EventNameErrMessage);
            eventNameErrMessage.setText("");

            TextView areaErrMessage = findViewById(R.id.EventRegionErrMessage);
            areaErrMessage.setText("");

            TextView placeErrMessage = findViewById(R.id.EventPlaceErrMessage);
            placeErrMessage.setText("");

            TextView eventDayErrMessage = findViewById(R.id.EventDayErrMessage);
            eventDayErrMessage.setText("");

            TextView wantedPersonErrMessage = findViewById(R.id.recruitmentNumbersErrMessage);
            wantedPersonErrMessage.setText("");

            TextView deadlineErrMessage = findViewById(R.id.LimitDayErrMessage);
            deadlineErrMessage.setText("");


            //イベント名を取得
            EditText eventName = findViewById(R.id.EventName);
            String eventNameStr = eventName.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (eventNameStr.equals("")) {
                eventNameErrFlg = 1;
                eventNameErrMessage.setText("イベント名が未入力です");
            }

            //イベントの開催県を取得
            Spinner areaSpinner = (Spinner) findViewById(R.id.EventRegion);
            String area = (String) areaSpinner.getSelectedItem();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (area.equals("未選択")) {
                areaErrFlg = 1;
                areaErrMessage.setText("開催県が未選択です");
            }

            //イベントの開催場所を取得
            EditText place = findViewById(R.id.EventPlace);
            String placeStr = place.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (placeStr.equals("")) {
                placeErrFlg = 1;
                placeErrMessage.setText("開催場所が未選択です");
            }

            //イベントの開催日付を取得（〇/〇〇の形にして登録）
            //EventDayの戻り値は　2019年2月6日　のように帰ってくる。
            TextView eventDay = findViewById(R.id.EventDay);
            String eventDayStr = eventDay.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (eventDayStr.equals("")) {
                eventDayErrFlg = 1;
                eventDayErrMessage.setText("日程が未選択です");
            }else {
                DateFormat eventDaydf1 = new SimpleDateFormat("yyyy年MM月dd日");
                DateFormat eventDaydf2 = new SimpleDateFormat("y/M/d");
                try {
                    Date d = eventDaydf1.parse(eventDayStr);
                    System.out.println(eventDaydf2.format(d)); // => 2014年05月11日
                    eventDayStr = new SimpleDateFormat("yyyy/MM/dd").format(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //選択されている人数を取得（２人→２の形にして登録）
            Spinner wantedPersonSpinner = (Spinner) findViewById(R.id.recruitmentNumbers);
            String wantedPersonStr = (String) wantedPersonSpinner.getSelectedItem();
            int size = wantedPersonStr.length();
            int cut_length = 1;
            int wantedPerson = 0;
            String wantedPersonStr2 = null;
            Log.d(wantedPersonStr, wantedPersonStr);
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if (wantedPersonStr.equals("未選択")) {
                wantedPersonErrFlg = 1;
                wantedPersonErrMessage.setText("募集人数が未選択です");
            } else {
                //substring(int beginIndex, int endIndex):beginIndex～endIndexまでの文字列を抜き出す
                wantedPersonStr2 = wantedPersonStr.substring(0, size - cut_length);
                wantedPerson = parseInt(wantedPersonStr2);
            }

            //イベント締切日付を取得（〇/〇〇の形にして登録）
            TextView deadline = findViewById(R.id.LimitDay);
            String deadlineStr = deadline.getText().toString();
            //登録チェック処理（一言以外は、未入力の場合エラー）
            if( deadlineStr.equals("")){
                deadlineErrFlg = 1;
                deadlineErrMessage.setText("募集期限が未選択です");
            }else {
                DateFormat deadlinedf1 = new SimpleDateFormat("yyyy年MM月dd日");
                DateFormat deadlinedf2 = new SimpleDateFormat("y/M/d");
                try {
                    Date d = deadlinedf1.parse(deadlineStr);
                    System.out.println(deadlinedf2.format(d)); // => 2014年05月11日
                    deadlineStr = new SimpleDateFormat("yyyy/MM/dd").format(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //その他、eventDBに必要な情報を取得する
            //founderは、person_infoより、nameを取得する
            //latchは1
//            final CountDownLatch latch = new CountDownLatch(1);
//            String founder = null;
//
//            //データベース関連はテストのために一回コメント化
//            PersonDB personDB = new PersonDB(1,latch);
//            personDB.execute();
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            founder = Common.personName;
//
//            Log.d("founder", Common.personName + "");
//            Log.d("founder", founder + "");

            //current_personは、初期値の1を登録する
            int current_person = 1;

            //delete_flgは、初期値のゼロを登録する
            int delete_flg = 0;

            //データベース関連はテストのために一回コメント化
            if((eventNameErrFlg == 1)||(areaErrFlg == 1)||(placeErrFlg == 1)||(eventDayErrFlg == 1)||(wantedPersonErrFlg == 1)||(deadlineErrFlg ==1)){
                Toast.makeText(EventCreate1.this, "入力項目に誤りがあります", Toast.LENGTH_SHORT).show();
            }else {
            //DBに書き込みに行く
//                EventCreateDB eventCreateDB = new EventCreateDB(eventNameStr,founder,area,placeStr,eventDayStr,deadlineStr,current_person,wantedPerson,commentStr,delete_flg);
//                eventCreateDB.execute();
//            //タグ情報登録画面へ遷移
                Intent intentEventCreate3 = new Intent(getApplicationContext(), EventCreate2.class);
                intentEventCreate3.putExtra("eventNameStr", eventNameStr);
                intentEventCreate3.putExtra("area", area);
                intentEventCreate3.putExtra("placeStr", placeStr);
                intentEventCreate3.putExtra("eventDayStr", eventDayStr);
                intentEventCreate3.putExtra("wantedPerson", wantedPerson);
                intentEventCreate3.putExtra("deadlineStr", deadlineStr);
                startActivity(intentEventCreate3);
            }
        }
    }
}



