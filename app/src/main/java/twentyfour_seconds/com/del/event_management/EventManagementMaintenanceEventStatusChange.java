package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.CustomActivity;

public class EventManagementMaintenanceEventStatusChange extends CustomActivity {
    // 募集再開ボタン
    private Button RecruitmentResumedButton;
    // 募集中止ボタン
    private Button RecruitmentStopButton;
    // グループ解散ボタン
    private Button GroupDissolutionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance_event_status_change);

        // EventManagementListから引き継いできた情報を取得する
            Intent intent = getIntent();
            String event_status;
            event_status = intent.getStringExtra("event_status");
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

        RecruitmentResumedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        RecruitmentStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}