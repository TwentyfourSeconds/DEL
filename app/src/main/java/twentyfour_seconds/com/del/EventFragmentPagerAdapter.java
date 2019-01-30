package twentyfour_seconds.com.del;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class EventFragmentPagerAdapter extends FragmentPagerAdapter {

    //タブ名称を登録
    private CharSequence[] tabTitles = {"イベント管理", "イベント作成"};

    public EventFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //タブ名称を登録する
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    //フラグメントをアダプターに登録する
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EventCreateflagment();
            case 1:
                return new Eventkanriflagment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
