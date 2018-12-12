package twentyfour_seconds.com.del;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class TopActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    //フリックのＸ位置
    private float X,firstX;
    // フリックの遊び部分（最低限移動しないといけない距離）
    private float adjust = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
    }

    // Using the following method, we will handle all screen swaps.
    public boolean dispatchTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN://タッチ押下
                firstX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP://指を持ち上げた場合
                X = touchevent.getX();

                // Handling left to right screen swap.
                if (X - firstX > adjust) {
                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left));
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_right));

                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                else if (firstX - X > adjust) {
                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right));
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left));

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }
}