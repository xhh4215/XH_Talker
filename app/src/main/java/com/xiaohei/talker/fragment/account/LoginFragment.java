package com.xiaohei.talker.fragment.account;
import android.accounts.Account;
import android.content.Context;

import com.xiaohei.common.app.Fragment;
import com.xiaohei.talker.R;

/**
 */
public class LoginFragment extends Fragment {
    private AccountTrigger mAccountTrigger;

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

    @Override
    public void onResume() {
        super.onResume();
        mAccountTrigger.triggerView();
    }
}
