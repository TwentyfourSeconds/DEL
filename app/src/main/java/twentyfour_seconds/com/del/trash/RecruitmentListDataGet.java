package twentyfour_seconds.com.del.trash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.Common;

public class RecruitmentListDataGet extends AppCompatActivity implements AbsListView.OnScrollListener {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_list);

        //メッセージ検索からくる場合
        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータを取得
        String searchWord = intent.getStringExtra("searchWord");

        final CountDownLatch latch = new CountDownLatch(1);
        //        DetectionDB ddb = new DetectionDB(searchWord, latch);
        event_info_event_name_search ddb = new event_info_event_name_search(searchWord, latch);
        ddb.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onScrollStateChanged(AbsListView v, int s) {
        //処理なし
    }

    @Override
    public void onScroll(AbsListView view,
                         int firstVisible, int visibleCount, int totalCount) {

        boolean loadMore = firstVisible + visibleCount >= totalCount;
        boolean countOver = totalCount < Common.total;

        if (loadMore && countOver) {
            Log.d("matusbi", "kitane");
//            progressBar.setVisibility(View.VISIBLE);
            count += visibleCount; // or any other amount

            final CountDownLatch latch = new CountDownLatch(1);

            event_info_event_name_search ddb = new event_info_event_name_search(count, latch);
            ddb.execute();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}