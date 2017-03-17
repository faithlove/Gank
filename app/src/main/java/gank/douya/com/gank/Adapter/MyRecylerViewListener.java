package gank.douya.com.gank.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by douya on 2017/3/16.
 */

public abstract class MyRecylerViewListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private boolean loading = true;
    private int previousTotal = 0;//存储之前加载的总条目数量
    private int currentPage = 0;

    public MyRecylerViewListener(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
//        Log.d("Listener", "x" + dx);
//        Log.d("Listener", "y" + dy);

        visibleItemCount = recyclerView.getChildCount();//当前看见item数量
        Log.d("Listener", "可见数量为：" + visibleItemCount);
        totalItemCount = mLinearLayoutManager.getItemCount();//已经加载出来的item的数量
        Log.d("Listener", "已加载条目数量:" + totalItemCount);
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        Log.d("Listener", "第一个可见条目的位置:" + firstVisibleItem);

        if (loading) {
            if (totalItemCount > previousTotal) {
                //加载完毕
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        /**
         * totalItemCount-firstVisibleItem<=visibleItemCount
         * 当滑动到最后一个条目出现时才会相等，因此可以判断滑动到底部了
         */
        if (!loading && totalItemCount - firstVisibleItem <= visibleItemCount) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }

        Log.d("Listener", "currentPage:" + currentPage);
    }

    public abstract void onLoadMore(int currentPage);
}
