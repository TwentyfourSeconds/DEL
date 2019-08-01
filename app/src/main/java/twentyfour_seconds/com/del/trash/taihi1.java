//package twentyfour_seconds.com.del;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.android.flexbox.FlexDirection;
//import com.google.android.flexbox.FlexboxLayout;
//
//import twentyfour_seconds.com.del.R;
//
//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
//
//
////①DBを読み込む。
////②タグ情報の数字を取得
////③タグ情報を文字列に変換
////④タグ情報を一覧に出力（今回の仕組み？）
////
//
//public class EventCreate4 extends AppCompatActivity {
//
//    FlexboxLayout flexboxlayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.eventcreate4);
//        //部品を取得
//        flexboxlayout = findViewById(R.id.flexboxlayout);
//
//        Button add = findViewById(R.id.add);
//        Button delete = findViewById(R.id.delete);
//
//        addButtonClickListener addButtonClickListener = new addButtonClickListener();
//        add.setOnClickListener(addButtonClickListener);
//        deleteButtonClickListener deleteButtonClickListener = new deleteButtonClickListener();
//        delete.setOnClickListener(deleteButtonClickListener);
//
//    }
//
//
//
//    private void write(){
//        FlexboxLayout right = createRightFlexbox();
//        TextView rightText = createTextView();
//
//        right.addView(rightText);
//        flexboxlayout.addView(right);
//    }
//
//    private FlexboxLayout createRightFlexbox() {
//
//        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
////        params.setFlexBasisPercent(0.3f);
////        params.setOrder(2);
//
//        FlexboxLayout flexbox = new FlexboxLayout(this);
//        flexbox.setLayoutParams(params);
//        flexbox.setFlexDirection(FlexDirection.ROW);
//
//        return flexbox;
//    }
//
//    private TextView createTextView() {
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
//
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(params);
//
//        textView.setText("こんにちは");
//
//        return textView;
//    }
//
//        //カレンダークリックイベント（カレンダーフラグメントを実行）
//        public class addButtonClickListener implements View.OnClickListener {
//            @Override
//            // クリックしたらダイアログを表示する処理
//
//            //レイアウトパラメーター作成
//            public void onClick(View view) {
//                write();
//            }
//        }
//
//
//        //カレンダークリックイベント（カレンダーフラグメントを実行）
//    public class deleteButtonClickListener implements View.OnClickListener {
//        @Override
//        // クリックしたらダイアログを表示する処理
//        public void onClick(View view) {
//            }
//        }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////public class EventCreate4 extends AppCompatActivity {
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.eventcreate4);
////
////        Button add = findViewById(R.id.add);
////        Button delete = findViewById(R.id.delete);
////
////        addButtonClickListener addButtonClickListener = new addButtonClickListener();
////        add.setOnClickListener(addButtonClickListener);
////        deleteButtonClickListener deleteButtonClickListener = new deleteButtonClickListener();
////        delete.setOnClickListener(deleteButtonClickListener);
////    }
////
////
////
////        //カレンダークリックイベント（カレンダーフラグメントを実行）
////        public class addButtonClickListener implements View.OnClickListener {
////            @Override
////            // クリックしたらダイアログを表示する処理
////
////            //レイアウトパラメーター作成
////            public void onClick(View view) {
////
////                //
////
////
////
////
////
////
////                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
////                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
////                        FlexboxLayout.LayoutParams.WRAP_CONTENT);
////
////                FlexboxLayout flexbox = new FlexboxLayout(getApplicationContext());
////                flexbox.setLayoutParams(params);
////                flexbox.setFlexDirection(FlexDirection.ROW);
////
////                ViewGroup.LayoutParams paramsA = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
////
////                TextView textView = new TextView(getApplicationContext());
////                textView.setLayoutParams(paramsA);
////                textView.setText("テスト");
////
////                flexbox.addView(textView);
////
////                }
////            }
////
////
////    //カレンダークリックイベント（カレンダーフラグメントを実行）
////    public class deleteButtonClickListener implements View.OnClickListener {
////        @Override
////        // クリックしたらダイアログを表示する処理
////        public void onClick(View view) {
////            }
////        }
////    }