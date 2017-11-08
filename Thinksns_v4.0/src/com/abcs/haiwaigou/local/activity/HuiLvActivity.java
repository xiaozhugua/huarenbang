package com.abcs.haiwaigou.local.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.local.beans.HuiLv;
import com.abcs.haiwaigou.utils.NumberUtils;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.model.Options;
import com.abcs.huaqiaobang.util.HttpRequest;
import com.abcs.huaqiaobang.util.HttpRevMsg;
import com.abcs.huaqiaobang.ytbt.common.utils.ToastUtil;
import com.abcs.sociax.android.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.abcs.huaqiaobang.activity.TurnInActivity.index;

public class HuiLvActivity extends BaseActivity {

    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.t_title_name)
    TextView tTitleName;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.activity_hui_lv)
    RelativeLayout activityHuiLv;
    @InjectView(R.id.rel_change)
    RelativeLayout rel_change;

    HuiLvAdapter huiLvAdapter;
    List<HuiLv.MsgBean> msgBeanList=new ArrayList<>();  // 保存原始数据
    double money=0;
    double hl=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hui_lv);
        ButterKnife.inject(this);
        getDatasFromNet();
    }

    Handler handler=new Handler();
    private void getDatasFromNet() {
        try {
            ProgressDlgUtil.showProgressDlg("", this);
//       https://japi.tuling.me/hrq/conListDetail/v2/getHl
            HttpRequest.sendGet(TLUrl.getInstance().URL_bind_base+"/hrq/conListDetail/v2/getHl","", new HttpRevMsg() {
                @Override
                public void revMsg(final String msg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("zds", "local_msg===" + msg);
                            if (TextUtils.isEmpty(msg)) {
                                ToastUtil.showMessage("服务器出现点小问题，一会儿再来！");
                                return;
                            }else {
                                msgBeanList.clear();
                                HuiLv huiLv= new Gson().fromJson(msg, HuiLv.class);
                                if(huiLv!=null&&huiLv.status==1&&huiLv.msg!=null&&huiLv.msg.size()>0){
                                    huiLvAdapter=new HuiLvAdapter(HuiLvActivity.this,huiLv.msg);
                                    listview.setAdapter(huiLvAdapter);
                                    msgBeanList.addAll(huiLv.msg);

                                    rel_change.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 先隐藏键盘
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘

                                            goToChange(money,hl,msgBeanList);
                                        }
                                    });
                                }
                            }

                            ProgressDlgUtil.stopProgressDlg();
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class HuiLvAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater inflater;
        private List<HuiLv.MsgBean> data=new ArrayList<>();

        public HuiLvAdapter(Context mcontext, List<HuiLv.MsgBean> data) {
            this.mcontext = mcontext;
            inflater = LayoutInflater.from(mcontext);
            this.data = data;
        }

        public void addDatas(List<HuiLv.MsgBean> msgBeanList){
            data.clear();
            if(msgBeanList!=null&&msgBeanList.size()>0){
                data.addAll(msgBeanList);
                notifyDataSetChanged();
            }
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rootView;
            final ViewHolder holder;
            rootView = inflater.inflate(R.layout.item_local_huilv, parent, false);
            holder = new ViewHolder(rootView);
            /////////////////////////////
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.item_local_huilv, parent, false);
//                holder = new ViewHolder(convertView);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }

            try {
                final HuiLv.MsgBean bean = data.get(position);
                if (bean != null && !TextUtils.isEmpty(bean.img)) {
                    ImageLoader.getInstance().displayImage(bean.img, holder.image, Options.getListOptions2());
                }
                if (bean != null && !TextUtils.isEmpty(bean.hbName)) {
                    holder.tvMoneyName.setText(bean.hbName);
                }
                if (bean != null && !TextUtils.isEmpty(bean.hbEnname)) {
                    holder.tvName.setText(bean.hbEnname);
                }
                if (bean != null && bean.relect>0) {
                    holder.tvNum.setText(NumberUtils.formatPriceNo(bean.relect));
                }


            /*防止ListView点击的时候Edittext失去焦点   stard*/
                holder.tvNum.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            index = position;
                        }
                        return false;
                    }
                });
                holder.tvNum.clearFocus();
                if (index != -1 && index == position) {
                    // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
                    holder.tvNum.requestFocus();
                }

                holder.tvNum.setSelection( holder.tvNum .getText().length());
/*防止ListView点击的时候Edittext失去焦点   end*/

                holder.tvNum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(s!=null&&!TextUtils.isEmpty(s.toString())){
                            money=Double.valueOf(s.toString());
                            hl=bean.hl;
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return rootView;
        }

        public class ViewHolder {
            @InjectView(R.id.image)
            ImageView image;
            @InjectView(R.id.relative_item)
            RelativeLayout relative_item;
            @InjectView(R.id.tv_name)
            TextView tvName;
            @InjectView(R.id.tv_num)
            EditText tvNum;
            @InjectView(R.id.tv_money_name)
            TextView tvMoneyName;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }



    private void goToChange(double money,double hl,List<HuiLv.MsgBean> msgBeanList){

        try {
            List<HuiLv.MsgBean> listdatas=new ArrayList<>();
            listdatas.clear();
            for (int i = 0; i < msgBeanList.size(); i++) {
                HuiLv.MsgBean  bean=msgBeanList.get(i);
                HuiLv.MsgBean itemBean=new HuiLv.MsgBean();
                if(bean!=null&&bean.hl>0){
                    itemBean.hl= bean.hl;
                    itemBean.hbEnname= bean.hbEnname;
                    itemBean.hbName= bean.hbName;
                    itemBean.img= bean.img;
                    itemBean.relect=  hl*money/bean.hl;
                    listdatas.add(itemBean);
                }
            }

            huiLvAdapter.addDatas(listdatas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.relative_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                finish();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                break;
        }
    }
}
