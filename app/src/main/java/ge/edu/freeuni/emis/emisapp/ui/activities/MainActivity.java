package ge.edu.freeuni.emis.emisapp.ui.activities;

import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.emis.emisapp.App;
import ge.edu.freeuni.emis.emisapp.R;
import ge.edu.freeuni.emis.emisapp.adapters.DrawerListAdapter;
import ge.edu.freeuni.emis.emisapp.ui.DrawerItem;
import ge.edu.freeuni.emis.emisapp.ui.fragments.BSTranscriptFragment;
import ge.edu.freeuni.emis.emisapp.ui.fragments.PlaceHolderFrag;
import ge.edu.freeuni.emis.emisapp.ui.fragments.SettingsFragment;
import ge.edu.freeuni.emis.emisapp.ui.fragments.StudentInfoFragment;
import ge.edu.freeuni.emis.emisapp.ui.fragments.TuitionCardFragment;


public class MainActivity extends OnOffActionBarActivity {

    public static final String FRAGMENT_POSITION_EXTRA = "E.T.";

    public static final int STUDENT_INFO_FRAGMENT = 0;
    public static final int TUITION_CARD_FRAGMENT = 1;
    public static final int TRANSCRIPT_FRAGMENT = 2;
    public static final int SETTINGS_FRAGMENT = 3;

    private App app;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private List<DrawerItem> drawerItemList;

    private CharSequence drawerTitle;
    private CharSequence title;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplication();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.theme_color)
        ));

        initDrawer();
        int fragmentByPosition = getIntent().getIntExtra(FRAGMENT_POSITION_EXTRA, -1);
        Log.i("TAG", "" + fragmentByPosition);
        if (fragmentByPosition != -1) {
            getIntent().putExtra(FRAGMENT_POSITION_EXTRA, -1);
            selectItem(fragmentByPosition);
        }
        else if (savedInstanceState == null)
            selectItem(0);

    }

    private void initDrawer() {
        title = drawerTitle = getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerItemList = new ArrayList<DrawerItem>();

        String[] drawerItems = getResources().getStringArray(R.array.drawer_items);
        TypedArray imgs = getResources().obtainTypedArray(R.array.drawer_icons);

        for (int i = 0; i < drawerItems.length; i++) {
            drawerItemList.add(new DrawerItem(imgs.getResourceId(i, -1), drawerItems[i]));
        }

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(title);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerList.setAdapter(new DrawerListAdapter(this, drawerItemList));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(this.title);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;
        switch (position) {
            case STUDENT_INFO_FRAGMENT: fragment = new StudentInfoFragment(); break;
            case TUITION_CARD_FRAGMENT: fragment = new TuitionCardFragment(); break;
            case TRANSCRIPT_FRAGMENT: fragment = new BSTranscriptFragment(); break;
            case SETTINGS_FRAGMENT: fragment = new SettingsFragment(); break;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        setTitle(drawerItemList.get(position).getItemText());
        drawerLayout.closeDrawer(drawerList);
    }
}
