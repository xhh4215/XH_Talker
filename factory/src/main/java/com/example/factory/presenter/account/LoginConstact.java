package com.example.factory.presenter.account;


import com.xiaohei.factory.presenter.BaseConstarct;

public interface LoginConstact {

    interface View extends  BaseConstarct.View<Presenter>{
        void LoginSuccess();
     }

    interface Presenter extends BaseConstarct.Presenter {
        void Login(String phone, String name, String password);

        boolean check(String phone);

    }
}
