package com.xiaohei.common.app;

import android.content.Context;

import com.xiaohei.factory.presenter.BaseConstarct;

public abstract class FragmentPresenter<Presenter extends BaseConstarct.Presenter> extends Fragment implements BaseConstarct.View<Presenter> {
    protected  Presenter mPresenter;
    protected  abstract Presenter initPreenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPreenter();
    }

    @Override
    public void showError(int str) {
        if (mPlaceHolderView!=null){
            mPlaceHolderView.triggerError(str);
        }else {
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView!=null){
            mPlaceHolderView.triggerLoading();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;

    }


}
