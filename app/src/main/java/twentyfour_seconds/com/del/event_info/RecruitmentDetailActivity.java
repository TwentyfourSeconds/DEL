package twentyfour_seconds.com.del.event_info;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.ViewAdapterImageReadOnly;
import twentyfour_seconds.com.del.util.ViewAdapterReadOnly;
import twentyfour_seconds.com.del.DTO.ViewItemDTO;


public class RecruitmentDetailActivity extends CustomActivity {

    private final String ID_SEND = "id=";
    private final String EVENT_URL = Common.ID_SEARCH_EVENT_URL;
    private final String REQUEST_JOIN = Common.ADD_PARTICIPANT_EVENT_URL;
    private final String EVENT_ID_SEND = "event_id=";
    private final String MEMBER_ID_SEND = "&member_uid=" + Common.uid;
    private int id;

    private EventInfoDTO eventInfoDTO = new EventInfoDTO();

    private TextView leader;
    private TextView title;
    private TextView date;
    private TextView comment;
    private TextView area;
    private TextView entryTime;
    private TextView deadline;
    private TextView member;
    private Button entry;

    //変数の定義
    private List<ViewItemDTO> messageList;
    private ViewAdapterReadOnly viewAdapterReadOnly;
    private String tag_id;
    //取得してきたタグをリストの中に入れる
    List<String> tagName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータを取得
        id = Integer.valueOf(intent.getStringExtra("id"));

        //新DB用
        String write = "";
        StringBuilder sb = new StringBuilder();
        sb.append(ID_SEND + id);
        write = sb.toString();

        final CountDownLatch latch = new CountDownLatch(1);
        EventInfoDAO eventInfoDAO = new EventInfoDAO(EVENT_URL, write, eventInfoDTO, latch);
        eventInfoDAO.execute();


//        String write = "";
//        StringBuilder sb = new StringBuilder();
//        sb.append("id=" + id);
//        write = sb.toString();
//
//        final CountDownLatch latch = new CountDownLatch(1);
//        EventInfoDAO eventInfoDAO = new EventInfoDAO(Common.EVENT_INFO_MYSQL_URL, write, eventInfoDTO, latch);
//        eventInfoDAO.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //取得したタグid情報を取得する。複数くることはない想定なので、一番最初の要素を抽出
//        tag_id = Common.tag_type;
//        Log.d("tag_id", tag_id + "");
//
//        //取得したタグid情報を分割する。
//        String[] tag = tag_id.split(",", 0);
//
//        Log.d("tag.length", tag.length + "");
//

//        for(int i = 0; i < tag.length; i++){
//            Log.d("tag", Integer.parseInt(tag[i]) + "");
//        }

        //タグDB用のラッチを新しく用意する
//        CountDownLatch latch2 = new CountDownLatch(1);

        //タグidから、tagDBを読み込み、タグ名称を取得する。
//        for(int i = 0; i < tag.length; i++){
//
//            int tagIdSearch = Integer.parseInt(tag[i]);
//            Log.d("tagIdSearch", tagIdSearch + "");
//            TagDB tdb = new TagDB(tagIdSearch, latch2);
//            tdb.execute();
//            try {
//                latch2.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            latch2 = new CountDownLatch(1);
//        }

        //

//        //タグを日本語化する処理を作成する★将来的には別クラスで作成する
//        for(int i = 0; i < tag.length; i++) {
//
//            int tagIdSearch = Integer.parseInt(tag[i]);
//            switch(tagIdSearch){
//                case 1:
//                    tagName.add("机に座ってガッツリと");
//                    break;
//                case 2:
//                    tagName.add("密室からの脱出");
//                    break;
//                case 3:
//                    tagName.add("街を歩き回って");
//                    break;
//                case 4:
//                    tagName.add("遊園地や野球場で");
//                    break;
//                case 5:
//                    tagName.add("短い時間で気軽に");
//                    break;
//            }
//        }

        setContentView(R.layout.wanted_detail);

//        icon = findViewById(R.id.icon);
        leader = findViewById(R.id.leader);
        title = findViewById(R.id.title);
        date = findViewById(R.id.EventDay);
        comment = findViewById(R.id.comment);
        area = findViewById(R.id.large_area);
        entryTime = findViewById(R.id.entryTime);
//        member = findViewById(R.id.member);
        deadline = findViewById(R.id.eventstatus);
        String eventTag = eventInfoDTO.getEventTag();
        entry = findViewById(R.id.entry);

        //eventTagをメッセージに変換する
        String[] strArray = eventTag.split("");
        ViewItemDTO viewItemDTO = new ViewItemDTO();
        List<ViewItemDTO> tagMessageList = new ArrayList<ViewItemDTO>();

        for(int i = 0; i < strArray.length; i++) {
            switch (strArray[i]) {
                case "1":
                    viewItemDTO.setText("机に座ってガッツリと");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "2":
                    viewItemDTO.setText("密室からの脱出");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "3":
                    viewItemDTO.setText("街を歩き回って");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "4":
                    viewItemDTO.setText("遊園地や野球場で");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "5":
                    viewItemDTO.setText("短い時間で気軽に");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "6":
                    viewItemDTO.setText("アニメタイアップ");
                    tagMessageList.add(viewItemDTO);
                    break;
            }
        }


        //********************************************* 参加者 start *********************************************

