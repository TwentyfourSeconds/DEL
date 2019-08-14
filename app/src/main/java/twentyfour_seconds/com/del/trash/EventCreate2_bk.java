package twentyfour_seconds.com.del.trash;

import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.CustomActivity;


public class EventCreate2_bk extends CustomActivity {

    //前回押下したボタンのID
    int prevButtonID = 0;
    //前回押下したボタン
    Button prevButton;
    //前回押したボタンの退避
    Button prevButtontaihi;
    int viewGroupSize = 0;
    //ConstraintSetを生成してConstraintLayoutから制約を複製する
    ConstraintSet constraintSet = new ConstraintSet();
    //constraintlayoutを定義
    ConstraintLayout constraintLayout;
    //画面のマージン
    int margin = 10;
    //realScreenWidth
    int realScreenWidth;
    LinearLayout linearLayoutAA;
    LinearLayout linearLayoutAB;
    //itemの長さ
    int itemwidth;
    //新しく生成したときのid
    int tsukue1Viewid = 0;

    //臨時処理：5回目以降、削除のほうに進む
    int i = 0;
    //臨時処理：5回目以降、削除のほうに進む

    //変更後の画面幅
    int viewGroupSizeAAafter;
    int viewGroupSizeABafter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventcreate_step2);

//        現在のlayoutのサイズを取得
//        constraintLayout = findViewById(R.id.constraint);
        //ConstraintSetを生成してConstraintLayoutから制約を複製する
