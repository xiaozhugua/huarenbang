package com.abcs.huaqiaobang;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.abcs.haiwaigou.local.beans.PinLeiRight;
import com.abcs.haiwaigou.model.BannersBean;
import com.abcs.hqbtravel.entity.LookPhotos;
import com.abcs.hqbtravel.entity.RestauransBean;
import com.abcs.hqbtravel.entity.ShopBean;
import com.abcs.hqbtravel.entity.TouristAttractionsBean;
import com.abcs.hqbtravel.wedgt.CrashHandler;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.huaqiaobang.model.User;
import com.abcs.huaqiaobang.tljr.news.channel.db.SQLHelper;
import com.abcs.huaqiaobang.util.LogUtil;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.imkfsdk.utils.FaceConversionUtil;
import com.abcs.sociax.t4.android.ActivityHome;
import com.abcs.sociax.t4.android.Thinksns;
import com.abcs.sociax.t4.android.ThinksnsActivity;
import com.abcs.sociax.t4.android.bean.CeSu;
import com.android.volley.RequestQueue;
import com.baidu.android.pushservice.PushManager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;
import com.tongdao.sdk.TongDao;
import com.yuntongxun.ecsdk.ECDevice;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

//import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Thinksns implements Thread.UncaughtExceptionHandler {

    /*处理特殊字符*/
    public static String encodeUrl(String urlStr)
    {
        String url=null;
        if(urlStr.contains("%")){
            url =urlStr.replace("%", "%25");
        }else if(urlStr.contains("+")){
            url =urlStr.replace("+", "%2B");
        }else if(urlStr.contains(" ")){
            url =urlStr.replace(" ", "%20");
        }
//        else if(urlStr.contains("/")){
//            urlStr.replace("/", "%2F");
//        }else if(urlStr.contains("?")){
//            url =urlStr.replace("?", "%3F");
//        }else if(urlStr.contains("#")){
//            url =urlStr.replace("#", "%23");
//        }else if(urlStr.contains("&")){
//            url =urlStr.replace("&", "%26");
//        }else if(urlStr.contains("=")){
//            url =urlStr.replace("=", "%3D");
//        }
        else {
            url =urlStr;
        }

        return url;
    }

    //客服SDK是否已经初始化过了
    public static boolean isKFSDK = false;
    public static  int current_position =0;
    public static  String QU_HAO =null;
    public static  String stord_member;  // 保存登录返回的stord_member，用来判断是否是本地用户

    public static  ArrayList<BannersBean> guanggao = new ArrayList<>();  // local 广告
    public static  ArrayList<BannersBean> guanggao2 = new ArrayList<>();  // local 广告

    public static List<LookPhotos.BodyBean> banners=new ArrayList<>();

    public static boolean isFlashVip=false;
    public static List<TouristAttractionsBean> touristAttractionsBeen=new ArrayList<>();
    public static List<ShopBean> shopsBeen=new ArrayList<>();
    public static JSONArray userLocal=new JSONArray();
    public static List<RestauransBean> restaurantsBeen=new ArrayList<>();

    public User self;
    public static Map<String, Activity> activityMap = new HashMap<String, Activity>();
    public static boolean isupdate = false;
    public static MyApplication instance;
    public static ImageLoader imageLoader;
    public static DisplayImageOptions options;
    public static RequestQueue requestQueue;
    public static String cardname;
    public static String carddesc;
    public String location;
    ProgressDlgUtil dlgUtil = new ProgressDlgUtil();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User user;

    public String mykey,myLevelId;
    public boolean userChange = false;
    private TongDao tongDao;


    public static String getPicFirstName(String image_url){
        String result=image_url.substring(0,image_url.indexOf("_"));
        if(!TextUtils.isEmpty(result)){
            return result;
        }
        return null;
    }
    public String getMykey() {
        return mykey;
    }

    public void setMykey(String mykey) {
        this.mykey = mykey;
    }

    public String getMyLevelId() {
        return myLevelId;
    }

    public  List<PinLeiRight.DatasBean.Class3Bean> getTabList() {
        return class3;
    }
    private  List<PinLeiRight.DatasBean.Class3Bean> class3=new ArrayList<>();
    public  void setTabList(List<PinLeiRight.DatasBean.Class3Bean> ceSuList) {
        this.class3 = ceSuList;
    }

    public List<CeSu> ceSuList=new ArrayList<>();

    public void setMyLevelId(String myLevelId) {
        this.myLevelId = myLevelId;
    }
    public  List<CeSu> getCeSuList() {
        return ceSuList;
    }

    public  void setCeSuList(List<CeSu> ceSuList) {
        this.ceSuList = ceSuList;
    }

    public static boolean isShake, isSound = false;
    public static ConcurrentHashMap<String, User> friends;
    public static HashMap<String, String> contacts = new HashMap<>();
    public static ArrayList<User> users;
    private String file;
    private String ownernickname, avater;
    private int uid;
    private String uid_yyg;
    public static String device_token;
    public static DbUtils dbUtils;
    public static BitmapUtils bitmapUtils;
    public static List<Activity> list = new ArrayList<>();
    public static HashSet<BaseActivity> REGISTERACTIVITYS = new HashSet();

    public static Context context;

    public String getUid_yyg() {
        return uid_yyg;
    }

    public void setUid_yyg(String uid_yyg) {
        this.uid_yyg = uid_yyg;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LogUtil.i("whystar","whystar****MyApplication***********************");
//        if (!quickStart() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//>=5.0的系统默认对dex进行oat优化
//            if (needWait(base)){
//                waitForDexopt(base);
//            }
           MultiDex.install (this);
//        } else {
//            return;
//        }
    }
    public boolean quickStart() {
        if (getCurProcessName(this).indexOf(":mini")>-1) {
            Log.d("loadDex", ":mini start!");
            return true;
        }
        return false ;
    }
    private boolean needWait(Context context){
        String flag = get2thDexSHA1(context);
        Log.d( "loadDex", "dex2-sha1 "+flag);
        SharedPreferences sp = context.getSharedPreferences(
                getPackageInfo(context). versionName, MODE_MULTI_PROCESS);
        String saveValue = sp.getString(KEY_DEX2_SHA1, "");
        return !flag.equals(saveValue);
    }
    /**
     * Get classes.dex file signature
     * @param context
     * @return
     */
    private String get2thDexSHA1(Context context) {
        ApplicationInfo ai = context.getApplicationInfo();
        String source = ai.sourceDir;
        try {
            JarFile jar = new JarFile(source);
            java.util.jar.Manifest mf = jar.getManifest();
            Map<String, Attributes> map = mf.getEntries();
            Attributes a = map.get("classes2.dex");
            return a.getValue("SHA1-Digest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";
    public static final String HUOHANG_STORELIST = "huohang_storelist2";  // 货行店铺地址
    // optDex finish
    public void installFinish(Context context){
        SharedPreferences sp = context.getSharedPreferences(
                getPackageInfo(context).versionName, MODE_MULTI_PROCESS);
        sp.edit().putString(KEY_DEX2_SHA1,get2thDexSHA1(context)).commit();
    }
    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.getLocalizedMessage());
        }
        return  new PackageInfo();
    }


    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context. ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess. processName;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null ;
    }


    // 同道APP_KEY
    private static final String TONGDAO_APP_KEY = "d0f2ce8dfdf120bff6b7b86970de60bc";

    synchronized public TongDao getTongDao() {
        if (tongDao == null){
            tongDao = TongDao.initialize(this,TONGDAO_APP_KEY,false);
        }
        return tongDao;
    }

    /**
     *判断某个数组中是否包含某一个值
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();  // auto layout 使用设备高度
        if (tongDao == null){
            tongDao = TongDao.initialize(this,TONGDAO_APP_KEY,false);
        }

        // 设置默认的Reanlm配置
        RealmConfiguration realmconfig = new RealmConfiguration.Builder(this).name("tljr_Default.realm").build();
        try {
            Realm.setDefaultConfiguration(realmconfig);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmconfig);
                // Realm file has been deleted.
                Realm.setDefaultConfiguration(realmconfig);
            } catch (Exception ex) {
                // No Realm file to remove.
            }
        }

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        instance = this;
        //初始化表情,界面效果需要  容联客服
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("xxx", "run: 初始化表情,界面效果需要  容联客服");
                FaceConversionUtil.getInstace().getFileText(getApplicationContext());
            }
        }).start();

        Util.init();
        options = Options.getListOptions();
        registerReceiver(receiver3, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));


        isShake = getSharedPreferences("user", MODE_PRIVATE).getBoolean(
                "isShake", false);
        isSound = getSharedPreferences("user", MODE_PRIVATE).getBoolean("isSound", true);

        context=getApplicationContext();
        inniImageLoder();

        //设置Thread Exception Handler
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    public void inniImageLoder(){
        imageLoader = ImageLoader.getInstance();

        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                // .discCacheExtraOptions(480, 800, CompressFormat.PNG, 75,
                // null)
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(2)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 3).denyCacheImageMultipleSizesInMemory()
                // .memoryCache(new WeakMemoryCache())
                .memoryCache(new LruMemoryCache(8 * 1024 * 1024))

                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(9 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .discCacheFileCount(200)
                // 缓存的文件数量

                  .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout
                // (5
                // ss),
                // readTimeout
                // (30
                // ss)超时时间
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        imageLoader.init(config);
    }

    public static DisplayImageOptions getListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
              /*  // // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.drawable.img_huaqiao_default)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.img_huaqiao_default)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.img_huaqiao_default)*/
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)

                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入

                .build();
        return options;
    }
    public static DisplayImageOptions getCircleImageOptions() {

        DisplayImageOptions options=null;
        if (options != null) {
            return options;
        }
        options = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
//				.showImageOnLoading(R.drawable.img_morentupian)
                // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageForEmptyUri(R.drawable.img_morentupian)
                // 设置图片加载/解码过程中错误时候显示的图片
//				.showImageOnFail(R.drawable.img_morentupian)
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(10))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();

        return options;
    }
    public static DisplayImageOptions getCircleFiveImageOptions() {

        DisplayImageOptions options=null;
        if (options != null) {
            return options;
        }
        options = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
//				.showImageOnLoading(R.drawable.img_morentupian)
                // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageForEmptyUri(R.drawable.img_morentupian)
                // 设置图片加载/解码过程中错误时候显示的图片
//				.showImageOnFail(R.drawable.img_morentupian)
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(8))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();

        return options;
    }

    public static DisplayImageOptions getAvatorOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
              /*  // // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.drawable.img_huaqiao_default)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.img_huaqiao_default)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.img_huaqiao_default)*/
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)

                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入

                .build();
        return options;
    }


    public boolean checkNetWork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = manager.getActiveNetworkInfo();
        return !(info == null || !manager.getBackgroundDataSetting());
    }

    public void stopKillService() {
        Intent intent = new Intent();
        intent.setAction("stopKillService");
        sendBroadcast(intent);
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }


    private static boolean isExist(List<String> dbFildsList, String fildName) {
        for (String string : dbFildsList) {
            if (string.equals(fildName)) {
                return true;
            }
        }
        return false;
    }

    BroadcastReceiver receiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra("reason");
                if (reason != null) {
                    if (reason.equals("homekey")) {
//                        Intent intent1 = new Intent(
//                                MyApplication.getInstance(), KillService.class);
//                        MyApplication.getInstance().startService(intent1);
                    } else if (reason.equals("recentapps")) {
                    }
                }
            }
        }
    };

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }



