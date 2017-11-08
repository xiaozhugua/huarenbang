package com.abcs.sociax.t4.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abcs.haiwaigou.utils.ACache;
import com.abcs.haiwaigou.utils.MyString;
import com.abcs.haiwaigou.view.recyclerview.NetworkUtils;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.login.AutoLogin;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.huaqiaobang.wxapi.RegisterActivity;
import com.abcs.sociax.android.R;
import com.abcs.sociax.constant.AppConstant;
import com.abcs.sociax.t4.android.img.RoundImageView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.thinksns.sociax.thinksnsbase.utils.ActivityStack;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.jpush.android.api.InstrumentedActivity;

import static com.abcs.sociax.t4.android.ActivityHome.BAIDULOCATION;

public class ThinksnsActivity extends InstrumentedActivity {
	public static boolean canOpenGoogle=false;
	public static ImageLoader imageLoader;
	public static DisplayImageOptions options;
	private ImageView bj;
	private ImageView bar;
	private static final String TAG = "zds";
	protected static final int SHOW_GUIDE = 4;
	private static final int LOGIN = 5;
	protected static final int REGISTER = 6;
	protected static final int GET_KEY = 7;

	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	private ViewGroup guide;
	private LayoutInflater inflater;
	private TextView tv_register, tv_login;
	public static SharedPreferences preferences;

	private ImageView smalldot;		// 广告位小圆点
	private ImageView[] smalldots;	// 广告位所有小圆点
	private LinearLayout ll_find_ads_dots;	// 广告位红点

	public Handler handlerUI;
	private ACache aCache;

	private Thinksns app;
	private static ThinksnsActivity instance = null;

	public static ThinksnsActivity getInstance() {
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		initPre();
		initHandler();
		initStartView();
		initHost();
//		initBaiduLbs();
	}


	private void initPre() {
		boolean loginOut = getIntent().getBooleanExtra("login_out", false);
		if(loginOut) {
			Thinksns.clearAllActivity();
			//清除当前用户信息
			Thinksns.getUserSql().clear();
		}

       /* 每次进入APP时 清除货行店铺列表缓存*/
		aCache= ACache.get(this);
		if(!TextUtils.isEmpty(aCache.getAsString(MyApplication.HUOHANG_STORELIST))){
			aCache.remove(MyApplication.HUOHANG_STORELIST);
		}

		if (Util.preference.getBoolean("ydlogin", false)) {
			Toast.makeText(this,"账号异地登陆！请修改密码！",Toast.LENGTH_LONG);
			Util.preference.edit().putBoolean("ydlogin", false).commit();
		}

		Thinksns.addActivity(this);
		instance = this;
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		inflater = getLayoutInflater();
	}

	private void initHost() {
		String currHost= MyApplication.getCurrentHost();
		if(!TextUtils.isEmpty(currHost)){
			String basU_hua=currHost.substring(currHost.lastIndexOf("/")+1,currHost.length());
			TLUrl.URL_BASE=currHost;
			TLUrl.URL_huayouhui=basU_hua;
			TLUrl.getInstance().isChange=true;
		}
	}


