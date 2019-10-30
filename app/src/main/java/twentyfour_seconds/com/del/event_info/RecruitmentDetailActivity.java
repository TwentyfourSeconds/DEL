package twentyfour_seconds.com.del.event_info;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.R;
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
    private TextView location;
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
        area = findViewById(R.id.area);
        location = findViewById(R.id.location);
        member = findViewById(R.id.member);
        deadline = findViewById(R.id.eventstatus);
//        tag = findViewById(R.id.tag);
        entry = findViewById(R.id.entry);

        //*取得してきたデータをタグ形式で出力する*//
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

        Log.d("eventInfo", eventInfoDTO.getEventName());

        leader.setText("募集者：" + "test" + "さん");
        title.setText(eventInfoDTO.getTitle());
        date.setText("日程：" + eventInfoDTO.getEventDay());
        comment.setText(eventInfoDTO.getComment());
        comment.setFocusable(false);
        area.setText("開催地：" + eventInfoDTO.getLargeArea());
        location.setText("開催場所：" + eventInfoDTO.getSmallArea());
        deadline.setText("掲載期限：" + eventInfoDTO.getClosedDay());
        member.setText("募集人数：" + eventInfoDTO.getMember());


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


        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.menu_bar).findViewById(R.id.menu_bar_mypage);

        menuClickListener menuClickListener = new menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);
    }

    private void requestJoin() {
        String write = "";
        StringBuilder sb = new StringBuilder();
        sb.append(EVENT_ID_SEND + id);
        sb.append(MEMBER_ID_SEND);
        write = sb.toString();
        Log.d("id", "write=" + write);
        RequestJoinDAO requestJoinDAO = new RequestJoinDAO(REQUEST_JOIN, write);
        requestJoinDAO.execute();
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
