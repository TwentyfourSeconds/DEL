package twentyfour_seconds.com.del.management_event;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import twentyfour_seconds.com.del.entry_event.EventEntryFragment2;

public class EventFragmentPagerAdapter extends FragmentPagerAdapter {

    //タブ名称を登録
    private CharSequence[] tabTitles = {"イベント管理", "イベント参加"};

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
                //イベント管理を設定するFlagmentをセット
                return new EventManagementFragment();
            case 1:
                //イベント参加を設定するFlagmentをセット
                return new EventEntryFragment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
