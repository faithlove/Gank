package gank.douya.com.gank.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gank.douya.com.gank.Adapter.MyRecylerViewListener;
import gank.douya.com.gank.R;
import gank.douya.com.gank.net.entity.CategoryEntity;
import gank.douya.com.gank.net.http.HttpMethods;
import gank.douya.com.gank.utils.ActivityUtils;
import rx.Subscriber;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BaseDetailFragment} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BaseListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Subscriber<CategoryEntity> subscriber;
    private List<CategoryEntity.ResultsBean> resultsBeanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String target;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SimpleItemRecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);

        target = getIntent().getStringExtra(ActivityUtils.ACTIVITY_TARGET);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(target);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                finish();
            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.android_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        if (findViewById(R.id.android_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        onRefresh();
    }

    public void getData(int pageCount) {
        subscriber = new Subscriber<CategoryEntity>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getApplicationContext(), "compelete", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(CategoryEntity categoryEntity) {
                resultsBeanList.addAll(categoryEntity.getResults());
                Log.d("Listener", "onNext:" + resultsBeanList.toString());
//                resultsBeanList = categoryEntity.getResults();
//                Toast.makeText(getApplicationContext(), resultsBeanList.toString(), Toast.LENGTH_SHORT).show();


            }
        };
        HttpMethods.getInstance().getCategory(subscriber, target, ActivityUtils.COUNT_PERPAGE, pageCount);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerViewAdapter = new SimpleItemRecyclerViewAdapter(resultsBeanList);
        recyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnScrollListener(new MyRecylerViewListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage + 1);
            }
        });

    }

    @Override
    public void onRefresh() {
        resultsBeanList.clear();
        getData(1);
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<CategoryEntity.ResultsBean> mValues;

        public SimpleItemRecyclerViewAdapter(List<CategoryEntity.ResultsBean> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.base_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
//            Log.d("spy",mValues.get(position).getImages().get(0));

            if (mValues.get(position).getImages() != null) {
                Glide.with(getApplicationContext())
                        .load(mValues.get(position).getImages().get(0))
                        .asBitmap()
                        .into(holder.mImage);
            } else {
                Glide.with(getApplicationContext())
                        .load(R.drawable.index).asGif()
                        .crossFade()
                        .into(holder.mImage);
            }

            holder.mTitle.setText(mValues.get(position).getDesc());
            holder.mPublisher.setText(mValues.get(position).getWho());
            holder.mTime.setText(mValues.get(position).getPublishedAt());


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        //json传递对象
                        arguments.putString(BaseDetailFragment.ARG_ITEM_ID, new Gson().toJson(holder.mItem));
                        BaseDetailFragment fragment = new BaseDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.android_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, BaseDetailActivity.class);
                        intent.putExtra(BaseDetailFragment.ARG_ITEM_ID, new Gson().toJson(holder.mItem));
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final View mView;
            public CategoryEntity.ResultsBean mItem;
            private final ImageView mImage;
            private final TextView mTitle;
            private final TextView mPublisher;
            private final TextView mTime;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                /**
                 * CardView设置点击事件时应将clickable设置为true
                 */
                mImage = (ImageView) view.findViewById(R.id.iv_item_img);
                mTitle = (TextView) view.findViewById(R.id.tv_item_title);
                mPublisher = (TextView) view.findViewById(R.id.tv_item_publisher);
                mTime = (TextView) view.findViewById(R.id.tv_item_time);
            }

        }
    }

}
