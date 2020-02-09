package com.xiaohei.talker.fragment.search;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.factory.model.card.UserCard;
import com.example.factory.presenter.contact.FollowContract;
import com.example.factory.presenter.contact.FollowPresenter;
import com.example.factory.presenter.search.SearchConstact;
import com.example.factory.presenter.search.SearchUserPresenter;
import com.xiaohei.common.EmptyView;
import com.xiaohei.common.app.FragmentPresenter;
import com.xiaohei.common.widget.PortraitView;
import com.xiaohei.common.widget.recycler.RecyclerAdapter;
import com.xiaohei.talker.R;
import com.xiaohei.talker.activities.SearchActivity;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索人的实现
 */
public class SearchUserFragment extends FragmentPresenter<SearchConstact.Presenter>
        implements SearchActivity.SearchFragment, SearchConstact.UserView {

    @BindView(R.id.empty)
    EmptyView mEmptyView;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    private RecyclerAdapter<UserCard> adapter;


    public SearchUserFragment() {
     }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter = new RecyclerAdapter<UserCard>() {
            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }

            @Override
            protected int getItemViewType(int position, UserCard userCard) {
                //返回cell的布局id
                return R.layout.cell_search_list;
            }


        });
        mEmptyView.bind(mRecyclerView);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected void initData() {
        super.initData();
        search("");
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);

    }

    @Override
    protected SearchUserPresenter initPreenter() {
        return new SearchUserPresenter(this);
    }

    @Override
    public void onSearchUserDone(List<UserCard> userCard) {
       adapter.replace(userCard);
       // 如果有数据 这是ok 没有数据就显示空布局
       mPlaceHolderView.triggerOkOrEmpty(adapter.getItemCount()>0);
    }

    class  ViewHolder extends RecyclerAdapter.ViewHolder<UserCard> implements FollowContract.View {
        @BindView(R.id.im_portrait)
        PortraitView  mPortraitView;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.im_follow)
        ImageView mFollow;
        private FollowContract.Presenter mPresenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            new FollowPresenter(this);
        }

        @Override
        protected void onBind(UserCard userCard) {
            Glide.with(getContext())
                    .load(userCard.getPortrait())
                    .centerCrop()
                    .into(mPortraitView);
            mName.setText(userCard.getName());
            mFollow.setEnabled(!userCard.isFollow());
        }

        @Override
        public void followSuccess(UserCard userCard) {
        if (mFollow.getDrawable() instanceof LoadingCircleDrawable){
            ((LoadingCircleDrawable) mFollow.getDrawable()).stop();
            mFollow.setImageResource(R.drawable.sel_opt_done_add);
        }
        updateData(userCard);
        }
        @OnClick(R.id.im_follow)
        void onFollowClick(){
            mPresenter.follow(mData.getId());
        }

        @Override
        public void showError(int str) {
            // 更改当前界面状态
            if (mFollow.getDrawable() instanceof LoadingDrawable) {
                // 失败则停止动画，并且显示一个圆圈
                LoadingDrawable drawable = (LoadingDrawable) mFollow.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void showLoading() {
            int Minsize = (int) Ui.dipToPx(getResources(),22);
            int Maxsize = (int) Ui.dipToPx(getResources(),30);
            LoadingDrawable loadingDrawable = new LoadingCircleDrawable(Minsize,Maxsize);
            loadingDrawable.setBackgroundColor(0);
            loadingDrawable.setForegroundColor(new int[]{UiCompat.getColor(getResources(),R.color.white_alpha_208)});
             mFollow.setImageDrawable(loadingDrawable);
             loadingDrawable.start();
        }

        @Override
        public void setPresenter(FollowContract.Presenter presenter) {
            mPresenter = presenter;
        }
    }

}
