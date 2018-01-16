package com.base.example;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity  extends BaseActivity implements View.OnClickListener{

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
        MenuItem search = menu.findItem(R.id.mu_search);
        final MenuItem setting = menu.findItem(R.id.mu_setting);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting.setVisible(false);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setting.setVisible(true);
                return false;
            }
        });
        initSearchView(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mu_setting){
            //设置按钮
            showPopWindow();
        }
        return true;
    }

    private PopupWindow mPopWindow;
    private View mPopWindowView;
    private TextView tvOne,tvTwo,tvThree;
    private void showPopWindow(){
        if(mPopWindowView == null){
            mPopWindowView = LinearLayout.inflate(this,R.layout.menu_item_layout,null);
            tvOne = (TextView) mPopWindowView.findViewById(R.id.tv_one);
            tvOne.setOnClickListener(this);
            tvTwo = (TextView) mPopWindowView.findViewById(R.id.tv_two);
            tvTwo.setOnClickListener(this);
            tvThree = (TextView) mPopWindowView.findViewById(R.id.tv_three);
            tvThree.setOnClickListener(this);
        }
        if(mPopWindow == null){
            mPopWindow = new PopupWindow(mPopWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
            mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopWindow.setOutsideTouchable(true);
        }
        mPopWindow.showAsDropDown(findViewById(R.id.mu_setting),-100,10);
    }

    @Override
    public void onClick(View view) {
        if(tvOne == view){
            mPopWindow.dismiss();
            showToast("One");
        }else if(tvTwo == view){
            mPopWindow.dismiss();
            showToast("Two");
        }else if(tvThree == view){
            mPopWindow.dismiss();
            showToast("Three");
        }
    }
}