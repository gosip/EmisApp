package ge.edu.freeuni.emis.emisapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.ui.fragments.PlaceHolderFrag;

/**
 * Created by giorgi on 7/8/15.
 */
public class ClassesPagerAdapter extends FragmentPagerAdapter {
    private List<Class> classList;
    public ClassesPagerAdapter(FragmentManager fm, List<Class> classList) {
        super(fm);
        this.classList = classList;
    }

    @Override
    public Fragment getItem(int i) {

        return new PlaceHolderFrag();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String ret = classList.get(position).getClassName();

        if (ret.length() > 30) {
            ret = ret.substring(0, 27) + "...";
        }

        return ret;
    }

    @Override
    public int getCount() {
        return classList.size();
    }
}
