package com.example.factory.presenter.account;

import android.text.TextUtils;

import com.example.factory.R;
import com.example.factory.data.helper.AccountHelper;
import com.example.factory.model.api.RegisterModel;
import com.example.factory.model.db.User;
import com.xiaohei.common.Common;
import com.xiaohei.factory.data.DataSource;
import com.xiaohei.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

public class RegisterPresenter  extends BasePresenter<RegisterConstact.View> implements RegisterConstact.Presenter, DataSource.CallBack<User> {

    public RegisterPresenter(RegisterConstact.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        start();
        RegisterConstact.View view = getmView();

        if (!check(phone)) {
            view.showError(R.string.data_account_register_invalid_parameter_mobile);

        } else if (name.length() < 2) {
            view.showError(R.string.data_account_register_invalid_parameter_name);

        } else if (password.length() < 6) {
            view.showError(R.string.data_account_register_invalid_parameter_password);

        } else {
            RegisterModel model = new RegisterModel(phone, password, name);
            AccountHelper.register(model, this);
        }

    }

    @Override
    public boolean check(String phone) {
        return !TextUtils.isEmpty(phone) && Pattern.matches(Common.Constance.REGES_MODLE, phone);
    }

    @Override
    public void onDataNotAvailable( final int str) {
        final RegisterConstact.View view = getmView();
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

    @Override
    public void onDataLoaded(User user) {
        final RegisterConstact.View view = getmView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });
    }
}
