package com.example.factory.presenter.contact;

import androidx.recyclerview.widget.DiffUtil;

import com.example.factory.data.helper.UserHelper;
import com.example.factory.data.user.ContactDataSource;
import com.example.factory.data.user.ContactRepository;
import com.example.factory.model.db.User;
import com.example.factory.utils.DiffUiDataCallback;
import com.xiaohei.common.widget.recycler.RecyclerAdapter;
import com.xiaohei.factory.data.DataSource;
import com.xiaohei.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * 联系人的Presenter实现
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class ContactPresenter extends BaseRecyclerPresenter<User,ContactContract.View>
        implements ContactContract.Presenter, DataSource.SuccessCallback<List<User>> {
    ContactDataSource mSource;
    public ContactPresenter(ContactContract.View view) {
        super(view);
        mSource = new ContactRepository();
    }
    @Override
    public void start() {
        super.start();
        //进行本地的数据加载同时添加监听
        mSource.load(this);
        // 加载网络数据
        UserHelper.refreshContacts();

        // TODO 问题：
        // 1.关注后虽然存储数据库，但是没有刷新联系人
        // 2.如果刷新数据库，或者从网络刷新，最终刷新的时候是全局刷新
        // 3.本地刷新和网络刷新，在添加到界面的时候会有可能冲突；导致数据显示异常
        // 4.如何识别已经在数据库中有这样的数据了


    }
    @Override
    public void onDataLoaded(List<User> user) {
     ContactContract.View view=  getView();
     if (view==null)
         return;
        RecyclerAdapter <User> adapter = view.getRecyclerViewApater();
       List<User> old =  adapter.getItems();
        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, user);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        // 调用基类 刷新
      refreshData(result,user);


    }

    @Override
    public void destory() {
        super.destory();
        mSource.dispose();
    }
}
