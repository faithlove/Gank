package gank.douya.com.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import gank.douya.com.gank.model.BaseListActivity;
import gank.douya.com.gank.model.SettingsActivity;
import gank.douya.com.gank.utils.ActivityUtils;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private FABToolbarLayout layout;
    private View one, two, three, four;
    private FloatingActionMenu menuRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);


        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.show();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


//        menuRight = (FloatingActionMenu) findViewById(R.id.menu_labels_right);
//        menuRight.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_android) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, BaseListActivity.class);
            intent.putExtra(ActivityUtils.ACTIVITY_TARGET, "Android");
            startActivity(intent);
        } else if (id == R.id.nav_app) {
            Intent intent = new Intent(MainActivity.this, BaseListActivity.class);
            intent.putExtra(ActivityUtils.ACTIVITY_TARGET, "all");
            startActivity(intent);
        } else if (id == R.id.nav_ios) {
            Intent intent = new Intent(MainActivity.this, BaseListActivity.class);
            intent.putExtra(ActivityUtils.ACTIVITY_TARGET, "iOS");
            startActivity(intent);
        } else if (id == R.id.nav_web) {
            Intent intent = new Intent(MainActivity.this, BaseListActivity.class);
            intent.putExtra(ActivityUtils.ACTIVITY_TARGET, "前端");
            startActivity(intent);
        } else if (id == R.id.nav_welfare) {

        } else if (id == R.id.nav_more) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                Toast.makeText(getApplicationContext(), "One clicked", Toast.LENGTH_SHORT).show();
                layout.hide();
                break;
            case R.id.two:

                break;
            case R.id.three:

                break;
            case R.id.four:

                break;
//            case R.id.menu_labels_right:
////                menuRight.showMenuButton(true);
//                break;
        }

    }
}