        //*取得してきたデータをタグ形式で出力する*//
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.member);

        //--------------------------------flexBox Layout の調整-----------------------------------------------//
        // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
        FlexboxLayoutManager flexboxLayoutManager1 = new FlexboxLayoutManager(getApplicationContext());
        //どっち側に伸ばすか.
        flexboxLayoutManager1.setFlexDirection(FlexDirection.ROW);
        // 子Viewの並び方向へのViewの張り付き位置
        flexboxLayoutManager1.setJustifyContent(JustifyContent.FLEX_START);
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView1.setLayoutManager(flexboxLayoutManager1);

        //--------------------------------viewAdapter の調整-----------------------------------------------//

        final List<ViewItemDTO> memberList = new ArrayList<ViewItemDTO>();

        ViewItemDTO viewItemDTO1 = new ViewItemDTO();
        viewItemDTO1.setText("" + R.drawable.business);
        memberList.add(viewItemDTO1);
        ViewItemDTO viewItemDTO2 = new ViewItemDTO();
        viewItemDTO2.setText("" + R.drawable.business);
        memberList.add(viewItemDTO2);
//        //Firebaseより、写真を取得し、画面に表示
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + Common.uid);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserDTO user = dataSnapshot.getValue(UserDTO.class);
//
//                ViewItemDTO viewItemDTO1 = new ViewItemDTO();
//                viewItemDTO1.setText(user.getProfileImageUrl());
//                memberList.add(viewItemDTO1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
        ViewAdapterImageReadOnly viewAdapterReadOnly1 = new ViewAdapterImageReadOnly(memberList);
        // アダプターオブジェクトをセット
        recyclerView1.setAdapter(viewAdapterReadOnly1);

        //********************************************* 参加者 end *********************************************



        //********************************************* タグ start *********************************************
        //*取得してきたデータをタグ形式で出力する*//
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.event_tag);

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

        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
        ViewAdapterReadOnly viewAdapterReadOnly = new ViewAdapterReadOnly(tagMessageList);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(viewAdapterReadOnly);

        //********************************************* タグ end *********************************************



        Log.d("eventInfo", eventInfoDTO.getEventName());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users/" + eventInfoDTO.getEventerUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("use_name", dataSnapshot.getKey());
                UserDTO uid = dataSnapshot.getValue(UserDTO.class);
//                leader.setText("募集者：" + uid.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        title.setText(eventInfoDTO.getEventName());   //タイトル
        date.setText(eventInfoDTO.getEventDay()); //日程
        comment.setText(eventInfoDTO.getComment()); //コメント
        comment.setFocusable(false);
        area.setText(eventInfoDTO.getLargeArea()); //開催場所
        entryTime.setText(eventInfoDTO.getSmallArea()); //開催時間
//        deadline.setText("掲載期限：" + eventInfoDTO.getClosedDay());
//        member.setText("募集人数：" + eventInfoDTO.getMember());

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("/Group/" + id + "/eventAttendees");
        ref2.orderByChild("uid").equalTo(Common.uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("onDataChange", "1");
                Object uid = dataSnapshot.getValue();
                if (uid == null) {
                    entry.setEnabled(true);
                    return;
                } else {
                    entry.setEnabled(false);
                    return;
                }
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled", "2");
                // ...
            }
        });

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecruitmentDetailActivity.this);
                alertDialog.setTitle("確認");
                alertDialog.setMessage("このグループ参加しますか？");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestJoin();
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

//        //下部メニューボタンを押下したときの処理を記載
//        ImageView menu_bar_home = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_home);
//        ImageView menu_bar_event = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_event);
//        ImageView menu_bar_chat = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_chat);
//        ImageView menu_bar_mypage = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_mypage);

        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.menu_bar_mypage);

        menuClickListener menuClickListener = new menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);
    }

    private void requestJoin() {


//        //ユーザーのuid、ユーザー情報のデータベースリファレンス、引き継いできた画像へのurl、ファイル名を登録する
//        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Group/"+ id + "/eventAttendees").push();
        //Firebaseのデータベースへの初期登録処理（uid, username_edittext_register_text, profileImageUrl以外は初期値を登録を実施する）

        //ここでRegisterActivityの内部で別のclassを定義すると、FirebaseでDatabaseException:Found conflicting getters for nameのエラーになる。
        //解決策は、別のpackageに移すこと：https://stackoverflow.com/questions/47767636/found-conflicting-getters-for-namedatabase-exception
        Map<String, Object> insertUserId = new HashMap<>();
        insertUserId.put("uid", Common.uid);

        //addOnSuccessListenerは、帰ってくる引数がないときはVoid、それ以外は決められた変数を指定する？？
        ref.setValue(insertUserId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("RegisterEventAttendees", "Finally we save the user to Firebase Databese in event data");

                Toast myToast = Toast.makeText(
                        getApplicationContext(),
                        "参加申請を行いました!",
                        Toast.LENGTH_SHORT
                );
                myToast.show();
                entry.setEnabled(false);
//                //登録に成功した場合は、LatestMessagesActivityに遷移する
//                Intent intent = new Intent(getApplicationContext(), TopActivity.class);
//                //この一文を記載することで、元のログイン画面に戻れないようにする
//                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        });
//        String write = "";
//        StringBuilder sb = new StringBuilder();
//        sb.append(EVENT_ID_SEND + id);
//        sb.append(MEMBER_ID_SEND);
//        write = sb.toString();
//        Log.d("id", "write=" + write);
//        RequestJoinDAO requestJoinDAO = new RequestJoinDAO(REQUEST_JOIN, write);
//        requestJoinDAO.execute();
    }

    private List<ViewItemDTO> initViewItemDtoList() {
        //リストの配列　retを定義
        List<ViewItemDTO> ret = new ArrayList<ViewItemDTO>();

        for (int i = 0; i < tagName.size(); i++) {
            //ViewItemDTOのインスタンスを生成
            ViewItemDTO itemDto = new ViewItemDTO();
            itemDto.setText(tagName.get(i));

//          ret配列に、設定した文字列を追加
            ret.add(itemDto);
        }
        return ret;
    }

}
