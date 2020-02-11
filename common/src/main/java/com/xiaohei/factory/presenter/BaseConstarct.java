package com.xiaohei.factory.presenter;

import androidx.annotation.StringRes;

import com.xiaohei.common.widget.recycler.RecyclerAdapter;


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

    interface  RecyclerView<T extends Presenter,ViewHolder> extends View<T>{
        RecyclerAdapter<ViewHolder> getRecyclerViewApater();
        void onAdapterDataChanged();

    }
}
