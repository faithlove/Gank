package gank.douya.com.gank.net.http;

import java.util.concurrent.TimeUnit;

import gank.douya.com.gank.net.entity.CategoryEntity;
import gank.douya.com.gank.net.service.GankService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by douya on 2017/3/14.
 */

public class HttpMethods {
    private static final String baseUrl = "http://gank.io/api/data/";
    private static final int DEFAULT_TIMEOUT = 5;
    private final GankService gankService;

    public HttpMethods() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gankService = retrofit.create(GankService.class);
    }

    private static class SingleHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void getCategory(Subscriber<CategoryEntity> subscriber, String category, int number, int page) {
        gankService.getCategory(category, number, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
