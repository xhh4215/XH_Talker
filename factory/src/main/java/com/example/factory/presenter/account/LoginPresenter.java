package com.example.factory.presenter.account;

import android.text.TextUtils;

import com.example.factory.R;
import com.example.factory.data.helper.AccountHelper;
import com.example.factory.model.api.account.LoginModel;
import com.example.factory.model.db.User;
import com.example.factory.prisistence.Account;
import com.xiaohei.factory.data.DataSource;
import com.xiaohei.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class LoginPresenter extends BasePresenter<LoginConstact.View> implements LoginConstact.Presenter, DataSource.CallBack<User> {
    public LoginPresenter(LoginConstact.View view) {
        super(view);
    }

    @Override
    public void Login(String phone, String password) {
        start();
        final LoginConstact.View view  = getView();
        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)){
         view.showError(R.string.data_account_login_invalid_parameter);
        }else{
            LoginModel model = new LoginModel(phone,password, Account.getPushId());
            AccountHelper.Login(model,this);
        }

    }

    @Override
    public void onDataLoaded(User user) {
        final LoginConstact.View view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.LoginSuccess();
            }
        });

    }

    @Override
    public void onDataNotAvailable(final int str) {
        final LoginConstact.View view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(str);
            }
        });


    }
}