//        constraintSet.clone(constraintLayout);

        linearLayoutAA = findViewById(R.id.linearLayoutAA);
        linearLayoutAB = findViewById(R.id.linearLayoutAB);
        Button button = findViewById(R.id.tsukue);
        Button mattari = findViewById(R.id.mattari);
        Button ahureru = findViewById(R.id.ahureru);
        buttonClickListener carenderClickListener = new buttonClickListener();
        button.setOnClickListener(carenderClickListener);
        mattari.setOnClickListener(carenderClickListener);
        ahureru.setOnClickListener(carenderClickListener);
    }

    //タグボタンクリックイベント
    public class buttonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //画面サイズを取得
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display disp = wm.getDefaultDisplay();
            Point realSize = new Point();
            disp.getRealSize(realSize);
            int realScreenWidth = realSize.x;

            Log.d("realScreenWidth", realScreenWidth + "");

            int viewGroupSizeAA = linearLayoutAA.getWidth();
            int viewGroupSizeAB = linearLayoutAB.getWidth();
            Log.d("viewGroupSizeAA", viewGroupSizeAA + "");
            Log.d("viewGroupSizeAB", viewGroupSizeAB + "");

            int id = view.getId();

            //臨時処理：5回目以降、削除のほうに進む
            i = i +1;
            Log.d("i", i + "");
            if(i<10){
                //臨時処理：5回目以降、削除のほうに進む

            //追加するときの話
            switch (id) {
                case R.id.tsukue:
                    itemwidth = findViewById(R.id.tsukue).getWidth();
                    if(realScreenWidth > viewGroupSizeAA + itemwidth) {
                        //コードでレイアウトを生成＆セット
                        Button tsukue1 = new Button(getBaseContext());
                        tsukue1Viewid = tsukue1.generateViewId();
                        tsukue1.setId(tsukue1Viewid);
                        //テキストを設定して画面に表示
                        tsukue1.setText("＋机に座ってガッツリと");
                        linearLayoutAA.addView(tsukue1);
                        Log.d("linearLayoutAA", "AA");
                        break;
                    }else{
                        if(realScreenWidth > viewGroupSizeAB + itemwidth){
                        Button tsukue1 = new Button(getBaseContext());
                        tsukue1Viewid = tsukue1.generateViewId();
                        tsukue1.setId(tsukue1Viewid);
                        //テキストを設定して画面に表示
                        tsukue1.setText("＋机に座ってガッツリと");
                        linearLayoutAB.addView(tsukue1);
                        Log.d("linearLayoutAB", "AB");
                        break;
                        }else{
                            Log.d("枠ないよ", "枠ないよ");
                        }
                    }
                    break;
                case R.id.mattari:
                    itemwidth = findViewById(R.id.mattari).getWidth();
                    Log.d("itemwidth", itemwidth + "");
                    //計算して、長かったら、下のレイアウトに出す
                    if(realScreenWidth > viewGroupSizeAA + itemwidth) {
                        //コードでレイアウトを生成＆セット
                        Button mattari = new Button(getBaseContext());
                        //テキストを設定して画面に表示
                        mattari.setText("＋まったりとやろう");
                        linearLayoutAA.addView(mattari);
                        Log.d("linearLayoutAA", "AA");
                        break;
                    }else{
                        if(realScreenWidth > viewGroupSizeAB + itemwidth){
                            Button mattari = new Button(getBaseContext());
                            //テキストを設定して画面に表示
                            mattari.setText("＋まったりとやろう");
                            linearLayoutAB.addView(mattari);
                            Log.d("linearLayoutAB", "AB");
                            break;
                        }else{
                            Log.d("枠ないよ", "枠ないよ");
                        }
                    }
                    break;
                case R.id.ahureru:
                    itemwidth = findViewById(R.id.ahureru).getWidth();
                    Log.d("itemwidth", itemwidth + "");
                    //計算して、長かったら、下のレイアウトに出す
                    if(realScreenWidth > viewGroupSizeAA + itemwidth) {
                        //コードでレイアウトを生成＆セット
                        Button ahureru = new Button(getBaseContext());
                        //テキストを設定して画面に表示
                        ahureru.setText("＋あふれる");
                        linearLayoutAA.addView(ahureru);
                        Log.d("linearLayoutAA", "AA");
                        break;
                    }else{
                        if(realScreenWidth > viewGroupSizeAB + itemwidth){
                            Button ahureru = new Button(getBaseContext());
                            //テキストを設定して画面に表示
                            ahureru.setText("＋あふれる");
                            linearLayoutAB.addView(ahureru);
                            Log.d("linearLayoutAB", "AB");
                            break;
                        }else{
                            Log.d("枠ないよ", "枠ないよ");
                        }
                    }
                    break;
                }
                //テスト箇所
            }else {
                //テスト箇所

                //削除するほう
                switch (id) {
                    case R.id.tsukue:
                        Button tsukuedelete = findViewById(tsukue1Viewid);
                        tsukuedelete.setVisibility(View.GONE);
                        //リスナ呼ぶならこれ
//                        deleteClickListener deleteClickListener = new deleteClickListener();
//                        tsukuedelete.setOnClickListener(deleteClickListener);
                        //下の列の配列要素を取得

                        //変更した後の長さを取りたいため、ViewTreeObserverを使う

                        ViewTreeObserver viewGroupSizeAAafterwidth = linearLayoutAA.getViewTreeObserver();
                        ViewTreeObserver viewGroupSizeABafterwidth = linearLayoutAB.getViewTreeObserver();

                        viewGroupSizeAAafterwidth.addOnGlobalLayoutListener(
                                new ViewTreeObserver.OnGlobalLayoutListener(){
                                    @Override
                                    public void onGlobalLayout()
                                    {
                                        viewGroupSizeAAafter = linearLayoutAA.getWidth();
                                        viewGroupSizeABafter = linearLayoutAB.getWidth();

                                        Log.d("addOnGlobalLayoutListener : ", "ボタン幅　AA = " + linearLayoutAA.getWidth());
                                        Log.d("addOnGlobalLayoutListener : ", "ボタン幅  AB = " + linearLayoutAB.getWidth());
                                    }
                                });

                        Log.d("viewGroupSizeAA", viewGroupSizeAA + "" );
                        Log.d("viewGroupSizeAAafter", viewGroupSizeAAafter + "" );
                        Log.d("viewGroupSizeAB", viewGroupSizeAB + "" );
                        Log.d("viewGroupSizeABafter", viewGroupSizeABafter + "" );

                        if(viewGroupSizeAAafter == viewGroupSizeAA){
                            //LinearLayoutAAは変更なし
                            if(viewGroupSizeABafter == viewGroupSizeAB){
                             //LinearLayoutABは変更なし
                            }else{
                                //LinearLayoutAB変更有り
                            }
                        }else{
                            //LinearLayoutAA変更有り
                                //下段の配列の最初のviewのを取得
                            Log.d("ここにきた","ここにきた");
                                Button buttontranslate = (Button)linearLayoutAB.getChildAt(0);
                                //ABから削除、AAに追加
                                buttontranslate.generateViewId();
//                              buttontranslate.setVisibility(View.GONE);
                                linearLayoutAB.removeView(buttontranslate);
                                linearLayoutAA.addView(buttontranslate);
                            }
                        break;
                    }
                }
            }
        //テスト箇所
    }
    //テスト箇所
}


//
//    //タグボタンクリックイベント
//    public class deleteClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
////            int id = view.getId();
////            switch (id) {
////                case R.id.tsukuedelete:
//                    Button tsukuedelete = findViewById(tsukue1Viewid);
//                    tsukuedelete.setVisibility(View.GONE);
//            }
//        }