	public BDLocationListener myListener = new MyLocationListener();
	private void initBaiduLbs() {

		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener( myListener );    //注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
		);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=1000;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		mLocationClient.setLocOption(option);
		mLocationClient.start();

	}


	public String current_lng;
	public String current_lat;
	double latitude  ;//获取经度
	double longitude  ;//获取纬度

	public LocationClient mLocationClient;
	private static final int BAIDU_READ_PHONE_STATE =100;

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			final Intent intent = new Intent(BAIDULOCATION);

			intent.putExtra("location", location.getCity() + "·" + location.getDistrict());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sendBroadcast(intent);
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sendBroadcast(intent);
			}

			//Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果

				latitude = location.getLatitude();//获取经度
				longitude = location.getLongitude();//获取纬度
				current_lat = String.valueOf(latitude);
				current_lng = String.valueOf(longitude);

				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// 单位度
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				latitude = location.getLatitude();//获取经度
				longitude = location.getLongitude();//获取纬度
				current_lat = String.valueOf(latitude);
				current_lng = String.valueOf(longitude);

				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				latitude = location.getLatitude();//获取经度
				longitude = location.getLongitude();//获取纬度
				current_lat = String.valueOf(latitude);
				current_lng = String.valueOf(longitude);

				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			sb.append("\nlocationdescribe : ");
			sb.append(location.getLocationDescribe());// 位置语义化信息
			List<Poi> list = location.getPoiList();// POI数据
			if (list != null) {
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list) {
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}

				Log.i("BaiduLocationApiDem_app", sb.toString());
				Log.i("uuuu_lat_app", latitude + "");
				Log.i("uuuu_lng_app", longitude + "");

				MyApplication.my_current_lat=current_lat;
				MyApplication.my_current_lng=current_lng;
				mLocationClient.stop();
			}
		}
	}
	TextView pro;
	private void initStartView() {
		pro = (TextView) findViewById(R.id.tljr_txt_jindu);
		bar = (ImageView) findViewById(R.id.tljr_pro_qd);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		bj = (ImageView) findViewById(R.id.tljr_img_qdbj);
		String url=null;
		try {
				if(!TextUtils.isEmpty(aCache.getAsString("qi_dong2"))){
					url=aCache.getAsString("qi_dong2");
				}else if(aCache.getAsJSONArray("qi_dong2_arr")!=null&&aCache.getAsJSONArray("qi_dong2_arr").length()>0){
					 url = aCache.getAsJSONArray("qi_dong2_arr").getString(new Random().nextInt(aCache.getAsJSONArray("qi_dong2_arr").length()));
				}else {
					url="";
				}

			if(!TextUtils.isEmpty(url)){
				MyApplication.imageLoader.displayImage(url,bj,MyApplication.getListOptions());
			}

			Log.i(TAG, "initStartView: "+url);

			} catch (Exception e) {
				e.printStackTrace();
			}

		bar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pro_move_left_in));
		((TextView) findViewById(R.id.tljr_txt_info)).setText(getVersion());
		initApp();
	}


	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return this.getString(R.string.version_name) + "V" + version;
		} catch (Exception e) {
			e.printStackTrace();
			return this.getString(R.string.can_not_find_version_name);
		}
	}
	private void initHandler() {
		handlerUI = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
			     if(msg.arg1==GET_KEY){
					Class<? extends Activity> clz;
					if(msg.what==LOGIN){
					//	clz = ActivityLogin.class;
//						clz = WXEntryActivity.class;  //原来的
						clz = ActivityHome.class;
					}else{
						clz = RegisterActivity.class;
					}
					//进入登录主页
					ActivityStack.startActivity(ThinksnsActivity.this, clz);
					finish();
				}
			}
		};
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	protected void initApp() {
		app = (Thinksns) this.getApplicationContext();
		//				app.initApi();  // 初始化华友会的数据库
		if (NetworkUtils.isNetAvailable(getApplicationContext())) {
//		if (app.HasLoginUser()&& NetworkUtils.isNetAvailable(getApplicationContext())) {
			// 已经有登录的用户，直接进入主页
			Log.i("zjz","有网");
			if(getIntent().getBundleExtra(AppConstant.YYG_PUSH) != null){
				//云购中奖
				Log.i("zjz","云购中奖");
				new AutoLogin(ThinksnsActivity.this,true,getIntent().getBundleExtra(AppConstant.YYG_PUSH));
			}else {
				new AutoLogin(ThinksnsActivity.this);
			}
		}else {
			Log.i("zjz","无网");
		}
//		else {

			if(!Util.preference.getBoolean(MyString.ISFIRST,false)){
				//第一次进入
				Log.i("zjz","首次进入");
				MyApplication.getInstance().saveIsFirstLocal(true);
				showGuide();
			}else {
				Message msg=new Message();
				msg.arg1 = GET_KEY;
				msg.what = LOGIN;
				handlerUI.sendMessageDelayed(msg, 2000);
			}
//		}
	}

	boolean initGuide = false;

	private void showGuide() {
		if (!initGuide) {
			pageViews = new ArrayList<View>();
			if (inflater!=null) {
				pageViews.add(inflater.inflate(R.layout.guideitem1, null));
//				pageViews.add(inflater.inflate(R.layout.guideitem2, null));
				pageViews.add(inflater.inflate(R.layout.guideitem3, null));
//				pageViews.add(inflater.inflate(R.layout.guideitem4, null));
				View view4=inflater.inflate(R.layout.guideitem4, null);
				ImageView img_lijikaiqi= (ImageView) view4.findViewById(R.id.img_lijikaiqi);
				View v_click= (View) view4.findViewById(R.id.v_click);
				v_click.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Message msg=new Message();
						msg.arg1 = GET_KEY;
						msg.what = LOGIN;
						handlerUI.sendMessage(msg);
					}
				});
				pageViews.add(view4);
				guide = (ViewGroup) inflater.inflate(R.layout.guide, null);
				
				viewPager = (ViewPager) guide.findViewById(R.id.guidePages);
				viewPager.setAdapter(new GuidePageAdapter());
				tv_login = (TextView) guide.findViewById(R.id.tv_login);
				tv_register = (TextView) guide.findViewById(R.id.tv_register);

				ll_find_ads_dots = (LinearLayout) guide.findViewById(R.id.ll_find_ads_dot);
				smalldots = new ImageView[pageViews.size()];
				for (int i = 0; i < smalldots.length; i++) {
					smalldot = new RoundImageView(this);

					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(30, 0, 0, 0);
					smalldot.setLayoutParams(lp);
					smalldots[i] = smalldot;
					if (i == 0) {
						smalldots[i].setBackgroundResource(R.drawable.dot_ring_checked);
					} else {
						smalldots[i].setBackgroundResource(R.drawable.dot_ring_unchecked);
					}
					ll_find_ads_dots.addView(smalldots[i]);
				}

				viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int i, float v, int i1) {

					}

					@Override
					public void onPageSelected(int i) {
						if (smalldots == null)
							return;
//						for (int j = 0; j < smalldots.length; j++) {
//							if (j == i) {
//								smalldots[j].setBackgroundResource(R.drawable.dot_ring_checked);
//							} else {
//								smalldots[j].setBackgroundResource(R.drawable.dot_ring_unchecked);
//							}
//						}
					}

					@Override
					public void onPageScrollStateChanged(int i) {

					}
				});

				tv_login.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Message msg=new Message();
						msg.arg1 = GET_KEY;
						msg.what = LOGIN;
						handlerUI.sendMessage(msg);
					}
				});
				tv_register.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Message msg=new Message();
						msg.arg1 = GET_KEY;
						msg.what = REGISTER;
						handlerUI.sendMessage(msg);
					}
				});
				initGuide = true;
			}
			setContentView(guide);
			}
	}

	class GuidePageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}

		public void finishUpdate(View arg0) {

		}

	}

}