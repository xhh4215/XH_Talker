package com.example.factory.net;

import com.example.factory.model.api.account.AccountRspModel;
import com.example.factory.model.api.account.RegisterModel;
import com.example.factory.model.api.account.RspModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemoteService {
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);
}