//public class EventCreate2_bk extends CustomActivity {
//
//    //前回押下したボタンのID
//    int prevButtonID = 0;
//    //前回押下したボタン
//    Button prevButton;
//    //前回押したボタンの退避
//    Button prevButtontaihi;
//    int viewGroupSize = 0;
//    //ConstraintSetを生成してConstraintLayoutから制約を複製する
//    ConstraintSet constraintSet = new ConstraintSet();
//    //constraintlayoutを定義
//    ConstraintLayout constraintLayout;
//    //画面のマージン
//    int margin = 10;
//    //realScreenWidth
//    int realScreenWidth;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.eventcreate_step2);
//
//        //現在のlayoutのサイズを取得
//        constraintLayout = findViewById(R.id.constraint);
//        //ConstraintSetを生成してConstraintLayoutから制約を複製する
//        constraintSet.clone(constraintLayout);
//        Button button = findViewById(R.id.tsukue);
//        Button mattari = findViewById(R.id.mattari);
//        buttonClickListener carenderClickListener = new buttonClickListener();
//        button.setOnClickListener(carenderClickListener);
//        mattari.setOnClickListener(carenderClickListener);
//    }
//
//    //タグボタンクリックイベント
//    public class buttonClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//
//            //画面サイズを取得
//            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//            Display disp = wm.getDefaultDisplay();
//            Point realSize = new Point();
//            disp.getRealSize(realSize);
//            int realScreenWidth = realSize.x;
//
//            Log.d("realScreenWidth", realScreenWidth + "");
//
//            int viewGroupSize = constraintLayout.getWidth();
//            Log.d("viewGroupSize", viewGroupSize + "");
//
//            int id = view.getId();
//            switch (id) {
//                case R.id.tsukue:
//                    //コードでレイアウトを生成＆セット
//                    Button tsukue1 = new Button(getBaseContext());
//                    //テキストを設定して画面に表示
//                    tsukue1.setText("＋机に座ってガッツリと");
//                    constraintLayout.addView(tsukue1);
//
//                    // 動的に追加するTextViewの横幅 + 左右のマージンサイズが追加するUIのサイズ
//                    int itemSize = tsukue1.getWidth() + (margin * 2);
//                    Log.d("itemSize", itemSize + "");
//
//                    // Layoutのサイズ - (これまで追加したUIのトータルサイズ + 今回追加のUIサイズ)
//                    // がゼロ以下 --> つまり、もう右にUIが入りきらない場合、改行してUIを追加
//                    if (realScreenWidth - (viewGroupSize + itemSize) < 0) {
//                        viewGroupSize = 0;
//                        Log.i("改行", "改行");
//                    } else {
//                        //初回の場合
//                        if (prevButtonID == 0) {
//                            prevButton = findViewById(R.id.tsukue);
//                            prevButtonID = prevButton.getId();
////                            viewGroupSize = viewGroupSize + itemSize;
//                            Log.i("初回", "初回");
//                            Log.i("prevButton", prevButton + "");
//                            Log.i("prevButtonID", prevButtonID + "");
//                        } else {
//                            //2回目以降の場合
//                            // 上辺を前回UIに揃えて、前回UIの右に配置
//                            prevButtonID = prevButton.getId();
//                            Log.i("prevButtonID", prevButtonID + "");
//                            constraintSet.connect(prevButton.getId(), ConstraintSet.TOP, prevButton.getId(), ConstraintSet.BOTTOM, 8);
//                            constraintSet.applyTo(constraintLayout);
////                        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
//                            viewGroupSize = viewGroupSize + itemSize;
//                            Log.i("二回目", "二回目");
//                        }
//                    }
//                    break;
//            }
//        }
//    }
//}
//









