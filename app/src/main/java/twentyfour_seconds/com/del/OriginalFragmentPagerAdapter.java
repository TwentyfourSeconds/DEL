package twentyfour_seconds.com.del;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class OriginalFragmentPagerAdapter extends FragmentPagerAdapter {

    private CharSequence[] tabTitles = {"イベント管理", "イベント作成"};

    public OriginalFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new flagment1();
            case 1:
                return new flagment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
