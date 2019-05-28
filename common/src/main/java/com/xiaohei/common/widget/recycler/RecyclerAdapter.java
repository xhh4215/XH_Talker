package com.xiaohei.common.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaohei.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>> implements View.OnClickListener, View.OnLongClickListener
        , AdapterCallback {
    private AdapterListener<Data> mListener;
    //保存数据的集合
    private final List<Data> mDataList;

    /***
     * 构造函数模块
     */
    public RecyclerAdapter() {
        this(null);

    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;

    }

    /***
     * 创建viewholder的方法
     * @param parent  RecyclerView
     * @param viewType  界面的类型 约定为xml布局的id
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        //通过子类必须实现的方法获取ViewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        //设置view的tag为ViewHolder 进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);
        //设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        //进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.callback = this;
        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型  XML的布局ID
     * @return viewholder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /***
     * holder和数据绑定的方法
     * @param holder  数据块holder
     * @param position holder的位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        //或得绑定的数据
        Data data = mDataList.get(position);
        //触发holder绑定的方法
        holder.onBind(data);
    }

    /***
     * 复写默认的布局类型返回
     * @param position  坐标
     * @return 返回的都是XML布局文件的id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /***
     * 得到布局的类型
     * @param position 坐标
     * @param data 当前数据
     * @return XML文件的id 用于创建ViewHolder
     */
    protected abstract int getItemViewType(int position, Data data);

    /***
     * 点击事件的处理
     * @param v 被点击的View
     */
    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到View Holder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            this.mListener.onItemCLick(viewHolder, mDataList.get(pos));
        }
    }

    /***
     * 我们的自定义的监听器的
     * @param <Data> 范型
     */
    public interface AdapterListener<Data> {
        //当Cell点击的时候触发
        void onItemCLick(RecyclerAdapter.ViewHolder holder, Data data);

        //当Cell长按的时候触发
        void onItemLongCLick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * 长按点击处理
     *
     * @param v 被长按的view
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到View Holder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            this.mListener.onItemLongCLick(viewHolder, mDataList.get(pos));
        }
        return true;
    }

    /***
     * 设置适配器监听
     * @param adapterListener adapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /***
     * 得到当前集合的数据量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /***
     * 插入一条数据并通知插入
     * @param data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /***
     * 插入一堆数据 并通知这段集合更新
     * @param datalist
     */
    public void add(Data... datalist) {

        if (datalist != null && datalist.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, datalist);
            notifyItemRangeInserted(startPos, datalist.length);

        }
    }

    /***
     * 插入一堆数据 并通知这段集合更新
     * @param datalist
     */
    public void add(Collection<Data> datalist) {

        if (datalist != null && datalist.size() > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList);
            notifyItemRangeInserted(startPos, datalist.size());

        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /***
     * 替换为一个新的集合
     * @param dataList 一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mDataList.addAll(dataList);
        notifyDataSetChanged();


    }

    /***
     *
     * 自定义的ViewHolder
     * @param <Data>  范型数据类型
     * */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder unbinder;
        private AdapterCallback<Data> callback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /***
         * 用于绑定数据的触发
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /***
         * 当触发绑定数据的时候的回调；必须复写
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /***
         * holder 对自己对应的数据进行更新的操作
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }
    }
}
