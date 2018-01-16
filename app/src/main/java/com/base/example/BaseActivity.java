package com.base.example;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private SearchView.SearchAutoComplete searchAutoComplete;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        setSystemBar();
    }

    /**
     * 设置沉浸式状态栏
     */
    private void setSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.touming);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.touming);
        }
    }

    public void initBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_base_toobar);
        if (toolbar == null) {
            return;
        }
        //设置返回按钮
        toolbar.setNavigationIcon(R.mipmap.back);
        //设置原本的标题为空，使用自定义的标题
        toolbar.setTitle("");
        //设置标题
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        //取代原本的actionbar
        setSupportActionBar(toolbar);
        //添加返回点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    /**
     * 设置menu的字体颜色（特例情况）
     *
     * @param menu
     */
    private void setMenuColor(Menu menu) {
        //设置字体颜色（特殊情况处理，未检测过）
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spanString.length(), 0);
            item.setTitle(spanString);
        }
    }

    public void initSearchView(SearchView searchView) {
        if (searchView == null) {
            return;
        }
        this.searchView = searchView;
        initSearchView();
        searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.While));    //设置输入框hint颜色
        searchAutoComplete.setHint("在此输入查询条件....");
        searchAutoComplete.setTextColor(getResources().getColor(R.color.While));    //设置输入框text颜色
        searchAutoComplete.setTextSize(14);
        try {
            //设置输入框的光标颜色
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            field.set(searchAutoComplete, R.drawable.search_cursor_color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSearchView() {
//        searchView.setIconified(true); //设置searchView处于展开状态
//        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
//        searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setMenuColor(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击toolbar退出按钮以及返回按钮的事件处理
     */
    private void back() {
        if (searchView != null && searchAutoComplete != null && searchAutoComplete.isShown()) {
            //清除文本
            searchAutoComplete.setText("");
            //方法一   模拟关闭按钮一次点击事件
            (searchView.findViewById(R.id.search_close_btn)).performClick();
            //方法二   利用反射调用收起SearchView的onCloseClicked()方法
                    /* try {
                        Method method = searchView.getClass().getDeclaredMethod("onCloseClicked");
                        method.setAccessible(true);
                        method.invoke(searchView);
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    } */

            return;
        }
        finish();
    }

    public void showToast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(Object msg) {
        showToast(msg + "");
    }

    public abstract int getLayoutId();

    public abstract void initView();

}
