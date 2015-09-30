package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;
import android.widget.TextView;

import com.tritonitsolutions.layaltydemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by TritonDev on 28/9/2015.
 */
public class ShopCategoryActivity extends TabActivity {
    ViewPager pager=null;
    int count=0;
    Timer timer;
    TabHost host;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopcategory_layout);
        ViewPagerAdapter adapter=new ViewPagerAdapter(this);
        pager=(ViewPager)findViewById(R.id.reviewpager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count <= 3) {
                            pager.setCurrentItem(count);
                            count++;
                        } else {
                            count = 0;
                            pager.setCurrentItem(count);
                        }

                    }
                });
            }
        }, 500, 3000);
        host=getTabHost();

        TabHost.TabSpec menCategory=host.newTabSpec("Mens");
        menCategory.setIndicator("Mens");
        Intent menIntent=new Intent(this,MenActivity.class);
        menCategory.setContent(menIntent);

        TabHost.TabSpec womenCategory=host.newTabSpec("Womens");
        womenCategory.setIndicator("Womens");
        Intent womenIntent=new Intent(this,WomenActivity.class);
        womenCategory.setContent(womenIntent);

        TabHost.TabSpec kidsCategory=host.newTabSpec("Kids");
        kidsCategory.setIndicator("Kids");
        Intent kidsIntent=new Intent(this,KidsActivity.class);
        kidsCategory.setContent(kidsIntent);



        host.addTab(menCategory);
        host.addTab(womenCategory);
        host.addTab(kidsCategory);

        for(int i=0;i< host.getTabWidget().getChildCount();i++){
            host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#D8D8D8"));
            tv=(TextView)host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#848484"));

        }
        host.getTabWidget().setCurrentTab(0);
        host.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
        tv=(TextView)host.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for(int j=0; j< host.getTabWidget().getChildCount();j++){
                    host.getTabWidget().getChildAt(j).setBackgroundColor(Color.parseColor("#D8D8D8"));
                    tv=(TextView)host.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#848484"));
                }
                host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
                tv=(TextView)host.getCurrentTabView().findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

    }
    }

