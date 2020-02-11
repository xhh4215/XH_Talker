package com.example.factory.presenter.contact;


import com.example.factory.model.db.User;
import com.xiaohei.factory.presenter.BaseConstarct;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface ContactContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseConstarct.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseConstarct.RecyclerView<Presenter, User>{

    }
}
