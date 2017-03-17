package gank.douya.com.gank.model;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import gank.douya.com.gank.R;
import gank.douya.com.gank.net.entity.CategoryEntity;
import gank.douya.com.gank.utils.ActivityUtils;

import static gank.douya.com.gank.model.BaseDetailFragment.ARG_ITEM_ID;

/**
 * An activity representing a single Android detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BaseListActivity}.
 */
public class BaseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CategoryEntity.ResultsBean mItem;
    private FloatingActionButton fab_share;
    private FloatingActionButton fab_copylink;
    private FloatingActionButton fab_openinbrower;
    private FloatingActionMenu floatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

//        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fam);


        fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        fab_share.setOnClickListener(this);
        fab_copylink = (FloatingActionButton) findViewById(R.id.fab_copylink);
        fab_copylink.setOnClickListener(this);
        fab_openinbrower = (FloatingActionButton) findViewById(R.id.fab_openinbrower);
        fab_openinbrower.setOnClickListener(this);


        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatingActionMenu.isOpened()) {

                }
                floatingActionMenu.toggle(true);
            }
        });


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            String json = getIntent().getStringExtra(ARG_ITEM_ID);
            mItem = new Gson().fromJson(json, CategoryEntity.ResultsBean.class);
            arguments.putString(ARG_ITEM_ID,
                    json);
            BaseDetailFragment fragment = new BaseDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.android_detail_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
//            navigateUpTo(new Intent(this, BaseListActivity.class));

            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_share:
                ActivityUtils.share(this, mItem.getUrl());
                floatingActionMenu.toggle(true);
                break;
            case R.id.fab_copylink:
                if (ActivityUtils.copyToClipBoard(this, mItem.getUrl())){
                    Snackbar.make(v,"链接复制成功", Snackbar.LENGTH_LONG).show();
                }
                floatingActionMenu.toggle(true);
                break;
            case R.id.fab_openinbrower:
                ActivityUtils.openInbrowser(this, mItem.getUrl());
                floatingActionMenu.toggle(true);
                break;
        }
    }
}
