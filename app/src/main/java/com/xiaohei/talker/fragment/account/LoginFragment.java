package com.xiaohei.talker.fragment.account;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.example.factory.presenter.account.LoginConstact;
import com.example.factory.presenter.account.LoginPresenter;
import com.xiaohei.common.app.FragmentPresenter;
import com.xiaohei.talker.R;
import com.xiaohei.talker.activities.MainActivity;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class LoginFragment extends FragmentPresenter<LoginConstact.Presenter> implements LoginConstact.View {
    private AccountTrigger mAccountTrigger;
    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;


    @Override
    protected LoginConstact.Presenter initPreenter() {
        return  new LoginPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }
    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        mPresenter.Login(phone,password);


    }
    @OnClick(R.id.txt_go_login)
    void onShowRegisterCklick(){
        mAccountTrigger.triggerView();

    }
    @Override
    public void LoginSuccess() {
        MainActivity.show(this.getContext());
        getActivity().finish();
    }
    @Override
    public void showError(int str) {
        super.showError(str);
        mLoading.stop();
        mPhone.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();
        mPhone.setEnabled(false);
         mPassword.setEnabled(false);
        mSubmit.setEnabled(false);
    }
    @Override
    public void setPresenter(LoginConstact.Presenter presenter) {

    }
}
