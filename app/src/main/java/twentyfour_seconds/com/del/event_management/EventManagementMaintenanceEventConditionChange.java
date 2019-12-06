package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.create_event.EventCreate3;
import twentyfour_seconds.com.del.event_info.EventInfoDAO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;

public class EventManagementMaintenanceEventConditionChange extends CustomActivity {

    String event_id;

    private final String ID_SEND = "id=";
    private final String EVENT_URL = Common.ID_SEARCH_EVENT_URL;

    private EventInfoDTO eventInfoDTO = new EventInfoDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance_event_condition_change);

        //情報を取得
        Receivedata();

        //event_idより、該当するイベントの情報を取得
        //新DB用
        String write = "";
        StringBuilder sb = new StringBuilder();
        sb.append(ID_SEND + event_id);
        write = sb.toString();

        final CountDownLatch latch = new CountDownLatch(1);
        EventInfoDAO eventInfoDAO = new EventInfoDAO(EVENT_URL, write, eventInfoDTO, latch);
        eventInfoDAO.execute();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //各部品のidを取得
        EditText eventName = findViewById(R.id.EventName);
        Spinner  eventRegion = findViewById(R.id.EventRegion);
        EditText eventPlace = findViewById(R.id.EventPlace);
        TextView eventDay = findViewById(R.id.EventDay);
        Spinner recruitmentNumbers = findViewById(R.id.recruitmentNumbers);
        TextView limitDay = findViewById(R.id.LimitDay);







        eventInfoDTO.getTitle();





    }




    //EventManagementListから引き継いできた情報を取得する
    private void Receivedata(){
        Intent intent = getIntent();
        this.event_id = intent.getStringExtra("event_id");

        Log.d("", event_id);
    }
}
