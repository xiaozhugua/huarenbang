package com.abcs.haiwaigou.local.activity;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcs.haiwaigou.broadcast.MyUpdateUI;
import com.abcs.haiwaigou.local.beans.FiletersBean;
import com.abcs.haiwaigou.local.beans.FiletersBeanX;
import com.abcs.haiwaigou.local.beans.MsgBean;
import com.abcs.haiwaigou.local.beans.TagsBean;
import com.abcs.haiwaigou.model.ImageItem;
import com.abcs.haiwaigou.utils.PhotoDialog;
import com.abcs.haiwaigou.utils.PictureUtil;
import com.abcs.huaqiaobang.MyApplication;
import com.abcs.huaqiaobang.dialog.ProgressDlgUtil;
import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.huaqiaobang.tljr.news.widget.InputTools;
import com.abcs.huaqiaobang.util.capturePhoto.CapturePhoto;
import com.abcs.huaqiaobang.wxapi.WXEntryActivity;
import com.abcs.sociax.android.R;
import com.abcs.sociax.t4.android.temp.SelectImageListener;
import com.abcs.sociax.t4.unit.UnitSociax;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.thinksns.sociax.thinksnsbase.utils.TLUrl;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishMessageActivity2 extends BaseActivity {


    @InjectView(R.id.t_title)
    TextView tTitle;
    @InjectView(R.id.relative_back)
    RelativeLayout relativeBack;
    @InjectView(R.id.relative_top)
    RelativeLayout relativeTop;
    @InjectView(R.id.gv_preview)
    GridView gvPreview;
    @InjectView(R.id.imageHoriScroll)
    HorizontalScrollView imageHoriScroll;
    @InjectView(R.id.ed_title)
    EditText edTitle;
    @InjectView(R.id.ed_content)
    EditText edContent;
    @InjectView(R.id.id_flowlayout_diqu)
    TagFlowLayout idFlowlayoutDiqu;
    @InjectView(R.id.id_flowlayout_gongzhong)
    TagFlowLayout idFlowlayoutGongzhong;
    @InjectView(R.id.id_flowlayout_gongqiu)
    TagFlowLayout idFlowlayoutGongqiu;
    @InjectView(R.id.id_flowlayout_xingzhi)
    TagFlowLayout idFlowlayoutXingzhi;
    @InjectView(R.id.id_flowlayout_tag)
    TagFlowLayout idFlowlayoutTag;
    @InjectView(R.id.ed_address)
    EditText edAddress;
    @InjectView(R.id.ed_phone)
    EditText edPhone;
    @InjectView(R.id.t_publish)
    TextView tPublish;
    @InjectView(R.id.tv_diqu)
    TextView tvDiqu;
    @InjectView(R.id.tv_gongzhong)
    TextView tvGongzhong;
    @InjectView(R.id.tv_gongqiu)
    TextView tvGongqiu;
    @InjectView(R.id.tv_xingzhi)
    TextView tvXingzhi;
    @InjectView(R.id.tv_biaoqiao)
    TextView tvBiaoqiao;
    @InjectView(R.id.ed_weixinhao)
    EditText edWeixinhao;
    @InjectView(R.id.ed_lainxiren)
    EditText edLainxiren;

    private static final String PUBLISH = "1";

    @InjectView(R.id.linear_take_photo)
    LinearLayout linearTakePhoto;
    @InjectView(R.id.relative_pic)
    RelativeLayout relativePic;

    int max = 0;
    PhotoDialog photoDialog;
    protected SelectImageListener listener_selectImage;   //拍照工具
    @InjectView(R.id.liner1)
    LinearLayout liner1;
    @InjectView(R.id.liner2)
    LinearLayout liner2;
    @InjectView(R.id.liner3)
    LinearLayout liner3;
    @InjectView(R.id.liner4)
    LinearLayout liner4;
    @InjectView(R.id.liner_tag)
    LinearLayout liner_tag;

    private String my_tag1="";
    private String my_tag2="";
    private String my_tag3="";
    private String my_tag4="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_publish_message2);
        ButterKnife.inject(this);

        tTitle.setText(getIntent().getStringExtra("title"));
        initPicviews();
//        Init();
        capture = new CapturePhoto(this);
        initTags();
        if (listener_selectImage == null)
            listener_selectImage = new SelectImageListener(this);
        photoDialog = new PhotoDialog(this);
    }

    String cityId, menuId;
    List<String> stringBuilder = new ArrayList<>();
    List<String> stringBuilderGongZhong = new ArrayList<>();

    /*标签*/
    private void initTags() {
        cityId = getIntent().getStringExtra("cityId");
        menuId = getIntent().getStringExtra("menuId");
        final MsgBean msgBean = (MsgBean) getIntent().getSerializableExtra("filter");

        if (msgBean != null) {

            if (msgBean.fileter != null && msgBean.fileter.size() > 0) {
                if (msgBean.fileter.size() == 1) {
                    liner1.setVisibility(View.VISIBLE);
                    final FiletersBeanX filetersBeanX = msgBean.fileter.get(0);
                    tvDiqu.setText(filetersBeanX.filterName);
                    if (filetersBeanX.fileters != null && filetersBeanX.fileters.size() > 0) {
                        idFlowlayoutDiqu.setMaxSelectCount(1);
                        idFlowlayoutDiqu.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag1=filetersBeanX.fileters.get(position).id + "";
                                }
                            }
                        });
                        idFlowlayoutDiqu.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutDiqu, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }
                }

                if (msgBean.fileter.size() == 2) {
                    liner1.setVisibility(View.VISIBLE);
                    liner2.setVisibility(View.VISIBLE);
                    final FiletersBeanX filetersBeanX = msgBean.fileter.get(0);
                    tvDiqu.setText(filetersBeanX.filterName);
                    if (filetersBeanX.fileters != null && filetersBeanX.fileters.size() > 0) {
                        idFlowlayoutDiqu.setMaxSelectCount(1);
                        idFlowlayoutDiqu.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag1=filetersBeanX.fileters.get(position).id + "";
                                }

                            }
                        });
                        idFlowlayoutDiqu.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutDiqu, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }

                    final FiletersBeanX filetersBeanX2 = msgBean.fileter.get(1);
                    tvGongzhong.setText(filetersBeanX2.filterName);
                    if (filetersBeanX2.fileters != null && filetersBeanX2.fileters.size() > 0) {
                        idFlowlayoutGongzhong.setMaxSelectCount(1);
                        idFlowlayoutGongzhong.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag2=filetersBeanX2.fileters.get(position).id + "";
                                }

                            }
                        });
                        idFlowlayoutGongzhong.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX2.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutGongzhong, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }
                }

                if (msgBean.fileter.size() == 3) {
                    liner1.setVisibility(View.VISIBLE);
                    liner2.setVisibility(View.VISIBLE);
                    liner3.setVisibility(View.VISIBLE);
                    final FiletersBeanX filetersBeanX = msgBean.fileter.get(0);
                    tvDiqu.setText(filetersBeanX.filterName);
                    if (filetersBeanX.fileters != null && filetersBeanX.fileters.size() > 0) {
                        idFlowlayoutDiqu.setMaxSelectCount(1);
                        idFlowlayoutDiqu.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag1=filetersBeanX.fileters.get(position).id + "";
                                }
                            }
                        });
                        idFlowlayoutDiqu.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutDiqu, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }

                    final FiletersBeanX filetersBeanX2 = msgBean.fileter.get(1);
                    tvGongzhong.setText(filetersBeanX2.filterName);
                    if (filetersBeanX2.fileters != null && filetersBeanX2.fileters.size() > 0) {
                        idFlowlayoutGongzhong.setMaxSelectCount(1);
                        idFlowlayoutGongzhong.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag2=filetersBeanX2.fileters.get(position).id + "";
                                }

                            }
                        });
                        idFlowlayoutGongzhong.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX2.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutGongzhong, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }

                    final FiletersBeanX filetersBeanX3 = msgBean.fileter.get(2);
                    tvGongqiu.setText(filetersBeanX3.filterName);
                    if (filetersBeanX3.fileters != null && filetersBeanX3.fileters.size() > 0) {
                        idFlowlayoutGongqiu.setMaxSelectCount(5);
                        idFlowlayoutGongqiu.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
//                                for (Integer position : selectPosSet) {
//                                    my_tag3=filetersBeanX3.fileters.get(position).id + "";
//                                }
                                Log.i("zds", "onSelected: " + selectPosSet.size());
                                stringBuilderGongZhong.clear();
                                for (Integer position : selectPosSet) {
                                    if (!stringBuilderGongZhong.contains(filetersBeanX3.fileters.get(position).id + "")) {
                                        stringBuilderGongZhong.add(filetersBeanX3.fileters.get(position).id + "");
                                    }
                                }
                            }
                        });
                        idFlowlayoutGongqiu.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX3.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutGongqiu, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }
                }

                if (msgBean.fileter.size() == 4) {
                    liner1.setVisibility(View.VISIBLE);
                    liner2.setVisibility(View.VISIBLE);
                    liner3.setVisibility(View.VISIBLE);
                    liner4.setVisibility(View.VISIBLE);
                    final FiletersBeanX filetersBeanX = msgBean.fileter.get(0);
                    tvDiqu.setText(filetersBeanX.filterName);
                    if (filetersBeanX.fileters != null && filetersBeanX.fileters.size() > 0) {
                        idFlowlayoutDiqu.setMaxSelectCount(1);
                        idFlowlayoutDiqu.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag1=filetersBeanX.fileters.get(position).id + "";
                                }
                            }
                        });
                        idFlowlayoutDiqu.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutDiqu, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }

                    final FiletersBeanX filetersBeanX2 = msgBean.fileter.get(1);
                    tvGongzhong.setText(filetersBeanX2.filterName);
                    if (filetersBeanX2.fileters != null && filetersBeanX2.fileters.size() > 0) {
                        idFlowlayoutGongzhong.setMaxSelectCount(1);
                        idFlowlayoutGongzhong.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag2=filetersBeanX2.fileters.get(position).id + "";
                                }

                            }
                        });
                        idFlowlayoutGongzhong.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX2.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutGongzhong, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }

                    final FiletersBeanX filetersBeanX3 = msgBean.fileter.get(2);
                    tvGongqiu.setText(filetersBeanX3.filterName);
                    if (filetersBeanX3.fileters != null && filetersBeanX3.fileters.size() > 0) {
                        idFlowlayoutGongqiu.setMaxSelectCount(5);
                        idFlowlayoutGongqiu.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
//                                for (Integer position : selectPosSet) {
//                                    my_tag3=filetersBeanX3.fileters.get(position).id + "";
//                                }

                                Log.i("zds", "onSelected: " + selectPosSet.size());
                                stringBuilderGongZhong.clear();
                                for (Integer position : selectPosSet) {
                                    if (!stringBuilderGongZhong.contains(filetersBeanX3.fileters.get(position).id + "")) {
                                        stringBuilderGongZhong.add(filetersBeanX3.fileters.get(position).id + "");
                                    }
                                }

                            }
                        });
                        idFlowlayoutGongqiu.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX3.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutGongqiu, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }

                    final FiletersBeanX filetersBeanX4 = msgBean.fileter.get(3);
                    tvXingzhi.setText(filetersBeanX4.filterName);
                    if (filetersBeanX4.fileters != null && filetersBeanX4.fileters.size() > 0) {
                        idFlowlayoutXingzhi.setMaxSelectCount(1);
                        idFlowlayoutXingzhi.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                for (Integer position : selectPosSet) {
                                    my_tag4=filetersBeanX4.fileters.get(position).id + "";
                                }

                            }
                        });
                        idFlowlayoutXingzhi.setAdapter(new TagAdapter<FiletersBean>(filetersBeanX4.fileters) {
                            @Override
                            public View getView(FlowLayout parent, int position, FiletersBean filetersBean) {
                                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutXingzhi, false);
                                tv.setText(filetersBean.name);
                                return tv;
                            }
                        });
                    }
                }
            }

          /*  标签*/
            if (msgBean.tags != null) {
                if(msgBean.tags.size() > 0){
                    liner_tag.setVisibility(View.VISIBLE);
                    idFlowlayoutTag.setMaxSelectCount(5);
                    stringBuilder.clear();
                    idFlowlayoutTag.setAdapter(new TagAdapter<TagsBean>(msgBean.tags) {

                        @Override
                        public View getView(FlowLayout parent, int position, TagsBean s) {
                            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.tv, idFlowlayoutTag, false);
                            tv.setText(s.name);
                            return tv;
                        }
                    });

                    idFlowlayoutTag.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                        @Override
                        public void onSelected(Set<Integer> selectPosSet) {
                            Log.i("zds", "onSelected: " + selectPosSet.size());
                            stringBuilder.clear();
                            for (Integer position : selectPosSet) {
                                if (!stringBuilder.contains(msgBean.tags.get(position).name)) {
                                    stringBuilder.add(msgBean.tags.get(position).name);
                                }
                            }
                        }
                    });
                }else {
                    liner_tag.setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick({R.id.relative_back, R.id.t_publish, R.id.linear_take_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                finish();
                break;
            case R.id.t_publish:
                try {
                    if (TextUtils.isEmpty(MyApplication.getInstance().getMykey())) {
                        Intent ty = new Intent(PublishMessageActivity2.this, WXEntryActivity.class);
                        ty.putExtra("isthome", true);
                        startActivity(ty);
                    } else {
                        actCommit(PUBLISH);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.linear_take_photo:
                InputTools.HideKeyboard(view);
                photoDialog.show();
                break;
        }
    }

    Handler handler = new Handler();
    private CapturePhoto capture;


    private boolean modify() {
        if (TextUtils.isEmpty(edTitle.getText().toString())) {
            showToast("标题不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(edContent.getText().toString())) {
            showToast("内容不能为空！");
            return false;
        }
//        if (TextUtils.isEmpty(edAddress.getText().toString())) {
//            showToast("地址不能不能为空！");
//            return false;
//        }
        if (TextUtils.isEmpty(edPhone.getText().toString())) {
            showToast("联系方式不能为空！");
            return false;
        }
        return true;
    }

    private ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();
    GridAdapter adapter;
//    PopupWindow pop;
//    LinearLayout ll_popup;

//    public void Init() {
//
//        pop = new PopupWindow(this);
//
//        View view = getLayoutInflater().inflate(R.layout.hwg_item_popwindow, null);
//
//        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//
//        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        pop.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        pop.setFocusable(true);
//        pop.setAnimationStyle(R.style.photostyle);
//        pop.setOutsideTouchable(true);
//        pop.setContentView(view);
//
//        LinearLayout line_images = (LinearLayout) view.findViewById(R.id.line_images);
//        LinearLayout line_camera = (LinearLayout) view.findViewById(R.id.line_camera);
//        LinearLayout line_cancel = (LinearLayout) view.findViewById(R.id.line_cancel);
//        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
//        parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//
//        line_images.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                selectPhoto();
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        line_camera.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                photo();
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        line_cancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
////                camera();
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//
//    }
//
//    public void camera() {
//        capture.dispatchTakePictureIntent(CapturePhoto.SHOT_IMAGE,
//                1);
//    }

    @Override
    protected void onRestart() {
        if (needPicture()) {
            adapter.update();
        }
        super.onRestart();
    }

//    //选择相册
//    private void selectPhoto() {
//        Intent getImage = new Intent(this, MultiImageSelectorActivity.class);
//        getImage.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
//        getImage.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
//        getImage.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//        getImage.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, Bimp.address);
//        startActivityForResult(getImage, StaticInApp.LOCAL_IMAGE);
//    }
//
//    private void photo() {
//        listener_selectImage.cameraImage();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoDialog.IMAGES && resultCode == RESULT_OK && data != null) {
            Uri contentUri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            //这里需要不管是照相的图片，还是选择的图片，都需要统一使用file://的格式
            String filePath = "file://" + cursor.getString(column_index);
            photoDialog.setImageTempUri(Uri.parse(filePath));
            Log.i("zjz", "pic_images=" + photoDialog.getImageTempUri().getPath());
            save(cursor.getString(column_index));
        }

        if (requestCode == PhotoDialog.CAMERA && resultCode == RESULT_OK) {
            Log.i("zjz", "pic_camera=" + photoDialog.getImageTempUri().getPath());
            save(photoDialog.getImageTempUri().getPath());
        }

    }

    private void save(String path) {
        if (path != null) {
            try {
                File f = new File(path);
                Bitmap bm = PictureUtil.getSmallBitmap(path);
                FileOutputStream fos = new FileOutputStream(new File(
                        PictureUtil.getAlbumDir(), "small_" + f.getName()));
                bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
                Log.i("zjz", "路径为：" + PictureUtil.getAlbumDir() + "/" + "small_" + f.getName());

                uploadPictrue(PictureUtil.getAlbumDir() + "/" + "small_" + f.getName(), bm);
                photoDialog.cancel();
            } catch (Exception e) {
                Log.i("zjz", "error", e);
            }


        }
    }

    private void uploadPictrue(final String filePath, final Bitmap bitmap) {
        final String path = filePath.replace("file:///", "/");
        Log.i("zjz", "path=" + path);
        ProgressDlgUtil.showProgressDlg("Loading...", this);
        final String putURL = TLUrl.getInstance().URL_local_upload_pic;
        final HttpUtils hu = new HttpUtils(20000);
        final RequestParams params = new RequestParams();
        params.addBodyParameter("file[]", new File(path));
        handler.post(new Runnable() {
            @Override
            public void run() {
                hu.send(HttpRequest.HttpMethod.POST, putURL, params,
                        new RequestCallBack<String>() {
                            @Override
                            public void onFailure(HttpException arg0, String arg1) {
                                ProgressDlgUtil.stopProgressDlg();
                                showToast("上传图片失败");
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> arg0) {
                                Log.i("zjz", "result=" + arg0.result);
                                ImageItem takePhoto = new ImageItem();
                                takePhoto.setFile_url(arg0.result);
                                takePhoto.setBitmap(bitmap);
                                tempSelectBitmap.add(takePhoto);
                                Log.i("zjz", "imageList=" + tempSelectBitmap.size());
                                imageHoriScroll.setVisibility(View.VISIBLE);
                                relativePic.setVisibility(View.GONE);
                                ProgressDlgUtil.stopProgressDlg();
                                adapter.notifyDataSetChanged();
                            }
                        });
            }
        });

    }

    //是否需要拍照、选相册
    protected boolean needPicture() {
        return true;
    }

    //初始化图片控件
    private void initPicviews() {
        adapter = new GridAdapter(this, gvPreview);
        adapter.update();
        gvPreview.setAdapter(adapter);
        gvPreview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                InputTools.HideKeyboard(view);
                if (arg2 == tempSelectBitmap.size()) {
                    InputTools.HideKeyboard(view);
                    photoDialog.show();
                }
            }
        });

    }

    /**
     * 图片加载
     */
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        private GridView mgridViwew;

        private int imgWidth = 0;   //图片显示宽高
        private int horizontalSpacing;  //图片左右间隙
        private int gridViewHeight;     //列表高度

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context, GridView gridView) {
            inflater = LayoutInflater.from(context);
            this.mgridViwew = gridView;
            imgWidth = UnitSociax.dip2px(context, 120);
            horizontalSpacing = UnitSociax.dip2px(context, 3);
            gridViewHeight = UnitSociax.dip2px(context, 120);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            int count = 0;
            if (tempSelectBitmap.size() == 0) {
                imageHoriScroll.setVisibility(View.GONE);
                relativePic.setVisibility(View.VISIBLE);
                count = 1;
            } else if (tempSelectBitmap.size() == 9) {
                imageHoriScroll.setVisibility(View.VISIBLE);
                relativePic.setVisibility(View.GONE);
                count = 9;
            } else {
                imageHoriScroll.setVisibility(View.VISIBLE);
                relativePic.setVisibility(View.GONE);
                count = tempSelectBitmap.size() + 1;
            }
            //计算列表的宽度
            int gridviewWidth = count * imgWidth + (count - 1) * horizontalSpacing;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    gridviewWidth, gridViewHeight);
            mgridViwew.setLayoutParams(params);         // 重点
            mgridViwew.setNumColumns(count);
            return count;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.hwg_item_grida_image,
                        parent, false);
                holder = new ViewHolder();
                holder.img_bg_fengmain = (ImageView) convertView.findViewById(R.id.img_bg_fengmain);
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                holder.delete = (ImageView) convertView.findViewById(R.id.img_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imgWidth, imgWidth);
            holder.image.setLayoutParams(params);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tempSelectBitmap.remove(position);
                    Log.i("zjz", "tempSize=" + tempSelectBitmap.size());
                    if (tempSelectBitmap.size() == 0) {
                        imageHoriScroll.setVisibility(View.GONE);
                        relativePic.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
            });

            if(position==0){
                holder.img_bg_fengmain.setVisibility(View.VISIBLE);
            }else {
                holder.img_bg_fengmain.setVisibility(View.INVISIBLE);
            }

            if (position == tempSelectBitmap.size()) {
                holder.delete.setVisibility(View.GONE);
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_add_pic));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(tempSelectBitmap.get(position).getBitmap());
                holder.delete.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public ImageView delete;
            public ImageView img_bg_fengmain;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (max == tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }

    }


    private void actCommit(final String type) {
        try {
            String pics = null;
            if (tempSelectBitmap.size() != 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < tempSelectBitmap.size(); i++) {
                    stringBuffer.append(tempSelectBitmap.get(i).getFile_url());
                    if (i != tempSelectBitmap.size() - 1) {
                        stringBuffer.append(",");
                    }
                }
                pics = stringBuffer.toString();
                Log.i("zds", "pics=" + pics);
            }

            StringBuffer fileterIds = new StringBuffer();
            if(my_tag1.length()>0){
                fileterIds.append(my_tag1);
                fileterIds.append(",");
            }

            if(my_tag2.length()>0){
                fileterIds.append(my_tag2);
                fileterIds.append(",");
            }
            //////
            if (stringBuilderGongZhong.size() != 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < stringBuilderGongZhong.size(); i++) {
                    stringBuffer.append(stringBuilderGongZhong.get(i));
                    if (i != stringBuilderGongZhong.size() - 1) {
                        stringBuffer.append(",");
                    }
                }
                my_tag3 = stringBuffer.toString();

                fileterIds.append(my_tag3);
                fileterIds.append(",");
                Log.i("zds", "tagsgongqiu=" + my_tag3);
                Log.i("zds", "tagsgongqiu2=" + fileterIds);
            }

            //////
