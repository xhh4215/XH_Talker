package com.example.factory.presenter.search;

import com.example.factory.model.card.GroupCard;
import com.example.factory.model.card.UserCard;
import com.xiaohei.factory.presenter.BaseConstarct;

import java.util.List;


public interface SearchConstact {

    interface  Presenter extends BaseConstarct.Presenter{
     void  search(String content);
    }

    interface UserView extends BaseConstarct.View<Presenter>{
        void  onSearchUserDone(List<UserCard> userCard);

    }
    interface GroupView extends BaseConstarct.View<Presenter>{
        void onSearchGroupDone(List<GroupCard> groupCards);

    }
}