//    public static void addActivity(Activity a) {
//        list.add(a);
//    }
//
//    public static void removeActivity(Activity a) {
//        list.remove(a);
//    }

    //    public static void exit() {
//        for (Activity a : list) {
//            if (a != null) {
//                a.finish();
//                a = null;
//            }
//        }
//
//    }


    private String name, token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOwnernickname() {
        return ownernickname;
    }

    public void setOwnernickname(String ownernickname) {
        this.ownernickname = ownernickname;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private Activity nowActivity;


    // 添加Activity到容器中
    public void addActivityToMap(Activity activity) {
        nowActivity = activity;
        activityMap.put(activity.getClass().getName(), activity);
    }

    public void removeActivity(Activity activity) {
        activityMap.remove(activity);
    }

    public ActivityHome getMainActivity() {
        return (ActivityHome) activityMap
                .get("com.abcs.sociax.t4.android.ActivityHome");
    }

    // 遍历所有Activity并finish
    public static void exit() {

//        ECDevice.is
        if (Util.preference.getBoolean("islogin", false)) {
            PushManager.stopWork(MyApplication.getInstance());
            ECDevice.logout(ECDevice.NotifyMode.NOT_NOTIFY, new ECDevice.OnLogoutListener() {
                @Override
                public void onLogout() {
                    ECDevice.unInitial();
                    Util.preference.edit().putBoolean("islogin", false).commit();
                    dbUtils.close();
                    bitmapUtils.cancel();
//                    AppManager.getAppManager().AppExit();
                    for (Activity activity : activityMap.values()) {
                        activity.finish();
                    }
//				mPushAgent.disable();
                }
            });
        } else {
            dbUtils.close();
            bitmapUtils.cancel();
//            AppManager.getAppManager().AppExit();
//				mPushAgent.disable();
            for (Activity activity : activityMap.values()) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        System.out.println("uncaughtException");
        System.exit(0);
        Intent intent = new Intent(this, ThinksnsActivity.class);  //  程序崩溃了自动重启
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        //  抓取log
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
    }

    public class getByteThread extends Thread {
        @Override
        public void run() {
            while (true) {
                getUidByte();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 获取数据库Helper
     */
    private SQLHelper sqlHelper;

    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(instance);
        return sqlHelper;
    }

    public static long first1 = 0, first2 = 0;

    public void getUidByte() {
//        try {
//            PackageManager pm = getPackageManager();
//            ApplicationInfo ai = pm.getApplicationInfo(Constent.packageName,
//                    PackageManager.GET_ACTIVITIES);
//            if (first1 == 0) {
//                first1 = TrafficStats.getUidRxBytes(ai.uid);
//            }
//            if (first2 == 0) {
//                first2 = TrafficStats.getUidTxBytes(ai.uid);
//            }
//            double s1 = (TrafficStats.getUidRxBytes(ai.uid) - first1) / 1024;
//            double s2 = (TrafficStats.getUidTxBytes(ai.uid) - first2) / 1024;
//            Constent.Liuliang = (int) s1 + (int) s2;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    // public void resultandLogin() {
    // ((MainActivity) umengPushServer.activityMap.get(Constent.packageName
    // + ".main.MainActivity")).mHandler.sendEmptyMessage(201);
    // }

    private static int sTheme = 1;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_RED = 1;
    public final static int THEME_BLUE = 2;
    public static int StatusBarColor;
    public static float turnIn_money;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            case THEME_DEFAULT:
                StatusBarColor = activity.getResources().getColor(
                        com.abcs.sociax.android.R.color.tljr_balck);
                activity.setTheme(com.abcs.sociax.android.R.style.MyThemeDefault);
                break;
            case THEME_RED:
                StatusBarColor = activity.getResources().getColor(
                        com.abcs.sociax.android.R.color.tljr_text_shense);
                activity.setTheme(com.abcs.sociax.android.R.style.MyThemeNight);
                break;
            default:
                break;
        }
    }


    //=========================================travel==============================
    public static String my_current_lng;
    public static String my_current_lat;
    public static final String LOCATION="location";
    public static final String LOCATION_NAME="locationsd";
    private static final String  CITY_ID="cityid";
    private static final String  CITY_ID_NAME="cityids";

    public static void saveLocation(String location){
        SharedPreferences sp=context.getSharedPreferences(LOCATION,MODE_PRIVATE);
        sp.edit().putString(LOCATION_NAME,location).commit();

    }
    public static String getLocation(){
        SharedPreferences sp=context.getSharedPreferences(LOCATION,MODE_PRIVATE);
        return  sp.getString(LOCATION_NAME,null);

    }

    /////////////////
    public static void savePosition(int Position){
        SharedPreferences sp=context.getSharedPreferences("Position",MODE_PRIVATE);
        sp.edit().putInt("Position",Position).commit();

    }
    public static int getPosition(){
        SharedPreferences sp=context.getSharedPreferences("Position",MODE_PRIVATE);
        return  sp.getInt("Position",0);

    }
    ///////////////////////////
    public static void saveTravelCityId(String cityId){
        SharedPreferences sp=context.getSharedPreferences("cityIdrf",MODE_PRIVATE);
        sp.edit().putString("cityIdrf",cityId).commit();

    }
    public static String getTravelCityId(){
        SharedPreferences sp=context.getSharedPreferences("cityIdrf",MODE_PRIVATE);
        return  sp.getString("cityIdrf",null);

    }
    public static void saveISRESTART(long ISRESTART){
        SharedPreferences sp=context.getSharedPreferences("ISRESTART",MODE_PRIVATE);
        sp.edit().putLong("ISRESTART",ISRESTART).commit();

    }
    public static long getISRESTART(){
        SharedPreferences sp=context.getSharedPreferences("ISRESTART",MODE_PRIVATE);
        return  sp.getLong("ISRESTART",System.currentTimeMillis());
    }
    public static void saveLatCenter(String LatCenter){
        SharedPreferences sp=context.getSharedPreferences("LatCenter",MODE_PRIVATE);
        sp.edit().putString("LatCenter",LatCenter).commit();

    }
    /////////////////////
    public static String getLocaCountryName(){
        SharedPreferences sp=context.getSharedPreferences("LocaCountryName",MODE_PRIVATE);
        return  sp.getString("LocaCountryName",null);

    }
    public static void saveLocaCountryName(String LocaCountryName){
        SharedPreferences sp=context.getSharedPreferences("LocaCountryName",MODE_PRIVATE);
        sp.edit().putString("LocaCountryName",LocaCountryName).commit();

    }
    public static void clearLocaCountryName(){
        SharedPreferences sp=context.getSharedPreferences("LocaCountryName",MODE_PRIVATE);
        sp.edit().clear().commit();

    }
    /////////////////////
    public static String getWenDu(){
        SharedPreferences sp=context.getSharedPreferences("WenDu",MODE_PRIVATE);
        return  sp.getString("WenDu",null);

    }
    public static void saveWenDu(String WenDu){
        SharedPreferences sp=context.getSharedPreferences("WenDu",MODE_PRIVATE);
        sp.edit().putString("WenDu",WenDu).commit();

    }
    /////////////////////
    public static String getCurrentHost(){
        SharedPreferences sp=context.getSharedPreferences("CurrentHost",MODE_PRIVATE);
        return  sp.getString("CurrentHost",null);

    }
    public static void saveCurrentHost(String CurrentHost){
        SharedPreferences sp=context.getSharedPreferences("CurrentHost",MODE_PRIVATE);
        sp.edit().putString("CurrentHost",CurrentHost).commit();

    }

    public static void clearLocaCityName(){
        SharedPreferences sp=context.getSharedPreferences("LocaCityName",MODE_PRIVATE);
        sp.edit().clear().commit();

    }
    /////////////////////
    public static String getLocaCityId(){
        SharedPreferences sp=context.getSharedPreferences("LocaCityId",MODE_PRIVATE);
        return  sp.getString("LocaCityId",null);

    }
    public static void saveLocaCityId(String LocaCityId){
        SharedPreferences sp=context.getSharedPreferences("LocaCityId",MODE_PRIVATE);
        sp.edit().putString("LocaCityId",LocaCityId).commit();

    }
    public static void clearLocaCityId(){
        SharedPreferences sp=context.getSharedPreferences("LocaCityId",MODE_PRIVATE);
        sp.edit().clear().commit();

    }


    public static String getLatCenter(){
        SharedPreferences sp=context.getSharedPreferences("LatCenter",MODE_PRIVATE);
        return  sp.getString("LatCenter",null);

    }
    public static void saveLeveId(String LeveId){
        SharedPreferences sp=context.getSharedPreferences("LeveId",MODE_PRIVATE);
        sp.edit().putString("LeveId",LeveId).commit();

    }
    public static String getLeveId(){
        SharedPreferences sp=context.getSharedPreferences("LeveId",MODE_PRIVATE);
        return  sp.getString("LeveId",null);

    }
    public static void saveBiChiNum(String num){
        SharedPreferences sp=context.getSharedPreferences("bichinu",MODE_PRIVATE);
        sp.edit().putString("bichinum",num).commit();

    }
    public static String getBiChiNum(){
        SharedPreferences sp=context.getSharedPreferences("bichinu",MODE_PRIVATE);
        return  sp.getString("bichinum",null);

    }
    public static void saveBiWanNum(String num){
        SharedPreferences sp=context.getSharedPreferences("biwannu",MODE_PRIVATE);
        sp.edit().putString("biwannum",num).commit();

    }
    public static String getBiWanNum(){
        SharedPreferences sp=context.getSharedPreferences("biwannu",MODE_PRIVATE);
        return  sp.getString("biwannum",null);

    }
    public static void saveIsFirstLocal(boolean isfitst){
        SharedPreferences sp=context.getSharedPreferences("isfitst",MODE_PRIVATE);
        sp.edit().putBoolean("isfitst",isfitst).commit();

    }
    public static boolean getIsFirstLocal(){
        SharedPreferences sp=context.getSharedPreferences("isfitst",MODE_PRIVATE);
        return  sp.getBoolean("isfitst",false);

    }

    // 保存本地的店铺名称
    public static void saveLocalStoreName(String StoreName){
        SharedPreferences sp=context.getSharedPreferences("StoreName",MODE_PRIVATE);
        sp.edit().putString("StoreName",StoreName).commit();

    }
    public static String getStoreName(){
        SharedPreferences sp=context.getSharedPreferences("StoreName",MODE_PRIVATE);
        return  sp.getString("StoreName",null);

    }
    // 保存本地的店铺标题
    public static void saveLocalDisterName(String DisterName){
        SharedPreferences sp=context.getSharedPreferences("DisterName",MODE_PRIVATE);
        sp.edit().putString("DisterName",DisterName).commit();

    }
    public static String getDisterName(){
        SharedPreferences sp=context.getSharedPreferences("DisterName",MODE_PRIVATE);
        return  sp.getString("DisterName",null);

    }
    public static void saveBiMaiNum(String num){
        SharedPreferences sp=context.getSharedPreferences("bimainu",MODE_PRIVATE);
        sp.edit().putString("bimainum",num).commit();

    }
    public static String getBiMaiNum(){
        SharedPreferences sp=context.getSharedPreferences("bimainu",MODE_PRIVATE);
        return  sp.getString("bimainum",null);

    }
    public static void saveCityId(String cityId){
        SharedPreferences sp=context.getSharedPreferences(CITY_ID,MODE_PRIVATE);
        sp.edit().putString(CITY_ID_NAME,cityId).commit();

    }
    public static String getCityId(){
        SharedPreferences sp=context.getSharedPreferences(CITY_ID,MODE_PRIVATE);
        return  sp.getString(CITY_ID_NAME,null);

    }
    public static void saveCityName(String cityname){
        SharedPreferences sp=context.getSharedPreferences("CityNames",MODE_PRIVATE);
        sp.edit().putString("CityName",cityname).commit();

    }

    public static String getCityName(){
        SharedPreferences sp=context.getSharedPreferences("CityNames",MODE_PRIVATE);
        return  sp.getString("CityName",null);

    }
    /*保存货币符号*/
    public static void saveHHStoreCoin(String HHStoreCoin){
        SharedPreferences sp=context.getSharedPreferences("HHStoreCoin",MODE_PRIVATE);
        sp.edit().putString("HHStoreCoin",HHStoreCoin).commit();

    }

    public static String getHHStoreCoin(){
        SharedPreferences sp=context.getSharedPreferences("HHStoreCoin",MODE_PRIVATE);
        return  sp.getString("HHStoreCoin",null);

    }


    public static int getAPILevel(){
        int version = android.provider.Settings.System.getInt(context
                        .getContentResolver(),
                android.provider.Settings.System.SYS_PROP_SETTING_VERSION,
                3);
        return version;
    }
    public static String getChannel(){

        return "official";
    }
    public static String getDeviceType(){

        return "android";
    }


    /**
     * 产品的版本号
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getProductVersion(){
        String version=null;
        try{
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
             version = info.versionName;


        }catch (PackageManager.NameNotFoundException e){
            Log.e("NameNotFoundException",e.getMessage());
        }
        return version;
    }

    /**
     * 获取设备序列号
     * @return
     */
    public static String getDeviceId() {
        String deviceId = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        if (deviceId == null || "".equals(deviceId)) {
            deviceId = "0000000000";
        }
        return deviceId;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    public static int getWidth(){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    public static int getHeight(){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public static String  getCituIdFromNet(String cityName){
        InputStream is=null;
        try {
            // 设置请求的地址 通过URLEncoder.encode(String sd, String enc)
            // 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式

            String spec= TLUrl.getInstance().URL_BASE+"/travel/find/getCityIdByName?cityName="+cityName;
            URL url = new URL(spec);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            urlConnection.setDoInput(true);
            urlConnection.connect();
//            // 设置请求的头
//            urlConnection
//                    .setRequestProperty("User-Agent",
//                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }

//                 返回字符串
                String result = new String(os.toByteArray());

                Log.i("zds",result+"");

                os.close();
                // 释放资源
                is.close();

                return result;
            } else {
                System.out.println("------------------链接失败-----------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*d动态设置tablayout下划线的长度*/
    public static void setIndicator (TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /*给AdapterItem 设置动画*/
    public static void startAniWithPosition(final int potisition, final View itemView) {
        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int hight = MyApplication.getHeight();
                if (potisition > (hight / itemView.getMeasuredHeight()) - 1) {
                    return;
                }
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(itemView, "translateY", -itemView.getMeasuredHeight(), 0),
//                        ObjectAnimator.ofFloat(itemView,"scaleX",0.3f,1.0f),
//                        ObjectAnimator.ofFloat(itemView,"scaleY",0.3f,1.0f),
                        ObjectAnimator.ofFloat(itemView, "alpha", 0, 1));
                set.setStartDelay(100 * potisition);
                set.setDuration(200).start();
                itemView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            }
        });
    }
    }
