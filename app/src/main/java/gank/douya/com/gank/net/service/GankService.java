package gank.douya.com.gank.net.service;

import gank.douya.com.gank.net.entity.CategoryEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by douya on 2017/3/14.
 */

public interface GankService {
    /**
     * http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     *
     * @return
     */
    @GET("{category}/{number}/{page}")
    Observable<CategoryEntity> getCategory(@Path("category") String category, @Path("number") int number, @Path("page") int page);



}