//        if(my_tag3.length()>0){
//            fileterIds.append(my_tag3);
//            fileterIds.append(",");
//        }
            if(my_tag4.length()>0){
                fileterIds.append(my_tag4);
            }

            Log.i("zds", "fileterIds=" + fileterIds);

            String tags = null;
            if (stringBuilder.size() != 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < stringBuilder.size(); i++) {
                    stringBuffer.append(stringBuilder.get(i));
                    if (i != stringBuilder.size() - 1) {
                        stringBuffer.append(",");
                    }
                }
                tags = stringBuffer.toString();
                Log.i("zds", "tags=" + tags);
            }

            String title = "";
            String content = "";
            String address = "";
            String phone = "";
            String weixinhao = "";
            String lianxiren = "";

            try {
                title = URLEncoder.encode(edTitle.getText().toString(), "utf-8");
                content = URLEncoder.encode(edContent.getText().toString(), "utf-8");
                address = URLEncoder.encode(edAddress.getText().toString(), "utf-8");
                phone = URLEncoder.encode(edPhone.getText().toString(), "utf-8");
                weixinhao = URLEncoder.encode(edWeixinhao.getText().toString(), "utf-8");
                lianxiren = URLEncoder.encode(edLainxiren.getText().toString(), "utf-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (modify()) {
                ProgressDlgUtil.showProgressDlg("Loading...", this);
                final String putURL = TLUrl.getInstance().URL_local_publish;
                final HttpUtils hu = new HttpUtils(20000);
                final RequestParams params = new RequestParams();
                params.addBodyParameter("cityId", cityId);
                params.addBodyParameter("title", title);
                params.addBodyParameter("content", content);
                params.addBodyParameter("contactMan", lianxiren);
                params.addBodyParameter("listTypeId", menuId);
                params.addBodyParameter("contact", phone);
                params.addBodyParameter("vx", weixinhao);
                params.addBodyParameter("address", address);
                params.addBodyParameter("filterId", fileterIds.toString());
                params.addBodyParameter("tags", tags);
                params.addBodyParameter("issueUser", MyApplication.getInstance().self.getId());
                params.addBodyParameter("isIssue", type);
                params.addBodyParameter("imgUrls", pics);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hu.send(HttpRequest.HttpMethod.POST, putURL, params,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onFailure(HttpException arg0, String arg1) {
                                        ProgressDlgUtil.stopProgressDlg();
                                        showToast("发布失败！请重试！");
                                    }

                                    @Override
                                    public void onSuccess(ResponseInfo<String> arg0) {
                                        Log.i("zjz", "publish_msg=" + arg0.result);
                                        try {
                                            JSONObject mainObj = new JSONObject(arg0.result);
                                            if (mainObj.optString("status").equals("1")) {
                                                showToast("发布成功！");
                                                MyUpdateUI.sendUpdateCollection(PublishMessageActivity2.this, "publish_success");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        finish();
                                                    }
                                                },1000);
                                            } else {
                                                showToast("发布失败，请重试！");
                                            }
                                            ProgressDlgUtil.stopProgressDlg();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
