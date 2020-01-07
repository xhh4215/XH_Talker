package com.example.factory.presenter.account;

import com.xiaohei.factory.presenter.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginConstact.View> implements LoginConstact.Presenter {
    public LoginPresenter(LoginConstact.View view) {
        super(view);
    }

    @Override
    public void Login(String phone, String password) {

    }

}
