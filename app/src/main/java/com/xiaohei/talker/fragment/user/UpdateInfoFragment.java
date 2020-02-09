package com.xiaohei.talker.fragment.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.factory.Factory;
import com.example.factory.presenter.user.UpdateInfoContract;
import com.example.factory.presenter.user.UpdateInfoPresenter;
import com.xiaohei.common.app.Application;
import com.xiaohei.common.app.FragmentPresenter;
import com.xiaohei.common.widget.PortraitView;
import com.xiaohei.talker.R;
import com.xiaohei.talker.activities.MainActivity;
import com.xiaohei.talker.fragment.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 用户信息更新的fragment
 */
public class UpdateInfoFragment extends FragmentPresenter<UpdateInfoContract.Presenter> implements UpdateInfoContract.View {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.im_sex)
    ImageView mSex;

    @BindView(R.id.edit_desc)
    EditText mDesc;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;

    // 头像的本地路径
    private String mPortraitPath;
    private boolean isMan = true;

    public UpdateInfoFragment() {
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
            Application.showToast(R.string.data_rsp_error_unknown);

        }
    }

    /***
     * 加载Uri到当前的头像
     * @param uri  图像的资源地址
     */
    private void loadPortrait(Uri uri) {
        mPortraitPath = uri.getPath();
        Glide.with(Factory.app().getApplicationContext())
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }



    @OnClick(R.id.im_sex)
    void onSexClick() {
        // 性别图片点击的时候触发
        isMan = !isMan; // 反向性别
        Drawable drawable = getResources().getDrawable(isMan ?
                R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
        mSex.setImageDrawable(drawable);
        // 设置背景的层级，切换颜色
        mSex.getBackground().setLevel(isMan ? 0 : 1);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = mDesc.getText().toString();
        // 调用P层进行注册
        mPresenter.update(mPortraitPath, desc, isMan);
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        // 当需要显示错误的时候触发，一定是结束了

        // 停止Loading
        mLoading.stop();
        // 让控件可以输入
        mDesc.setEnabled(true);
        mPortrait.setEnabled(true);
        mSex.setEnabled(true);
        // 提交按钮可以继续点击
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();

        // 正在进行时，正在进行注册，界面不可操作
        // 开始Loading
        mLoading.start();
        // 让控件不可以输入
        mDesc.setEnabled(false);
        mPortrait.setEnabled(false);
        mSex.setEnabled(false);
        // 提交按钮不可以继续点击
        mSubmit.setEnabled(false);
    }

    @Override
    public void updateSuccess() {
        // 更新成功跳转到主界面
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @Override
    protected UpdateInfoContract.Presenter initPreenter() {
        // 初始化Presenter
        return new UpdateInfoPresenter(this);
    }
}
