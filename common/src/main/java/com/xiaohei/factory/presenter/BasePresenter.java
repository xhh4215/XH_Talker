package com.xiaohei.factory.presenter;

public class BasePresenter<T extends BaseConstarct.View> implements BaseConstarct.Presenter {
    private   T mView;
    public  BasePresenter(T view){
        setView(view);

    }

    public  final T getView(){
        return mView;
    }
    protected void  setView(T view){
        this.mView = view;
        this.mView.setPresenter(this);

    }    @Override
    public void start() {
      T view = mView;
      if (view!=null){
          view.showLoading();
      }
    }

    @Override
    public void destory() {
        T view = mView;
        mView = null;
        if (view!=null){
         view.setPresenter(null);
         }
    }
}
