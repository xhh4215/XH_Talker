package com.example.factory.data.user;

import com.example.factory.model.db.User;
import com.xiaohei.factory.data.DataSource;

import java.util.List;

/**
 * 联系人数据源
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface ContactDataSource {
    /***
     * 对数据进行加载的职责
     * @param callback  加载成功的回调
     */
  void load(DataSource.SuccessCallback<List<User>> callback);

    /***
     * 对数据的销毁操作
     */
  void dispose();
}
