package com.example.factory.presenter.contact;


import com.example.factory.model.card.UserCard;
import com.xiaohei.factory.presenter.BaseConstarct;

public interface FollowContract {

      interface Presenter extends BaseConstarct.Presenter{
          void follow(String id);
    }

    interface  View extends BaseConstarct.View<Presenter>{
         void followSuccess(UserCard userCard);
      }
}
