package twentyfour_seconds.com.del.util;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import twentyfour_seconds.com.del.R;

//RecyclerListViewHolderクラス
//概要：ビューホルダクラスは、画面部品を保持するクラス。このクラスを通じて、部品のデータを管理する。（今回は、TextViewのみ）

public class RecyclerListViewHolder extends RecyclerView.ViewHolder {

    public TextView textItem;
    public ImageView imageItem;

    //コンストラクタ
    public RecyclerListViewHolder(View itemView) {
        //親クラスのコンストラクタの呼び出し
        super(itemView);
        //引数で渡されたitemViewがない場合の処理（保険）
        if(itemView!=null) {
            //引数で渡された一行分の画面部品から、表示に使われる部品を取得。
            this.textItem = itemView.findViewById(R.id.inviteTag);
            this.imageItem = itemView.findViewById(R.id.member_image);
        }
    }
//    //コンストラクタ
//    public RecyclerListViewHolder(TextView itemView) {
//        //親クラスのコンストラクタの呼び出し
//        super(itemView);
//        //引数で渡されたitemViewがない場合の処理（保険）
//        if(itemView!=null) {
//            //引数で渡された一行分の画面部品から、表示に使われる部品を取得。
//            this.textItem = itemView.findViewById(R.id.inviteTag);
//        }
//    }
//    //コンストラクタ
//    public RecyclerListViewHolder(ImageView imageItem) {
//        //親クラスのコンストラクタの呼び出し
//        super(imageItem);
//        //引数で渡されたitemViewがない場合の処理（保険）
//        if(imageItem!=null) {
//            //引数で渡された一行分の画面部品から、表示に使われる部品を取得。
//            this.imageItem = imageItem.findViewById(R.id.member_image);
//        }
//    }

    public TextView getTextItem() {
        return textItem;
    }
    public ImageView getImageItem() {
        return imageItem;
    }
}