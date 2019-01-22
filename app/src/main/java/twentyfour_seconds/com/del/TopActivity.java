package twentyfour_seconds.com.del;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        //横スクロールに入るimageviewの横幅をプログラムより指定
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();

        //画像3枚の横幅を取得する
        final ImageView img12 = (ImageView) findViewById(R.id.imageView12);
        final ImageView img13 = (ImageView) findViewById(R.id.imageView13);
        final ImageView img14 = (ImageView) findViewById(R.id.imageView14);
        ViewGroup.LayoutParams params12 = img12.getLayoutParams();
        ViewGroup.LayoutParams params13 = img12.getLayoutParams();
        ViewGroup.LayoutParams params14 = img12.getLayoutParams();
        //画面サイズを取得
        Point realSize = new Point();
        disp.getRealSize(realSize);

        int realScreenWidth = realSize.x;
        int realScreenHeight = realSize.y;

        //  横幅のみ画面サイズに変更
        params12.width = realScreenWidth;
        img12.setLayoutParams(params12);
        params13.width = realScreenWidth;
        img13.setLayoutParams(params13);
        params14.width = realScreenWidth;
        img14.setLayoutParams(params14);
    }
}


