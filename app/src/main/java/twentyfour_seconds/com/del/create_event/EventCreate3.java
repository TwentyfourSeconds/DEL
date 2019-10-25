package twentyfour_seconds.com.del.create_event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import twentyfour_seconds.com.del.DTO.ViewItemDTO;
import twentyfour_seconds.com.del.R;
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
    private ArrayList<String> StringList;

    //この画面で入力したコメントを取得する
    private String conmmentstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreate3);

        //引きついてきたデータを取得
        ReceiveData();

        //確認画面(EventCreate4に遷移)
        Button event4GoButton = findViewById(R.id.Event4Gobutton);
        View.OnClickListener event4GoButtonClick = new  EventCreate3.event4GoButtonClickListener();
        event4GoButton.setOnClickListener( event4GoButtonClick);

    }

    //EventCreate3で引き継いできた情報をリストに渡す。
    private void ReceiveData(){
        Intent intent = getIntent();
        this.eventNameStr = intent.getStringExtra("eventNameStr");
        this.area = intent.getStringExtra("area");
        this.placeStr = intent.getStringExtra("placeStr");
        this.eventDayStr = intent.getStringExtra("eventDayStr");
        this.wantedPerson = intent.getIntExtra("wantedPerson", 0);
        this.deadlineStr = intent.getStringExtra("deadlineStr");
        this.StringList = intent.getStringArrayListExtra("list");
    }

    private void GetComment(){
        TextView comment = findViewById(R.id.comment);
        conmmentstr = comment.getText().toString();

    }


    //登録内容の確認ボタンを押下時の動き(eventCreate4に移動)
    public class  event4GoButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent EventCreate4 = new Intent(getApplicationContext(), EventCreate4.class);

            EventCreate4.putStringArrayListExtra("list", StringList);
            EventCreate4.putExtra("eventNameStr", eventNameStr);
            EventCreate4.putExtra("area", area);
            EventCreate4.putExtra("placeStr", placeStr);
            EventCreate4.putExtra("eventDayStr", eventDayStr);
            EventCreate4.putExtra("wantedPerson", wantedPerson);
            EventCreate4.putExtra("deadlineStr", deadlineStr);
            EventCreate4.putExtra("commentStr", conmmentstr);

            //画面のコメントを取得
            GetComment();

            startActivity(EventCreate4);
        }
    }



}
