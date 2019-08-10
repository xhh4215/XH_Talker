package com.xiaohei.talker.fragment.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;




import com.bumptech.glide.Glide;
import com.example.factory.Factory;
import com.example.factory.net.UploadHelper;

import com.xiaohei.common.app.Application;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.widget.PortraitView;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;


import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 用户信息更新的fragment
 */
public class UpdateinfoFragment extends Fragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    public UpdateinfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_updateinfo;
    }

    @OnClick(R.id.im_portrait)
    public void onPortraitClicked() {
        new GalleryFragment().setListener(new GalleryFragment.onSelectedListener() {
            @Override
            public void onSelectedImage(String path) {
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);//设置图片处理格式
                options.setCompressionQuality(96);//设置压缩后的图片精度
                // 得到压缩的缓存地址
                File dPath = Application.getPortraitTmpFile();
                //发起剪切
                UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(520, 520)
                        .withOptions(options)
                        .start(getActivity());
             }
        }).show(getChildFragmentManager(), GalleryFragment.class.getName());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //收到从Activity传递过来的回调，然后取出去处其中的值进行加载图片
        //如果是我能处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);

        }
    }

    /***
     * 加载Uri到当前的头像
     * @param uri  图像的资源地址
     */
    private void loadPortrait(Uri uri) {
        Glide.with(Factory.app().getApplicationContext())
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);

        // 拿到本地文件的地址
        final String localPath = uri.getPath();
        Log.e("TAG", "localPath:" + localPath);
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
              String url =   UploadHelper.uploadPortrait(localPath);
                Log.e("TAG", "url:" + url);
            }
        });


    }
}
