package com.xiaohei.talker.fragment.main;


import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.widget.GalleryView;
import com.xiaohei.talker.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment {
    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();
        checkPermission();

    }

    /***
     * 动态运行时权限的使用
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
//            mGallery.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
//                @Override
//                public void onSelectedCountChanged(int count) {
//
//                }
//            });
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    mGallery.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
////                        @Override
////                        public void onSelectedCountChanged(int count) {
////
////                        }
////                    });
////                } else {
////                    Toast.makeText(getContext(), "没有权限", Toast.LENGTH_LONG).show();
////                }
//        }
//    }
}
