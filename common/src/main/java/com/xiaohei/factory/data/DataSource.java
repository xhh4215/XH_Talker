package com.xiaohei.factory.data;

import androidx.annotation.StringRes;

public interface DataSource {
    interface  CallBack<T> extends SuccessCallback<T>,FailCallback{

    }
    interface  SuccessCallback<T>{
        void  onDataLoaded(T user);
    }
    interface  FailCallback{
        void  onDataNotAvailable(@StringRes int  str);
    }
}
