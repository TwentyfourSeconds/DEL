package twentyfour_seconds.com.del.event_management;

import android.os.Bundle;
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

        // ビューの変数を初期化する
        RecruitmentResumedButton = findViewById(R.id.Resumed);
        RecruitmentStopButton = findViewById(R.id.Stop);
        GroupDissolutionButton = findViewById(R.id.Dissolution);

        RecruitmentResumedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンが押されたら無効化
                RecruitmentResumedButton.setEnabled(false);
                RecruitmentStopButton.setEnabled(false);
                GroupDissolutionButton.setEnabled(false);
            }
        });
    }
}