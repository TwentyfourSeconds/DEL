package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class EventManagementDetailActivity extends AppCompatActivity {

    private Button dissolution;
    private Button recruitment_quit;
    private ListView entry_request;
    private GridView entry_member;
    private List<Map<String, String>> applicant = new ArrayList<>();
    private List<Map<String, String>> participant = new ArrayList<>();
    private List<Map<String, String>> nothing = new ArrayList<>();
    private String[] fromReservation = {"image", "name", "join", "reservation"};
//    private String[] fromReservation = {"image", "name", "join", "reservation", "approved", "unapproved"};
    private String[] fromParticipant = {"image", "name"};
    private String[] from = {"nothing"};
    private int[] toReservation = new int[4];
//    private int[] toReservation = new int[6];
    private int[] toParticipant = new int[2];
    private int[] to = new int[1];
    private SimpleAdapter adapter;
    private int id;
    private String title;
    private String deadline;
    //文字列検索時の検索用語
    private String searchWord;
    //top画面より渡されたタグ情報
    private int tag_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management_detail);

        entry_request = findViewById(R.id.entry_request);
        entry_member = findViewById(R.id.entry_member);

        //latchは1
        final CountDownLatch latch = new CountDownLatch(1);
        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータから、どの処理を動かすかを判断
        id = intent.getIntExtra("id", 1);
        title = intent.getStringExtra("title");
        deadline = intent.getStringExtra("deadline");

        JoinMemberDB jmDB = new JoinMemberDB(id, latch);
        jmDB.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < Common.nameList.size(); i++) {
            if(Common.joinStatusList.get(i) == 0) {
                Map<String, String> map = new HashMap<>();
                map.put("image", "" + getResources().getIdentifier("business","drawable", getPackageName()));
                map.put("name", Common.nameList.get(i));
                participant.add(map);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("id", Common.idList.get(i));
                map.put("image", "" + getResources().getIdentifier("business","drawable", getPackageName()));
                map.put("name", Common.nameList.get(i));
                map.put("join", "参加希望");
                map.put("reservation", Common.messageList.get(i));
                applicant.add(map);
            }
        }


//        for(int i = 0; i < Common.titleList.size(); i++) {
//            Map<String, String> map = new HashMap<>();
//            map.put("image", Common.imageList.get(i));
//            map.put("name", Common.titleList.get(i));
//            map.put("join", Common.areaList.get(i));
//            map.put("reservation", Common.localList.get(i));
//            applicant.add(map);
//        }
//
//        for(int i = 0; i < Common.titleList.size(); i++) {
//            Map<String, String> map = new HashMap<>();
//            map.put("image", Common.imageList.get(i));
//            map.put("name", Common.titleList.get(i));
//            participant.add(map);
//        }

//        for(int i = 0; i < 3; i++) {
//            Map<String, String> map = new HashMap<>();
//            map.put("image", "" + getResources().getIdentifier("business","drawable", getPackageName()));
//            map.put("name", "geeta");
//            map.put("join", "参加希望");
//            map.put("reservation", "メッセージ");
//            applicant.add(map);
//        }

//        for(int i = 0; i < 5; i++) {
//            Map<String, String> map = new HashMap<>();
//            map.put("image", "" + getResources().getIdentifier("business","drawable", getPackageName()));
//            map.put("name", "geeta");
//            participant.add(map);
//        }

        toReservation[0] = R.id.image;
        toReservation[1] = R.id.name;
        toReservation[2] = R.id.join;
        toReservation[3] = R.id.reservation;

        toParticipant[0] = R.id.image;
        toParticipant[1] = R.id.name;

        if(applicant.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("nothing", "なし");
            nothing.add(map);
            to[0] = R.id.nothing;
            adapter = new SimpleAdapter(EventManagementDetailActivity.this, nothing, R.layout.row_nothing, from, to);
        } else {
            adapter = new SimpleAdapter(EventManagementDetailActivity.this, applicant, R.layout.row_applicant, fromReservation, toReservation);
        }

//        Log.d("adapter", adapter.toString());
        entry_request.setAdapter(adapter);

        if(participant.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("nothing", "なし");
            nothing.add(map);
            to[0] = R.id.nothing;
            adapter = new SimpleAdapter(EventManagementDetailActivity.this, nothing, R.layout.row_nothing, from, to);
        } else {
            adapter = new SimpleAdapter(EventManagementDetailActivity.this, participant, R.layout.row_participant, fromParticipant, toParticipant);
        }

        entry_member.setAdapter(adapter);
        entry_member.setOnItemClickListener(new ListItemClickListener());

        //コンテキストメニューをリストビューに登録。
        registerForContextMenu(entry_request);

        dissolution = findViewById(R.id.dissolution);
        recruitment_quit = findViewById(R.id.recruitment_quit);

        dissolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recruitment_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * リストがタップされたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //タップされた行のデータを取得。
            Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);

            String idNum = (String)item.get("id");
            Log.d("id", idNum);

            // インテントへのインスタンス生成
            Intent intent = new Intent(EventManagementDetailActivity.this, RecruitmentDetailActivity.class);
            //　インテントに値をセット
            intent.putExtra("id", idNum);
            // サブ画面の呼び出し
            startActivity(intent);
        }
    }
}
