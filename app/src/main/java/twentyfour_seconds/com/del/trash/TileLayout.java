//package twentyfour_seconds.com.del.trash;
//
//import android.content.Context;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.constraintlayout.Constraints;
//import android.util.AttributeSet;
//
////以下の機能を持つViewGroupを実装する
//
////①子ビューを追加した際、一定の間隔を持って追加する（間隔は20dx）
////②追加した際、
//
//
//public class TileLayout extends ConstraintLayout {
//
//    public TileLayout(Context context) {
//        this(context, null);
//    }
//
//    public TileLayout(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public TileLayout(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//
//    }
//
//    //①画面の横幅を取得
//    //②アイテムの長さを取得
//    //③アイテムを横に追加する
//    //④画面の幅-アイテムの幅
//    //→超えたら下の列に出す
//
//    //⑤アイテムをクリックすると消える
//
//
//
//    public void saihai(){
//
//    }
//
//
//}
//
//
//
//
////
////public class TileLayout extends ViewGroup {
////
////public TileLayout(Context context){
////    this(context, null);
////    }
////
////    public TileLayout(Context context, AttributeSet attrs){
////        this(context, attrs, 0);
////    }
////
////    public TileLayout(Context context, AttributeSet attrs, int defStyle){
////        super(context, attrs, defStyle);
////    }
////
////    @Override
////    //すべてのViewGroupで子ビューを実装する
////    protected void onLayout(boolean changed, int l, int t, int r, int b){
////
////    }
////
////
////
////
////
////
////    @Override
////    //すべてのViewのサイズを計算する
////    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
////        //親のViewGroupによって割り当てられたサイズとレイアウトモードを渡たす。
////        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
////        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
////
////        if(widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY){
////            //レイアウトモードがEXACTLY以外の時はエラーにする
////            throw new IllegalStateException("Must measure width an exact width");
////        }
////
////        //このViewGroupに割り当てられているサイズを取得する
////        final int widthsize = MeasureSpec.getSize(widthMeasureSpec);
////        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
////
////        //このViewGroupのサイズをセットする
////        setMeasuredDimension(widthsize,heightSize);
////    }
////}
//
////public class EventCreateStep2 extends CustomActivity {
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.eventcreate_step2);
//
////        Button button = findViewById(R.id.button);
////        buttonClickListener carenderClickListener = new buttonClickListener();
////        button.setOnClickListener(carenderClickListener);
////    }
//
//
//
////    //タグボタンクリックイベント
////    public class buttonClickListener implements View.OnClickListener {
////        @Override
////        public void onClick(View view) {
////
////        int id = view.getId();
////        switch (id) {
////            case R.id.button:
////                //コードでレイアウトを生成＆セット
////                Button button1 = new Button(getBaseContext());
////                button1.setText("Button1");
////                LinearLayout linearLayout = findViewById(R.id.linearLayoutA);
////                linearLayout.addView(button1);
////                break;
////            }
////        }
////    }
////}