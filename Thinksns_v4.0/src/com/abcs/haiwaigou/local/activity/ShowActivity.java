package com.abcs.haiwaigou.local.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.abcs.huaqiaobang.model.BaseActivity;
import com.abcs.sociax.android.R;

public class ShowActivity extends BaseActivity {

    private ImageView img_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity_show);
        img_pic= (ImageView) findViewById(R.id.img_pic);
        setImageBitmap(getImageFormBundle());
    }

    /**
     * 将MainActivity传过来的图片显示在界面当中
     *
     * @param bytes
     */
    public void setImageBitmap(byte[] bytes) {
        Bitmap cameraBitmap = byte2Bitmap();
        // 根据拍摄的方向旋转图像（纵向拍摄时要需要将图像选择90度)
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        cameraBitmap = Bitmap
                .createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(),
                        cameraBitmap.getHeight(), matrix, true);
        img_pic.setImageBitmap(cameraBitmap);
    }

    /**
     * 从Bundle对象中获取数据
     *
     * @return
     */
    public byte[] getImageFormBundle() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        byte[] bytes = data.getByteArray("bytes");
        return bytes;
    }

    /**
     * 将字节数组的图形数据转换为Bitmap
     *
     * @return
     */
    private Bitmap byte2Bitmap() {
        byte[] data = getImageFormBundle();
        // 将byte数组转换成Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }
}
