package com.xiaohei.talker.fragment.search;


import com.xiaohei.common.app.Fragment;
import com.xiaohei.talker.R;
import com.xiaohei.talker.activities.SearchActivity;

/**
 * 搜索群的实现
 */
public class SearchGroupFragment extends Fragment implements SearchActivity.SearchFragment {


    public SearchGroupFragment() {
        // Required empty public constructor
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {

    }
}
