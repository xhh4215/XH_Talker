package com.example.factory.prisistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.factory.Factory;
import com.example.factory.model.api.account.AccountRspModel;
import com.example.factory.model.db.User;
import com.example.factory.model.db.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class Account {
    private  static final  String KEY_PUSH_ID = "KEY_PUSH_ID";
    private  static final  String KEY_IS_BIND = "KEY_IS_BIND";
    private  static final  String KEY_IS_TOKEN = "KEY_IS_TOKEN";
    private  static final  String KEY_IS_USERID = "KEY_IS_USERID";
    private  static final  String KEY_IS_ACCOUNT = "KEY_IS_ACCOUNT";
    private static  String pushId ;
    private static  boolean isBind ;
    private static  String token ;
    private static  String userId ;
    private static  String account ;
    public static String getPushId(){
        return pushId;
    }
    public static  void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND,isBind)
                .putString(KEY_IS_TOKEN,token)
                .putString(KEY_IS_USERID,userId)
                .putString(KEY_IS_ACCOUNT,account)
                .apply();

    }
    public static void setPushId(String pushId){
        Account.pushId = pushId;
        Account.save(Factory.app());

    }
    public static void Login(AccountRspModel model){
        Account.token = model.getToken();
        Account.account = model.getAccount();
        Account.userId = model.getUser().getId();
        save(Factory.app());


    }

    public static boolean isLogin(){
        return !TextUtils.isEmpty(userId)&& !TextUtils.isEmpty(token);
    }

    public static boolean isComplete(){
        // 首先保证登录成功
        if (isLogin()) {
            User self = getUser();
            return !TextUtils.isEmpty(self.getDesc())
                    && !TextUtils.isEmpty(self.getPortrait())
                    && self.getSex() != 0;
        }
        // 未登录返回信息不完全
        return false;
    }

    public  static void load(Context context){
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID,"");
        isBind = sp.getBoolean(KEY_IS_BIND,false);
        token = sp.getString(KEY_IS_TOKEN,"");
        userId = sp.getString(KEY_IS_USERID,"");
        account = sp.getString(KEY_IS_ACCOUNT,"");
    }

    public  static  boolean isBind(){
       return  isBind;
    }
    public static void setBind(boolean isBind){
       Account.isBind = isBind;
       Account.save(Factory.app());
    }

    public static User getAccount(){
        return TextUtils.isEmpty(userId)?new User():
                SQLite.select()
                        .from(User.class)
                        .where(User_Table.id.eq(userId))
                        .querySingle();
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return User
     */
    public static User getUser() {
        // 如果为null返回一个new的User，其次从数据库查询
        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(userId))
                .querySingle();
    }

    /**
     * 返回用户Id
     *
     * @return 用户Id
     */
    public static String getUserId() {
        return getUser().getId();
    }
    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    public static String getToken() {
        return token;
    }
}
