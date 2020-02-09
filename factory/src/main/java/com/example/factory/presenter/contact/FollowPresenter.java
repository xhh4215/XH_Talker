package com.example.factory.presenter.contact;

import com.example.factory.data.helper.UserHelper;
import com.example.factory.model.card.UserCard;
import com.xiaohei.factory.data.DataSource;
import com.xiaohei.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class FollowPresenter extends BasePresenter<FollowContract.View> implements FollowContract.Presenter, DataSource.CallBack<UserCard> {


    public FollowPresenter(FollowContract.View view) {
        super(view);
    }



    @Override
    public void onDataLoaded(final UserCard user) {
      final FollowContract.View  view = getView();
      if (view!=null){
          Run.onUiAsync(new Action() {
              @Override
              public void call() {
                    view.followSuccess(user);
              }
          });
      }
    }

    @Override
    public void onDataNotAvailable(final int str) {
        final FollowContract.View  view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                   view.showError(str);
                }
            });
        }
    }

    @Override
    public void follow(String id) {
        start();
        UserHelper.follow(id,this);
    }
}
