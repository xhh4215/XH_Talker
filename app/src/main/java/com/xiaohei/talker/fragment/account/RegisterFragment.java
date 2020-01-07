package com.xiaohei.talker.fragment.account;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.example.factory.presenter.account.RegisterConstact;
import com.example.factory.presenter.account.RegisterPresenter;
import com.xiaohei.common.app.FragmentPresenter;
import com.xiaohei.talker.R;
import com.xiaohei.talker.activities.MainActivity;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class RegisterFragment extends FragmentPresenter<RegisterConstact.Presenter> implements RegisterConstact.View {
    private AccountTrigger mAccountTrigger;
    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;


    @Override
    protected RegisterConstact.Presenter initPreenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }
    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String name = mName.getText().toString();
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        mPresenter.register(phone,name,password);


    }
    @OnClick(R.id.txt_go_login)
    void onShowLoginCklick(){
        mAccountTrigger.triggerView();

    }

    @Override
    public void showError(int str) {
        super.showError(str);
        mLoading.stop();
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassword.setEnabled(false);
        mSubmit.setEnabled(false);
    }

    @Override
    public void registerSuccess() {
        MainActivity.show(this.getContext());
        getActivity().finish();
    }
}
