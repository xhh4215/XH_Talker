package com.xiaohei.talker;

import android.text.TextUtils;

public class Presenter implements IPresenter {
    private IView mView;

    public Presenter(IView view) {
        mView = view;
    }

    @Override
    public void search() {
        //开启界面loading
        String inputString = mView.getInputString();
        if (TextUtils.isEmpty(inputString)) {
            return;
        }
        int hashcode = inputString.hashCode();
        IUserService service = new UserService();
        String result = "Result" + service.search(hashcode);
        //关闭界面loading
        mView.setResultString(result);
    }

}
