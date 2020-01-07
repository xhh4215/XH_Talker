package com.example.factory.presenter.account;
import com.xiaohei.factory.presenter.BaseConstarct;
public interface RegisterConstact {
    interface View extends BaseConstarct.View<Presenter>{
         void registerSuccess();
    }
    interface Presenter extends BaseConstarct.Presenter {
        void register(String phone ,String name,String password);
        boolean check(String phone);


    }
}
