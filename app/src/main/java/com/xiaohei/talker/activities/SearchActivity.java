package com.xiaohei.talker.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;

import com.xiaohei.common.app.Fragment;
import com.xiaohei.common.app.ToolBarActivity;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.search.SearchGroupFragment;
import com.xiaohei.talker.fragment.search.SearchUserFragment;

public class SearchActivity extends ToolBarActivity {
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_USER =1;
    public static final int TYPE_GROUP =2;
    private int type;
    private SearchFragment mSearchFragment;


    /***
     * 显示搜索界面
     * @param context  上下文
     * @param type  搜索类型
     */
    public static void show(Context context, int type){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putExtra(EXTRA_TYPE,type);
        context.startActivity(intent);

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //显示对应的fragment
        Fragment fragment;
        if (type== TYPE_USER){
            SearchUserFragment searchUserFragment = new SearchUserFragment();
            fragment = searchUserFragment;
            mSearchFragment = searchUserFragment;
        }else{
            SearchGroupFragment searchGroupFragment = new SearchGroupFragment();
            fragment = searchGroupFragment;
            mSearchFragment = searchGroupFragment;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container,fragment).commit();
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRA_TYPE);
        return type==TYPE_GROUP|| type== TYPE_USER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_items,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView!=null){
            // 拿到一个搜索管理器
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            // 添加搜索监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // 当点击了提交按钮的时候
                    search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // 当文字改变的时候，咱们不会及时搜索，只在为null的情况下进行搜索
                    if (TextUtils.isEmpty(s)) {
                        search("");
                        return true;
                    }
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * 搜索的发起点
     *
     * @param query 搜索的文字
     */
    private void search(String query) {
        if (mSearchFragment == null)
            return;
        mSearchFragment.search(query);
    }
    /**
     * 搜索的Fragment必须继承的接口
     */
    public interface SearchFragment {
        void search(String content);
    }

}
