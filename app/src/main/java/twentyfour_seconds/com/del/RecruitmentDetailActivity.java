package twentyfour_seconds.com.del;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;


public class RecruitmentDetailActivity extends CustomActivity {

    private ImageView icon;
    private TextView leader;
    private TextView title;
    private TextView date;
    private TextView comment;
    private TextView area;
    private TextView location;
    private TextView deadline;
    private TextView member;
    private GridView tag;
    private Button entry;
    private Button temporary;
    private ListView chat;
    private TextView newComment;
    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wanted_detail);

        icon = findViewById(R.id.icon);
        leader = findViewById(R.id.leader);
        title = findViewById(R.id.title);
        date = findViewById(R.id.EventDay);
        comment = findViewById(R.id.comment);
        area = findViewById(R.id.area);
        location = findViewById(R.id.location);
        member = findViewById(R.id.member);
        deadline = findViewById(R.id.deadline);
        tag = findViewById(R.id.tag);
        entry = findViewById(R.id.entry);
//        temporary = findViewById(R.id.temporary);
//        chat = findViewById(R.id.chat);
//        newComment = findViewById(R.id.newComment);
//        chatButton = findViewById(R.id.chatButton);


        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータを取得
        int id = Integer.valueOf(intent.getStringExtra("id"));

        final CountDownLatch latch = new CountDownLatch(1);
        DetailDB ddb = new DetailDB(id, latch);
        ddb.execute();
//        ChatDB cdb = new ChatDB(id, latch);
//        cdb.execute();
        Log.i("id",id + "");
        TagDB tdb = new TagDB(id, latch);
        tdb.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        leader.setText("募集者：" + Common.name + "さん");
        title.setText(Common.title);
        date.setText("日程：" + Common.date);
        comment.setText(Common.comment);
        comment.setFocusable(false);
        area.setText("開催地：" + Common.area);
        location.setText("開催場所：" + Common.local);
        deadline.setText("掲載期限：" + Common.deadline);
        member.setText("募集人数：" + Common.member);



//        ArrayAdapter<String> tagAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Common.tagList);
//        tag.setAdapter(tagAdapter);
//
////        String[] data = {"1","2","3"};
////        String[] data = new String[Common.chat.size()];
////        for(int i = 0; i < Common.chat.size(); i++) {
////            Log.d("chat", Common.chat.get(i));
////            data[i] = Common.chat.get(i).toString();
////        }
////        ArrayList data = new ArrayList<>();
////        for(int i = 0; i < Common.chat.size(); i++) {
////            Log.d("chat", Common.chat.get(i));
////            data.add("" + Common.chat.get(i));
////            Log.d("data", "" + data.get(i));
////        }
////        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Common.chat);
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(RecruitmentDetailActivity.this, android.R.layout.simple_list_item_1, data);
////        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
////        Log.d("data", data.toString());
////        Log.d("chat", chat.toString());
//        chat.setAdapter(adapter);


//        icon.setImlocationResource(R.drawable.);
//        leader.setText("ジータ");
//        date1.setText("12/24");
//        date2.setText("12/24");
//        title.setText("○○のリアル脱出ゲーム");
//        valuation.setText("50");
//        comment.setText("自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介");
//        comment.setFocusable(false);
//        location.setText("東京");
//        age.setText("29歳");
//        gender.setText("男");
//        ticket.setText("なし");
//        deadline.setText("12/20");
//        chat.setFocusable(false);

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecruitmentDetailActivity.this);
                alertDialog.setTitle("確認");
                alertDialog.setMessage("このグループ参加しますか？");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create().show();
            }
        });

//        chatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecruitmentDetailActivity.this);
//                alertDialog.setTitle("下記のコメントでよろしいですか？");
//                alertDialog.setMessage("テストメッセージ");
//                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alertDialog.create().show();
//            }
//        });


        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_mypage);

        MyPageActivity.menuClickListener menuClickListener = new MyPageActivity.menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

//        Intent intent = getIntent();
//        int iconId = getResources().getIdentifier(intent.getStringExtra("icon"), "drawable", getPacklocationdate());
//        icon.setImlocationResource(iconId);
//        leader.setText(intent.getStringExtra("date"));
//        date.setText(intent.getStringExtra("date"));
//        title.setText(intent.getStringExtra("title"));
//        valuation.setText(intent.getStringExtra("valuation"));
//        comment.setText(intent.getStringExtra("comment"));
//        location.setText(intent.getStringExtra("location") + "才");
//        age.setText(intent.getStringExtra("age"));
//        gender.setText(intent.getStringExtra("gender"));
    }
}
