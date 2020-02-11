package com.example.factory.data.user;

import androidx.annotation.NonNull;

import com.example.factory.data.helper.DbHelper;
import com.example.factory.model.db.User;
import com.example.factory.model.db.User_Table;
import com.example.factory.prisistence.Account;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.xiaohei.factory.data.DataSource;

import java.util.LinkedList;
import java.util.List;

/**
 * 联系人仓库
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class ContactRepository implements ContactDataSource, QueryTransaction.QueryResultListCallback<User>, DbHelper.ChangedListener<User> {
      private DataSource.SuccessCallback<List<User>> callback;
    @Override
    public void load(DataSource.SuccessCallback<List<User>> callback) {
        this.callback = callback;
        // 添加一个数据更新的监听
        DbHelper.addChangedListener(User.class,this);
        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    public void dispose() {
    this.callback = null;
    //    取消对数据变更的监听
    DbHelper.removeChangedListener(User.class,this);

    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<User> tResult) {
        if (tResult.size()==0){
            users.clear();
            notifyDataChange();
            return;
        }
        User[] users = tResult.toArray(new User[0]);
        onDataSave(users);
    }

    @Override
    public void onDataSave(User... list) {
        boolean isChange = false ;
         //当数据库数据变更的操作
        for (User user:list){
            if (isRequire(user)){
                insertOrUpdate(user);
                isChange = true;
            }
        }
        if (isChange){
            notifyDataChange();
        }
    }
    List<User> users = new LinkedList<>();
    private void   insertOrUpdate(User  user){
       int index = indexOf(user);
       if (index>=0){
         replace(index,user);
       }else{
           insert(user);
       }

   }

   private  void insert(User user){
        users.add(user);

   }
   private void replace( int index,User user){
        users.remove(index);
        users.add(index,user);

   }
   private int indexOf(User user){
        int index = -1;
        for (User user1:users){
            index++;
            if (user1.isSame(user)){
                return index;
            }
        }
        return -1;
   }

   private void notifyDataChange(){
        if (callback!=null){
            callback.onDataLoaded(users);
        }
    }
    @Override
    public void onDataDelete(User... list) {
        boolean isChange = false;
        // 当数据库数据删除的操作
        for (User user:list){
           if( users.remove(user)){
                isChange = true;
            }
        }
        if (isChange){
            notifyDataChange();
        }


    }
    // 检查是不是我关注的user
    private  boolean isRequire(User user){
         boolean isRequire =  user.isFollow() && !user.getId().equals(Account.getUserId());
    return isRequire;

    }
}
