package ge.edu.freeuni.emis.emisapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.ui.fragments.ClassDetailedInfoFragment;
import ge.edu.freeuni.emis.emisapp.ui.fragments.PlaceHolderFrag;

/**
 * Created by giorgi on 7/8/15.
 */
public class ClassesPagerAdapter extends FragmentPagerAdapter {
    private List<Class> classList;
    private int semesterIdx;
    public ClassesPagerAdapter(FragmentManager fm, List<Class> classList, int semesterIdx) {
        super(fm);
        this.classList = classList;
        this.semesterIdx = semesterIdx;
    }

    @Override
    public Fragment getItem(int i) {
        return ClassDetailedInfoFragment.newInstance(semesterIdx, i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String ret = classList.get(position).getClassName();

        if (ret.length() > 20) {
            ret = ret.substring(0, 17) + "...";
        }

        return ret;
    }

    @Override
    public int getCount() {
        return classList.size();
    }
}
