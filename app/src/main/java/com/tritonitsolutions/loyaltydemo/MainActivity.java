package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.tritonitsolutions.layaltydemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    String TITLES[] = {"Profile","Shop","Shop Locator","Wallet","Call Us","Review","Feedback","WishList","Logout"};
    int ICONS[] = {R.drawable.menu_profile,R.drawable.menu_shop,R.drawable.menu_shop_locator,R.drawable.menu_wallet,R.drawable.menu_callus,R.drawable.menu_review,R.drawable.menu_feedback,R.drawable.menu_wishlist,R.drawable.menu_logout};
    String NAME = "Loyalty";
    String EMAIL = "11102000456";
    int PROFILE = R.drawable.img_user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    Context cntx=this;
    ViewPager pager=null;
    int count=0;
    Timer timer;
    LinearLayout ll_shop,ll_new_arrival,ll_notification,ll_loyalty;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        ll_loyalty=(LinearLayout)findViewById(R.id.iv_loyalty);
        ll_shop=(LinearLayout)findViewById(R.id.iv_shop);
        ll_new_arrival=(LinearLayout)findViewById(R.id.iv_new_arrival);
        ll_notification=(LinearLayout)findViewById(R.id.iv_notification);
        ll_loyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,LoyaltyActivity.class);
                startActivity(intent);
            }
        });
        ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });

        ll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,ShopCategoryActivity.class);
                startActivity(intent);

            }
        });
        ll_new_arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,NewArrivalActivity.class);
                startActivity(intent);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.nav_openDrawer,R.string.nav_closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
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
                        if(count<=3){
                            pager.setCurrentItem(count);
                            count++;
                        }else {
                            count=0;
                            pager.setCurrentItem(count);
                        }

                    }
                });
            }
        },500,3000);
    }

}



