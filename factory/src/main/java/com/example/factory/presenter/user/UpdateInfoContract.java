package com.example.factory.presenter.user;

import com.xiaohei.factory.presenter.BaseConstarct;

public interface UpdateInfoContract {
    interface Presenter extends BaseConstarct.Presenter{
        void update(String PhotoFilePath,String desc,boolean isMan);

    }

    interface View extends BaseConstarct.View<Presenter>{
        void updateSuccess();
    }
}
