package com.abcs.huaqiaobang.login;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.model.User;
import com.abcs.huaqiaobang.presenter.MyPrsenter;
import com.abcs.huaqiaobang.tljr.data.InitData;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.util.LogUtil;
import com.abcs.huaqiaobang.util.Util;
import com.abcs.sociax.t4.android.ActivityHome;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * @author xbw 接收Umeng推送过来的分享广播
 * @version 创建时间：2015-6-2 上午10:29:06
 */
public class LoginResultReceiver extends BroadcastReceiver {
    ActivityHome activity;
    public static LoginResultReceiver instance;
    private String userName;
    private String pwd;
    private ProgressDialog dialog;
    private User user;

    public static LoginResultReceiver getInstance(ActivityHome context) {
        if (null == instance) {
            instance = new LoginResultReceiver(context);
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.abct.occft.hq.login");
            try {
                context.registerReceiver(instance, filter);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return instance;
    }

    public LoginResultReceiver(ActivityHome context) {
        // TODO Auto-generated constructor stub
        this.activity = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getStringExtra("type").equals("login")
                && !intent.getStringExtra("msg").equals("")) {


            loginResult(intent.getStringExtra("msg"));

            Log.i("zjz","登录成功！！！！");
//            getUserLevel();//用户等级，经验
//            getUserEvent();
//            activity.my.loadData();
//            activity.huanQiuShiShi.refreshFragment();
            //zjz更新海外购购物车数量
//            activity.main.initCarNum();


//            activity.mHandler.sendEmptyMessage(5);
//			if(MyApplication.getInstance().self!=null) {
//				activity.main.getHotNews();
//			}


//            MyApplication myApplication = MyApplication.getInstance();
//            ChartActivity.getYunZhiToken(myApplication.self.getId(), myApplication.self.getNickName()
//                    , null, myApplication.self.getAvatarUrl());
        } else if (intent.getStringExtra("type").equals("logout")) {
//            activity.main.logout();
            if (!Util.preference.getString("logintype", "").equals("")) {
                Util.preference.edit().putString("lizai_userName", "");
            }
            Util.preference.edit().putString("lizai_pwd", "").commit();
            Util.preference.edit().putString("logintype", "").commit();
            Util.preference.edit().putString("token", "").commit();
            Util.preference.edit().putString("cityhistory", "").commit();

            //zjz
            Util.preference.edit().putBoolean("isDefault", false).commit();
            Util.preference.edit().putString("address_id", "").commit();
            Util.preference.edit().putString("area_id", "").commit();
            Util.preference.edit().putString("city_id", "").commit();
            Util.preference.edit().putString("address", "").commit();
            Util.preference.edit().putString("name", "").commit();
            Util.preference.edit().putString("phone", "").commit();

            InitData.isrefreshByUser = true;
            MyApplication.getInstance().self = null;
            MyApplication.getInstance().setMykey(null);
            MyUpdateUI.sendUpdateCarNum(activity);
            if (activity.my != null)
                new MyPrsenter(activity.my).loginSuccess();
        }
//		activity.current.initUser();
//		 activity.mHandler.sendEmptyMessage(101);
    }

    private void loginResult(String msg) {
        LogUtil.e("loginResult", msg);
//        dialog = new ProgressDialog(activity);
//        dialog.setMessage("正在登陆");
//        dialog.show();

//        ProgressDlgUtil.showProgressDlg("正在登陆", activity);
        try {
            JSONObject object = new JSONObject(msg);
            /*货行数据*/
            if(object.has("userLocal")){

                JSONArray userLocal=object.optJSONArray("userLocal");
                if(userLocal!=null&&userLocal.length()>0){
                    MyApplication.getInstance().userLocal=userLocal;
                }
            }
            /*货行数据*/

            MyApplication.getInstance().self = new User();
            MyApplication.getInstance().self.setBindId(object.optString("id"));
            MyApplication.getInstance().self.setId(object.optString("uid"));
            MyApplication.getInstance().self.setUserName(object
                    .optString("uname"));
            MyApplication.getInstance().self.setNickName(object
                    .optString("nickname"));
//            MyApplication.getInstance().self.setArea(object
//                    .optString("location"));
            MyApplication.getInstance().self.setFrom(object.optString("from"));
            MyApplication.getInstance().self.setLast(object
                    .optJSONObject("last"));
            MyApplication.getInstance().self.setSex(object
                    .optString("gender"));
            MyApplication.getInstance().self.setAvatarUrl(object
                    .optString("avatar"));
            MyApplication.getInstance().setMykey(object
                    .optString("key"));
            JSONObject bind = object.getJSONObject("bind");
            if (!bind.has("err")) {
                MyApplication.getInstance().self.setEmail(bind
                        .optString("email"));
                MyApplication.getInstance().self.setPhone(bind
                        .optString("phone"));
                MyApplication.getInstance().self.setInvail(bind
                        .optBoolean("emailVerify"));
                Log.i("emailVerify", "emailVerify" + bind
                        .optBoolean("emailVerify"));
                MyApplication.getInstance().self.setIsInvailPhone(bind
                        .optBoolean("phoneVerify"));
            }
            if (!Util.isThirdLogin) {

                Editor editor = Util.preference.edit();
                if (!Util.preference.getString("logintype", "").equals("")) {
                    editor.putString("lizai_userName", "");
                } else {
                    editor.putString("lizai_userName",
                            MyApplication.getInstance().self.getUserName());
                }
                editor.putString("news_p_id", object.optString("uid"));

                editor.commit();
            }
            userName = Util.preference.getString("lizai_userName", "");
            pwd = Util.preference.getString("lizai_pwd", "");
            user = new User();
            user.setId(object.getString("id"));
            user.setUname(object.getString("uname"));
            user.setFromUser(object.getString("from"));
            user.setUid(object.getInt("uid"));
            user.setNickname(object
                    .getString("nickname"));
            user.setAvatar(object.getString("avatar"));
            user.setGender(object.getInt("gender"));
            user.setLocation(object
                    .getString("location"));
            MyApplication.getInstance().setAvater(
                    object.getString("avatar"));
            MyApplication.getInstance().setUid(
                    object.getInt("uid"));
            MyApplication
                    .getInstance()
                    .setOwnernickname(
                            object.getString("nickname"));
            Log.i("zjz", "userName=" + userName);
            Log.i("zjz", "object.getString(uname)=" + object.getString("uname"));
            Log.i("zjz", "login_key=" + MyApplication.getInstance().getMykey());
            loginForPOINT();
            loginForYYG(object.getString("uname"),object.getString("pwd")); // 登录一元云购

            EventBus.getDefault().post("login_success_for_every");  // 登录成功通知
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 登录一元云购
    private void loginForYYG(String u_name,String u_pwd) {

        Log.i("xuke1222","登录信息:u_name"+u_name+"====u_pwd"+u_pwd+"&uid="+MyApplication.getInstance().getUid());

        HttpRequest.sendPost(TLUrl.URL_YYG_LOGIN+"/"+u_name,"pwd="+u_pwd+"&uid="+MyApplication.getInstance().getUid(), new HttpRevMsg() { //现在
            @Override
            public void revMsg(String msg) {
                Log.i("xuke1222","登录信息:"+msg);     //以前:  登录信息:{"code":1,"result":"79350b6ac16d5ad987806996127310c9"}
                // TODO Auto-generated method stub
                if (msg.length() == 0) {
//                    showToast("服务器异常，请稍后再试");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(msg);
                    if (json!=null && json.has("state")){
                        int state = json.getInt("state");
                        if (state == 0){
                            //登录成功  拿到用户信息
                            JSONObject user_info = json.optJSONObject("user_info");
                            Log.i("xuke1223","登录之后的用户信息:"+user_info.toString());
                            MyApplication.getInstance().setUid_yyg(user_info.optString("uid"));
                        }else{
                            //登录失败
//                            showToast(state);
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }



    private void loginForPOINT() {
        HttpRequest.sendPost(TLUrl.getInstance().URL_hwg_login_for_point, "user_name=" + URLEncoder.encode(Util.preference.getString("lizai_userName", "")) + "&password="
                + Util.preference.getString("lizai_pwd", "") + "&key=" + MyApplication.getInstance().getMykey(), new HttpRevMsg() {
            @Override
            public void revMsg(String msg) {
                if (msg == null) {
                    return;
                }
                try {
                    JSONObject json = new JSONObject(msg);
                    if (json.optInt("code") == 200) {
                        if (json.optString("datas").contains("成功"))
                            Log.i("zjz", "成功获得积分");
                    }
                    Log.i("zjz", "login_for_point=" + msg);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
    }
}
