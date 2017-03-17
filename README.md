
API：

>
分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
>
数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
>
请求个数： 数字，大于0
>
第几页：数字，大于0

Gradle:

    //添加retrofit2 的依赖 添加这个依赖就默认添加了okhttp依赖
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    
    //支持Gson 及 rxjava
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
    
    //okhttp log 工具
    compile 'com.squareup.okhttp3:logging-interceptor:3.1.2'
    
    //gson
    compile 'com.google.code.gson:gson:2.5'
    
    //rxjava
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxjava:1.1.3'
    
    //fabtoolbar
    compile 'com.github.fafaldo:fab-toolbar:1.2.0'
    
    //fabAction
    compile 'com.github.clans:fab:1.6.4'
    
    //Material Design
    compile 'com.android.support:recyclerview-v7:25.2.0'
    compile 'com.android.support:cardview-v7:25.2.0'
    
    //图片加载：Glide支持动态图 而Picasso不可以
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.squareup.picasso:picasso:2.5.2'
    
    
    
    
