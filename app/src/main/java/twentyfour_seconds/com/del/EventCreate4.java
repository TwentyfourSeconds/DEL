package twentyfour_seconds.com.del;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class EventCreate4 extends AppCompatActivity {

    private String textArr[] = {};
    private List<ViewItemDTO> messageList;
    private ViewAdapterReadOnly viewAdapterReadOnly;

    //前ページから引き継いできた情報をここに記載する
    private String eventNameStr;
    private String area;
    private String placeStr;
    private String eventDayStr;
    private String wantedPersonStr;
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
        recruitmentNumbers.setText(wantedPersonStr);
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
        View.OnClickListener contentregistrationButtonClick = new EventCreate4.contentregistrationButtonClick();
        registrationButton.setOnClickListener(contentregistrationButtonClick);

    }

    //EventCreate3で引き継いできた情報をリストに渡す。
    private void Receivedata(){
    Intent intent = getIntent();
    this.eventNameStr = intent.getStringExtra("eventNameStr");
    this.area = intent.getStringExtra("area");
    this.placeStr = intent.getStringExtra("placeStr");
    this.eventDayStr = intent.getStringExtra("eventDayStr");
    this.wantedPersonStr = intent.getStringExtra("wantedPersonStr");
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
            Intent EventCreate5Page = new Intent(getApplicationContext(), EventCreate5.class);
            startActivity(EventCreate5Page);
        }
    }



}