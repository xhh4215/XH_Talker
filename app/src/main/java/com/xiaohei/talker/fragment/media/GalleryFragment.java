package com.xiaohei.talker.fragment.media;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.widget.GalleryView;
import com.xiaohei.talker.R;


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
        return new BottomSheetDialog(getContext());
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
            mListener=null;
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
}
