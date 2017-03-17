package gank.douya.com.gank.model;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import gank.douya.com.gank.R;
import gank.douya.com.gank.net.entity.CategoryEntity;

/**
 * A fragment representing a single Android detail screen.
 * This fragment is either contained in a {@link BaseDetailFragment}
 * in two-pane mode (on tablets) or a {@link BaseDetailFragment}
 * on handsets.
 */
public class BaseDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item";

    /**
     * The dummy content this fragment is presenting.
     */
    private CategoryEntity.ResultsBean mItem;
    private WebView mWebview;
    private ImageView mToolbarBackgroud;
    private ProgressBar progressBar;

    private Activity activity;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BaseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            String itemJson = getArguments().getString(ARG_ITEM_ID);
            mItem = new Gson().fromJson(itemJson, CategoryEntity.ResultsBean.class);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getDesc());
            }

            mToolbarBackgroud = (ImageView) activity.findViewById(R.id.toolbar_backgroud);
            if (mItem.getImages() != null) {
                Glide.with(activity)
                        .load(mItem.getImages().get(0))
                        .asBitmap()
                        .into(mToolbarBackgroud);
            } else {
                Glide.with(activity)
                        .load(R.drawable.index)
                        .asBitmap()
                        .into(mToolbarBackgroud);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.base_detail, container, false);

        // Show the dummy content as text in a TextView.
        mWebview = (WebView) rootView.findViewById(R.id.android_detail);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar_webview);


        if (mItem != null) {


            WebSettings settings = mWebview.getSettings();
            settings.setJavaScriptEnabled(true);

            settings.setLoadWithOverviewMode(true);
            settings.setJavaScriptEnabled(true);
            settings.setAppCacheEnabled(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setSupportZoom(true);


            mWebview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }
            });


            mWebview.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            });

            mWebview.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            //处理webView的返回事件
                            mWebview.goBack();
                        }
                    }
                    return false;
                }
            });

            mWebview.loadUrl(mItem.getUrl());


        }


        return rootView;
    }
}