//過去の履歴
//public class EventCreate2_bk extends CustomActivity {
//
//    int previd = 0;
//    Button prevbutton;
//    int viewGroupSize = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.eventcreate_step2);
//
//        Button button = findViewById(R.id.tsukue);
//        Button mattari = findViewById(R.id.mattari);
//        buttonClickListener carenderClickListener = new buttonClickListener();
//        button.setOnClickListener(carenderClickListener);
//        mattari.setOnClickListener(carenderClickListener);
//    }
//
//    //タグボタンクリックイベント
//    public class buttonClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//
//            //画面サイズを取得
//            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//            Display disp = wm.getDefaultDisplay();
//            Point realSize = new Point();
//            disp.getRealSize(realSize);
//            int realScreenWidth = realSize.x;
//
//            Log.d("realScreenWidth", realScreenWidth + "");
//
//            //現在のlayoutのサイズを取得
//            ConstraintLayout constraintLayout = findViewById(R.id.constraint);
////          int viewGroupSize = constraintLayout.getWidth();
//            Log.d("viewGroupSize", viewGroupSize + "");
//
//            int margin = 10;
//
//            int id = view.getId();
//            switch (id) {
//                case R.id.tsukue:
//
//                    //コードでレイアウトを生成＆セット
//                    Button tsukue1 = new Button(getBaseContext());
//
//                    //ViewのLayoutParamsを取得
//                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tsukue1.getLayoutParams();
//
//                    //テキストを設定して画面に表示
//                    tsukue1.setText("＋机に座ってガッツリと");
//                    constraintLayout.addView(tsukue1);
//
//                    // 動的に追加するTextViewの横幅 + 左右のマージンサイズが追加するUIのサイズ
//                    int itemSize = tsukue1.getWidth() + (margin * 2);
//
//                    Log.d("itemSize", itemSize + "");
//
//                    // Layoutのサイズ - (これまで追加したUIのトータルサイズ + 今回追加のUIサイズ)
//                    // がゼロ以下 --> つまり、もう右にUIが入りきらない場合、改行してUIを追加
//                    if (realScreenWidth - (viewGroupSize + itemSize) < 0) {
//                        viewGroupSize = 0;
//                        tsukue1.setLayoutParams(params);
//                        Log.i("改行", "改行");
//                    } else {
//                        //初回の場合
//                        if (previd == 0) {
//                            previd = tsukue1.getId();
//                            viewGroupSize = viewGroupSize + itemSize;
//                            Log.i("初回", "初回");
//                            Log.i("previd", previd + "");
//                        } else {
//                            //2回目以降の場合
//                            // 上辺を前回UIに揃えて、前回UIの右に配置
//                            params.startToEnd = previd;
//                            params.topToBottom = previd;
//                            tsukue1.setLayoutParams(params);
////                        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
//                            previd = tsukue1.getId();
//                            viewGroupSize = viewGroupSize + itemSize;
//                            Log.i("二回目", "二回目");
//                        }
//                    }
//                    break;
//
//                case R.id.mattari:
//
//                    //コードでレイアウトを生成＆セット
//                    Button mattari1 = new Button(getBaseContext());
//
//                    //ViewのLayoutParamsを取得
//                    ConstraintLayout.LayoutParams mattariparams = (ConstraintLayout.LayoutParams) mattari1.getLayoutParams();
//
//                    // ConstraintSetを生成してConstraintLayoutから制約を複製する
////                  ConstraintSet constraintSet = new ConstraintSet();
////                  constraintSet.clone(constraintLayout);
//
//                    //テキストを設定して画面に表示
//                    mattari1.setText("＋まったりと");
//                    constraintLayout.addView(mattari1);
//
//                    // 動的に追加するTextViewの横幅 + 左右のマージンサイズが追加するUIのサイズ
//                    itemSize = mattari1.getWidth() + (margin * 2);
//                    Log.d("itemSize", itemSize + "");
//
//                    Log.i("previd", previd + "");
//
//                    // Layoutのサイズ - (これまで追加したUIのトータルサイズ + 今回追加のUIサイズ)
//                    // がゼロ以下 --> つまり、もう右にUIが入りきらない場合、改行してUIを追加
//                    if (realScreenWidth - (viewGroupSize + itemSize) < 0) {
//                        viewGroupSize = 0;
////                      params.startToEnd = prevbutton.getId();
////                      params.topToBottom = prevbutton.getId();
//                        mattari1.setLayoutParams(mattariparams);
//                        Log.i("改行", "改行");
//                    } else {
//                        //初回の場合
//                        if (previd == 0) {
//                            previd = mattari1.getId();
//                            viewGroupSize = viewGroupSize + itemSize;
//                            Log.i("初回", "初回");
//                            Log.i("previd", previd + "");
//                        } else {
//                            //2回目以降の場合
//                            // 上辺を前回UIに揃えて、前回UIの右に配置
//                            mattariparams.startToEnd = previd;
//                            mattariparams.topToBottom = previd;
//                            mattari1.setLayoutParams(mattariparams);
////                          constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
//                            previd = mattari1.getId();
//                            viewGroupSize = viewGroupSize + itemSize;
//                            Log.i("二回目", "二回目");
//                        }
//                    }
//                }
//            }
//        }
//    }