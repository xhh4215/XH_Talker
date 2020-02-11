package com.example.factory.data.helper;

import android.util.Log;

import com.example.factory.Factory;
import com.example.factory.R;
import com.example.factory.model.api.account.RspModel;
import com.example.factory.model.api.user.UserUpdateModel;
import com.example.factory.model.card.UserCard;
import com.example.factory.model.db.User;
import com.example.factory.model.db.User_Table;
import com.example.factory.net.NetWork;
import com.example.factory.net.RemoteService;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiaohei.factory.data.DataSource;
import com.xiaohei.utils.CollectionUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class UserHelper {
    // 更新用户信息的操作，异步的
    public static void update(UserUpdateModel model, final DataSource.CallBack<UserCard> callBack) {
        RemoteService service = NetWork.remote();
        Call<RspModel<UserCard>>  call = service.userUpdate(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()){
                       UserCard userCard = rspModel.getResult();
                       Factory.getUsercenter().dispatch(userCard);
                       callBack.onDataLoaded(userCard);
                }else{
                    Factory.decodeRspCode(rspModel,callBack);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable throwable) {
                Log.d("TAG",throwable.getMessage());
                callBack.onDataNotAvailable(R.string.data_network_error);

            }
        });
    }
    //搜索用户
    public static Call search(String name, final DataSource.CallBack<List<UserCard>> callBack) {
        RemoteService service = NetWork.remote();
        Call<RspModel<List<UserCard>>>  call = service.userSearch(name);
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()){
                    callBack.onDataLoaded(rspModel.getResult());
                }else{
                    Factory.decodeRspCode(rspModel,callBack);

                }

            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable throwable) {
                callBack.onDataNotAvailable(R.string.data_network_error);

            }
        });
        return call;
    }
    //关注
    public static void follow(String followId, final DataSource.CallBack<UserCard> callBack) {
        RemoteService service = NetWork.remote();
        Call<RspModel<UserCard>>  call = service.userFollow(followId);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()){
                    UserCard userCard = rspModel.getResult();
                    Factory.getUsercenter().dispatch(userCard);
                    callBack.onDataLoaded(userCard);
                }else{
                    Factory.decodeRspCode(rspModel,callBack);
                }

            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable throwable) {
                callBack.onDataNotAvailable(R.string.data_network_error);

            }
        });
    }
    // 刷新联系人的操作
    public static void refreshContacts() {
        RemoteService service = NetWork.remote();
        service.userContacts()
                .enqueue(new Callback<RspModel<List<UserCard>>>() {
                    @Override
                    public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                        RspModel<List<UserCard>> rspModel = response.body();
                        if (rspModel.success()) {
                            // 返回数据
                        List<UserCard> cards = rspModel.getResult();
                        if (cards==null||cards.size()==0)
                            return;
                        Factory.getUsercenter().dispatch(CollectionUtil.toArray(cards,UserCard.class));
                        } else {
                            Factory.decodeRspCode(rspModel, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                     }
                });
    }
    // 从本地查询一个用户的信息
    public static User findFromLocal(String id) {
        return SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }

    public static User findFromNet(String id) {
        RemoteService service = NetWork.remote();
        try {
            Response<RspModel<UserCard>> response = service.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {
                User user = card.build();
                 Factory.getUsercenter().dispatch(card);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 搜索一个用户，优先本地缓存，
     * 没有用然后再从网络拉取
     */
    public static User search(String id) {
        User user = findFromLocal(id);
        if (user == null) {
            return findFromNet(id);
        }
        return user;
    }
    /**
     * 搜索一个用户，优先网络查询
     * 没有用然后再从本地缓存拉取
     */
    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
            return findFromLocal(id);
        }
        return user;
    }
}
