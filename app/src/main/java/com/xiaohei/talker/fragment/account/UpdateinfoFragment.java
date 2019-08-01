package com.xiaohei.talker.fragment.account;

import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.widget.PortraitView;
import com.xiaohei.talker.R;

import butterknife.BindView;
import butterknife.OnClick;

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

    }
}
