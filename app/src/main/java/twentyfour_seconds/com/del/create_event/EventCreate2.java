package twentyfour_seconds.com.del.create_event;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.dev2qa.example.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import twentyfour_seconds.com.del.DTO.ViewItemDTO;
import twentyfour_seconds.com.del.R;

//元のサンプルコード：https://www.dev2qa.com/android-flexbox-layout-example/

public class EventCreate2 extends AppCompatActivity {

    // This is the text that will be rendered in the screen.
//    private String textArr[] = {"dev2qa.com", "is", "a very good", "android example website", "there are", "a lot of", "android, java examples";
    private String textArr[] = {};
    private List<ViewItemDTO> messageList;
    private ViewAdapter viewAdapter;

    //削除用に別のクラスでも参照できるように、定義をここで行う。
    public TextView tukue;
    public TextView missitu;
    public TextView cityType;
    public TextView outField;
    public TextView shortTime;
    public TextView anime;

    //前ページから引き継いできた情報をここに記載する
    private String eventNameStr;
    private String area;
    private String placeStr;
    private String eventDayStr;
    private int wantedPerson;
    private String deadlineStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreate2);

    //前ページより引き継いできた情報を取得
        Receivedata();

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

    //リスナを設定(押下時、文字を選択する）
    tukue = findViewById(R.id.tukueType);
    missitu = findViewById(R.id.missituType);
    cityType = findViewById(R.id.cityType);
    outField = findViewById(R.id.outFieldType);
    shortTime = findViewById(R.id.shortTimeType);
    anime = findViewById(R.id.animeType);

    textbuttonClickListener tukueText = new EventCreate2.textbuttonClickListener();
    textbuttonClickListener missituText = new EventCreate2.textbuttonClickListener();
    textbuttonClickListener cityTypeText = new EventCreate2.textbuttonClickListener();
    textbuttonClickListener outFieldText = new EventCreate2.textbuttonClickListener();
    textbuttonClickListener shortTimeText = new EventCreate2.textbuttonClickListener();
    textbuttonClickListener animeText = new EventCreate2.textbuttonClickListener();

    tukue.setOnClickListener(tukueText);
    missitu.setOnClickListener(missituText);
    cityType.setOnClickListener(cityTypeText);
    outField.setOnClickListener(outFieldText);
    shortTime.setOnClickListener(shortTimeText);
    anime.setOnClickListener(animeText);

    Button contentCheckButton = findViewById(R.id.contentCheckButton);
    View.OnClickListener contentCheckButtonClick = new contentCheckButtonClickListener();
    contentCheckButton.setOnClickListener(contentCheckButtonClick);

    // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
    viewAdapter = new ViewAdapter(messageList);
    // アダプターオブジェクトをセット
    recyclerView.setAdapter(viewAdapter);

}

    //メソッドの概要：ViewItemDTOクラスを使って、アダプターの中に
    //　　　　　　　　テキストを設定する

    //*Tips：String型の項目が入るリストを定義
    //List<String> list = new ArrayList<String>();

    //*Tips：List<ViewItemDTO>の戻り値を返すメソッド
    //returnで値を戻す

    private List<ViewItemDTO> initViewItemDtoList() {
        //リストの配列　retを定義
        List<ViewItemDTO> ret = new ArrayList<ViewItemDTO>();

        for (int i = 0; i < this.textArr.length; i++) {
            //ViewItemDTOのインスタンスを生成
            ViewItemDTO itemDto = new ViewItemDTO();

            itemDto.setText(this.textArr[i]);
//          ret配列に、設定した文字列を追加
            ret.add(itemDto);
        }
        return ret;
    }

    //登録内容の確認ボタンを押下時の動き(eventCreate4に移動)
    public class  contentCheckButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent EventCreate3 = new Intent(getApplicationContext(), EventCreate3.class);
            //intentに送るデータ配列(String)と、変換用の一行のViewItemDTOを定義
            List<String> list = new ArrayList<String>();
            ViewItemDTO data;
            //messageListの長さを取得
            messageList.size();
            Log.i("messageList", messageList.size() + "");
            for (int i=0; i < messageList.size(); i++){
                data = messageList.get(i);
                //次の画面に送るときは、先頭の―を取る
                String dataA = data.getText();
                String dataB = dataA.substring(1);
                list.add(dataB);
            }
            EventCreate3.putStringArrayListExtra("list", (ArrayList<String>) list);
            EventCreate3.putExtra("eventNameStr", eventNameStr);
            EventCreate3.putExtra("area", area);
            EventCreate3.putExtra("placeStr", placeStr);
            EventCreate3.putExtra("eventDayStr", eventDayStr);
            EventCreate3.putExtra("wantedPerson", wantedPerson);
            EventCreate3.putExtra("deadlineStr", deadlineStr);
            startActivity(EventCreate3);
        }
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
    }

    private class textbuttonClickListener implements View.OnClickListener {
        @Override
        // クリックしたらダイアログを表示する処理
        public void onClick(View view) {
            int id = view.getId();
            ViewItemDTO itemDto = new ViewItemDTO();
            switch (id) {
                case R.id.tukueType:
                    itemDto.setText("－机に座ってガッツリと");
                    //共通で使用しているviewadapterに入れないと、こちらで別途定義をして、入れても、渡しているのは別のadapterなので意味ない。
                    //（参考）https://qiita.com/koujin/items/0bddffdab8f282a3cc6b
                    messageList.add(itemDto);
                    viewAdapter.notifyDataSetChanged();
                    //二回目に押せないよう、制御を実施
                    //※ここでsetClickableをしてはいけない。リスナの中で、setClickableを行っても反応しない。
                    view.setEnabled(false);
                    tukue.setTextColor(Color.BLUE);
                    break;
                case R.id.missituType:
                    itemDto.setText("－密室からの脱出");
                    messageList.add(itemDto);
                    viewAdapter.notifyDataSetChanged();
                    view.setEnabled(false);
                    missitu.setTextColor(Color.BLUE);
                    break;
                case R.id.cityType:
                    itemDto.setText("－街を歩き回って");
                    messageList.add(itemDto);
                    viewAdapter.notifyDataSetChanged();
                    view.setEnabled(false);
                    cityType.setTextColor(Color.BLUE);
                    break;
                case R.id.outFieldType:
                    itemDto.setText("－遊園地や野球場で");
                    messageList.add(itemDto);
                    viewAdapter.notifyDataSetChanged();
                    view.setEnabled(false);
                    outField.setTextColor(Color.BLUE);
                    break;
                case R.id.shortTimeType:
                    itemDto.setText("－短い時間で気軽に");
                    messageList.add(itemDto);
                    viewAdapter.notifyDataSetChanged();
                    view.setEnabled(false);
                    shortTime.setTextColor(Color.BLUE);
                    break;
                case R.id.animeType:
                    itemDto.setText("－アニメタイアップ");
                    messageList.add(itemDto);
                    viewAdapter.notifyDataSetChanged();
                    view.setEnabled(false);
                    anime.setTextColor(Color.BLUE);
                    break;
            }
        }
    }
}




