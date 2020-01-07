package com.xiaohei.factory.presenter;

import androidx.annotation.StringRes;

public interface BaseConstarct {
    interface View<T extends Presenter>{
        void showError(@StringRes int str);
        void showLoading();
        void setPresenter(T presenter);
    }
    interface Presenter{

        void start();
        void destory();
    }
}
