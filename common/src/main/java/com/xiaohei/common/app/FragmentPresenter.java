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
       Application.showToast(str);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;

    }


}
