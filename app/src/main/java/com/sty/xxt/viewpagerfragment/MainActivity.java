package com.sty.xxt.viewpagerfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyFragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //获取数据 在values/arrays.xml中进行定义然后调用
        String[] tabTitle = getResources().getStringArray(R.array.tab_titles);
        //将fragment装进列表中
        fragmentList = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            fragmentList.add(MyFragment.newInstance(i));
        }

        //声明ViewPager
        viewPager = findViewById(R.id.view_pager);
        //viewPager加载adapter
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabTitle);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1); //设置左右各缓存多少个page，如果小于1，该值默认设置为1（此时最多缓存3个page）
        //viewPager事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //定义TabLayout
        tabLayout = findViewById(R.id.tab_layout);
        //tabLayout的事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //tabLayout加载ViewPager
        tabLayout.setupWithViewPager(viewPager);
        Drawable d = null;
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            String tabText = tabTitle[i];
            switch (i) {
                case 0:
                    d = getResources().getDrawable(R.drawable.selector_tab_home);
                    break;
                case 1:
                    d = getResources().getDrawable(R.drawable.selector_tab_manager);
                    break;
                case 2:
                    d = getResources().getDrawable(R.drawable.selector_tab_shopping_cart);
                    break;
                case 3:
                    d = getResources().getDrawable(R.drawable.selector_tab_me);
                    break;
            }
//            tab.setIcon(d);
            tab.setCustomView(getTabView(tabText, d));
        }
    }

    // Tab自定义item view
    private View getTabView(String title, Drawable image_src) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_item_view, null);
        TextView textView = (TextView) v.findViewById(R.id.textview);
        textView.setText(title);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageview);
//        imageView.setImageResource(image_src);
        imageView.setImageDrawable(image_src);
        return v;
    }

}
