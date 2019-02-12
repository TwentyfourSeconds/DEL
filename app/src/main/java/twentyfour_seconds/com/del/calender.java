package twentyfour_seconds.com.del;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class calender extends DialogFragment {
    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // 今日の日付のカレンダーインスタンスを取得
        //  Calendarクラスは演算子newではなく、 getInstanceメソッドを呼ぶことにより、オブジェクトを生成します。
        final Calendar calendar = Calendar.getInstance();

        //DatePickerDialog:ユーザーが日付または時刻を選択できるようにあらかじめ定義された UI を含むダイアログ。
        //DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth)
        //Overrideするのは、OnDateSetListener

        // ダイアログ生成  DatePickerDialogのBuilderクラスを指定してインスタンス化します
        DatePickerDialog dateBuilder = new DatePickerDialog(
                //getActivity()とは、現在Fragmentと関連付けられているActivityを返すメソッドになります。
                getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 選択された年・月・日を整形 ※月は0-11なので+1している
                        String dateStr = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                        // MainActivityのインスタンスを取得
                        EventCreate EventCreateActivity = (EventCreate) getActivity();
                        EventCreateActivity.setTextView(dateStr);
                    }
                },
                calendar.get(Calendar.YEAR), // 初期選択年
                calendar.get(Calendar.MONTH), // 初期選択月
                calendar.get(Calendar.DAY_OF_MONTH) // 初期選択日
        );

        // dateBulderを返す
        return dateBuilder;
    }
}
