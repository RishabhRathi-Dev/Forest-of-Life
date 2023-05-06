package com.example.forestoflife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainPage extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FragmentAdaptorForView fragmentAdaptorForView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize ViewPager2 and TabLayout
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);

        // Create adapter for ViewPager2
        fragmentAdaptorForView = new FragmentAdaptorForView(getSupportFragmentManager(), getLifecycle());

        // Set adapter for ViewPager2
        viewPager.setAdapter(fragmentAdaptorForView);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Nursery");
                    break;
                case 1:
                    tab.setText("Forest");
                    break;
                case 2:
                    tab.setText("Garden");
                    break;
            }
        }).attach();


    }
}