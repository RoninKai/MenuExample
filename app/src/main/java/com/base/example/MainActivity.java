package com.base.example;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    public void initView() {
        initBar("Title");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //设置menu视图
        getMenuInflater().inflate(R.menu.main, menu);
        initSearch(menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化搜索框属性
     *
     * @param menu
     */
    private void initSearch(Menu menu) {
        //设置按钮的相关配置
        final MenuItem setting = menu.findItem(R.id.mu_setting);
        View view = setting.getActionView();
        //获取自定义布局的控件
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
        //自定义布局添加点击事件，以此替换menu的id，如不需要改变menu显示值则取setting.getActionView()设置点击事件即可
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(setting);
            }
        });

        //搜索的相关配置
        MenuItem search = menu.findItem(R.id.mu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting.setVisible(false);
                tvToolbarTitle.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setting.setVisible(true);
                tvToolbarTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        initSearchView(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mu_setting) {
            //设置按钮
            showPopWindow();
        }
        return true;
    }

    private PopupWindow mPopWindow;
    private View mPopWindowView;
    private TextView tvOne, tvTwo, tvThree, tvStatus;

    private void showPopWindow() {
        if (mPopWindowView == null) {
            mPopWindowView = LinearLayout.inflate(this, R.layout.menu_item_layout, null);
            tvOne = (TextView) mPopWindowView.findViewById(R.id.tv_one);
            tvOne.setOnClickListener(this);
            tvTwo = (TextView) mPopWindowView.findViewById(R.id.tv_two);
            tvTwo.setOnClickListener(this);
            tvThree = (TextView) mPopWindowView.findViewById(R.id.tv_three);
            tvThree.setOnClickListener(this);
        }
        if (mPopWindow == null) {
            mPopWindow = new PopupWindow(mPopWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopWindow.setOutsideTouchable(true);
        }
        mPopWindow.showAsDropDown(findViewById(R.id.mu_setting), -100, 10);
    }

    @Override
    public void onClick(View view) {
        if (tvOne == view) {
            mPopWindow.dismiss();
            tvStatus.setText("One");
        } else if (tvTwo == view) {
            mPopWindow.dismiss();
            tvStatus.setText("Two");
        } else if (tvThree == view) {
            mPopWindow.dismiss();
            tvStatus.setText("Three");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}