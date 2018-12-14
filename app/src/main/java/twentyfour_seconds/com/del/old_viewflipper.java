package twentyfour_seconds.com.del;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class old_viewflipper extends AppCompatActivity {
    //テスト
    public ScrollView Scrollview;
    public ConstraintLayout ConstraintLayout;
    //test
    public ViewFlipper viewFlipper;
    //フリックのＸ位置
    private float X,firstX;
    // フリックの遊び部分（最低限移動しないといけない距離）
    private float adjust = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        ConstraintLayout = findViewById(R.id.constraintinscroll);
        Scrollview = findViewById(R.id.scrollView4);
    }
//        ScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("RelativeLayout");
//                return false;
//            }
//        });

//        ConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("試し" , "ConstraintLayout");
//                return false;
//            }
//        });
//
//        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
//                                           @Override
//                                           public boolean onTouch(View v, MotionEvent event) {
//                                               switch (event.getAction()) {
//                                                   case MotionEvent.ACTION_DOWN://タッチ押下
//                                                       firstX = event.getX();
//                                                       Log.d("X", "" + firstX);
//                                                       break;
//                                                   case MotionEvent.ACTION_UP://指を持ち上げた場合
//                                                       X = event.getX();
//                                                       Log.d("X", "" + X);
//                                                       // Handling left to right screen swap.
//                                                       if (X - firstX > adjust) {
//                                                           // Next screen comes in from left.
//                                                           viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left));
//                                                           // Current screen goes out from right.
//                                                           viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_right));
//
//                                                           // Display next screen.
//                                                           viewFlipper.showNext();
//                                                       }
//
//                                                       // Handling right to left screen swap.
//                                                       else if (firstX - X > adjust) {
//                                                           // Next screen comes in from right.
//                                                           viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right));
//                                                           // Current screen goes out from left.
//                                                           viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left));
//
//                                                           // Display previous screen.
//                                                           viewFlipper.showPrevious();
//                                                       }
//                                                       break;
//                                               }
//                                               return false;
////            }
////        });
//                                           }
//                                       });

    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
//        ScrollView ScrollView = findViewById(R.id.scrollView4);
//        ScrollView.requestDisallowInterceptTouchEvent(true);
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN://タッチ押下
                firstX = touchevent.getX();
                Log.d("X", ""+ firstX);
                break;
            case MotionEvent.ACTION_UP://指を持ち上げた場合
                X = touchevent.getX();
                Log.d("X", ""+ X);
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