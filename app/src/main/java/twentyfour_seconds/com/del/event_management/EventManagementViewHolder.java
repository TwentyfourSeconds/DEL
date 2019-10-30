package twentyfour_seconds.com.del.event_management;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import twentyfour_seconds.com.del.R;

//RecyclerListViewHolderクラス
//概要：ビューホルダクラスは、画面部品を保持するクラス。このクラスを通じて、部品のデータを管理する。（今回は、TextViewのみ）

public class EventManagementViewHolder extends RecyclerView.ViewHolder{

    //row_event_managementの一行分の画面定義
    public TextView event_name;
    public TextView area;
    public TextView place;
    public TextView event_day;
    public TextView inviteTag;
    public TextView eventstatus;

    //コンストラクタ
    public EventManagementViewHolder(View EventEntryItemView) {
        //親クラスのコンストラクタの呼び出し
        super(EventEntryItemView);
        //引数で渡されたitemViewがない場合の処理（保険）
        if(EventEntryItemView!=null) {
            //引数で渡された一行分の画面部品から、表示に使われる部品を取得。
            this.event_name = EventEntryItemView.findViewById(R.id.event_name);
            this.area = EventEntryItemView.findViewById(R.id.area);
            this.place = EventEntryItemView.findViewById(R.id.place);
            this.event_day = EventEntryItemView.findViewById(R.id.event_day);
            this.inviteTag = EventEntryItemView.findViewById(R.id.inviteTag);
            this.eventstatus = EventEntryItemView.findViewById(R.id.eventstatus);
        }
    }

    public TextView getTextItem() {
        return event_name;
    }
}
