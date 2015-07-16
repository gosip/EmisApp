package ge.edu.freeuni.emis.emisapp.ui.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.adapters.ClassesPagerAdapter;
import ge.edu.freeuni.emis.emisapp.model.Semester;
import ge.edu.freeuni.emis.emisapp.ui.extras.SlidingTabLayout;
import ge.edu.freeuni.emis.emisapp.ui.fragments.PlaceHolderFrag;

public class ClassesActivity extends OnOffActionBarActivity {
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

    private Semester semester;
    private App app;
    private int semesterIdx, currClassIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        if (savedInstanceState == null) {
            semesterIdx = getIntent().getIntExtra(getString(R.string.semester_key), 0);
            currClassIdx = getIntent().getIntExtra(getString(R.string.class_key), 0);
        } else {
            semesterIdx = savedInstanceState.getInt(getString(R.string.semester_key));
            currClassIdx = savedInstanceState.getInt(getString(R.string.class_key));
        }

        app = (App) getApplication();
        semester = app.getSemesterList().get(semesterIdx);

        initUI();
    }

    private void initUI() {
        initActionBar();
        tabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(new ClassesPagerAdapter(getSupportFragmentManager(),
                semester.getClasses(), semesterIdx));
        tabLayout.setViewPager(viewPager);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.theme_color);
            }
        });

        viewPager.setCurrentItem(currClassIdx);

    }

    private void initActionBar() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.theme_color)
        ));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Semester " + semester.getNumSemester());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_classes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.class_key), viewPager.getCurrentItem());
        outState.putInt(getString(R.string.semester_key), semesterIdx);
    }
}
