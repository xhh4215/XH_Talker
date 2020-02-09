package com.example.factory.presenter.search;

import com.example.factory.data.helper.UserHelper;
import com.example.factory.model.card.UserCard;
import com.xiaohei.factory.data.DataSource;
import com.xiaohei.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

public class SearchUserPresenter extends BasePresenter<SearchConstact.UserView>
        implements SearchConstact.Presenter, DataSource.CallBack<List<UserCard>> {
    private Call searchCall;
    public SearchUserPresenter(SearchConstact.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();
        Call call = searchCall;
        if (call!=null&&call.isCanceled()){
            call.cancel();
        }
       searchCall =  UserHelper.search(content,this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> user) {
       final SearchConstact.UserView userView = getView();
       if (userView!=null){
           Run.onUiAsync(new Action() {
               @Override
               public void call() {
                 userView.onSearchUserDone(user);
               }
           });
       }
    }

    @Override
    public void onDataNotAvailable(final int str) {
        final SearchConstact.UserView userView = getView();
        if (userView!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    userView.showError(str);
                }
            });
        }
    }
}
