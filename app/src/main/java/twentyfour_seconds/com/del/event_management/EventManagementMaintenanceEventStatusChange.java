package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.create_event.SearchNewEventDAO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;

public class EventManagementMaintenanceEventStatusChange extends CustomActivity {
    // 募集再開ボタン
    private Button RecruitmentResumedButton;
    // 募集中止ボタン
    private Button RecruitmentStopButton;
    // グループ解散ボタン
    private Button GroupDissolutionButton;
    //イベントステータス
    private String event_status;
    //イベントid
    private String eventUidStr;
    private int event_uid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance_event_status_change);

        // EventManagementListから引き継いできた情報を取得する
        Intent intent = getIntent();
        event_status = intent.getStringExtra("event_status");
        eventUidStr = intent.getStringExtra("event_uid");
        event_uid = Integer.parseInt(eventUidStr);
        final int ievent_status = Integer.parseInt(event_status);
        Log.d("", event_status);

        // ビューの変数を初期化する
        RecruitmentResumedButton = findViewById(R.id.Resumed);
        RecruitmentStopButton = findViewById(R.id.Stop);
        GroupDissolutionButton = findViewById(R.id.Dissolution);
        // イベント状態が"0"のとき、再開ボタン無効化
        if (ievent_status == 0) {
            RecruitmentResumedButton.setEnabled(false);
        }
        // イベント状態が"1"のとき、停止ボタン無効化
        if (ievent_status == 1) {
            RecruitmentStopButton.setEnabled(false);
        }

        //イベント再開ボタン
        RecruitmentResumedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //イベント状態を0に変更する
                MysqlAccess(0);
            }
        });

        //イベント停止ボタン
        RecruitmentStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //イベント状態を1に変更する
                MysqlAccess(1);
            }
        });

        GroupDissolutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //イベント状態を9に変更する
                MysqlAccess(9);
            }
        });
    }

    //データベースにアクセスするメソッド
    private void MysqlAccess(int event_status){
        StringBuilder sb = new StringBuilder();
        String write = "";
        sb.append("event_status=" + event_status);
        sb.append("&event_uid=" + event_uid);
        write = sb.toString();

        Log.d("write",write);

        final CountDownLatch latch = new CountDownLatch(1);

        EventStatusUpdateDAO eventStatusUpdateDAO = new EventStatusUpdateDAO(Common.EVENT_STATUS_UPDATE_URL, write, latch);
        eventStatusUpdateDAO.execute();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}