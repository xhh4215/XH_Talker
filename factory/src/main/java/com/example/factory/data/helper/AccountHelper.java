package com.example.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import com.example.factory.Factory;
import com.example.factory.R;
import com.example.factory.model.api.account.AccountRspModel;
import com.example.factory.model.api.account.LoginModel;
import com.example.factory.model.api.account.RegisterModel;
import com.example.factory.model.api.account.RspModel;
import com.example.factory.model.db.User;
import com.example.factory.net.NetWork;
import com.example.factory.net.RemoteService;
import com.example.factory.prisistence.Account;
import com.xiaohei.factory.data.DataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountHelper {


    public static  void register(final RegisterModel model, final DataSource.CallBack<User> callBack){
        RemoteService service = NetWork.remote();
        Call<RspModel<AccountRspModel>> call =  service.accountRegister(model);
       call.enqueue(new AccountRspCallBack(callBack));
    }
    public static  void Login(final LoginModel model, final DataSource.CallBack<User> callBack){
        RemoteService service = NetWork.remote();
        Call<RspModel<AccountRspModel>> call =  service.accountLogin(model);
        call.enqueue(new AccountRspCallBack(callBack));
    }

    /***
     * 对设备id绑定
     * @param callBack
     */
    public static void bindPush( final DataSource.CallBack<User> callBack){
        String pushid = Account.getPushId();
        if (TextUtils.isEmpty(pushid))
            return;
        RemoteService service = NetWork.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushid);
        call.enqueue(new AccountRspCallBack(callBack));


    }

    private static class AccountRspCallBack implements Callback<RspModel<AccountRspModel>> {
        final DataSource.CallBack<User> callback;

        public AccountRspCallBack(DataSource.CallBack<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()) {
                AccountRspModel accountmodel = rspModel.getResult();
                final User user = accountmodel.getUser();
                user.save();
//                       DatabaseDefinition definition = FlowManager.getDatabase(AppDataBase.class);
//                       definition.beginTransactionAsync(new ITransaction() {
//                           @Override
//                           public void execute(DatabaseWrapper databaseWrapper) {
//                               FlowManager.getModelAdapter(User.class).save(user);
//                           }
//                       }).build().execute();
                // 数据库写入和缓存
                Account.Login(accountmodel);
                if (accountmodel.isBind()){
                    // 设置绑定状态为True
                    Account.setBind(true);
                    if (callback!=null)
                        callback.onDataLoaded(user);
                }else{
                    // 绑定设置的操作
                    bindPush(callback);

                }
            }else{
                Factory.decodeRspCode(rspModel,callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable throwable) {
            if (callback!=null)
            callback.onDataNotAvailable(R.string.data_network_error);
            Log.d("tag",throwable.getLocalizedMessage());
        }
    }
}
