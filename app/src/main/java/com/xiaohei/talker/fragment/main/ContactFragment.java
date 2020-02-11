package com.xiaohei.talker.fragment.main;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.factory.model.db.User;
import com.example.factory.presenter.contact.ContactContract;
import com.example.factory.presenter.contact.ContactPresenter;
import com.xiaohei.common.EmptyView;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.app.FragmentPresenter;
import com.xiaohei.common.widget.PortraitView;
import com.xiaohei.common.widget.recycler.RecyclerAdapter;
import com.xiaohei.talker.R;
import com.xiaohei.talker.activities.MessageActivity;
import com.xiaohei.talker.activities.PersonalActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends FragmentPresenter<ContactContract.Presenter> implements ContactContract.View {
    @BindView(R.id.empty)
    EmptyView mEmptyView;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    private RecyclerAdapter<User> adapter;
    public ContactFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter = new RecyclerAdapter<User>() {
            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new ContactFragment.ViewHolder(root);
            }

            @Override
            protected int getItemViewType(int position, User user) {
                //返回cell的布局id
                return R.layout.cell_contact_list;
            }


        });
        adapter.setListener(new RecyclerAdapter.AdapterListener<User>() {
            @Override
            public void onItemCLick(RecyclerAdapter.ViewHolder holder, User user) {
                MessageActivity.show(getContext(),user);
            }

            @Override
            public void onItemLongCLick(RecyclerAdapter.ViewHolder holder, User user) {

            }

        });
        mEmptyView.bind(mRecyclerView);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected void onFristInit() {
        super.onFristInit();
        mPresenter.start();
    }

    @Override
    protected ContactContract.Presenter initPreenter() {
        return new ContactPresenter(this);
    }

    @Override
    public RecyclerAdapter<User> getRecyclerViewApater() {
        return adapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mPlaceHolderView.triggerOkOrEmpty(adapter.getItemCount()>0);

    }

    class ViewHolder  extends RecyclerAdapter.ViewHolder<User>{
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.txt_desc)
        TextView mDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User user) {
            mPortraitView.setUp( Glide.with(getContext()),user);
            mName.setText(user.getName());
            mDesc.setText(user.getDesc());
            }
        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            // 显示信息
            PersonalActivity.show(getContext(), mData.getId());
        }
    }

}
