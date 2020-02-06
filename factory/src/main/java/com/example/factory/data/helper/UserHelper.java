package com.example.factory.data.helper;

import com.example.factory.Factory;
import com.example.factory.R;
import com.example.factory.model.api.account.RspModel;
import com.example.factory.model.api.user.UserUpdateModel;
import com.example.factory.model.card.UserCard;
import com.example.factory.net.NetWork;
import com.example.factory.net.RemoteService;
import com.xiaohei.factory.data.DataSource;

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
