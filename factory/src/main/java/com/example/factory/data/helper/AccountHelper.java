package com.example.factory.data.helper;

import com.example.factory.R;
import com.example.factory.model.api.RegisterModel;
import com.example.factory.model.db.User;
import com.xiaohei.factory.data.DataSource;


public class AccountHelper {


    public static  void register(RegisterModel model, final DataSource.CallBack<User> callBack){
      new Thread(){
          @Override
          public void run() {
              super.run();
              try{
                  Thread.sleep(3000);
              }catch(  InterruptedException  e){
                  e.printStackTrace();

              }
              callBack.onDataNotAvailable(R.string.data_rsp_error_parameters);
          }

      }.start();
    }
}
