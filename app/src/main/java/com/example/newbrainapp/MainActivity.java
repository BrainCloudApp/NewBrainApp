package com.example.newbrainapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.lmq.common.Appstorage;

public class MainActivity extends AppCompatActivity {

    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment3 fragment3 = new Fragment3();
    private Fragment[] fragments;
    private int lastFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    if (lastFragment != 0) {
                        switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    return true;
                case R.id.navigation_community:
                    if (lastFragment != 1) {
                        switchFragment(lastFragment, 1);
                        lastFragment = 1;
                    }
                    return true;
                case R.id.navigation_my:
                    if (lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   Appstorage.setLoginState(this, true);
        if (Appstorage.getLoginState(this) == true){
            initFragment();
        }else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

    }

    private void initFragment() {
        fragments = new Fragment[]{fragment1, fragment2, fragment3};
        lastFragment = 0;

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, fragment1)
                .show(fragment1).commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(changeFragment);
    }

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments[lastFragment]);
        if (!fragments[index].isAdded()) {
            fragmentTransaction.add(R.id.main_view, fragments[index]);
        }
        fragmentTransaction.show(fragments[index]).commitAllowingStateLoss();
    }


}
