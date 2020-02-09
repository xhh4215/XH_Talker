package com.example.factory.data.helper;

import android.util.Log;

import com.example.factory.Factory;
import com.example.factory.R;
import com.example.factory.model.api.account.RspModel;
import com.example.factory.model.api.user.UserUpdateModel;
import com.example.factory.model.card.UserCard;
import com.example.factory.model.db.User;
import com.example.factory.net.NetWork;
import com.example.factory.net.RemoteService;
import com.xiaohei.factory.data.DataSource;

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
                       User user  = userCard.build();
                       user.save();
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
                    User user = userCard.build();
                    user.save();
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

}
