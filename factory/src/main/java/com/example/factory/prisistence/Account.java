package com.example.factory.prisistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.factory.Factory;

public class Account {
    private  static final  String KEY_PUSH_ID = "KEY_PUSH_ID";
    private  static final  String KEY_IS_BIND = "KEY_IS_BIND";
    private static  String pushId ;
    private static  boolean isBind ;
    public static String getPushId(){
        return pushId;
    }
    public static  void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND,isBind)
                .apply();

    }
    public static void setPushId(String pushId){
        Account.pushId = pushId;
        Account.save(Factory.app());

    }


    public static boolean isLogin(){
        return true;
    }

    public  static void load(Context context){
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
       pushId = sp.getString(KEY_PUSH_ID,"");
       isBind = sp.getBoolean(KEY_IS_BIND,false);
    }

    public  static  boolean isBind(){
       return  false;
    }
    public static void setBind(boolean isBind){
       Account.isBind = isBind;
       Account.save(Factory.app());
    }
}
