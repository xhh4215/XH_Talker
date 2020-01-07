package com.example.factory.prisistence;

public class Account {
    private static  String pushId;
    public static String getPushId(){
        return pushId;
    }
    public void setPushId(String pushId){
        Account.pushId = pushId;

    }
}
