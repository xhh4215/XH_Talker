package com.xiaohei.talker.fragment.media;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.tools.UiTool;
import com.xiaohei.common.widget.GalleryView;
import com.xiaohei.talker.R;

import net.qiujuer.genius.ui.Ui;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {

    private GalleryView mGallery;
    private onSelectedListener mListener;

    public GalleryFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGallery = root.findViewById(R.id.galleryView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGallery.setup(getLoaderManager(), this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        //如果选中一张图片
        if (count > 0) {
            //就隐藏自己
            dismiss();
        }
        if (mListener != null) {
            //得到所有的选中的图片的路径
            String[] paths = mGallery.getSelectedPath();
            //返回第一张
            mListener.onSelectedImage(paths[0]);
            mListener = null;
        }
    }

    /***
     * 设置事件监听 并返回自己
     * @param listener  onSelectedListener
     * @return GalleryFragment
     */
    public GalleryFragment setListener(onSelectedListener listener) {
        mListener = listener;
        return this;
    }

    public interface onSelectedListener {
        void onSelectedImage(String path);
    }


    private static class TransStatusBottomSheetDialog extends BottomSheetDialog {

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Window window = getWindow();
            if (window == null) {
                return;

            }
            //得到屏幕的高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            //获得状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());
            //计算dialog高度
            int dialogDialog = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogDialog <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogDialog);

        }
    }
}
