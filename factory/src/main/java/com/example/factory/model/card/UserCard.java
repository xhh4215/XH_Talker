package com.example.factory.model.card;


import com.example.factory.model.db.User;

import java.util.Date;

/**
 * 用户卡片，用于接收服务器返回
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class UserCard{
     private String id;
     private String name;
     private String phone;
     private String portrait;
     private String desc;
     private int sex = 0;
     private Date modifyAt;
     private int follows;
     private boolean isFollow;
     private int following;

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
    //
    private transient User user;
    public User build(){
     if (user==null){
      User user = new User();
      user.setId(id);
      user.setName(name);
      user.setPortrait(portrait);
      user.setPhone(phone);
      user.setDesc(desc);
      user.setSex(sex);
      user.setFollow(isFollow);
      user.setFollows(follows);
      user.setFollowing(following);
      user.setModifyAt(modifyAt);
      this.user = user;

     }
     return user;
    }
}
