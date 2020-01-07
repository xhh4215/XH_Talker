package com.example.factory.data.helper;

import com.example.factory.Factory;
import com.example.factory.R;
import com.example.factory.model.api.account.AccountRspModel;
import com.example.factory.model.api.account.RegisterModel;
import com.example.factory.model.api.account.RspModel;
import com.example.factory.model.db.User;
import com.example.factory.net.NetWork;
import com.example.factory.net.RemoteService;
import com.xiaohei.factory.data.DataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountHelper {


    public static  void register(final RegisterModel model, final DataSource.CallBack<User> callBack){
        RemoteService service = NetWork.getRetrofit().create(RemoteService.class);
        Call<RspModel<AccountRspModel>> call =  service.accountRegister(model);
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
               RspModel<AccountRspModel> rspModel = response.body();
               if (rspModel.success()) {
                   AccountRspModel accountmodel = rspModel.getResult();
                   if (accountmodel.isBind()){
                       User user = accountmodel.getUser();
                   // 数据库写入和缓存
                   callBack.onDataLoaded(user);
               }else{
                       // 绑定设置的操作
                       bindPush(callBack);

                   }
               }else{
                   Factory.decodeRspCode(rspModel,callBack);
               }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable throwable) {
             callBack.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /***
     * 对设备id绑定
     * @param callBack
     */
    public static void bindPush( final DataSource.CallBack<User> callBack){
      callBack.onDataNotAvailable(R.string.app_name);
    }
}
