package twentyfour_seconds.com.del.event_create;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.ViewItemDTO;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.ViewAdapterReadOnly;

public class EventCreate3 extends AppCompatActivity {

    private String textArr[] = {};
    private List<ViewItemDTO> messageList;
    private ViewAdapterReadOnly viewAdapterReadOnly;

    //前ページから引き継いできた情報をここに記載する
    private String eventNameStr;
    private String area;
    private String placeStr;
    private String eventDayStr;
    private int wantedPerson;
    private String deadlineStr;
    private String commentStr;
    private ArrayList<String> StringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreate4);

        //引きついてきたデータを取得
        Receivedata();

        //画面のテキスト部品に引き継いできた条件を割り当て
        TextView EventName = findViewById(R.id.EventName);
        EventName.setText(eventNameStr);
        TextView EventRegion = findViewById(R.id.EventRegion);
        EventRegion.setText(area);
        TextView EventPlace = findViewById(R.id.EventPlace);
        EventPlace.setText(placeStr);
        TextView EventDay = findViewById(R.id.EventDay);
        EventDay.setText(eventDayStr);
        TextView recruitmentNumbers = findViewById(R.id.recruitmentNumbers);
        recruitmentNumbers.setText(wantedPerson + "");
        TextView LimitDay = findViewById(R.id.LimitDay);
        LimitDay.setText(deadlineStr);
        TextView hitokoto = findViewById(R.id.hitokoto);
        hitokoto.setText(commentStr);

        // Get the RecyclerView object.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.flex_box_recycler_view);

        //--------------------------------flexBox Layout の調整-----------------------------------------------//
        // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        //どっち側に伸ばすか.
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        // 子Viewの並び方向へのViewの張り付き位置
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView.setLayoutManager(flexboxLayoutManager);

        //--------------------------------viewAdapter の調整-----------------------------------------------//

        //データをmessageListに格納
        messageList = this.initViewItemDtoList();

        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
        viewAdapterReadOnly = new ViewAdapterReadOnly(messageList);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(viewAdapterReadOnly);

        //登録ボタンを押下
        Button registrationButton = findViewById(R.id.registrationButton);
        View.OnClickListener contentregistrationButtonClick = new EventCreate3.contentregistrationButtonClick();
        registrationButton.setOnClickListener(contentregistrationButtonClick);

    }

    //EventCreate3で引き継いできた情報をリストに渡す。
    private void Receivedata(){
    Intent intent = getIntent();
    this.eventNameStr = intent.getStringExtra("eventNameStr");
    this.area = intent.getStringExtra("area");
    this.placeStr = intent.getStringExtra("placeStr");
    this.eventDayStr = intent.getStringExtra("eventDayStr");
    this.wantedPerson = intent.getIntExtra("wantedPerson", 0);
    this.deadlineStr = intent.getStringExtra("deadlineStr");
    this.commentStr = intent.getStringExtra("commentStr");
    this.StringList = intent.getStringArrayListExtra("list");
    }

    private List<ViewItemDTO> initViewItemDtoList() {
        //リストの配列　retを定義
        List<ViewItemDTO> ret = new ArrayList<ViewItemDTO>();

        for (int i = 0; i < this.StringList.size(); i++) {
            //ViewItemDTOのインスタンスを生成
            ViewItemDTO itemDto = new ViewItemDTO();

            itemDto.setText(StringList.get(i));
//          ret配列に、設定した文字列を追加
            ret.add(itemDto);
        }
        return ret;
    }

    //登録内容の確認ボタンを押下時の動き(eventCreate4に移動)
    public class  contentregistrationButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //データベースへの登録処理を行う。

            //founderは、person_infoより、nameを取得する
            //latchは1
            final CountDownLatch latch = new CountDownLatch(1);
            String founder = "サンプル太郎";

            //current_personは、初期値の1を登録する
            int current_person = 1;

            //delete_flgは、初期値のゼロを登録する
            int delete_flg = 0;


//            //founderは一回コメント化
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

            //DBに書き込みに行く
            StringBuilder sb = new StringBuilder();
            String write = "";
            sb.append("event_name=" + eventNameStr);
            sb.append("&founder=" + founder);
            sb.append("&area=" + area);
            sb.append("&place=" + placeStr);
            sb.append("&event_day=" + eventDayStr);
            sb.append("&deadline=" + deadlineStr);
            sb.append("&current_person=" + current_person);
            sb.append("&wanted_person=" + wantedPerson);
            sb.append("&comment=" + commentStr);
            sb.append("&delete_flg=" + delete_flg);
            write = sb.toString();
            EventCreateDAO eventCreateDAO = new EventCreateDAO(Common.EVENT_CREATE_URL, write);
            eventCreateDAO.execute();

            //TagDBに登録する

            //登録完了画面(EventCreate4)に移動
            Intent EventCreate5Page = new Intent(getApplicationContext(), EventCreate4.class);
            startActivity(EventCreate5Page);
        }
    }



}