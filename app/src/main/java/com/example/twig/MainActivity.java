package com.example.twig;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.iammert.library.readablebottombar.ReadableBottomBar;


public class MainActivity extends AppCompatActivity {


    View rootView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(rootView);

        DatabaseUtil.init();
        StorageUtil.init();

        //Hide Toolbar manually
        //getSupportActionBar().hide();
        toolbar = rootView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MainActivity.this.setTitle("My Profile");

        //Transparent Action Bar
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        toolbar.setVisibility(View.GONE);
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();

        ReadableBottomBar bottomBar = rootView.findViewById(R.id.readableBottomBar);
        bottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.content, new HomeFragment());
                        break;
                    case 1:
                        toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.content, new NotificationFragment());
                        break;
                    case 2:
                        toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.content, new PostFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.content, new SearchFragment());
                        toolbar.setVisibility(View.GONE);
                        break;
                    case 4:
                        transaction.replace(R.id.content, new Profile2Fragment());
                        toolbar.setVisibility(View.VISIBLE);
                        break;

                }
                transaction.commit();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Toast.makeText(this, "Setting Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}