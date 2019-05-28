package com.xiaohei.talker;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohei.common.app.Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements IView {

    @BindView(R.id.edit_query)
    EditText mInputText;
    @BindView(R.id.txt_result)
    TextView mResultText;
    private IPresenter mPresenter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new Presenter(this);

    }

    @Override
    protected void initWidget() {
    }


    @Override
    public String getInputString() {
        return mInputText.getText().toString();
    }

    @Override
    public void setResultString(String string) {
        mResultText.setText(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        Toast.makeText(this, "onCLick 事件", Toast.LENGTH_LONG).show();
        mPresenter.search();
    }
}
